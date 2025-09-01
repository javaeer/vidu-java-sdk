package cn.net.yunlou.vidu.sdk.video;

import lombok.Getter;

@Getter
public final class VideoConfig {
    private final Model model;
    private final int duration;
    private final String resolution;

    public VideoConfig(Model model) {
        this.model = model;
        this.duration = model.getDefaultDuration();
        this.resolution = model.getDefaultResolution(this.duration);
    }

    public VideoConfig(Model model, int duration) {
        this.model = model;
        if (!model.getSupportedDurations().contains(duration)) {
            throw new IllegalArgumentException("Unsupported duration for model " + model + ": " + duration);
        }
        this.duration = duration;
        this.resolution = model.getDefaultResolution(duration);
    }

    public VideoConfig(Model model, int duration, String resolution) {
        this.model = model;
        if (!model.getSupportedDurations().contains(duration)) {
            throw new IllegalArgumentException("Unsupported duration for model " + model + ": " + duration);
        }
        this.duration = duration;
        if (!model.getSupportedResolutions(duration).contains(resolution)) {
            throw new IllegalArgumentException("Unsupported resolution for model " + model + " and duration " + duration + ": " + resolution);
        }
        this.resolution = resolution;
    }

}