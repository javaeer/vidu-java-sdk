package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务类型
 * •	text2video
 * •	img2video
 * •	character2video
 * •	upscale
 */

@AllArgsConstructor
public enum TaskTypeEnums implements IEnum<String> {
    UPSCALE("upscale","高级"),
    CHARACTER2VIDEO("character2video","人物生视频"),
    IMG2VIDEO("img2video","图生视频"),
    TEXT2VIDEO("text2video","文生视频");

    @Getter
    private String value;

    @Getter
    private String label;
}
