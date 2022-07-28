package com.urgence.reservation.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.urgence.reservation.entities.Reservation;
import com.urgence.reservation.repositories.ReservationRepository;

@Service
public class ReservationServiceImpl implements IReservationService{
	@Autowired
	ReservationRepository repository;

	@Override
	public Reservation reserverUnLit(int hopital_id, int specialite_id, String intervenant) {
		Reservation reservation = new Reservation(0, hopital_id, specialite_id, new Date(), null, null, intervenant); 
		reservation =  this.repository.save(reservation);
		RestTemplate restTemplate = new RestTemplate();
		
		return reservation;
	}
}
