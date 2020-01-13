package cn.lac.wechat.vo;



/**
 * @author lac
 * @date 2019/2/26 0026 - 19:21
 */
public class LayerVo {



    private String code = "0";
    private String msg;
    private Integer count;
    private Object data;

    public LayerVo(){}

    public LayerVo(Integer count, Object data){
        this.count = count;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
