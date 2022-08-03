package com.hopital.app.consumers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hopital.app.dtos.ReservationRequestDTO;
import com.hopital.app.entities.Reservation;

@FeignClient(name="reservation-service", url="${RESERVATION_SERVICE_URI}")
public interface IReservationConsumer {
	@PostMapping("/reservation")
	public Reservation reserverLitHopital(@ModelAttribute("reservationDTO")  ReservationRequestDTO request);
	
	@PutMapping("/reservation")
	public Reservation mettreAJourReservation(@RequestParam int reservation_id, @RequestParam String action);
	
	@GetMapping("/reservation/{id}")
	public Reservation consulterReservation(@RequestParam("id") int id);
}
