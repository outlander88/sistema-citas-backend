package com.gestion.citas.sistemacitas.controller;

import com.gestion.citas.sistemacitas.model.Cita;
import com.gestion.citas.sistemacitas.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.guardarCita(cita);
            return ResponseEntity.ok(nuevaCita);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Cita>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(citaService.obtenerCitasPorUsuario(idUsuario));
    }
}