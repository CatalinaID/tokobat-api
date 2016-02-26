package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Apotek;
import com.catalina.tokobat.entity.User;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        user = em.merge(user);
        em.flush();
        em.close();

        return user;
    }

    @Override
    @Transactional(readOnly=false)
    public User updateUser(User user) {

        em = EntityManagerFactoryUtils.getTransactionalEntityManager(getJpaTemplate().getEntityManagerFactory());
        user = em.merge(user);
        em.flush();
        em.close();

        return user;
    }

    @Override
    public User findByUsername(String username) {
        try{
            Query query = em.createQuery(
                    "SELECT c FROM User c WHERE c.username = :usr");
            query.setParameter("usr", username);
            User user = (User) query.getSingleResult();
            return user;
        } catch(NoResultException e) {
            return null;
        }
    }
}
