package com.web.auction.data;

import com.web.auction.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByAuthority(String authority);
    Role findById(String id);

    // Добавьте другие методы для работы с ролями, если необходимо
}