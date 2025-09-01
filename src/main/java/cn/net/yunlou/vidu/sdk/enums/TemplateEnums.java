package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IHierarchicalEnum;
import cn.net.yunlou.common.utils.JsonUtils;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public enum TemplateEnums implements IHierarchicalEnum<String, TemplateEnums> {

    HUG("hug", "拥抱", null, null, 1),
    HUGGING("hugging", "拥抱", HUG, "视频内容\\n画面中的两个主体转向彼此，并开始拥抱。\\n# 要求\\n将Motion Level设置为“Large”", 1),
    HUGGING_PRO("hugging_pro", "拥抱Pro", HUG, "Video content\\n 画面中的主体开始转向彼此，并开始拥抱。\\n# 要求\\n将Motion Level设置为“Large”", 2),
    COUPLE_ARRIVAL("couple_arrival", "情侣拥抱", HUG, "视频内容\\n 主体站在画面中央，正面朝向镜头。主体的伴侣人从画面右侧以放松的步伐逐渐走入画面，接近站在中心的人，然后相互拥抱，动作充满温暖和爱意。拥抱完成后，两人转向镜头，正脸朝向镜头，展现出轻松与友好的氛围。主体的伴侣形象为：{user_input}\\n# 要求 1.如果没有特别说明，针对主体伴侣，主体伴侣跟主体的种族、年龄、肤色应该是匹配的。主体伴侣的性别一般是跟主体是相反的。2.主体伴侣的数量跟主体的数量应该是一致的，如果图片中是一个主体，那么主体伴侣应该也是一个。3.拥抱完成后，主体伴侣跟主体一样正脸朝向镜头。", 3),

    KISS("kiss", "亲吻", null, null, 2),
    KISSING("kissing", "亲吻", KISS, "视频内容\\n画面中的两个主体开始转向彼此，开始接吻，画面呈现充满爱意的氛围。\\n# 要求\\n1.亲吻时候，嘴唇的动作要彼此贴合。\\n2.Motion Level ：Large.", 1),
    KISSING_PRO("kissing_pro", "亲吻Pro", KISS, "Video content\\n 画面中的两个主体开始转向彼此，开始接吻，画面呈现充满爱意的氛围。\\nRequirements:\\n1. 亲吻时候，嘴唇的动作要彼此贴合 \\n2.Motion Level ：Large。", 2),
    CHEEK_KISS("cheek_kiss", "亲吻脸颊", KISS, "In [scene/background], [character 1], [character 2] (and [character 3], if present) are [standing/sitting] together, with a [warm/relaxed/shy] mood. \\n- Two characters: Left kisses right on the cheek; right faces the camera, blushing with a shy smile. \\n- Three characters: Left and right kiss the middle on both cheeks; middle faces forward, blushing, nervously smiling. \\nRequirements:\\1.Replace all [ ] with actual details based on the image.\\n2. Motion Level：Large", 3),
    FRENCH_KISS("french_kiss", "法式热吻", KISS, "The two figures in the painting move closer and then passionately kiss, alternating with deep and firm intensity.", 4),
    FRENCH_KISS_8S("french_kiss_8s", "法式热吻8S", KISS, "The two figures in the painting move closer and then passionately kiss, alternating with deep and firm intensity.", 5),
    RAIN_KISS("rain_kiss", "雨中热吻", KISS,  "The picture depicts two people. The overall style is aesthetically pleasing and full of passion to highlight the fervor of the emotions. In the rain, the hair and clothes of the two people are completely soaked, and the water droplets on their clothes and hair are clearly visible. The two first look at each other affectionately, with love and longing filling their eyes, and then they kiss each other passionately.\\nWhen kissing, the lips should be in sync with each other.\\nThe movements are smooth and natural, without any stiff posture of pulling back and forth. The raindrops are normal and natural, and they don't splash around randomly. The camera captures their upper bodies and remains stationary.\\nSoft rain fell, soaking their clothes until the fabric clung to their skin. Water droplets trickled down, glistening under the dim light.The fabric gradually turned heavy and sodden, clinging to their bodies as it absorbed the moisture. Dark patches spread across the material, marking where the water seeped in deepest.\\nIt is prohibited to change the original appearance, clothing, and hairstyle of the characters.\\n It is prohibited to have black liquid appear", 6),
    BLOW_KISS("blow_kiss", "飞吻", KISS,  "Image Description:\n The image is a [close-up / wide shot / medium shot], featuring [number] subject(s), with the subject(s) [facing the camera / turned to the side].\nCamera Logic:\nIf the image is a wide or medium shot, the camera should rapidly push in from the beginning, ending with a close-up of the subject’s face.\nIf the original image is already a close-up, the video should begin directly with the subject’s action.\nIf the subject is turned to the side, they should naturally turn to face the camera before starting the performance.\nAction Sequence:\n The subject gently leans forward, blows a kiss toward the camera from just below the lips using the right hand, then naturally waves at the camera.\n Afterward, she unfolds a sweet and warm smile. The entire motion is smooth and soft, emphasizing approachability and emotional expressiveness.\nMulti-subject Adaptation:\n If there are multiple subjects in the image, describe the following:\n (All individuals should smile and face the camera, performing the kiss-blowing gesture in unison to maintain a naturally coordinated rhythm.)", 7),

    LIVE("live","老照片",null,null,3),
    LIVE_MEMORY("live_memory", "老照片动起来", LIVE, "Video content\\n 视频中的人物们，简单互动，微笑看着屏幕。\\n Requirements: \\n1.严格根据图片判断有多少个人物，精准简要的描述人物外观。\\n2.Motion Level ：Middle", 1),
    LIVE_MEMORY_360P("live_memory_360p", "老照片动起来-360P", LIVE, "People in the video interact briefly, smiling at the camera.\\n\\n#Requirements:\\n\\n1. Based solely on the image, state the exact number of people and give a precise, concise description of their appearance.\\n\\n2. Motion Level: Middle.", 2);



    @Getter
    private final String value;
    @Getter
    private final String label;
    @Getter
    private final TemplateEnums parent;
    @Getter
    private final String description;
    @Getter
    private final Integer sort;

    private transient List<TemplateEnums> childrenCache;


    // 显式定义构造函数（避免@AllArgsConstructor顺序问题）
    TemplateEnums(String value, String label, TemplateEnums parent,
                  String description, Integer sort) {
        this.value = value;
        this.label = label;
        this.parent = parent;
        this.description = description;
        this.sort = sort;
    }

    @Override
    public List<TemplateEnums> getChildren() {
        if (childrenCache == null) {
            childrenCache = Collections.unmodifiableList(
                    EnumSet.allOf(TemplateEnums.class).stream()
                            .filter(item -> this.equals(item.getParent()))
                            .sorted(Comparator.comparing(TemplateEnums::getSort))
                            .collect(Collectors.toList())
            );
        }
        return childrenCache;
    }


    public static void main(String[] args) {
        // 1. 验证顶级节点
        System.out.println("顶级节点:");
        IHierarchicalEnum.getTopLevelEnums(TemplateEnums.class)
                .forEach(e -> System.out.println(e.getLabel()));

        // 2. 验证子节点
        System.out.println("\nHUG的子节点:");
        TemplateEnums.HUG.getChildren()
                .forEach(e -> System.out.println(e.getLabel()));

        // 3. 验证树形结构
        System.out.println("\n完整树形结构:");
        System.out.println(JsonUtils.toJson(IHierarchicalEnum.getTree(TemplateEnums.class)));


        // 4. 验证是否有下级
        System.out.println("\n是否有下级:");
        System.out.println(TemplateEnums.HUG.hasNextLevel());
    }

}