package coffee.lkh.commonslangcdoc.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.logging.Logger;

public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        // Add more sophisticated logging here as required
        final String errorMessage = String.format(String.format("%s : %s", exception.getClass().getSimpleName(), exception.getMessage()));
        Logger.getLogger(RuntimeExceptionMapper.class).error(String.format(errorMessage));
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}