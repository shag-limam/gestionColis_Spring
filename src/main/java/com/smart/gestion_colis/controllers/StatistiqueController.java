//package com.smart.gestion_colis.controllers;
//
//import com.smart.gestion_colis.services.VehiculeService;
//import com.smart.gestion_colis.services.LivraisonService;
//import com.smart.gestion_colis.services.ColisService;
//import com.smart.gestion_colis.services.UserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.smart.gestion_colis.entities.*;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/statistiques")
//public class StatistiqueController {
//
//    private final VehiculeService vehiculeService;
//    private final LivraisonService livraisonService;
//    private final ColisService colisService;
//    private final UserService userService;
//
//    public StatistiqueController(
//            VehiculeService vehiculeService,
//            LivraisonService livraisonService,
//            ColisService colisService,
//            UserService userService
//    ) {
//        this.vehiculeService = vehiculeService;
//        this.livraisonService = livraisonService;
//        this.colisService = colisService;
//        this.userService = userService;
//    }
//
//    // Existing vehicle-related statistics
//    @GetMapping("/vehicules/approuves")
//    public ResponseEntity<Long> getTotalVehiculesApprouves() {
//        long totalApproved = vehiculeService.getTotalVehiculesApprouves();
//        return ResponseEntity.ok(totalApproved);
//    }
//
//    @GetMapping("/vehicules/rejetes")
//    public ResponseEntity<Long> getTotalVehiculesRejetes() {
//        long totalRejected = vehiculeService.countRejectedVehicules();
//        return ResponseEntity.ok(totalRejected);
//    }
//
//    @GetMapping("/livraisons")
//    public ResponseEntity<Map<String, Object>> getLivraisonStatistics() {
//        Map<String, Object> stats = new HashMap<>();
//
//        // Nombre total de livraisons
//        stats.put("totalLivraisons", livraisonService.getAllLivraisons().size());
//
//        // Répartition des statuts de livraison
//        Map<String, Long> statutRepartition = livraisonService.getAllLivraisons().stream()
//                .collect(Collectors.groupingBy(Livraison::getStatut, Collectors.counting()));
//        stats.put("statutRepartition", statutRepartition);
//
//        // Taux de livraisons réussies
//        long totalLivrees = statutRepartition.getOrDefault("Livré", 0L);
//        double tauxReussite = (double) totalLivrees / livraisonService.getAllLivraisons().size() * 100;
//        stats.put("tauxReussite", tauxReussite);
//
//        // Nombre de livraisons par livreur
//        Map<String, Long> livraisonsParLivreur = livraisonService.getAllLivraisons().stream()
//                .filter(livraison -> livraison.getLivreur() != null)
//                .collect(Collectors.groupingBy(livraison -> livraison.getLivreur().getFullName(), Collectors.counting()));
//        stats.put("livraisonsParLivreur", livraisonsParLivreur);
//
//        return ResponseEntity.ok(stats);
//    }
//
//    @GetMapping("/colis/overview")
//    public ResponseEntity<Map<String, Object>> getColisStatistics() {
//        Map<String, Object> colisStats = new HashMap<>();
//        try {
//            // Fetch all colis
//            List<Colis> allColis = colisService.getAllColis();
//
//            // Total number of colis
//            long totalColis = allColis.size();
//            colisStats.put("total_colis", totalColis);
//
//            // Number of available colis (without delivery)
//            long availableColis = colisService.getColisDisponibles().size();
//            colisStats.put("colis_disponibles", availableColis);
//
//            // Calculate percentage of available colis
//            double availablePercentage = (totalColis > 0)
//                    ? (availableColis * 100.0 / totalColis)
//                    : 0.0;
//            colisStats.put("pourcentage_disponibles", String.format("%.2f%%", availablePercentage));
//
//            // Parcels per Client
//            Map<Integer, Long> parcelsPerClient = allColis.stream()
//                    .filter(colis -> colis.getClient() != null)
//                    .collect(Collectors.groupingBy(colis -> colis.getClient().getId(), Collectors.counting()));
//            colisStats.put("parcels_per_client", parcelsPerClient);
//
//            // Parcels per Weight Category
//            Map<String, Long> weightDistribution = allColis.stream()
//                    .collect(Collectors.groupingBy(colis -> {
//                        if (colis.getPoids() <= 5) return "0-5kg";
//                        else if (colis.getPoids() <= 10) return "5-10kg";
//                        else return "10kg+";
//                    }, Collectors.counting()));
//            colisStats.put("weight_distribution", weightDistribution);
//
//            // Parcels per Delivery Status
//            Map<String, Long> deliveryStatusDistribution = allColis.stream()
//                    .collect(Collectors.groupingBy(colis ->
//                                    colis.getLivraison() != null ? colis.getLivraison().getStatut() : "No Delivery Assigned",
//                            Collectors.counting()));
//            colisStats.put("delivery_status_distribution", deliveryStatusDistribution);
//
//            // Parcels per Origin Address
//            Map<String, Long> originDistribution = allColis.stream()
//                    .collect(Collectors.groupingBy(Colis::getAdresseExpediteur, Collectors.counting()));
//            colisStats.put("origin_distribution", originDistribution);
//
//            // Parcels per Destination Address
//            Map<String, Long> destinationDistribution = allColis.stream()
//                    .collect(Collectors.groupingBy(Colis::getAdresseDestinataire, Collectors.counting()));
//            colisStats.put("destination_distribution", destinationDistribution);
//
//            // Parcels Created per Month
//            Map<String, Long> parcelsPerMonth = allColis.stream()
//                    .collect(Collectors.groupingBy(colis -> getMonthName(colis.getDateExpedition()), Collectors.counting()));
//            colisStats.put("parcels_per_month", parcelsPerMonth);
//
//            // Average Weight of Parcels
//            OptionalDouble averageWeight = allColis.stream()
//                    .mapToDouble(Colis::getPoids)
//                    .average();
//            colisStats.put("average_weight", averageWeight.orElse(0.0));
//
//            // Total Weight of All Parcels
//            double totalWeight = allColis.stream()
//                    .mapToDouble(Colis::getPoids)
//                    .sum();
//            colisStats.put("total_weight", totalWeight);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            colisStats.put("error", "An error occurred while fetching statistics.");
//        }
//        return ResponseEntity.ok(colisStats);
//    }
//
//    private String getMonthName(Date date) {
//        if (date == null) {
//            return "Unknown";
//        }
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        return localDate.getMonth().name();
//    }
//}

