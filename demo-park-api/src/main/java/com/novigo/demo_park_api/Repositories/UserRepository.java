package com.novigo.demo_park_api.Repositories;

import com.novigo.demo_park_api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}
