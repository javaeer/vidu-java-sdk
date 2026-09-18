package cn.net.yunlou.vidu;


import lombok.Getter;

@Getter
public class ViduException extends RuntimeException {

    private final Integer code;

    public ViduException(Integer code, String message) {
        super(message);
        this.code = code;
    }


}
