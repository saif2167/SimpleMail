package com.saif.simplemail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
class MailSender {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    public static void sendEmail(Properties props, Session session, MailData md) {
        try {
            if (!md.hasAttachment()) {
                sendTextMail(props, session, md);
            } else {
                sendMailWithAttachment(props, session, md);
            }
            logger.info("Successfully sent Email");
        } catch (Exception e) {
            logger.error("Could not send email ! {}", e);
        }
    }

    private static void sendTextMail(Properties props, Session session, MailData md) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress(props.getProperty("mail.from"), "No-reply"));
        // msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
        msg.setSubject(md.getSubject(), "utf-8");
        msg.setText(md.getBody(), "utf-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(md.getTo(), false));
        Transport.send(msg);

    }

    private static void sendMailWithAttachment(Properties props, Session session, MailData md) throws UnsupportedEncodingException, MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(props.getProperty("mail.from"), "No-reply"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(md.getTo(), false));
        msg.setSubject(md.getSubject(), "utf-8");
        //MultiPart
        Multipart multipart = new MimeMultipart();
        //-- Text body part
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(md.getBody());
        multipart.addBodyPart(textBodyPart);
        //--attacementBodtPart
        // md.getAttachment().forEach(f -> {
        md.getAttachment()
                .stream()
                .filter((f) -> (f.exists()))
                .forEachOrdered((f) -> {
                    try {
                        BodyPart fileBodyPart = new MimeBodyPart();
                        DataSource ds = new FileDataSource(f.getAbsolutePath());
                        fileBodyPart.setDataHandler(new DataHandler(ds));
                        fileBodyPart.setFileName(f.getName());
                        multipart.addBodyPart(fileBodyPart);
                        System.out.println("Added "+f.getName());
                    } catch (MessagingException ex) {
                        logger.error("Could not add File DataSource for Email Attachment ! {}", ex);
                    }
                });
        //Set the body content
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
