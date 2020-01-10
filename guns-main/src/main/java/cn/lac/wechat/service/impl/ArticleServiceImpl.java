package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.ArticleMapper;
import cn.lac.wechat.domain.Article;
import cn.lac.wechat.enums.ArticleType;
import cn.lac.wechat.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: ArticleServiceImpl <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 17:01
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    private final static String MENU1 = "menu_01"; //咱。社区
    private final static String MENU2 = "menu_02";//办事指南
    private final static String MENU3 = "menu_03";//电子阅览


    /**
     * 根据菜单类别获取文章列表
     *
     * @param menu
     * @return
     */
    @Override
    public List<Article> getList(String menu) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (menu.equals(MENU1)) {
            wrapper.lambda().in(Article::getArType,
                    Arrays.asList(ArticleType.TZTG.toString(), ArticleType.SQJS.toString(), ArticleType.SQFC.toString()));
        } else if (menu.equals(MENU2)) {
            wrapper.lambda().in(Article::getArType,
                    Arrays.asList(ArticleType.YLYX.toString(), ArticleType.LNR.toString(),
                            ArticleType.JS.toString(), ArticleType.JZZ.toString(), ArticleType.CJR.toString()));
        } else if (menu.equals(MENU3)) {
            wrapper.lambda().eq(Article::getArType, ArticleType.DZYL.toString());
        }
        return articleMapper.selectList(wrapper);
    }

    @Override
    public Article getById(String arId) {
        return articleMapper.selectById(arId);
    }
}
