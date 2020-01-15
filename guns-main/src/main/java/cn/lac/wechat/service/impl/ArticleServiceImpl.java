package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.ArticleMapper;
import cn.lac.wechat.domain.Article;
import cn.lac.wechat.enums.ArticleType;
import cn.lac.wechat.service.ArticleService;
import cn.lac.wechat.template.WangEditor;
import cn.lac.wechat.utils.Util;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.PageResult;
import cn.lac.wechat.vo.QueryVo;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.sys.modular.system.entity.Dept;
import cn.stylefeng.guns.sys.modular.system.mapper.DeptMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
@Slf4j
@Transactional(rollbackFor = Exception.class)
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
        article.setArUser(user.getId() + "");


        checkHasImg(article);

        articleMapper.insert(article);
    }

    @Override
    public void edit(Article article) {
        article.setUpdateTime(new Date());
        checkHasImg(article);
        article.setArText(article.getArText().trim());
        article.setArTextJson(article.getArTextJson().trim());
        articleMapper.updateById(article);
    }


    /**
     * 新增电子书
     */
    @Override
    public void insertPdf(Article article) {
        LoginUser user = LoginContextHolder.getContext().getUser();
        Long deptId = user.getDeptId();
        Dept dept = deptMapper.selectById(deptId);
        if (null != dept) {
            String name = dept.getSimpleName();
            article.setArOrg(name);
        }
        article.setCreateTime(new Date());
        article.setArUser(user.getId() + "");
        article.setArType("DZYL");
        if (article.getPdfType().equals("01")) {
            createPdf(article);
        } else {
            article.setArText("");
        }
        articleMapper.insert(article);
    }

    @Override
    public void editPdf(Article article) {
        article.setUpdateTime(new Date());
        article.setArText(article.getArText().trim());
        if (article.getPdfType().equals("01")) {
            createPdf(article);
        } else {
            article.setArText("");
        }
        articleMapper.updateById(article);
    }

    /**
     * html 生成 pdf
     */
    public void createPdf(Article article) {
        String arText = article.getArText();
        arText = arText.replaceAll("<br>", "").replaceAll("(<img((?!>).)*>)", "$1</img>");
        //pdf路径

        String arPath = article.getArPath();

        if (StringUtils.isBlank(arPath)) {
            arPath = "/images/pdf/" + UUID.randomUUID().toString().replace("-", "").toLowerCase() + ".pdf";
        }

        String filePath = this.getClass().getClassLoader().getResource("").toString().replace("file:/", "") + "/static" + arPath;


        Document document = new Document();

        try {


            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }


            PdfWriter writer = PdfWriter.getInstance(document, new BufferedOutputStream(new FileOutputStream(file)));
            document.open();
            String content = "<html><body style=\"font-family: 宋体, SimHei;\">" + arText + "</body></html>";
            byte b[] = content.getBytes("utf-8");
            ByteArrayInputStream bais = new ByteArrayInputStream(b);


            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bais, Charset.forName("UTF-8"));
            bais.close();


            article.setArPath(arPath);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    /**
     * 判断文章是否是图文 & 获取第一张图片路径
     */
    private void checkHasImg(Article article) {
        String json = article.getArTextJson();
        List<Map> list = JSONArray.parseArray(json, Map.class);
        for (Map map : list) {
            if (null != map.get("children")) {
                List children = (List) map.get("children");
                for (Object m : children) {
                    if (m instanceof Map) {
                        Map m2 = (Map) m;
                        if (m2.get("tag").equals("img")) {
                            List<Map> attr = (List<Map>) m2.get("attrs");
                            for (Map n : attr) {
                                if (n.get("name").equals("src")) {
                                    article.setArIspath("02");
                                    article.setArFirstpath((String) n.get("value"));
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        article.setArIspath("01");
        article.setArFirstpath("");

    }

}
