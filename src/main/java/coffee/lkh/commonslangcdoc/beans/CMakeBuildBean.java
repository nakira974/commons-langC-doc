package coffee.lkh.commonslangcdoc.beans;

import jakarta.ejb.Local;
import jakarta.ejb.Stateful;
import jakarta.inject.Named;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.*;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of {@link IBuild} to process git clone and cmake build
 */
@Stateful
@Named("buildDocumentationBean")
@Local(IBuild.class)
public class CMakeBuildBean implements IBuild {
    Logger logger = Logger.getLogger("CmakeLogger");

    /**
     * Published pages path
     */
    private Path buildPath;

    public Path getBuildPath(){
        return this.buildPath.resolve("cmake-build-debug");
    }
    public void cloneRepo(String url, String folderName) {
        // Fetch temp directory.
        final Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"));
        final Path finalPath = tempPath.resolve("srv").resolve("pages");
        try {
            // Create a directory in the temp directory.
            final Path directory = Paths.get(finalPath.toString(), folderName);
            buildPath = directory;
            if(!buildPath.toFile().exists()){
                Files.createDirectories(directory);
                logger.info("cloning repository..");
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
            }else{
                logger.info("pulling repository..");
                try(final Git pull = Git
                        .open(this.buildPath.toFile())){
                    final PullResult res = pull.pull().call();
                    if(!res.isSuccessful()) throw new NotMergedException();
                } catch (GitAPIException e) {
                    throw new RuntimeException(e);
                }
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
                final ProcessBuilder pbConfigure = new ProcessBuilder("cmake", "-G","MinGW Makefiles", "-DCMAKE_BUILD_TYPE=" + "Debug", "-S", this.buildPath.toString(), "-B", this.buildPath.resolve("cmake-build-debug").toString());
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
