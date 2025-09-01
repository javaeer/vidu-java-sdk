package cn.net.yunlou.vidu.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 "task_id": "your_task_id_here",
 "state": "created",
 "model": "viduq1",
 "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/image2video.png"],
 "prompt": "The astronaut waved and the camera moved up.",
 "duration": 5,
 "seed": random_number,
 "resolution": "1080p",
 "movement_amplitude": "auto",
 "payload":"",
 "off_peak": false,
 "credits":credits_number,
 "created_at": "2025-01-01T15:41:31.968916Z"
 */
@Data
public class ViduResponse {

    /**
     * 任务ID，系统生成
     */
    @JsonProperty("id")
    private String taskId;
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

    /**
     * 生成物
     */
    private List<Creation> creations;

}
