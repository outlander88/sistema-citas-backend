package com.gestion.citas.sistemacitas.service;

import com.gestion.citas.sistemacitas.model.Cita;
import com.gestion.citas.sistemacitas.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Cita guardarCita(Cita cita) {
        if (cita.getFechaHora() == null) {
            throw new IllegalArgumentException("La fecha de la cita no puede ser nula");
        }
        
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la cita debe ser futura");
        }

        return citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasPorUsuario(Long idUsuario) {
        return citaRepository.findByIdUsuario(idUsuario);
    }
}