package org.API.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("api/car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {

    @Inject
    CarRepository carRepository;

    @Inject
    UserRepository userRepository;

    // GET ALL CARS
    @GET
    @Operation(summary = "Get all cars", description = "Retrieve a list of all cars in the system")
    @APIResponse(responseCode = "200", description = "List of cars retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CarEntity[].class)))
    @APIResponse(responseCode = "204", description = "No cars found")
    public Response getCars(@HeaderParam("X-API-KEY") String apiKey) {

        List<CarEntity> cars = carRepository.getAllCars();

        if (cars.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(cars).build();
    }

    // GET CARS BY USER
    @GET
    @Path("/user")
    @Operation(summary = "Get cars for selected user")
    @APIResponse(responseCode = "200", description = "Filtered cars by user", content = @Content(schema = @Schema(implementation = CarEntity[].class)))
    @APIResponse(responseCode = "401", description = "Invalid or missing API key")
    @APIResponse(responseCode = "204", description = "User has no cars")
    public Response getCarsByUserApiKey(
            @HeaderParam("X-API-KEY") String apiKey) {

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

    // CREATE
    @POST
    @Operation(summary = "Create new car", description = "Create a new car associated with the authenticated user")
    @APIResponse(responseCode = "201", description = "Car successfully created", content = @Content(schema = @Schema(implementation = CarEntity.class)))
    @APIResponse(responseCode = "401", description = "Invalid or missing API key")
    public Response createCar(
            @HeaderParam("X-API-KEY") String apiKey,
            CarEntity car) throws URISyntaxException {

        UserEntity user = userRepository.findByApiKey(apiKey);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        car.setUser(user);
        car = carRepository.createCar(car);

        URI createdUri = new URI("api/car/" + car.getId());
        return Response.created(createdUri).entity(car).build();
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete car")
    @APIResponse(responseCode = "204", description = "Car successfully deleted")
    @APIResponse(responseCode = "401", description = "Invalid or missing API key")
    @APIResponse(responseCode = "403", description = "Not owner of car")
    @APIResponse(responseCode = "404", description = "Car not found")
    public Response deleteCar(
            @HeaderParam("X-API-KEY") String apiKey,
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

    // UPDATE MILAGE
    @PATCH
    @Path("/{id}")
    @Operation(summary = "Update car milage")
    @APIResponse(responseCode = "200", description = "Car successfully updated", content = @Content(schema = @Schema(implementation = CarEntity.class)))
    @APIResponse(responseCode = "401", description = "Invalid or missing API key")
    @APIResponse(responseCode = "403", description = "Not owner of car")
    @APIResponse(responseCode = "404", description = "Car not found")
    public Response updateCar(
            @HeaderParam("X-API-KEY") String apiKey,
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

        CarEntity savedCar = carRepository.updateMilage(id, updateMilage.getMilage());
        return Response.ok(savedCar).build();
    }
}