package cn.net.yunlou.vidu.sdk.video;

import cn.net.yunlou.vidu.sdk.enums.DurationEnums;
import cn.net.yunlou.vidu.sdk.enums.ResolutionEnums;

import java.util.Arrays;
import java.util.List;

public enum Model {
    VIDUQ1 {
        @Override
        public int getDefaultDuration() {
            return DurationEnums.S5.getValue();
        }

        @Override
        public List<Integer> getSupportedDurations() {
            return Arrays.asList(DurationEnums.S5.getValue());
        }

        @Override
        public String getDefaultResolution(int duration) {
            if (duration == DurationEnums.S5.getValue()) {
                return ResolutionEnums.FHD.getValue();
            }
            throw new IllegalArgumentException("Unsupported duration for VIDUQ1: " + duration);
        }

        @Override
        public List<String> getSupportedResolutions(int duration) {
            if (duration == DurationEnums.S5.getValue()) {
                return Arrays.asList(ResolutionEnums.FHD.getValue());
            }
            throw new IllegalArgumentException("Unsupported duration for VIDUQ1: " + duration);
        }
    },
    VIDUQ1_CLASSIC {
        @Override
        public int getDefaultDuration() {
            return DurationEnums.S5.getValue();
        }

        @Override
        public List<Integer> getSupportedDurations() {
            return Arrays.asList(DurationEnums.S5.getValue());
        }

        @Override
        public String getDefaultResolution(int duration) {
            if (duration == DurationEnums.S5.getValue()) {
                return ResolutionEnums.FHD.getValue();
            }
            throw new IllegalArgumentException("Unsupported duration for VIDUQ1_CLASSIC: " + duration);
        }

        @Override
        public List<String> getSupportedResolutions(int duration) {
            if (duration == DurationEnums.S5.getValue()) {
                return Arrays.asList(ResolutionEnums.FHD.getValue());
            }
            throw new IllegalArgumentException("Unsupported duration for VIDUQ1_CLASSIC: " + duration);
        }
    },
    VIDU2_0 {
        @Override
        public int getDefaultDuration() {
            return DurationEnums.S4.getValue();
        }

        @Override
        public List<Integer> getSupportedDurations() {
            return Arrays.asList(DurationEnums.S4.getValue(), DurationEnums.S8.getValue());
        }

        @Override
        public String getDefaultResolution(int duration) {
            if (duration == DurationEnums.S4.getValue()) {
                return ResolutionEnums.SD.getValue();
            } else if (duration == DurationEnums.S8.getValue()) {
                return ResolutionEnums.HD.getValue();
            }
            throw new IllegalArgumentException("Unsupported duration for VIDU2.0: " + duration);
        }

        @Override
        public List<String> getSupportedResolutions(int duration) {
            if (duration == DurationEnums.S4.getValue()) {
                return Arrays.asList(ResolutionEnums.SD.getValue(), ResolutionEnums.HD.getValue(), ResolutionEnums.FHD.getValue());
            } else if (duration == DurationEnums.S8.getValue()) {
                return Arrays.asList(ResolutionEnums.HD.getValue());
            }
            throw new IllegalArgumentException("Unsupported duration for VIDU2.0: " + duration);
        }
    },
    VIDU1_5 {
        @Override
        public int getDefaultDuration() {
            return DurationEnums.S4.getValue();
        }

        @Override
        public List<Integer> getSupportedDurations() {
            return Arrays.asList(DurationEnums.S4.getValue(), DurationEnums.S8.getValue());
        }

        @Override
        public String getDefaultResolution(int duration) {
            if (duration == DurationEnums.S4.getValue()) {
                return ResolutionEnums.SD.getValue();
            } else if (duration == DurationEnums.S8.getValue()) {
                return ResolutionEnums.HD.getValue();
            }
            throw new IllegalArgumentException("Unsupported duration for VIDU1.5: " + duration);
        }

        @Override
        public List<String> getSupportedResolutions(int duration) {
            if (duration == DurationEnums.S4.getValue()) {
                return Arrays.asList(ResolutionEnums.SD.getValue(), ResolutionEnums.HD.getValue(), ResolutionEnums.FHD.getValue());
            } else if (duration == DurationEnums.S8.getValue()) {
                return Arrays.asList(ResolutionEnums.HD.getValue());
            }
            throw new IllegalArgumentException("Unsupported duration for VIDU1.5: " + duration);
        }
    };

    // 从字符串转换为枚举值
    public static Model fromString(String name) {
        try {
            // 将输入字符串转换为大写，并将点替换为下划线
            String enumName = name.toUpperCase().replace(".", "_").replace("-", "_");
            return Model.valueOf(enumName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown model: " + name);
        }
    }

    public abstract int getDefaultDuration();

    public abstract List<Integer> getSupportedDurations();

    public abstract String getDefaultResolution(int duration);

    public abstract List<String> getSupportedResolutions(int duration);
}