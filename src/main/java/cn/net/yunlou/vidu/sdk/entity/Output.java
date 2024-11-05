package cn.net.yunlou.vidu.sdk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 当提交text2video，img2video，character2video任务时为必填
 * 当提交upscale任务时为非必填
 */
@Data
public class Output implements Serializable {

    /**
     * 生成视频数量
     * 目前仅支持 1
     */
    @JsonProperty("sample_count")
    private Integer sampleCount = 1;

    /**
     * 视频时长
     * 目前支持 4,8
     */
    private Integer duration = 4;
}
