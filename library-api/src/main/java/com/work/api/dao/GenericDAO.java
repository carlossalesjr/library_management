package com.work.api.dao;

import java.util.List;

public interface GenericDAO<T> {
    void save(T entity);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
}