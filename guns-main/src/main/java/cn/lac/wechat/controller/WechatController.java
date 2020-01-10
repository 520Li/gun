package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Appeal;
import cn.lac.wechat.domain.Appoint;
import cn.lac.wechat.domain.User;
import cn.lac.wechat.service.*;
import cn.lac.wechat.utils.SendMessageUtil;
import cn.lac.wechat.vo.Message;
import cn.lac.wechat.vo.Result;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private GiftService giftService;
    @Autowired
    private HomeService homeService;
    @Autowired
    private VolunteerService volunteerService;


    /**
     * 实名注册页面
     *
     * @return
     */
    @GetMapping(value = "/login")
    public String login(HttpServletRequest request) {
        return "/system/signup.html";
    }

    /**
     * 实名注册
     *
     * @param user
     * @return
     */
    @PostMapping("/signup.do")
    @ResponseBody
    public Result signup(User user, HttpSession session) {
        if (null == session.getAttribute("openid")) {
            return new Result(false, "该页面已失效！");
        }
        if (StringUtils.isBlank(user.getUserId())) {
            log.error("{}", "openid为空，认证失败");
            return new Result(false, "认证失败!");
        }
        boolean flag = userService.checkIphone(user);
        if (!flag) {
            return new Result(false, "该手机号已实名认证!");
        }
        userService.insertUser(user);
        session.removeAttribute("openid");
        session.removeAttribute("url");
        session.setAttribute("login_user", user);
        return new Result(true, "认证成功！");
    }

    /**
     * 图片上传
     *
     * @param request
     * @return
     */
    @PostMapping("/file.do")
    @ResponseBody
    public Result upload(String path, HttpSession session, HttpServletRequest request) {
        /*if (null == session.getAttribute("openid")) {
            return new Result(false, "该页面已失效！");
        }*/

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        FileChannel outChannel = null;
        try {
            String oldName = file.getOriginalFilename();
            String suffix = oldName.substring(oldName.lastIndexOf("."));
            if (!suffix.equalsIgnoreCase(".jpg") && !suffix.equalsIgnoreCase(".png") && !suffix.equalsIgnoreCase(".gif") && !suffix.equalsIgnoreCase(".jpeg")) {
                return new Result(false, "只能上传格式为 .jpg /.png / .jpeg / .gif 的图片");
            }
            //后缀名
            String newName = UUID.randomUUID().toString().replace("-", "").toLowerCase() + suffix;
            outChannel = FileChannel.open(Paths.get(this.getClass().getClassLoader().getResource("").toString().replace("file:/", "") + "/static/images/" + path + "/" + newName),
                    StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer buf = ByteBuffer.allocate((int) file.getSize());
            buf.put(file.getBytes());
            buf.flip();
            outChannel.write(buf);
            buf.clear();

            // 数据库需要保存：相对路径
            String relativePath = "/images/" + path + "/" + newName;
            return new Result(true, "上传成功！", relativePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Result(false, "上传图片失败");
        } finally {
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 发送短信验证码
     *
     * @param mobile
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/sendSms.do")
    @ResponseBody
    public Result sendSms(String mobile, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (null == session.getAttribute("openid")) {
            return new Result(false, "该页面已失效！");
        }
        if (StringUtils.isBlank(mobile)) {
            return new Result(false, "手机号不能为空！");
        }

        String code = SendMessageUtil.getSixNum();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        Message message = new Message(mobile, "紫萝园", "SMS_181851483", jsonObject.toJSONString());
        try {
            SendSmsResponse sendSmsResponse = SendMessageUtil.sendSms(message);
            if ("OK".equalsIgnoreCase(sendSmsResponse.getCode())) {
                return new Result(true, "短信发送成功！", code);
            } else {
                log.error("短信发送失败原因：{}", sendSmsResponse.getMessage());
                return new Result(true, "短信发送失败！");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new Result(true, "短信发送失败！");
        }


    }

    /**
     * 预约界面
     *
     * @param request
     * @return
     */
    @GetMapping("/second/appointment.do")
    public String appointment(HttpServletRequest request) {
        return "/menu_02/appointment.html";
    }

    /**
     * 新增预约
     *
     * @param appoint
     * @return
     */
    @PostMapping("/second/newAppoint.do")
    @ResponseBody
    public Result newAppoint(Appoint appoint) {
        userService.saveAppoint(appoint);
        return new Result(true, "预约成功！");
    }

    /**
     * 查询用户预约列表
     *
     * @return
     */
    @PostMapping("/second/initAppoint.do")
    @ResponseBody
    public Result initAppoint() {
        return new Result(true, "查询成功", userService.findAppointByUser());
    }

    /**
     * 咱。社区
     *
     * @return
     */
    @GetMapping("/first/index.do")
    public String index(HttpServletRequest request) {
        return "/menu_01/index.html";
    }

    /**
     * 文章详细页面
     *
     * @param arId
     * @return
     */
    @GetMapping("/first/detail.do")
    public String articleDetail(String arId, ModelMap map) {
        map.put("article", articleService.getById(arId));
        return "/menu_01/article.html";
    }


    /**
     * 获取文章列表
     *
     * @param type
     * @return
     */
    @PostMapping("/first/article.do")
    @ResponseBody
    public Result article(String type) {
        return new Result(true, "查询成功！", articleService.getList(type));
    }


    /**
     * 办事指南
     *
     * @return
     */
    @GetMapping("/second/guide.do")
    public String guide() {
        return "/menu_02/guide.html";
    }

    /**
     * 线上活动
     *
     * @return
     */
    @GetMapping("/second/report.do")
    public String toReport() {
        return "/menu_02/report.html";
    }

    /**
     * 获取线上活动列表
     *
     * @return
     */
    @PostMapping("/second/eventList.do")
    @ResponseBody
    public Result event(String query) {
        return new Result(true, "查询成功！", userService.getEventList(query));
    }


    /**
     * 活动详细
     */
    @GetMapping("/second/eventDis.do")
    public String eventDis(String eventId, ModelMap map) {
        map.put("event", userService.getEventDis(eventId));
        return "/menu_02/event.html";
    }

    /**
     * 用户报名活动
     */
    @PostMapping("/second/report.do")
    @ResponseBody
    public Result report(String eventId) {
        return userService.reportEvent(eventId);
    }


    /**
     * 社区接诉即办
     */
    @GetMapping("/third/appeal.do")
    public String appeal() {
        return "/menu_03/appeal.html";
    }


    /**
     * 用户事件举报
     *
     * @return
     */
    @PostMapping("/third/appeal.do")
    @ResponseBody
    public Result appealByUser(Appeal appeal) {
        userService.appealByUser(appeal);
        return new Result(true, "提交成功！");
    }

    /**
     * 根据用户查举报列表
     *
     * @return
     */
    @PostMapping("/third/appealList.do")
    @ResponseBody
    public Result appealList() {
        return new Result(true, "查询成功！", userService.getAppealByUser());
    }


    /**
     * 公益积分
     */
    @GetMapping("/third/benefit.do")
    public String benefit(ModelMap map) {
        map.put("gifts", giftService.findGift());
        return "/menu_03/benefit.html";
    }

    /**
     * 兑换积分
     */
    @PostMapping("/third/exchange.do")
    @ResponseBody
    public Result exchange(String gift) {
        List<Map> gifts = JSONArray.parseArray(gift, Map.class);
        return giftService.insertGiftLog(gifts);
    }

    /**
     * 一刻钟生活圈
     */
    @GetMapping("/third/home.do")
    public String home() {
        return "/menu_03/home.html";
    }


    /**
     * 获取服务商列表
     */
    @PostMapping("/third/home.do")
    @ResponseBody
    public Result getHomeList() {
        return new Result(true, "查询成功!", homeService.getHomeList());
    }

    /**
     * 电子阅览
     */
    @GetMapping("/third/read.do")
    public String read() {
        return "/menu_03/read.html";
    }

    /**
     * 志愿者招募
     */
    @GetMapping("/third/volunteer.do")
    public String volunteer() {
        return "/menu_03/volunteer.html";
    }

    /**
     * 志愿者招募列表
     */
    @PostMapping("/third/volunteer.do")
    @ResponseBody
    public Result voList() {
        return new Result(true, "查询成功!", volunteerService.getVoList());
    }

    /**
     * 志愿者申请
     */
    @GetMapping("/third/pact.do")
    public String pact(HttpSession session, ModelMap map) {
        User user = (User) session.getAttribute("login_user");
        map.put("user", userService.getUserById(user.getUserId()));
        return "/menu_03/volunteer_pact.html";
    }

    /**
     * 申请/撤销 志愿者
     *
     * @param state
     * @return
     */
    @PostMapping("/third/pact.do")
    @ResponseBody
    public Result packUser(String state) {
        return userService.packVo(state);
    }


    /**
     * 志愿者活动详情页
     *
     * @param map
     * @return
     */
    @GetMapping("/third/detail.do")
    public String detail(String voId, ModelMap map) {
        map.put("vo", volunteerService.details(voId));
        return "/menu_03/volunteer_detail.html";
    }


    /**
     * 报名志愿者活动
     *
     * @param voId
     * @return
     */
    @PostMapping("/third/join.do")
    @ResponseBody
    public Result join(String voId) {
        return volunteerService.joinVo(voId);
    }


}
