package bat.tech.lab2.repository;

import bat.tech.lab2.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
