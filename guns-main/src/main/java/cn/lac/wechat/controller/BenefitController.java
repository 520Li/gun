package cn.lac.wechat.controller;

import cn.lac.wechat.domain.Benefit;
import cn.lac.wechat.domain.GiftLog;
import cn.lac.wechat.service.BenefitService;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;
import cn.lac.wechat.wx.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公益积分
 */

@Controller
@Slf4j
@RequestMapping("/admin/benefit")
public class BenefitController {


    @Autowired
    private BenefitService benefitService;

    @GetMapping("")
    public String benefit() {
        return "/admin/benefit/benefit.html";
    }

    /**
     * 公益审核列表
     *
     * @param vo
     * @return
     */
    @GetMapping("/benefit_list")
    @ResponseBody
    public LayerVo benefitList(QueryVo vo) {
        return benefitService.findBenefitByVo(vo);
    }

    /**
     * 兑换记录列表
     *
     * @param vo
     * @return
     */
    @GetMapping("/log_list")
    @ResponseBody
    public LayerVo logList(QueryVo vo) {
        return benefitService.findLogByVo(vo);
    }

    /**
     * 兑换品列表
     *
     * @param vo
     * @return
     */
    @GetMapping("/gift_list")
    @ResponseBody
    public LayerVo giftList(QueryVo vo) {
        return benefitService.findGiftByVo(vo);
    }

    /**
     * 跳转新增兑换品
     *
     * @return
     */
    @GetMapping("/gift_add")
    public String giftAdd() {
        return "/admin/benefit/gift_add.html";
    }

    /**
     * 跳转编辑兑换品
     *
     * @return
     */
    @GetMapping("/gift_edit")
    public String giftEdit(String giftId, ModelMap map) {
        map.put("gift", benefitService.findGiftById(giftId));
        return "/admin/benefit/gift_edit.html";
    }

    /**
     * 删除兑换品
     *
     * @return
     */
    @PostMapping("/gift_delete")
    public Result giftDelete(String giftId) {
        benefitService.deleteGiftById(giftId);
        return new Result(true, "删除成功！");
    }


    /**
     * 跳转公益详情
     *
     * @return
     */
    @GetMapping("/benefit_dis")
    public String benefit_dis(String btId, ModelMap map) {
        map.put("benefit", benefitService.findBenefitById(btId));
        return "/admin/benefit/benefit_dis.html";
    }

    /**
     * 公益审核
     *
     * @return
     */
    @PostMapping("/benefit_apply")
    public Result benefit_apply(Benefit benefit) {
        benefitService.updateBenefitState(benefit);
        return new Result(true, "操作成功！");
    }

    /**
     * 跳转兑换记录详情
     *
     * @return
     */
    @GetMapping("/log_dis")
    public String log_dis(String zglBatchId, ModelMap map) {
        map.put("giftLog", benefitService.findBenefitByBatchId(zglBatchId));
        return "/admin/benefit/log_dis.html";
    }

    /**
     * 兑换审核
     *
     * @return
     */
    @PostMapping("/log_apply")
    public Result log_apply(GiftLog giftLog) {
        benefitService.updateGiftLogState(giftLog);
        return new Result(true, "操作成功！");
    }


}
