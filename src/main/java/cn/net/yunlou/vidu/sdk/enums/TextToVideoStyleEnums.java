package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 风格
 只有text2video任务需要填写
 •	general
 •	anime
 */

@AllArgsConstructor
public enum TextToVideoStyleEnums implements IEnum<String> {
    GENERAL("general","一般"),
    ANIME("anime","动漫");

    @Getter
    private String value;

    @Getter
    private String label;
}
