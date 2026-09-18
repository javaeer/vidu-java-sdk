package cn.net.yunlou.vidu.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @Author javaeer(javaeer @ aliyun.com)
 * @Date 2020/4/10 18:25
 * @Version 1.0
 */
@Slf4j
public class OkHttp3ClientUtils {

    private static final int READ_TIMEOUT = 60;
    private static final int CONNECT_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 5 * 60;
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");//提交Json数据
    private static final MediaType FORM = MediaType.get("multipart/form-data");//上传文件
    private static final byte[] LOCKER = new byte[0];

    private static final int CHUNK_SIZE = 20 * 1024 * 1024; // 每个分片大小为20MB


    private static OkHttpClient okHttpClient;
    private volatile static OkHttp3ClientUtils instance;//防止多个线程同时访问


    public OkHttp3ClientUtils() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 单例模式获取 NetUtils
     *
     * @return {@link OkHttp3ClientUtils}
     */
    public static OkHttp3ClientUtils getInstance() {
        if (instance == null) {
            synchronized (LOCKER) {
                if (instance == null) {
                    instance = new OkHttp3ClientUtils();
                }
            }
        }
        return instance;
    }

    /**
     * GET，同步方式，获取网络数据
     *
     * @param url 请求地址
     * @return {@link String}
     */
    public String getData(String url) {
        // 构造 Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        // 将 Request 封装为 Call
        Call call = okHttpClient.newCall(request);
        // 执行 Call，得到 Response
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("【get】请求发生异常:{}", e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * GET，同步方式，获取网络数据
     *
     * @param url 请求地址
     * @return {@link String}
     */
    public String getData(String url, Map<String, String> headerParams) {
        // 构造 Request
        Request.Builder builder = new Request.Builder();

        Headers headers = setRequestHeaders(headerParams);

        builder.headers(headers);

        Request request = builder.get().url(url).build();

        // 将 Request 封装为 Call
        Call call = okHttpClient.newCall(request);
        // 执行 Call，得到 Response
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("【get】请求发生异常:{}", e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    /**
     * GET 请求，异步方式，获取网络数据
     *
     * @param url      请求地址
     * @param callBack 回调函数
     */
    public void getDataAsync(String url, final Callback callBack) {
        // 构造 Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        // 将 Request 封装为 Call
        Call call = okHttpClient.newCall(request);
        // 执行 Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(call, response);
            }
        });
    }

    /**
     * POST 请求，同步方式，提交数据
     *
     * @param url        请求地址
     * @param bodyParams 请求参数
     * @return {@link String}
     */
    public String postData(String url, Map<String, String> bodyParams) {
        // 构造 RequestBody
        RequestBody body = setRequestBody(bodyParams);
        // 构造 Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        // 将 Request 封装为 Call
        Call call = okHttpClient.newCall(request);
        // 执行 Call，得到 Response
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("【post】请求发生异常:{}", e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * POST 请求，同步方式，提交数据
     *
     * @param url          请求地址
     * @param headerParams 请求头参数
     * @param bodyParams   请求参数
     * @return {@link String}
     */
    public String postData(String url, Map<String, String> headerParams, Map<String, String> bodyParams) {
        // 构造 RequestBody
        RequestBody body = setRequestBody(bodyParams);
        // 构造 Request
        Request.Builder requestBuilder = new Request.Builder();
        Headers headers = Headers.of(headerParams);
        Request request = requestBuilder
                .url(url)
                .headers(headers)
                .post(body)
                .build();
        // 将 Request 封装为 Call
        Call call = okHttpClient.newCall(request);
        // 执行 Call，得到 Response
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("【post】请求发生异常:{}", e);
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * POST 请求，异步方式，提交数据
     *
     * @param url        请求地址
     * @param bodyParams 请求参数
     * @param callBack   回调函数
     */
    public void postDataAsync(String url, Map<String, String> bodyParams, final Callback callBack) {
        // 构造 RequestBody
        RequestBody body = setRequestBody(bodyParams);
        // 构造 Request
        buildRequest(url, callBack, body);
    }

    /**
     * 同步 POST 请求，使用 JSON 格式作为参数
     *
     * @param url  请求地址
     * @param json JSON 格式参数
     * @return 响应结果
     * @throws IOException 异常
     */
    public String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 同步 POST 请求，使用 JSON 格式作为参数
     *
     * @param url  请求地址
     * @param json JSON 格式参数
     * @return 响应结果
     * @throws IOException 异常
     */
    public String postJson(String url, Map<String, String> headerParams, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);

        //自定义header 参数
        Headers headers = Headers.of(headerParams);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 异步 POST 请求，使用 JSON 格式作为参数
     *
     * @param url      请求地址
     * @param json     JSON 格式参数
     * @param callBack 回调函数
     * @throws IOException 异常
     */
    public void postJsonAsync(String url, String json, final Callback callBack) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        // 构造 Request
        buildRequest(url, callBack, body);
    }


    /**
     * 同步 POST 请求，使用 File参数
     *
     * @param url          请求地址
     * @param headerParams
     * @param file         File 文件
     * @return 响应结果
     */
    public String uploadMultiFile(String url, Map<String, String> headerParams, File file) throws IOException {

        String result = null;


        //自定义header 参数
        Headers headers = Headers.of(headerParams);

        RequestBody requestBody = RequestBody.create(FORM, file);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .headers(headers)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            result = response.body().string();
        } else {
            if (response != null) {
                response.body().close();
            }
            throw new IOException("Unexpected code " + response);
        }

        return result;
    }

    /**
     * 分片上传
     *
     * @param url
     * @param headerParams
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadChunkFile(String url, Map<String, String> headerParams, File file) throws IOException {

        String result = null;

        String fileName = file.getName();
        String fileExt = FileUtils.getFileSuffix(file);
        Long fileCrc32 = FileUtils.checksumCRC32(file);
        String fileMd5 = MessageDigestUtils.digest(file);
        long fileSize = Files.size(file.toPath());
        int totalChunks = (int) ((fileSize + CHUNK_SIZE - 1) / CHUNK_SIZE); // 计算总分片数

        log.info("总共分为【{}】个分片", totalChunks);
        for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
            log.info("当前第【{}】个分片", chunkIndex + 1);
            byte[] buffer = readChunk(file, chunkIndex, CHUNK_SIZE);
            log.info("启动上传");
            result = uploadChunk(url, headerParams, fileName, fileExt, fileCrc32, fileMd5,
                    chunkIndex, totalChunks, buffer);
            log.info("上传完成，查找下一分片");
        }
        log.info("整体上传结束，返回最终结果【{}】", result);
        return result;
    }

    private String uploadChunk(String url, Map<String, String> headerParams, String fileName, String fileExt, Long fileCrc32,
                               String fileMd5, int chunkIndex, int totalChunks, byte[] chunkData) throws IOException {


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "chunk-" + chunkIndex + "." + fileExt,
                        RequestBody.create(MediaType.parse("application/octet-stream"), chunkData))
                .addFormDataPart("file_name", fileName)
                .addFormDataPart("file_md5", fileMd5)
                .addFormDataPart("file_crc32", String.valueOf(fileCrc32))
                .addFormDataPart("file_ext", fileExt)
                .addFormDataPart("chunk", String.valueOf(chunkIndex))
                .addFormDataPart("chunks", String.valueOf(totalChunks))
                .build();

        //自定义header 参数
        Headers headers = Headers.of(headerParams);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                if (response != null) {
                    response.body().close();
                }
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private byte[] readChunk(File file, int chunkIndex, int chunkSize) throws IOException {
        log.info("开始读取第【{}】个分片", chunkIndex);
        long startByte = chunkIndex * chunkSize;
        long endByte = Math.min(startByte + chunkSize, file.length());

        // 确保 startByte 不超过文件长度
        if (startByte >= file.length()) {
            log.warn("分片索引超出文件长度，跳过分片【{}】", chunkIndex);
            return new byte[0];
        }

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            // 跳过 startByte 字节
            long skipped = bis.skip(startByte);
            if (skipped < startByte) {
                log.error("未能成功跳过足够的字节数，期望跳过【{}】，实际跳过【{}】", startByte, skipped);
                throw new IOException("Failed to skip the expected number of bytes");
            }

            // 读取指定数量的字节
            int length = (int) (endByte - startByte);
            byte[] buffer = new byte[length];
            int bytesRead = 0;
            while (bytesRead < length) {
                int result = bis.read(buffer, bytesRead, length - bytesRead);
                if (result == -1) {
                    break; // 文件结束
                }
                bytesRead += result;
            }

            if (bytesRead != length) {
                log.warn("未能读取完整的分片数据，期望读取【{}】字节，实际读取【{}】字节", length, bytesRead);
            } else {
                log.info("成功读取分片【{}】的数据", chunkIndex);
            }

            return buffer;
        }
    }

    /**
     * 下载网络文件
     *
     * @param url
     * @param outputFile
     */
    public void downloadFile(String url, String outputFile) {
        long startTime = System.currentTimeMillis(); // 记录开始时间

        // 构造 Request
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        try (Response response = call.execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.code());

            // Ensure the parent directory exists
            Path outputFilePath = Paths.get(outputFile);
            Files.createDirectories(outputFilePath.getParent());

            // Start downloading
            log.info("开始下载文件: " + url);
            try (InputStream in = response.body().byteStream();
                 FileOutputStream out = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[4096];
                int read;
                //long totalBytesRead = 0;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                    //totalBytesRead += read;
                    //log.info("已下载字节数: " + totalBytesRead);
                }
                log.info("文件下载完成: " + outputFile);
            }
        } catch (IOException e) {
            log.info("文件下载失败: ", e);
            throw new RuntimeException(e);
        } finally {
            long endTime = System.currentTimeMillis(); // 记录结束时间
            long duration = (endTime - startTime) / 1000; // 耗时（秒）
            log.info("整个下载过程耗时：" + duration + " 秒");
        }
    }


