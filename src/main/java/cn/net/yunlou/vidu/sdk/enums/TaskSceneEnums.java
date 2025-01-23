package cn.net.yunlou.vidu.sdk.enums;

import cn.net.yunlou.common.IHierarchicalEnum;
import cn.net.yunlou.common.utils.ObjectUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 场景类型
 * 可选值
 * • hug
 * • kiss
 * • christmas
 * • morphlab
 */

@AllArgsConstructor
public enum TaskSceneEnums implements IHierarchicalEnum<String, TaskSceneEnums> {


    //顶级
    NORMAL("normal", "默认", null, "", 1),
    HUG("hug", "拥抱", null, "拥抱", 2),
    KISS("kiss", "亲吻", null, "亲吻", 3),
    CHRISTMAS("christmas", "圣诞节", null, "", 4),
    MORPHLAB("morphlab", "变形特效", null, "", 5),
    LIVE_PHOTO("live_photo", "实况照片", null, "", 6),
    EMOTIONLAB("emotionlab", "情绪特效", null, "", 7),
    LIVE_MEMORY("live_memory", "老照片动起来", null, "视频内容\\n视频中的人物们，简单互动，微笑看着屏幕\\n# 要求\\n1.严格根据图片判断有多少个人物，精准简要的描述人物外观\\n2.Motion Level ：Small", 8),
    HAIR_SWAP("hair_swap", "AI换发", null, "", 9),
    INTERACTION("interaction", "双人互动", null, "", 10),
    LUNAR_NEWYEAR("lunar_newyear", "春节特效", null, "", 11),
    YOUTH_REWIND("youth_rewind", "童年回忆", null, "Video content\\ n  The subject(s) is/are facing the camera, appearing as their 3-year-old self, wearing a red cotton-padded jacket, holding burning sparklers in both hands, and joyfully dancing in the snowy courtyard. The sparklers flicker brightly, casting a warm glow on their happy, smiling faces. The subject(s) performs lively, natural movements, such as waving the sparklers while skipping around, always remaining in the center of the frame.  The scene is set in a snowy village courtyard during a festive winter evening, as shown in the provided background. Snow covers the ground and rooftops, while red lanterns hang overhead, glowing warmly in the golden sunlight of dusk. The wooden doors of nearby houses are adorned with festive decorations. The overall ambiance is filled with a nostalgic and joyful Chinese New Year atmosphere.  The warm orange tones from the sunlight and the red lanterns harmonize with the vibrant movements of the subject(s), enhancing the festive mood. The steady camera ensures the subject(s) stays at the center of the frame throughout the joyous performance.  \\ n #Requirements\\ 1. The subject(s) in the picture may be pets or one or more people. Generate according to the actual situation in the reference image.  2. The subject(s) should smile and immerse themselves in the festive atmosphere of Chinese New Year.  3. The subject(s) must always remain at the center of the frame, even while joyfully dancing. 4.The subject(s), including any pets, must wear red New Year cotton-padded jackets.", 12),
    DYNASTY_DRESS("dynasty_dress", "古风换装", null, "视频内容\\n一个角色穿着服装和鞋子面对镜头摆pose，眨眨眼睛，甜美微笑\\n# 要求 - 对角色的面部细节详细描述，对服装配饰的各种细节要尽量精准详细的描述.- 如果图片展示了[服装、手提包、配饰、帽子、鞋子]，需要出现在对人物的服装描述中.- 如果图片展示了背景图，则设定为背景并详细描述。- 只有1个角色！不要出现任何复数代词.- Shot Size 为：Wide Shot- 角色不要有任何手部动作、腰部动作,强调自然的微笑.", 13),
    LOVE_POSE("love_pose", "全家福比心", null, "视频内容\\n画中的角色面对镜头，抬起双手在胸前，做出比心的动作.\\n# 要求-根据图片判断人物数量，如果有多个人，每个人物都要做出比心的动作。", 14),
    WISH_SENDER("wish_sender", "财神特效", null, "", 15),

