package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.BenefitMapper;
import cn.lac.wechat.dao.GiftLogMapper;
import cn.lac.wechat.dao.GiftMapper;
import cn.lac.wechat.domain.Benefit;
import cn.lac.wechat.domain.Gift;
import cn.lac.wechat.domain.GiftLog;
import cn.lac.wechat.service.BenefitService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class BenefitServiceImpl implements BenefitService {


    @Autowired
    private BenefitMapper benefitMapper;
    @Autowired
    private GiftLogMapper giftLogMapper;
    @Autowired
    private GiftMapper giftMapper;

    /**
     * 公益审核列表
     *
     * @param vo
     * @return
     */
    @Override
    public LayerVo findBenefitByVo(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getLimit(), vo.getPage())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<Benefit> list = benefitMapper.findByVo(vo);
        int count = benefitMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    /**
     * 兑换记录列表
     *
     * @param vo
     * @return
     */
    @Override
    public LayerVo findLogByVo(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getLimit(), vo.getPage())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<GiftLog> list = giftLogMapper.findByVo(vo);
        int count = giftLogMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    /**
     * 兑换品列表
     *
     * @param vo
     * @return
     */
    @Override
    public LayerVo findGiftByVo(QueryVo vo) {
        if (!ToolUtil.isAllEmpty(vo.getLimit(), vo.getPage())) {
            vo.setPage((vo.getPage() - 1) * vo.getLimit());
        }
        List<Gift> list = giftMapper.findByVo(vo);
        int count = giftMapper.countByVo(vo);
        return new LayerVo(count, list);
    }

    /**
     * 查询单个兑换品
     *
     * @param giftId
     * @return
     */
    @Override
    public Gift findGiftById(String giftId) {
        return giftMapper.selectById(giftId);
    }

    /**
     * 删除兑换品
     *
     * @param giftId
     */
    @Override
    public void deleteGiftById(String giftId) {
        giftMapper.deleteById(giftId);
    }

    /**
     * 公益详情
     *
     * @param btId
     * @return
     */
    @Override
    public Benefit findBenefitById(String btId) {
        return benefitMapper.selectById(btId);
    }

    /**
     * 更改公益审核状态
     *
     * @param benefit
     */
    @Override
    public void updateBenefitState(Benefit benefit) {
        benefitMapper.updateById(benefit);
    }

    /**
     * 更改兑换记录审核状态
     *
     * @param giftLog
     */
    @Override
    public void updateGiftLogState(GiftLog giftLog) {
        giftLogMapper.updateById(giftLog);
    }


    /**
     * 兑换批次编号查询兑换记录
     *
     * @param zglBatchId
     * @return
     */
    @Override
    public List<GiftLog> findBenefitByBatchId(String zglBatchId) {
        QueryWrapper<GiftLog> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(GiftLog::getZglBatchId, zglBatchId);
        return giftLogMapper.selectList(wrapper);
    }

    /**
     * 新增兑换品
     */
    @Override
    public void insertGift(Gift gift) {
        gift.setCreateTime(new Date());
        giftMapper.insert(gift);
    }

    /**
     * 编辑兑换品
     */
    @Override
    public void editGift(Gift gift) {
        gift.setUpdateTime(new Date());
        giftMapper.updateById(gift);
    }

}
