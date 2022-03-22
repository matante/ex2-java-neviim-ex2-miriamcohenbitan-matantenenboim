package hac;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


// a simple demo servlet to remove from your repo upon submission!
@WebServlet(name = "api", urlPatterns = {"/getQuestions"})
public class api extends HttpServlet {


    private final String PARAM_NAME = "POLLFILE";
    private List<String> lines = new ArrayList<>();


    public void init() {
        System.out.println("in init");
        String fileName = getServletContext().getInitParameter(PARAM_NAME);
        String filePath = getServletContext().getRealPath(fileName);

        // read line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finished init");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("in get");
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("title", lines.get(0));
        for (int i = 1; i < lines.size(); i ++){
            builder.add("answer" + i, lines.get(i));
        }

        JsonObject jsonObject = builder.build();
        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonw = Json.createWriter(out);
            jsonw.write(jsonObject);
            jsonw.close();
        }catch (Exception e){
            System.out.println("catch");
        }
        System.out.println("out of get");
    }




    public void destroy() {
        System.out.println("des");
    }
}