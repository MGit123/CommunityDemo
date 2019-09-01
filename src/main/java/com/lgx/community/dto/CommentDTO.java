package com.lgx.community.dto;

import com.lgx.community.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/9/1 21:14
 */

@Setter
@Getter
public class CommentDTO {

    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentor;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private User user;
}
