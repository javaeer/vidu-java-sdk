package cn.net.yunlou.vidu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViduClientConfig {

    private String domain;

    private String apiVersion;

    private String apiKey;
}
