package net.bgsystems.util.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.bgsystems.util.dao.IGenericDao;

public abstract class AbstractGenericDao<T> implements IGenericDao<T> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> entityClass;

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public T findById(Serializable id) throws Exception {
		return em.find(entityClass, id);
	}

	@Override
	public List<T> findAll() throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> rootEntry = cq.from(entityClass);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	@Transactional
	public void save(T entity) throws Exception {
		em.persist(entity);
	}

	@Override
	@Transactional
	public T update(T entity) throws Exception {
		return em.merge(entity);
	}
}
