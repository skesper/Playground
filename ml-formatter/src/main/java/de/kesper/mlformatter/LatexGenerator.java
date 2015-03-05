package de.kesper.mlformatter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * User: kesper
 * Date: 12.02.2015
 * Time: 15:16
 */
public class LatexGenerator implements Runnable, AutoCloseable {

    private FileWriter fw;
    private Iterator<String> lineIterator;

    private boolean useChapterNumbers = true;
    private boolean useSectionNumbers = true;

    public boolean isUseChapterNumbers() {
        return useChapterNumbers;
    }

    public void setUseChapterNumbers(boolean useChapterNumbers) {
        this.useChapterNumbers = useChapterNumbers;
    }

    public boolean isUseSectionNumbers() {
        return useSectionNumbers;
    }

    public void setUseSectionNumbers(boolean useSectionNumbers) {
        this.useSectionNumbers = useSectionNumbers;
    }

    public LatexGenerator(String title, String latexFile, Iterator<String> lineIterator) throws IOException {
        fw = new FileWriter(latexFile);
        this.lineIterator = lineIterator;

        String p = preamble.replace("__TITLE__", title);
        fw.write(p);
    }

    @Override
    public void close() throws Exception {
        fw.write(postamble);
        fw.flush();
        fw.close();
    }

