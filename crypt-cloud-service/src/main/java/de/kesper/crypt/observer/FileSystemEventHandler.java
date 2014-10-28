package de.kesper.crypt.observer;

import java.nio.file.Path;

/**
 * User: kesper
 * Date: 27.10.2014
 * Time: 16:48
 */
public interface FileSystemEventHandler {

    void handleFile(FileSystemModel model, Path file, FileSystemEventType type);

    void handleDirectory(FileSystemModel model, Path directory, FileSystemEventType type);

}
