package ivan.personal.dao;

import ivan.personal.entity.User;

public interface UserDaoInterface {
    User selectUserByUsername(String username);
}
