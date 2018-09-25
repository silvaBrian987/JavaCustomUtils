package net.bgsystems.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MimeType;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class MailUtils {

	private static final Logger LOGGER = LogManager.getLogManager().getLogger(MailUtils.class.getName());

	public static Session createSession(MailContext mailContext) {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", mailContext.getProtocol());
		props.setProperty("mail.host", mailContext.getHost());
		props.put("mail.smtp.port", mailContext.getPort());
		return Session.getInstance(props, mailContext.getAuthenticator());
	}

	public static MimeMessage generateMimeMessage(Session session, String from, String to, String cc, String subject) throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		if (to != null) {
			LOGGER.log(Level.FINE, "TO = " + to);
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		}
		if (cc != null) {
			LOGGER.log(Level.FINE, "CC = " + cc);
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
		}
		message.setSubject(subject);
		return message;
	}

	public static void appendBody(MimeMessage mimeMessage, String mensaje, MimeType mimeType, DataHandler dataHandler) throws Exception {
		if (mimeType == null)
			mimeType = new MimeType("application/octet-stream");

		Multipart content = null;

		try {
			Object mimeContent = mimeMessage.getContent();
			if (mimeContent instanceof Multipart) {
				content = (Multipart) mimeMessage.getContent();
			} else {
				content = new MimeMultipart();
			}
		} catch (Exception e) {
			if (!(e instanceof MessagingException || e.getCause() instanceof MessagingException))
				throw e;
			content = new MimeMultipart();
		}

		BodyPart bodyPart = null;
		if (content.getCount() == 0) {
			bodyPart = new MimeBodyPart();
			content.addBodyPart(bodyPart);
		} else {
			bodyPart = content.getBodyPart(0);
		}
		bodyPart.setContent(mensaje, mimeType.toString());

		if (dataHandler != null)
			MailUtils.addAttachment(content, dataHandler);

		mimeMessage.setContent(content);
	}

	public static void addAttachment(Multipart content, DataHandler dataHandler) throws MessagingException {
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setDataHandler(dataHandler);
		bodyPart.setFileName(dataHandler.getName());
		content.addBodyPart(bodyPart);
	}

	public static ByteArrayDataSource bytesToByteArrayDataSource(byte[] bytes, String datatype) throws FileNotFoundException, IOException {
		return new ByteArrayDataSource(bytes, datatype);
	}

	public static ByteArrayDataSource fileToByteArrayDataSource(File file) throws FileNotFoundException, IOException {
		return MailUtils.bytesToByteArrayDataSource(FileUtils.fileToBytes(file), Files.probeContentType(Paths.get(file.toURI())));
	}

	public static void sendMail(MailContext mailContext, String from, String to, String cc, String subject, String message, String mimeType, DataSource attachment) throws Exception {
		Session session = MailUtils.createSession(mailContext);
		MimeMessage mimeMessage = MailUtils.generateMimeMessage(session, from, to, cc, subject);
		MailUtils.appendBody(mimeMessage, message, new MimeType(mimeType), (attachment != null ? new DataHandler(attachment) : null));
		MailUtils.sendMail(session, mimeMessage);
	}

	public static void sendMail(Session session, MimeMessage message) throws Exception {
		Transport t = null;
		try {
			t = session.getTransport();
			t.connect();
			t.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			throw e;
		} finally {
			if (t != null) {
				try {
					t.close();
				} catch (MessagingException e) {
					throw e;
				}
			}
		}
	}
}
