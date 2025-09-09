package cn.net.yunlou.vidu.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
* FileName: ViduTaskResponse
* Description: 
* Created By laughtiger
* Created At 2025/9/8 18:47
* Modified By 
* Modified At 
*/
@Data
public class ViduCatchResponse extends ViduResponse {

    /**
     * 任务ID，系统生成
     */
    @JsonProperty("id")
    private String id;

    /**
     * 生成物
     */
    private List<Creation> creations;
}
