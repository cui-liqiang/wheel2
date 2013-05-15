package com.thoughtworks.orm.core;

import java.sql.SQLException;
import java.util.List;

public interface IDB {
    public void save(Object obj, Object... belongsTo) throws Exception;
    public void delete(Object obj) throws Exception;
    public <T> int count(Class<T> clazz) throws SQLException;
    public <T> T find(Class<T> clazz, int id) throws Exception;
    public <T> List<T> findAll(Class<T> clazz, String criteria) throws Exception;
}
