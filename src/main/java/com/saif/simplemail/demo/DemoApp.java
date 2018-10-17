package com.saif.simplemail.demo;

import com.saif.simplemail.EmailService;
import com.saif.simplemail.MailData;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
public class DemoApp {

    public static void main(String[] args) {
        Properties prop = getProperties();
        EmailService service = EmailService.getMailService();
        //service.sendEmail(prop, new MailData("saiful.islam@printoscope.pl", "test sub", "Body.."));
        File f1 = new File("file1.txt");
        File f2 = new File("file2.pdf");
        MailData md1 = new MailData("saiful.islam@printoscope.pl", "test sub", "Body..", Arrays.asList(f1, f2));     
        service.sendEmail(prop,md1);
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
