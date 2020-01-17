package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Article;
import cn.lac.wechat.domain.Volunteer;
import cn.lac.wechat.service.ArticleService;
import cn.lac.wechat.service.VolunteerService;
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
 * 志愿者活动
 */
@Controller
@Slf4j
@RequestMapping("/admin/volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    /**
     * 跳转志愿者活动管理页面
     */
    @GetMapping("")
    public String admin() {
        return "/admin/volunteer/volunteer.html";
    }

    /**
     * 获取文志愿者活动列表
     */
    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return volunteerService.getList(vo);
    }

    /**
     * 跳转志愿者活动新增
     */
    @GetMapping("/volunteer_add")
    public String index_add() {
        return "/admin/volunteer/volunteer_add.html";
    }

    /**
     * 跳转志愿者活动编辑
     */
    @GetMapping("/volunteer_edit")
    public String index_edit(String voId, ModelMap map) {
        map.put("volunteer", volunteerService.getById(voId));
        return "/admin/volunteer/volunteer_edit.html";
    }


    /**
     * 新增志愿者活动
     */
    @PostMapping("/add")
    @ResponseBody
    public Result indexAdd(Volunteer volunteer) {
        volunteerService.insert(volunteer);
        return new Result(true, "发布成功！");
    }

    /**
     * 编辑志愿者活动
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result indexEdit(Volunteer volunteer) {
        volunteerService.edit(volunteer);
        return new Result(true, "修改成功！");
    }

    /**
     * 删除志愿者活动
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result indexRemove(String voId) {
        volunteerService.deleteById(voId);
        return new Result(true, "删除成功！");
    }

    /**
     * 志愿者列表
     */
    @GetMapping("/vo_list")
    @ResponseBody
    public LayerVo voList(QueryVo vo, String voId) {
        return volunteerService.userList(vo, voId);
    }

    /**
     * 跳转志愿者列表
     */
    @GetMapping("/volunteer_user")
    public String volunteerUser(String voId, ModelMap map) {
        map.put("voId", voId);
        return "/admin/volunteer/volunteer_user.html";
    }


}
