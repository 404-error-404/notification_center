package general.notification_center.utils;


/**
 * @author 小乐乐
 * @date 2022/2/16 21:57
 */
public class HttpResult {
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    public HttpResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
