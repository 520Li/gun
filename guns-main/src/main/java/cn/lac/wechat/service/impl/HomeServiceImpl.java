package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.HomeMapper;
import cn.lac.wechat.domain.Home;
import cn.lac.wechat.service.HomeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;


    @Override
    public List<Home> getHomeList() {
        QueryWrapper<Home> wrapper = new QueryWrapper<>();
        return homeMapper.selectList(wrapper);
    }
}
