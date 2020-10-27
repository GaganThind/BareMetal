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
	public void sendEmail(String toAddress, String subject, String message);

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
