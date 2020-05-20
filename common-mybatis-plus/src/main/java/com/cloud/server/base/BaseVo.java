package com.cloud.server.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;

import lombok.Data;

@Data
public class BaseVo extends BaseSystemVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableField(value = "project_id", fill = FieldFill.INSERT)
	private Integer projectId;
}
