package com.catalina.tokobat.dao;

import com.catalina.tokobat.entity.Apotek;

/**
 * Created by Alifa on 2/7/2016.
 */
public interface ApotekDao {
    Apotek findByUsername(String apotek);
    Apotek add(Apotek apotek);
    Apotek getApotekById(Long id);
}
