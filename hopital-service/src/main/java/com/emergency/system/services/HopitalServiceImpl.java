package com.emergency.system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emergency.system.entities.Disponibilite;
import com.emergency.system.entities.GoogleDistanceMatrixApiResponse;
import com.emergency.system.entities.Hopital;
import com.emergency.system.repositories.DisponibiliteRepository;

@Service
public class HopitalServiceImpl implements IHopitalService{
	
	@Autowired
	private DisponibiliteRepository disponibiliteRepository;
	
	@Autowired
	private IGoogleDistanceMatrix distanceService;
	 

	@Override
	public Hopital rechercherHopital(String lieu, int specialiteId) {
		
		List<Disponibilite> listDisponibilites = this.disponibiliteRepository.findBySpecialiteId(specialiteId);
		
		Hopital hopital = null;
		long distance = -1;
		
		for (Disponibilite disponibilite : listDisponibilites) {
			try {
				GoogleDistanceMatrixApiResponse googleDistanceMatrixApiResponse = distanceService.compute(lieu, disponibilite.getHopital().getAddress().getCodePostal() + " " + disponibilite.getHopital().getAddress().getVille()+", "+ disponibilite.getHopital().getAddress().getPays());
				
				long distanceEnCours = distanceService.getDistance(googleDistanceMatrixApiResponse);
				
				if(distance <0 || distanceEnCours < distance ) {
					distance = distanceEnCours;
					hopital = disponibilite.getHopital();
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return hopital;
	}
}
