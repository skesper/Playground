package de.kesper.mlformatter;

/**
 * User: kesper
 * Date: 13.02.2015
 * Time: 10:39
 */
public class DelimTest {

    public static void main(String[] args) throws Exception {

        String s = "Auf dem Weg zur Zufahrt auf das MaRC Gelände, sah Hendrik noch weitere Menschen am Straßenrand. Sie standen dort und taten nichts weiter, als sie anzustarren, als sie vorbeifuhren. ''Zumindest stellen sie sich uns nicht mehr in den Weg'', dachte Hendrik. Und gleichzeitig traf es ihn, wie ein Truck von der Seite: Rachel war diesen Typen hilflos ausgeliefert.";

        String[] sp = s.split("''");

        System.out.println(sp.length+" splits.");

        for(String ss : sp) {
            System.out.println("    "+ss);
        }
    }
}
