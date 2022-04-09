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
import java.util.Optional;

import in.gagan.base.framework.entity.base.BaseEntity;

/**
 * Base interface for all DAO classes
 * 
 * @author gaganthind
 *
 * @param <E> - Entity
 * @param <K> - Key
 */
public interface BaseDAO<E extends BaseEntity, K extends Serializable> {

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	Optional<Iterable<E>> findAll();

	/**
	 * Refresh the instance
	 *
	 * @param <S> Entity object to be refreshed from database
	 *
	 * @param entity must not be {@literal null}.
	 */
	<S extends E> void refresh(S entity);
	
	/**
	 * Saves a given entity.
	 *
	 * @param entity must not be {@literal null}.
	 */
	<S extends E> void save(S entity);
	
	/**
	 * Saves all given entities.
	 *
	 * @param entities must not be {@literal null}.
	 */
	<S extends E> void saveAll(Iterable<S> entities);

	/**
	 * Mark the active SW as N for this given entity.
	 *
	 * @param entity Entity object for deletion from database
	 */
	void delete(E entity);

	/**
	 * Mark the active SW as N for these entities.
	 *
	 * @param entities Entity objects for deletion from database
	 */
	void deleteAll(Iterable<E> entities);

	/**
	 * Deletes a given entity.
	 *
	 * @param entity Entity object for permanent deletion from database
	 */
	void hardDelete(E entity);

	/**
	 * Deletes the given entities.
	 *
	 * @param entities Entity objects for permanent deletion from database
	 */
	void hardDeleteAll(Iterable<E> entities);

}
