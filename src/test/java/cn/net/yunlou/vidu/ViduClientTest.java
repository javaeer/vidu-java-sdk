package cn.net.yunlou.vidu;

import cn.net.yunlou.vidu.sdk.entity.Input;
import cn.net.yunlou.vidu.sdk.entity.Output;
import cn.net.yunlou.vidu.sdk.entity.Prompt;
import cn.net.yunlou.vidu.sdk.entity.Task;
import cn.net.yunlou.vidu.sdk.enums.MovementAmplitudeEnums;
import cn.net.yunlou.vidu.sdk.enums.PromptTypeEnums;
import cn.net.yunlou.vidu.sdk.enums.TaskSceneEnums;
import cn.net.yunlou.vidu.sdk.enums.TaskTypeEnums;
import cn.net.yunlou.vidu.sdk.enums.TextToVideoStyleEnums;
import cn.net.yunlou.vidu.sdk.enums.ToVideoModelEnums;
import cn.net.yunlou.vidu.sdk.enums.UpscaleModelEnums;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.junit.Test;

public class ViduClientTest {

    @Test
    public void sceneVideo() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Task task = new Task();
        //task.setType(TaskTypeEnums.TEXT2VIDEO.getValue());
        task.setScene(TaskSceneEnums.KISS.getValue());
        //task.setModel(ToVideoModelEnums.VIDU_HIGH_PERFORMANCE.getValue());
        //task.setStyle(TextToVideoStyleEnums.GENERAL.getValue());
        task.setModelVersion("1.5");
        ArrayList<Prompt> prompts = Lists.newArrayList();
        Prompt prompt = new Prompt();
        prompt.setType(PromptTypeEnums.TEXT.getValue());
        prompt.setContent("两个人亲在一起");
        prompts.add(prompt);
        prompt = new Prompt();
        prompt.setType(PromptTypeEnums.IMAGE.getValue());
        prompt.setContent("http://dfs.smartcloudx.com/group1/M00/06/6C/cnT9GmdYDcuAHhdHAAf1jguSIdA10.jpeg");
        prompts.add(prompt);
        prompt = new Prompt();
        prompt.setType(PromptTypeEnums.IMAGE.getValue());
        prompt.setContent("http://dfs.smartcloudx.com/group1/M00/06/6C/cnT9GmdYDjCAZjlAAABR9UbYzkk391.jpg");
        prompts.add(prompt);

        Input input = new Input();
        input.setPrompts(prompts);
        //input.setSeed(1L);
        //input.setEnhance(false);
        task.setInput(input);
        //Output output = new Output();
        //output.setSampleCount(1);
        //output.setDuration(8);
        //task.setOutputParams(output);

