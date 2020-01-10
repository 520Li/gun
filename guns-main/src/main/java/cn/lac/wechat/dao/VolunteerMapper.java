package cn.lac.wechat.dao;

import cn.lac.wechat.domain.Volunteer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface VolunteerMapper extends BaseMapper<Volunteer> {

    Volunteer selectByVoId(@Param("voId") String voId, @Param("userId") String userId);

}
