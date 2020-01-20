package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Appeal;
import cn.lac.wechat.service.AppealService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 事件举报 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/14 0014 - 22:11
 */
@Controller
@Slf4j
@RequestMapping("/admin/appeal")
public class AppealController {

    @Autowired
    private AppealService appealService;

    /**
     * 跳转事件举报列表页面
     *
     * @return
     */
    @GetMapping("")
    public String appeal() {
        return "/admin/appeal/appeal.html";
    }

    /**
     * 事件举报列表
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return appealService.findAppealByVo(vo);
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public Result updateStatus(Appeal appeal) {
        appealService.updateStatus(appeal);
        return new Result(true, "操作成功！");
    }


    /**
     * 跳转事件详情页面
     */
    @GetMapping("/appeal_dis")
    public String appealDis(String appealId, ModelMap map) {
        map.put("appeal", appealService.findAppealById(appealId));
        return "/admin/appeal/appeal_dis.html";
    }


}
