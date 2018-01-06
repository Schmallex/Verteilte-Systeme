package vertSys;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static vertSys.Scan.encode;

/**
 * Created by Alexander Görisch on 10.10.2017.
 */
public class NumThread extends Thread {
    private String nummer;
    public List<String> ausgabe;

    protected NumThread(String nummer, List ausgabe) {
        this.nummer = nummer;
        this.ausgabe = ausgabe;
    }

    public void run() {
        boolean running = true;
        while (running) {
            boolean found = false;
            Telefon buch = new Telefon();
            Iterator<AbstractMap.SimpleEntry> it = buch.iterator();
            while (it.hasNext()) {
                AbstractMap.SimpleEntry entry = it.next();
                if (entry.getValue().equals(nummer)) {
                    ausgabe.add(encode((String) entry.getKey()) + " " + entry.getValue());
                    found = true;
                }
            }
            running = false;
            if (!found) {
                ausgabe.add("Kein Eintrag zu " + nummer);
            }
        }
    }
}
