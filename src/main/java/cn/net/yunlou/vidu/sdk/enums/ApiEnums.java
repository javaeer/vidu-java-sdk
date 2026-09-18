package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.vidu.sdk.constant.ViduConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FileName: ApiEnums
 * Description:
 * Created By laughtiger
 * Created At 2025/9/1 16:02
 * Modified By
 * Modified At
 */
@AllArgsConstructor
@Getter
public enum ApiEnums implements IEnum<String> {
    TEMPLATE_2_VIDEO("template2video", ViduConstant.API_TEMPLATE_TO_VIDEO),
    TEXT_2_VIDEO("text2video", ViduConstant.API_TEXT_TO_VIDEO),
    START_END_2_VIDEO("start-end2video", ViduConstant.API_START_END_TO_VIDEO),
    REFERENCE_2_VIDEO("reference2video", ViduConstant.API_REFERENCE_TO_VIDEO),
    IMG_2_VIDEO("img2video", ViduConstant.API_IMG_TO_VIDEO);

    private final String value;

    private final String label;
}
