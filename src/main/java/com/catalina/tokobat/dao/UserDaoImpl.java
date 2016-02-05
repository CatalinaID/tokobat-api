package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.User;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Alifa on 1/29/2016.
 */
public class UserDaoImpl extends JpaDaoSupport implements UserDao {

    @PersistenceContext
    private EntityManager em;    
    

    @Override
    public List<User> getAllUser() {
        return getJpaTemplate().find("select u from User u");
    }

    @Override
    public User getUserById(Long id) {
        return getJpaTemplate().find(User.class, id);
    }

    @Override
    @Transactional(readOnly=false)
    public User addNewUser(User user) {

        em = EntityManagerFactoryUtils.getTransactionalEntityManager(getJpaTemplate().getEntityManagerFactory());
        em.merge(user);
        em.flush();
        em.close();

        return user;
    }

    @Override
    @Transactional(readOnly=false)
    public User updateUser(User user) {

        em = EntityManagerFactoryUtils.getTransactionalEntityManager(getJpaTemplate().getEntityManagerFactory());
        em.merge(user);
        em.flush();
        em.close();

        return user;
    }

    @Override
    public User getUserByMsisdn(String msisdn) throws Exception {
        List<User> users = getJpaTemplate().find(
                "from User u where u.msisdn=?", msisdn);
        if (users.isEmpty()) {
            String msg = "User with msisdn = " + msisdn + " not found";
            throw new Exception(msg);
        }
        return users.get(0);
    }
}
