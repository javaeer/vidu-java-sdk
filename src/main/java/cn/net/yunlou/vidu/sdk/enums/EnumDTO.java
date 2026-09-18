package cn.net.yunlou.vidu.sdk.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EnumDTO implements Serializable {
    private Object value;
    private String label;
}