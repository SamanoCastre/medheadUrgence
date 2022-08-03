package com.hopital.app.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopital.app.consumers.IHopitalConsumer;
import com.hopital.app.consumers.IReservationConsumer;
import com.hopital.app.dtos.RechercheRequestDTO;
import com.hopital.app.dtos.ReservationRequestDTO;
import com.hopital.app.entities.Hopital;
import com.hopital.app.entities.Reservation;
import com.hopital.app.entities.Specialite;
import com.hopital.app.services.IHopitalService;
import com.hopital.app.web.HopitalController;

@SuppressWarnings("finally")
@Service
public class HopitalServiceImpl implements IHopitalService {
	private Logger logger = LoggerFactory.getLogger(HopitalServiceImpl.class);
	
	@Autowired
	IHopitalConsumer hopitalConsumer;
	
	

	@Override
	public Hopital rechercherHopital(RechercheRequestDTO request) {
		Hopital hopital = null;
		try {
			hopital = hopitalConsumer.rechercherHopital(request.getLieuIncident(), request.getSpecialite());
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return hopital;
		}
	}

	

}
