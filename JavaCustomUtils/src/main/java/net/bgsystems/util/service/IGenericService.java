package net.bgsystems.util.service;

import java.io.Serializable;
import java.util.List;

import net.bgsystems.util.dao.IGenericDao;

public interface IGenericService<T> {
	T findById(Serializable id) throws Exception;

	List<T> findAll() throws Exception;

	void save(T entity) throws Exception;

	T update(T entity) throws Exception;

	void validate(T entity) throws Exception;
	
	void setDao(IGenericDao<T> dao);
}
