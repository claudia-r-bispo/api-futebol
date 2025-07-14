package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.repository.AddressRepository;
import com.neoCamp.footballMatch.response.ViaCepResponse; // ← Import correto
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ViaCepService viaCepService;

    public AddressService(AddressRepository addressRepository, ViaCepService viaCepService) {
        this.addressRepository = addressRepository;
        this.viaCepService = viaCepService;
    }


    public AddressEntity createAddressPorCep(String cep) {

        ViaCepResponse viaCepResponse = viaCepService.buscarCep(cep);


        if (viaCepResponse.getLogradouro() == null || viaCepResponse.getLogradouro().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP informado não possui logradouro válido");
        }


        AddressEntity endereco = new AddressEntity();
        endereco.setLogradouro(viaCepResponse.getLogradouro());
        endereco.setCidade(viaCepResponse.getCidade());
        endereco.setEstado(viaCepResponse.getEstado());
        endereco.setCep(viaCepResponse.getCep());

        return addressRepository.save(endereco);
    }
}