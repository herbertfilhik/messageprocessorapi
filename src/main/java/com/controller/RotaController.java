package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.Rota;
import com.service.RotaKafkaConsumer;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rotas")
public class RotaController {

   @Autowired
   private RotaKafkaConsumer rotaKafkaConsumer;

   @GetMapping("/concluidas")
   @Operation(summary = "Obter rotas concluídas", description = "Retorna uma lista de rotas concluídas.")
   public ResponseEntity<List<Rota>> obterRotasCompletas() {
       List<Rota> rotasCompletas = rotaKafkaConsumer.obterRotasCompletas();
       return ResponseEntity.ok(rotasCompletas);
   }
}
