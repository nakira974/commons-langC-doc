package coffee.lkh.commonslangcdoc.beans;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Local EJB bean that describes Doxygen build & expose logic
 */
public interface IBuild {

    /**
     * Clone the target repository and its submodule recursively
     * @param url Target git repository
     * @param localPath Folder name in temp/srv/pages
     */
    void cloneRepo(String url, String localPath);

    /**
     * Runs a cmake build before build the CMake Doxygen doc target
     * @throws IOException Shell or java I/O error happened
     * @throws InterruptedException Log output process failed to read stdout or stderr
     */
    public void executeCmake() throws IOException, InterruptedException;

    /**
     * @return Returns the path to the published Doxygen documentation pages
     */
    public Path getBuildPath();
}
