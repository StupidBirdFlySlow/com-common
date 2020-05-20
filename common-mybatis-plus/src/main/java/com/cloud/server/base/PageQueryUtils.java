package com.cloud.server.base;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.cloud.server.common.exception.BusinessRuntimeException;

public class PageQueryUtils {
    public static <T> Wrapper<T> getWrapper(BaseQueryVo<T> queryVo){
        if(queryVo.getPageObj()==null){
            queryVo.setPageObj(new PageObj());
        }
        queryVo.setPageObj(new PageObj(queryVo.getPageObj().getCurrent()==null?1:queryVo.getPageObj().getCurrent(),queryVo.getPageObj().getSize()==null?100000:queryVo.getPageObj().getSize()));
        Wrapper  wrapper =  new EntityWrapper<T>();
        if(queryVo.getFields()!=null && queryVo.getFields().size()>0){
            wrapper.setSqlSelect(queryVo.getFields().stream().map(one->{return new Column().column(StringUtils.camelToUnderline(one)).as(one);
            }).collect(Collectors.toList()).toArray(new Column[0]));
        }
        if(queryVo.getNotNullFields()!=null && queryVo.getNotNullFields().size()>0){
            queryVo.getNotNullFields().forEach(notNullField->wrapper.isNotNull(StringUtils.camelToUnderline
                    (notNullField)));
        }
        if(queryVo.getNullFields()!=null && queryVo.getNullFields().size()>0){
            queryVo.getNullFields().forEach(nullField->wrapper.isNull(StringUtils.camelToUnderline
                    (nullField)));
        }
        if( queryVo.getAndCondition()!=null&& queryVo.getAndCondition().size()>0){
            Map<String, Object> andCondition = queryVo.getAndCondition();
            Object projectId = andCondition.get("projectId");
            wrapper.allEq(andCondition.entrySet().stream().collect(Collectors.toMap(entry->
                      StringUtils.camelToUnderline(entry.getKey()),entry -> {return entry.getValue()==null?"":entry
                      .getValue();})));
        }
        if( queryVo.getInCondition()!=null&& queryVo.getInCondition().size()>0){
            queryVo.getInCondition() .entrySet().stream().forEach(one -> {wrapper.in(StringUtils.camelToUnderline(one.getKey()),one.getValue());});
        }
        if( queryVo.getLikeCondition()!=null&& queryVo.getLikeCondition().size()>0){
            Map<String, Object> likeCondition = queryVo.getLikeCondition();
            Object projectId = likeCondition.get("projectId");
            if(projectId!=null){
                throw new BusinessRuntimeException("like 条件不能传项目");
            }
            queryVo.getLikeCondition().entrySet().stream().forEach(one -> {wrapper.like(StringUtils.camelToUnderline(one.getKey()),one.getValue().toString());});
        }
        if( queryVo.getGtCondition()!=null&& queryVo.getGtCondition().size()>0){
            Map<String, Object> andCondition = queryVo.getGtCondition();
            Object projectId = andCondition.get("projectId");
            if(projectId!=null){
                throw new BusinessRuntimeException("gt 条件不能传项目");
            }
            queryVo.getGtCondition().entrySet().stream().forEach(one -> {
                if(!one.getValue().toString().equals("")){
                    wrapper.gt(StringUtils.camelToUnderline(one.getKey()),one.getValue().toString());}
                }

                );
        }
        if( queryVo.getLtCondition()!=null&& queryVo.getLtCondition().size()>0){
            Map<String, Object> andCondition = queryVo.getLtCondition();
            Object projectId = andCondition.get("projectId");
            if(projectId!=null){
                throw new BusinessRuntimeException("lt 条件不能传项目");
            }
            queryVo.getLtCondition().entrySet().stream().forEach(one -> {
                        if (!one.getValue().toString().equals("")) {
                            wrapper.lt(StringUtils.camelToUnderline(one.getKey()), one.getValue().toString());
                        }
                    }
                );
        }
        if( queryVo.getOrderByCondition()!=null&& queryVo.getOrderByCondition().size()>0){
            queryVo.getOrderByCondition().entrySet().stream().forEach(one -> {wrapper.orderBy(StringUtils.camelToUnderline(one.getKey()),one.getValue().toString().toLowerCase().equals("asc"));});
        }else {
            Map<String,Object> orderCondition=new HashMap<>();
            orderCondition.put("create_time","asc");
        }
     /*   if( queryVo.getOrderByConditionStr()!=null&& queryVo.getOrderByConditionStr().size()>0){
            queryVo.getOrderByConditionStr().entrySet().stream().forEach(one -> {wrapper.orderBy(one.getKey(),one.getValue().toString().toLowerCase().equals("asc"));});
        }*/

        return wrapper;
    }
}
