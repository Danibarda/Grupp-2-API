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

    @POST
    @Transactional
    public Response createUser(UserEntity user) {
        UserEntity savedUser = userRepository.saveOrUpdate(user);
        return Response.status(Response.Status.CREATED).entity(savedUser).build();
    }
}