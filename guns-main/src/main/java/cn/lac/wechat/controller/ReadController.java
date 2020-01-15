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
 * 电子阅览 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/11 0011 - 12:39
 */
@Controller
@Slf4j
@RequestMapping("/admin/read")
public class ReadController {


    @Autowired
    private ArticleService articleService;

    /**
     * 电子阅览页面
     *
     * @return
     */
    @GetMapping("")
    public String read() {
        return "/admin/read/read.html";
    }


    /**
     * 电子书列表
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public LayerVo list(QueryVo vo) {
        return articleService.getList(vo);
    }


    /**
     * 跳转电子书新增
     */
    @GetMapping("/read_add")
    public String read_add() {
        return "/admin/read/read_add.html";
    }

    /**
     * 跳转电子书编辑
     */
    @GetMapping("/read_edit")
    public String read_edit(String arId, ModelMap map) {
        map.put("article", articleService.getById(arId));
        return "/admin/read/read_edit.html";
    }


    /**
     * 新增电子书
     */
    @PostMapping("/add")
    @ResponseBody
    public Result indexAdd(Article article) {
        articleService.insertPdf(article);
        return new Result(true, "新增成功！");
    }

    /**
     * 编辑电子书
     */
    @PostMapping("/edit")
    @ResponseBody
    public Result indexEdit(Article article) {
        articleService.editPdf(article);
        return new Result(true, "修改成功！");
    }

    /**
     * 删除电子书
     */
    @PostMapping("/delete")
    @ResponseBody
    public Object indexRemove(String arId) {
        articleService.deleteById(arId);
        return new Result(true, "删除成功！");
    }

}
