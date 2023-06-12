package com.github.newkayak12.config;

import com.github.newkayak12.enums.Vendor;
import com.github.newkayak12.message.HtmlMailForm;
import com.github.newkayak12.message.SimpleMailForm;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class SimpleEmailSender {
    private final JavaMailSenderImpl javaMailSender;

    private final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private String userName;
    private String prefix;


    public SimpleEmailSender(Vendor vendor, String prefix, String userName, String password) {
        this.javaMailSender = new JavaMailSenderImpl();
        switch (vendor.name()) {
            case "GMAIL":
                javaMailSender.setHost("smtp.gmail.com");
                javaMailSender.setProtocol("smtps");
                javaMailSender.setDefaultEncoding(StandardCharsets.UTF_8.name());
                javaMailSender.setPort(465);
                Properties properties = javaMailSender.getJavaMailProperties();
                properties.put(MAIL_SMTP_AUTH, true);
                properties.put(MAIL_SMTP_STARTTLS_ENABLE, true);
                break;
            case "NAVER":
                break;
            default:
                break;
        }
        this.javaMailSender.setUsername(userName);
        this.javaMailSender.setPassword(password);
        this.userName = userName;
        this.prefix = prefix;
    }


    public void send(SimpleMailForm message){
        if(Objects.isNull(message.getFrom())) message.setFrom(this.userName);
        javaMailSender.send(message);
    }

    private ClassLoaderTemplateResolver  emailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(this.prefix);
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(true);
        return resolver;
    }
    public  String resolvingTemplate( String template, Map<String,Object> attribute){
        TemplateEngine templateEngine = new TemplateEngine();
        Context thymeLeafContext = new Context();
        thymeLeafContext.setVariables(attribute);
        templateEngine.setTemplateResolver(emailTemplateResolver());
        return templateEngine.process(template, thymeLeafContext);
    }
    private MimeMessage resolvingMimeMessage(HtmlMailForm message){
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

        try {
            if(Objects.isNull(message.getFrom())) {
                helper.setFrom(this.userName);
            }
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), true);

            return mimeMessage;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void send(HtmlMailForm message){
        javaMailSender.send(this.resolvingMimeMessage(message));
    }



}
