package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.Rota;
import com.service.RotaService; // Importe o serviço aqui

@RestController
@RequestMapping("/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService; // Injete aqui o serviço que lida com as rotas

    @GetMapping("/concluidas")
    public ResponseEntity<List<Rota>> obterRotasConcluidas() {
        // Implemente a lógica para obter todas as rotas concluídas
        List<Rota> rotasConcluidas = rotaService.obterRotasConcluidas();
        return ResponseEntity.ok(rotasConcluidas);
    }
}
