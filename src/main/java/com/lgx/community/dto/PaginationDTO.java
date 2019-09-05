package com.lgx.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2019/8/26 10:16
 */

@Setter
@Getter
public class PaginationDTO <T>{

    private List<T> data;
    private boolean showPrevious;  //是否展示前一页
    private boolean showFirstPage;  //展示第一页
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer page, Integer totalPage){

        this.page=page;
       /* if(totalCount%size==0){     //计算总共的页数
            totalPage=totalCount/size;
        }else{
            totalPage=(totalCount/size)+1;
        }*/

        pages.add(page);
        for(int i=1;i<=3;i++){      //添加页码信息
            if(page-i>0){
                pages.add(0,page-i);
            }

            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示上一页
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }

        //是否展示下一页
        if(page>=totalPage){
           showNext=false;
        }else{
            showNext=true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