        viduClient.addSceneTaskApi(task);
    }

    @Test
    public void testText2video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Task task = new Task();
        task.setModeration(false);
        task.setType(TaskTypeEnums.TEXT2VIDEO.getValue());
        task.setModel(ToVideoModelEnums.VIDU_HIGH_PERFORMANCE.getValue());
        task.setStyle(TextToVideoStyleEnums.GENERAL.getValue());
        ArrayList<Prompt> prompts = Lists.newArrayList();
        Prompt prompt = new Prompt();
        prompt.setType(PromptTypeEnums.TEXT.getValue());
        prompt.setContent("男孩子对女孩子说喜欢你，然后小情侣开始接吻，温馨的场面");
        prompts.add(prompt);
        Input input = new Input();
        input.setPrompts(prompts);
        input.setSeed(1L);
        input.setEnhance(false);
        task.setInput(input);
        Output output = new Output();
        output.setSampleCount(1);
        output.setDuration(8);
        task.setOutputParams(output);

        viduClient.addTaskApi(task);
    }

    @Test
    public void testImg2video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Task task = new Task();
        /*task.setType(TaskTypeEnums.TEXT2VIDEO.getValue());
        task.setStyle(TextToVideoStyleEnums.GENERAL.getValue());
        ArrayList<Prompt> prompts = Lists.newArrayList();
        Prompt prompt = new Prompt();
        prompt.setType(PromptTypeEnums.TEXT.getValue());
        prompt.setContent("雄鹰在蓝天上翱翔");
        prompts.add(prompt);
        Input input = new Input();
        input.setPrompts(prompts);
        input.setSeed(1L);
        input.setEnhance(false);
        task.setInput(input);
        Output output = new Output();
        output.setSampleCount(1);
        output.setDuration(8);
        task.setOutputParams(output);*/
        task.setType(TaskTypeEnums.IMG2VIDEO.getValue());
        task.setStyle(TextToVideoStyleEnums.GENERAL.getValue());
        task.setModelVersion("1.5");
        task.setModel(ToVideoModelEnums.VIDU_HIGH_PERFORMANCE.getValue());
        ArrayList<Prompt> prompts = Lists.newArrayList();
        Prompt prompt = new Prompt();
        prompt.setType(PromptTypeEnums.IMAGE.getValue());
        prompt.setContent("https://n.sinaimg.cn/translate/20170906/ZKDd-fykqmrw1599061.jpg");

        prompts.add(prompt);

        Input input = new Input();
        input.setSeed(1L);
        input.setEnhance(false);
        input.setMultiImageBoost(false);
        input.setPrompts(prompts);
        task.setInput(input);
        Output output = new Output();
        output.setSampleCount(1);
        output.setDuration(8);
        output.setAspectRatio("16:9");
        output.setMovementAmplitude(MovementAmplitudeEnums.auto.getValue());
        task.setOutputParams(output);

        viduClient.addTaskApi(task);
    }
    @Test
    public void testCharacter2video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Task task = new Task();
        task.setType(TaskTypeEnums.CHARACTER2VIDEO.getValue());
        task.setStyle(TextToVideoStyleEnums.GENERAL.getValue());
        task.setModelVersion("1.5");
        task.setModel(ToVideoModelEnums.VIDU_HIGH_PERFORMANCE.getValue());
        ArrayList<Prompt> prompts = Lists.newArrayList();
        Prompt prompt = new Prompt();
        prompt.setType(PromptTypeEnums.TEXT.getValue());
        prompt.setContent("女孩的手心漂浮着一只半透明金鱼，镜头逐渐拉近，镜头顺时针旋转");
        prompts.add(prompt);
        Prompt prompt1 = new Prompt();
        prompt1.setType(PromptTypeEnums.IMAGE.getValue());
        prompt1.setContent("https://dev-ss-images.s3.cn-northwest-1.amazonaws.com.cn/tmp/20241126-175112.png");
        prompts.add(prompt1);
        Prompt prompt2 = new Prompt();
        prompt2.setType(PromptTypeEnums.IMAGE.getValue());
        prompt2.setContent("https://dev-ss-images.s3.cn-northwest-1.amazonaws.com.cn/tmp/20241126-175120.png");
        prompts.add(prompt1);

        Input input = new Input();
        input.setSeed(1L);
        input.setEnhance(false);
        input.setMultiImageBoost(false);
        input.setPrompts(prompts);
        task.setInput(input);
        Output output = new Output();
        output.setSampleCount(1);
        output.setDuration(8);
        output.setAspectRatio("1:1");
        output.setResolution("720p");
        output.setMovementAmplitude(MovementAmplitudeEnums.auto.getValue());
        task.setOutputParams(output);

        viduClient.addTaskApi(task);
    }

    @Test
    public void testUpscale() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Task task = new Task();
        task.setType(TaskTypeEnums.UPSCALE.getValue());
        //task.setStyle(TextToVideoStyleEnums.GENERAL.getValue());
        task.setModel(UpscaleModelEnums.STABLE.getValue());

        Input input = new Input();
        input.setCreationId("2549713556571138");
        task.setInput(input);

        Output output = new Output();
        output.setSampleCount(1);
        output.setDuration(8);
        task.setOutputParams(output);

        viduClient.addTaskApi(task);
    }



    @Test
    public void testGetTaskApi() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_2480427274095509_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        //viduClient.getTaskApi("2548774043267062");
        //viduClient.getTaskApi("2548795231012357");
        //viduClient.getTaskApi("2548799434684836");
        //viduClient.getTaskApi("2549713556571138");
        //viduClient.getTaskApi("2553826101320335");
        viduClient.getTaskApi("2558723910423224");
    }
}