package cn.net.yunlou.vidu.sdk.enums;


import java.util.*;
import java.util.stream.Collectors;

public interface IHierarchicalEnum<T, E extends Enum<E> & IHierarchicalEnum<T, E>> extends IEnum<T> {

    // 静态工具方法
    static <E extends Enum<E> & IHierarchicalEnum<?, E>> List<E> getTopLevelEnums(Class<E> clazz) {
        return Collections.unmodifiableList(EnumSet.allOf(clazz).stream().filter(IHierarchicalEnum::isTopLevel).sorted(Comparator.comparing(IHierarchicalEnum::getSort)).collect(Collectors.toList()));
    }

    static <E extends Enum<E> & IHierarchicalEnum<?, E>> List<EnumTreeNode> getTree(Class<E> clazz) {
        return getTopLevelEnums(clazz).stream().map(IHierarchicalEnum::toTreeNode).collect(Collectors.toList());
    }

    // 核心方法
    E getParent();

    String getDescription();

    Integer getSort();

    // 默认方法实现
    default boolean isTopLevel() {
        return getParent() == null;
    }

    default boolean hasNextLevel() {
        // 优化为使用缓存/预计算（如需高性能可添加缓存逻辑）
        return !getChildren().isEmpty();
    }

    @SuppressWarnings("unchecked")
    default List<E> getChildren() {
        Class<E> enumClass = (Class<E>) this.getClass();
        return EnumSet.allOf(enumClass).stream().filter(item -> Objects.equals(item.getParent(), this)).sorted(Comparator.comparing(IHierarchicalEnum::getSort)).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @SuppressWarnings("unchecked")
    default List<E> getPath() {
        List<E> path = new LinkedList<>(); // 使用 LinkedList 提高头部插入性能
        E current = (E) this;
        Set<E> visited = Collections.newSetFromMap(new IdentityHashMap<>()); // 使用 IdentityHashMap 提高性能

        while (current != null) {
            if (!visited.add(current)) {
                throw new IllegalStateException("检测到循环引用: " + path);
            }
            path.add(0, current); // 插入到头部
            current = current.getParent();
        }

        return Collections.unmodifiableList(path);
    }

    default EnumTreeNode toTreeNode() {
        return new EnumTreeNode(this);
    }
}