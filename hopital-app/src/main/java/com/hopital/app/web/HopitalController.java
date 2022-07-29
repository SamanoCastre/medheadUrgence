package com.hopital.app.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hopital.app.dtos.RechercheRequestDTO;
import com.hopital.app.dtos.ReservationDTO;
import com.hopital.app.entities.Hopital;
import com.hopital.app.entities.Reservation;
import com.hopital.app.entities.Specialite;
import com.hopital.app.services.IHopitalService;

@SuppressWarnings("finally")
@Controller
public class HopitalController {
	Logger logger = LoggerFactory.getLogger(HopitalController.class);
	
	@Autowired
	IHopitalService hopitalService;
	
	
	@RequestMapping(value={"/", "/index"})
	public String index(Model model) {
		Specialite[] specialites = this.hopitalService.getSpecialites();
		model.addAttribute("specialites", specialites);
		return "index";
	}
	
	
	@PostMapping(value={"/rechercherHopital"})
	public String lancerRecherche(Model model, @RequestParam("lieuIncident") String lieuIncident, @RequestParam("id") int id) {
		
		try {
			Specialite[] specialites = this.hopitalService.getSpecialites();
			model.addAttribute("specialites", specialites);
			
			if(lieuIncident == null || id <= 0) {
				throw new Exception("Formulaire invalid");
			}
			Hopital hopital = this.hopitalService.rechercherHopital(lieuIncident, id);
			if(hopital == null) throw new Exception("Hopital null from Rest API");
			model.addAttribute("hopital", hopital);
			model.addAttribute("specialite_id",id);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
			model.addAttribute("searchError", true);
		}
		finally {
			return "index";
		}
	}
	
	@GetMapping(value={"/reserver"})
	public String reserver(Model model,@RequestParam("hopital") int hopital_id, @RequestParam("specialite") int specialite_id, @RequestParam("intervenant") String intervenant ) {
		try {
			
			if(hopital_id <= 0 || specialite_id <= 0) {
				throw new Exception("Formulaire invalid");
			}
			
			ReservationDTO reservationDTO = new ReservationDTO(hopital_id,specialite_id,intervenant);
			
			Reservation reservation = this.hopitalService.reserverLitHopital(reservationDTO);
			if(reservation == null) throw new Exception("Reservation null from Rest API");
			model.addAttribute("reservation", reservation);
			model.addAttribute("specialite_id",specialite_id);
			model.addAttribute("reservationSuccess", true);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
			model.addAttribute("reservationError", true);
		}
		finally {
			return "index";
		}
	}
	
}
