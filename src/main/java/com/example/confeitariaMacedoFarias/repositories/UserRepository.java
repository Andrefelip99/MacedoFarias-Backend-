package com.example.confeitariaMacedoFarias.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.confeitariaMacedoFarias.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}