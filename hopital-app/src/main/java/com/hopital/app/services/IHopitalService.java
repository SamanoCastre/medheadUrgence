package com.hopital.app.services;

import com.hopital.app.dtos.RechercheRequestDTO;
import com.hopital.app.entities.Hopital;

public interface IHopitalService {
	
	public Hopital rechercherHopital(RechercheRequestDTO request);
}
