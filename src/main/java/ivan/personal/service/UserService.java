package ivan.personal.service;

import ivan.personal.dao.UserDaoInterface;
import ivan.personal.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("prototype")
public class UserService {

    private final UserDaoInterface userDao;

    public UserService(@Qualifier("hibernateUserDao") UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    public User getUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }
}
