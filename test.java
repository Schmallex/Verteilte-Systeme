package vertSys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

import java.util.List;

import static vertSys.Scan.analyse;

/**
 * Created by Alex on 21.12.2017.
 */
public class test {
    public static void main(String[] args) throws Exception {
        //Socket Erstellung
        int port = 3141;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Serverantwort auf Port : " + port);

        // Auf Connections warten
        while (true) {

            Socket clientSocket = serverSocket.accept();
            System.err.println("Neuer Client verbunden");

            // on ouvre un flux de converation

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            //
            //
            // Verarbeiten der Get Request

            String request = in.readLine();
            String s;
            if(request.startsWith("GET /favicon")) {
                System.out.println("Favicon-Request");
                in.close();
                continue;                       // Zum naechsten Request
            }
            while ((s = in.readLine()) != null) {
                System.out.println(s);

                if(s.contains("Get /?back")){

                }

                if (s.isEmpty()) {
                    break;
                }
            }
            CharSequence name ="name";
            System.out.print("Request : "+request);
            if(request.contains(name)){
                String escaped = URLDecoder.decode(request,"UTF-8");
                System.out.println(escaped);
                List<String> ausgabe = Scan.analyse(escaped);
                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Connection:close\r\n");
                out.write("Server: Apache/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("Content-Length: 20\r\n");
                out.write("\r\n");
                out.write("<html>");
                out.write("<body>");
                out.write("<h2 align=center>Telefonverzeichnis</h2>");
                out.write("<p>deine ma</p>");
                out.write("<h3>Ergebnis</h3>");
                out.write("<ul>");
              /*  for (String f:ausgabe) {
                    out.write("<li>"+f+"</li>");
                }*/
                out.write("</ul>");
                out.write("</body>");
                out.write("</html>");
                out.flush();
                System.err.println("Verbindung mit Client beendet");
                out.close();
                in.close();
                clientSocket.close();

            }
            else{
                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Connection:close\r\n");
                out.write("Server: Apache/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("Content-Length: 400\r\n");
                out.write("\r\n");
                out.write("<html>");
                out.write("<body>");
                out.write("<h2 align=center>Telefonverzeichnis</h2>");
                out.write("<h3>Nach Name,Nummer oder beidem suchen</h3>");
                out.write("<p>deine ma</p>");
                out.write("<form method=get action='http://localhost:3141'");
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

                //Schließen der Verbindung.
                System.err.println("Verbindung mit Client beendet");
                out.close();
                in.close();
                clientSocket.close();
            }


        }
    }
}
