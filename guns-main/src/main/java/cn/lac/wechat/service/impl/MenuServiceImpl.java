package cn.lac.wechat.service.impl;


import cn.lac.wechat.service.ApiService;
import cn.lac.wechat.service.MenuService;
import cn.lac.wechat.utils.HttpClientUtil;
import cn.lac.wechat.wx.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Value("${getMenu}")
    private String getMenuUrl;
    @Value("${delMenu}")
    private String delMenuUrl;
    @Value("${url}")
    private String url;
    @Autowired
    private ApiService apiService;


    /**
     * 更改菜单
     */
    @Override
    public void getMenu() throws Exception {
        delMenu();
        FileChannel inChannel = FileChannel.open(Paths.get(this.getClass().getClassLoader().getResource("json/menu.json").toURI()), StandardOpenOption.READ);
        ByteBuffer buf = ByteBuffer.allocate(4096);
        inChannel.read(buf);
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        buf.clear();
        inChannel.close();
        String val = new String(dst);
        val = String.format(val, url);
        log.info("{}", val);
        String result = HttpClientUtil.sendJsonStr(getMenuUrl, val);
        //log.info(result);
    }

    /**
     * 删除菜单
     */
    private void delMenu() {
        AccessToken accessToken = apiService.fetchAccessToken();
        getMenuUrl = String.format(getMenuUrl, accessToken.getAccess_token());
        delMenuUrl = String.format(delMenuUrl, accessToken.getAccess_token());
        String result = HttpClientUtil.doGet(delMenuUrl, null);
        //log.info(result);
    }


}
