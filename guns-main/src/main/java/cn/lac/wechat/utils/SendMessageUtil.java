package cn.lac.wechat.utils;

import cn.lac.wechat.wx.Message;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Random;

/**
 * ClassName: SendMessageUtil <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/2 0002 - 23:48
 */
public class SendMessageUtil {
    static final String product = "Dysmsapi";
    static final String domain = "dysmsapi.aliyuncs.com";
    static final String accessKeyId = "1";
    static final String accessKeySecret = "2";


    public static SendSmsResponse sendSms(Message message) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(message.getMobile());
        request.setSignName(message.getSignName());
        request.setTemplateCode(message.getTemplateCode());
        request.setTemplateParam(message.getTemplateParam());
        SendSmsResponse sendSmsResponse = (SendSmsResponse) acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    public static String getSixNum() {
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(4);

        for (int i = 0; i < 6; ++i) {
            char ch = str.charAt((new Random()).nextInt(str.length()));
            sb.append(ch);
        }

        return sb.toString();
    }


}