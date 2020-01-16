package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.HomeMapper;
import cn.lac.wechat.domain.Home;
import cn.lac.wechat.service.HomeService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;


    @Override
    public List<Home> getHomeList() {
        QueryWrapper<Home> wrapper = new QueryWrapper<>();
        return homeMapper.selectList(wrapper);
    }

    @Override
    public LayerVo findList(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<Home> list = homeMapper.selectByVo(vo);
        int count = homeMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    @Override
    public Home findById(String homeId) {
        return homeMapper.findById(homeId);
    }

    @Override
    public void insert(Home home) {
        home.setCreateTime(new Date());
        homeMapper.insert(home);
    }

    @Override
    public void edit(Home home) {
        home.setUpdateTime(new Date());
        homeMapper.updateById(home);
    }

    @Override
    public void deleteById(String homeId) {
        homeMapper.deleteById(homeId);
    }
}
