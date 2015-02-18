package de.kesper.mlformatter;

/**
 * User: kesper
 * Date: 12.02.2015
 * Time: 15:13
 */
public class MLFormatter {

    public static void main(String[] args) throws Exception {

        try (MLParser p = new MLParser("D:\\tmp\\privte\\export\\sealed\\mediawiki.txt")) {
            p.run();
            try (LatexGenerator lg = new LatexGenerator("Sealed", "D:\\tmp\\privte\\export\\sealed\\mediawiki.tex", p.lineIterator())) {
//                lg.setUseChapterNumbers(false);
                lg.setUseSectionNumbers(false);
                lg.run();
            }
        }



    }
}
