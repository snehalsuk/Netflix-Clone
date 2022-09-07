package com.netflixclone.config;


import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommunicationConfig {


    @Bean
    public Mailer getMailer() {
        String email = System.getenv("email");
        String password = System.getenv("password");

        if (email == null || email.length() == 0 || password == null || password.length() == 0) {
            System.out.println("Either email or password not set");
            System.exit(1);

        }
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, email, password)
                .buildMailer();

        return mailer;
    }
    @Bean("fromemail")
    public String getFromEmail(){
        return System.getenv("email");

}
@Bean
    public String getFromName(){
        return System.getenv("name");
}

}
