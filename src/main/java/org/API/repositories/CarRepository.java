package org.API.repositories;

import java.util.ArrayList;
import java.util.List;

import org.API.entities.CarEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@Transactional(Transactional.TxType.SUPPORTS)
@ApplicationScoped
public class CarRepository {

    @Inject
    EntityManager em;
    
    public List<CarEntity> getAllCars() {
        List<CarEntity> cars = new ArrayList<>();
        cars = em.createQuery("SELECT c FROM CarEntity c", CarEntity.class)
                 .getResultList();
        return cars;
    }
}