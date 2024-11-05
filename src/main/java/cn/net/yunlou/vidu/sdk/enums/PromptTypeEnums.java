package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 提示词类型
 * •	text
 * •	image
 */
@AllArgsConstructor
public enum PromptTypeEnums implements IEnum<String> {
    VOICE("voice","音频提示词"),
    IMAGE("image","图片提示词"),
    TEXT("text","文本提示词");

    @Getter
    private String value;

    @Getter
    private String label;
}
