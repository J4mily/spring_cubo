package br.com.hotelCalifornia.domain.service;

import br.com.hotelCalifornia.infraestructure.model.HotelCaliforniaModel;
import br.com.hotelCalifornia.infraestructure.repository.HotelCaliforniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HotelCaliforniaService {

    @Autowired
    private HotelCaliforniaRepository repository;

    public List<HotelCaliforniaModel> listarTodos() {
        return repository.findAll();
    }

    public Optional<HotelCaliforniaModel> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public HotelCaliforniaModel salvar(HotelCaliforniaModel hotel) {
        return repository.save(hotel);
    }

    public Optional<HotelCaliforniaModel> atualizar(HotelCaliforniaModel hotel) {
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
    }

    public boolean deletar(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
