package com.cloud.server.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.cloud.server.base.BaseQueryVo;
import com.cloud.server.base.BaseSystemVo;
import com.cloud.server.bean.PageEntity;

public interface BaseService<T extends BaseSystemVo> extends IService<T> {
    /**
     * 实体类查询
     * @param t
     * @return
     */
    public  List<T> queryByEntity(T t);
    public <M> List<T> queryByVo(M entity);
    public boolean insert(T t, Boolean resetId);
    /**
     * 分页查询
     * @param queryVo
     * @return
     */
    PageEntity<T> queryPage(BaseQueryVo<T> queryVo);
}
