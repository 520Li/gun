package cn.lac.wechat.vo;

import lombok.Data;

@Data
public class JsapiTicket {

    /*
    {
  "errcode":0,
  "errmsg":"ok",
  "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
  "expires_in":7200
    }
     */

    private String ticket;
    private Long expires_in;
}
