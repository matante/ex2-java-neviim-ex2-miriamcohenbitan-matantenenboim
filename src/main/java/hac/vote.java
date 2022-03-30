package hac;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@WebServlet(name = "vote", urlPatterns = {"/api/vote"})
public class vote extends HttpServlet {

    private Poll poll = null;

    public void init() {
        poll = (Poll) getServletContext().getAttribute("poll");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (Answer answer : poll.getAnswers()) {
            builder.add(answer.getVotes());
        }

        JsonArray jsonArray = builder.build();
        try (OutputStream out = response.getOutputStream()) {
            JsonWriter jsonw = Json.createWriter(out);
            jsonw.write(jsonArray);
            jsonw.close();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");

        String answerFromUser = request.getParameter("answer");

        if (answerFromUser.length() == 0) { // user made no choice but pressed vote
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            boolean isFirstVote = true;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("voted")) {
                        isFirstVote = false;
                        break;
                    }
                }
            } else { // first time to vote, cookies array is null
                Cookie voteCookie = new Cookie("voted", "true");
                voteCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(voteCookie);
            }

            if (isFirstVote) {
                poll.voteTo(answerFromUser);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    public void destroy() {
    }
}