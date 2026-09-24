package com.hospital.agendamento_api.repository;

import com.hospital.agendamento_api.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findByPublicId(UUID publicId);
    
    // Busca histórico de agendamentos de um paciente específico
    List<Agendamento> findByPacienteId(Long pacienteId);
    
    // Busca agendamentos filtrados por status (ex: "PENDENTE", "CONFIRMADO")
    List<Agendamento> findByStatus(String status);
    
    // Validação para evitar agendamento duplicado no mesmo horário para o mesmo paciente
    boolean existsByPacienteIdAndDataOpcao1(Long pacienteId, OffsetDateTime dataOpcao1);
}