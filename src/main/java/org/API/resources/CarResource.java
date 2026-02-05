package org.API.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.API.entities.CarEntity;
import org.API.entities.UpdateMilage;
import org.API.entities.UserEntity;
import org.API.repositories.CarRepository;
import org.API.repositories.UserRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("api/car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {

    @Inject
    CarRepository carRepository;

    @Inject 
    UserRepository userRepository;

    @GET
    @Operation(summary="Get all cars", description = "Retrieve a list of all cars in the system")
    @APIResponse(
        responseCode = "200", 
        description = "List of cars retrieved successfully"
        )
        @APIResponse(
            responseCode = "204", 
            description = "No cars found"
            )

    public Response getCars() {
        List<CarEntity> cars = carRepository.getAllCars();

        if (cars.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(cars).build();
    }

    @GET
    @Path("/{id}")
    public Response getCarById(@PathParam("id") Long id) {
        CarEntity car = carRepository.getCarById(id);

        if (car == null) {
            return Response.noContent().build();
        }
        return Response.ok(car).build();
    }

    @POST
    public Response createCar(@HeaderParam("X-API-KEY") String apiKey, CarEntity car) throws URISyntaxException {

        UserEntity user = userRepository.findByApiKey(apiKey);
        car.setUser(user);
        car = carRepository.createCar(car);

        URI createdUri = new URI("api/car/" + car.getId());
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