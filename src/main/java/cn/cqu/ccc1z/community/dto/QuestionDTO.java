package cn.cqu.ccc1z.community.dto;

import cn.cqu.ccc1z.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    //通过creator关联到user的creator 拿到头像
    private User user;
}
