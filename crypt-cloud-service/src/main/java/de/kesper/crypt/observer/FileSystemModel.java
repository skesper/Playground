package de.kesper.crypt.observer;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

/**
 * Created by Stephan on 25.10.2014.
 */
public class FileSystemModel {
    private final Path root;
    private final HashMap<String, Path> fileModel;
    private final HashMap<String, Path> directoryModel;

    public FileSystemModel(Path root) throws IOException {
        fileModel = new HashMap<>();
        directoryModel = new HashMap<>();
        this.root = root;
        init();
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

    private void init() throws IOException {
        Files.walkFileTree(root, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                directoryModel.put(mangleFileName(dir), dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileModel.put(mangleFileName(file), file);
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
        String fpath = root.toAbsolutePath().toString();
        return fpath.substring(rpath.length());
    }
}
