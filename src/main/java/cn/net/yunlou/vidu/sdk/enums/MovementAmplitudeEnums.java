package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 运动幅度
 */
@Getter
@AllArgsConstructor
public enum MovementAmplitudeEnums implements IEnum<String> {
    large("large","large"),
    medium("medium","medium"),
    small("small","small"),
    auto("auto","auto");

    private final String value;

    private final String label;
}
