package coffee.lkh.commonslangcdoc.beans;

import jakarta.ejb.Local;
import jakarta.ejb.Stateful;
import jakarta.inject.Named;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Stateful
@Named("buildDocumentationBean")
@Local(IBuild.class)
public class CMakeBuildBean implements IBuild {
    Logger logger = Logger.getLogger("CmakeLogger");

    private Path buildPath;

    public Path getBuildPath(){
        return this.buildPath.resolve("cmake-build-debug");
    }
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
                Logger.getLogger(CMakeBuildBean.class).error("Git api error!",e);

            }
        } catch (IOException e) {
            Logger.getLogger(CMakeBuildBean.class).error("Error while cloning repo!",e);
        }
    }

    @Override
    public void executeCmake()  throws IOException, InterruptedException{
        try {
            // Check if cmake is present
            final ProcessBuilder pbTest = new ProcessBuilder("where", "cmake");
            final Process pTest = pbTest.start();
            int exitCodeTest = pTest.waitFor();

            if (exitCodeTest == 0) {
                // Configure project
                final ProcessBuilder pbConfigure = new ProcessBuilder("cmake", "-DCMAKE_BUILD_TYPE=" + "Debug", "-S", this.buildPath.toString(), "-B", this.buildPath.resolve("cmake-build-debug").toString());
                logOutput(pbConfigure.start());

                // Build project
                final ProcessBuilder pbBuild = new ProcessBuilder("cmake", "--build", this.buildPath.resolve("cmake-build-debug").toString(), "--target", "collections_commons_doc", "--", "-j", "10");
                logOutput(pbBuild.start());

                //logOutput(pbBuild.start());
            } else {
                logger.warn("cmake not found in system PATH");
            }
        } catch (Exception e) {
            logger.error("Error executing command", e);
        }
    }
    private void logOutput(Process process) {
        // Thread to handle stdout
        Thread loggerThreadOut = new Thread(() -> {
            try (BufferedReader readerOut = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = readerOut.readLine()) != null) {
                    logger.info(line);
                }
            } catch (IOException e) {
                logger.error("Error reading stdout", e);
            }
        });

        // Thread to handle stderr
        Thread loggerThreadErr = new Thread(() -> {
            try (BufferedReader readerErr = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = readerErr.readLine()) != null) {
                    logger.error(line);
                }
            } catch (IOException e) {
                logger.error("Error reading stderr", e);
            }
        });

        // Start threads
        loggerThreadOut.start();
        loggerThreadErr.start();

        // Optional: Wait for process to finish
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            logger.error("Error in process execution", e);
        }
    }
}
