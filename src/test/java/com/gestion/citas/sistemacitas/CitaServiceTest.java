package com.gestion.citas.sistemacitas;

import com.gestion.citas.sistemacitas.model.Cita;
import com.gestion.citas.sistemacitas.repository.CitaRepository;
import com.gestion.citas.sistemacitas.service.CitaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaService citaService;

    @Test
    void guardarCita_DeberiaLanzarExcepcion_CuandoFechaEsNula() {
        Cita citaInvalida = new Cita(1L, 1L, 1L, null, "Pendiente", "Consulta médica");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            citaService.guardarCita(citaInvalida);
        });

        assertEquals("La fecha de la cita no puede ser nula", excepcion.getMessage());
    }

    @Test
    void guardarCita_DeberiaLanzarExcepcion_CuandoFechaEsEnElPasado() {
        LocalDateTime fechaPasada = LocalDateTime.now().minusDays(1);
        Cita citaPasada = new Cita(1L, 1L, 1L, fechaPasada, "Pendiente", "Consulta médica");

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            citaService.guardarCita(citaPasada);
        });

        assertEquals("La fecha de la cita debe ser futura", excepcion.getMessage());
    }

    @Test
    void guardarCita_DeberiaGuardar_CuandoDatosSonCorrectos() {
        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(2);
        Cita citaValida = new Cita(1L, 1L, 1L, fechaFutura, "Pendiente", "Consulta médica");

        when(citaRepository.save(any(Cita.class))).thenReturn(citaValida);

        Cita resultado = citaService.guardarCita(citaValida);

        assertNotNull(resultado);
        assertEquals("Pendiente", resultado.getEstado());
    }
}