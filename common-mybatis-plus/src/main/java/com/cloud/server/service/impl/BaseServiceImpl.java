package com.cloud.server.service.impl;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;
import com.cloud.server.base.BaseQueryVo;
import com.cloud.server.base.BaseSystemVo;
import com.cloud.server.base.PageQueryUtils;
import com.cloud.server.bean.PageEntity;
import com.cloud.server.service.BaseService;

import cn.hutool.core.util.IdUtil;

public class BaseServiceImpl<U  extends BaseMapper<T >,T extends BaseSystemVo>extends ServiceImpl<U, T> implements
        BaseService<T> {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(T t){
        t.setId(IdUtil.objectId());
        return super.insert(t);
    }
    @Override
	public boolean insert(T t, Boolean resetId) {
		if(resetId) {
			 t.setId(IdUtil.objectId());
		}
		return super.insert(t);
	}
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(List<T> entityList) {
        entityList.forEach(t->{t.setId(IdUtil.objectId());});
        return insertBatch(entityList, 30);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertOrUpdateBatch(List<T> entityList) {
        entityList.forEach(t->{
            if(org.apache.commons.lang.StringUtils.isBlank(t.getId())){
                t.setId(IdUtil.objectId());
            }
        });
        return insertOrUpdateBatch(entityList, 30);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertOrUpdate(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && org.apache.commons.lang.StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    return insert(entity);
                } else {
                    /*
                     * 更新成功直接返回，失败执行插入逻辑
                     */
                    return updateById(entity) || insert(entity);
                }
            } else {
                throw new MybatisPlusException("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }
    /**
     * 根据传入的vo对象查询实体
     * @param entity
     * @param <M>
     * @return
     */
    @Override
    public <M> List<T> queryByVo(M entity){
        Class<M> aClass =  (Class<M>)entity.getClass();
        Map<String, Object> fieldValueMap = getFieldValueMapByVo(entity);
        List<T> ts = this.selectByMap(fieldValueMap);
        return ts;
    }

    @Override
    public PageEntity<T> queryPage(BaseQueryVo<T> queryVo) {
        Wrapper<T> wrapper= PageQueryUtils.getWrapper(queryVo);
        Page<T> tPage = selectPage(new Page<T>(queryVo.getPageObj().getCurrent(), queryVo.getPageObj().getSize()), wrapper);
        return new PageEntity<T>(tPage);
    }


    /**
     * 根据实体查询
     * @param  t
     * @return
     */
    @Override
    public  List<T> queryByEntity(T t){
        Map<String, Object> fieldValueMap = this.getFieldValueMap(t);
        List<T> ts = this.selectByMap(fieldValueMap);
        return ts;
    }
    public  <M> Map<String,Object> getFieldValueMapByVo(M t) {
        // key是属性名，value是对应值
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Class<M> aClass =  (Class<M>) t.getClass();
        // 获取当前加载的实体类中所有属性（字段）
        Field[] fields = aClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String key = f.getName();// 属性名
            Object value = null;//属性值
            if (! "serialVersionUID".equals(key)) {// 忽略序列化版本ID号

                f.setAccessible(true);// 取消Java语言访问检查
                try {
                    value =f.get(t);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(value!=null){
                    if(value instanceof  String ){
                            if(org.apache.commons.lang.StringUtils.isBlank((String)value)){
                                    continue;
                            }
                    }
                    key=StringUtils.camelToUnderline(key);
                    fieldValueMap.put(key, value);
                }

            }
        }
        return fieldValueMap;
    }
    /**
     * 获取指定实例的所有属性名及对应值的Map实例
     * @param t
     * @return 字段名及对应值的Map实例
     */
    private Map<String, Object> getFieldValueMap(T t) {
        // key是属性名，value是对应值
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Class<T> aClass =  (Class<T>) t.getClass();
        // 获取当前加载的实体类中所有属性（字段）
        Field[] fields = aClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            String key = f.getName();// 属性名
            Object value = null;//属性值
            if (! "serialVersionUID".equals(key)) {// 忽略序列化版本ID号

                f.setAccessible(true);// 取消Java语言访问检查
                try {
                    value =f.get(t);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(value!=null){
                    key=StringUtils.camelToUnderline(key);
                    fieldValueMap.put(key, value);
                }

            }
        }
        return fieldValueMap;
    }
}
