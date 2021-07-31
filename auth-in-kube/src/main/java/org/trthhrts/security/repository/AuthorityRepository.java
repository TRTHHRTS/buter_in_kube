package org.trthhrts.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trthhrts.security.model.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
