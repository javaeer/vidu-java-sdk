package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* FileName: ModelEnums
* Description: 
* Created By laughtiger
* Created At 2025/8/25 12:00
* Modified By 
* Modified At 
*/

@Getter
@AllArgsConstructor
public enum ModelEnums implements IEnum<String> {

    VIDUQ1("viduq1", "Q1模型"),
    VIDUQ1_CLASSIC("viduq1-classic", "Q1传统模型"),
    VIDU2_0("vidu2.0", "2.0模型"),
    VIDU1_5("vidu1.5", "1.5模型");

    private final String value;

    private final String label;
}