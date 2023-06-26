package co.indiagold.dto;

import java.util.Set;


public class IgnoredFilesDto {
    private Set<String> filesToIgnore;
    private Set<String> directoriesToIgnore;

    public IgnoredFilesDto(Set<String> filesToIgnore, Set<String> directoriesToIgnore) {
        this.filesToIgnore = filesToIgnore;
        this.directoriesToIgnore = directoriesToIgnore;
    }

    public Set<String> getFilesToIgnore() {
        return filesToIgnore;
    }

    public void setFilesToIgnore(Set<String> filesToIgnore) {
        this.filesToIgnore = filesToIgnore;
    }

    public Set<String> getDirectoriesToIgnore() {
        return directoriesToIgnore;
    }

    public void setDirectoriesToIgnore(Set<String> directoriesToIgnore) {
        this.directoriesToIgnore = directoriesToIgnore;
    }
}
