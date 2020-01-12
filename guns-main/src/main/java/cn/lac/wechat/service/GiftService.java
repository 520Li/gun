package cn.lac.wechat.service;


import cn.lac.wechat.domain.Gift;
import cn.lac.wechat.wx.Result;

import java.util.List;
import java.util.Map;

public interface GiftService {


    List<Gift> findGift();

    Result insertGiftLog(List<Map> gifts);
}
