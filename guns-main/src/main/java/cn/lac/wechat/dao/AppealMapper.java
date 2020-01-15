package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Appeal;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface AppealMapper extends BaseMapper<Appeal> {
    List<Appeal> selectByVo(QueryVo vo);

    int countByVo(QueryVo vo);

    Appeal findById(String appealId);
}
