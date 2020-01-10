package cn.lac.wechat.vo;

/**
 * ClassName: Message <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/2 0002 - 23:53
 */
public class Message {
    private String mobile;
    private String signName;
    private String TemplateCode;
    private String TemplateParam;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSignName() {
        return this.signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return this.TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.TemplateCode = templateCode;
    }

    public String getTemplateParam() {
        return this.TemplateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.TemplateParam = templateParam;
    }

    public Message(String mobile, String signName, String templateCode, String templateParam) {
        this.mobile = mobile;
        this.signName = signName;
        this.TemplateCode = templateCode;
        this.TemplateParam = templateParam;
    }

    public Message() {
    }
}