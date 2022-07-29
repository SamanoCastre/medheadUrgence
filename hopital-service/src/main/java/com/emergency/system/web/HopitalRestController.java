package com.emergency.system.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emergency.system.dtos.DisponibiliteRequestDTO;
import com.emergency.system.dtos.RechercheRequestDTO;
import com.emergency.system.entities.Disponibilite;
import com.emergency.system.entities.Hopital;
import com.emergency.system.entities.Specialite;
import com.emergency.system.repositories.DisponibiliteRepository;
import com.emergency.system.repositories.HopitalRepository;
import com.emergency.system.repositories.SpecialiteRepository;
import com.emergency.system.services.IHopitalService;
import com.emergency.system.services.SequenceGeneratorService;


@SuppressWarnings("finally")
@RefreshScope
@RestController
public class HopitalRestController {
	 Logger logger = LoggerFactory.getLogger(HopitalRestController.class);
	
	@Autowired
	private IHopitalService hopitalService;
	
	@Autowired
	private SpecialiteRepository specialiteRepository; 
	
	@Autowired
	private HopitalRepository hopitalRepository;
	
	@Autowired
	private DisponibiliteRepository disponibiliteRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/hopital")
	public Hopital rechercherHopital(@RequestParam("lieuIncident") String lieuIncident, @RequestParam("specialite") int specialite) {
		Hopital hopital = null;
		try {
			if(lieuIncident == null || specialite <=0) {
				throw new Exception("Le lieu de l'incident ainsi que la spécialité est requis pour éffectuer la recherche");
			}
			hopital = this.hopitalService.rechercherHopital(lieuIncident, specialite);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return hopital;
		}
	}

	
	@PostMapping("/disponibilite")
	public Disponibilite enregistrerDisponibilite(@RequestBody DisponibiliteRequestDTO request) {
		Disponibilite disponibilite = null;
		
		try {
			if(!request.valid()) {
				throw new Exception("DisponibiliteRequestDTO invalid");
			}
			Specialite specialite = this.specialiteRepository.findById(request.getSpecialite_id());
			Hopital hopital = this.hopitalRepository.findById(request.getHopital_id());
			disponibilite = new Disponibilite(sequenceGeneratorService.generateSequence(Disponibilite.SEQUENCE_NAME),hopital, specialite, request.getLits(),new Date());
			this.disponibiliteRepository.save(disponibilite);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return disponibilite;
		}
	}
	
	@PutMapping("/disponibilite")
	public Disponibilite mettreajourDisponibilite(@RequestBody DisponibiliteRequestDTO request) {
		Disponibilite disponibilite = null;
		
		try {
			if(!request.valid()) {
				new Exception("DisponibiliteRequestDTO invalid");
			}
		    disponibilite = this.getDisponibilite(request.getHopital_id(), request.getSpecialite_id());
		    disponibilite.setLits(request.getLits());
		    this.disponibiliteRepository.save(disponibilite);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return disponibilite; 
		}
	}
	@GetMapping("/disponibilite")
	public Disponibilite getDisponibilite(@RequestParam("hopital_id") int hopital_id, @RequestParam("specialite_id") int specialite_id ) {
		Disponibilite disponibilite = null;
		try {
			if(hopital_id<0 || specialite_id <0) {
				throw new Exception("DisponibiliteRequestDTO invalid");
			}
			Specialite specialite = this.specialiteRepository.findById(specialite_id);
			Hopital hopital = this.hopitalRepository.findById(hopital_id);
			List<Disponibilite> disponibilites =  this.disponibiliteRepository.findByHopitalAndSpecialite(hopital.getId(), specialite.getId());
			disponibilite = disponibilites.get(0);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return disponibilite;
		}
	}

	
	@GetMapping("/specialites")
	public List<Specialite> getSpecialites() {
		List<Specialite> specialites = new ArrayList<Specialite>();
		try {
			specialites = this.specialiteRepository.findAll();
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return specialites;
		}
	}
}