package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.services.VehiculeService;
import com.smart.gestion_colis.services.LivraisonService;
import com.smart.gestion_colis.services.ColisService;
import com.smart.gestion_colis.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smart.gestion_colis.entities.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {

    private final VehiculeService vehiculeService;
    private final LivraisonService livraisonService;
    private final ColisService colisService;
    private final UserService userService;

    public StatistiqueController(
            VehiculeService vehiculeService,
            LivraisonService livraisonService,
            ColisService colisService,
            UserService userService
    ) {
        this.vehiculeService = vehiculeService;
        this.livraisonService = livraisonService;
        this.colisService = colisService;
        this.userService = userService;
    }

    // Statistiques pour les véhicules
    @GetMapping("/vehicules")
    public ResponseEntity<Map<String, Object>> getVehiculeStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Nombre total de véhicules approuvés
        long totalApproved = vehiculeService.getTotalVehiculesApprouves();
        stats.put("véhicules_approuvés", totalApproved);

        // Nombre total de véhicules rejetés
        long totalRejected = vehiculeService.countRejectedVehicules();
        stats.put("véhicules_rejetés", totalRejected);

        // Taux d'approbation des véhicules
        double tauxApprobation = (totalApproved + totalRejected > 0)
                ? (totalApproved * 100.0 / (totalApproved + totalRejected))
                : 0.0;
        stats.put("taux_approbation", String.format("%.2f%%", tauxApprobation));

        return ResponseEntity.ok(stats);
    }

    // Statistiques pour les livraisons
    @GetMapping("/livraisons")
    public ResponseEntity<Map<String, Object>> getLivraisonStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Nombre total de livraisons
        long totalLivraisons = livraisonService.getAllLivraisons().size();
        stats.put("total_livraisons", totalLivraisons);

        // Répartition des statuts de livraison
        Map<String, Long> statutRepartition = livraisonService.getAllLivraisons().stream()
                .collect(Collectors.groupingBy(Livraison::getStatut, Collectors.counting()));
        stats.put("répartition_statuts", statutRepartition);

        // Taux de livraisons réussies
        long totalLivrees = statutRepartition.getOrDefault("Livré", 0L);
        double tauxReussite = (double) totalLivrees / totalLivraisons * 100;
        stats.put("taux_réussite", String.format("%.2f%%", tauxReussite));

        // Nombre de livraisons par livreur
        Map<String, Long> livraisonsParLivreur = livraisonService.getAllLivraisons().stream()
                .filter(livraison -> livraison.getLivreur() != null)
                .collect(Collectors.groupingBy(livraison -> livraison.getLivreur().getFullName(), Collectors.counting()));
        stats.put("livraisons_par_livreur", livraisonsParLivreur);

        return ResponseEntity.ok(stats);
    }

    // Statistiques pour les colis
    @GetMapping("/colis")
    public ResponseEntity<Map<String, Object>> getColisStatistics() {
        Map<String, Object> colisStats = new HashMap<>();
        try {
            // Récupérer tous les colis
            List<Colis> allColis = colisService.getAllColis();

            // Nombre total de colis
            long totalColis = allColis.size();
            colisStats.put("total_colis", totalColis);

            // Nombre de colis disponibles (sans livraison)
            long availableColis = colisService.getColisDisponibles().size();
            colisStats.put("colis_disponibles", availableColis);

            // Pourcentage de colis disponibles
            double availablePercentage = (totalColis > 0)
                    ? (availableColis * 100.0 / totalColis)
                    : 0.0;
            colisStats.put("pourcentage_disponibles", String.format("%.2f%%", availablePercentage));

            // Colis par client (en utilisant le nom du client)
            Map<String, Long> colisParClient = allColis.stream()
                    .filter(colis -> colis.getClient() != null)
                    .collect(Collectors.groupingBy(
                            colis -> colis.getClient().getFullName(), // Utiliser le nom du client
                            Collectors.counting()
                    ));
            colisStats.put("colis_par_client", colisParClient);

            // Répartition des colis par catégorie de poids
            Map<String, Long> repartitionPoids = allColis.stream()
                    .collect(Collectors.groupingBy(colis -> {
                        if (colis.getPoids() <= 5) return "0-5kg";
                        else if (colis.getPoids() <= 10) return "5-10kg";
                        else return "10kg+";
                    }, Collectors.counting()));
            colisStats.put("répartition_poids", repartitionPoids);

            // Répartition des colis par statut de livraison
            Map<String, Long> repartitionStatuts = allColis.stream()
                    .collect(Collectors.groupingBy(colis ->
                                    colis.getLivraison() != null ? colis.getLivraison().getStatut() : "Non assigné",
                            Collectors.counting()));
            colisStats.put("répartition_statuts", repartitionStatuts);

            // Répartition des colis par adresse d'origine
            Map<String, Long> repartitionOrigine = allColis.stream()
                    .collect(Collectors.groupingBy(Colis::getAdresseExpediteur, Collectors.counting()));
            colisStats.put("répartition_origine", repartitionOrigine);

            // Répartition des colis par adresse de destination
            Map<String, Long> repartitionDestination = allColis.stream()
                    .collect(Collectors.groupingBy(Colis::getAdresseDestinataire, Collectors.counting()));
            colisStats.put("répartition_destination", repartitionDestination);

            // Colis créés par mois
            Map<String, Long> colisParMois = allColis.stream()
                    .collect(Collectors.groupingBy(colis -> getMonthName(colis.getDateExpedition()), Collectors.counting()));
            colisStats.put("colis_par_mois", colisParMois);

            // Poids moyen des colis
            OptionalDouble poidsMoyen = allColis.stream()
                    .mapToDouble(Colis::getPoids)
                    .average();
            colisStats.put("poids_moyen", poidsMoyen.orElse(0.0));

            // Poids total des colis
            double poidsTotal = allColis.stream()
                    .mapToDouble(Colis::getPoids)
                    .sum();
            colisStats.put("poids_total", poidsTotal);

        } catch (Exception e) {
            e.printStackTrace();
            colisStats.put("erreur", "Une erreur s'est produite lors de la récupération des statistiques.");
        }
        return ResponseEntity.ok(colisStats);
    }

    private String getMonthName(Date date) {
        if (date == null) {
            return "Inconnu";
        }
        // Convert java.sql.Date or java.util.Date to LocalDate
        LocalDate localDate;
        if (date instanceof java.sql.Date) {
            // For java.sql.Date, convert to LocalDate directly
            localDate = ((java.sql.Date) date).toLocalDate();
        } else {
            // For java.util.Date, convert to Instant and then to LocalDate
            localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return localDate.getMonth().name();
    }
//    @GetMapping("/colis")
//    public ResponseEntity<Map<String, Object>> getColisStatistics() {
//        Map<String, Object> colisStats = new HashMap<>();
//        try {
//            // Récupérer tous les colis
//            List<Colis> allColis = colisService.getAllColis();
//
//            // Nombre total de colis
//            long totalColis = allColis.size();
//            colisStats.put("total_colis", totalColis);
//
//            // Nombre de colis disponibles (sans livraison)
//            long availableColis = colisService.getColisDisponibles().size();
//            colisStats.put("colis_disponibles", availableColis);
//
//            // Pourcentage de colis disponibles
//            double availablePercentage = (totalColis > 0)
//                    ? (availableColis * 100.0 / totalColis)
//                    : 0.0;
//            colisStats.put("pourcentage_disponibles", String.format("%.2f%%", availablePercentage));
//
//            // Colis par client
//            Map<Integer, Long> colisParClient = allColis.stream()
//                    .filter(colis -> colis.getClient() != null)
//                    .collect(Collectors.groupingBy(colis -> colis.getClient().getId(), Collectors.counting()));
//            colisStats.put("colis_par_client", colisParClient);
//
//            // Répartition des colis par catégorie de poids
//            Map<String, Long> repartitionPoids = allColis.stream()
//                    .collect(Collectors.groupingBy(colis -> {
//                        if (colis.getPoids() <= 5) return "0-5kg";
//                        else if (colis.getPoids() <= 10) return "5-10kg";
//                        else return "10kg+";
//                    }, Collectors.counting()));
//            colisStats.put("répartition_poids", repartitionPoids);
//
//            // Répartition des colis par statut de livraison
//            Map<String, Long> repartitionStatuts = allColis.stream()
//                    .collect(Collectors.groupingBy(colis ->
//                                    colis.getLivraison() != null ? colis.getLivraison().getStatut() : "Non assigné",
//                            Collectors.counting()));
//            colisStats.put("répartition_statuts", repartitionStatuts);
//
//            // Répartition des colis par adresse d'origine
//            Map<String, Long> repartitionOrigine = allColis.stream()
//                    .collect(Collectors.groupingBy(Colis::getAdresseExpediteur, Collectors.counting()));
//            colisStats.put("répartition_origine", repartitionOrigine);
//
//            // Répartition des colis par adresse de destination
//            Map<String, Long> repartitionDestination = allColis.stream()
//                    .collect(Collectors.groupingBy(Colis::getAdresseDestinataire, Collectors.counting()));
//            colisStats.put("répartition_destination", repartitionDestination);
//
//            // Colis créés par mois
//            Map<String, Long> colisParMois = allColis.stream()
//                    .collect(Collectors.groupingBy(colis -> getMonthName(colis.getDateExpedition()), Collectors.counting()));
//            colisStats.put("colis_par_mois", colisParMois);
//
//            // Poids moyen des colis
//            OptionalDouble poidsMoyen = allColis.stream()
//                    .mapToDouble(Colis::getPoids)
//                    .average();
//            colisStats.put("poids_moyen", poidsMoyen.orElse(0.0));
//
//            // Poids total des colis
//            double poidsTotal = allColis.stream()
//                    .mapToDouble(Colis::getPoids)
//                    .sum();
//            colisStats.put("poids_total", poidsTotal);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            colisStats.put("erreur", "Une erreur s'est produite lors de la récupération des statistiques.");
//        }
//        return ResponseEntity.ok(colisStats);
//    }
//
//    // Méthode utilitaire pour obtenir le nom du mois à partir d'une date
//    private String getMonthName(Date date) {
//        if (date == null) {
//            return "Inconnu";
//        }
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        return localDate.getMonth().name();
//    }
}

