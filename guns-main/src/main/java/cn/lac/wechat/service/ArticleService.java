package cn.lac.wechat.service;

import cn.lac.wechat.domain.Article;

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
     * 根据菜单类别获取文章
     *
     * @param menu
     * @return
     */
    List<Article> getList(String menu);

    Article getById(String arId);
}
