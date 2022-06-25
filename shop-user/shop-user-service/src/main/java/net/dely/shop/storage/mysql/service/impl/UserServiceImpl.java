package net.dely.shop.storage.mysql.service.impl;

import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.mapper.UserMapper;
import net.dely.shop.storage.mysql.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dely
 * @since 2022-06-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
