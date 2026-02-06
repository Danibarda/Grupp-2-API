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
import jakarta.ws.rs.core.Response;
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

@Path("api/car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {

    @Inject
    CarRepository carRepository;

    @Inject
    UserRepository userRepository;

    @GET
    @Operation(summary = "Get all cars", description = "Retrieve a list of all cars in the system")
    @APIResponse(responseCode = "200", description = "List of cars retrieved successfully")
    @APIResponse(responseCode = "204", description = "No cars found")

    public Response getCars(@HeaderParam("X-API-KEY") String apiKey) {
        List<CarEntity> cars = carRepository.getAllCars();

        if (cars.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(cars).build();
    }

    @GET
    @Path("/user")
    public Response getCarsByUserApiKey(@HeaderParam("X-API-KEY") String apiKey) {

        UserEntity user = userRepository.findByApiKey(apiKey);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid or missing API key")
                    .build();
        }

        List<CarEntity> cars = carRepository.getCarsByUserId(user.getId());

        if (cars.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(cars).build();
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
    public Response deleteCar(@HeaderParam("X-API-KEY") String apiKey,
            @PathParam("id") Long id) {

        UserEntity user = userRepository.findByApiKey(apiKey);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarEntity car = carRepository.getCarById(id);

        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!car.getUser().getId().equals(user.getId())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        carRepository.deleteCar(id);

        return Response.noContent().build();
    }

    

    @PATCH
    @Path("/{id}")
    public Response updateCar(@HeaderParam("X-API-KEY") String apiKey,
            @PathParam("id") Long id,
            UpdateMilage updateMilage) {

        UserEntity user = userRepository.findByApiKey(apiKey);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarEntity car = carRepository.getCarById(id);

        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!car.getUser().getId().equals(user.getId())) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return carRepository.updateMilage(id, updateMilage.getMilage());
    }

}