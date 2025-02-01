package com.polstatstis.labkom.repository;

/**
 * @author blessy
 */

import com.polstatstis.labkom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
