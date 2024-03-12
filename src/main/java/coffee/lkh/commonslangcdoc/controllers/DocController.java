package coffee.lkh.commonslangcdoc.controllers;

import coffee.lkh.commonslangcdoc.beans.DocBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Path("/")
public class DocController {
    @Context
    ServletContext servletContext;

    /**
     * Application handle for the build & publish
     */
    private final DocBean docBean;

    @Inject
    public DocController(DocBean docBean) {
        this.docBean = docBean;
    }

    /**
     * Main entry point of the application
     * @return index.html if found, error 500 otherwise
     */
    @GET
    @Path("/")
    public Response get() {
        try {
            final String base = this.docBean.getDocPath().resolve("html").toString();
            final File f = new File(String.format("%s/%s", base, "index.html"));
            if(f.exists() && !f.isDirectory()) {
                final Response.ResponseBuilder response = Response.ok((Object) new FileInputStream(f));
                response.header("Content-Disposition", "inline; filename=\"index.html\"");
                return response.build();
            }else throw new Exception();


        } catch (Exception e) {
            throw new RuntimeException("Doxygen index.html file is missing!");
        }
    }

    /**
     * Returns a file from the published documentation if found
     * @param path Target file in the published files
     * @return The target .html file if found, error 500 otherwise
     * @throws FileNotFoundException The file hasn't been found in the published pages
     */
    @GET
    @Path("/{path: .+}")
    public Response getFile(@PathParam("path") String path) throws FileNotFoundException {
        final String base = this.docBean.getDocPath().resolve("html").toString();
        final File f = new File(String.format("%s/%s", base, path));
        if (f.exists() && !f.isDirectory()) {
            final Response.ResponseBuilder response = Response.ok((Object) new FileInputStream(f));
            response.header("Content-Disposition", "inline; filename=\"" + path + "\"");
            return response.build();
        }else{
            // assuming your 404.jsp is in your WEB-INF folder
            throw new FileNotFoundException(path);
        }
    }
}