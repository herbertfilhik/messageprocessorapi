package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.modelo.Rota;

@Service
public class RotaService {

    // URL do route-generator-main (ajuste de acordo com a sua configuração)
    private static final String ROUTE_GENERATOR_URL = "http://localhost:8088"; // Substitua pela URL correta

    @Autowired
    private RestTemplate restTemplate; // Injete o RestTemplate para fazer chamadas HTTP

    // Implemente a lógica para obter as rotas concluídas do route-generator-main
    public List<Rota> obterRotasConcluidas() {
        // Faça uma chamada HTTP para o route-generator-main para buscar as rotas concluídas
        // Por exemplo, você pode usar restTemplate para fazer a chamada GET para o endpoint apropriado
        Rota[] rotasArray = restTemplate.getForObject(ROUTE_GENERATOR_URL + "/rotas/concluidas", Rota[].class);

        // Converta o array em uma lista de rotas
        List<Rota> rotasConcluidas = new ArrayList<>();
        if (rotasArray != null) {
            for (Rota rota : rotasArray) {
                rotasConcluidas.add(rota);
            }
        }

        return rotasConcluidas;
    }
}
