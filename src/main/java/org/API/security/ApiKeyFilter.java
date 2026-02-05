package org.API.security;

import java.io.IOException;

import org.API.entities.UserEntity;
import org.API.repositories.UserRepository;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyFilter implements ContainerRequestFilter {

    @Inject
    UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        
        // Skippa autentisering för POST /api/users
        if (path.contains("users") && method.equals("POST")) {
            return;
        }
        
        // Hämta API-nyckel från header
        String apiKey = requestContext.getHeaderString("X-API-KEY");

        // Validera att nyckel finns
        if (apiKey == null || apiKey.isBlank()) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("API key is missing")
                        .build()
            );
            return;
        }  

        // Hämta användaren
        UserEntity user = userRepository.findByApiKey(apiKey);
        
        if (user == null) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid API key")
                        .build()
            );
            return;
        }
        
        // Spara användaren för användning i endpoints
        requestContext.setProperty("currentUser", user);
    }
}