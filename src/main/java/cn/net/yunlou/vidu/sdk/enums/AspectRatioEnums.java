package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FileName: DurationEnums
 * Description:
 * Created By laughtiger
 * Created At 2025/8/25 12:03
 * Modified By
 * Modified At
 */
@AllArgsConstructor
@Getter
public enum AspectRatioEnums implements IEnum<String> {

    R16_9("16:9","16:9"),
    R9_16("9:16","9:16"),
    R1_1("1:1","1:1");

    private final String  value;

    private final String label;
}
