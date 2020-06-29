package in.gagan.base.framework.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.model.AbstractBaseModel;

/**
 * The Base DAO for all the DAO classes. This class provides the commonly used CRUD methods. 
 * 
 * @author gaganthind
 *
 * @param <E>
 * @param <K>
 */
@Repository
public abstract class AbstractBaseDAO<E extends AbstractBaseModel, K extends Serializable> implements BaseDAO<E, K> {
	
	private static final String ITERAL_FROM = "from ";

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * Persistence class. This variable store the model object class
	 */
	private Class<E> persistentClass;

	/**
	 * AbstractBaseDAO Constructor
	 */
	@SuppressWarnings("unchecked")
	public AbstractBaseDAO() {
		this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Get the persistence class that called this DAO
	 */
	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<E> findAll() {
		return entityManager.createQuery(ITERAL_FROM + getPersistentClass().getSimpleName()).getResultList();
	}
	
	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none
	 *         found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}.
	 */
	@Override
	public E findById(K id) {
		return entityManager.find(getPersistentClass(), id);
	}
	
	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists,
	 *         {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@code id} is {@literal null}.
	 */
	@Override
	public boolean existsById(K id) {
		return null != findById(id);
	}

	/**
	 * Returns all instances of the type with the given IDs.
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public Iterable<E> findAllById(Iterable<K> ids) {
		return entityManager.unwrap(Session.class).byMultipleIds(getPersistentClass()).multiLoad((List<K>) ids);
	}

	/**
	 * @return the number of entities
	 */
	@Override
	public long count() {
		Collection<E> entities = (Collection<E>) findAll();
		return CollectionUtils.isEmpty(entities) ? 0 : entities.size();
	}
	
	/**
	 * Refresh the instance
	 * 
	 * @param <S>
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity will never be {@literal null}.
	 */
	public <S extends E> void refresh(S entity) {
		entityManager.refresh(entity);
	}
	
	/**
	 * Save the instance.
	 * 
	 * @param <S>
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity will never be {@literal null}.
	 */
	@Override
	public <S extends E> void save(S entity) {
		entityManager.persist(entity);
	}

	/**
	 * Saves all given entities.
	 *
	 * @param entities must not be {@literal null}.
	 * @return the saved entities will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	@Override
	public <S extends E> void saveAll(Iterable<S> entities) {
		Iterator<S> iterator = entities.iterator();
		while(iterator.hasNext()) {
			save(iterator.next());
		}
	}

	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is
	 *                                  {@literal null}
	 */
	@Override
	public void hardDeleteById(K id) {
		E entity = findById(id);
		if (null != entity) {
			hardDelete(entity);
		}
	}

	/**
	 * Deletes a given entity.
	 *
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	@Override
	public void hardDelete(E entity) {
		entityManager.remove(entity);
		entityManager.flush();
	}

	/**
	 * Deletes the given entities.
	 *
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is
	 *                                  {@literal null}.
	 */
	@Override
	public void hardDeleteAll(Iterable<E> entities) {
		Iterator<E> iterator = entities.iterator();
		while(iterator.hasNext()) {
			hardDelete(iterator.next());
		}
	}

	/**
	 * Deletes all entities managed by the repository.
	 */
	@Override
	public void hardDeleteAll() {
		Iterable<E> entities = findAll();
		if (null != entities) {
			hardDeleteAll(entities);
		}
	}
	
	/**
	 * Mark the active SW as N for this id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
	 */
	@Override
	public void deleteById(K id) {
		E entity = findById(id);
		if (null != entity) {
			delete(entity);
		}
	}

	/**
	 * Mark the active SW as N for this given entity.
	 *
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	@Override
	public void delete(E entity) {
		entity.setActiveSw(ApplicationConstants.CHAR_N);
		save(entity);
	}

	/**
	 * Mark the active SW as N for these entities.
	 *
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
	 */
	@Override
	public void deleteAll(Iterable<E> entities) {
		Iterator<E> iterator = entities.iterator();
		while(iterator.hasNext()) {
			delete(iterator.next());
		}
	}

	/**
	 * Mark the active SW as N for entities managed by the repository.
	 */
	@Override
	public void deleteAll() {
		Iterable<E> entities = findAll();
		if (null != entities) {
			deleteAll(entities);
		}
	}
	
}
