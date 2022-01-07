package com.sq.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class SendGridUtil {
    private final SendGrid sendGrid;
    private final Logger logger = LoggerFactory.getLogger(SendGridUtil.class);

    @Async("executor")
    public void sendEmail(String recipient, String subject, Map<String, String> customization, String templateId) throws Exception{
        Email from = new Email("shuang@shuangqiu.blog");
        Email to = new Email(recipient);
        Personalization personalization = new Personalization();
        personalization.setFrom(from);
        personalization.addTo(to);
        personalization.setSubject(subject);
        customization.keySet().forEach(key -> personalization.addDynamicTemplateData(key, customization.get(key)));

        Mail mail = new Mail();
        mail.addPersonalization(personalization);
        mail.setTemplateId(templateId);
        mail.setFrom(from);
        mail.setSubject(subject);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sendGrid.api(request);
        logger.info(String.valueOf(response.getStatusCode()));
        logger.info(String.valueOf(response.getBody()));
    }
}
