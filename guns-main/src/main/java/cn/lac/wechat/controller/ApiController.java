package cn.lac.wechat.controller;


import cn.lac.wechat.service.ApiService;
import cn.lac.wechat.service.MenuService;
import cn.lac.wechat.service.MessageService;
import cn.lac.wechat.template.Message;
import cn.lac.wechat.utils.EncryptUtil;
import cn.lac.wechat.utils.FastJsonUtil;
import cn.lac.wechat.utils.RequestUtil;
import cn.lac.wechat.utils.XmlUtil;
import cn.lac.wechat.vo.GetParam;
import cn.lac.wechat.vo.JsapiTicket;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class ApiController {

    @Value("${token}")
    private String token;
    @Autowired
    private ApiService apiService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MenuService menuService;

    /**
     * 校验服务器的有效性
     *
     * @param param
     * @param request
     * @param response
     * @throws IOException
     */
   /* @RequestMapping(value = "/token", method = RequestMethod.GET)
    public void index(GetParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean flag = checkParam(param);
        if (flag) {
            response.getWriter().println(param.getEchostr());
        } else {
            response.getWriter().println("认证失败");
        }
    }*/


    /**
     * 接收服务器信息
     *
     * @param param
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void message(GetParam param, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        boolean flag = checkParam(param);
        if (!flag) {
            //response.sendError(500, "认证失败");
        }


        String xmlStr = RequestUtil.ReadAsChars(request);
        String jsonStr = XmlUtil.xml2Json(xmlStr).toString();
        //获取接收消息
        Message message = formatJsonStr(jsonStr);
        //自定义回复消息
        String replyTemplate = messageService.getReplyTemplate(message);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(replyTemplate);


    }

    /*
     * @param jsonStr
     * @return
     */
    private Message formatJsonStr(String jsonStr) {
        Map<Object, Object> map = FastJsonUtil.stringToCollect(jsonStr);
        Message message = JSONObject.parseObject(JSONObject.toJSONString(map.get("xml")), Message.class);
        return message;
    }


    /*
     * 校验参数
     *
     * @param param
     * @return
     */
    private boolean checkParam(GetParam param) {
        List<String> list = Arrays.asList(param.getTimestamp(), param.getNonce(), token);
        list.sort(String::compareTo);
        String result = list.stream().collect(Collectors.joining(""));
        result = EncryptUtil.getSha1(result);
        return result.equals(param.getSignature());
    }


    /**
     * 更新菜单测试
     *
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public JsapiTicket test() throws Exception {
        //AccessToken accessToken = apiService.fetchAccessToken();
        menuService.getMenu();
        JsapiTicket jsapiTicket = apiService.fetchJsapiTicket();
        return jsapiTicket;
    }


    /**
     * js-sdk测试
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/list.do")
    public String index(HttpServletRequest request, ModelMap map) {
        map.putAll(apiService.fetchJsSdk(request.getRequestURL().toString()));
        return "/search.html";
    }

}
