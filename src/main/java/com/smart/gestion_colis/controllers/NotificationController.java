//package com.smart.gestion_colis.controllers;
//
//import com.smart.gestion_colis.entities.Admin;
//import com.smart.gestion_colis.entities.Notification;
//import com.smart.gestion_colis.repositories.AdminRepository;
//import com.smart.gestion_colis.repositories.NotificationRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.StreamSupport;
//
//@RestController
//@RequestMapping("/api/notifications")
//public class NotificationController {
//
//    private final NotificationRepository notificationRepository;
//    private final AdminRepository adminRepository;
//
//    public NotificationController(NotificationRepository notificationRepository,AdminRepository adminRepository) {
//        this.notificationRepository = notificationRepository;
//        this.adminRepository = adminRepository;
//    }
//
//    @GetMapping("/livreur/{livreurId}")
//    public ResponseEntity<List<Notification>> getNotificationsForLivreur(@PathVariable Integer livreurId) {
//        List<Notification> notifications = notificationRepository.findByLivreurId(livreurId);
//        return ResponseEntity.ok(notifications);
//    }
//
//    @GetMapping("/admin")
//    public ResponseEntity<List<Notification>> getNotificationsForAdmin() {
//        // Récupérer l'admin (on suppose qu'il y en a un seul)
//        Admin admin = StreamSupport.stream(adminRepository.findAll().spliterator(), false)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//        List<Notification> notifications = notificationRepository.findByAdminId(admin.getId());
//        return ResponseEntity.ok(notifications);
//    }
//
//    @GetMapping("/client/{livreurId}")
//    public ResponseEntity<List<Notification>> getNotificationsForClient(@PathVariable Integer clientId) {
//        List<Notification> notifications = notificationRepository.findByClientId(clientId);
//        return ResponseEntity.ok(notifications);
//    }
//
//    @PutMapping("/markAsRead/{notificationId}")
//    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Integer notificationId) {
//        Notification notification = notificationRepository.findById(notificationId)
//                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
//        notification.setRead(true);
//        notificationRepository.save(notification);
//        return ResponseEntity.ok().build();
//    }
//
//}
package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.entities.Admin;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.entities.Notification;
import com.smart.gestion_colis.repositories.AdminRepository;
import com.smart.gestion_colis.repositories.ColisRepository;
import com.smart.gestion_colis.repositories.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final ColisRepository colisRepository;
    private final AdminRepository adminRepository;

    public NotificationController(NotificationRepository notificationRepository,ColisRepository colisRepository, AdminRepository adminRepository) {
        this.notificationRepository = notificationRepository;
        this.adminRepository = adminRepository;
        this.colisRepository= colisRepository;
    }

    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<Notification>> getNotificationsForLivreur(@PathVariable Integer livreurId) {
        List<Notification> notifications = notificationRepository.findByLivreurIdOrderByCreatedAtDesc(livreurId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Notification>> getNotificationsForAdmin() {
        // Récupérer l'admin (on suppose qu'il y en a un seul)
        Admin admin = StreamSupport.stream(adminRepository.findAll().spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        List<Notification> notifications = notificationRepository.findByAdminId(admin.getId());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Notification>> getNotificationsForClient(@PathVariable Integer clientId) {
        List<Notification> notifications = notificationRepository.findByClientIdOrderByCreatedAtDesc(clientId);
        return ResponseEntity.ok(notifications);
    }


    @PutMapping("/markAsRead/{notificationId}")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
        return ResponseEntity.ok().build();
    }


}
