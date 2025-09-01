package cn.net.yunlou.vidu;

import cn.net.yunlou.vidu.sdk.*;
import cn.net.yunlou.vidu.sdk.enums.AspectRatioEnums;
import cn.net.yunlou.vidu.sdk.enums.ModelEnums;
import cn.net.yunlou.vidu.sdk.enums.TemplateEnums;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;

public class ViduClientTest {

    public static final String API_KEY="vda_XXXXX";


    @Test
    public void img2video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey(API_KEY);
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Img2VideoViduRequest request = new Img2VideoViduRequest();

        request.setModel(ModelEnums.VIDUQ1.getValue());
        ArrayList<String> images = Lists.newArrayList();
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/image2video.png");
        request.setImages(images);
        request.setPrompt("The astronaut waved and the camera moved up.");
        request.setDuration(5);
        request.setResolution("1080p");
        request.setOffPeak(false);
        viduClient.img2Video(request);
    }
    @Test
    public void reference2Video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey(API_KEY);
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        Reference2VideoViduRequest request = new Reference2VideoViduRequest();

        request.setModel(ModelEnums.VIDUQ1.getValue());
        ArrayList<String> images = Lists.newArrayList();
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/reference2video-1.png");
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/reference2video-2.png");
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/reference2video-3.png");
        request.setImages(images);
        request.setPrompt("Santa Claus and the bear hug by the lakeside.");
        request.setDuration(5);
        request.setResolution("1080p");
        request.setAspectRatio(AspectRatioEnums.R9_16.getValue());
        request.setOffPeak(false);
        viduClient.reference2Video(request);

    }

    @Test
    public void startEnd2Video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey(API_KEY);
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        StartEnd2VideoViduRequest request = new StartEnd2VideoViduRequest();

        request.setModel(ModelEnums.VIDUQ1.getValue());
        ArrayList<String> images = Lists.newArrayList();
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/startend2video-1.jpeg");
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/template/startend2video-2.jpeg");
        request.setImages(images);
        request.setPrompt("The camera zooms in on the bird, which then flies to the right. With its flight being smooth and natural, the bird soars in the sky. with a red light effect following and surrounding it from behind.");
        request.setDuration(5);
        request.setResolution("1080p");
        request.setOffPeak(false);
        viduClient.startEnd2Video(request);
    }

    @Test
    public void text2Video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey(API_KEY);
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();

        Text2VideoViduRequest request = new Text2VideoViduRequest();

        request.setModel(ModelEnums.VIDUQ1.getValue());
        request.setStyle("general");
        request.setPrompt("In an ultra-realistic fashion photography style featuring light blue and pale amber tones, an astronaut in a spacesuit walks through the fog. The background consists of enchanting white and golden lights, creating a minimalist still life and an impressive panoramic scene.");
        request.setDuration(5);
        request.setResolution("1080p");
        request.setAspectRatio(AspectRatioEnums.R16_9.getValue());
        request.setOffPeak(false);
        viduClient.text2Video(request);
    }
    @Test
    public void template2Video() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey(API_KEY);
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();

        Template2VideoViduRequest request = new Template2VideoViduRequest();

        request.setTemplate(TemplateEnums.HUGGING.getValue());
        ArrayList<String> images = Lists.newArrayList();
        images.add("https://prod-ss-images.s3.cn-northwest-1.amazonaws.com.cn/vidu-maas/scene-template/hug.jpeg");
        request.setImages(images);
        request.setPrompt("Video content\\n画面中的两个主体转向彼此，并开始拥抱# 要求\\n将Motion Level设置为‘Large’");
        request.setAspectRatio(AspectRatioEnums.R16_9.getValue());
        request.setWatermark(false);
        viduClient.template2Video(request);
    }

    @Test
    public void testGetTaskApi() {
        ViduClientConfig clientConfig = new ViduClientConfig();
        clientConfig.setApiKey(API_KEY);
        clientConfig.setApiVersion("1.0.0");
        ViduClientConfigUtils.putClientConfig(clientConfig);
        ViduClient viduClient = new ViduClient();
        //viduClient.getTaskApi("857464026177769472");
        //viduClient.getTaskApi("857503414022606848");
        //viduClient.getTaskApi("857508046513922048");
        //viduClient.getTaskApi("857511677267038208");
        viduClient.getTaskApi("859930141390856192");
    }



}