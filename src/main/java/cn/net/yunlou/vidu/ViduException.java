package cn.net.yunlou.vidu;


public class ViduException extends RuntimeException {

    private Integer code;

    public ViduException(Integer code, String message) {
        super(message);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }
}
