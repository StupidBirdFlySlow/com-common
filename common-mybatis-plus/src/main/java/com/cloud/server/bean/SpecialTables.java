package com.cloud.server.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "mybatis-plus.configuration")
@Data
public class SpecialTables {
    /**
     * 没有项目id的表
     */
    private List<String> tenantTables;
    /**
     * 存在项目id，但是项目id 为0
     */
    private List<String>systemTables;
}
