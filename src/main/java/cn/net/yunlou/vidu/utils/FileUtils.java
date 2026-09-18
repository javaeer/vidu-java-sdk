package cn.net.yunlou.vidu.utils;

import cn.net.yunlou.vidu.sdk.constant.RegexConstant;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 */
@Slf4j
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static final String CLASS_PATH = "classpath:";

    // 协议超时时间（毫秒）
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    private static final long MAX_CHUNK_SIZE = 100L * 1024 * 1024; // 100 MB in bytes


    public static final int BUFFER_AREA_SIZE =8192;


    public static long checksumCRC32(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream cannot be null");
        }

        CRC32 crc = new CRC32();
        byte[] buffer = new byte[BUFFER_AREA_SIZE]; // 更大的缓冲区提升性能
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                crc.update(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to compute CRC32 checksum", e);
        }

        return crc.getValue();
    }

    public static void download(String url, String tmpVideoFile) throws IOException {

        OkHttp3ClientUtils instance = OkHttp3ClientUtils.getInstance();
        long size = instance.getFileSize(url);
        if (size > MAX_CHUNK_SIZE) {
            instance.downloadChunkFile(url, tmpVideoFile);
        } else {
            instance.downloadFile(url,tmpVideoFile);
            //FileUtils.copyURLToFile(new URL(url), new File(tmpVideoFile));
        }

    }

    /**
     * @param fileSize
     * @return
     */
    public static String formatFileSize(Double fileSize) {
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileSize == null) {
            return "";
        }
        if (fileSize < 1024) {
            fileSizeString = df.format(fileSize) + " B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format(fileSize / 1024) + " K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format(fileSize / 1048576) + " M";
        } else {
            fileSizeString = df.format(fileSize / 1073741824) + " G";
        }
        return fileSizeString;
    }


    /**
     * 将MB换算为GB
     *
     * @param fileSizeMb
     * @return
     */
    public static String formatFileSizeMb2Gb(double fileSizeMb) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(fileSizeMb / 1024) + "GB";
    }

    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String descFileName) {
        return FileUtils.copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @param coverlay     如果目标文件已存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFileCover(String srcFileName,
                                        String descFileName, boolean coverlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            log.debug("复制文件失败，源文件 " + srcFileName + " 不存在!");
            return false;
        }
        // 判断源文件是否是合法的文件
        else if (!srcFile.isFile()) {
            log.debug("复制文件失败，" + srcFileName + " 不是一个文件!");
            return false;
        }
        File descFile = new File(descFileName);
        // 判断目标文件是否存在
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                log.debug("目标文件已存在，准备删除!");
                if (!FileUtils.remove(descFileName)) {
                    log.debug("删除目标文件 " + descFileName + " 失败!");
                    return false;
                }
            } else {
                log.debug("复制文件失败，目标文件 " + descFileName + " 已存在!");
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                log.debug("目标文件所在的目录不存在，创建目录!");
                // 创建目标文件所在的目录
                if (!descFile.getParentFile().mkdirs()) {
                    log.debug("创建目标文件所在的目录失败!");
                    return false;
                }
            }
        }

        // 准备复制文件
        // 读取的位数
        int readByte;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 打开源文件
            ins = new FileInputStream(srcFile);
            // 打开目标文件的输出流
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            // 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
            while ((readByte = ins.read(buf)) != -1) {
                // 将读取的字节流写入到输出流
                outs.write(buf, 0, readByte);
            }
            log.debug("复制单个文件 " + srcFileName + " 到" + descFileName
                    + "成功!");
            return true;
        } catch (Exception e) {
            log.debug("复制文件失败：" + e.getMessage());
            return false;
        } finally {
            // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException oute) {
                    oute.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    ine.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) {
        return FileUtils.copyDirectoryCover(srcDirName, descDirName,
                false);
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @param coverlay    如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectoryCover(String srcDirName,
                                             String descDirName, boolean coverlay) {
        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            log.debug("复制目录失败，源目录 " + srcDirName + " 不存在!");
            return false;
        }
        // 判断源目录是否是目录
        else if (!srcDir.isDirectory()) {
            log.debug("复制目录失败，" + srcDirName + " 不是一个目录!");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames += File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                log.debug("目标目录已存在，准备删除!");
                if (!FileUtils.remove(descDirNames)) {
                    log.debug("删除目录 " + descDirNames + " 失败!");
                    return false;
                }
            } else {
                log.debug("目标目录复制失败，目标目录 " + descDirNames + " 已存在!");
                return false;
            }
        } else {
            // 创建目标目录
            log.debug("目标目录不存在，准备创建!");
            if (!descDir.mkdirs()) {
                log.debug("创建目标目录失败!");
                return false;
            }

        }

        boolean flag = true;
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果是一个单个文件，则直接复制
            if (files[i].isFile()) {
                flag = FileUtils.copyFile(files[i].getAbsolutePath(),
                        descDirName + files[i].getName());
                // 如果拷贝文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 如果是子目录，则继续复制目录
            if (files[i].isDirectory()) {
                flag = FileUtils.copyDirectory(files[i]
                        .getAbsolutePath(), descDirName + files[i].getName());
                // 如果拷贝目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 失败!");
            return false;
        }
        log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 成功!");
        return true;

    }

    /**
     * 删除文件，可以删除单个文件或文件夹
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean remove(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.debug(fileName + " 文件不存在!");
            return true;
        } else {
            if (file.isFile()) {
                return FileUtils.deleteFile(fileName);
            } else {
                return FileUtils.deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.debug("删除文件 " + fileName + " 成功!");
                return true;
            } else {
                log.debug("删除文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            log.debug(fileName + " 文件不存在!");
            return true;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames += File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.debug(dirNames + " 目录不存在!");
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i]
                        .getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            log.debug("删除目录 " + dirName + " 成功!");
            return true;
        } else {
            log.debug("删除目录 " + dirName + " 失败!");
            return false;
        }

    }

    /**
     * 创建单个文件
     *
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            log.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            log.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                log.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                log.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                log.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(descFileName + " 文件创建失败!");
            return false;
        }

    }

    /**
     * 创建目录
     *
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames += File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            log.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            log.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            log.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    /**
     * 写入文件
     *
     * @param fileName
     * @param content
     * @param append
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, "utf-8", append);
            log.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 写入文件
     *
     * @param fileName
     * @param content
     * @param encoding
     * @param append
     */
    public static void writeToFile(String fileName, String content, String encoding, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, encoding, append);
            log.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    public static String readInputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (true) {
            try {
                if (null == (line = reader.readLine())) {
                    break;
                }
                stringBuilder.append(line);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return stringBuilder.toString();
    }

    /**
     * 压缩文件或目录
     *
     * @param srcDirName   压缩的根目录
     * @param fileName     根目录下的待压缩的文件名或文件夹名，其中*或""表示跟目录下的全部文件
     * @param descFileName 目标zip文件
     */
    public static void zipFiles(String srcDirName, String fileName,
                                String descFileName) {
        // 判断目录是否存在
        if (srcDirName == null) {
            log.debug("文件压缩失败，目录 " + srcDirName + " 不存在!");
            return;
        }
        File fileDir = new File(srcDirName);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            log.debug("文件压缩失败，目录 " + srcDirName + " 不存在!");
            return;
        }
        String dirPath = fileDir.getAbsolutePath();
        File descFile = new File(descFileName);
        try {
            ZipOutputStream zouts = new ZipOutputStream(new FileOutputStream(
                    descFile));
            if ("*".equals(fileName) || "".equals(fileName)) {
                FileUtils.zipDirectoryToZipFile(dirPath, fileDir, zouts);
            } else {
                File file = new File(fileDir, fileName);
                if (file.isFile()) {
                    FileUtils.zipFilesToZipFile(dirPath, file, zouts);
                } else {
                    FileUtils
                            .zipDirectoryToZipFile(dirPath, file, zouts);
                }
            }
            zouts.close();
            log.debug(descFileName + " 文件压缩成功!");
        } catch (Exception e) {
            log.debug("文件压缩失败：" + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
     *
     * @param zipFileName  需要解压的ZIP文件
     * @param descFileName 目标文件
     */
    public static boolean unZipFiles(String zipFileName, String descFileName) {
        String descFileNames = descFileName;
        if (!descFileNames.endsWith(File.separator)) {
            descFileNames += File.separator;
        }
        try {
            // 根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(zipFileName);
            ZipEntry entry;
            String entryName;
            String descFileDir;
            byte[] buf = new byte[4096];
            int readByte;
            // 获取ZIP文件里所有的entry
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.getEntries();
            // 遍历所有entry
            while (enums.hasMoreElements()) {
                entry = (ZipEntry) enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory()) {
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                } else {
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }
                File file = new File(descFileDir);
                // 打开文件输出流
                OutputStream os = new FileOutputStream(file);
                // 从ZipFile对象中打开entry的输入流
                InputStream is = zipFile.getInputStream(entry);
                while ((readByte = is.read(buf)) != -1) {
                    os.write(buf, 0, readByte);
                }
                os.close();
                is.close();
            }
            zipFile.close();
            log.debug("文件解压成功!");
            return true;
        } catch (Exception e) {
            log.debug("文件解压失败：" + e.getMessage());
            return false;
        }
    }

    public static void writeZip(List<String> files, String zipFile) {
        byte[] buf = new byte[8192];
        int len;
        OutputStream os;
        ZipOutputStream zos = null;
        BufferedInputStream bis = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(zipFile));
            zos = new ZipOutputStream(os);
            for (String filePath : files) {
                File file = new File(filePath);
                if (!file.exists() || !file.isFile()) {
                    continue;
                }
                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                bis = new BufferedInputStream(new FileInputStream(file));
                while ((len = bis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
            }
            zos.setEncoding("UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            IOUtils.closeQuietly(bis);
            try {
                zos.closeEntry();
            } catch (IOException e) {

            }
            IOUtils.closeQuietly(zos);
        }
    }

    /**
     * 将目录压缩到ZIP输出流
     *
     * @param dirPath 目录路径
     * @param fileDir 文件信息
     * @param zouts   输出流
     */
    public static void zipDirectoryToZipFile(String dirPath, File fileDir,
                                             ZipOutputStream zouts) {
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            // 空的文件夹
            if (files.length == 0) {
                // 目录信息
                ZipEntry entry = new ZipEntry(getEntryName(dirPath, fileDir));
                try {
                    zouts.putNextEntry(entry);
                    zouts.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    // 如果是文件，则调用文件压缩方法
                    FileUtils
                            .zipFilesToZipFile(dirPath, files[i], zouts);
                } else {
                    // 如果是目录，则递归调用
                    FileUtils.zipDirectoryToZipFile(dirPath, files[i],
                            zouts);
                }
            }

        }

    }

    /**
     * 将文件压缩到ZIP输出流
     *
     * @param dirPath 目录路径
     * @param file    文件
     * @param zouts   输出流
     */
    public static void zipFilesToZipFile(String dirPath, File file,
                                         ZipOutputStream zouts) {
        FileInputStream fin;
        ZipEntry entry;
        // 创建复制缓冲区
        byte[] buf = new byte[4096];
        int readByte;
        if (file.isFile()) {
            try {
                // 创建一个文件输入流
                fin = new FileInputStream(file);
                // 创建一个ZipEntry
                entry = new ZipEntry(getEntryName(dirPath, file));
                // 存储信息到压缩文件
                zouts.putNextEntry(entry);
                // 复制字节到压缩文件
                while ((readByte = fin.read(buf)) != -1) {
                    zouts.write(buf, 0, readByte);
                }
                zouts.closeEntry();
                fin.close();
                System.out
                        .println("添加文件 " + file.getAbsolutePath() + " 到zip文件中!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }




    /**
     * 修复路径，将 \\ 或 / 等替换为 File.separator
     *
     * @param url
     * @return
     */
    public static String repairUrl(String url) {
        String p = StringUtils.replace(url, "\\", File.separator);
        p = StringUtils.join(StringUtils.split(p, File.separator), File.separator);
        if (!StringUtils.startsWithAny(p, File.separator) && StringUtils.startsWithAny(url, "\\", File.separator)) {
            p += File.separator;
        }
        if (!StringUtils.endsWithAny(p, File.separator) && StringUtils.endsWithAny(url, "\\", File.separator)) {
            p = p + File.separator;
        }
        return p;
    }


    /**
     * 追加文件分隔符
     *
     * @param url
     * @return
     */
    public static String appendFileSeparator(String url) {
        return url + (url.lastIndexOf(File.separator) == url.length() - 1 ? "" : File.separator);
    }


    /**
     * 文件编码格式转换
     *
     * @param inFile     输入文件
     * @param inCharset  输入文件字符集
     * @param outFile    输出文件
     * @param outCharset 输出文件字符集
     * @return 转码后的字符流
     * @throws IOException
     */
    public static byte[] convertFileEncoding(File inFile, Charset inCharset, File outFile, Charset outCharset) throws IOException {

        RandomAccessFile inRandom = new RandomAccessFile(inFile, "r");


        FileChannel inChannel = inRandom.getChannel();

        //将输入文件的通道通过只读的权限 映射到内存中。
        MappedByteBuffer byteMapper = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int) inFile.length());

        CharsetDecoder inDecoder = inCharset.newDecoder();
        CharsetEncoder outEncoder = outCharset.newEncoder();

        CharBuffer cb = inDecoder.decode(byteMapper);
        ByteBuffer outBuffer = null;
        try {
            outBuffer = outEncoder.encode(cb);

            RandomAccessFile outRandom = null;
            FileChannel outChannel = null;
            if (outFile != null) {
                try {
                    outRandom = new RandomAccessFile(outFile, "rw");
                    outChannel = outRandom.getChannel();
                    outChannel.write(outBuffer);
                } finally {
                    if (outChannel != null) {
                        outChannel.close();
                    }
                    if (outRandom != null) {
                        outRandom.close();
                    }
                }
            }
        } finally {
            inChannel.close();
            inRandom.close();
        }


        return outBuffer.array();
    }

    /**
     * 文件编码格式转换
     *
     * @param inFile     输入文件
     * @param inCharset  输入文件字符集
     * @param outCharset 输出字符集
     * @return 转码后的字符流
     * @throws IOException Exception
     */
    public static byte[] convertFileEncoding(File inFile, Charset inCharset, Charset outCharset) throws IOException {
        return convertFileEncoding(inFile, inCharset, null, outCharset);
    }

    /**
     * 将文件字符集转化为指定字符集
     *
     * @param inFile     输入文件
     * @param outFile    输出文件
     * @param outCharset 输出文件字符集
     * @return 转码后的字符流
     * @throws IOException
     */
    public static byte[] convertFileEncoding(File inFile, File outFile, Charset outCharset) throws IOException {
        return convertFileEncoding(inFile, getFileEncoding(inFile), outFile, outCharset);
    }

    /**
     * 将文件字符集转化为指定字符集
     *
     * @param inFile 输入文件
     * @return 转码后的字符流
     * @throws IOException
     */
    public static byte[] convertFileEncoding(File inFile, Charset outCharset) throws IOException {
        return convertFileEncoding(inFile, (File) null, outCharset);
    }

    /**
     * 将文件字符集转换为系统字符集
     *
     * @param inFile 输入文件
     * @return 转码后的字符流
     * @throws IOException
     */
    public static byte[] convertFileEncodingToSys(File inFile) throws IOException {
        return convertFileEncoding(inFile, (File) null, Charset.defaultCharset());
    }

    /**
     * 将文件字符集转换为系统字符集
     *
     * @param inFile  输入文件
     * @param outFile 输出文件
     * @return 转码后的字符流
     * @throws IOException
     */
    public static byte[] convertFileEncodingToSys(File inFile, File outFile) throws IOException {
        return convertFileEncoding(inFile, outFile, Charset.defaultCharset());
    }

    public static String getFileNameFromUrl(String url) {

        // 解析URL参数
        String fileName = null;
        try {
            String query = new URL(url).getQuery();
            if (query != null) {
                String prefix = "filename=";
                if (query.contains("filename=")) {
                    prefix = "filename=";
                }
                if (query.contains("file=")) {
                    prefix = "file=";
                }
                String[] params = query.split("&");
                for (String param : params) {
                    if (param.startsWith(prefix)) {
                        return URLDecoder.decode(param.substring(prefix.length()), StandardCharsets.UTF_8.name());
                    }
                }
            }
            // 如果URL中没有filename参数，则尝试从路径中获取文件名
            String path = new URL(url).getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileSuffix(String filePath) {
        int splitIndex = filePath.lastIndexOf(".");
        return filePath.substring(splitIndex + 1);
    }

    public static String getFileSuffix(File file) {
        String fileFullName = file.getName();
        return getFileSuffix(fileFullName);
    }

    /**
     * 获取文件编码格式(字符集)
     *
     * @param file 输入文件
     * @return 字符集名称，如果不支持的字符集则返回null
     */
    public static Charset getFileEncoding(File file) {
        String charset = "GBK"; // 默认编码
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return Charset.forName(charset);
            }
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0) {
                        break;
                    }
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF) {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
                        // (0x80 -0xBF),也可能在GB编码内
                        {
                            continue;
                        } else {
                            break;
                        }
                        // 也有可能出错，但是几率较小
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return Charset.forName(charset);
    }

    public static String getFilePrefix(File file) {
        String fileFullName = file.getName();
        return getFilePrefix(fileFullName);
    }

    public static String getFilePrefix(String filePath) {
        int splitIndex = filePath.lastIndexOf(".");
        return filePath.substring(0, splitIndex);
    }

    /**
     * 获取待压缩文件在ZIP文件中entry的名字，即相对于跟目录的相对路径名
     *
     * @param file entry文件名
     * @return
     */
    private static String getEntryName(String dirPath, File file) {
        String dirPaths = dirPath;
        if (!dirPaths.endsWith(File.separator)) {
            dirPaths += File.separator;
        }
        String filePath = file.getAbsolutePath();
        // 对于目录，必须在entry名字后面加上File.separator，表示它将以目录项存储
        if (file.isDirectory()) {
            filePath += File.separator;
        }
        int index = filePath.indexOf(dirPaths);

        return filePath.substring(index + dirPaths.length());
    }

    /**
     * @param webUrl 网络路径
     * @return boolean
     * @Description 获取网络图片
     * @Date 2018/7/27 14:06
     * @Author laughtiger
     * @Version 1.0
     **/
    public static InputStream getInputStreamHttp(String webUrl) {
        URL url;
        InputStream is = null;
        try {
            url = new URL(webUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            //POST可以更好的传参
            // 设置之后可以向服务端写入数据、、可以使用conn.getOutputStream().write()
            // 可以传输参数,POST请求数据放在正文内,所以需要开启传参
//            conn.setRequestMethod("POST");// 设置请求方式,默认是GET
//            conn.setDoOutput(true);
            // http读取数据 默认是true,所以可以一般的请求都可以获取数据
            conn.setDoInput(true);
            // 是否使用缓存,Post 请求不能使用缓存
//            conn.setUseCaches(false);
            // 设置请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//            添加请求头的时候如果有中文，就有可能造成中文乱码这时候可以使用这个方法
            //conn.setRequestProperty("test", URLEncoder.encode("一个测试请求头", "UTF-8"))

            is = new DataInputStream(conn.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;

    }



    /**
     * 文件转化为字节数组
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try (FileInputStream stream = new FileInputStream(f);
             ByteArrayOutputStream out = new ByteArrayOutputStream(1000)
        ) {
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {

        }
        return null;
    }

    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


    public static long getFileSize(String filePath) {
        try {
            if (filePath == null || filePath.isEmpty()) {
                throw new IllegalArgumentException("File path cannot be null or empty.");
            }

            Path path = Paths.get(filePath);

            // Check if the file exists and is readable.
            if (!Files.exists(path)) {
                throw new FileNotFoundException("The specified file does not exist: " + filePath);
            }
            if (!Files.isReadable(path)) {
                throw new AccessDeniedException("Cannot read the file: " + filePath);
            }

            return Files.size(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to get the size of the file: " + filePath, e);
        }
    }

    /**
     * 腾讯云视频转码
     *
     * @param extName
     * @return
     */
    public static boolean isVideo(String extName) {
        String extNameStr = "wmv;wm;asf;asx;rm;rmvb;ra;ram;mpg;mpeg;mpe;vob;dat;mov;3gp;mp4;mp4v;m4v;mkv;avi;flv;f4v";
        if (StringUtils.isBlank(extNameStr)) {
            return false;
        }
        extName = extName.toLowerCase();
        String[] exts = extNameStr.split(";");
        for (String e : exts) {
            if (e.equals(extName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 科大讯飞语音转写
     *
     * @param extName
     * @return
     */
    public static boolean isVoice(String extName) {
        String extNameStr = "wav;flac;opus;m4a;mp3";
        if (StringUtils.isBlank(extNameStr)) {
            return false;
        }
        extName = extName.toLowerCase();
        String[] exts = extNameStr.split(";");
        for (String ext : exts) {
            if (ext.equals(extName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * aspose 支持转图片
     *
     * @param extName
     * @return
     */
    public static boolean isDocument(String extName) {
        String extNameStr = "doc;docx;ppt;pptx;xls;xlsx;pdf;jpg;jpeg;png";
        if (StringUtils.isBlank(extNameStr)) {
            return false;
        }
        extName = extName.toLowerCase();
        String[] exts = extNameStr.split(";");
        for (String ext : exts) {
            if (ext.equals(extName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param extName
     * @return
     */
    public static boolean isImage(String extName) {
        String extNameStr = "jpg;jpeg;gif;png;bmp";
        if (StringUtils.isBlank(extNameStr)) {
            return false;
        }
        extName = extName.toLowerCase();
        String[] exts = extNameStr.split(";");
        for (String ext : exts) {
            if (ext.equals(extName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 支持多种协议的文件存在性检查
     * @param uri 文件路径（支持格式示例）:
     *            - http://example.com/file.txt
     *            - smb://user:pass@192.168.1.1/share/file.txt
     *            - ftp://user:pass@ftp.example.com/file.txt
     *            - file:///path/to/local/file.txt
     *            - /absolute/path/to/file (本地绝对路径)
     * @return 文件是否存在
     */
    public static boolean isExists(String uri) {
        try {
            // 自动添加file协议前缀（用于识别纯路径）
            String normalizedUri = uri.contains("://") ? uri : "file://" + uri;
            URL url = new URL(normalizedUri);
            String protocol = url.getProtocol().toLowerCase();

            switch (protocol) {
                case "http":
                case "https":
                    return checkHttpExists(url);
                case "smb":
                    return checkSmbExists(url);
                case "ftp":
                case "ftps":
                    return checkFtpExists(url);
                case "file":
                    return checkLocalExists(url);
                default:
                    throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }
        } catch (Exception e) {
            return false; // 统一异常处理
        }
    }

    // 检查HTTP(S)文件
    private static boolean checkHttpExists(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    // 检查SMB共享文件（需要JCIFS库）
    private static boolean checkSmbExists(URL url) throws Exception {
        SmbFile smbFile = new SmbFile(url.toString());
        smbFile.setConnectTimeout(CONNECTION_TIMEOUT);
        return smbFile.exists();
    }

    // 检查FTP文件（需要Apache Commons Net库）
    private static boolean checkFtpExists(URL url) throws Exception {
        FTPClient ftp = new FTPClient();
        try {
            // 解析认证信息
            String userInfo = url.getUserInfo();
            String user = "anonymous";
            String pass = "";
            if (userInfo != null) {
                String[] parts = userInfo.split(":");
                user = parts[0];
                pass = (parts.length > 1) ? parts[1] : "";
            }

            // 连接服务器
            ftp.connect(url.getHost(), url.getPort() > 0 ? url.getPort() : 21);
            ftp.login(user, pass);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();

            return ftp.listFiles(url.getPath()).length > 0;
        } finally {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        }
    }

    // 检查本地文件
    private static boolean checkLocalExists(URL url) {
        return new File(url.getPath()).exists();
    }

    /**
     * 循环目录文件
     *
     * @param file
     */
    public static void loopDirectoryFiles(File file) {

        if (file.exists()) {
            if (file.isFile()) {
                if (file.length() > 5 * 1048576) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cal.setTimeInMillis(file.lastModified());
                    log.info("file is ==>>" + file.getAbsolutePath() + "\t修改时间:" + format.format(cal.getTime()));
                }
            } else {
                File[] list = file.listFiles();//list获取的结果：[D:\qrcodeFile\20190116, D:\qrcodeFile\20190117]
                if (list.length == 0) {
                    log.info(file.getAbsolutePath() + " is null");
                } else {
                    for (int i = 0; i < list.length; i++) {
                        loopDirectoryFiles(list[i]);
                    }
                }
            }
        } else {
            log.info("文件不存在！");
        }
    }

    /**
     * 该方式支持本地文件，也支持http/https远程文件
     *
     * @param fileUrl
     * @return
     */
    public static String getContentType(String fileUrl) {
        String contentType = null;
        try {
            contentType = new MimetypesFileTypeMap().getContentType(new File(fileUrl));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentType;
    }


    public static void main(String[] args) {
        //File file = new File("/Users/laughtiger/Documents/software");
        //loopDirectoryFiles(file);
//        log.info(getInputStreamFromUrl("http://thirdwx.qlogo.cn/mmopen/vi_32/eIdg5He49Rib6nX62XA1LHq7yuv8J6MSkERNiaiaxnDaR3l7AkVLAA9CL1gBZklf3TtZpxviaHapo6WJ42ktfCU8SQ/132"));
//        log.info(getInputStreamFromUrl("http://kipo-att-test.img-cn-beijing.aliyuncs.com/201805/ffww2NVTrC4Nwtti9Jp.png"));
//        log.info(getInputStreamHttp("http://img4.imgtn.bdimg.com/it/u=1373411777,3992091759&fm=27&gp=0.jpg"));

       /* try {
            copyURLToFile(new URL("https://prod-ss-vidu.s3.cn-northwest-1.amazonaws.com.cn/infer/tasks/24/1211/06/2556945731386601/creation-01/video.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIARRHG6JR7EMNHVUWT%2F20241211%2Fcn-northwest-1%2Fs3%2Faws4_request&X-Amz-Date=20241211T070012Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&response-cache-control=max-age%3D86400&response-content-disposition=attachment%3Bfilename%3Dhug--4-2024-12-11T07%253A00%253A12Z.mp4&x-id=GetObject&X-Amz-Signature=61a83e11b261a5b6471ff0536d66577449dd370401448f0e77e2a3baf0e35e37"), new File("/Users/laughtiger/Documents/memorial.mp4"));
            copyURLToFile(new URL("https://tcss.mothpro.cn/video/2024-12-11134348_4462.mov"), new File("/Users/laughtiger/Documents/ss.mp4"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        log.info(getFileNameFromUrl("https://prod-ss-vidu.s3.cn-northwest-1.amazonaws.com.cn/infer/tasks/24/1211/06/2556945731386601/creation-01/video.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIARRHG6JR7EMNHVUWT%2F20241211%2Fcn-northwest-1%2Fs3%2Faws4_request&X-Amz-Date=20241211T070012Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&response-cache-control=max-age%3D86400&response-content-disposition=attachment%3Bfilename%3Dhug--4-2024-12-11T07%253A00%253A12Z.mp4&x-id=GetObject&X-Amz-Signature=61a83e11b261a5b6471ff0536d66577449dd370401448f0e77e2a3baf0e35e37"));
        log.info(getFileNameFromUrl("https://tcss.mothpro.cn/video/2024-12-11134348_4462.mov"));


    }


}
