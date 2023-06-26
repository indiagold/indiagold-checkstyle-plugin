package co.indiagold.filters;

import co.indiagold.dto.IgnoredFilesDto;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FilesBeforeExecutionFileFilter extends AutomaticBean implements BeforeExecutionFileFilter {

    private String filesToCheckPath;
    private String filesToIgnorePath;

    public String getFilesToCheckPath() {
        return filesToCheckPath;
    }

    public void setFilesToCheckPath(String filesToCheckPath) {
        this.filesToCheckPath = filesToCheckPath;
    }

    public String getFilesToIgnorePath() {
        return filesToIgnorePath;
    }

    public void setFilesToIgnorePath(String filesToIgnorePath) {
        this.filesToIgnorePath = filesToIgnorePath;
    }

    public FilesBeforeExecutionFileFilter() {
    }

    private Set<String> loadAcceptedFiles(String filesToCheckPath){
        IgnoredFilesDto ignoredFilesDto = loadIgnoredFiles(filesToIgnorePath);
        Set<String> acceptedFiles = new HashSet<>();
        Path path = Paths.get(filesToCheckPath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
            String contentLine = reader.readLine();
            while (contentLine != null) {
                boolean isIgnoredDueToDirectory = false;
                for (String ignoredDirectory : ignoredFilesDto.getDirectoriesToIgnore()) {
                    if (contentLine.startsWith(ignoredDirectory)) {
                        isIgnoredDueToDirectory = true;
                        break;
                    }
                }
                String fileName = contentLine.substring(contentLine.lastIndexOf("/") + 1);
                if(!isIgnoredDueToDirectory && !ignoredFilesDto.getFilesToIgnore().contains(fileName)){
                    acceptedFiles.add(fileName);
                }
                contentLine = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return acceptedFiles;
    }

    private IgnoredFilesDto loadIgnoredFiles(String filesToIgnorePath){
        Set<String> ignoredFiles = new HashSet<>();
        Set<String> ignoredDirectories = new HashSet<>();
        Path path = Paths.get(filesToIgnorePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
            String contentLine = reader.readLine();
            while (contentLine != null) {
                if(contentLine.endsWith("*")){
                    ignoredDirectories.add(contentLine.substring(0,contentLine.lastIndexOf("/")));
                }else {
                    ignoredFiles.add(contentLine.substring(contentLine.lastIndexOf("/") + 1));
                }
                contentLine = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new IgnoredFilesDto(ignoredFiles,ignoredDirectories);
    }

    @Override
    protected void finishLocalSetup() {

    }

    @Override
    public boolean accept(String uri) {
        Set<String> files = loadAcceptedFiles(filesToCheckPath);
        return files.contains(Paths.get(uri).getFileName().toString());
    }
}
