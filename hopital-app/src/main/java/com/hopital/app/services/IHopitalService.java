package com.hopital.app.services;

import com.hopital.app.dtos.ReservationDTO;
import com.hopital.app.entities.Hopital;
import com.hopital.app.entities.Reservation;
import com.hopital.app.entities.Specialite;

public interface IHopitalService {
	public Specialite[] getSpecialites();
	public Hopital rechercherHopital(String lieuIncident, int specialite_id);
	public Reservation reserverLitHopital(ReservationDTO reservationDTO);
}
