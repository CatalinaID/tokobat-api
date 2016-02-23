package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Apotek;
import com.catalina.tokobat.entity.Transaction;
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
 * Created by Alifa on 2/7/2016.
 */
public class ApotekDaoImpl extends JpaDaoSupport implements ApotekDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Apotek getApotekById(Long id) {
        return getJpaTemplate().find(Apotek.class, id);
    }

    @Override
    public Apotek findByUsername(String username) {

        try{
            Query query = em.createQuery(
                    "SELECT c FROM Apotek c WHERE c.username = :usr");
            query.setParameter("usr", username);
            Apotek c = (Apotek)query.getSingleResult();
            return c;
        } catch(NoResultException e) {
            return null;
        }


    }

    @Override
    @Transactional(readOnly=false)
    public Apotek add(Apotek apotek) {

        try {
            em = EntityManagerFactoryUtils.getTransactionalEntityManager(getJpaTemplate().getEntityManagerFactory());
            em.merge(apotek);
            em.flush();
            em.close();

            return apotek;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly=false)
    public Apotek update(Apotek apotek) {

        em = EntityManagerFactoryUtils.getTransactionalEntityManager(getJpaTemplate().getEntityManagerFactory());
        em.merge(apotek);
        em.flush();
        em.close();

        return apotek;
    }

    @Override
    public List<Apotek> listAll() {
        return getJpaTemplate().find("select u from Apotek u");
    }
}
