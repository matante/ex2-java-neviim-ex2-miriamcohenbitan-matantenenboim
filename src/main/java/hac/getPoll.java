package hac;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


// a simple demo servlet to remove from your repo upon submission!
@WebServlet(name = "getPoll", urlPatterns = {"/api/getPoll"})
public class getPoll extends HttpServlet {

    private Poll poll = null;

    public void init() {
        poll = (Poll) getServletContext().getAttribute("poll");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");

        JsonArrayBuilder builder = Json.createArrayBuilder();


        builder.add(poll.getQuestion());

        for (Answer answer : poll.getAnswers()){
            builder.add(answer.getAnswer());
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




    public void destroy() {
        System.out.println("des");
    }
}