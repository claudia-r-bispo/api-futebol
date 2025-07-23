package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.repository.AddressRepository;
import com.neoCamp.footballMatch.response.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ViaCepService viaCepService;

    public AddressEntity save(AddressEntity address) {
        return addressRepository.save(address);
    }

    public Optional<AddressEntity> findById(Long id) {
        return addressRepository.findById(id);
    }

    public List<AddressEntity> findAll() {
        return addressRepository.findAll();
    }

    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    public AddressEntity createAddressPorCep(String cep) {
        ViaCepResponse response = viaCepService.buscarCep(cep);
        AddressEntity address = new AddressEntity();
        address.setStreet(response.getLogradouro());
        address.setNumber(""); // Pode ser preenchido depois
        address.setCity(response.getCidade()); // Corrigido: getCidade()
        address.setState(response.getEstado()); // Corrigido: getEstado()
        address.setZipCode(response.getCep());
        return addressRepository.save(address);
    }
}
