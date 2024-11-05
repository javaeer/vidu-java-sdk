package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 模型类型
 text2video，img2video，character2video任务可选值
 •	vidu-high-performance
 */

@AllArgsConstructor
public enum ToVideoModelEnums implements IEnum<String> {
    VIDU_HIGH_PERFORMANCE("vidu-high-performance","高级");

    @Getter
    private String value;

    @Getter
    private String label;
}
