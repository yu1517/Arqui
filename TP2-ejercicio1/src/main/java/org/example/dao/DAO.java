package org.example.dao;

import java.util.List;

public interface DAO<T> {

    void insert(T t);

    T selectById(int id);

    List<T> selectAll();

    boolean update(T t);

    boolean delete(int id);
}
