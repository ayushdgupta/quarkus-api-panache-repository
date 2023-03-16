package com.guptaji.repository;

import com.guptaji.entity.LaptopEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LaptopRepository implements PanacheRepository<LaptopEntity> {
}
