package cn.net.yunlou.vidu.sdk;

import cn.net.yunlou.vidu.sdk.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *  text2video
 *     "model": "viduq1",
 *     "style": "general",
 *     "prompt": "In an ultra-realistic fashion photography style featuring light blue and pale amber tones, an astronaut in a spacesuit walks through the fog. The background consists of enchanting white and golden lights, creating a minimalist still life and an impressive panoramic scene.",
 *     "duration": 5,
 *     "seed": 0,
 *     "aspect_ratio": "16:9",
 *     "resolution": "1080p",
 *     "movement_amplitude": "auto",
 *     "off_peak": false
 */


@Data
public class Text2VideoViduRequest extends ViduRequest<Text2VideoViduRequest>{

    /**
     * 模型名称
     * 可选值：viduq1 、vidu1.5
     * - viduq1：画面清晰，平滑转场，运镜稳定
     * - vidu1.5：动态幅度大
     * @see ModelEnums
     */
    private String model;


    /**
     * 风格
     * 默认 general，可选值：general、anime
     * general：通用风格，可以通过提示词来控制风格
     * anime：动漫风格，仅在动漫风格表现突出，可以通过不同的动漫风格提示词来控制
     *
     * @see StyleEnums
     *
     */
    private String style;

    /**
     * 文本提示词
     * 生成视频的文本描述。
     * 注：字符长度不能超过 1500 个字符
     */
    private String prompt;

    /**
     * 视频时长参数，默认值依据模型而定：
     * - viduq1 : 默认5秒，可选：5
     * - vidu1.5 : 默认4秒，可选：4、8
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
     * - viduq1 5秒：默认 1080p，可选：1080p
     * - vidu1.5 4秒：默认 360p，可选：360p、720p、1080p
     * - vidu1.5 8秒：默认 720p，可选：720p
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
