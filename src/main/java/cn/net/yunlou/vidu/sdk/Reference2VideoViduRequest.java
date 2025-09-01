package cn.net.yunlou.vidu.sdk;

import cn.net.yunlou.vidu.sdk.enums.AspectRatioEnums;
import cn.net.yunlou.vidu.sdk.enums.DurationEnums;
import cn.net.yunlou.vidu.sdk.enums.ModelEnums;
import cn.net.yunlou.vidu.sdk.enums.ResolutionEnums;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *  reference2video
 *  "model": "vidu2.0",
 *  "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/reference2video-1.png","https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/reference2video-2.png","https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/reference2video-3.png"],
 *  "prompt": "Santa Claus and the bear hug by the lakeside.",
 *  "duration": 4,
 *  "seed": 0,
 *  "aspect_ratio": "16:9",
 *  "resolution": "720p",
 *  "movement_amplitude": "auto",
 *  "off_peak": false
 */


@Data
public class Reference2VideoViduRequest extends ViduRequest<Reference2VideoViduRequest>{

    /**
     * 模型名称
     * 可选值：viduq1、vidu2.0、vidu1.5
     * - viduq1：画面清晰，平滑转场，运镜稳定
     * - vidu2.0：生成速度快
     * - vidu1.5：动态幅度大
     *
     * @see ModelEnums
     */
    private String model;


    /**
     *首帧图像
     * 模型将以此参数中传入的图片为首帧画面来生成视频。
     * 注1：支持传入图片 Base64 编码或图片URL（确保可访问）；
     * 注2：只支持输入 1 张图；
     * 注3：图片支持 png、jpeg、jpg、webp格式；
     * 注4：图片比例需要小于 1:4 或者 4:1 ；
     * 注5：图片大小不超过 50 MB；
     * 注6：请注意，base64 decode之后的字节长度需要小于10M，且编码必须包含适当的内容类型字符串
     */
    private List<String> images;

    /**
     * 文本提示词
     * 生成视频的文本描述。
     * 注：字符长度不能超过 1500 个字符
     */
    private String prompt;

    /**
     * 视频时长参数，默认值依据模型而定：
     * viduq1：默认5秒，可选：5
     * vidu2.0：默认4秒，可选：4
     * vidu1.5: 默认4秒，可选：4、8
     * @see DurationEnums
     */
    private Integer duration;

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
     * 分辨率参数，默认值依据模型和视频时长而定：
     * viduq1 （5秒）：默认 1080p, 可选：1080p
     * vidu2.0 （4秒）：默认 360p, 可选：360p、720p
     * vidu1.5（4秒）：默认 360p，可选：360p、720p、1080p
     * vidu1.5（8秒）：默认 720p，可选：720p
     * @see ResolutionEnums
     */
    private String resolution;

    /**
     * 运动幅度
     * 默认 auto，可选值：auto、small、medium、large
     * @see cn.net.yunlou.vidu.sdk.enums.MovementAmplitudeEnums
     */
    @JsonProperty(value = "movement_amplitude")
    private String movementAmplitude;

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
     * 错峰模式，默认为：false，可选值：
     * - true：错峰生成视频；
     * - false：即时生成视频；
     */
    @JsonProperty(value = "off_peak")
    private Boolean offPeak;

    /**
     * Callback 协议
     * 需要您在创建任务时主动设置 callback_url，请求方法为 POST，当视频生成任务有状态变化时，Vidu 将向此地址发送包含任务最新状态的回调请求。回调请求内容结构与查询任务API的返回体一致
     */
    @JsonProperty(value = "callback_url")
    private String callbackUrl;
}