//    @GetMapping("/colis")
//    public ResponseEntity<Map<String, Object>> getColisStatistics() {
//        Map<String, Object> colisStats = new HashMap<>();
//        try {
//            // Récupérer tous les colis
//            List<Colis> allColis = colisService.getAllColis();
//
//            // Nombre total de colis
//            long totalColis = allColis.size();
//            colisStats.put("total_colis", totalColis);
//
//            // Nombre de colis disponibles (sans livraison)
//            long availableColis = colisService.getColisDisponibles().size();
//            colisStats.put("colis_disponibles", availableColis);
//
//            // Pourcentage de colis disponibles
//            double availablePercentage = (totalColis > 0)
//                    ? (availableColis * 100.0 / totalColis)
//                    : 0.0;
//            colisStats.put("pourcentage_disponibles", String.format("%.2f%%", availablePercentage));
//
//            // Colis par client
//            Map<Integer, Long> colisParClient = allColis.stream()
//                    .filter(colis -> colis.getClient() != null)
//                    .collect(Collectors.groupingBy(colis -> colis.getClient().getId(), Collectors.counting()));
//            colisStats.put("colis_par_client", colisParClient);
//
//            // Répartition des colis par catégorie de poids
//            Map<String, Long> repartitionPoids = allColis.stream()
//                    .collect(Collectors.groupingBy(colis -> {
//                        if (colis.getPoids() <= 5) return "0-5kg";
//                        else if (colis.getPoids() <= 10) return "5-10kg";
//                        else return "10kg+";
//                    }, Collectors.counting()));
//            colisStats.put("répartition_poids", repartitionPoids);
//
//            // Répartition des colis par statut de livraison
//            Map<String, Long> repartitionStatuts = allColis.stream()
//                    .collect(Collectors.groupingBy(colis ->
//                                    colis.getLivraison() != null ? colis.getLivraison().getStatut() : "Non assigné",
//                            Collectors.counting()));
//            colisStats.put("répartition_statuts", repartitionStatuts);
//
//            // Répartition des colis par adresse d'origine
//            Map<String, Long> repartitionOrigine = allColis.stream()
//                    .collect(Collectors.groupingBy(Colis::getAdresseExpediteur, Collectors.counting()));
//            colisStats.put("répartition_origine", repartitionOrigine);
//
//            // Répartition des colis par adresse de destination
//            Map<String, Long> repartitionDestination = allColis.stream()
//                    .collect(Collectors.groupingBy(Colis::getAdresseDestinataire, Collectors.counting()));
//            colisStats.put("répartition_destination", repartitionDestination);
//
//            // Colis créés par mois
//            Map<String, Long> colisParMois = allColis.stream()
//                    .collect(Collectors.groupingBy(colis -> getMonthName(colis.getDateExpedition()), Collectors.counting()));
//            colisStats.put("colis_par_mois", colisParMois);
//
//            // Poids moyen des colis
//            OptionalDouble poidsMoyen = allColis.stream()
//                    .mapToDouble(Colis::getPoids)
//                    .average();
//            colisStats.put("poids_moyen", poidsMoyen.orElse(0.0));
//
//            // Poids total des colis
//            double poidsTotal = allColis.stream()
//                    .mapToDouble(Colis::getPoids)
//                    .sum();
//            colisStats.put("poids_total", poidsTotal);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            colisStats.put("erreur", "Une erreur s'est produite lors de la récupération des statistiques.");
//        }
//        return ResponseEntity.ok(colisStats);
//    }
//
//    private String getMonthName(Date date) {
//        if (date == null) {
//            return "Inconnu";
//        }
//        // Convert java.sql.Date or java.util.Date to LocalDate
//        LocalDate localDate;
//        if (date instanceof java.sql.Date) {
//            // For java.sql.Date, convert to LocalDate directly
//            localDate = ((java.sql.Date) date).toLocalDate();
//        } else {
//            // For java.util.Date, convert to Instant and then to LocalDate
//            localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        }
//        return localDate.getMonth().name();
//    }