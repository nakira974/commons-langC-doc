package coffee.lkh.commonslangcdoc.beans;

import java.io.IOException;
import java.nio.file.Path;

public interface IBuild {

    void cloneRepo(String url, String localPath);

    public void executeCmake() throws IOException, InterruptedException;

    public Path getBuildPath();
}
