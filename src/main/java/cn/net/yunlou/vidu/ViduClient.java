package cn.net.yunlou.vidu;

import cn.net.yunlou.common.IEnum;
import cn.net.yunlou.common.IHierarchicalEnum;
import cn.net.yunlou.common.utils.*;
import cn.net.yunlou.vidu.sdk.*;
import cn.net.yunlou.vidu.sdk.constant.ViduConstant;
import cn.net.yunlou.vidu.sdk.enums.*;
import cn.net.yunlou.vidu.sdk.video.VideoConfig;
import cn.net.yunlou.vidu.sdk.video.VideoConfigFactory;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class ViduClient {

    public ViduResponse img2Video(Img2VideoViduRequest viduRequest) {

        validateImg2VideoMode(viduRequest);

        String url = ViduConstant.API_DOMAIN + ViduConstant.API_IMG_TO_VIDEO;

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        String json = JsonUtils.toJson(viduRequest);
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

    public ViduResponse reference2Video(Reference2VideoViduRequest viduRequest) {

        validateReference2VideoMode(viduRequest);

        String url = ViduConstant.API_DOMAIN + ViduConstant.API_REFERENCE_TO_VIDEO;

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        String json = JsonUtils.toJson(viduRequest);
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


    public ViduResponse startEnd2Video(StartEnd2VideoViduRequest viduRequest) {

        validateStartEnd2VideoMode(viduRequest);

        String url = ViduConstant.API_DOMAIN + ViduConstant.API_START_END_TO_VIDEO;

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        String json = JsonUtils.toJson(viduRequest);
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



    public ViduResponse text2Video(Text2VideoViduRequest viduRequest) {

        validateText2VideoMode(viduRequest);

        String url = ViduConstant.API_DOMAIN + ViduConstant.API_TEXT_TO_VIDEO;

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        String json = JsonUtils.toJson(viduRequest);
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



    public ViduResponse template2Video(Template2VideoViduRequest viduRequest) {

        validateTemplate2VideoMode(viduRequest);

        String url = ViduConstant.API_DOMAIN + ViduConstant.API_TEMPLATE_TO_VIDEO;

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        String json = JsonUtils.toJson(viduRequest);
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




    /**
     * @param taskId
     * @return
     */
    public ViduResponse getTaskApi(String taskId) {
        String url = ViduConstant.API_DOMAIN + StringUtils.replace(ViduConstant.API_TASK_GET, "{id}", taskId);

        HashMap<String, String> headerParams = Maps.newHashMap();
        headerParams.put("Authorization", "Token " + ViduClientConfigUtils.getClientConfig().getApiKey());
        ViduResponse response = null;
        String data = OkHttp3ClientUtils.getInstance().getData(url, headerParams);
        System.out.println(data);
        if (ObjectUtils.isNotEmpty(data)) {
            response = JsonUtils.fromJson(data, ViduResponse.class);
        }

        return response;
    }

    private void validateTemplate2VideoMode(Template2VideoViduRequest viduRequest) {
        TemplateEnums templateEnums = IEnum.getEnumByValue(viduRequest.getTemplate(), TemplateEnums.class);
        if (ObjectUtils.isEmpty(templateEnums)) {
            throw new ViduException(400, "不支持的template");
        }
        if (templateEnums.hasNextLevel()) {
            throw new ViduException(400, "选择具体的的template");
        }

        if (ObjectUtils.isNotEmpty(viduRequest.getAspectRatio())) {
            AspectRatioEnums aspectRatioEnums = IEnum.getEnumByValue(viduRequest.getAspectRatio(), AspectRatioEnums.class);
            if (ObjectUtils.isEmpty(aspectRatioEnums)){
                throw new ViduException(400, "不支持的aspectRatio");
            }
        }
    }

    private void validateText2VideoMode(Text2VideoViduRequest viduRequest) {
        ModelEnums modelEnums = IEnum.getEnumByValue(viduRequest.getModel(), ModelEnums.class);
        if (ObjectUtils.isEmpty(modelEnums)) {
            throw new ViduException(400, "不支持的model");
        }
        if (modelEnums != ModelEnums.VIDUQ1
                //&& modelEnums != ModelEnums.VIDUQ1_CLASSIC
                && modelEnums != ModelEnums.VIDU1_5
                //&& modelEnums != ModelEnums.VIDU2_0
        ) {
            throw new ViduException(400, "不支持的model");
        }
        DurationEnums durationEnums = IEnum.getEnumByValue(viduRequest.getDuration(), DurationEnums.class);
        if (ObjectUtils.isEmpty(durationEnums)) {
            throw new ViduException(400, "不支持的duration");
        }
        if (durationEnums != DurationEnums.S4
                && durationEnums != DurationEnums.S5
                && durationEnums != DurationEnums.S8) {
            throw new ViduException(400, "不支持的duration");
        }
        ResolutionEnums resolutionEnums = IEnum.getEnumByValue(viduRequest.getResolution(), ResolutionEnums.class);
        if (ObjectUtils.isEmpty(resolutionEnums)) {
            throw new ViduException(400, "不支持的resolution");
        }
        if (resolutionEnums != ResolutionEnums.SD
                && resolutionEnums != ResolutionEnums.HD
                && resolutionEnums != ResolutionEnums.FHD) {
            throw new ViduException(400, "不支持的resolution");
        }

        if (ObjectUtils.isNotEmpty(viduRequest.getStyle())) {
            StyleEnums styleEnums = IEnum.getEnumByValue(viduRequest.getStyle(), StyleEnums.class);
            if (ObjectUtils.isEmpty(styleEnums)){
                throw new ViduException(400, "不支持的style");
            }
        }

        if (ObjectUtils.isNotEmpty(viduRequest.getAspectRatio())) {
            AspectRatioEnums aspectRatioEnums = IEnum.getEnumByValue(viduRequest.getAspectRatio(), AspectRatioEnums.class);
            if (ObjectUtils.isEmpty(aspectRatioEnums)){
                throw new ViduException(400, "不支持的aspectRatio");
            }
        }

        VideoConfig config = VideoConfigFactory.createConfig(viduRequest.getModel(), viduRequest.getDuration(), viduRequest.getResolution());

        if (ObjectUtils.isEmpty(config)) {
            throw new ViduException(400, "参数有误");
        }

    }

    private void validateStartEnd2VideoMode(StartEnd2VideoViduRequest viduRequest) {
        ModelEnums modelEnums = IEnum.getEnumByValue(viduRequest.getModel(), ModelEnums.class);
        if (ObjectUtils.isEmpty(modelEnums)) {
            throw new ViduException(400, "不支持的model");
        }
        if (modelEnums != ModelEnums.VIDUQ1
                && modelEnums != ModelEnums.VIDUQ1_CLASSIC
                && modelEnums != ModelEnums.VIDU1_5
                && modelEnums != ModelEnums.VIDU2_0) {
            throw new ViduException(400, "不支持的model");
        }
        DurationEnums durationEnums = IEnum.getEnumByValue(viduRequest.getDuration(), DurationEnums.class);
        if (ObjectUtils.isEmpty(durationEnums)) {
            throw new ViduException(400, "不支持的duration");
        }
        if (durationEnums != DurationEnums.S4
                && durationEnums != DurationEnums.S5
                && durationEnums != DurationEnums.S8) {
            throw new ViduException(400, "不支持的duration");
        }
        ResolutionEnums resolutionEnums = IEnum.getEnumByValue(viduRequest.getResolution(), ResolutionEnums.class);
        if (ObjectUtils.isEmpty(resolutionEnums)) {
            throw new ViduException(400, "不支持的resolution");
        }
        if (resolutionEnums != ResolutionEnums.SD
                && resolutionEnums != ResolutionEnums.HD
                && resolutionEnums != ResolutionEnums.FHD) {
            throw new ViduException(400, "不支持的resolution");
        }

        VideoConfig config = VideoConfigFactory.createConfig(viduRequest.getModel(), viduRequest.getDuration(), viduRequest.getResolution());

        if (ObjectUtils.isEmpty(config)) {
            throw new ViduException(400, "参数有误");
        }

        if (Collections3.isEmpty(viduRequest.getImages())) {
            throw new ViduException(400, "images参数有误 不得为空");
        }
        if (viduRequest.getImages().size() != 2) {
            throw new ViduException(400, "images参数有误 只允许两张图片");
        }
    }


    private void validateReference2VideoMode(Reference2VideoViduRequest viduRequest) {
        ModelEnums modelEnums = IEnum.getEnumByValue(viduRequest.getModel(), ModelEnums.class);
        if (ObjectUtils.isEmpty(modelEnums)) {
            throw new ViduException(400, "不支持的model");
        }
        if (modelEnums != ModelEnums.VIDUQ1
                //&& modelEnums != ModelEnums.VIDUQ1_CLASSIC
                && modelEnums != ModelEnums.VIDU1_5
                && modelEnums != ModelEnums.VIDU2_0) {
            throw new ViduException(400, "不支持的model");
        }
        DurationEnums durationEnums = IEnum.getEnumByValue(viduRequest.getDuration(), DurationEnums.class);
        if (ObjectUtils.isEmpty(durationEnums)) {
            throw new ViduException(400, "不支持的duration");
        }
        if (durationEnums != DurationEnums.S4
                && durationEnums != DurationEnums.S5
                && durationEnums != DurationEnums.S8) {
            throw new ViduException(400, "不支持的duration");
        }
        ResolutionEnums resolutionEnums = IEnum.getEnumByValue(viduRequest.getResolution(), ResolutionEnums.class);
        if (ObjectUtils.isEmpty(resolutionEnums)) {
            throw new ViduException(400, "不支持的resolution");
        }
        if (resolutionEnums != ResolutionEnums.SD
                && resolutionEnums != ResolutionEnums.HD
                && resolutionEnums != ResolutionEnums.FHD) {
            throw new ViduException(400, "不支持的resolution");
        }

        if (ObjectUtils.isNotEmpty(viduRequest.getAspectRatio())) {
            AspectRatioEnums aspectRatioEnums = IEnum.getEnumByValue(viduRequest.getAspectRatio(), AspectRatioEnums.class);
            if (ObjectUtils.isEmpty(aspectRatioEnums)){
                throw new ViduException(400, "不支持的aspectRatio");
            }
        }

        VideoConfig config = VideoConfigFactory.createConfig(viduRequest.getModel(), viduRequest.getDuration(), viduRequest.getResolution());

        if (ObjectUtils.isEmpty(config)) {
            throw new ViduException(400, "参数有误");
        }

        if (Collections3.isEmpty(viduRequest.getImages())) {
            throw new ViduException(400, "images参数有误 不得为空");
        }
        if (viduRequest.getImages().size() > 7) {
            throw new ViduException(400, "images参数有误 不得多于 7 张图片");
        }

    }

    private void validateImg2VideoMode(Img2VideoViduRequest viduRequest) {

        ModelEnums modelEnums = IEnum.getEnumByValue(viduRequest.getModel(), ModelEnums.class);
        if (ObjectUtils.isEmpty(modelEnums)) {
            throw new ViduException(400, "不支持的model");
        }
        if (modelEnums != ModelEnums.VIDUQ1
                && modelEnums != ModelEnums.VIDU1_5
                && modelEnums != ModelEnums.VIDU2_0) {
            throw new ViduException(400, "不支持的model");
        }
        DurationEnums durationEnums = IEnum.getEnumByValue(viduRequest.getDuration(), DurationEnums.class);
        if (ObjectUtils.isEmpty(durationEnums)) {
            throw new ViduException(400, "不支持的duration");
        }
        if (durationEnums != DurationEnums.S4
                && durationEnums != DurationEnums.S5
                && durationEnums != DurationEnums.S8) {
            throw new ViduException(400, "不支持的duration");
        }
        ResolutionEnums resolutionEnums = IEnum.getEnumByValue(viduRequest.getResolution(), ResolutionEnums.class);
        if (ObjectUtils.isEmpty(resolutionEnums)) {
            throw new ViduException(400, "不支持的resolution");
        }
        if (resolutionEnums != ResolutionEnums.SD
                && resolutionEnums != ResolutionEnums.HD
                && resolutionEnums != ResolutionEnums.FHD) {
            throw new ViduException(400, "不支持的resolution");
        }


        VideoConfig config = VideoConfigFactory.createConfig(viduRequest.getModel(), viduRequest.getDuration(), viduRequest.getResolution());

        if (ObjectUtils.isEmpty(config)) {
            throw new ViduException(400, "参数有误");
        }

        if (Collections3.isEmpty(viduRequest.getImages()) || viduRequest.getImages().size() > 1) {
            throw new ViduException(400, "images参数有误 不得为空，且 只允许 一张图片");
        }


    }

}
