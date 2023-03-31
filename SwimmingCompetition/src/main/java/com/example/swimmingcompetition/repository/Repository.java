package com.example.swimmingcompetition.repository;

import com.example.swimmingcompetition.domain.Entity;

import java.util.Vector;

public interface Repository <ID, E extends Entity<ID>>{

    E findOne(ID id);
    Vector<E> findAll();
    void add(E entity);
    void delete(ID id);
}
