package cn.cqu.ccc1z.community.dto;

import lombok.Data;

/**
 * Created by Ccc1Z on 2019/03/22
 *
 * Some data transfer objects used to accesstoken
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