    // 圣诞特效子类
    BECOME_SANTA_CLAUS("become_santa_claus", "变身为圣诞老人", CHRISTMAS, "视频内容\\n画面中的人物从下前方轻轻举起鲜红的布，动作干净利落地将其从头覆盖到全身，然后随着人物将红布迅速向下扯下，人物变成了充满节日气息的圣诞老人。\\n# 要求\\n1.Style设定为:Realistic, HD\\n2.根据用户上传图片确定人物数量，不要出现[一群人]、[们]等代词而是精准地指出人物数量\\n3.如果用户上传图片有多个人物，则他们需要一起举起红布，所有人都变身为圣诞老人。不要出现图片中没有的人物", 401),
    SANTA_CLAUS_GIVE_GIFTS("santa_claus_give_gifts", "圣诞老人来送礼", CHRISTMAS, "视频内容\\n镜头中人物看向右边，然后一个圣诞老人满面笑容地从右边走入画面，手中捧着一个精美的礼物盒。他轻轻将礼物递给画面中的人物，动作自然且充满温暖。镜头聚焦在接到礼物的人物，人物神情中充满惊喜与感激。画面捕捉到人物的微表情和互动细节。\\n# 要求\\n1.Style设定为:Realistic, HD\\n2.根据用户上传图片确定人物数量，不要出现[一群人]、[们]等代词而是精准地指出人物数量\\n3.如果用户上传图片有多个人物，则他们需要一起举起红布，所有人都变身为圣诞老人。不要出现图片中没有的人物", 402),
    RAISE_TO_CHRISTMAS("raise_to_christmas", "圣诞节举杯庆祝", CHRISTMAS, "视频内容\\n画面人物手里拿起香槟酒杯，庆祝圣诞快乐，随着镜头拉远，画面出现圣诞树等圣诞节日物品\\n# 要求\\n1.Take a step-by-step approach in your response\\n2.以我的视频描述为第一要素，背景的描述统一、合理，不要描述两次.\\n3.根据用户上传图片确定人物数量，不要出现[一群人]、[们]等代词而是精准地指出人物数量\\n4.Motion Level 设定为：Middle\\n5.如果用户上传图片有多个人物，每个人都需要拿起香槟酒杯。不要出现图片中没有的人物", 403),
    HUG_SANTA_CLAUS("hug_santa_claus", "圣诞老人来拥抱", CHRISTMAS, "视频内容\\n镜头中人物看向画面外，接着一个圣诞老人满面笑容地从画面外走入画面，然后和人物拥抱，动作自然且充满温暖，镜头聚焦在一个温暖的拥抱，画面捕捉到人物的的微表情和互动细节\\n# 要求\\n1.根据人物的位置和状态合理的设计动作，而不是突兀的直接拥抱，要先描写人物转变到一个合适拥抱的姿势\\n2.Take a step-by-step approach in your response\\n3.根据用户上传图片确定人物数量，不要出现[一群人]、[们]等代词而是精准地指出人物数量\\n4.如果图片中有多个人物，则需要一起和圣诞老人拥抱。不要出现图片中没有的人物\\n5.强调只有1个圣诞老人", 403),


    // 变形特效子类
    DILATE("dilate", "膨胀", MORPHLAB, "视频内容\\n画面中主体开始膨胀变形，变得越来越大，越来越圆，就像一个气球，慢慢飘了起来\\n# 要求\\n1.根据用户上传图片确定主体数量,每个主体都要膨胀\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次.", 501),
    PINCH("pinch", "捏捏", MORPHLAB, "视频内容\\n画面开始，主体静止不动。随后，一双大手出现，将主体像橡皮泥般抓住并挤压。\\n随着主体逐渐缩小，被夹在手指间柔软变形\\n# 要求\\n1.根据用户上传图片确定主体数量,每个主体都要被捏扁\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次.", 502),
    BLOW_UP("blow_up", "爆炸", MORPHLAB, "视频内容\\n画面开始主体突然爆炸，细碎的颗粒爆炸开来\\n# 要求\\n1.根据用户上传图片确定主体数量,每个主体都要爆炸\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次.", 503),
    MELT("melt", "融化", MORPHLAB, "视频内容\\n画面中主体慢慢地开始融化，最终形成一个表面光滑的水坑\\n# 要求\\n1.根据用户上传图片确定主体数量,每个主体都要融化\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次.", 503),

