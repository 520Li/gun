package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Home;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface HomeMapper extends BaseMapper<Home> {

    List<Home> selectByVo(QueryVo vo);

    int countByVo(QueryVo vo);

    Home findById(String homeId);
}
