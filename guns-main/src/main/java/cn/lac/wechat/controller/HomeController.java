package cn.lac.wechat.controller;


import cn.lac.wechat.domain.Article;
import cn.lac.wechat.domain.Home;
import cn.lac.wechat.service.HomeService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 便利生活一点通
 */
@Controller
@Slf4j
@RequestMapping("/admin/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("")
    public String home() {
        return "/admin/home/home.html";
    }


    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return homeService.findList(vo);
    }


    /**
     * 跳转资源新增
     */
    @GetMapping("/home_add")
    public String index_add() {
        return "/admin/home/home_add.html";
    }

    /**
     * 跳转资源编辑
     */
    @GetMapping("/home_edit")
    public String index_edit(String homeId, ModelMap map) {
        map.put("home", homeService.findById(homeId));
        return "/admin/home/home_edit.html";
    }


    /**
     * 新增资源
     */
    @PostMapping("/add")
    @ResponseBody
    public Result indexAdd(Home home) {
        homeService.insert(home);
        return new Result(true, "发布成功！");
    }

    /**
     * 编辑资源
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result indexEdit(Home home) {
        homeService.edit(home);
        return new Result(true, "修改成功！");
    }

    /**
     * 删除资源
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result indexRemove(String homeId) {
        homeService.deleteById(homeId);
        return new Result(true, "删除成功！");
    }

    /**
     * 地图页面
     */
    @GetMapping("/map")
    public String toMap(String local, ModelMap map) {
        map.put("local", local);
        return "/common/map.html";
    }


}
