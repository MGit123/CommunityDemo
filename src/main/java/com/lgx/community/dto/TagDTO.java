package com.lgx.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2019/9/4 17:20
 */

@Setter
@Getter
public class TagDTO {

    private String categoryName;
    private List<String> tags;

}
