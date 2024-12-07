package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByEmailAndDocumento(String email, String documento);

    Page<Medico> findByActivoTrue(Pageable pageable);
}