    @Override
    public void run() {

        while(lineIterator.hasNext()) {
            String line = lineIterator.next();

            try {
                fw.write(mangle(line));
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String mangle(String paragraph) {
        if (paragraph==null) return "";

        if ("".equals(paragraph)) return "\n\n";

        paragraph = paragraph.trim();
        paragraph = paragraph.replace("„", "\"`");
        paragraph = paragraph.replace("“","\"'");
        paragraph = paragraph.replace("&","\\&");
        paragraph = paragraph.replace("%","\\%");
        paragraph = paragraph.replace("\"'\"`","\"'\n\n\"`");

        paragraph = sanitizeSpeech(paragraph);


        if (paragraph.startsWith("=")) {
            if (paragraph.startsWith("==")) {
                if (paragraph.startsWith("===")) {
                    paragraph = paragraph.replace("===", "");
                    if (useSectionNumbers) {
                        return "\\subsection{".concat(paragraph.trim()).concat("}\n\n");
                    } else {
                        return "\\subsection*{".concat(paragraph.trim()).concat("}\n\n");
                    }
                } else {
                    paragraph = paragraph.replace("==", "");
                    if (paragraph.contains("***")) {
                        return "\\begin{center}\n***\n\\end{center}\n\n";
                    } else if (useSectionNumbers) {
                        return "\\section{".concat(paragraph.trim()).concat("}\n\n");
                    } else {
                        return "\\section*{".concat(paragraph.trim()).concat("}\n\n");
                    }
                }
            } else {
                paragraph = paragraph.replace("=", "");
                if (useChapterNumbers) {
                    return "\\chapter{".concat(paragraph.trim()).concat("}\n\n");
                } else {
                    return "\\chapter*{".concat(paragraph.trim()).concat("}\n\n");
                }
            }
        } else if (paragraph.startsWith("TODO:")) {
            StringBuilder code = new StringBuilder();
            code.append("\\begin{quote}\n");
            code.append("\\footnotesize \n");
            code.append("\\color{blue}\n");
            code.append("\\textsl{ \n");
            code.append(paragraph);
            code.append("}\n");
            code.append("\\end{quote}\n\n");
            return code.toString();
        }

        if (hasBoldOrItalics(paragraph)) {
            paragraph = replaceBoldOrItalics(paragraph);
        }


        return paragraph.concat("\n");
    }

    private String sanitizeSpeech(String paragraph) {
        if (paragraph==null) return null;
        String[] spl = paragraph.split("\"'");
        if (spl==null || spl.length<2) return paragraph;

        StringBuilder p = new StringBuilder();
        p.append(spl[0]);
        for(int i=1;i<spl.length;++i) {
            char first = spl[i].charAt(0);
            if (Character.isAlphabetic(first) || Character.isDigit(first)) {
                p.append("\"' \\ \\ ");
            } else {
                p.append("\"'");
            }
            p.append(spl[i]);
        }
        if (spl[spl.length-1].equals("")) {
            p.append("\"'");
        }

        if (paragraph.endsWith("\"'")) {
            p.append("\"'");
        }

        return p.toString();
    }

    private String replaceBoldOrItalics(String paragraph) {
        paragraph = replaceByDelimiter(paragraph, MLDelimiterTypes.BOLD);
        paragraph = replaceByDelimiter(paragraph, MLDelimiterTypes.ITALICS);
        return paragraph;
    }

    private String replaceByDelimiter(String p, MLDelimiterTypes type) {
        StringBuilder target = new StringBuilder();

        String[] splits;

        switch(type) {
            case BOLD: {
                splits = p.split("'''");
            } break;
            case ITALICS: {
                splits = p.split("''");
            } break;
            default: {
                throw new RuntimeException("Unknown delimiter type! "+type);
            }
        }

//        System.out.println("DEBUG: splits: "+splits.length);
//
//        for(String split : splits) {
//            System.out.println("DEBUG: split : "+split+" - "+type);
//        }

        if (splits==null || splits.length<3) return p;

        boolean open = true;

        for(int i=1; i<splits.length;++i) {
            String split = splits[i];
            if (i==1) {
                target.append(splits[0]);
            }

            if (open) {
                open = false;
                switch(type) {
                    case BOLD: {
                        target.append(" \\textbf{");
                    } break;
                    case ITALICS: {
                        target.append(" \\textsl{");
                    }
                }
            } else {
                open = true;
                target.append("}");
            }

            target.append(split);
        }
        if (!open) target.append("}");

//        System.out.println("DEBUG: contains special formatting: "+target.toString());
        return target.toString();
    }


    private boolean hasBoldOrItalics(String p) {
        if (p==null) return false;
        int idx = p.indexOf("''");
        return idx>=0;
    }



    private static final String preamble = "\\documentclass[10pt,a5paper,final,openright]{memoir}\n" +
            "\\usepackage[ngerman]{babel}\n" +
            "\\usepackage[T1]{fontenc}\n" +
            "\\usepackage[utf8]{inputenc}\n" +
            "\\usepackage{graphicx}\n" +
            "\\usepackage{color}\n" +
            "\n" +
            "% Werte nach http://www.boooks.org/\n" +
            "\\usepackage[a5paper]{geometry}\n" +
            "\\setlength{\\textwidth}{108mm}\n" +
            "\\setlength{\\textheight}{170mm}\n" +
            "\\setlength{\\topmargin}{-10mm}\n" +
            "\\setlength{\\headsep}{0mm}\n" +
            "\\setlength{\\headheight}{0mm}\n" +
            "\\setlength{\\oddsidemargin}{-5mm}\n" +
            "\\setlength{\\evensidemargin}{-5mm}\n" +
            "\n" +
            "\\usepackage{librecaslon}\n" +
            "\\linespread{1.05}\n" +
            "\n" +
            "\\sloppy\n" +
            "\n" +
            "\\begin{document}\n" +
            "\\fixpdflayout\n" +
            "\\frontmatter\n" +
            "%\\begin{titlepage}\n" +
            "\\pagestyle{empty}\n" +
            "\\begin{center}\n" +
            "{Stephan Kesper}\n" +
            "\\vskip 2cm\n" +
            "{\\Huge __TITLE__}\n" +
            "\\vskip 2cm\n" +
            "{Roman}\n" +
            "\\vfill\n" +
            "\n" +
            "\\includegraphics[scale=0.05]{Vesta-s.png}\n" +
            "\n" +
            "{\\tiny \\textit{Vesta Verlag * 2014}}\n" +
            "\\end{center}\n" +
            "\\newpage\n" +
            "%\\end{titlepage}\n" +
            "\\noindent{\\footnotesize  \\textbf{Stephan Kesper}\\\\\n" +
            "__TITLE__\\\\\n" +
            "Vesta Verlag\n" +
            "}\n" +
            "\\vfill\n" +
            "\\noindent {\\footnotesize  \\textcopyright \\  Copyright 2015 by Stephan Kesper \\\\\n" +
            "ISBN: 3- \\\\\n" +
            "Satz: Stephan Kesper\\\\\n" +
            "Produktion: Vesta Verlag\\\\\n" +
            "Lektorat: Alexa Simon\\\\\n" +
            "Umschlaggestaltung: NF|G -- \\texttt{http://nfg-net.de}\\\\\n" +
            "\n" +
            "\\bigskip \n" +
            "\n" +
            "\\noindent Die vorliegende Publikation ist urheberrechtlich geschützt. Alle Rechte vorbehalten. Kein Teil dieses Buches darf ohne schriftliche Genehmigung des Verlags in irgendeiner Form durch Fotokopie, Mikrofilm oder andere, im Besonderen digitale Verfahren, reproduziert oder in eine für Maschinen, insbesondere Datenverarbeitungsanalgen, verwendbare Sprache übertragen werden. Auch die Rechte der Wiedergabe durch Vortrag, Funk und Fernsehen, sowie aller weiterer Medien, sind vorbehalten.\n" +
            "}\n" +
            "\n" +
            "\\clearpage\n" +
            "\\hfill\n" +
            "\\vskip 3cm\n" +
            "\\begin{center}\n" +
            "\\textit{für Alexa}\n" +
            "\\end{center}\n" +
            "\\vfill\n" +
            "\\clearpage\n" +
            "\\hfill\n" +
            "\n" +
            "\\mainmatter\n" +
            "\\pagestyle{plain}\n\n\n";

    private static final String postamble = "\n" +
            "\\end{document}\n";
}
