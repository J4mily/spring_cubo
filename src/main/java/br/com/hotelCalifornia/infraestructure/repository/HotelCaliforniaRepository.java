package br.com.hotelCalifornia.infraestructure.repository;

import java.util.Optional;
import java.util.UUID;

import br.com.hotelCalifornia.infraestructure.model.dto.HotelCaliforniaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;

@Repository
public interface HotelCaliforniaRepository extends JpaRepository<HotelCaliforniaModel, Long>{

    @Query(value = "SELECT * FROM hotel_california WHERE cnpj = :cnpj", nativeQuery = true)
    Optional<HotelCaliforniaModel> findByCnpj(@Param("cnpj") String cnpj);

    @Modifying
    @Query(value = "DELETE FROM hotel_california WHERE cnpj = :cnpj", nativeQuery = true)
    void deleteByCnpj(@Param("cnpj") String cnpj);


}
