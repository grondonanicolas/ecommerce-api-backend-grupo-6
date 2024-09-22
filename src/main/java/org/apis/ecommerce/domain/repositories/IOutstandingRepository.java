package org.apis.ecommerce.domain.repositories;

import org.apis.ecommerce.domain.models.Outstanding;
import java.util.List;

public interface IOutstandingRepository {

    Outstanding save(Outstanding outstanding);
     
    List<Outstanding> findAll();
}
