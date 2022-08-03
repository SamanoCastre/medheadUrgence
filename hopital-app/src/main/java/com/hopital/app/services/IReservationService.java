package com.hopital.app.services;

import com.hopital.app.dtos.ReservationRequestDTO;
import com.hopital.app.entities.Reservation;

public interface IReservationService {
	public Reservation reserverLitHopital(ReservationRequestDTO reservationDTO);
}
