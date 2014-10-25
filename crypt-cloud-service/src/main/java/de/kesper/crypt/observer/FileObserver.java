package de.kesper.crypt.observer;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Stephan on 25.10.2014.
 */
public class FileObserver implements Runnable {
    private Path cloudDir;
    private Path localDir;

    public FileObserver(Path cloudDir, Path localDir) {
        this.cloudDir = cloudDir;
        this.localDir = localDir;
    }

    @Override
    public void run() {
        try {
            Files.walkFileTree(cloudDir, new SyncFileVisitor(localDir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class SyncFileVisitor implements FileVisitor<Path> {
        private Path local;
        public SyncFileVisitor(Path local) {
            this.local = local;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            System.out.println("entering "+dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("visiting "+file);

            /*

            cases:

            1. File existiert lokal, aber nicht in cloud.
                verschlüsseln und in die cloud kopieren.
            2. File existiert nicht lokal, aber in der cloud.
                entschlüsseln und von cloud nach lokal kopieren.
            3. File existiert in beiden Verzeichnissen, aber der Zeitstempel lokal ist neuer, als der der Cloud
                verschlüsseln und in die cloud kopieren
            4. w.o., nur Zeitstempel cloud ist neuer
                entschlüsseln und von der cloud nach lokal kopieren.

             */


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
    }
}
