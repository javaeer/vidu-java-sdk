package cn.net.yunlou.vidu.sdk.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * "resolution":{"width":688, "height":384}
 */
@Data
public class Resolution implements Serializable {

    private Integer width;

    private Integer height;
}
