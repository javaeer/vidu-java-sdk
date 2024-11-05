package cn.net.yunlou.vidu.sdk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 生成物结果
 */
@Data
public class Creation implements Serializable {

    /**
     * 生成物id，用来标识不同的生成物
     */
    private String id;
    /**
     * 生成物URL， 一个小时有效期
     */
    private String url;
    /**
     * 生成物封面，一个小时有效期
     */
    @JsonProperty("cover_url")
    private String coverUrl;


    private Video video;

}
