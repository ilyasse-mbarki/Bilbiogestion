package com.example.biblio_Api.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblio_Api.Exceptions.LivresNotFoundException;
import com.example.biblio_Api.entities.Livre;
import com.example.biblio_Api.repos.LivreRepo;

@RestController
@RequestMapping("emsi_api")
public class LivreController {
	private final LivreRepo repo;
	LivreController(LivreRepo repo)
	{
		this.repo = repo;
	}
	 // Aggregate root
	  // tag::get-aggregate-root[]
	  @GetMapping("/livres")
	  List<Livre> all() {
	    return repo.findAllByOrderByDateAsc();
	  }
	  // end::get-aggregate-root[]
	  @PostMapping("/ajout_livre")
	  Livre newLivre(Livre newLivre) {
	    return repo.save(newLivre);
	  }
	  
	  @GetMapping("/livres/{id}")
	  Livre one(@PathVariable Long id) {
	    
	    return repo.findById(id).orElseThrow(() -> new LivresNotFoundException(id));
	  }
	  @PutMapping("/livres/{id}")
	  Livre replaceEmployee(@RequestBody Livre newLivre, @PathVariable Long id) {
	    
	    return repo.findById(id)
	      .map(livre -> {
	        livre.setTitre(newLivre.getTitre());
	        livre.setMaisonEdition(newLivre.getMaisonEdition());
	        livre.SetDateSortie(newLivre.getDate_sortie());
	        livre.setAuteur(newLivre.getAuteur());
	        livre.SetNbrPage(newLivre.getNbr_page());
	        livre.setIsbn(newLivre.getIsbn());
	        livre.setIsDispo(newLivre.getIsDispo());
	        livre.setDateDernierConsu(newLivre.getDateDernierConsu());
	        return repo.save(livre);
	      })
	      .orElseGet(() -> {
	        newLivre.setId(id);
	        return repo.save(newLivre);
	      });
	  }
	  
	  @DeleteMapping("/livres/{id}")
	  void deleteEmployee(@PathVariable Long id) {
	    repo.deleteById(id);
	  }
	  
}
