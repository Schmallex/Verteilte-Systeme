package vertSys;

import java.util.AbstractMap;
import java.util.Iterator;

/**
 * Created by Alexander Görisch on 10.10.2017.
 */

public class StrThread implements Runnable {
    private String name;

    public StrThread(String name) {
        this.name = name;
    }

    public void run() {
        boolean found = false;
        Telefon buch = new Telefon();
        Iterator<AbstractMap.SimpleEntry> it = buch.iterator();
        while(it.hasNext()) {
            AbstractMap.SimpleEntry entry = it.next();
            if (entry.getKey().equals(name)) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Kein Eintrag zu " + name);
        }
    }
}
