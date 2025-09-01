package cn.net.yunlou.vidu.sdk;

import cn.net.yunlou.vidu.sdk.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *  template2video
 *     "template": "hugging",
 *     "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/scene-template/hug.jpeg"],
 *     "prompt": "Video content\\n画面中的两个主体转向彼此，并开始拥抱# 要求\\n将Motion Level设置为‘Large’",
 *     "seed": 0
 */


@Data
public class Template2VideoViduRequest extends ViduRequest<Template2VideoViduRequest>{

    /**
     * 场景模版参数
     * @see TemplateEnums
     */
    private String template;


    /**
     * 图片
     *
     */
    private List<String> images;

    /**
     * 文本提示词
     * 生成视频的文本描述。
     * 注：字符长度不能超过 1500 个字符
     */
    private String prompt;

    /**
     * 随机种子
     * 当默认不传或者传0时，会使用随机数替代
     * 手动设置则使用设置的种子
     */
    private Integer seed;

    /**
     * 比例
     * 默认 16:9，可选值如下：16:9、9:16、1:1
     * @see AspectRatioEnums
     */
    @JsonProperty("aspect_ratio")
    private String aspectRatio;

    /**
     * 异域公主特效专属参数
     * 仅当 template 传 exotic_princess 时可用，可选值如下：
     * 默认：auto, 可选：denmark,uk,africa,china,mexico,switzerland,russia,italy,korea,thailand,india,japan
     * @see AreaEnums
     */
    private String area;

    /**
     * 与兽同行特效专属参数
     * 仅当 template 传 beast_companion 时可用，可选值如下：
     * 默认 auto,可选：bear,tiger,elk,snake,lion,wolf
     */
    private String beast;

    /**
     * 是否为生成的视频添加背景音乐。
     * 默认：false，可选值 true 、false
     * - 传 true 时系统将从预设 BGM 库中自动挑选合适的音乐并添加；不传或为 false 则不添加 BGM。
     * - BGM不限制时长，系统根据视频时长自动适配
     */
    private Boolean bgm;

    /**
     * 透传参数
     * 不做任何处理，仅数据传输
     * 注：最多 1048576个字符
     */
    private String payload;

    /**
     * 是否添加水印
     * - true：添加水印；
     * - false：不添加水印；
     * 注：目前水印内容为固定，内容由AI生成，默认不加
     */
    private Boolean watermark;

    /**
     * Callback 协议
     * 需要您在创建任务时主动设置 callback_url，请求方法为 POST，当视频生成任务有状态变化时，Vidu 将向此地址发送包含任务最新状态的回调请求。回调请求内容结构与查询任务API的返回体一致
     */
    @JsonProperty(value = "callback_url")
    private String callbackUrl;
}
