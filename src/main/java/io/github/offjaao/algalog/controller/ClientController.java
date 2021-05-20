package io.github.offjaao.algalog.controller;

import io.github.offjaao.algalog.modal.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
public class ClientController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/clientes")
    public List<Client> list() {
        return entityManager.createQuery("from Client", Client.class)
                .getResultList();
    }

}
