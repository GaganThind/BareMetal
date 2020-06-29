package in.gagan.base.framework.dao;

/**
 * Base interface for all DAO classes
 * 
 * @author gaganthind
 *
 * @param <E>
 * @param <K>
 */
public interface BaseDAO<E, K> {
	
	/**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}.
	 */
	E findById(K id);

	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@code id} is {@literal null}.
	 */
	boolean existsById(K id);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	Iterable<E> findAll();

	/**
	 * Returns all instances of the type with the given IDs.
	 *
	 * @param ids
	 * @return
	 */
	Iterable<E> findAllById(Iterable<K> ids);

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities
	 */
	long count();
	
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
	 * @return the saved entities will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	<S extends E> void saveAll(Iterable<S> entities);
	
	/**
	 * Mark the active SW as N for this id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
	 */
	void deleteById(K id);

	/**
	 * Mark the active SW as N for this given entity.
	 *
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	void delete(E entity);

	/**
	 * Mark the active SW as N for these entities.
	 *
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
	 */
	void deleteAll(Iterable<E> entities);

	/**
	 * Mark the active SW as N for entities managed by the repository.
	 */
	void deleteAll();
	
	/**
	 * Deletes the entity with the given id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
	 */
	void hardDeleteById(K id);

	/**
	 * Deletes a given entity.
	 *
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	void hardDelete(E entity);

	/**
	 * Deletes the given entities.
	 *
	 * @param entities
	 * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
	 */
	void hardDeleteAll(Iterable<E> entities);

	/**
	 * Deletes all entities managed by the repository.
	 */
	void hardDeleteAll();

}
