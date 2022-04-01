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

package in.gagan.base.framework.constant;

/**
 * Persistence constants
 * 
 * @author gaganthind
 *
 */
public final class PersistenceConstants {
	
	private PersistenceConstants() { }
	
	public static final String HIBERNATE_CONF_DIALECT = "hibernate.dialect";
	public static final String HIBERNATE_CONF_SHOW_SQL = "hibernate.show_sql";
	public static final String HIBERNATE_CONF_FORMAT_SQL = "hibernate.format_sql";
	public static final String HIBERNATE_CONF_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	public static final String HIBERNATE_CONF_CONNECTION_POOL_SIZE = "hibernate.connection.pool_size";
	public static final String HIBERNATE_CONF_CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";
	public static final String HIBERNATE_CONF_CACHE_PROVIDER_CLASS = "hibernate.cache.provider_class";
	
	public static final String BASE_ENTITY_PATH = "in.gagan.base.framework.entity";
	public static final String APPLICATION_ENTITY_PATH = "in.gagan.base.framework.model";

}
