/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.AMS.ClubDePlongee.entities;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Un membre de l'association du club de plongée est une personne inscrite.
 * @author Miage
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Membre implements Serializable {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(unique=true)
    // Nom d'utlisateur
    private String userLogin;
        
    @NotNull
    // Mdp de l'utilisateur
    private String password;
    
    @NotNull
    // Nom du membre
    private String nom;
    
    @NotNull
    // Prénom du membre
    private String prenom;
    
    // Email du membre
    private String email;
    
    // Adresse de résidence
    private String adresse;
    
    // Niveau d'expertise en plongée (de 1 à 5)
    private Niveau niveauExpertise;
    
    // Numéro de licence du membre
    private Long numLicence;
    
    // Date à laquelle le certificat est déposé
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCertificat;
    
    // Numéro de l'iban pour régler la cotisation
    private String iban;
    
    // Signale si le paiement est valide ou non
    private Boolean cotisationValide = false;

    // Un membre est président
    private Boolean president = false;
    
    // Mmebre enseignant
    private Boolean enseignant = false;
    
    // Membre secretaire
    private Boolean secretaire = false;
    
    // Désigne le fait qu'un membre soit apte ou non; true = apte
    private Boolean apte = false;
 
    
}
