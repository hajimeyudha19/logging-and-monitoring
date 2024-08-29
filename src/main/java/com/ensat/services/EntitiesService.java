package com.ensat.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensat.entities.Entities;
import com.ensat.repositories.EntitiesRepository;

@Service
public class EntitiesService {

    @Autowired
    private EntitiesRepository entitiesRepository;

    public List<Entities> getAllEntities() {
        return entitiesRepository.findAll();
    }

    public Optional<Entities> getEntityById(Integer id) {
        return entitiesRepository.findById(id);
    }

    public Entities saveEntity(Entities entity) {
        return entitiesRepository.save(entity);
    }

    public Entities updateEntity(Integer id, Entities entity) {
        if (entitiesRepository.existsById(id)) {
            entity.setId(id);
            return entitiesRepository.save(entity);
        }
        return null;
    }

    public void deleteEntity(Integer id) {
        entitiesRepository.deleteById(id);
    }
}
