package com.saif.simplemail.demo;

import com.saif.simplemail.EmailService;
import com.saif.simplemail.MailData;
import java.util.Properties;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
public class DemoApp {

    public static void main(String[] args) {
        Properties prop = getProperties();
        EmailService service = EmailService.getMailService();
        service.sendEmail(prop, new MailData("saiful.islam@printoscope.pl", "test sub", "Body.."));
    }

    public static Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.enabled", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.from", "testuser@test.com");
        prop.put("mail.smtp.user", "user@gmail.com");
        prop.put("mail.smtp.password", "********");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        return prop;
    }
}
