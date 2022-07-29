package com.hopital.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopital.app.dtos.RechercheRequestDTO;
import com.hopital.app.dtos.ReservationDTO;
import com.hopital.app.entities.Hopital;
import com.hopital.app.entities.Reservation;
import com.hopital.app.entities.Specialite;

@SuppressWarnings("finally")
@Service
public class HopitalService implements IHopitalService {
	Logger logger = LoggerFactory.getLogger(HopitalService.class);
	@Value("${HOPITAL_SERVICE_URI}")
    private String HOPITAL_SERVICE_URI;
	
	@Value("${RESERVATION_SERVICE_URI}")
    private String RESERVATION_SERVICE_URI;
	
	@Override
	public Specialite[] getSpecialites() {
		Specialite[] specialites = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Specialite[]> response = restTemplate.getForEntity(this.HOPITAL_SERVICE_URI + "specialites",Specialite[].class);
			specialites = response.getBody();
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return specialites;
		}
	}


	@Override
	public Hopital rechercherHopital(String lieuIncident, int specialite_id) {
		Hopital hopital = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.HOPITAL_SERVICE_URI + "hopital").queryParam("lieuIncident",lieuIncident).queryParam("specialite", specialite_id);
			String uri = builder.toUriString();
			String response = restTemplate.getForObject(builder.toUriString(),String.class);
			ObjectMapper mapper = new ObjectMapper();
			hopital = mapper.readValue(response, Hopital.class);
			
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return hopital;
		}
	}


	@Override
	public Reservation reserverLitHopital(ReservationDTO reservationDTO) {
		Reservation reservation = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    HttpEntity<String> request = new HttpEntity<String>(reservationDTO.toString(), headers);
		    String response = restTemplate.postForObject(this.RESERVATION_SERVICE_URI + "reservation", request, String.class);
		    ObjectMapper mapper = new ObjectMapper();
			reservation = mapper.readValue(response, Reservation.class);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return reservation;
		}
	}

}
