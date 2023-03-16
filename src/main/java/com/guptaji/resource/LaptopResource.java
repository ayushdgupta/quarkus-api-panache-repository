package com.guptaji.resource;

import com.guptaji.entity.LaptopEntity;
import com.guptaji.repository.LaptopRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/laptopInfo")
public class LaptopResource {

    // IMP link for DB Connectivity https://quarkus.io/guides/datasource, https://quarkus.io/guides/hibernate-orm

    // Here we are using Panache Repository to perform CRUD operations

    @Inject
    public LaptopRepository laptopRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLaptopInfo(){
        List<LaptopEntity> laptopDetails = laptopRepository.listAll();
        return Response.ok(laptopDetails).build();
    }

    @POST
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewLaptopInfo(LaptopEntity laptop){
        laptopRepository.persist(laptop);
        if (laptopRepository.isPersistent(laptop)){
            return Response.created(URI.create(("/laptopInfo/"+laptop.getId()))).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getLaptopInfo(@PathParam("id") long id){
        LaptopEntity laptop = laptopRepository.findById(id);
        if (laptop == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(laptop).build();
        }
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateLaptopInfo(@PathParam("id") long id, LaptopEntity laptop){
        Optional<LaptopEntity> laptopOptional = laptopRepository.findByIdOptional(id);
        if (laptopOptional.isPresent()){
            LaptopEntity currentLaptopData = laptopOptional.get();
            if (Objects.nonNull(laptop.getName())){
                currentLaptopData.setName(laptop.getName());
            }

            laptopRepository.persist(currentLaptopData);
            if (laptopRepository.isPersistent(currentLaptopData)){
                return Response.created(URI.create("/laptopInfo/"+id)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response deleteLaptopInfo(@PathParam("id") long id){
        Boolean isDeleted = laptopRepository.deleteById(id);
        if (isDeleted){
            return Response.ok("Done dana dan done").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
