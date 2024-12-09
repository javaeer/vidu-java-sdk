package cn.net.yunlou.vidu;

import cn.net.yunlou.common.IEnum;
import cn.net.yunlou.common.utils.JsonUtils;
import cn.net.yunlou.common.utils.ObjectUtils;
import cn.net.yunlou.common.utils.OkHttp3ClientUtils;
import cn.net.yunlou.common.utils.StringUtils;
import cn.net.yunlou.vidu.sdk.ViduResponse;
import cn.net.yunlou.vidu.sdk.entity.Prompt;
import cn.net.yunlou.vidu.sdk.entity.Task;
import cn.net.yunlou.vidu.sdk.enums.PromptTypeEnums;
import cn.net.yunlou.vidu.sdk.enums.TaskTypeEnums;
import cn.net.yunlou.vidu.sdk.enums.TextToVideoStyleEnums;
import cn.net.yunlou.vidu.sdk.enums.ToVideoModelEnums;
import cn.net.yunlou.vidu.sdk.enums.UpscaleModelEnums;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ViduClient {

    /**
     *
     */
    public static final String VIDU_API_DOMAIN = "https://api.vidu.zone";

    public static final String VIDU_API_TASK_ADD = "/ent/v1/tasks";

    public static final String VIDU_API_TASK_GET = "/ent/v1/tasks/{id}/creations";

    public ViduResponse addTaskApi(Task task) {

        //validateAddTaskMode(task);

        String url = VIDU_API_DOMAIN + VIDU_API_TASK_ADD;

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        String json = JsonUtils.toJson(task);
        System.out.println(json);
        ViduResponse response = null;
        try {
            String data = OkHttp3ClientUtils.getInstance().postJson(url, headerParams, json);
            if (ObjectUtils.isNotEmpty(data)) {
                response = JsonUtils.fromJson(data, ViduResponse.class);
            }
            System.out.println(data);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return response;
    }

    public ViduResponse getTaskApi(String taskId) {
        String url = VIDU_API_DOMAIN + StringUtils.replace(VIDU_API_TASK_GET, "{id}", taskId);

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        ViduResponse response = null;
        String data = OkHttp3ClientUtils.getInstance().getData(url, headerParams);
        System.out.println(data);
        if (ObjectUtils.isNotEmpty(data)) {
            //{"state":"success", "err_code":"", "creations":[{"id":"2483737583952842", "url":"https://prod-ss-vidu.s3.cn-northwest-1.amazonaws.com.cn/infer/tasks/24/1020/13/2483736620333299/creation-01/video.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIARRHG6JR7EMNHVUWT%2F20241020%2Fcn-northwest-1%2Fs3%2Faws4_request&X-Amz-Date=20241020T135540Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&response-content-disposition=attachment%3Bfilename%3Dvidu-high-performance-general-8-2024-10-20T13%253A48%253A31Z.mp4&x-id=GetObject&X-Amz-Signature=e7e3dd8fcb52988f1aa67ec46b2af3c926ff590b65466eb9e613b2b964bf8db2", "cover_url":"https://prod-ss-vidu.s3.cn-northwest-1.amazonaws.com.cn/infer/tasks/24/1020/13/2483736620333299/creation-01/cover.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIARRHG6JR7EMNHVUWT%2F20241020%2Fcn-northwest-1%2Fs3%2Faws4_request&X-Amz-Date=20241020T135540Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&x-id=GetObject&X-Amz-Signature=dcd418532566bfcc2d4a03b69387b9464572f04595fb232447d2260cbd5b1ff1", "video":{"duration":7.85, "fps":16, "resolution":{"width":688, "height":384}}}]}
            response = JsonUtils.fromJson(data, ViduResponse.class);
        }


        return response;
    }

    private void validateAddTaskMode(Task task) {
        if (ObjectUtils.isEmpty(task)) {
            throw new ViduException(400, "参数不可为空");
        }

        if (ObjectUtils.isEmpty(task.getType())) {
            throw new ViduException(400, "type 不可为空");
        } else {
            if (!IEnum.validateValue(task.getType(), TaskTypeEnums.class)) {
                throw new ViduException(400, "type 不是有效的值");
            }

            if (ObjectUtils.isEmpty(task.getModel())) {
                throw new ViduException(400, "model 不可为空");
            }

            if (ObjectUtils.isEmpty(task.getInput())) {
                throw new ViduException(400, "input 不可为空");
            }

            if (ObjectUtils.isEmpty(task.getInput().getCreationId())){
                if (ObjectUtils.isEmpty(task.getInput().getSeed())) {
                    throw new ViduException(400, "input.speed 不可为空");
                }
                if (ObjectUtils.isEmpty(task.getInput().getEnhance())) {
                    throw new ViduException(400, "input.speed 不可为空");
                }
            }

            List<Prompt> prompts = task.getInput().getPrompts();
            if (ObjectUtils.isEmpty(prompts)) {
                throw new ViduException(400, "prompts 不可为空");
            }
            for (Prompt p : prompts) {
                if (ObjectUtils.isEmpty(p)) {
                    throw new ViduException(400, "prompts 不可为空");
                }
                if (ObjectUtils.isEmpty(p.getType())) {
                    throw new ViduException(400, "prompt.type 不可为空");
                }
                if (ObjectUtils.isEmpty(p.getContent())) {
                    throw new ViduException(400, "prompt.content 不可为空");
                }
                if (!IEnum.validateValue(p.getType(), PromptTypeEnums.class)) {
                    throw new ViduException(400, "prompt.type 不是有效值");
                }
            }


            TaskTypeEnums typeEnums = IEnum.getEnumByValue(task.getType(), TaskTypeEnums.class);
            switch (typeEnums) {
                case UPSCALE:
                    if (!IEnum.validateValue(task.getModel(), UpscaleModelEnums.class)) {
                        throw new ViduException(400, "type 不是有效的值");
                    }
                    if (ObjectUtils.isEmpty(task.getInput().getCreationId())) {
                        throw new ViduException(400, "creationId 不可为空");
                    }
                    break;
                case TEXT2VIDEO:
                    if (!IEnum.validateValue(task.getModel(), ToVideoModelEnums.class)) {
                        throw new ViduException(400, "model 不是有效的值");
                    }
                    if (ObjectUtils.isEmpty(task.getStyle())) {
                        throw new ViduException(400, "style 不可为空");
                    }
                    if (!IEnum.validateValue(task.getStyle(), TextToVideoStyleEnums.class)) {
                        throw new ViduException(400, "style 不是有效的值");
                    }
                    break;
                case IMG2VIDEO:
                    if (!IEnum.validateValue(task.getModel(), ToVideoModelEnums.class)) {
                        throw new ViduException(400, "model 不是有效的值");
                    }
                    break;
                case CHARACTER2VIDEO:
                    if (!IEnum.validateValue(task.getModel(), ToVideoModelEnums.class)) {
                        throw new ViduException(400, "model 不是有效的值");
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
