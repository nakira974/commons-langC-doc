package coffee.lkh.commonslangcdoc;

import jakarta.enterprise.inject.Default;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

@Default
public class EditorApplication extends ResourceConfig {
    public EditorApplication() {
        // Register JSP MVC processing feature
        final JspMvcFeature jspMvcFeature = new JspMvcFeature();
        register(jspMvcFeature);
        property(MvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp"); // Adjust path based on your structure
        packages("coffee.lkh.commonslangcdoc.controllers");
    }
}