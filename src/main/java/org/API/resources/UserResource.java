package org.API.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.API.entities.UserEntity;
import org.API.repositories.UserRepository;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class UserResource {

    @Inject
    UserRepository userRepository;

    @POST
    @Transactional
    @Operation(summary = "Create a new user", description = "Create a new user to the system")
    @APIResponse(responseCode = "201", description = "Successfully created a new user", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserEntity.class)))
    @APIResponse(responseCode = "400", description = "Invalid input / validation failed")
    public Response createUser(@Valid UserEntity user) {
        UserEntity savedUser = userRepository.saveOrUpdate(user);
        return Response.status(Response.Status.CREATED)
                .entity(savedUser)
                .build();
    }
}