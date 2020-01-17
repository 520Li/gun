package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Gift;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface GiftMapper extends BaseMapper<Gift> {
    List<Gift> findByVo(QueryVo vo);

    int countByVo(QueryVo vo);
}
