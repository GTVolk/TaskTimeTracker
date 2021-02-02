package ru.devvault.tttracker.dao;

public interface GenericDao<T, ID> {

    T find(ID id);
    void persist(T obj);
    T merge(T obj);
    void remove(T obj);
}
