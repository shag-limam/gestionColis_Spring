//package com.smart.gestion_colis.entities;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Accessors(chain = true)
//@Table(name = "Livreur")
//public class Livreur extends User {
//
//    @Column(nullable = false)
//    private String address;
//
//    @Column(nullable = false)
//    private String phoneNumber;
//
//    @Column(nullable = false)
//    private String licence;
//
//    @Column(nullable = false)
//    private boolean isAvailable = true;  // Par défaut, le livreur est disponible.
//
//    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonBackReference  // Côté inverse de la relation
//    private List<Livraison> livraisons;
//
//    @OneToOne
//    @JoinColumn(name = "vehicule_id", referencedColumnName = "id")
//    private Vehicule vehicule;
//
//    @Override
//    public String toString() {
//        return "Livreur{" +
//                "address='" + address + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", licence='" + licence + '\'' +
//                ", isAvailable=" + isAvailable +
//                ", fullName='" + getFullName() + '\'' +
//                '}';
//    }
//}
package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "Livreur")
public class Livreur extends User {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String licence;

    @Column(nullable = false)
    private boolean isAvailable = true;  // Par défaut, le livreur est disponible.

    @JsonIgnore  // Ignore la relation inverse pour éviter les cycles
    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livraison> livraisons;

    @OneToOne
    @JoinColumn(name = "vehicule_id", referencedColumnName = "id")
    private Vehicule vehicule;

    @Override
    public String toString() {
        return "Livreur{" +
                "address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", licence='" + licence + '\'' +
                ", isAvailable=" + isAvailable +
                ", fullName='" + getFullName() + '\'' +
                '}';
    }
}

//package com.smart.gestion_colis.entities;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Accessors(chain = true)
//@Table(name = "Livreur")
//public class Livreur extends User {
//
//    @Column(nullable = false)
//    private String address;
//
//    @Column(nullable = false)
//    private String phoneNumber;
//
//    @Column(nullable = false)
//    private String licence;
//
//    @Column(nullable = false)
//    private boolean isAvailable = true;  // Par défaut, le livreur est disponible.
//
//    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference  // Côté propriétaire de la relation
//    private List<Livraison> livraisons;
//
//    @OneToOne
//    @JoinColumn(name = "vehicule_id", referencedColumnName = "id")
//    private Vehicule vehicule;
//
//    @Override
//    public String toString() {
//        return "Livreur{" +
//                "address='" + address + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", licence='" + licence + '\'' +
//                ", isAvailable=" + isAvailable +
//                ", fullName='" + getFullName() + '\'' +
//                '}';
//    }
//}
