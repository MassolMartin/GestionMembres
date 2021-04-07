/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.AMS.ClubDePlongee.repositories;

import miage.AMS.ClubDePlongee.entities.Membre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Reppository d'un membre
 * @author Miage
 */
@Repository
public interface MembreRepository extends CrudRepository<Membre,Long> {

    public Iterable<Membre> findAllByEnseignant(boolean b);
    
    public Membre findByUserLogin(String mLogin);
    
}
