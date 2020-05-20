package com.cloud.server.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class BaseSystemVo  implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableField(value="create_time",fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 创建人
     */
    @JsonIgnore
    @TableField(value="creator",fill = FieldFill.INSERT)
    private String creator;
    @TableId(value="id",type=IdType.UUID)
    private String id;
    @TableLogic
    @TableField(value="dr")
    private Integer dr=0;
}
