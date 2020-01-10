package cn.lac.wechat.service;

import cn.lac.wechat.vo.AccessToken;
import cn.lac.wechat.vo.JsapiTicket;

import java.util.Map;

public interface ApiService {

    /**
     * 获取access_token
     *
     * @return
     */
    public AccessToken fetchAccessToken();

    /**
     * 获取jsapi_ticket
     *
     * @return
     */
    public JsapiTicket fetchJsapiTicket();


    /**
     * 获取js-sdk认证
     *
     * @return
     */
    public Map fetchJsSdk(String url);


}
