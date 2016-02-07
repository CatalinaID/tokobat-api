package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Apotek;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Alifa on 2/7/2016.
 */
public class ApotekDaoImpl extends JpaDaoSupport implements ApotekDao {

    @PersistenceContext
    private EntityManager em;

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
}
