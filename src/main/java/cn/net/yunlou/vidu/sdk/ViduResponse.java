package cn.net.yunlou.vidu.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * FileName: CommonViduResponse
 * Description:
 * Created By laughtiger
 * Created At 2025/9/8 18:49
 * Modified By
 * Modified At
 */
@Data
public class ViduResponse {

    /**
     * 任务状态
     */
    private String state;

    @JsonProperty("err_code")
    private String errCode;
    /**
     * 提交任务的model
     */
    private String model;

    private List<String> images;

    private String prompt;
    /**
     * 提交任务的风格
     */
    private Integer duration;

    /**
     * 提交任务的input
     */
    private Integer seed;

    private String resolution;

    private Boolean bgm;
    /**
     * 提交任务的输出设置
     */
    @JsonProperty("movement_amplitude")
    private String movementAmplitude;

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("off_peak")
    private Boolean offPeak;

    private Integer credits;
    /**
     * 任务创建时间
     */
    @JsonProperty("created_at")
    private String createdAt;
}
