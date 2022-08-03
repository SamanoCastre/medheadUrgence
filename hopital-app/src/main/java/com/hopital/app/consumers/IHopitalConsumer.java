package com.hopital.app.consumers;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hopital.app.entities.Hopital;
import com.hopital.app.entities.Specialite;



@FeignClient(name="hopital-service", url="${HOPITAL_SERVICE_URI}")
public interface IHopitalConsumer {
	
	@GetMapping("/hopital")
	public Hopital rechercherHopital(@RequestParam("lieuIncident") String lieuIncident, @RequestParam("specialite") int specialite);
	
	@GetMapping("/specialites")
	public List<Specialite> getSpecialites();
	
}
