package vertSys;

/**
 * Created by Alexander Görisch on 10.10.2017.
 */
public class Scan {
    /**
     * Analysiert den gegebenen Input undruft die entsprechenden Suchmethoden auf.
     * @param s sei ein Eingabestring der aus Nummern und Buchstaben besteht
     */
    protected static void analyse(String s) {
        try {
            int i = Integer.parseInt(s);
            searchNumber(i);
        } catch (Exception e) {
            if (s.matches(".*\\d+.*")) {
                String[] parts = s.split("_");
                try {
                    searchJoin(parts[0], Integer.parseInt(parts[1]));
                } catch (Exception en) {
                    searchJoin(parts[1], Integer.parseInt(parts[0]));
                }
            } else {
                searchName(s);
            }
        }

    }

    /**
     * Startet einen Thread der Im Telefonbuch die gegebene Nummer sucht
     * @param i sei ein Integer Wert
     */
    protected static void searchNumber(int i) {
        Runnable run = new NumThread(i);
        Thread th = new Thread(run);
        th.start();

    }

    /**
     * Startet einen Thread der im Telefonbuch den gegebenen Namen sucht
     * @param s sei ein String
     */
    protected static void searchName(String s) {
        Runnable run = new StrThread(s);
        Thread th = new Thread(run);
        th.start();
    }

    /**
     * Startet zwei Threads die nebenläufig Im Telefonbuch nach Namen und Nummer suchen
     * @param s sei ein String
     * @param i sei ein Integer
     */
    protected static void searchJoin(String s, int i) {
        Runnable runStr = new StrThread(s);
        Thread threadStr = new Thread(runStr);
        Runnable runNum = new NumThread(i);
        Thread threadNum = new Thread(runNum);
        try {
            threadNum.start();
            threadStr.start();
            threadNum.join();
            threadStr.join();

        } catch (Exception e) {
            System.out.println("Thread interrupted");
        }
    }
}
