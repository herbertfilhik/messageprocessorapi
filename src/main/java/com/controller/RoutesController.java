package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.RouteModel;
import com.service.RouteKafkaConsumer;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/routes")
public class RoutesController {

   @Autowired
   private RouteKafkaConsumer rotaKafkaConsumer;

   @GetMapping("/completed")
   @Operation(summary = "Get completed routes", description = "Returns a list of completed routes.")
   public ResponseEntity<List<RouteModel>> getCompletedRoutes() {
      List<RouteModel> completedRoutes = rotaKafkaConsumer.getCompletedRoutes();
      return ResponseEntity.ok(completedRoutes);
   }
}
