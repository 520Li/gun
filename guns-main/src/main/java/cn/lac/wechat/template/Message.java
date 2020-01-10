package cn.lac.wechat.template;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Message {
    /*
       <xml>
           <ToUserName><![CDATA[gh_908ec5c3648e]]></ToUserName>
           <FromUserName><![CDATA[omRjy1OC0-3_iFigWCNsKwsxAcAg]]></FromUserName>
           <CreateTime>1577678830</CreateTime>
           <MsgType><![CDATA[text]]></MsgType>
           <Content><![CDATA[aaa]]></Content>
           <MsgId>22586931644625766</MsgId>
       </xml>

        */
    /*
        "xml":
        {"Content":"aaaaaa",
        "CreateTime":"1577679337",
        "ToUserName":"gh_908ec5c3648e",
        "FromUserName":"omRjy1OC0-3_iFigWCNsKwsxAcAg",
        "MsgType":"text",
        "MsgId":"22586936784564501"}

         */
    private String Content;
    private String CreateTime;
    private String ToUserName;
    private String FromUserName;
    private String MsgType;
    private String MsgId;
    private String MediaId;
    private String Title;
    private String Description;

    private String Event;
    private String EventKey;

    private String Latitude;
    private String Longitude;
    private String Precision;

    private Map<String, Object> map = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return map;
    }
    @JsonAnySetter
    public void setMap(String key, Object value) {
        this.map.put(key, value);
    }


}
