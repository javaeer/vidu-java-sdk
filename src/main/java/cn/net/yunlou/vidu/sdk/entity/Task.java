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

    private String type;

    private String state;

    private String model;

    @JsonProperty("model_version")
    private String modelVersion;

    private String style;

    private boolean moderation;

    private Input input;

    private List<Prompt> prompts;

    @JsonProperty("output_params")
    private Output outputParams;

    @JsonProperty("err_code")
    private String errCode;

    @JsonProperty("creations_count")
    private Integer creationsCount;

    @JsonProperty("created_at")
    private String createdAt;

}
