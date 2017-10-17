package vertSys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Alexander Görisch on 10.10.2017.
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.println("Bitte Name eingeben(Exit zum schließen)");
                String name = reader.readLine();
                if (name.equals("Exit") || name.equals("exit")) {
                    break;
                }
                System.out.print("Bitte Nummer eingeben");
                String nummer = reader.readLine();
                List<String> ausgabe= Scan.analyse("Name=" + name + "&Nummer=" + nummer);
                Iterator<String> it = ausgabe.iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
            }
            reader.close();
        } catch (IOException e) {
        }

    }
}
