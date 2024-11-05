package cn.net.yunlou.vidu.sdk;

import cn.net.yunlou.vidu.sdk.entity.Creation;
import cn.net.yunlou.vidu.sdk.entity.Input;
import cn.net.yunlou.vidu.sdk.entity.Output;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * id	string
 * type	string
 * state	string
 * model	string
 * style	string
 * moderation	bool
 * input	struct
 * output_params	struct
 */
@Data
public class ViduResponse {

    public static final String FAILED = "failed";
    public static final String SUCCESS = "success";
    public static final String PROCESSING = "processing";
    public static final String SCHEDULING = "scheduling";
    public static final String QUEUEING = "queueing";
    public static final String CREATED = "created";

    /**
     * 任务ID，系统生成
     */
    private String id;
    /**
     * 提交的任务类型
     */
    private String type;
    /**
     * 任务状态
     */
    private String state;
    /**
     * 提交任务的model
     */
    private String model;
    /**
     * 提交任务的风格
     */
    private String style;
    /**
     * 提交任务是否开启审核
     */
    private Boolean moderation;
    /**
     * 提交任务的input
     */
    private Input input;
    /**
     * 提交任务的输出设置
     */
    @JsonProperty("output_params")
    private Output outputParams;

    /**
     * 错误码，具体见错误码表
     */
    @JsonProperty("err_code")
    private String errCode;
    /**
     * 成功生成的生成物数量
     */
    @JsonProperty("creations_count")
    private Integer creationsCount;
    /**
     * 任务创建时间
     */
    @JsonProperty("created_at")
    private String createdAt;


    private List<Creation> creations;
}
