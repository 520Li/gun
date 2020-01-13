package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.ArticleMapper;
import cn.lac.wechat.domain.Article;
import cn.lac.wechat.enums.ArticleType;
import cn.lac.wechat.service.ArticleService;
import cn.lac.wechat.utils.Util;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.PageResult;
import cn.lac.wechat.vo.QueryVo;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.sys.modular.system.entity.Dept;
import cn.stylefeng.guns.sys.modular.system.mapper.DeptMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

import static java.awt.SystemColor.menu;

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
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 获取文章列表
     */
    @Override
    public LayerVo getList(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        int count = articleMapper.countListByVo(vo);
        List<Article> articles = articleMapper.selectListByVo(vo);
        return new LayerVo(count, articles);
    }

    /**
     * 获取文章详细
     */
    @Override
    public Article getById(String arId) {
        return articleMapper.selectById(arId);
    }

    /**
     * 删除文章
     */
    @Override
    public void deleteById(String arId) {
        articleMapper.deleteById(arId);
    }

    @Override
    public void insert(Article article) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long deptId = user.getDeptId();
        Dept dept = deptMapper.selectById(deptId);
        if (null != dept) {
            String name = dept.getSimpleName();
            article.setArOrg(name);
        }
        article.setCreateTime(new Date());
        articleMapper.insert(article);
    }

    @Override
    public void edit(Article article) {
        article.setUpdateTime(new Date());
        articleMapper.updateById(article);
    }


}
