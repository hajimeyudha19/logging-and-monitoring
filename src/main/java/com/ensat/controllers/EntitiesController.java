package com.ensat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensat.entities.Entities;
import com.ensat.services.EntitiesService;

@RestController
@RequestMapping("/entities")
public class EntitiesController {

    @Autowired
    private EntitiesService entitiesService;

    @GetMapping
    public List<Entities> getAllEntities() {
        return entitiesService.getAllEntities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entities> getEntityById(@PathVariable Integer id) {
        Optional<Entities> entity = entitiesService.getEntityById(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Entities createEntity(@RequestBody Entities entity) {
        return entitiesService.saveEntity(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entities> updateEntity(@PathVariable Integer id, @RequestBody Entities entity) {
        Entities updatedEntity = entitiesService.updateEntity(id, entity);
        return updatedEntity != null ? ResponseEntity.ok(updatedEntity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Integer id) {
        if (entitiesService.getEntityById(id).isPresent()) {
            entitiesService.deleteEntity(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
