package coffee.lkh.commonslangcdoc.beans;

import jakarta.ejb.Local;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Stateful
@Named("buildDocumentationBean")
@Local(IBuildDoc.class)
public class BuildDocBean  implements IBuildDoc{
    Logger logger = Logger.getLogger("CmakeLogger");

    private Path buildPath;
    public void cloneRepo(String url, String folderName) {
        // Fetch temp directory.
        final String tmpDir = System.getProperty("java.io.tmpdir");

        try {
            // Create a directory in the temp directory.
            final Path directory = Paths.get(tmpDir, folderName);
            Files.createDirectories(directory);
            buildPath = directory;
            try(final Git git =  Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(this.buildPath.toFile())
                    .call()){
                // Init and update submodules after the clone
                git.submoduleInit().call();
                git.submoduleUpdate().call();
            }catch (JGitInternalException | GitAPIException e){
                Logger.getLogger(BuildDocBean.class).error("Git api error!",e);

            }
        } catch (IOException e) {
            Logger.getLogger(BuildDocBean.class).error("Error while cloning repo!",e);
        }
    }

    @Override
    public void executeCmake()  throws IOException, InterruptedException{
        Files.createDirectories(this.buildPath.resolve("cmake-build-release"));
        try {
            // Check if cmake is present
            final ProcessBuilder pbTest = new ProcessBuilder("where", "cmake");
            final Process pTest = pbTest.start();
            int exitCodeTest = pTest.waitFor();

            if (exitCodeTest == 0) {
                // Configure project
                final ProcessBuilder pbConfigure = new ProcessBuilder("cmake", "-G", "MinGW Makefiles", "-DCMAKE_BUILD_TYPE=" + "Debug", "-S", this.buildPath.toString(), "-B", this.buildPath.resolve("cmake-build-debug").toString());
                logOutput(pbConfigure.start());

                // Build project
                final ProcessBuilder pbBuild = new ProcessBuilder("cmake", "--build", this.buildPath.resolve("cmake-build-debug").toString(), "--target", "collections_commons_doc", "--", "-j", "10");
                logOutput(pbBuild.start());
            } else {
                logger.warn("cmake not found in system PATH");
            }
        } catch (Exception e) {
            logger.error("Error executing command", e);
        }
    }
    private void logOutput(Process process) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info(line);  // Log output line by line
        }
        int exitCode = process.waitFor();
        logger.info("Exit code: " + exitCode);
    }
}
