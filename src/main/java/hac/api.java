package hac;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


// a simple demo servlet to remove from your repo upon submission!
@WebServlet(name = "api", urlPatterns = {"/getQuestions"})
public class api extends HttpServlet {


    private final String PARAM_NAME = "POLLFILE";
    private final static Map<String, Integer> poll = new HashMap<>();
    private String question = "";
//peace

    public void init() {
        String fileName = getServletContext().getInitParameter(PARAM_NAME);
        String filePath = getServletContext().getRealPath(fileName);

        // read line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            question = br.readLine();
            while ((line = br.readLine()) != null) {
                poll.put(line, 0);
                //lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");

        JsonArrayBuilder builder = Json.createArrayBuilder();


        builder.add(question);

        for (String answer : poll.keySet()) {
            builder.add(answer);
            builder.add(poll.get(answer));
        }

        JsonArray jsonArray = builder.build();
        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonw = Json.createWriter(out);
            jsonw.write(jsonArray);
            jsonw.close();
        } catch (Exception e) {
            //todo
            System.out.println("catch");
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");

        JsonArrayBuilder builder = Json.createArrayBuilder();


        builder.add("question");


        JsonArray jsonArray = builder.build();
        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonw = Json.createWriter(out);
            jsonw.write(jsonArray);
            jsonw.close();
        } catch (Exception e) {
            //todo
            System.out.println("catch");
        }

    }


    public void destroy() {
        System.out.println("des");
    }
}