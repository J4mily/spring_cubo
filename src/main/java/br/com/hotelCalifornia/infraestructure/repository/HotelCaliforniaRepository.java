package br.com.hotelCalifornia.infraestructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;

@Repository
public interface HotelCaliforniaRepository extends JpaRepository<HotelCaliforniaModel, Long>{
    
}
