package com.lgx.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/8/30 20:56
 */

@Setter
@Getter
public class CommentDTO {
    private Integer parentId;
    private String content;
    private Integer type;
}
