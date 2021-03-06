package cn.lac.wechat.dao;

import cn.lac.wechat.domain.GiftLog;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface GiftLogMapper extends BaseMapper<GiftLog> {
    List<GiftLog> findByVo(QueryVo vo);

    int countByVo(QueryVo vo);

}
