package cn.net.yunlou.vidu.sdk.video;

import cn.net.yunlou.vidu.sdk.enums.ModelEnums;

public class VideoConfigFactory {
    public static VideoConfig createConfig(String modelName, Integer duration, String resolution) {
        Model model = Model.fromString(modelName);
        if (duration == null && resolution == null) {
            return new VideoConfig(model);
        } else if (resolution == null) {
            return new VideoConfig(model, duration);
        } else {
            // 如果提供了分辨率，必须提供时长
            if (duration == null) {
                throw new IllegalArgumentException("Duration must be provided when resolution is specified.");
            }
            return new VideoConfig(model, duration, resolution);
        }
    }

    public static void main(String[] args) {
        // 只指定模型，使用默认时长和分辨率
        VideoConfig config1 = VideoConfigFactory.createConfig(ModelEnums.VIDUQ1.getValue(), null, null);
        System.out.println("Model: " + config1.getModel() + ", Duration: " + config1.getDuration() + ", Resolution: " + config1.getResolution());

        // 指定模型和时长，使用默认分辨率
        VideoConfig config2 = VideoConfigFactory.createConfig(ModelEnums.VIDU2_0.getValue(), 4, null);
        System.out.println("Model: " + config2.getModel() + ", Duration: " + config2.getDuration() + ", Resolution: " + config2.getResolution());

        // 指定模型、时长和分辨率
        VideoConfig config3 = VideoConfigFactory.createConfig(ModelEnums.VIDU1_5.getValue(), 8, "720p");
        System.out.println("Model: " + config3.getModel() + ", Duration: " + config3.getDuration() + ", Resolution: " + config3.getResolution());

        // 错误示例：不支持的时长
        try {
            VideoConfig config4 = VideoConfigFactory.createConfig(ModelEnums.VIDUQ1.getValue(), 4, null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 错误示例：不支持的分辨率
        try {
            VideoConfig config5 = VideoConfigFactory.createConfig("vidu1.5", 4, "4K");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}