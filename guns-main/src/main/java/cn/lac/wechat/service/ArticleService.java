package cn.lac.wechat.service;

import cn.lac.wechat.domain.Article;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.PageResult;
import cn.lac.wechat.vo.QueryVo;

import java.util.List;

/**
 * ClassName: ArticleService <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 16:57
 */
public interface ArticleService {

    /**
     * 获取文章列表
     */
    LayerVo getList(QueryVo vo);

    /**
     * 文章详细
     */
    Article getById(String arId);

    /**
     * 删除文章
     */
    void deleteById(String arId);

    /**
     * 发布文章
     */
    void insert(Article article);

    /**
     * 编辑文章
     */
    void edit(Article article);

}
