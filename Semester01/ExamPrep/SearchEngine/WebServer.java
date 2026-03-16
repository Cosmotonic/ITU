
// it extracts all words on a page then search those words 

// main task here is to focus on logic. Extract logic, introduct object orient,
// like separate the different lists. Then imporve performance. 
// can we use mod %? And tree views. 

package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

// Classes 
// web server logic 
// 
// search logic 

// 
// note: one method does one thing. 

public class WebServer {
  static final int BACKLOG = 0; // rapport
  static final Charset CHARSET = StandardCharsets.UTF_8; // formatering til internet tekst

  // for each page we have a list of words and pages
  // very innificient
  HttpServer server;
  int port;
  private SearchEngine searchEngine;

  public WebServer(int port) throws IOException {
    this.port = port;
    // Start search engine
    searchEngine = new SearchEngine();

    // Start frontend
    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);

    server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html"))); // Start website.
    server.createContext("/search", io -> toByte(io, searchEngine.search(converteToString(io)))); // IO:inputoutput

    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
    server.start();
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");

  }

  public String converteToString(HttpExchange io) {
    String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
    return searchTerm;
  }

  public byte[] getFile(String filename) { // convertes all to bytes so the web can read it.
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  public void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {

      // throw exception?
    } finally {
      io.close();
    }
  }

  public static Charset getCharset() {
    return CHARSET;
  }

  public void toByte(HttpExchange io, List<String> response) {

    byte[] bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);

  }

  public void stop() {
    server.stop(0);
  }

  public int getPort() {
    return port;
  }

  public static void main(final String... args) throws IOException {

    int port = 8080;
    WebServer server = new WebServer(port);


  }

}