package org.future.sharding.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: zhpj
 * @date: 2022-03-01 16:44
 */
@Data
@TableName("t_user")
public class TUser {

    @TableId
    private String cId;

    private String cName;

}
