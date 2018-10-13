package net.bgsystems.util.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T> {
	T findById(Serializable id) throws Exception;
	List<T> findAll() throws Exception;
	void save(T entity) throws Exception;
	T update(T entity) throws Exception;
}
