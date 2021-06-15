/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.AMS.ClubDePlongee.serviceRecurrent;

import java.util.Date;
import miage.AMS.ClubDePlongee.entities.Membre;
import miage.AMS.ClubDePlongee.repositories.MembreRepository;
import miage.AMS.ClubDePlongee.rest.MembreController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service permettant d'automatiser les tâches de mise à jours des données des membres
 * @author Miage
 */
@Service
public class updateTask {
    
    @Autowired
    private MembreRepository membreRepo;
    
    Logger logger = LoggerFactory.getLogger(MembreController.class);
    
    /**
     * Met à jour le statut de validité des paiements des membres chaque premier janvier
     */
    
    // Pour les tests, toutes les 5 minutes
    // Chaque premier janvier : 0 0 0 01 01 ?
    @Scheduled(fixedRate = 420000, initialDelay = 420000)
    public void updateDureeValiditePaiement() {
        for (Membre m : this.membreRepo.findAll()) {
            m.setIban(null);
            m.setCotisationValide(false);
            this.membreRepo.save(m);
        }
        logger.info("Statuts de validité de paiement des membres mis à jour");
    }
    
    /**
     * Met à jour les certificats médicaux des membres
     * Cette tâche s'exécute une fois par jour
     */
    // Pour les tests, toutes les 5 minutes
    // Une fois par jour : 0 1 1 * * ?
    @Scheduled(fixedRate = 420000, initialDelay = 420000)
    public void updateCertificatsMedicaux() {
        Date d = new Date();
        for (Membre m : this.membreRepo.findAll()) {
            if(d.after(new Date(m.getDateCertificat().getTime() + 604800000))) {
                m.setApte(false);
            }
            logger.info("Le certificat médical de " + m.getUserLogin() + " n'est plus valide");
            this.membreRepo.save(m);
        }
        logger.info("Fin de mise à jour des certificats médicaux");
    }
}
