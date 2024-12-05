package cn.net.yunlou.vidu.sdk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 */
@Data
public class Task implements Serializable {

    private Long id;

    /**
     * 任务类型
     * 可选值
     * • text2video
     * • img2video
     * • character2video
     * • headtailimg2video
     * • upscale
     */
    private String type;

    /**
     *
     */
    private String state;

    /**
     * 模型类型 （v1.5版本后废弃）
     * text2video，img2video，character2video任务可选值
     * • vidu-high-performance
     * upscale任务可选值
     * • stable
     */
    private String model;

    /**
     * 模型版本
     * • 1.0
     * • 1.5
     */
    @JsonProperty("model_version")
    private String modelVersion;

    /**
     * 风格 只有text2video任务需要填写
     * • general
     * • anime
     */
    private String style;

    /**
     * 是否打开审核
     * 默认值为 true， 打开审核
     */
    private boolean moderation;

    private Input input;

    @JsonProperty("output_params")
    private Output outputParams;

    @JsonProperty("err_code")
    private String errCode;

    @JsonProperty("creations_count")
    private Integer creationsCount;

    @JsonProperty("created_at")
    private String createdAt;

}
