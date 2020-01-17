package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Benefit;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface BenefitMapper extends BaseMapper<Benefit> {
    List<Benefit> findByVo(QueryVo vo);

    int countByVo(QueryVo vo);
}
