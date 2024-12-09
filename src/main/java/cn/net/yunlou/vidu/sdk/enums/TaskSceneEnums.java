package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 场景类型
 *  可选值
 * • hug
 * • kiss
 * • christmas
 * • morphlab
 */

@AllArgsConstructor
public enum TaskSceneEnums implements IEnum<String> {
    HUG("hug", "拥抱"),
    KISS("kiss", "亲吻"),
    CHRISTMAS("christmas", "圣诞节"),
    MORPHLAB("morphlab", "变形特效");

    @Getter
    private String value;

    @Getter
    private String label;
}
