package org.API.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.API.entities.UserEntity;
import org.API.repositories.UserRepository;

@Path("api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;

    // Create a new user
    // @POST
    // @Transactional
    // public Response createUser(UserEntity user) {
    // userRepository.persist(user);
    // return Response.status(Response.Status.CREATED)
    // .entity(user)
    // .build();
    // }

    @POST
    @Transactional
    public Response createUser(UserEntity user) {
        UserEntity savedUser = userRepository.saveOrUpdate(user);
        return Response.status(Response.Status.CREATED).entity(savedUser).build();
    }

    // Get user by API key
    // Behövs verkligen den här?
    @GET
    @Path("/me")
    public Response getMe(@HeaderParam("X-API-KEY") String apiKey) {

        // Validate API key presence
        if (apiKey == null || apiKey.isBlank()) {
            throw new NotAuthorizedException("API key missing");
        }
        UserEntity user = userRepository.findByApiKey(apiKey);

        if (user == null) {
            throw new NotAuthorizedException("Invalid API key");
        }

        return Response.ok(user).build();
    }
}