package br.com.hotelCalifornia.api.controller;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.domain.service.HotelCaliforniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
public class HotelCaliforniaController {

    @Autowired
    private HotelCaliforniaService service;

    @GetMapping
    public List<HotelCaliforniaModel> hotelCaliforniaGetAll() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelCaliforniaModel> getHotelById(@PathVariable Long id) {
        Optional<HotelCaliforniaModel> hotel = service.buscarPorId(id);
        return hotel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<HotelCaliforniaModel> getHotelByCnpj(@PathVariable String cnpj) {
        Optional<HotelCaliforniaModel> hotel = service.buscarPorCnpj(cnpj);
        return hotel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HotelCaliforniaModel> createHotel(@Valid @RequestBody HotelCaliforniaModel hotel) {
        HotelCaliforniaModel savedHotel = service.salvar(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
    }

    @PutMapping
    public ResponseEntity<HotelCaliforniaModel> updateHotel(@RequestBody HotelCaliforniaModel hotel) {
        Optional<HotelCaliforniaModel> updatedHotel = service.atualizar(hotel);
        return updatedHotel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        if (service.deletarPorId(id)) {
            return ResponseEntity.ok().body("Deletado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletar/cnpj/{cnpj}")
    public ResponseEntity<String> deleteHotelCnpj(@PathVariable String cnpj) {
        if (service.deletarPorCnpj(cnpj)) {
            return ResponseEntity.ok().body("Deletado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

