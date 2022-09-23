/*
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.entity.base.BaseEntity;

/**
 * The Base DAO for all the DAO classes. This class provides the commonly used CRUD methods. 
 * 
 * @author gaganthind
 *
 * @param <E> - Entity
 * @param <K> - Key
 */
@Repository
public abstract class AbstractBaseDAO<E extends BaseEntity, K extends Serializable> implements BaseDAO<E, K> {
	
	protected static final String LITERAL_SELECT = "SELECT ";
	protected static final String LITERAL_DELETE = "DELETE ";
	protected static final String LITERAL_UPDATE = "UPDATE ";
	protected static final String LITERAL_DISTINCT = "DISTINCT ";
	protected static final String LITERAL_FROM = "FROM ";
	protected static final String LITERAL_WHERE = " WHERE ";
	protected static final String LITERAL_AND = " AND ";

	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * Persistence class. This variable store the model object class
	 */
	private final Class<E> persistentClass;
	
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
	protected Class<E> getPersistentClass() {
		return persistentClass;
	}
	
	/**
	 * Method used to get the table name.
	 * 
	 * @return String - table name
	 */
	protected String getTableName() {
		return getPersistentClass().getSimpleName();
	}

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	@Override
	public Optional<Iterable<E>> findAll() {
		TypedQuery<E> query = entityManager.createQuery(LITERAL_FROM + getPersistentClass().getSimpleName(), getPersistentClass());
		return Optional.ofNullable(query.getResultList());
	}
	
	/**
	 * Refresh the instance
	 * 
	 * @param <S> Entity object to be refreshed from database
	 *
	 * @param entity must not be {@literal null}.
	 */
	@Override
	public <S extends E> void refresh(S entity) {
		entityManager.refresh(entity);
	}
	
	/**
	 * Save the instance.
	 * 
	 * @param <S> Entity object to be saved from database
	 *
	 * @param entity must not be {@literal null}.
	 */
	@Override
	public <S extends E> void save(S entity) {
		entityManager.persist(entity);
	}

	/**
	 * Saves all given entities.
	 *
	 * @param entities must not be {@literal null}.
	 */
	@Override
	public <S extends E> void saveAll(Iterable<S> entities) {
		for (S entity : entities) {
			save(entity);
		}
	}

	/**
	 * Deletes a given entity.
	 *
	 * @param entity Entity object for permanent deletion from database
	 */
	@Override
	public void hardDelete(E entity) {
		entityManager.remove(entity);
		entityManager.flush();
	}

	/**
	 * Deletes the given entities.
	 *
	 * @param entities Entity objects for permanent deletion from database
	 */
	@Override
	public void hardDeleteAll(Iterable<E> entities) {
		for (E entity : entities) {
			hardDelete(entity);
		}
	}

	/**
	 * Mark the active SW as N for this given entity.
	 *
	 * @param entity Entity object for deletion from database
	 */
	@Override
	public void delete(E entity) {
		entity.setActiveSw(ApplicationConstants.CHAR_N);
		save(entity);
	}

	/**
	 * Mark the active SW as N for these entities.
	 *
	 * @param entities Entity objects for deletion from database
	 */
	@Override
	public void deleteAll(Iterable<E> entities) {
		for (E entity : entities) {
			delete(entity);
		}
	}
	
}
