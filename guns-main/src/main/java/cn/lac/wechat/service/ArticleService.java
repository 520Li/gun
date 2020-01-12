package cn.lac.wechat.service;

import cn.lac.wechat.domain.Article;
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
    PageResult<Article> getList(QueryVo vo);

    /**
     * 文章详细
     */
    Article getById(String arId);
}
