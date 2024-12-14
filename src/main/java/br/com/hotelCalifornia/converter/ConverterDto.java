package br.com.hotelCalifornia.converter;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.model.dto.HotelCaliforniaDto;
import org.springframework.beans.BeanUtils;

public class ConverterDto {

    // Conversão DTO -> Model
    public static HotelCaliforniaModel toModel(HotelCaliforniaDto dto) {
        HotelCaliforniaModel model = new HotelCaliforniaModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }

    // Conversão Model -> DTO
    public static HotelCaliforniaDto toDto(HotelCaliforniaModel model) {
        HotelCaliforniaDto dto = new HotelCaliforniaDto();
        dto.setName(model.getName());
        dto.setLocal(model.getLocal());
        dto.setCapacidade(model.getCapacidade());
        dto.setCnpj(model.getCnpj());
        return dto;
    }
}
