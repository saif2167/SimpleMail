package com.saif.simplemail;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
class EmailJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);
    private Properties prop;
    private List<MailData> mailTask = new ArrayList<>();

    public EmailJob(Properties prop, List<MailData> md) {
        this.prop = prop;
        this.mailTask = md;
    }

    @Override
    public void run() {
        if (prop != null && !prop.isEmpty()) {
            if (Boolean.valueOf(prop.getProperty("mail.enabled")) == true) {
                sendMail();
            } else {
                logger.error("Could not send Email.. Mailing System is disabled!");
            }
        } else {
            logger.error("Could not send Email.. Invalid email configuration!");
        }
    }

    private void sendMail() {
        try {
            Authenticator auth = null;
            String username = prop.getProperty("mail.smtp.user");
            String pass = prop.getProperty("mail.smtp.password");
            if (Boolean.valueOf(prop.getProperty("mail.smtp.auth")) == true) {
                prop.put("mail.smtp.starttls.enable", "true"); //enable starttls
                prop.setProperty("mail.smtp.ssl.trust", "*");
                auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, pass);
                    }
                };

            }
            Session session = Session.getDefaultInstance(prop, auth);
            logger.info("Java Mail Session Created..");
            mailTask.forEach(md -> {
                try {
                    MailSender.sendEmail(prop, session, md);
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    logger.error("{}", e);
                }

            });

        } catch (Exception e) {
            logger.error("Could not send email ! {}", e);
        }
    }

}
