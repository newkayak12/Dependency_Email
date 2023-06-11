package com.github.newkayak12.message;


import org.springframework.mail.SimpleMailMessage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HtmlMailForm extends SimpleMailMessage {
    private Map<String, Object> attribute = new HashMap<>();
    private String templateName;
    private HtmlMailForm() {
        super();
        super.setSentDate(Calendar.getInstance().getTime());
    }

    public static HtmlMailForm write(String subject, String templateName, Map<String, Object> attribute, String... to){
        HtmlMailForm email = new HtmlMailForm();
        email.setTo(to);
        email.setSubject(subject);
        email.attribute = attribute;
        email.templateName = templateName;
        return email;
    }

    public Map<String, Object> getAttribute() {
        return attribute;
    }

    public String getTemplateName() {
        return templateName;
    }
}
