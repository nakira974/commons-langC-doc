package coffee.lkh.commonslangcdoc.mappers;

import jakarta.ws.rs.core.Configuration;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.logging.Logger;

import java.io.FileNotFoundException;

public class FileNotFoundExceptionMapper implements ExceptionMapper<FileNotFoundException> {

    @Context
    Configuration config;

    @Override
    public Response toResponse(FileNotFoundException exception) {
        // Assuming you have an html file under WEB-INF/jsp named 404.jsp*
        final String fileName = exception.getMessage();
        final String errorMessage = String.format("File : %s not found", fileName);
        Logger.getLogger(FileNotFoundExceptionMapper.class).error(String.format(errorMessage));
        return Response.status(Response.Status.NOT_FOUND).entity(String.format(errorMessage)).build();
    }
}
