package io.github.offjaao.algalog.domain.service;

import io.github.offjaao.algalog.domain.exceptions.ClientException;
import io.github.offjaao.algalog.modal.Client;
import io.github.offjaao.algalog.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CRUDClient {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        final boolean foundedClient = clientRepository.findByEmail(client.getEmail())
                .stream()
                .anyMatch($ -> !$.equals(client));

        if (foundedClient) {
            throw new ClientException("Já existe um cliente cadastrado com este e-mail.");
        }
        return clientRepository.save(client);
    }

    @Transactional
    public void delete(Long clientId) {
        clientRepository.deleteById(clientId);
    }

}
