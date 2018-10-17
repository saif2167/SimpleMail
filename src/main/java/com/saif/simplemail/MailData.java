package com.saif.simplemail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saiful Islam<saifislam2167@gmail.com>
 */
public class MailData {

    private String to;
    private String subject;
    private String body;
    private List<File> attachment;

    public MailData(String to, String subject, String body) {
        this(to, subject, body, null);
    }

    public MailData(String to, String subject, String body, List<File> attachment) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachment = attachment;
    }

    public boolean hasAttachment() {
        return (attachment != null && !attachment.isEmpty());
    }

    public void addAttachment(File f) {
        if (attachment == null) {
            attachment = new ArrayList<>();
        }
        if (!attachment.contains(f)) {
            attachment.add(f);
        }
    }

    public List<File> getAttachment() {
        return attachment;
    }

    public String getBody() {
        return body;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

}
