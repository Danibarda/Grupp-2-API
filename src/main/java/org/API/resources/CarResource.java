package org.API.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.API.entities.CarEntity;
import org.API.repositories.CarRepository;


@Path("api/car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {

    @Inject
    CarRepository carRepository;
    
    @GET
    public Response getCars() {
        List<CarEntity> cars = carRepository.getAllCars();

        if(cars.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(cars).build();
    }





}