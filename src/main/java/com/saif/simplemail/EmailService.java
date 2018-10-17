package com.saif.simplemail;

import java.util.List;
import java.util.Properties;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
public interface EmailService {

    public void sendEmail(Properties prop, MailData md);
    public void sendEmail(Properties prop, List<MailData> md);

    /**
     * It return a implementation of MailService that uses a new Thread for
     * every mail to send
     *
     * @return
     */
    public static EmailService getMailService() {
        return SingleThreadEmailService.getInstance();
    }
}
