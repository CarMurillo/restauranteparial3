package com.example.mongodb.proyecto.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.mongodb.proyecto.entity.Opinion;

public interface OpinionRepository extends MongoRepository<Opinion, String> {
}

