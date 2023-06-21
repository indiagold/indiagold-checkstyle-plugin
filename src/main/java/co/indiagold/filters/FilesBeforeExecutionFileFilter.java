package co.indiagold.filters;

import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesBeforeExecutionFileFilter extends AutomaticBean implements BeforeExecutionFileFilter {

    private String filesToCheckPath;

    public String getFilesToCheckPath() {
        return filesToCheckPath;
    }

    public void setFilesToCheckPath(String filesToCheckPath) {
        this.filesToCheckPath = filesToCheckPath;
    }

    public FilesBeforeExecutionFileFilter() {
    }

    private List<String> loadAcceptedFiles(String filesToCheckPath){
        ArrayList<String> acceptedFiles = new ArrayList<>();
        Path path = Paths.get(filesToCheckPath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
            String contentLine = reader.readLine();
            while (contentLine != null) {
                acceptedFiles.add(contentLine.substring(contentLine.lastIndexOf("/")+1));
                contentLine = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return acceptedFiles;
    }

    @Override
    protected void finishLocalSetup() {

    }

    @Override
    public boolean accept(String uri) {
        List<String> files = loadAcceptedFiles(filesToCheckPath);
        return files.contains(Paths.get(uri).getFileName().toString());
    }
}
