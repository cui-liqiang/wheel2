package com.thoughtworks.orm.core;

import java.sql.SQLException;
import java.util.List;

public class DBProxy implements IDB {
    final IDB db;

    public DBProxy(String name) throws ClassNotFoundException, SQLException {
        db = (IDB) DB.connect(name);
    }

    @Override
    public void save(Object obj, Object... belongsTos) throws Exception {
        db.save(obj, belongsTos);
    }

    @Override
    public void delete(Object obj) throws Exception {
        db.delete(obj);
    }

    @Override
    public <T> int count(Class<T> clazz) throws SQLException {
        return db.count(clazz);
    }

    @Override
    public <T> T find(Class<T> clazz, int id) throws Exception {
        return db.find(clazz, id);
    }

    @Override
    public <T> List<T> findAll(Class<T> clazz, String criteria) throws Exception {
        return db.findAll(clazz, criteria);
    }
}
