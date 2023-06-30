package cn.withmes.su.user.business.dao.interfaces;

import cn.withmes.su.user.business.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author leegoo
 * @since 2023-06-26
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}
