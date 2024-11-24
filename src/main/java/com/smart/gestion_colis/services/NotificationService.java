package com.smart.gestion_colis.services;

import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void createNotificationForLivreur(Livreur livreur, Livraison livraison, String message) {
        Notification notification = new Notification(message, livreur, livraison);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/livreur/" + livreur.getId(), notification);
    }

    public void createNotificationForLivreur(Livreur livreur, Vehicule vehicule, String message) {
        Notification notification = new Notification(message, livreur, vehicule);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/livreur/" + livreur.getId(), notification);
    }

    public void createNotificationForClient(Client client, Livraison livraison, String message) {
        Notification notification = new Notification(message, client,livraison);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/client/" + client.getId(), notification);
    }

//    public void createNotificationForClient(Client client, Colis colis, String message) {
//        Notification notification = new Notification(message, client,colis);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/client/" + client.getId(), notification);
//    }

    public void createNotificationForAdmin(Admin admin, Vehicule vehicule, String message) {
        Notification notification = new Notification(message, admin, vehicule);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/admin", notification);
    }

    // Nouvelle méthode pour envoyer une notification à l'admin pour une livraison
    public void createNotificationForAdmin(Admin admin, Livraison livraison, String message) {
        Notification notification = new Notification(message, admin,livraison);
        notification.setLivraison(livraison);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/admin", notification);
    }
}


//@Service
//public class NotificationService {
//
//    private final NotificationRepository notificationRepository;
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
//        this.notificationRepository = notificationRepository;
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    public void createNotificationForLivreur(Livreur livreur, Livraison livraison, String message) {
//        Notification notification = new Notification(message, livreur, livraison);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/livreur/" + livreur.getId(), notification);
//    }
//
//    public void createNotificationForLivreur(Livreur livreur, Vehicule vehicule, String message) {
//        Notification notification = new Notification(message, livreur, vehicule);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/livreur/" + livreur.getId(), notification);
//    }
//
//    public void createNotificationForClient(Client client,Livraison livraison, String message) {
//        Notification notification = new Notification(message, client);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/client/" + client.getId(), notification);
//    }
//    public void createNotificationForClient(Client client,Colis colis, String message) {
//        Notification notification = new Notification(message, client);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/client/" + client.getId(), notification);
//    }
//
//
//    public void createNotificationForAdmin(Admin admin, Vehicule vehicule, String message) {
//        Notification notification = new Notification(message, admin, vehicule);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/admin", notification);
//    }
//}

