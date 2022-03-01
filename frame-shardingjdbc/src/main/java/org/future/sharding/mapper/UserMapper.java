package org.future.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.future.sharding.domain.TUser;

/**
 * @author: zhpj
 * @date: 2022-03-01 16:45
 */
@Mapper
public interface UserMapper extends BaseMapper<TUser> {
}
