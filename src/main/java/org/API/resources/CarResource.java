package org.API.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.API.entities.CarEntity;
import org.API.entities.UpdateMilage;
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

    @GET
    @Path("/{id}")
    public Response getCarById(@PathParam("id") Long id) {
        return carRepository.getCarById(id);
    }

    @POST
    public Response createCar(CarEntity car) throws URISyntaxException {

        car = carRepository.createCar(car);

        URI createdUri = new URI(car.getId().toString());
        return Response.created(createdUri).entity(car).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCar(@PathParam("id") Long id) {
        carRepository.deleteCar(id);
        return Response.noContent().build();

}
    @PATCH
    @Path("/{id}")
    public Response updateCar(@PathParam("id") Long id, UpdateMilage updateMilage) {
        return carRepository.updateMilage(id, updateMilage.getMilage());
    }
}