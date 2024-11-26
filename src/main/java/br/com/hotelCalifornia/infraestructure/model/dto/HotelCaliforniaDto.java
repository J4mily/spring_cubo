package br.com.hotelCalifornia.infraestructure.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelCaliforniaDto {
    private String name;
    private String local;
    private int capacidade;
    private String cnpj;
}
