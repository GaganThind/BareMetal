package in.gagan.base.framework.service;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

/**
 * This class provides the implementation of EmailService interface and provides operations for Email service functionality.
 * 
 * @author gaganthind
 *
 */
@Transactional
@Service
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender javaMailSender;
	
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * This method is used to send a text email to user.
	 * 
	 * @param toAddress - Email address of recipient
	 * @param subject - Subject of email
	 * @param message - Message inside the email
	 */
	@Override
	public void sendEmail(String toAddress, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(toAddress);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		this.javaMailSender.send(simpleMailMessage);
	}

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
	@Override
	public void sendAttachmentEmail(String toAddress, String subject, String message, String attachment) 
			throws MessagingException, FileNotFoundException {
		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		messageHelper.setTo(toAddress);
		messageHelper.setSubject(subject);
		messageHelper.setText(message);
		FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
		messageHelper.addAttachment("File", file);
		this.javaMailSender.send(mimeMessage);
	}

}
