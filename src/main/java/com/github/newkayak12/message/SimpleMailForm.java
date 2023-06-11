package com.github.newkayak12.message;

import org.springframework.mail.SimpleMailMessage;

import java.io.Serializable;
import java.util.Calendar;

public class SimpleMailForm extends SimpleMailMessage implements Serializable {
    private static final long serialVersionUID = 1851737769224615994L;

    private SimpleMailForm() {
        super();
        super.setSentDate(Calendar.getInstance().getTime());
    }
    public static SimpleMailForm write(String subject, String message, String... to){

        SimpleMailForm email = new SimpleMailForm();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        return email;
    }
}
