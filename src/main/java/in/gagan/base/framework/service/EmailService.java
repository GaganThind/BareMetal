package in.gagan.base.framework.service;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;

public interface EmailService {
	
	public void sendEmail(String toAddress, String subject, String message);

	void sendAttachmentEmail(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException;
	
}
