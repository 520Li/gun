package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.ArticleMapper;
import cn.lac.wechat.domain.Article;
import cn.lac.wechat.enums.ArticleType;
import cn.lac.wechat.service.ArticleService;
import cn.lac.wechat.utils.Util;
import cn.lac.wechat.vo.PageResult;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.menu;

/**
 * ClassName: ArticleServiceImpl <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 17:01
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    private final static String MENU1 = "menu_01"; //咱。社区
    private final static String MENU2 = "menu_02";//办事指南
    private final static String MENU3 = "menu_03";//电子阅览


    /**
     * 根据菜单类别获取文章列表
     *
     * @param vo
     * @return
     */
    @Override
    public PageResult<Article> getList(QueryVo vo) {
        String menu = Util.toString(vo.getArType());
        String arTitle = Util.toString(vo.getArTitle());
        long limit = vo.getLimit();
        long page = (vo.getPage() - 1) * limit;


        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Article> lambda = wrapper.lambda();
        if (menu.equals(MENU1)) {
            lambda.in(Article::getArType,
                    Arrays.asList(ArticleType.TZTG.toString(), ArticleType.SQJS.toString(), ArticleType.SQFC.toString()));

        } else if (menu.equals(MENU2)) {
            lambda.in(Article::getArType,
                    Arrays.asList(ArticleType.YLYX.toString(), ArticleType.LNR.toString(),
                            ArticleType.JS.toString(), ArticleType.JZZ.toString(), ArticleType.CJR.toString()));
        } else if (menu.equals(MENU3)) {
            lambda.eq(Article::getArType, ArticleType.DZYL.toString());
        }

        if (StringUtils.isNotBlank(arTitle)) {
            lambda.eq(Article::getArTitle, arTitle);
        }

        int count = articleMapper.selectCount(wrapper);
        lambda.last("limit " + page + "," + limit);

        return new PageResult<>(count, articleMapper.selectList(lambda));
    }

    @Override
    public Article getById(String arId) {
        return articleMapper.selectById(arId);
    }


}
