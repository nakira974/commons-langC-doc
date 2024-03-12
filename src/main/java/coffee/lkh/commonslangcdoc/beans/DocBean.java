package coffee.lkh.commonslangcdoc.beans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.jboss.logging.Logger;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Startup bean to load application parameters and execute git clone and cmake build by using {@link CMakeBuildBean}
 */
@Singleton
@Startup
public class DocBean {
    private static final Logger LOGGER = Logger.getLogger(DocBean.class);

    @EJB
    private IBuild buildDocBean;

    /**
     * Reads <b>application.properties</b> and fetch target repository url
     * @return Target repository URL
     */
    public String getRepositoryUrl() {
        String propFileName = "application.properties";
        String repoUrl = "";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();

            if (inputStream != null) {
                prop.load(inputStream);
                repoUrl = prop.getProperty("repository_url");
            } else {
                LOGGER.error("Property file '" + propFileName + "' not found in classpath");
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading the repository url in application.properties!",e);
        }
        return repoUrl;
    }

    /**
     * @return Returns Path to the published Doxygen documentation pages
     */
    public Path getDocPath() {
        return this.buildDocBean.getBuildPath();
    }

    /**
     * Launches the git clone and cmake builds operations at startup
     */
    @PostConstruct
    void construct() {
        final String repoUrl = getRepositoryUrl();
        LOGGER.debug(String.format("Cloning repository %s...", repoUrl));
        buildDocBean.cloneRepo(repoUrl, "commons-langC");
        LOGGER.debug(String.format("Repository %s has been cloned successfully", repoUrl));
        LOGGER.debug("CMake build...");
        try{
            buildDocBean.executeCmake();
        }catch (Exception ex){
            LOGGER.error("Error while building the documentation!",ex);
        }
        LOGGER.debug("Documentation built successfully");
    }
}