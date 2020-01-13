package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Article;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * ClassName: ArticleMapper <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/4 0004 - 17:02
 */
public interface ArticleMapper extends BaseMapper<Article> {


    List<Article> selectListByVo(QueryVo vo);

    Integer countListByVo(QueryVo vo);
}
