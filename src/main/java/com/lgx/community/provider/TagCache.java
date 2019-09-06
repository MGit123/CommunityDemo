package com.lgx.community.provider;

import com.lgx.community.dto.TagDTO;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2019/9/4 17:21
 */
public  class  TagCache {

    public static List<TagDTO> get(){

        List<TagDTO> tagDTOS=new ArrayList<>();

        TagDTO program=new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Java","C++","C","PHP","js","C#","python","node.js","javascript","css","html","html5"));
        tagDTOS.add(program);

        TagDTO framework=new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","springMVC","springboot","express","django","flask","springColud","springsecurity","koa","css"));
        tagDTOS.add(framework);

        TagDTO server=new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Windows 2010","ubuntu","Windows 2007","Windows 2003","Windows XP","Linux","Unix","MacOx","IOS","android","Centos7","Red Hat"));
        tagDTOS.add(server);

        TagDTO database=new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("H2","MySql","hibernate","spring data jpa","Mybatis","sqlServer","oracle"));
        tagDTOS.add(database);

        TagDTO tool=new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("Myeclipse","eclipse","IDIE","","dev c++","visio studio","Maven","github","Maven"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public  static String filterIsValid(String tags){
       String[] split= StringUtils.split(tags,",");
       List<TagDTO> tagDTOS=get();

       List<String> tagList=tagDTOS.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
       String invalid=Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));

       return invalid;
    }
}
