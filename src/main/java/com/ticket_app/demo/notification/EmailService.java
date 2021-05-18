package com.ticket_app.demo.notification;

import com.sendgrid.*;
import java.io.IOException;


public class EmailService {

  public EmailService(){

  }
  // Minimum required to send an email
  public static Mail buildHelloEmail(String customerName,String email) {
    Email from = new Email("yogesh@sinecycle.com");
    String subject = "Agent Ticket Response";
    Email to = new Email(email);
    String content_text = "An agent" + customerName + "has been waiting for your response";
    Content content = new Content("text/plain", content_text);
    Mail mail = new Mail(from, subject, to, content);

    return mail;
  }

  public void baselineExample(String customerName, String email) {
    final Mail helloWorld = buildHelloEmail(customerName,email);
    try {
      send(helloWorld);
    } catch (IOException e) {
      //TODO: handle exception
    }
    
  }

  private static void send(final Mail mail) throws IOException {
    final SendGrid sg = new SendGrid("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");

    final Request request = new Request();
    request.setMethod(Method.POST);
    request.setEndpoint("mail/send");
    request.setBody(mail.build());

    final Response response = sg.api(request);
    System.out.println(response.getStatusCode());
    System.out.println(response.getBody());
    System.out.println(response.getHeaders());
  }

}