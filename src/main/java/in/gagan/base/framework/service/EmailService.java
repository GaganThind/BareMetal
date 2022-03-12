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

package in.gagan.base.framework.service;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;

/**
 * This Service interface provides the operations for Email service functionality.
 * 
 * @author gaganthind
 *
 */
public interface EmailService {
	
	/**
	 * This method is used to send a text email to user.
	 * 
	 * @param toAddress - Email address of recipient
	 * @param subject - Subject of email
	 * @param message - Message inside the email
	 */
	void sendEmail(String toAddress, String subject, String message);

	/**
	 * This method is used to send a text email with attachment to user.
	 * 
	 * @param toAddress - Email address of recipient
	 * @param subject - Subject of email
	 * @param message - Message inside the email
	 * @param attachment - Email attachment
	 * @throws MessagingException - if multipart creation failed
	 * @throws FileNotFoundException - if the resource cannot be resolved to a file in the file system
	 */
	void sendAttachmentEmail(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException;
	
}
