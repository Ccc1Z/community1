package cn.cqu.ccc1z.community.model;

import lombok.Data;
/**
 * Created by Ccc1Z on 2020/03/23
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String account_id;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
    private String avatarURL;

}
