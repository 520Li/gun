package cn.lac.wechat.service;

import cn.lac.wechat.domain.Appeal;
import cn.lac.wechat.domain.AppealLog;
import cn.lac.wechat.vo.LayerVo;
import cn.lac.wechat.vo.QueryVo;

/**
 * ClassName: AppealService <br/>
 *
 * @author lac
 * @version 1.0
 * @date 2020/1/14 0014 - 22:20
 */
public interface AppealService {
    LayerVo findAppealByVo(QueryVo vo);

    Appeal findAppealById(String appealId);

    void updateStatus(Appeal appeal);

    void insertLog(AppealLog appealLog);

}
