package cn.net.yunlou.vidu;


import cn.net.yunlou.common.utils.StringUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ViduClientConfigUtils {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    private static final Map<String, ViduClientConfig> CONFIG_MAP = new ConcurrentHashMap<>();
    private static final String DEFAULT_CONFIG_KEY = "_default_config_key_";

    /**
     * <p>向缓存中设置 ViduClientConfig </p>
     * <p>每个 apiKey 只需添加一次，相同 apiKey 将被覆盖</p>
     *
     * @param clientConfig 客户端配置
     * @return {@link ViduClientConfig}
     */
    public static ViduClientConfig putClientConfig(ViduClientConfig clientConfig) {
        if (CONFIG_MAP.size() == 0) {
            CONFIG_MAP.put(DEFAULT_CONFIG_KEY, clientConfig);
        }
        CONFIG_MAP.put(clientConfig.getApiKey(), clientConfig);
        return clientConfig;
    }

    /**
     * 向当前线程中设置 {@link ViduClientConfig}
     *
     * @param clientConfig {@link ViduClientConfig} 客户端配置对象
     * @return {@link ViduClientConfig}
     */
    public static ViduClientConfig setThreadLocalClientConfig(ViduClientConfig clientConfig) {
        if (StringUtils.isNotEmpty(clientConfig.getApiKey())) {
            setThreadLocalAppKey(clientConfig.getApiKey());
        }
        return putClientConfig(clientConfig);
    }

    /**
     * 通过 ViduClientConfig 移除客户端配置
     *
     * @param aliPayApiConfig {@link ViduClientConfig} 客户端配置对象
     * @return {@link ViduClientConfig}
     */
    public static ViduClientConfig removeClientConfig(ViduClientConfig aliPayApiConfig) {
        return removeClientConfig(aliPayApiConfig.getApiKey());
    }

    /**
     * 通过 appKey 移除客户端配置
     *
     * @param appKey 客户端应用编号
     * @return {@link ViduClientConfig}
     */
    public static ViduClientConfig removeClientConfig(String appKey) {
        return CONFIG_MAP.remove(appKey);
    }

    /**
     * 向当前线程中设置 appKey
     *
     * @param appKey 客户端应用编号
     */
    public static void setThreadLocalAppKey(String appKey) {
        if (StringUtils.isEmpty(appKey)) {
            appKey = CONFIG_MAP.get(DEFAULT_CONFIG_KEY).getApiKey();
        }
        THREAD_LOCAL.set(appKey);
    }

    /**
     * 移除当前线程中的 appKey
     */
    public static void removeThreadLocalAppKey() {
        THREAD_LOCAL.remove();
    }

    /**
     * 获取当前线程中的  appKey
     *
     * @return 客户端应用编号 appKey
     */
    public static String getAppKey() {
        String appKey = THREAD_LOCAL.get();
        if (StringUtils.isEmpty(appKey)) {
            appKey = CONFIG_MAP.get(DEFAULT_CONFIG_KEY).getApiKey();
        }
        return appKey;
    }

    /**
     * 获取当前线程中的 ViduClientConfig
     *
     * @return {@link ViduClientConfig}
     */
    public static ViduClientConfig getClientConfig() {
        String appKey = getAppKey();
        return getClientConfig(appKey);
    }

    /**
     * 通过 appKey 获取 ViduClientConfig
     *
     * @param appKey 客户端应用编号
     * @return {@link ViduClientConfig}
     */
    public static ViduClientConfig getClientConfig(String appKey) {
        ViduClientConfig clientConfig = CONFIG_MAP.get(appKey);
        if (clientConfig == null) {
            throw new IllegalStateException("需事先调用 ViduClientConfigUtils.putClientConfig(viduClientConfig) 将 appKey对应的 aliPayApiConfig 对象存入，才可以使用 ViduClientConfigUtils.getClientConfig() 的系列方法");
        }
        return clientConfig;
    }
}
