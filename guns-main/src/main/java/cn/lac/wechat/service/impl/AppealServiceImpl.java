package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.AppealLogMapper;
import cn.lac.wechat.dao.AppealMapper;
import cn.lac.wechat.domain.Appeal;
import cn.lac.wechat.domain.AppealLog;
import cn.lac.wechat.service.AppealService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.roses.core.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName: AppealServiceImpl <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/14 0014 - 22:20
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AppealServiceImpl implements AppealService {
    @Autowired
    private AppealMapper appealMapper;
    @Autowired
    private AppealLogMapper appealLogMapper;

    @Override
    public LayerVo findAppealByVo(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getPage(), vo.getLimit())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<Appeal> list = appealMapper.selectByVo(vo);
        int count = appealMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    @Override
    public Appeal findAppealById(String appealId) {
        return appealMapper.findById(appealId);
    }

    @Override
    public void updateStatus(Appeal appeal) {
        appeal.setUpdateTime(new Date());
        appealMapper.updateById(appeal);
    }


    @Override
    public void insertLog(AppealLog appealLog) {
        appealLog.setCreateTime(new Date());
        appealLogMapper.insert(appealLog);
        Appeal appeal = appealMapper.selectById(appealLog.getAppealId());
        appeal.setAppealStatus(appealLog.getAcceptorStatus());
        appeal.setUpdateTime(new Date());
        appealMapper.updateById(appeal);
    }
}
