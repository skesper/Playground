package de.kesper.crypt.observer;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Stephan on 25.10.2014.
 */
public class FileObserver {

    private FileSystemModel source;
    private FileSystemModel target;

    private WatchService sourceService;
    private WatchService targetService;

    private Thread sourceThread;
    private Thread targetThread;

    private FileSystemEventHandler handler;

    public FileObserver(FileSystemModel source, FileSystemModel target, FileSystemEventHandler handler) throws IOException {
        this.source = source;
        this.target = target;
        this.handler = handler;

        FileSystem sfs = source.getRoot().getFileSystem();
        FileSystem tfs = target.getRoot().getFileSystem();

        sourceService = sfs.newWatchService();
        if (sfs.equals(tfs)) {
            targetService = sourceService;
        } else {
            targetService = tfs.newWatchService();
        }

        source.addWatchService(sourceService);
        target.addWatchService(targetService);
    }

    public void startObservation() {
        sourceThread = new Thread(new WatchServiceHandler(sourceService, source, handler));
        sourceThread.setDaemon(true);
        sourceThread.start();

        targetThread = new Thread(new WatchServiceHandler(targetService, target, handler));
        targetThread.setDaemon(true);
        targetThread.start();
    }

    private class WatchServiceHandler implements Runnable {
        private final WatchService service;
        private final FileSystemModel model;
        private final FileSystemEventHandler handler;

        public WatchServiceHandler(WatchService service, FileSystemModel model, FileSystemEventHandler handler) {
            this.service = service;
            this.handler = handler;
            this.model = model;
        }

        @Override
        public void run() {
            while(true) {
                WatchKey watchKey;
                try {
                    watchKey = service.take();
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (watchKey!=null) {
                    for(WatchEvent<?> p : watchKey.pollEvents()) {
                        System.out.println(p.context() + " -> " + p.kind());
                        Path path = (Path)p.context();
                        if (watchKey.watchable() instanceof Path) {
                            System.out.println("DEBUG: watchKey.watchable = " + watchKey.watchable());
                        }

                        Path modifiedItem = Paths.get(watchKey.watchable().toString(), path.toString());
                        System.out.println("DEBUG: modifiedItem = "+modifiedItem);

                        if (Files.isDirectory(modifiedItem)) {
                            if (p.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                                model.addDirectory(modifiedItem);
                                try {
                                    modifiedItem.register(service);
                                } catch(IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            handler.handleDirectory(model, modifiedItem, toType(p.kind()));
                        } else if (Files.isRegularFile(modifiedItem)) {
                            handler.handleFile(model, modifiedItem, toType(p.kind()));
                        } else {
                            if (Files.exists(modifiedItem)) {
                                System.err.println("Could not handle: " + modifiedItem.toAbsolutePath().toString());
                                try {
                                    BasicFileAttributes bfa = Files.readAttributes(modifiedItem.toAbsolutePath(), BasicFileAttributes.class);
                                    System.out.println("    is regular " + bfa.isRegularFile());
                                    System.out.println("    is directory " + bfa.isDirectory());
                                    System.out.println("    is other " + bfa.isOther());
                                    System.out.println("    is link " + bfa.isSymbolicLink());

                                } catch(IOException e) {
                                    e.printStackTrace();
                                }
                            } // else the file does not exist, which is may be because it was renamed or moved.
                        }
                    }
                    watchKey.reset();
                }
            }
        }

        private FileSystemEventType toType(WatchEvent.Kind<?> kind) {
            if (kind==StandardWatchEventKinds.ENTRY_CREATE) {
                return FileSystemEventType.CREATE;
            }
            if (kind==StandardWatchEventKinds.ENTRY_DELETE) {
                return FileSystemEventType.DELETE;
            }
            if (kind==StandardWatchEventKinds.ENTRY_MODIFY) {
                return FileSystemEventType.MODIFY;
            }
            throw new RuntimeException("Unknown type of WatchEvent: "+kind);
        }
    }
}
