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
public enum DurationEnums implements IEnum<Integer> {

    S8(8,"8秒"),
    S5(5,"5秒"),
    S4(4,"4秒");

    private final Integer value;

    private final String label;
}
