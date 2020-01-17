package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Volunteer;
import cn.lac.wechat.vo.QueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VolunteerMapper extends BaseMapper<Volunteer> {

    Volunteer selectByVoId(@Param("voId") String voId, @Param("userId") String userId);

    List<Volunteer> selectByVo(QueryVo vo);

    int countByVo(QueryVo vo);

}
