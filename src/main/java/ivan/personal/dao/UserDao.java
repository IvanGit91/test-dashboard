package ivan.personal.dao;

import ivan.personal.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Qualifier("hibernateUserDao")
@Transactional
@RequiredArgsConstructor
public class UserDao implements UserDaoInterface {

    private final HibernateTemplate hibernateTemplate;

    @Override
    public User selectUserByUsername(String username) {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("username", username));
        List<User> res = (List<User>) hibernateTemplate.findByCriteria(criteria);
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }
}
