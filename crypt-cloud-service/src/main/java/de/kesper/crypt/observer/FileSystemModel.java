package de.kesper.crypt.observer;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

/**
 * Created by Stephan on 25.10.2014.
 */
public class FileSystemModel {
    private final Path root;
    private final HashMap<String, Path> fileModel;
    private final HashMap<String, Path> directoryModel;
    private WatchService service;

    public FileSystemModel(Path root) throws IOException {
        fileModel = new HashMap<>();
        directoryModel = new HashMap<>();
        this.root = root;
        init();
    }

    public void addWatchService(WatchService service) throws IOException {
        this.service = service;

        for(Path dir : directoryModel.values()) {
            dir.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        }
    }

    public WatchService getService() {
        return service;
    }

    public Path getRoot() {
        return root;
    }

    public HashMap<String, Path> getFileModel() {
        return fileModel;
    }

    public HashMap<String, Path> getDirectoryModel() {
        return directoryModel;
    }

    public boolean addDirectory(Path dir) {
        directoryModel.put(mangleFileName(dir), dir);
        return true;
    }

    private void init() throws IOException {
        Files.walkFileTree(root, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                directoryModel.put(mangleFileName(dir), dir.toAbsolutePath());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileModel.put(mangleFileName(file), file.toAbsolutePath());
                BasicFileAttributes bfa = Files.readAttributes(file, BasicFileAttributes.class);
                System.out.println(file.toAbsolutePath());
                System.out.println("    last modified: "+bfa.lastModifiedTime().toString());
                System.out.println("    created      : "+bfa.creationTime().toString());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private String mangleFileName(Path file) {
        String rpath = root.toAbsolutePath().toString();
        String fpath = file.toAbsolutePath().toString();
        return fpath.substring(rpath.length());
    }
}
