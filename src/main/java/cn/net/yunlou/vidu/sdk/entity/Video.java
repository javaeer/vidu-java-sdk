package cn.net.yunlou.vidu.sdk.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * "video":{
 *  * "duration":7.85,
 *  * "fps":16,
 *  * "resolution":{"width":688, "height":384}
 *  * }
 */

@Data
public class Video implements Serializable {

    private Double duration;

    private Integer fps;

    private Resolution resolution;

}
