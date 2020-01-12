package cn.lac.wechat.service.impl;

import cn.lac.wechat.dao.GiftLogMapper;
import cn.lac.wechat.dao.GiftMapper;
import cn.lac.wechat.dao.WxUserMapper;
import cn.lac.wechat.domain.Gift;
import cn.lac.wechat.domain.GiftLog;
import cn.lac.wechat.domain.User;
import cn.lac.wechat.service.GiftService;
import cn.lac.wechat.wx.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class GiftServiceImpl implements GiftService {

    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private WxUserMapper userMapper;
    @Autowired
    private GiftLogMapper giftLogMapper;

    @Override
    public List<Gift> findGift() {
        QueryWrapper<Gift> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Gift::getGiftState, "01").orderByDesc(Gift::getCreateTime);
        return giftMapper.selectList(wrapper);
    }

    @Override
    public Result insertGiftLog(List<Map> gifts) {
        User user = (User) session.getAttribute("login_user");
        Integer total = gifts.stream().map((in) -> Integer.parseInt(in.get("sum").toString())).reduce(0, (a, b) -> a + b);
        if (total > user.getUserCore()) {
            return new Result(false, "积分不足");
        }

        gifts.forEach((g) -> {
            String id = (String) g.get("id");
            int num = Integer.parseInt(g.get("num").toString());
            if (num == 0) {
                return;
            }
            int sum = Integer.parseInt(g.get("sum").toString());

            String batchId = UUID.randomUUID().toString().replaceAll("-", "");

            GiftLog giftLog = GiftLog.builder().zglBatchId(batchId)
                    .zglCore(sum)
                    .zglNum(num)
                    .zglUserId(user.getUserId())
                    .zglGiftId(id)
                    .createTime(new Date())
                    .build();
            //新增兑换记录
            giftLogMapper.insert(giftLog);

            //更改用户公益积分
            user.setUserCore(user.getUserCore() - sum);

            //更新兑换品库存
            Gift gift = giftMapper.selectById(id);
            gift.setGiftNum(gift.getGiftNum() - num);
            giftMapper.updateById(gift);
        });
        userMapper.updateById(user);
        return new Result(true, "提交成功");
    }
}