    // 实况特效子类
    SWEET_SMILE("sweet_smile", "甜美微笑", LIVE_PHOTO, "主体面对镜头，露出了甜美动人的微笑", 601),
    WIND_MOVEMENT("wind_movement", "风动", LIVE_PHOTO, "主体头发丝被风朝着某一个方向微微吹动", 602),
    CAMERA_MOVEMENT("camera_movement", "镜头动", LIVE_PHOTO, "镜头缓缓拉近,风轻轻吹着主体", 603),
    WALK("walk", "走路", LIVE_PHOTO, "视频内容\\n画面主体，正对镜头走模特步\\n# 要求\\n1.根据img图片人物状态，设计合适的镜头运动，镜头应该慢慢转变为中景或者牛仔镜头【拉远或者推进】能够显露出人物的大半个身体\\n2.根据img图片人物状态，设计合理的动作转变，最终人物应当面对镜头走模特步\\n3.严格根据图片确定人物外观，不要出现图片没有的人物", 603),

    // 情感特效子类
    FEAR("fear", "恐惧", EMOTIONLAB, "视频内容\\n画面主体的表情开始慢慢变化，变得恐惧害怕，眼神里满是无助和惊慌，害怕的大声尖叫\\n# 要求\\n1.根据用户上传图片确定主体数量、人物性别、shot_size,每个主体都是同样的表情变化\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景和图片保持一致，不要变化，不要描述两次\\n4.镜头固定不要移动", 701),
    SMILE("smile", "微笑", EMOTIONLAB, "视频内容\\n画面主体的表情开始慢慢变化,表情逐渐放松，嘴角轻轻上扬，眼神变得柔和，微笑逐渐展现出温和与宁静的氛围。\\n# 要求\\n1.根据用户上传图片确定主体数量、人物性别、shot_size,每个主体都是同样的表情变化\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次\\n4.镜头固定不要移动\\n5.根据人物初始状态选择比较微小的微笑，还是比较开朗的微笑.不要过于夸张的微笑", 702),
    CRAZY_LAUGH("crazy_laugh", "狂笑", EMOTIONLAB, "视频内容\\n画面主体的表情开始慢慢变化,随着心情的放松，眉头慢慢舒展，嘴角开始上扬。然后他的笑容逐渐扩大，眼睛也变得更加明亮，仿佛看到了什么有趣的事物。最终，他大笑起来，嘴巴张得大大的，露出洁白的牙齿，动作夸张\\n# 要求\\n1.根据用户上传图片确定主体数量、人物性别、shot_size,每个主体都是同样的表情变化\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次\\n4.镜头固定不要移动,人物表情变化要自然", 703),
    AMAZED("amazed", "惊讶", EMOTIONLAB, "视频内容\\n图中的人物逐渐露出特别惊讶的表情，眼睛睁大，嘴巴微微张开，透出难以置信的神情。\\n# 要求\\n1.根据用户上传图片确定主体数量、人物性别、shot_size,每个主体都是同样的表情变化\\n2.Motion Level 设定为:Middle\\n3.以我的视频内容为第一要素，背景的描述统一、合理，不要描述两次\\n4.镜头固定不要移动,人物主要是表情变化，人物表情变化要自然", 704),

    // AI换发特效子类
    WOOL_ROLL("wool_roll", "羊毛卷", HAIR_SWAP, "视频内容\\n角色正对镜头不动，眨眨眼睛，头发缓慢转变为“{Curly Wool Hair}”发型\\n# 要求 角色静静地面对镜头，目光平和，眨眨眼睛，头发的变换成为画面的焦点，呈现出一场优雅的发型演化表演。", 901),
    DOUBLE_PONYTAIL("double_ponytail", "双马尾", HAIR_SWAP, "表现微笑情绪的特效", 902),
    LONG_HAIR("long_hair", "长发", HAIR_SWAP, "表现狂笑情绪的特效", 903),


    // 双人互动特效子类
    HEART_SIGN("heart_sign", "比心", INTERACTION, "图片中的两个人[简要精准的外观描述]面对镜头，各自伸出一只手，合在一起在胸前比了一个爱心", 1001),
    GIVE_ROSES("give_roses", "送玫瑰花", INTERACTION, "视频内容\\n镜头左边的人物[简要精准的外观描述、性别描述]拿起一只玫瑰花转身送给图片右边的人物[简要精准的外观描述]，右边的人物同样转身，面对面接受玫瑰花\\n# 要求1.Shot Size：镜头缓慢自然的变为Medium Shot；2.Camera Movement:根据图片初始镜头确定镜头是Zoom Out 还是Zoom In才能变为Medium Shot，如果初始镜头为近景则需要Zoom Out，如果初始就是Medium Shot则为None", 1002),


