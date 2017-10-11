package vertSys;

import java.util.Scanner;

/**
 * Created by Alexander Görisch on 10.10.2017.
 */
public class Main {
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        while (true){
            System.out.println("Bitte Name/Nummer oder beides(Exit zum schließen)");
            String s = reader.next();
            if(s.equals("Exit")|| s.equals("exit")){
                break;
            }
            Scan.analyse(s);
        }
        reader.close();
    }
}
