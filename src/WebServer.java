//package je3.net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

    public class WebServer {
        public static void main(String args[]) {
            try {
                StringBuilder jsonBuff = new StringBuilder();
                int port = Integer.parseInt(args[0]);
                System.out.println("Listening on port: " + port);
                ServerSocket portSocket = new ServerSocket(port);

                while(true) {
                    Socket socket = portSocket.accept();

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream());

                    out.print("HTTP/1.1 200 \r\n");
                    out.print("Content-Type: application/json\n");
                    out.print("Connection: close\r\n");
                    out.print("\r\n");

                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.length() == 0)
                            break;
                        jsonBuff.append(line);
                        out.print(line + "\r\n");
                    }
                    System.out.println(jsonBuff.toString());
                    out.close();
                    in.close();
                    socket.close();
                }
            }
            catch (Exception e) {
                System.err.println(e);
                System.err.println("Usage: java WebServer <port>");
            }
        }
    }
