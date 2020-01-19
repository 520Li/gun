package cn.lac.wechat.service;

import cn.lac.wechat.domain.Benefit;
import cn.lac.wechat.domain.Gift;
import cn.lac.wechat.domain.GiftLog;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;

import java.util.List;

public interface BenefitService {
    LayerVo findBenefitByVo(QueryVo vo);

    LayerVo findLogByVo(QueryVo vo);

    LayerVo findGiftByVo(QueryVo vo);

    Gift findGiftById(String giftId);

    void deleteGiftById(String giftId);

    Benefit findBenefitById(String btId);

    void updateBenefitState(Benefit benefit);

    void updateGiftLogState(GiftLog giftLog);

    List<GiftLog> findBenefitByBatchId(String zglBatchId);

    void insertGift(Gift gift);

    void editGift(Gift gift);

}
