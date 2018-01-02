package vertSys;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander Görisch on 10.10.2017.
 */
public class Scan {
    /**
     * Analysiert den gegebenen Input undruft die entsprechenden Suchmethoden auf.
     *
     * @param s sei ein Eingabestring der aus Nummern und Buchstaben besteht
     */
    protected static List<String> analyse(String s) {
        Pattern r = Pattern.compile("(\\d)");
        Matcher m = r.matcher((s));
        String number = "";
        while (m.find()) {
            number += m.group();
        }
        String sn = s.substring(s.indexOf("=") +1, s.indexOf("&num"));
        sn=decode(sn);
         number = s.substring(s.indexOf("nummer=")+7,s.indexOf("&C"));
        if(number.isEmpty()&& sn.isEmpty()){
            List<String> ausgabe = new ArrayList();
            ausgabe.add("Leere Suche");
            return ausgabe;
        }
        else if (number.isEmpty()) {
            return searchName(sn);
        }
        else if (sn.isEmpty()) {
            return searchNumber(number);
        }
        else {
            return searchJoin(sn, number);
        }

    }
    public static String decode(String s){

        String d= s;
        d =d.replace("%C4","Ä");
        d =d.replace("%E4","ä");
        d =d.replace("%D6","Ö");
        d =d.replace("%F6","ö");
        d =d.replace("%DC","Ü");
        d =d.replace("%FC","ü");
        d=d.replace("%20"," ");
        return d;
    }

    /**
     * Startet einen Thread der Im Telefonbuch die gegebene Nummer sucht
     *
     * @param i sei ein Integer Wert
     */
    protected static List<String> searchNumber(String i) {
        List<String> ausgabe = new ArrayList();
        Thread th = new NumThread(i, ausgabe);
        th.start();
        try {
            th.join();
        } catch (Exception e) {
        }
        return ausgabe;
    }

    /**
     * Startet einen Thread der im Telefonbuch den gegebenen Namen sucht
     *
     * @param s sei ein String
     */
    protected static List<String> searchName(String s) {
        List<String> ausgabe = new ArrayList();
        Thread th = new StrThread(s, ausgabe);
        th.start();
        try {
            th.join();
        } catch (Exception e) {
        }
        return ausgabe;
    }

    /**
     * Startet zwei Threads die nebenläufig Im Telefonbuch nach Namen und Nummer suchen
     *
     * @param s sei ein String
     * @param i sei ein Integer
     */
    protected static List<String> searchJoin(String s, String i) {
        List numAusgabe = new ArrayList();
        List strAusgabe = new ArrayList();
        Thread threadStr = new StrThread(s, strAusgabe);
        Thread threadNum = new NumThread(i, numAusgabe);
        try {
            threadNum.start();
            threadStr.start();
            threadNum.join();
            threadStr.join();

        } catch (Exception e) {
            System.out.println("Thread interrupted");
        }
        strAusgabe.addAll(numAusgabe);
        return strAusgabe;
    }
}