    public void downloadChunkFile(String url, String outputFile) throws IOException {
        long startTime = System.currentTimeMillis(); // 记录开始时间

        try {
            long fileSize = getFileSize(url);
            log.info("文件大小：【" + fileSize + "】字节");
            int totalChunks = (int) ((fileSize + CHUNK_SIZE - 1) / CHUNK_SIZE); // 计算总分片数
            log.info("计算总分片数:【" + totalChunks + "】");

            // 创建临时目录用于存放分片
            File tempDir = Files.createTempDirectory("chunks").toFile();
            tempDir.deleteOnExit();
            log.info("创建临时目录用于存放分片: " + tempDir.getAbsolutePath());

            ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                final int index = chunkIndex;
                executor.submit(() -> {
                    try {
                        downloadChunk(url, fileSize, index, CHUNK_SIZE, tempDir);
                    } catch (IOException e) {
                        log.error("下载第【" + index + "】个分片失败：" + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }

            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                    executor.shutdownNow();
                    log.warn("部分分片下载未完成，强制终止线程池");
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                log.error("线程池等待过程中被中断：" + e.getMessage());
            }

            // 合并分片
            mergeChunks(tempDir, outputFile);
            // 清理临时文件
            deleteDirectory(tempDir);

            long endTime = System.currentTimeMillis(); // 记录结束时间
            long duration = (endTime - startTime) / 1000; // 耗时（秒）
            log.info("整个下载过程耗时：" + duration + " 秒");
        } catch (IOException e) {
            throw e;
        }
    }

    public long getFileSize(String url) {

        if (url == null || url.isEmpty()) {
            log.error("URL不能为空");
            return 0L;
        }

        Request request = new Request.Builder().url(url).head().build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String contentLengthHeader = response.header("Content-Length");
                if (contentLengthHeader != null) {
                    try {
                        return Long.parseLong(contentLengthHeader);
                    } catch (NumberFormatException e) {
                        log.error("无法解析Content-Length头: {}", contentLengthHeader, e);
                    }
                } else {
                    log.warn("响应中未找到Content-Length头");
                }
            } else {
                log.error("请求失败，状态码: {}", response.code());
            }
        } catch (IOException e) {
            log.error("无法获取网络文件大小", e);
        } catch (Exception e) {
            log.error("发生未知错误", e);
        }
        return 0L; // 如果有任何问题，返回0表示未能成功获取文件大小
    }

