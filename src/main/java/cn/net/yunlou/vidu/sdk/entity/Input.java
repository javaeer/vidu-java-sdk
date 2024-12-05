package cn.net.yunlou.vidu.sdk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 */
@Data
public class Input implements Serializable {

    /**
     * 生成物id。 如果提交upscale任务时则必填，提交其他任务不用填
     */
    @JsonProperty("creation_id")
    private String creationId;

    /**
     * 随机种子，不传或者传0则使用随机数替代
     */
    private Long seed;

    /**
     * 是否使用提示词增强功能
     * 默认值为true，启用提示词增强功能
     */
    private Boolean enhance;

    /**
     * 是否开启多主体参考
     */
    @JsonProperty(value = "multi_image_boost")
    private Boolean multiImageBoost;

    /**
     * 提示词
     */
    private List<Prompt> prompts;

}
