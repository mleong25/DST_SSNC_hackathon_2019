import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

                    Scanner s = new Scanner(in).useDelimiter("user_name");
                    String result = s.hasNext() ? s.next() : "";

//                    String line;
//                    while ((line = in.readLine()) != null) {
//                        jsonBuff.append(line);
//                        out.print(line + "\r\n");
//                        System.out.println("\n" + jsonBuff + "\n\n\n\n\n\n");
//
//                    }

                    String response;
                    //TODO: pull token id from result and insure it matches webhook token for security
                    if("" != result) {
                        System.out.println("Result found");
                        char start;
                        char end;

                        //Get team id from post
                        start = '+';
                        end = '&';
                        String team_id = result.substring(result.indexOf(start) - 35, result.indexOf(end, 330));

                        //Get channel id from post
                        start = '=';
                        end = '&';

                        String channel_id = result.substring(result.indexOf(start) + 1, result.indexOf(end));

                        //Get text from post
                        start = '+';
                        end = '&';

                        String text = result.substring(result.indexOf(start) + 1, result.indexOf(end, result.indexOf(start)));

                        String keyword = "create";
                        if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                            System.out.println("Create found");
                            keyword = "channel";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                                System.out.println("Creating new channel");
                            }
                            keyword = "email";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                                System.out.println("Creating new email");
                            }
                            keyword = "alert";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                                System.out.println("Creating new alert");
                                HttpClient client = HttpClientBuilder.create().build();
                                HttpPost post = new HttpPost("http://dev-alert.dstcorp.net/api/v1/message");

                                // Create some NameValuePair for HttpPost parameters
                                List<NameValuePair> arguments = new ArrayList<>(3);
                                arguments.add(new BasicNameValuePair("Content-Type", "application/json"));
                                arguments.add(new BasicNameValuePair("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh"));
                                arguments.add(new BasicNameValuePair("Host", "chat-hack.dstcorp.net"));
                                arguments.add(new BasicNameValuePair("Accept", "*/*"));
                                arguments.add(new BasicNameValuePair("User-Agent", "ccb"));
                                arguments.add(new BasicNameValuePair("Connection", "keep-alive"));
                                System.out.println(arguments);

                                try {
                                    post.setEntity(new UrlEncodedFormEntity(arguments));
                                    HttpResponse returned = client.execute(post);

                                    // Print out the response message
                                    System.out.println("\n\n" + EntityUtils.toString(returned.getEntity()));
                                    System.out.println("Success \n\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println(e.toString());
                                }
                            }
                        }
                        else {
                            System.out.println("unrecognized");
                            System.out.println(channel_id + " " + team_id + " " + text);

//                            HttpURLConnection connection = null;
//                            try {
//                                URL url = new URL("https://chat-hack.dstcorp.net/api/v4/posts");
//                                connection = (HttpsURLConnection) url.openConnection();
//                                connection.setRequestMethod("POST");
//
//                            } finally {
//                                connection.disconnect();
//                            }
//                            try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
//
//                                HttpPost request = new HttpPost("https://httpbin.org/post");
//                                request.setHeader("User-Agent", "Java client");
//                                request.setEntity(new StringEntity("My test data"));
//
//                                HttpResponse returned = (HttpResponse) client.execute(request);
//
//                                BufferedReader bufReader = new BufferedReader(new InputStreamReader(
//                                        returned.getEntity().getContent()));
//
//                                StringBuilder builder = new StringBuilder();
//
//                                String line;
//
//                                while ((line = bufReader.readLine()) != null) {
//                                    builder.append(line);
//                                    builder.append(System.lineSeparator());
//                                }
//
//                                System.out.println(builder);
//                            }
//                                response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/posts")
//                                        .header("Content-Type", "application/json")
//                                        .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
//                                        .header("User-Agent", "PostmanRuntime/7.15.2")
//                                        .header("Accept", "*/*")
//                                        .header("Cache-Control", "no-cache")
//                                        .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
//                                        .header("Host", "chat-hack.dstcorp.net")
//                                        .header("Accept-Encoding", "gzip, deflate")
//                                        .header("Content-Length", "153")
//                                        .header("Connection", "keep-alive")
//                                        .header("cache-control", "no-cache")
//                                        .body("{\n" +
//                                                "    \"channel_id\": \"" + channel_id + "\",\n" +
//                                                "    \"message\": \"This is a message from a bot\",\n" +
//                                                "    \"props\": {\n" +
//                                                "        \"attachments\": [\n" +
//                                                "            {\n" +
//                                                "                \"text\": \"" + text + "\"\n" +
//                                                "            }\n" +
//                                                "        ]\n" +
//                                                "    }\n" +
//                                                "}")
//                                        .asJson().toString();

//                            System.out.println(response);

                            HttpClient client = HttpClientBuilder.create().build();
                            HttpPost post = new HttpPost("https://chat-hack.dstcorp.net/api/v4/teams");

                            // Create some NameValuePair for HttpPost parameters
                            List<NameValuePair> arguments = new ArrayList<>(3);
                            arguments.add(new BasicNameValuePair("Content-Type", "application/json"));
                            arguments.add(new BasicNameValuePair("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh"));
                            arguments.add(new BasicNameValuePair("Host", "chat-hack.dstcorp.net"));
                            arguments.add(new BasicNameValuePair("Accept", "*/*"));
                            arguments.add(new BasicNameValuePair("User-Agent", "ccb"));
                            arguments.add(new BasicNameValuePair("Connection", "keep-alive"));
                            System.out.println(arguments);

                            try {
                                post.setEntity(new UrlEncodedFormEntity(arguments));
                                HttpResponse returned = client.execute(post);

                                // Print out the response message
                                System.out.println("\n\n" + EntityUtils.toString(returned.getEntity()));
                                System.out.println("Success \n\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println(e.toString());
                            }
                        }
                        System.out.println(channel_id + " " + team_id + " " + text);
                    }

                    out.close();
                    in.close();
                    socket.close();
                }
            } catch (Exception e) {
                System.err.println(e);
                System.err.println("Usage: java WebServer <port>");
            }
        }
    }
