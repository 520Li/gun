package cn.lac.wechat.service.impl;

import cn.lac.wechat.enums.MessageType;
import cn.lac.wechat.service.MessageService;
import cn.lac.wechat.template.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Override
    public String getReplyTemplate(Message message) {
        return getReplayXml(message);
    }


    /**
     * @param message 接收的信息
     * @return 返回的信息
     */
    private String getReplayXml(Message message) {
        StringBuilder replayXml = new StringBuilder();
        String msgType = message.getMsgType();
        String content = "";
        if (msgType.equals(MessageType.TEXT.toString())) {
            content = "<Content><![CDATA[" + message.getContent() + "]]></Content>";
        } else if (msgType.equals(MessageType.IMAGE.toString())) {
            content = "<Image><MediaId><![CDATA[" + message.getMediaId() + "]]></MediaId></Image>";
        } else if (msgType.equals(MessageType.VOICE.toString())) {
            content = "<Voice><MediaId><![CDATA[" + message.getMediaId() + "]]></MediaId></Voice>";
        } else if (msgType.equals(MessageType.VIDEO.toString())) {
            content = "<Video>" +
                    "<MediaId><![CDATA[" + message.getMediaId() + "]]></MediaId>" +
                    "<Title><![CDATA[" + message.getTitle() + "]]></Title>" +
                    "<Description><![CDATA[" + message.getDescription() + "]]></Description>" +
                    "</Video>";
        } else if (msgType.equals(MessageType.LOCATION.toString())) {
            msgType = "text";
            content = "<Content><![CDATA[地址]]></Content>";
        } else if (msgType.equals(MessageType.LINK.toString())) {
            msgType = "text";
            content = "<Content><![CDATA[链接]]></Content>";
        } else if (msgType.equals(MessageType.EVENT.toString())) {
            msgType = "text";
            if (message.getEvent().equals("subscribe")) {
                content = "<Content><![CDATA[谢谢关注]]></Content>";
                if (null != message.getEventKey()) {
                    log.info("用户扫描带参数二维码事件");
                }
            } else if (message.getEvent().equals("unsubscribe")) {
                content = "<Content><![CDATA[退订]]></Content>";
            } else if (message.getEvent().equals("LOCATION")) {
                content = "<Content><![CDATA[经度：" + message.getLongitude() +
                        ",纬度：" + message.getLatitude() +
                        ",精度：" + message.getPrecision() + "]]></Content>";
            } else if (message.getEvent().equals("CLICK")) {
                content = "<Content><![CDATA[用户点击了菜单: " + message.getEventKey() + "]]></Content>";
            } else if (message.getEvent().equals("VIEW")) {
                content = "<Content><![CDATA[用户点击了跳转视图:" + message.getEventKey() + "]]></Content>";
            }
        } else {
            msgType = "text";
            content = "<Content><![CDATA[你好]]></Content>";
        }

        replayXml.append("<xml>" +
                "<ToUserName><![CDATA[" + message.getFromUserName() + "]]></ToUserName>" +
                "<FromUserName><![CDATA[" + message.getToUserName() + "]]></FromUserName>" +
                "<CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
                "<MsgType><![CDATA[" + msgType + "]]></MsgType>");
        replayXml.append(content);
        replayXml.append("</xml>");
        log.info("{}", replayXml);
        return replayXml.toString();
    }


}
