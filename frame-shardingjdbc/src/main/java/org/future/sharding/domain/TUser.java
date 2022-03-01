package org.future.sharding.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author: zhpj
 * @date: 2022-03-01 16:44
 */
@Data
@TableName("t_user")
public class TUser {

    @TableId
    @Null(groups = InsertValid.class)
    private Long id;

    @NotNull(groups = InsertValid.class, message = "cName字段不能为空")
    private String cName;

//    @TableField(exist = false)
    private Integer part;

    public interface InsertValid {}

}
