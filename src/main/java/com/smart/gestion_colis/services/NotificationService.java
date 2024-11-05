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

    public void createNotificationForClient(Client client, String message) {
        Notification notification = new Notification(message, client);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/client/" + client.getId(), notification);
    }

    public void createNotificationForAdmin(Admin admin, Vehicule vehicule, String message) {
        Notification notification = new Notification(message, admin, vehicule);
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/admin", notification);
    }
}





// Récupérer les notifications pour un livreur spécifique
//    public List<Notification> getNotificationsForLivreur(Integer livreurId) {
//        return notificationRepository.findByLivreurId(livreurId);
//    }
//
//    // Récupérer les notifications pour un client spécifique
//    public List<Notification> getNotificationsForClient(Integer clientId) {
//        return notificationRepository.findByClientId(clientId);
//    }
//
//    // Récupérer les notifications pour un admin spécifique
//    public List<Notification> getNotificationsForAdmin(Integer adminId) {
//        return notificationRepository.findByAdminId(adminId);
//    }



//package com.smart.gestion_colis.services;
//
//        import com.smart.gestion_colis.entities.*;
//        import com.smart.gestion_colis.repositories.NotificationRepository;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.messaging.simp.SimpMessagingTemplate;
//        import org.springframework.stereotype.Service;
//
//        import java.util.List;
//
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
//        messagingTemplate.convertAndSend("/topic/livreur-notifications", notification);
//    }
//
//    public void createNotificationForClient(Client client, String message) {
//        Notification notification = new Notification(message, client);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/client-notifications", notification);
//    }
//
//    public void createNotificationForAdmin(Admin admin, Vehicule vehicule, String message) {
//        Notification notification = new Notification(message, admin, vehicule);
//        notificationRepository.save(notification);
//        messagingTemplate.convertAndSend("/topic/admin-notifications", notification);
//    }
//}