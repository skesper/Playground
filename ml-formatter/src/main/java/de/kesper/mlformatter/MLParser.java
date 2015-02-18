package de.kesper.mlformatter;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: kesper
 * Date: 12.02.2015
 * Time: 15:07
 */
public class MLParser implements Runnable, AutoCloseable {

    private FileReader fr;
    private LineNumberReader lnr;
    private ArrayList<String> lines = new ArrayList<String>();

    public MLParser(String fileName) throws IOException {
        fr = new FileReader(fileName);
        lnr = new LineNumberReader(fr);
    }

    @Override
    public void run() {
        while(true) {
            try {
                String line = lnr.readLine();
                if (line==null) return;
                lines.add(line);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception {
        lnr.close();
        fr.close();
    }

    public Iterator<String> lineIterator() {
        return lines.iterator();
    }
}
