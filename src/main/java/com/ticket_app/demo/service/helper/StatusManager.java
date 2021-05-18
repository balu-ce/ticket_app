package com.ticket_app.demo.service.helper;

import java.util.concurrent.CompletableFuture;
import com.ticket_app.demo.notification.EmailService;

public class StatusManager{

    private String status;
    
    public StatusManager(String status){
        this.status = status;
    }

    public void handleStatus(String agentName, String email){
        switch (status) {
            case "WAITING_ON_CUSTOMER":
                EmailService em_svc = new EmailService();
                CompletableFuture.supplyAsync(() -> {
                    em_svc.baselineExample(agentName,email);
                    return null; 
                });
                break;
            case "RESOLVED":
                System.out.println("RESOLVED AND SCHEDULED");
                break;
            default:
                break;
        }
    }


}