package general.notification_center.utils;


import com.alibaba.fastjson.JSON;

/**
 * @author 小乐乐
 * @date 2022/2/16 21:57
 */
public class HttpResult {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 响应数据
     */
    private Object data;

    public HttpResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult ok() {
        return new HttpResult(200, "success", null);
    }

    public static HttpResult ok(String msg) {
        return new HttpResult(200, msg, null);
    }

    public static HttpResult ok(String msg, Object data) {
        return new HttpResult(200, msg, data);
    }

    public static HttpResult okWithData(Object data) {
        return new HttpResult(200, "success", data);
    }

    public static HttpResult error(Integer code, String msg) {
        return new HttpResult(code, msg, null);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
