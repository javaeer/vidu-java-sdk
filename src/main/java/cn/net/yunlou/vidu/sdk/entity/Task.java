package cn.net.yunlou.vidu.sdk.entity;

import cn.net.yunlou.vidu.sdk.enums.TextToVideoStyleEnums;
import cn.net.yunlou.vidu.sdk.enums.ToVideoModelEnums;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
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

    private String style;

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
