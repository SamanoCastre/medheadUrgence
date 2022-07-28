package com.emergency.system.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.emergency.system.entities.Disponibilite;

public interface DisponibiliteRepository extends MongoRepository<Disponibilite, Integer>{
	public Disponibilite save(Disponibilite disponibilite);
	
	
	@Query("{ 'specialite._id' : ?0 }") 
	public List<Disponibilite> findBySpecialiteId(int id);
	
	@Query("{ 'hopital._id' : ?0,  'specialite._id' : ?1}") 
	public List<Disponibilite> findByHopitalAndSpecialite(int hopital_id, int specialite_id);
}

