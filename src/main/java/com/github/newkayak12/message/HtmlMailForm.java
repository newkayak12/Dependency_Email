package com.github.newkayak12.message;


import org.springframework.mail.SimpleMailMessage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HtmlMailForm extends SimpleMailMessage {
    private HtmlMailForm() {
        super();
        super.setSentDate(Calendar.getInstance().getTime());
    }

    public static HtmlMailForm write(String subject, String... to){
        HtmlMailForm email = new HtmlMailForm();
        email.setTo(to);
        email.setSubject(subject);
        return email;
    }


}
