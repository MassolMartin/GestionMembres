/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.AMS.ClubDePlongee.rest;

import java.util.Date;
import java.util.Optional;
import miage.AMS.ClubDePlongee.entities.Membre;
import miage.AMS.ClubDePlongee.entities.Niveau;
import miage.AMS.ClubDePlongee.repositories.MembreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Expose les services associés à un membre
 * @author Miage
 */
@RestController
@RequestMapping("/")
public class MembreController {
    
    Logger logger = LoggerFactory.getLogger(MembreController.class);
    
    @Autowired
    private MembreRepository membreRepo;
    
    /**
     * POST Inscription d'un nouveau membre
     * @param m
     * @return le membre inscrit
     */
    @PostMapping("/inscription")
    public Membre inscription(@RequestBody Membre m) {
        logger.info("Inscription réussie !");
        return this.membreRepo.save(m);
    }
    
    /**
     * GET Retourne un membre
     * @param m
     * @return un membre
     */
    @GetMapping("{id}")
    public Membre getMembre(@PathVariable("id") Membre m) {
        Optional<Membre> me = this.membreRepo.findById(m.getId());
        if(!me.isPresent()) {
            logger.info("ERREUR : Ce membre n'existe pas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ce membre n'existe pas");
        } else {
            return m;
        }
    }
    
    /**
     * POST Paiement de sa cotisation par un membre
     * @param m
     * @param iban
     * @return le membre 
     */
    @PostMapping("/cotisation/{id}")
    public Membre paiementCotisation(@PathVariable("id") Membre m,@RequestBody String iban) {
        Optional<Membre> me = this.membreRepo.findById(m.getId());
        if(me.isPresent()) {
            Membre mbre = me.get();
            mbre.setIban(iban);
            mbre.setCotisationValide(Boolean.FALSE);
            logger.info("Cotisation payée avec succès !");
            return this.membreRepo.save(mbre);
        } else {
            logger.info("ERREUR : Ce membre n'existe pas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé");
        }
    }
    
    /**
     * POST validation de la cotisation d'un membre par le secretaire
     * @param m
     * @param numLicence
     * @return le membre
     */
    @PostMapping("/validation/{id}")
    public Membre validationPaiement(@PathVariable("id") Membre m, @RequestBody Long numLicence) {
        Optional<Membre> me = this.membreRepo.findById(m.getId());
        if(me.isPresent()) {
            Membre mbre = me.get();
            if(!mbre.getCotisationValide()) {
                mbre.setCotisationValide(Boolean.TRUE);
                mbre.setNumLicence(numLicence);
                logger.info("Paiement de ce membre validé");
                return this.membreRepo.save(mbre);
            } else {
                logger.info("ERREUR : Ce membre a déjà payé sa cotisation");
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce membre a déjà payé sa cotisation");
            }
        } else {
            logger.info("ERREUR : Ce membre n'existe pas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé");
        }
    }
    
    /**
     * POST MAJ du certificat par le secrétaire
     * @param m
     * @return le membre
     */
    @PostMapping("/certificat/{id}")
    public Membre majCertificat(@PathVariable("id") Membre m) {
        Optional<Membre> me = this.membreRepo.findById(m.getId());
        // Si un membre existe
        if(me.isPresent()) {
            Membre mbre = me.get();
            mbre.setDateCertificat(new Date());
            mbre.setApte(true);
            logger.info("Certificat mis à jour avec succès !");
            return this.membreRepo.save(mbre);
        } else {
            logger.info("ERREUR : Ce membre n'existe pas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé");
        }
    }
    
    /**
     * POST MAJ du niveau d'expertise (entre 1 et 5)
     * @param m
     * @param niveau
     * @return le membre
     */
    @PostMapping("/niveau/{id}")
    public Membre majNiveau(@PathVariable("id") Membre m,@RequestParam("niveau") Niveau niveau) {
        Optional<Membre> me = this.membreRepo.findById(m.getId());
        // Si un membre existe
        if(me.isPresent()) {
            Membre mbre = me.get();
            mbre.setNiveauExpertise(niveau);
            logger.info("Niveau d'expertise mis à jour avec succès !");
            return this.membreRepo.save(mbre);
        } else {
            logger.info("ERREUR : Ce membre n'existe pas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé");
        }
    }
    
    /**
     * GET Liste tous les membres
     * @return 
     */
    @GetMapping
    public Iterable<Membre> listerMembres() {
        return this.membreRepo.findAll();
    }
    
    /**
     * POST Permet de désigner un membre comme enseignant
     * @param m
     * @return 
     */
    @PostMapping("/enseignant/{id}")
    public Membre nouvelEnseignant(@PathVariable("id") Membre m) {
        Optional<Membre> me = this.membreRepo.findById(m.getId());
        // Si un membre existe
        if(me.isPresent()) {
            Membre mbre = me.get();
            if(mbre.getEnseignant() == null || !mbre.getEnseignant()) {
                mbre.setEnseignant(true);
                logger.info("Nouvel enseignant renseigné avec succès");
                return this.membreRepo.save(mbre);
            } else {
                logger.info("ERREUR : Ce membre est déjà enseignant");
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Membre déjà enseignant");
            }
        }else {
            logger.info("ERREUR : Ce membre n'existe pas");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé");
        }
    }
    
    /**
     * GET Liste les enseigants
     * @return la liste des enseignants
     */
    @GetMapping("/enseignants")
    public Iterable<Membre> getListeEnseignants() {
        return this.membreRepo.findAllByEnseignant(true);
    }
    
    /**
     * GET Récupère un membre via son login
     * @param login
     * @return un membre
     */
    @GetMapping("/recherche/{login}")
    public Membre getMembreByLogin(@PathVariable("login") String login) {
        logger.info("Recherche du membre par son login...");
        Membre me = this.membreRepo.findByUserLogin(login);
        if(me == null) {
            logger.info("ERREUR : Membre non trouvé " + login);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membre non trouvé");
        } else {
            return me;
        }
    }
}
