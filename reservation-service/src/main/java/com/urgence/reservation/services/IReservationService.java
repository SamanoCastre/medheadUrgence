package com.urgence.reservation.services;

import com.urgence.reservation.entities.Reservation;

public interface IReservationService {
	
	public Reservation reserverUnLit(int hopital_id, int specialisation_id, String intervenant);

}
