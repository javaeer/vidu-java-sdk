package cn.net.yunlou.vidu.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * "task_id": "your_task_id_here",
 * "state": "created",
 * "model": "viduq1",
 * "images": ["https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/image2video.png"],
 * "prompt": "The astronaut waved and the camera moved up.",
 * "duration": 5,
 * "seed": random_number,
 * "resolution": "1080p",
 * "movement_amplitude": "auto",
 * "payload":"",
 * "off_peak": false,
 * "credits":credits_number,
 * "created_at": "2025-01-01T15:41:31.968916Z"
 */
@Data
public class ViduCreateResponse extends ViduResponse {

    /**
     * 任务ID，系统生成
     */
    @JsonProperty("task_id")
    private String taskId;

}
