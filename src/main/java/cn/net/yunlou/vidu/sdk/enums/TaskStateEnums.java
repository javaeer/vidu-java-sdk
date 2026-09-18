package cn.net.yunlou.vidu.sdk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FileName: TaskStateEnums
 * Description:
 * Created By laughtiger
 * Created At 2025/9/8 17:22
 * Modified By
 * Modified At
 * 可选值：
 * created 创建成功
 * queueing 任务排队中
 * processing 任务处理中
 * success 任务成功
 * failed 任务失败
 */
@Getter
@AllArgsConstructor
public enum TaskStateEnums implements IEnum<String> {

    CREATED("created" ,"创建成功"),
    QUEUEING("queueing" ,"任务排队中"),
    PROCESSING("processing","任务处理中"),
    SUCCESS("success","任务成功"),
    FAILED("failed" ,"任务失败");

    public final String value;

    public final String label;
}
