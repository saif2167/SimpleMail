package com.saif.simplemail;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
class SingleThreadEmailService implements EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private static SingleThreadEmailService instance;
    
    private SingleThreadEmailService() {

    }
    
    public static SingleThreadEmailService getInstance() {
        if (instance == null) {
            instance = new SingleThreadEmailService();
        }
        return instance;
    }
    
    @Override
    public void sendEmail(Properties prop, List<MailData> md) {
        EmailJob job = new EmailJob(prop, md);
        Thread t = new Thread(job);
        t.start();
    }
    
    @Override
    public void sendEmail(Properties prop, MailData md) {
        sendEmail(prop, Arrays.asList(md));
    }    
    
}
