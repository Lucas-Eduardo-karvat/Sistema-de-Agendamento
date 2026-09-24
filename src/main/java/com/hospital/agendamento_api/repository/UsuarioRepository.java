package com.hospital.agendamento_api.repository;

import com.hospital.agendamento_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByPublicId(UUID publicId);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}