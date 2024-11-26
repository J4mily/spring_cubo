package br.com.hotelCalifornia.domain.service;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.model.dto.HotelCaliforniaDto;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelCaliforniaService {

    @Autowired
    private HotelCaliforniaRepository repository;

    public HotelCaliforniaDto  salvar(HotelCaliforniaDto dto) {
        try {

            if(repository.findByCnpj(dto.getCnpj()).isPresent()){
                throw new RuntimeException("Cnpj duplicado");
            }

            HotelCaliforniaModel hotelModel = toModel(dto);
            HotelCaliforniaModel savedHotel = repository.save(hotelModel);

            return toDto(savedHotel);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar o hotel: " + dto.getName(), e);
        }
    }

    // Conversão DTO -> Model
    private HotelCaliforniaModel toModel(HotelCaliforniaDto dto) {
        HotelCaliforniaModel model = new HotelCaliforniaModel();
        BeanUtils.copyProperties(dto, model);
        return model;
    }

    // Conversão Model -> DTO
    private HotelCaliforniaDto toDto(HotelCaliforniaModel model) {
        HotelCaliforniaDto dto = new HotelCaliforniaDto();
        dto.setName(model.getName());
        dto.setLocal(model.getLocal());
        dto.setCapacidade(model.getCapacidade());
        dto.setCnpj(model.getCnpj());
        return dto;
    }

    public List<HotelCaliforniaDto> listarTodos() {
        try {
            List<HotelCaliforniaModel> hotels = repository.findAll();
            return hotels.stream().map(this::toDto).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar todos os hotéis: " + e.getMessage(), e);
        }
    }

    public Optional<HotelCaliforniaModel> buscarPorId(Long id) {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar hotel com o ID: " + id, e);
        }
    }

    public Optional<HotelCaliforniaModel> buscarPorCnpj(String cnpj) {
        try {
            return repository.findByCnpj(cnpj);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao buscar hotel com o CNPJ: " + cnpj, e);
        }
    }

    public Optional<HotelCaliforniaModel> atualizar(HotelCaliforniaModel hotel) {
        try {
            if (repository.existsById(hotel.getId())) {
                HotelCaliforniaModel hotelAtualizado = repository.findById(hotel.getId()).get();
                hotelAtualizado.setName(hotel.getName());
                hotelAtualizado.setLocal(hotel.getLocal());
                hotelAtualizado.setCapacidade(hotel.getCapacidade());
                hotelAtualizado.setCnpj(hotel.getCnpj());
                return Optional.of(repository.save(hotelAtualizado));
            } else {
                return Optional.empty();
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao atualizar o hotel com ID: " + hotel.getId(), e);
        }
    }

    public boolean deletarPorId(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao deletar hotel com o ID: " + id, e);
        }
    }

    @Transactional
    public boolean deletarPorCnpj(String cnpj) {
        try {
            Optional<HotelCaliforniaModel> hotel = repository.findByCnpj(cnpj);
            if (hotel.isPresent()) {
                repository.deleteByCnpj(cnpj);
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao deletar hotel com o CNPJ: " + cnpj, e);
        }
    }
}
