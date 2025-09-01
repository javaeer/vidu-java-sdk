package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FileName: ResolutionEnums
 * Description:
 * Created By laughtiger
 * Created At 2025/8/25 12:02
 * Modified By
 * Modified At
 */
@AllArgsConstructor
@Getter
public enum ResolutionEnums implements IEnum<String> {
    //UHD("4K","超清"),
    FHD("1080p","全高清"),
    HD("720p","高清"),
    SD("360p","标清");

    private final String value;

    private final String label;
}