    private void downloadChunk(String url, long fileSize, int chunkIndex, int chunkSize, File tempDir) throws IOException {
        log.info("执行下载第【" + chunkIndex + "】个分片");
        long startByte = chunkIndex * chunkSize;
        long endByte = Math.min(startByte + chunkSize - 1, fileSize - 1);

        Request request = new Request.Builder()
                .url(url)
                .header("Range", "bytes=" + startByte + "-" + endByte)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response.code());

            Path outputPath = Paths.get(tempDir.getAbsolutePath(), "chunk-" + chunkIndex);
            Files.copy(response.body().byteStream(), outputPath);
            log.info("第【" + chunkIndex + "】个分片下载完成");
        }
    }

    private void mergeChunks(File tempDir, String outputFile) throws IOException {
        log.info("开始合并分片至完整文件");
        try (BufferedSink sink = Okio.buffer(Okio.sink(new File(outputFile)))) {
            for (int i = 0; ; i++) {
                File chunkFile = new File(tempDir, "chunk-" + i);
                if (!chunkFile.exists()) break;

                try (BufferedSource source = Okio.buffer(Okio.source(chunkFile))) {
                    sink.writeAll(source);
                    log.info("合并分片【" + i + "】");
                }
            }
        }
        log.info("所有分片合并完成");
    }

    private void deleteDirectory(File directory) {
        log.info("开始清除临时目录");
        File[] entries = directory.listFiles();
        if (entries != null) {
            for (File entry : entries) {
                if (entry.delete()) {
                    log.info("删除临时文件【" + entry.getAbsolutePath() + "】成功");
                } else {
                    log.warn("删除临时文件【" + entry.getAbsolutePath() + "】失败");
                }
            }
        }
        if (directory.delete()) {
            log.info("临时目录【" + directory.getAbsolutePath() + "】删除成功");
        } else {
            log.warn("临时目录【" + directory.getAbsolutePath() + "】删除失败");
        }
    }

    /**
     * 构造 POST 请求参数
     *
     * @param params 请求参数
     * @return {@link RequestBody}
     */
    private RequestBody setRequestBody(Map<String, String> params) {
        RequestBody body;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (!CollectionUtils.isEmpty(params)) {
            Iterator<String> iterator = params.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                formEncodingBuilder.add(key, params.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }

    /**
     * 构造 请求头参数
     *
     * @param params 请求头参数
     * @return
     */
    private Headers setRequestHeaders(Map<String, String> params) {
        Headers headers;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (!CollectionUtils.isEmpty(params)) {
            Iterator<String> iterator = params.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                headersbuilder.add(key, params.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }

    /**
     * 构造 Request 发起异步请求
     *
     * @param url      请求地址
     * @param callBack 回调函数
     * @param body     {@link RequestBody}
     */
    private void buildRequest(String url, Callback callBack, RequestBody body) {
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        // 将 Request 封装为 Call
        Call call = okHttpClient.newCall(request);
        // 执行 Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onResponse(call, response);
            }
        });
    }


    public static void main(String[] args) {
        Map sigMap = Maps.newHashMap();
        sigMap.put("api_version", "1.0");
        sigMap.put("app_id", "10000");
        sigMap.put("app_key", "111111");
//        sigMap.put("device_id","111");
//        sigMap.put("device_type","ios");
//        sigMap.put("platform","ios");
//        sigMap.put("app_version","1.1.0");
        sigMap.put("timestamp", System.currentTimeMillis() + "");
        sigMap.put("private_key", "Macheal");
//
        sigMap.put("signature", SignUtils.sign(sigMap));

        OkHttp3ClientUtils clientUtils = OkHttp3ClientUtils.getInstance();
        /*String s = null;
        try {
            s = clientUtils.uploadMultiFile("https://api.eyhhzzx.com/file/file/upload", sigMap, new File("/Users/laughtiger/Downloads/方世玉.mkv"));
            //s = clientUtils.uploadChunkFile("https://api.eyhhzzx.com/file/file/chunk_upload", sigMap, new File("/Users/laughtiger/Downloads/方世玉.mkv"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(s);*/

        //HashMap<String, String> headerParams = Maps.newHashMap();
        //headerParams.put("Authorization", "Token vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        //
        /*String s = null;
        s = clientUtils.getData("https://api.vidu.zone/ent/v1/tasks/2483736620333299/creations", headerParams);
        */

        try {
            /*long startTime = System.currentTimeMillis(); // 记录开始时间
            FileUtils.copyURLToFile(new URL("https://tcss.mothpro.cn/video/2024-12-13120254_9199.mp4"),new File("/Users/laughtiger/Documents/memorial.mp4"));
            long endTime = System.currentTimeMillis(); // 记录结束时间
            long duration = (endTime - startTime) / 1000; // 耗时（秒）
            log.info("整个下载过程耗时：" + duration + " 秒");*/

            //372 秒
            //clientUtils.downloadFile("https://tcss.mothpro.cn/video/2024-12-13120254_9199.mp4","/Users/laughtiger/Documents/memorial.mp4");
            //377
            //clientUtils.downloadChunkFile("https://tcss.mothpro.cn/video/2024-12-13120254_9199.mp4", "/Users/laughtiger/Documents/memorial.mp4");
            /*long fileSize = clientUtils.getFileSize("https://prod-ss-vidu.s3.cn-northwest-1.amazonaws.com.cn/infer/tasks/24/1215/03/2562375016464380/creation-01/video.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIARRHG6JR7EMNHVUWT%2F20241215%2Fcn-northwest-1%2Fs3%2Faws4_request&X-Amz-Date=20241215T033511Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&response-cache-control=max-age%3D86400&response-content-disposition=attachment%3Bfilename%3Dhug--4-2024-12-15T03%253A03%253A14Z.mp4&x-id=GetObject&X-Amz-Signature=3b0237ab496d7ab61fb481f7bbd11b1bc948860984fe83754c473665b24a6bf7");
            log.info(fileSize);*/
            //
            //long l = FileUtils.getFileSize("/Users/laughtiger/Downloads/复刻.mp3");
            //log.info(l);

            //clientUtils.downloadFile("https://prod-ss-vidu.s3.cn-northwest-1.amazonaws.com.cn/infer/tasks/24/1215/03/2562375016464380/creation-01/video.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIARRHG6JR7EMNHVUWT%2F20241215%2Fcn-northwest-1%2Fs3%2Faws4_request&X-Amz-Date=20241215T033511Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&response-cache-control=max-age%3D86400&response-content-disposition=attachment%3Bfilename%3Dhug--4-2024-12-15T03%253A03%253A14Z.mp4&x-id=GetObject&X-Amz-Signature=3b0237ab496d7ab61fb481f7bbd11b1bc948860984fe83754c473665b24a6bf7","/Users/laughtiger/Documents/memorial.mp4");



            HashMap<Object, Object> map = Maps.newHashMap();
            map.put("mobile","18610880038");
            String postJson = clientUtils.postJson("http://39.104.14.227:9010/me/silent", JsonUtils.toJson(map));

            log.info(postJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
