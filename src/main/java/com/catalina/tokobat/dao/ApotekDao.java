package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Apotek;
import com.catalina.tokobat.entity.Transaction;
import com.catalina.tokobat.entity.User;

import java.util.List;

/**
 * Created by Alifa on 2/7/2016.
 */
public interface ApotekDao {
    Apotek findByUsername(String apotek);
    Apotek add(Apotek apotek);
    Apotek getApotekById(Long id);
    Apotek update(Apotek apotek);
    List<Apotek> listAll();
}
