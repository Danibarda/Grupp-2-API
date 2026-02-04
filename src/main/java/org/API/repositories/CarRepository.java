package org.API.repositories;

import java.util.ArrayList;
import java.util.List;

import org.API.entities.CarEntity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;


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


    public Response getCarById(Long id) {
        CarEntity car = em.find(CarEntity.class, id);
        if(car == null) {
            return Response.noContent().build();
        }
        return Response.ok(car).build();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public CarEntity createCar(CarEntity car) {
        em.persist(car);
        return car;
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteCar(Long id) {
        em.remove(em.find(CarEntity.class, id));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Response updateMilage(Long id, int milage) {
       CarEntity car = em.find(CarEntity.class, id);
         if(car == null) {
              return Response.noContent().build();
         }
            car.setMilage(milage);
            em.merge(car);
            return Response.ok(car).build();
    }

}