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

@Service
public class HotelCaliforniaService {

    @Autowired
    private HotelCaliforniaRepository repository;

    public HotelCaliforniaModel salvar(HotelCaliforniaModel dto) {
        try {

            if(repository.findByCnpj(dto.getCnpj()).isPresent()){
                throw new RuntimeException("Cnpj duplicado");
            }

            HotelCaliforniaDto hotel = toModel(dto);
            HotelCaliforniaModel saveHotel = repository.save(dto);

            return repository.save(dto);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar o hotel: " + dto.getName(), e);
        }
    }

    private HotelCaliforniaDto toModel(HotelCaliforniaModel hotel) {
        HotelCaliforniaDto dto = new HotelCaliforniaDto();
        dto.setName(hotel.getName());
        dto.setLocal(hotel.getLocal());
        dto.setCapacidade(hotel.getCapacidade());
        dto.setCnpj(hotel.getCnpj());

        BeanUtils.copyProperties(hotel, dto);
        return dto;
    }

    public List<HotelCaliforniaModel> listarTodos() {
        try {
            return repository.findAll();
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
