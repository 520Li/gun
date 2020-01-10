package cn.lac.wechat.vo;

/**
 * @author
 * @date
 */
public class Result {

    private boolean flag; //状态码, 0表示成功

    private Integer code;//状态码

    private String msg;  //提示信息

    private Object data; // 当前数据

    public Result() {
    }

    public Result(boolean flag) {
        this.flag = flag;
    }


    public Result(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Result(boolean flag, String msg, Object data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }


    public Result(Integer code) {
        this.flag = false;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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
}
