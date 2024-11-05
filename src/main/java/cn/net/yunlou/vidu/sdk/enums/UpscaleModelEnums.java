package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模型类型
 upscale任务可选值
 •	stable
 */

@AllArgsConstructor
public enum UpscaleModelEnums implements IEnum<String> {
    STABLE("stable","稳定版");

    @Getter
    private String value;

    @Getter
    private String label;
}
