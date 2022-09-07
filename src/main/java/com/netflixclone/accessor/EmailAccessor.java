package com.netflixclone.accessor;


import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EmailAccessor {

    @Autowired
    Mailer mailer;

    @Qualifier("fromEmail")
    @Autowired
    private String fromEmail;

    @Qualifier("fromName")
    private String fromeName;

    public void sendEmail(final String toName,
                          final String toEmail,
                          final String subject, final String contents){
        Email email = EmailBuilder
                .startingBlank()
                .to(toName, toEmail)
                .from(fromeName,fromEmail)
                .withSubject(subject)
                .withPlainText(contents)
                .buildEmail();
        mailer.sendMail(email);
    }

}