    // 新春特效子类
    FIREWORKS("fireworks", "2025新年烟花", LUNAR_NEWYEAR, "视频内容\\n视频展示人物手持仙女棒 面对镜头，人物身后绽放新年烟花 烟花上写2025 采用远景视角 动作自然且充满温暖，画面捕捉到人物的的微表情和互动细节  \\n# 要求1.根据人物的位置和状态合理的设计动作.2.Take a step-by-step approach in your response.3.根据用户上传图片确定人物数量，不要出现[一群人]、[们]等代词而是精准地指出人物数量.4.如果图片中有多个人物，则需要一起手持仙女棒。不要出现图片中没有的人物.5.如果图片展示了背景图，则设定为背景并详细描述.6.保证人物始终在画面中心.", 1001),
    HOLDING_GLASSES("holding_glasses", "双人举杯", LUNAR_NEWYEAR, "视频内容\\n视频展示新年餐桌上人物拿着香槟高脚杯，干杯庆祝，场面十分红火热闹，动作自然且充满温暖，画面捕捉到人物的的微表情和互动细节.\\n# 要求1.根据人物的位置和状态合理的设计动作.2.Take a step-by-step approach in your response.3.根据用户上传图片确定人物数量，不要出现[一群人]，不要出现图片中没有的人物.4.如果图片中有多个人物，则需要一起举杯庆祝。每个只拿一只杯子.5.如果图片展示了背景图，则设定为背景并详细描述.6.保证人物始终在画面中心 颜色温馨自然喜庆，不要黑白色.", 1002),
    RAINING_RED_PACKET("raining_red_packet", "红包雨", LUNAR_NEWYEAR, "视频内容\\n视频展示人物推开屋门 门内在下金光闪闪的红包雨 洒在人物身上 人物很开心。人物动作自然且充满温暖，画面捕捉到人物的的微表情和互动细节 \\n# 要求1.根据人物的位置和状态合理的设计动作.2.Take a step-by-step approach in your response.3.根据用户上传图片确定人物数量，不要出现[一群人]、[们]等代词而是精准地指出人物数量.4.不要出现图片中没有的人物.5.保证人物始终在画面中心.6.推开房门后才洒落红包雨，推开房门前不要蹦出东西.7.洒落的是红色的红包雨不是金币 .8.要有一把真实的门，要有打开房门的动作，红包从门外掉落.", 1002),

    // 财神特效子类
    HEART_SIGN_WITH_WISH_SENDER("heart_sign_with_wish_sender", "视频内容\\n 视频展示了红衣服财神角色站在人物角色旁边，他们一起面对镜头,每个人伸出双手各自在自己胸前比心，动作自然且充满温暖。\\n# 要求 1. 如果图片中有多个人物，则每个人各自在自己胸前比心。不要出现图片中没有的人物。2. 强调只有1个财神。3.如果图片展示了背景图，则设定为背景并详细描述。4. 保证人物和财神始终在画面中心。", WISH_SENDER, "表现恐惧情绪的特效", 1401),
    GOLD_COINS_FROM_WISH_SENDER("gold_coins_from_wish_sender", "视频内容\\n 视频展示一个财神角色与人物的互动。财神在人物旁边站立着举起双手向上洒金币，然后金币如雨点般从空中散落下来，主体人物伸出双手开心的抓住2个金币拿在手里，脸上露出惊喜的笑容。红色喜庆背景，充满了中国春节元素，金币细节。\\n# 要求 1.根据图片精准描述人物外观。2.根据图片精准描述财神外观和金币外观。3.如果图片展示了背景图，则设定为背景并详细描述。4.人物和财神时候总保持在画面中心。5.motion_level:small.", WISH_SENDER, "表现微笑情绪的特效", 1402);

    @Getter
    private String value;

    @Getter
    private String label;

    @Getter
    private TaskSceneEnums parent;

    @Getter
    private String description;

    @Getter
    private Integer sort;

    public static void main(String[] args) {
        System.out.println(IHierarchicalEnum.getTopLevelEnums(TaskSceneEnums.class));

        List<TaskSceneEnums> children = TaskSceneEnums.HAIR_SWAP.getChildren();
        if (ObjectUtils.isNotEmpty(children)){
            System.out.println(children);
        }

    }
}
