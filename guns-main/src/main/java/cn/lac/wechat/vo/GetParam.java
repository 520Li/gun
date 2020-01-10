package cn.lac.wechat.vo;


import lombok.Data;

@Data
public class GetParam {

    /*
    signature:   [d25f8e7c1a65839306591b2c14c3a8bd5192039a]
    echostr:   [3504928018447202270]
    timestamp:   [1577669790]
    nonce:   [5352968]
     */
    private String signature;
    private String echostr;
    private String timestamp;
    private String nonce;
    private String openid;//微信用户id

}
