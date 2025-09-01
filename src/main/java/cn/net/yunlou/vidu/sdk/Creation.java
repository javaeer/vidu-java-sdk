package cn.net.yunlou.vidu.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * "id": "your_creations_id",
 * "url": "your_generated_results_url",
 * "cover_url": "your_generated_results_cover_url"
 */
@Data
public class Creation implements Serializable {

    private Long id;

    private String url;

    @JsonProperty("cover_url")
    private String coverUrl;
}