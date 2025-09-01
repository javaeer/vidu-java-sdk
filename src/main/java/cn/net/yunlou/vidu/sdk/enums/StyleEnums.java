package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FileName: StyleEnums
 * Description:
 * Created By laughtiger
 * Created At 2025/8/25 12:02
 * Modified By
 * Modified At
 */
@AllArgsConstructor
@Getter
public enum StyleEnums implements IEnum<String> {
    ANIME("anime","动漫"),
    GENERAL("general","通用");

    private final String value;

    private final String label;
}
