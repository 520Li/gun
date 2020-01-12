package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Article;
import cn.lac.wechat.service.ArticleService;
import cn.lac.wechat.vo.PageResult;
import cn.lac.wechat.vo.QueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公众号后台 <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/11 0011 - 12:39
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转文章管理页面
     */
    @GetMapping("/index")
    public String admin() {
        return "/admin/index/index.html";
    }

    /**
     * 获取文章列表
     */
    @GetMapping("/index/list")
    @ResponseBody
    public PageResult<Article> list(QueryVo vo) {
        return articleService.getList(vo);
    }

    /**
     * 跳转文章新增
     */
    @GetMapping("/index/index_add")
    public String index_add() {
        return "/admin/index/index_add.html";
    }

    /**
     * 跳转文章编辑
     */
    @GetMapping("/index/index_edit")
    public String index_edit() {
        return "/admin/index/index_edit.html";
    }


    /**
     * 新增文章
     */
    @PostMapping("/index/add")
    @ResponseBody
    public Object indexAdd() {
        return null;
    }
    /**
     * 删除文章
     */
    @PostMapping("/index/remove")
    @ResponseBody
    public Object indexRemove() {
        return null;
    }
}
