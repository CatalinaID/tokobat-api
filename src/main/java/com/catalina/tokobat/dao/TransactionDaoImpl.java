/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Transaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.catalina.tokobat.entity.User;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author icha
 */
public class TransactionDaoImpl extends JpaDaoSupport implements TransactionDao {

    @PersistenceContext
    private EntityManager em;    
    
    @Override
    public Transaction getTransactionById(long id) {
        return getJpaTemplate().find(Transaction.class, id);
    }

    @Override
    @Transactional(readOnly=false)
    public Transaction updateTransaction(Transaction trans) {
        em = EntityManagerFactoryUtils.getTransactionalEntityManager(getJpaTemplate().getEntityManagerFactory());
        em.merge(trans);
        em.flush();
        em.close();

        return trans;
    }

    @Override
    public List<Transaction> listTransactionsByApotek(long apotekId) {
        return getJpaTemplate().find(
                "from Transaction u where u.apotek.id=?", apotekId);
    }
    
}

