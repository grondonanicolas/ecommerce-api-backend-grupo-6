package org.apis.ecommerce.infrastructure.repositories;

import org.apis.ecommerce.domain.models.Category;
import org.apis.ecommerce.domain.repositories.ICategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer>, ICategoryRepository {

}

