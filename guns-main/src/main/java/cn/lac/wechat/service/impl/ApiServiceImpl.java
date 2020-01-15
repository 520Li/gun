package cn.lac.wechat.service.impl;

import cn.lac.wechat.service.ApiService;
import cn.lac.wechat.utils.EncryptUtil;
import cn.lac.wechat.utils.FastJsonUtil;
import cn.lac.wechat.utils.HttpClientUtil;
import cn.lac.wechat.wx.AccessToken;
import cn.lac.wechat.wx.JsapiTicket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApiServiceImpl implements ApiService {

    @Value("${access_token_url}")
    private String accessTokenUrl;
    @Value("${jsapi_ticket_url}")
    private String jsapiTicketUrl;
    /*@Value("${url}")
    private String url;*/

    /**
     * 获取access_token
     *
     * @return
     */
    @Override
    public AccessToken fetchAccessToken() {
        AccessToken accessToken = null;
        try {
            //获取access_toekn
            accessToken = readAccessToken("accessToken.txt");
            //校验是否过期
            if (isValidAccessToken(accessToken)) {
                throw new Exception();
            }
        } catch (Exception e) {
            //已过期 重新获取access_token
            accessToken = getAccessToken();
            try {
                //保存本地
                saveAccessToken("accessToken.txt", accessToken);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        /*System.setProperty("access_token", accessToken.getAccess_token());*/
        return accessToken;
    }

    /**
     * 获取access_token
     *
     * @return
     */
    private AccessToken getAccessToken() {
        String data = HttpClientUtil.doGet(accessTokenUrl, null);
        AccessToken result = FastJsonUtil.toBean(data, AccessToken.class);
        //设置过期时间
        result.setExpires_in(System.currentTimeMillis() + (result.getExpires_in() - 60 * 5) * 1000);
        return result;
    }

    /**
     * 读取access_token
     *
     * @return
     */
    private AccessToken readAccessToken(String fileName) throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
        ByteBuffer dst = ByteBuffer.allocate(1024);
        inChannel.read(dst);
        dst.flip();
        byte[] bytes = new byte[dst.limit()];
        dst.get(bytes);
        AccessToken accessToken = FastJsonUtil.toBean(new String(bytes), AccessToken.class);
        dst.clear();
        inChannel.close();
        return accessToken;
    }

    /**
     * 保存access_token
     *
     * @return
     */
    private void saveAccessToken(String fileName, AccessToken token) throws IOException {
        FileChannel outChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer dst = ByteBuffer.allocate(1024);
        String tokenStr = FastJsonUtil.convertObjectToJSON(token);
        dst.put(tokenStr.getBytes());
        dst.flip();
        outChannel.write(dst);
        dst.clear();
        outChannel.close();
    }

    /**
     * 校验access_token是否过期
     *
     * @return
     */
    private boolean isValidAccessToken(AccessToken token) {
        if (null == token || null == token.getAccess_token() || null == token.getExpires_in()) {
            return false;
        }
        return System.currentTimeMillis() > token.getExpires_in();
    }


    /**
     * 获取jsapi_ticket
     *
     * @return
     */
    @Override
    public JsapiTicket fetchJsapiTicket() {
        JsapiTicket jsapiTicket = null;
        try {
            //获取jsapi_ticket
            jsapiTicket = readJsapiTicket("jsapiTicket.txt");
            //校验是否过期
            if (isValidJsapiTicket(jsapiTicket)) {
                throw new Exception();
            }
        } catch (Exception e) {
            //已过期 重新获取jsapi_ticket
            jsapiTicket = getJsapiTicket();
            try {
                //保存本地
                saveJsapiTicket("jsapiTicket.txt", jsapiTicket);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return jsapiTicket;
    }


    /**
     * 获取jsapi_ticket
     *
     * @return
     */
    private JsapiTicket getJsapiTicket() {
        AccessToken accessToken = fetchAccessToken();
        jsapiTicketUrl = String.format(jsapiTicketUrl, accessToken.getAccess_token());
        String data = HttpClientUtil.doGet(jsapiTicketUrl, null);
        JsapiTicket result = FastJsonUtil.toBean(data, JsapiTicket.class);
        //设置过期时间
        result.setExpires_in(System.currentTimeMillis() + (result.getExpires_in() - 60 * 5) * 1000);
        return result;
    }

    /**
     * 读取jsapi_ticket
     *
     * @return
     */
    private JsapiTicket readJsapiTicket(String fileName) throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.READ);
        ByteBuffer dst = ByteBuffer.allocate(1024);
        inChannel.read(dst);
        dst.flip();
        byte[] bytes = new byte[dst.limit()];
        dst.get(bytes);
        JsapiTicket jsapiTicket = FastJsonUtil.toBean(new String(bytes), JsapiTicket.class);
        dst.clear();
        inChannel.close();
        return jsapiTicket;
    }

    /**
     * 保存jsapi_ticket
     *
     * @return
     */
    private void saveJsapiTicket(String fileName, JsapiTicket ticket) throws IOException {
        FileChannel outChannel = FileChannel.open(Paths.get(fileName), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer dst = ByteBuffer.allocate(1024);
        String tokenStr = FastJsonUtil.convertObjectToJSON(ticket);
        dst.put(tokenStr.getBytes());
        dst.flip();
        outChannel.write(dst);
        dst.clear();
        outChannel.close();
    }

    /**
     * 校验jsapi_ticket是否过期
     *
     * @return
     */
    private boolean isValidJsapiTicket(JsapiTicket ticket) {
        if (null == ticket || null == ticket.getTicket() || null == ticket.getExpires_in()) {
            return false;
        }
        return System.currentTimeMillis() > ticket.getExpires_in();
    }


    /**
     * 获取JS-SDK认证
     *
     * @return
     */
    @Override
    public Map fetchJsSdk(String url) {
        /*
        noncestr=Wm3WZYTPz0wzccnW
        jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
        timestamp=1414587457
        url=http://mp.weixin.qq.com?params=value
         */
        String noncestr = UUID.randomUUID().toString();
        JsapiTicket jsapiTicket = fetchJsapiTicket();
        String jsapi_ticket = jsapiTicket.getTicket();
        String timestamp = System.currentTimeMillis() + "";

        List<String> list = Arrays.asList("jsapi_ticket=" + jsapi_ticket,
                "noncestr=" + noncestr,
                "timestamp=" + timestamp,
                "url=" + url);
        list.sort(String::compareTo);
        String key = list.stream().collect(Collectors.joining("&"));
        //System.out.println(key);
        key = EncryptUtil.getSha1(key);
        Map map = new HashMap();
        map.put("signature", key);
        map.put("noncestr", noncestr);
        map.put("timestamp", timestamp);
        return map;
    }

}
