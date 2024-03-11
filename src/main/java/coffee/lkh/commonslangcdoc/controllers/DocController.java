package coffee.lkh.commonslangcdoc.controllers;

import coffee.lkh.commonslangcdoc.beans.DocBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Path("/")
public class DocController {
    @Context
    ServletContext servletContext;

    private final DocBean docBean;

    @Inject
    public DocController(DocBean docBean) {
        this.docBean = docBean;
    }

    @GET
    @Path("/")
    public Response get() {
        try {
            final String base = this.docBean.getDocPath().resolve("html").toString();
            final File f = new File(String.format("%s/%s", base, "index.html"));

            final Response.ResponseBuilder response = Response.ok((Object) new FileInputStream(f));
            response.header("Content-Disposition", "inline; filename=\"index.html\"");
            return response.build();

        } catch (FileNotFoundException e) {
            return Response.serverError().entity(new Viewable("general_error")).build();
        }
    }

    @Path("/{path: .+}")
    @GET
    public Response getFile(@PathParam("path") String path) {
        try {
            final String base =  this.docBean.getDocPath().resolve("html").toString();
            final File f = new File(String.format("%s/%s", base, path));

            final Response.ResponseBuilder response = Response.ok((Object) new FileInputStream(f));
            response.header("Content-Disposition", "inline; filename=\"" + path + "\"");
            return response.build();
        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("File not found: " + e.getMessage()).build();
        }
    }
}