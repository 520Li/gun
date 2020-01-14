package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Appoint;
import cn.lac.wechat.service.UserService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 线上服务预约 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/11 0011 - 12:39
 */
@Controller
@Slf4j
@RequestMapping("/admin/appoint")
public class AppointController {

    @Autowired
    private UserService userService;


    /**
     * 跳转预约管理页面
     */
    @GetMapping("")
    public String admin() {
        return "/admin/appoint/appoint.html";
    }

    /**
     * 获取预约列表
     */
    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return userService.findAppoint(vo);
    }

    /**
     * 跳转预约详细页面
     */
    @GetMapping("/appoint_dis")
    public String appointDis(String appointId, ModelMap map) {
        map.put("appoint", userService.selectAppoint(appointId));
        return "/admin/appoint/appoint_dis.html";
    }

    /**
     * 跳转预约详细页面
     */
    @PostMapping("/apply")
    public Result apply(String status, Appoint appoint) {
        appoint.setApplyStatus(status);
        userService.applyAppoint(appoint);
        return new Result(true, "处理成功！");
    }

}
