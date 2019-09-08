package com.lgx.community.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2019/9/7 19:56
 */

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer offset;
    private Integer size;
}
