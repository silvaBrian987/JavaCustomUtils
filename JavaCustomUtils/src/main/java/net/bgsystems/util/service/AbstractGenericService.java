package net.bgsystems.util.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bgsystems.util.dao.AbstractGenericDao;
import net.bgsystems.util.dao.IGenericDao;
import net.bgsystems.util.service.IGenericService;

public abstract class AbstractGenericService<T> implements IGenericService<T> {

	protected IGenericDao<T> dao;

//	public void setEntityClass(Class<T> entityClass) throws Exception {
//		((AbstractGenericDao<T>) ((Advised) dao).getTargetSource().getTarget()).setEntityClass(entityClass);
//	}

	@Override
	public T findById(Serializable id) throws Exception {
		return dao.findById(id);
	}

	@Override
	public List<T> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public void save(T entity) throws Exception {
		validate(entity);
		dao.save(entity);
	}

	@Override
	public T update(T entity) throws Exception {
		validate(entity);
		return dao.update(entity);
	}

	@Override
	public void validate(T entity) throws Exception {
		if (entity == null)
			throw new NullPointerException("La entidad es nula");
	}
}
