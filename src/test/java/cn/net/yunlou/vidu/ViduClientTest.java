package cn.net.yunlou.vidu;

import cn.net.yunlou.vidu.sdk.entity.Input;
import cn.net.yunlou.vidu.sdk.entity.Output;
import cn.net.yunlou.vidu.sdk.entity.Prompt;
import cn.net.yunlou.vidu.sdk.entity.Task;
import cn.net.yunlou.vidu.sdk.enums.PromptTypeEnums;
import cn.net.yunlou.vidu.sdk.enums.TaskTypeEnums;
import cn.net.yunlou.vidu.sdk.enums.TextToVideoStyleEnums;
import cn.net.yunlou.vidu.sdk.enums.ToVideoModelEnums;
import cn.net.yunlou.vidu.sdk.enums.UpscaleModelEnums;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.junit.Test;

public class ViduClientTest {


    @Test
    public void testAddTaskApi() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey("vda_XXXX_pCTBbstR0hHyDZ5nDJzoxWSTGBxnsm2s");
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
        task.setModel(ToVideoModelEnums.VIDU_HIGH_PERFORMANCE.getValue());
        ArrayList<Prompt> prompts = Lists.newArrayList();
        Prompt prompt = new Prompt();
        prompt.setType(PromptTypeEnums.TEXT.getValue());
        prompt.setContent("让人物笑起来");
        prompts.add(prompt);
        Prompt prompt1 = new Prompt();
        prompt1.setType(PromptTypeEnums.IMAGE.getValue());
        prompt1.setContent("https://n.sinaimg.cn/translate/20170906/ZKDd-fykqmrw1599061.jpg");
        prompts.add(prompt1);
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
    public void testAddTaskApi2() {
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
        input.setCreationId("2484889552625059");
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
        viduClient.getTaskApi("2484890214112854");
    }
}