package cn.lac.wechat.wx;


import lombok.Data;

@Data
public class AccessToken {
    /*
   {"access_token":"28_mXrLSY6keuX4UWfjpwvU_gpI-C0eS9WhjSPQHOrdCSUs0W1FtnNu830b3VhkPIH269ql68p07UGPK613urZaM-IUzXMBPaPP7b6vFSiox386YilKxWKznjfiDN02nFYZsFyKS0980uPCiX8fKVZfACASDH",
   "expires_in":7200}
    */
    private String access_token;
    private Long expires_in;
}
