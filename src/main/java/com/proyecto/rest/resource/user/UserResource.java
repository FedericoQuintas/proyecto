package com.proyecto.rest.resource.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.proyecto.user.dto.UserDTO;

@Path("/users")
public class UserResource {
	
	@GET
	@Path("/{userId}")
	public UserDTO findById(@PathParam("userId") Long userId){
		
		return new UserDTO();
	}

}
