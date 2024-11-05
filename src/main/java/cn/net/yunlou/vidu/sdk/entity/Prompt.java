package cn.net.yunlou.vidu.sdk.entity;

import cn.net.yunlou.vidu.sdk.enums.PromptTypeEnums;
import java.io.Serializable;
import lombok.Data;

/**
 * 提示词
 */

@Data
public class Prompt implements Serializable {

    /**
     * 提示词类型
     * •	text
     * •	image
     */
    private String type = PromptTypeEnums.TEXT.getValue();// "text",
    /**
     * 当type为text时，content为文本内容
     * content 长度不能大于 1500个字符
     * 当text为image时，content为图像URL(需要确保URL公网可访问)
     * 图片格式仅支持 JPG,PNG。且图片大小需要小于50M， 图片长宽比需要小于1:4或者4:1
     * 当提交character2video任务时，图片尺寸不能小于 128*128，比例需要小于 1:16 或者 16:1
     */
    private String content;//generate five iphone",

    /**
     *
     */
    private Boolean negative;// false
}
