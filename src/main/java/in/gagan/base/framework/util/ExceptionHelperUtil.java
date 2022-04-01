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

package in.gagan.base.framework.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Utility class for providing exception related helper methods.
 * 
 * @author gaganthind
 *
 */
public final class ExceptionHelperUtil {
	
	private ExceptionHelperUtil() { }

	/**
	 * Method used to generate NoSuchElementException with message text provided by user.
	 *
	 * @param email - Email id to be included in exception
	 * @return UsernameNotFoundException - exception
	 */
	public static Supplier<? extends UsernameNotFoundException> noUserNotFoundExceptionSupplier(String email) {
		StringBuilder message = new StringBuilder();
		message.append("Username/Email: ")
				.append(email)
				.append(" not found");
		return () -> new UsernameNotFoundException(message.toString());
	}

	/**
	 * Method used to generate NoSuchElementException with message text provided by user.
	 *
	 * @param message - message text to be thrown to user
	 * @return Supplier<? extends NoSuchElementException> - supplier for NoSuchElementException
	 */
	public static Supplier<? extends NoSuchElementException> noSuchElementExceptionSupplier(String message) {
		return () -> new NoSuchElementException(message);
	}

}
