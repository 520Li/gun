package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Article;
import cn.lac.wechat.service.ArticleService;
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
 * 办事指南 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/11 0011 - 12:39
 */
@Controller
@RequestMapping("/admin/office")
@Slf4j
public class OfficeController {

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转文章管理页面
     */
    @GetMapping("")
    public String admin() {
        return "/admin/office/office.html";
    }

    /**
     * 获取文章列表
     */
    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return articleService.getList(vo);
    }

    /**
     * 跳转文章新增
     */
    @GetMapping("/office_add")
    public String index_add() {
        return "/admin/office/office_add.html";
    }

    /**
     * 跳转文章编辑
     */
    @GetMapping("/office_edit")
    public String index_edit(String arId, ModelMap map) {
        map.put("article", articleService.getById(arId));
        return "/admin/office/office_edit.html";
    }


    /**
     * 新增文章
     */
    @PostMapping("/add")
    @ResponseBody
    public Result indexAdd(Article article) {
        articleService.insert(article);
        return new Result(true, "发布成功！");
    }

    /**
     * 编辑文章
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result indexEdit(Article article) {
        articleService.edit(article);
        return new Result(true, "修改成功！");
    }

    /**
     * 删除文章
     */
    @PostMapping("/delete")
    @ResponseBody
    public Object indexRemove(String arId) {
        articleService.deleteById(arId);
        return new Result(true, "删除成功！");
    }
}
