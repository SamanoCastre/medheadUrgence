package com.hopital.app.web;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hopital.app.dtos.RechercheRequestDTO;
import com.hopital.app.dtos.ReservationRequestDTO;
import com.hopital.app.entities.Hopital;
import com.hopital.app.entities.Reservation;
import com.hopital.app.entities.Specialite;
import com.hopital.app.services.IHopitalService;
import com.hopital.app.services.IReservationService;
import com.hopital.app.services.ISpecialiteService;

@SuppressWarnings("finally")
@Controller
public class HopitalController {
	Logger logger = LoggerFactory.getLogger(HopitalController.class);
	
	@Autowired
	IHopitalService hopitalService;
	
	@Autowired 
	IReservationService reservationService;
	
	@Autowired
	ISpecialiteService specialiteService;
	
	
	@RequestMapping(value={"/", "/index"})
	public String index(Model model, RedirectAttributes redirectAttributes) {
		List<Specialite> specialites = this.specialiteService.getSpecialites();
		model.addAttribute("specialites", specialites);
		return "index";
	}
	
	
	@PostMapping(value={"/rechercherHopital"})
	public String lancerRecherche(Model model, @ModelAttribute RechercheRequestDTO request) {
		try {
			List<Specialite> specialites = this.specialiteService.getSpecialites();
			model.addAttribute("specialites", specialites);
			
			if(!request.valid()) {
				throw new Exception("RechercheRequestDTO invalid");
			}
			
			Hopital hopital = this.hopitalService.rechercherHopital(request);
			if(hopital == null) {
				throw new Exception("Hopital null from Rest API");
			}
			model.addAttribute("hopital", hopital);
			model.addAttribute("specialite_id",request.getSpecialite());
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
			model.addAttribute("searchError", true);
		}
		finally {
			return "index";
		}
	}
	
	@PostMapping(value={"/reserverHopital"})
	public String reserver(Model model, @ModelAttribute ReservationRequestDTO request ) {
		try {
			List<Specialite> specialites = this.specialiteService.getSpecialites();
			model.addAttribute("specialites", specialites);
			
			if(!request.valid()) {
				throw new Exception("ReservationRequestDTO invalid");
			}
			Reservation reservation = this.reservationService.reserverLitHopital(request);
			if(reservation == null) {
				throw new Exception("Reservation null from Rest API");
			}
			model.addAttribute("reservation", reservation);
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
