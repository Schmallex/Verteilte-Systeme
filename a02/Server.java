package vertSys;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.List;


/**
 * Created by Alex on 21.12.2017.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hostname?");
        String host = br.readLine();
        System.out.println("Port?");
        int port = Integer.parseInt(br.readLine());
        //Socket Erstellung
        //int port = 9865;
        ServerSocket serverSocket = new ServerSocket(port);


        System.err.println("Serverantwort auf Port : " + port);
        String http = "http://" + host + ":" + port;

        // Auf Connections warten
        while (true) {

            Socket clientSocket = serverSocket.accept();
            System.err.println("Neuer Client verbunden");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // Verarbeiten der Get Request

            String request = in.readLine();
            if (request.startsWith("GET /favicon")) {
                System.out.println("Favicon-Request");
                in.close();
                out.close();
                continue;                       // Zum naechsten Request
            }

            System.out.println("Request : " + request);

            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Connection:close\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("\r\n");

            if (request.startsWith("GET /?name") && !request.contains("Server+beenden")) {//Telefonsuche

                System.out.println("Such-Request");
                List<String> ausgabe = Scan.analyse(request);
                out.write("<html>");
                out.write("<body>");
                out.write("<h2 align=center>Telefonbuch</h2>");
                out.write("<h3>Ergebnis</h3>");
                out.write("<ul>");
                for (String f : ausgabe) {
                    out.write("<li>" + f + "</li>");
                }
                out.write("</ul>");
                out.write("<form method=get action='" + http + "'");
                out.write("<table>");
                out.write("<td><input type=submit name=back value=back></td>");
                out.write("</table>");
                out.write("</body>");
                out.write("</html>");
                out.flush();

            } else if (request.contains("Server+beenden")) {//Server_OLD beenden
                out.write("<html>");
                out.write("<body>");
                out.write("Server beendet");
                out.write("</body>");
                out.write("</html>");
                out.flush();
                System.err.println("Verbindung mit Client beendet");
                System.err.println("Server beendet");
                out.close();
                in.close();
                clientSocket.close();
                break;

            } else {//Standardantwort
                System.out.println("Standard-Request");
                out.write("<html>");
                out.write("<body>");
                out.write("<h2 align=center>Telefonverzeichnis</h2>");
                out.write("<h3>Nach Name,Nummer oder beidem suchen</h3>");
                out.write("<form method=get action='" + http + "'");
                out.write("<table>");
                out.write("<tr> <td valign=top>Name:</td>    <td><input name=name></td>    <td></td> </tr>");
                out.write("<tr> <td valign=top>Nummer:</td> <td><input name=nummer></td>    <td></td> </tr>");
                out.write("<tr> <td valign=top><input type=submit name=C value=Suchen></td>");
                out.write("<td><input type=reset></td>");
                out.write("<td><input type=submit name=D value=\"Server beenden\" ></td> </tr>");
                out.write("</table>");
                out.write("</form>");
                out.write("</body>");
                out.write("</html>");
                out.flush();
            }

            //Schließen der Verbindung.
            System.err.println("Verbindung mit Client beendet");
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
}
