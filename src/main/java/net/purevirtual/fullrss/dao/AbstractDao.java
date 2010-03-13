package net.purevirtual.fullrss.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import com.google.inject.Inject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class AbstractDao<Entity, Key> implements IDao<Entity, Key> {

	protected Class<Entity> entityClass;
	@Inject
	protected EntityManager entityManager;

	public AbstractDao() {
		// FIXME: will it still work with sub sub class?
		// @link http://blog.xebia.com/2009/03/09/jpa-implementation-patterns-data-access-objects/
		final Type thisType = getClass().getGenericSuperclass();
		final Type type;
		if (thisType instanceof ParameterizedType) {
			type = ((ParameterizedType) thisType).getActualTypeArguments()[0];
		} else if (thisType instanceof Class) {
			type = ((ParameterizedType) ((Class) thisType).getGenericSuperclass()).getActualTypeArguments()[0];
		} else {
			throw new IllegalArgumentException("Problem handling type construction for " + getClass());
		}
		if (type instanceof Class) {
			entityClass = (Class<Entity>) type;
		} else if (type instanceof ParameterizedType) {
			entityClass = (Class<Entity>) ((ParameterizedType) type).getRawType();
		} else {
			throw new IllegalArgumentException("Problem determining the class of the generic for " + getClass());
		}
	}

	@Override
	public void persist(Entity entity) {
		if (entityManager.getTransaction().isActive()) {
			entityManager.persist(entity);
			entityManager.flush();
		} else {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.flush();
			entityManager.getTransaction().commit();
		}
	}

	@Override
	public void remove(Entity entity) {
		if (entityManager.getTransaction().isActive()) {
			entityManager.remove(entity);
			entityManager.flush();
		} else {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.flush();
			entityManager.getTransaction().commit();
		}
	}

	@Override
	public Entity findById(Key id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public Entity merge(Entity entity) {
		Entity merged;
		if (entityManager.getTransaction().isActive()) {
			merged = entityManager.merge(entity);
			entityManager.flush();
		} else {
			entityManager.getTransaction().begin();
			merged = entityManager.merge(entity);
			entityManager.flush();
			entityManager.getTransaction().commit();
		}
		return merged;
	}

	public List<Entity> getAll() {

		Query createQuery = entityManager.createQuery("select e from "+entityClass.getName()+" e");
		return (List<Entity>)createQuery.getResultList();
	}
}
