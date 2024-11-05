package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态
 * •	created       创建成功
 * •	queueing      排队中
 * •	scheduling    调度中
 * •	processing    处理中
 * •	success       生成成功
 * •	failed        生成失败
 */

@AllArgsConstructor
public enum TaskStateEnums implements IEnum<String> {
    FAILED("failed", "生成失败"),
    SUCCESS("success", "生成成功"),
    PROCESSING("processing", "处理中"),
    SCHEDULING("scheduling", "调度中"),
    QUEUEING("queueing", "排队中"),
    CREATED("created", "创建成功");

    @Getter
    private String value;

    @Getter
    private String label;
}
