package com.cloud.server.base;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 条件封装
 * @author wu
 * @date 2018/08/24
 * @Description:
 */
@ApiModel(value = "通用分页查询Vo")
@Data
public class BaseQueryVo<T> {

    @ApiModelProperty(value = "分页对象,currentPage,size,totalPage,totalElements")
    private PageObj pageObj;
    @ApiModelProperty(value = "大于等于查询条件,传对应map,key为字段名,value为字段值")
    private Map<String, Object> gtCondition;
    @ApiModelProperty(value = "小于等于查询条件,传对应map,key为字段名,value为字段值")
    private Map<String, Object> ltCondition;
    @ApiModelProperty(value = "and查询条件,传对应map,key为字段名,value为字段值")
    private Map<String, Object> andCondition;
    @ApiModelProperty(value = "like等于查询条件,传对应map,key为字段名,value为字段值")
    private Map<String, Object> likeCondition;
    @ApiModelProperty(value = "字段不为空的字段")
    private  List<String >notNullFields;
    @ApiModelProperty(value = "字段为空的字段")
    private  List<String >nullFields;
    @ApiModelProperty(value = "排序查询条件,传对应map,key为字段名,value为字段值(asc,desc)")
    private Map<String, Object> orderByCondition;
    private Map<String, List<Object>> inCondition;
    private Map<String,Object> otherCondition;
    private List<String> fields;

}