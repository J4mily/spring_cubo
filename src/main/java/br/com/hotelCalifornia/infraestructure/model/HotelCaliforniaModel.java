package br.com.hotelCalifornia.infraestructure.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "hotel_california")
public class HotelCaliforniaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank(message = "Nome do hotel é obrigatório")
    @Size(max = 100, message = "O nome do hotel não pode ter mais que 100 caracteres")
    private String name;

    @Column(name = "local")
    @NotNull
    @NotBlank(message = "Local é obrigatório")
    @Size(max = 200, message = "A localização do hotel não pode ter mais que 200 caracteres")
    private String local;

    @Column(name = "capacidade")
    @NotNull(message = "Capacidade é obrigatória")
    @Min(value = 1, message = "Capacidade deve ser pelo menos 1")
    private Integer capacidade;

    @Column(name = "cnpj")
    @NotNull
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;
}
