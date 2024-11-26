package br.com.hotelCalifornia.api.controller;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.domain.service.HotelCaliforniaService;
import br.com.hotelCalifornia.infraestructure.model.dto.HotelCaliforniaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
@Tag(name = "Hotels", description = "Endpoints for managing hotels")
public class HotelCaliforniaController {

    @Autowired
    private HotelCaliforniaService service;

    @GetMapping
    @Operation(summary = "Find all hotels", description = "Retrieve a list of all hotels", tags = {"Hotels"}, responses = {
            @ApiResponse(description = "Sucess", responseCode = "200",
            content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = HotelCaliforniaDto.class)))),
    @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public List<HotelCaliforniaDto> hotelCaliforniaGetAll() {
        return service.listarTodos();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Find a hotel by ID", description = "Retrieve hotel details by its ID",
            tags = {"Hotels"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HotelCaliforniaDto.class))),
            @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ResponseEntity<HotelCaliforniaModel> getHotelById(@PathVariable Long id) {
        Optional<HotelCaliforniaModel> hotel = service.buscarPorId(id);
        return hotel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Find a hotel by CNPJ", description = "Retrieve hotel details by its CNPJ",
            tags = {"Hotels"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HotelCaliforniaDto.class))),
            @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ResponseEntity<HotelCaliforniaModel> getHotelByCnpj(@PathVariable String cnpj) {
        Optional<HotelCaliforniaModel> hotel = service.buscarPorCnpj(cnpj);
        return hotel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a hotel", description = "Add a new hotel to the system",
            tags = {"Hotels"}, responses = {
            @ApiResponse(description = "Created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = HotelCaliforniaDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ResponseEntity<HotelCaliforniaDto> createHotel(@Valid @RequestBody HotelCaliforniaDto hotel) {
        HotelCaliforniaDto savedHotel = service.salvar(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
    }

    @PutMapping
    @Operation(summary = "Update a hotel", description = "Update details of an existing hotel",
            tags = {"Hotels"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HotelCaliforniaDto.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
            @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ResponseEntity<HotelCaliforniaModel> updateHotel(@RequestBody HotelCaliforniaModel hotel) {
        Optional<HotelCaliforniaModel> updatedHotel = service.atualizar(hotel);
        return updatedHotel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a hotel by ID", description = "Remove a hotel from the system by its ID",
            tags = {"Hotels"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        if (service.deletarPorId(id)) {
            return ResponseEntity.ok().body("Deletado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletar/cnpj/{cnpj}")
    @Operation(summary = "Delete a hotel by CNPJ", description = "Remove a hotel from the system by its CNPJ",
            tags = {"Hotels"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
    })
    public ResponseEntity<String> deleteHotelCnpj(@PathVariable String cnpj) {
        if (service.deletarPorCnpj(cnpj)) {
            return ResponseEntity.ok().body("Deletado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

