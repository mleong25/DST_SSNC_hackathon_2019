import com.mashape.unirest.http.Unirest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
//                    System.out.println(result);
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
                        System.out.println(channel_id + " " + team_id + " " + text);
                        String keyword = "create";
                        if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                            System.out.println("Create found");
                            keyword = "channel";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {

                                int count = 0;
                                int i;
                                start = '+';
                                end = '+';
                                String type = "";
                                String name = "";
                                String display_name = "";
                                for(i = 0; i < text.length(); i++) {
                                    if(text.charAt(i) == '+') {
                                        count++;
                                        if(count == 2) {
                                            name = text.substring(i+1, text.indexOf(end, i+1));
                                            System.out.println("name: " + name);
                                        }
                                        if(count == 3) {
                                            display_name = text.substring(i+1, text.indexOf(end, i+1));
                                            System.out.println("dname: " + display_name);
                                        }
                                        if(count == 4) {
                                            type = text.substring(i + 1);
                                            System.out.println("type: " + type);
                                        }
                                    }
                                }

//                                name = text.substring(text.indexOf(start, 18), text.indexOf(end, 20));
//
//                                display_name = text.substring(text.indexOf(start, 20), text.indexOf(end, text.indexOf(start, 20) + 1));

                                if(type.equals("private")) {
                                    type = "P";
                                }
                                else {
                                    type = "O";
                                }

                                if(count == 4) {
                                    System.out.println("{\n" +
                                            "    \"team_id\": \"" + team_id + "\",\n" +
                                            "    \"name\": \""+ name +"\",\n" +
                                            "    \"display_name\": \"" + display_name + "\",\n" +
                                            "    \"type\": \""+ type +"\"\n" +
                                            "}");
                                    System.out.println("Creating new channel");
                                    response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/channels")
                                            .header("Content-Type", "application/json")
                                            .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
                                            .header("User-Agent", "PostmanRuntime/7.15.2")
                                            .header("Accept", "*/*")
                                            .header("Cache-Control", "no-cache")
                                            .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                            .header("Host", "10.226.88.81:5000")
                                            .header("Accept-Encoding", "gzip, deflate")
                                            .header("Connection", "keep-alive")
                                            .header("cache-control", "no-cache")
                                            .body("{\n" +
                                                    "    \"team_id\": \"" + team_id + "\",\n" +
                                                    "    \"name\": \""+ name +"\",\n" +
                                                    "    \"display_name\": \"" + display_name + "\",\n" +
                                                    "    \"type\": \""+ type +"\"\n" +
                                                    "}")
                                            .asJson().toString();
                                    response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/posts")
                                            .header("Content-Type", "application/json")
                                            .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
                                            .header("User-Agent", "PostmanRuntime/7.15.2")
                                            .header("Accept", "*/*")
                                            .header("Cache-Control", "no-cache")
                                            .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                            .header("Host", "10.226.88.81:5000")
                                            .header("Accept-Encoding", "gzip, deflate")
                                            .header("Connection", "keep-alive")
                                            .header("cache-control", "no-cache")
                                            .body("{\n" +
                                                    "    \"channel_id\": \"" + channel_id + "\",\n" +
                                                    "    \"message\": \"Channel status:\",\n" +
                                                    "    \"props\": {\n" +
                                                    "        \"attachments\": [\n" +
                                                    "            {\n" +
                                                    "                \"text\": \"" + "Channel creation request sent for channel "+ display_name +". Channel should be available now: https://chat-hack.dstcorp.net/team4p/channels/"+ name + "\"\n" +
                                                    "            }\n" +
                                                    "        ]\n" +
                                                    "    }\n" +
                                                    "}")
                                            .asJson().toString();
                                } else {
                                    response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/posts")
                                            .header("Content-Type", "application/json")
                                            .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
                                            .header("User-Agent", "PostmanRuntime/7.15.2")
                                            .header("Accept", "*/*")
                                            .header("Cache-Control", "no-cache")
                                            .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                            .header("Host", "10.226.88.81:5000")
                                            .header("Accept-Encoding", "gzip, deflate")
                                            .header("Connection", "keep-alive")
                                            .header("cache-control", "no-cache")
                                            .body("{\n" +
                                                    "    \"channel_id\": \"" + channel_id + "\",\n" +
                                                    "    \"message\": \"Channel status:\",\n" +
                                                    "    \"props\": {\n" +
                                                    "        \"attachments\": [\n" +
                                                    "            {\n" +
                                                    "                \"text\": \"" + "Error creating channel. Command format: ccb create channel name display_name type(public or private)" + "\"\n" +
                                                    "            }\n" +
                                                    "        ]\n" +
                                                    "    }\n" +
                                                    "}")
                                            .asJson().toString();
                                }
                            }
                            keyword = "email";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                                System.out.println("Creating new email");
                            }
                            keyword = "alert";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                                int count = 0;
                                int i;
                                start = '+';
                                end = '+';
                                String phone = "";
                                String message = "";
                                for(i = 0; i < text.length(); i++) {
                                    if (text.charAt(i) == '+') {
                                        count++;
                                        System.out.println(count);
                                        if (count == 3) {
                                            System.out.println(i);
                                            System.out.println(text.indexOf(end, i+1));
                                            phone = text.substring(i+1, text.indexOf(end, i+1));
                                            System.out.println(phone);
                                        }
                                        if(count == 4) {
                                            message = text.substring(i+1);
                                            System.out.println(message);
                                        }
                                    }
                                }
                                message = message.replace('+', ' ');
                                System.out.println(message);

                                if(count > 4) {
                                    System.out.println("{\\n\" +\n" +
                                            "                                                    \"    \\\"recipients\\\": [\\n\" + \"{\" +\n" +
                                            "                                                    \"           \\\"type\\\": \\\"phone\\\",\" +\n" +
                                            "                                                    \"           \\\"id\\\": \\\"+\" + phone + \"\\\",\" +\n" +
                                            "                                                    \"           \\\"response_required\\\": \\\"false\\\"\" +\n" +
                                            "                                                    \"}\" + \"],\\n\" +\n" +
                                            "                                                    \"    \\\"message\\\": \\\"\" + message + \"\\\"\\n\" +\n" +
                                            "                                                    \"}");
                                    System.out.println("Creating new alert");
                                    response = Unirest.post("https://dev-alert.dstcorp.net/api/v1/message")
                                            .header("Content-Type", "application/json")
                                            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDbENpMHJCcC1vNndYS2tET1BOQXpWM1B2VnNNVmlJcjNhZ2ZJeW9OOXRzIn0.eyJqdGkiOiI4MjM5M2I3MS03YmNkLTQ5NjQtYTZhNC00YTIzZDk3Y2QxM2YiLCJleHAiOjE1NjUxNDA0MDksIm5iZiI6MCwiaWF0IjoxNTY1MTExNjA5LCJpc3MiOiJodHRwczovL2tleWNsb2FrdGVzdC5kc3Rjb3JwLm5ldC9hdXRoL3JlYWxtcy9TU05DLUludGVybmFsIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImU1ZWVmM2NjLWU0MDUtNDliZS1iNDZjLWQ2MGExNDU3MDVhYSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFsZXJ0aW5nLWRldiIsImF1dGhfdGltZSI6MTU2NTExMTAzOSwic2Vzc2lvbl9zdGF0ZSI6IjRhNGRmZTAyLWQxNDgtNGI0Ny04YzEzLTA0NmJlYWRhMjEyNyIsImFjciI6IjAiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly9kZXYtYWxlcnQuZHN0c3lzdGVtcy5jb20iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwibGFzdE5hbWUiOiJXZWVrbGV5IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiQnJvY2sgV2Vla2xleSIsInByZWZlcnJlZF91c2VybmFtZSI6ImJ3ZWVrbGV5QGRzdHN5c3RlbXMuY29tIiwiZ2l2ZW5fbmFtZSI6IkJyb2NrIiwiZmFtaWx5X25hbWUiOiJXZWVrbGV5IiwiZW1haWwiOiJid2Vla2xleUBkc3RzeXN0ZW1zLmNvbSJ9.G75qdxgPqI26Bzi28zxFvTlUUb9GKiezmOhgjKZ99KoFSdthZs-dqUWNJ-fCTZNXLqEnytljRS0PYecdeYO-zjFpBvFNunFvAzHRERqgGCDQw4Pn5WeZCxaj-XZYTUwv9_y-j1gM5QaV29_zRaQLoLoyqVlUPOJ55aSypJgMdAS3Wdcj5gQIzgw5Iq4PNLu12PUYXLvvvido45J_SjCMgfNTvcn89VdoZDk8Y81YsqKoVYZDrHDUw1J4okO1etK0dm6Pj6oLqJa2zuqgS1SRAo06TwfEuOHWiAIB32aUYwdC3Fh5Ys-rrMNlFvFnCfB6-abkb6_ax42ni3aH5zAJig")
                                            .header("User-Agent", "PostmanRuntime/7.15.2")
                                            .header("Accept", "*/*")
                                            .header("Cache-Control", "no-cache")
                                            .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                            .header("Host", "10.226.88.81:5000")
                                            .header("Accept-Encoding", "gzip, deflate")
                                            .header("Connection", "keep-alive")
                                            .header("cache-control", "no-cache")
                                            .body("{\n" +
                                                    "    \"recipients\": [\n" + "{" +
                                                    "           \"type\": \"phone\"," +
                                                    "           \"id\": \"+" + phone + "\"," +
                                                    "           \"response_required\": \"false\"" +
                                                    "}" + "],\n" +
                                                    "    \"message\": \"" + message + "\"\n" +
                                                    "}")
                                            .asJson().toString();
                                }
                                else {
                                    response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/posts")
                                            .header("Content-Type", "application/json")
                                            .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
                                            .header("User-Agent", "PostmanRuntime/7.15.2")
                                            .header("Accept", "*/*")
                                            .header("Cache-Control", "no-cache")
                                            .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                            .header("Host", "10.226.88.81:5000")
                                            .header("Accept-Encoding", "gzip, deflate")
                                            .header("Connection", "keep-alive")
                                            .header("cache-control", "no-cache")
                                            .body("{\n" +
                                                    "    \"channel_id\": \"" + channel_id + "\",\n" +
                                                    "    \"message\": \"Error creating alert. Command format: ccb create alert number(only numbers, no spaces with area code) message\",\n" +
                                                    "    \"props\": {\n" +
                                                    "        \"attachments\": [\n" +
                                                    "            {\n" +
                                                    "                \"text\": \"" + text + "\"\n" +
                                                    "            }\n" +
                                                    "        ]\n" +
                                                    "    }\n" +
                                                    "}")
                                            .asJson().toString();
                                }
                            }
                        }
                        else {
                            System.out.println("unrecognized/test");
                            System.out.println(channel_id + " " + team_id + " " + text);

                            keyword = "test";
                            if(text.toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                                response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/posts")
                                        .header("Content-Type", "application/json")
                                        .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
                                        .header("User-Agent", "PostmanRuntime/7.15.2")
                                        .header("Accept", "*/*")
                                        .header("Cache-Control", "no-cache")
                                        .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                        .header("Host", "10.226.88.81:5000")
                                        .header("Accept-Encoding", "gzip, deflate")
                                        .header("Connection", "keep-alive")
                                        .header("cache-control", "no-cache")
                                        .body("{\n" +
                                                "    \"channel_id\": \"" + channel_id + "\",\n" +
                                                "    \"message\": \"This is a message from a bot\",\n" +
                                                "    \"props\": {\n" +
                                                "        \"attachments\": [\n" +
                                                "            {\n" +
                                                "                \"text\": \"" + text + "\"\n" +
                                                "            }\n" +
                                                "        ]\n" +
                                                "    }\n" +
                                                "}")
                                        .asJson().toString();
                            }
                            else {
                                response = Unirest.post("https://chat-hack.dstcorp.net/api/v4/posts")
                                        .header("Content-Type", "application/json")
                                        .header("Authorization", "Bearer hbqxks5d37bpuyekjuctf54nfh")
                                        .header("User-Agent", "PostmanRuntime/7.15.2")
                                        .header("Accept", "*/*")
                                        .header("Cache-Control", "no-cache")
                                        .header("Postman-Token", "e951ebc3-1a4e-4d2e-8142-72b6dad7fe21,2ae88811-9f20-46a0-abe7-709d0612e3e3")
                                        .header("Host", "10.226.88.81:5000")
                                        .header("Accept-Encoding", "gzip, deflate")
                                        .header("Connection", "keep-alive")
                                        .header("cache-control", "no-cache")
                                        .body("{\n" +
                                                "    \"channel_id\": \"" + channel_id + "\",\n" +
                                                "    \"message\": \"I didn't understand that command\",\n" +
                                                "    \"props\": {\n" +
                                                "        \"attachments\": [\n" +
                                                "            {\n" +
                                                "                \"text\": \"" + text + "\"\n" +
                                                "            }\n" +
                                                "        ]\n" +
                                                "    }\n" +
                                                "}")
                                        .asJson().toString();
                            }
                        }
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
