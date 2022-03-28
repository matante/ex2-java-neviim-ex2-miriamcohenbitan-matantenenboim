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
@WebServlet(name = "vote", urlPatterns = {"/api/vote"})
public class vote extends HttpServlet {

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

        for (Answer answer : poll.getAnswers()){
            builder.add(answer.getVotes());
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

        String answerFromUser = request.getParameter("answer");

        if (answerFromUser.length() > 0 ){
            poll.voteTo(request.getParameter("answer"));
            response.setStatus(200); // todo : cookies
        }else{
            response.setStatus(400);
        }

        //forward to get

    }


    public void destroy() {
        System.out.println("des");
    }
}