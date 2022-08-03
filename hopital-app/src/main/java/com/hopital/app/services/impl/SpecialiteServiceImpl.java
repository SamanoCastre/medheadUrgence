package com.hopital.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopital.app.consumers.IHopitalConsumer;
import com.hopital.app.entities.Specialite;
import com.hopital.app.services.ISpecialiteService;

@Service
public class SpecialiteServiceImpl implements ISpecialiteService{
	@Autowired
	IHopitalConsumer specialiteConsumer;
	
	@Override
	public List<Specialite> getSpecialites() {
	    return this.specialiteConsumer.getSpecialites();
		/*
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
		}*/
	}

}
