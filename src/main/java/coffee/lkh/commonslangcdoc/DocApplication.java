package coffee.lkh.commonslangcdoc;

import coffee.lkh.commonslangcdoc.mappers.FileNotFoundExceptionMapper;
import jakarta.enterprise.inject.Default;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 * JAX-RS Doxygen API for commons-langC
 */
@Default
public class DocApplication extends ResourceConfig {
    public DocApplication() {
        // Register JSP MVC processing feature
        final JspMvcFeature jspMvcFeature = new JspMvcFeature();
        register(FileNotFoundExceptionMapper.class);
        register(jspMvcFeature);
        property(MvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp"); // Adjust path based on your structure
        packages("coffee.lkh.commonslangcdoc.controllers");
    }
}