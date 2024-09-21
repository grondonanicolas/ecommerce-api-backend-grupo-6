package org.apis.ecommerce.infrastructure.repositories;

import org.apis.ecommerce.domain.models.Outstanding;
import org.apis.ecommerce.domain.repositories.IOutstandingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutstandingRepository extends JpaRepository<Outstanding, Integer>, IOutstandingRepository {
}
