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
    private Path root;
    private HashMap<String, Path> fileModel;

    public FileSystemModel(Path root) throws IOException {
        fileModel = new HashMap<>();
        this.root = root;

        init();
    }

    private void init() throws IOException {
        Files.walkFileTree(root, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
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
