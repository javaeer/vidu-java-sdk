package cn.net.yunlou.vidu.sdk.enums;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EnumTreeNode implements Serializable {
    private final Object value;
    private final String label;
    private final String description;
    private final Integer sort;
    private final List<EnumTreeNode> children;

    public <T, E extends Enum<E> & IHierarchicalEnum<T, E>>
    EnumTreeNode(IHierarchicalEnum<T, E> source) {
        Objects.requireNonNull(source, "源枚举不能为null");

        this.value = source.getValue();
        this.label = source.getLabel();
        this.description = source.getDescription();
        this.sort = source.getSort();

        // 递归构建子节点（不可变列表）
        this.children = Collections.unmodifiableList(
            source.getChildren().stream()
                .map(IHierarchicalEnum::toTreeNode)
                .collect(Collectors.toList())
        );
    }

    // Getter 方法（省略setter保证不可变性）
    public Object getValue() { return value; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }
    public Integer getSort() { return sort; }
    public List<EnumTreeNode> getChildren() { return children; }

    @Override
    public String toString() {
        return "TreeNode{" +
            "label='" + label + '\'' +
            ", children=" + children.size() +
            '}';
    }
}