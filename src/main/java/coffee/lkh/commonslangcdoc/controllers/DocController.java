package coffee.lkh.commonslangcdoc.controllers;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Path("/")
public class DocController {
    @Context
    ServletContext servletContext;

    @GET
    @Path("/")
    public InputStream get() {
        try {
            String base = servletContext.getRealPath("/WEB-INF/html");
            File f = new File(String.format("%s/%s", base, "index.html"));
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Path("/{path: .+}")
    @GET
    public InputStream getFile(@PathParam("path") String path) {
        try {
            String base = servletContext.getRealPath("/WEB-INF/html");
            File f = new File(String.format("%s/%s", base, path));
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
}