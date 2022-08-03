package com.hopital.app.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopital.app.consumers.IReservationConsumer;
import com.hopital.app.dtos.ReservationRequestDTO;
import com.hopital.app.entities.Reservation;
import com.hopital.app.services.IReservationService;

@Service
@SuppressWarnings("finally")
public class ReservationServiceImpl implements IReservationService{
	private Logger logger = LoggerFactory.getLogger(HopitalServiceImpl.class);
	@Autowired
	IReservationConsumer reservationConsumer;
	
	@Override
	public Reservation reserverLitHopital(ReservationRequestDTO reservationDTO) {
		Reservation reservation = null;
		try {
			reservation = reservationConsumer.reserverLitHopital(reservationDTO);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return reservation;
		}
	}
}
