package coffee.lkh.commonslangcdoc.beans;

import java.io.IOException;

public interface IBuildDoc  {

    void cloneRepo(String url, String localPath);

    public void executeCmake() throws IOException, InterruptedException;
}
