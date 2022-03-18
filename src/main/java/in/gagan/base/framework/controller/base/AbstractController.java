/*
 * SpringBoot_Hibernate_Framework
 * 
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

package in.gagan.base.framework.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * This abstract controller class provides the common functionality.
 * 
 * @author gaganthind
 *
 */
@RestController
public abstract class AbstractController implements BaseController {

	@Autowired
	protected MessageSource message;

	/**
	 * Method used to get the message text from the message property file.
	 *
	 * @param message - message key corresponding to the message text
	 * @param tokens - any input token to be added to message text
	 * @return messageString - returns the complete message to be shown to user
	 */
	protected String getMessage(String message, Object... tokens) {
		return this.message.getMessage(message, tokens, Locale.ENGLISH);
	}
	
}
