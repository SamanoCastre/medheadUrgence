package com.urgence.reservation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.urgence.reservation.dtos.ReservationDTO;
import com.urgence.reservation.entities.Reservation;
import com.urgence.reservation.repositories.ReservationRepository;
import com.urgence.reservation.services.IReservationService;
import com.urgence.reservation.utils.TypeAction;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("finally")
@RestController
public class ReservationRestController {
	Logger logger = LoggerFactory.getLogger(ReservationRestController.class);
	
	@Autowired
	private IReservationService reservationService;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	
	@PostMapping("/reservation")
	public Reservation reserverLitHopital(@ModelAttribute("reservationDTO")  ReservationDTO request) {
		Reservation reservation = null;
		try {
			reservation = this.reservationService.reserverUnLit(request.getHopital_id(), request.getSpecialite_id(), request.getIntervenant());
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return reservation;
		}
	}
	
	@PutMapping("/reservation")
	public Reservation mettreAJourReservation(@RequestParam int reservation_id, @RequestParam String action) {
		Reservation reservation = null;
		try {
			if(TypeAction.ANNULER.get() == action) {
				reservation = this.consulterReservation(reservation_id);
			    reservation.setDate_annulation(new Date());
			} 
			else if(TypeAction.TERMINER.get() == action) {
				reservation = this.consulterReservation(reservation_id);
			    reservation.setDate_fin(new Date());
			}
			else {
				throw new Exception("Action inconnue");
			}
			this.reservationRepository.save(reservation);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return reservation;
		}
	}
	
	@GetMapping("/reservation/{id}")
	public Reservation consulterReservation(@RequestParam("id") int id) {
		Reservation reservation = null;
		try {
			reservation = this.reservationRepository.findById(id).get();
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return reservation;
		}
	}
	
	@GetMapping("/reservations")
	public List<Reservation> listerReservations() {
		List<Reservation> reservations = null;
		try {
			reservations = this.reservationRepository.findAll();
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return reservations;
		}
	}
}
