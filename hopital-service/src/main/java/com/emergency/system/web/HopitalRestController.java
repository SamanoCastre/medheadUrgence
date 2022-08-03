package com.emergency.system.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.emergency.system.entities.Hopital;
import com.emergency.system.services.IHopitalService;


@SuppressWarnings("finally")
@RefreshScope
@RestController
public class HopitalRestController {
	 Logger logger = LoggerFactory.getLogger(HopitalRestController.class);
	
	@Autowired
	private IHopitalService hopitalService;
	
	@GetMapping("/hopital")
	public Hopital rechercherHopital(@RequestParam("lieuIncident") String lieuIncident, @RequestParam("specialite") int specialite) {
		Hopital hopital = null;
		try {
			if(lieuIncident == null || specialite <=0) {
				throw new Exception("Le lieu incident ou spécialité incorrect");
			}
			hopital = this.hopitalService.rechercherHopital(lieuIncident, specialite);
		}
		catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		finally {
			return hopital;
		}
	}
}

