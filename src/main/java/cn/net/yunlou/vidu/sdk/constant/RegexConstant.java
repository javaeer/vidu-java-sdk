package cn.net.yunlou.vidu.sdk.constant;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 常用 正则表达式 常量
 */
@Slf4j
public class RegexConstant implements Serializable {


    //末尾任意一个字符
    public static final String LAST_EVERY_CHAR = ".$";

    //开头任意一个字符
    public static final String FIRST_EVERY_CHAR = "^.";

    //匹配前面的表达式1次或多次 StringUtils.PLUS
    public static final String REGEX_PLUS = "+";

    //匹配前面的表达式0次或多次
    public static final String REGEX_STAR = "*";

    //匹配任意单个或多个字符
    public static final String REGEX_MORE = ".*";


    //纯数字组成
    public static final String ONLY_NUM_REGEX = "^\\d*$";

    //纯汉字组成
    public static final String ONLY_CHINESE_REGEX = "^[\\u4e00-\\u9fa5]*$";

    //数字+字母组成
    public static final String ONLY_NUM_AND_LETTER_REGEX = "^[a-zA-Z0-9]+$";

    //邮箱校验
    public static final String EMAIL_REGEX = "^\\w+([-.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    //国内手机号码校验
    /**
     * 正则：手机号（精确）, 已知3位前缀＋8位数字
     * <p>
     * 移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
     * </p>
     * <p>
     * 联通：130、131、132、145、155、156、175、176、185、186
     * </p>
     * <p>
     * 电信：133、153、173、177、180、181、189
     * </p>
     * <p>
     * 全球星：1349
     * </p>
     * <p>
     * 虚拟运营商：170
     * </p>
     */
    public static final String CN_MOBILE_REGEX = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    //国内固话
    public static final String CN_TEL_REGEX = "^0\\d{2,3}-?\\d{7,8}$";


    //http(s)网络协议
    public static final String NP_HTTP_REGEX = "^http(s)*://.*$";

    //ftp网络协议
    public static final String NP_FTP_REGEX = "^ftp(s)*://.*$";

    //Mac地址校验
    public static final String MAC_REGEX = "^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2}){5}$";

    //域名校验
    public static final String DOMAIN_REGEX = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";

    //车牌号
    public static final String CAR_NUMBER_REGEX = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

    //身份证号
    public static final String ID_CARD_REGEX = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

    //邮政编码
    public static final String POST_CODE_REGEX = "^[1-9]\\d{5}$";


    //IP地址校验
    public static final String IP_REGEX = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";

    //货币金额
    public static final String CURRENCY_AMOUNT_REGEX = "^(-)?[\\d]+(.)?[\\d]*$";

    //网络地址(包含ftp等协议)"^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$"
    public static final String NET_ADDRESS_REGEX = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";


    public static void main(String[] args) {
        String s = "a:bc:cc";
        boolean b = s.matches("^.*[^:]*:.*$");
        log.info(b + "");
    }


}
