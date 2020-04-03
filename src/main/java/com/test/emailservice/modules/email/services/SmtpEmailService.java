package com.test.emailservice.modules.email.services;

import com.test.emailservice.core.resources.Pagination;
import com.test.emailservice.modules.email.entities.Host;
import com.test.emailservice.modules.email.entities.SmtpEmail;
import com.test.emailservice.modules.email.repositories.EmailRepository;
import com.test.emailservice.modules.email.repositories.HostRepository;
import com.test.emailservice.modules.email.resources.EmailRequest;
import com.test.emailservice.modules.email.resources.SmtpEmailResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Service
public class SmtpEmailService {
    private JavaMailSenderImpl sender;
    ApplicationEnvironmentPreparedEvent event;
    private MimeMessage message;


    @Autowired
    EmailRepository repository;

    @Autowired
    HostRepository hostRepository;

    public List<SmtpEmail> findAll() {
        return repository.findAll();
    }


    public SmtpEmailResource sendMail(EmailRequest request) {
        List<Host> hosts = hostRepository.findAll();
        Host host = hosts.get(0);
        int index = 1;
        while (true) {
            setHost(host);
            try {
                dispatchMail(request);
                return repository.save(SmtpEmail.builder()
                                        .from(request.getFrom())
                                        .to(request.getTo())
                                        .subject(request.getSubject())
                                        .message(request.getMessage())
                                        .build()).toSmtpEmailResource();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (index == hosts.size()) {
                System.out.println(host.getHost());
                break;
            }
            host = hosts.get(index);
            index++;

        }
        return  null;
    }


    /**
     *
     * @param host
     */
    public void setHost(Host host) {
        if(sender == null) {
            sender = new JavaMailSenderImpl();
        }
        sender.setHost(host.getHost());
        sender.setUsername(host.getUsername());
        sender.setPassword(host.getPassword());
        sender.setPort(host.getPort());

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailProperties.put("mail.smtp.timeout", 1000);
        mailProperties.put("mail.smtp.connectiontimeout", 1000);
        sender.setJavaMailProperties(mailProperties);

    }

    /**
     *
     * @param request
     * @throws MessagingException
     * @throws IOException
     */
    public void dispatchMail(EmailRequest request) throws MessagingException, IOException {

        message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_16.name());
        helper.setTo(request.getTo());
        helper.setText(request.getMessage(), false);
        helper.setSubject(request.getSubject());
        helper.setFrom(request.getFrom());
        sender.send(message);

    }

}
