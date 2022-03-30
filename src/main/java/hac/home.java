package hac;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "home", urlPatterns = {""})
public class home extends HttpServlet {

    public void init() {
        final String PARAM_NAME = "POLLFILE";
        String fileName = getServletContext().getInitParameter(PARAM_NAME);
        String filePath = getServletContext().getRealPath(fileName);
        //open file

        // read line by line

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals(""))
                    lines.add(line);
            }

            final int QUESTION_PLACE = 0;
            if (lines.size() > 0) {
                getServletContext().setAttribute("poll", new Poll(lines.get(QUESTION_PLACE)));
                Poll poll = (Poll) getServletContext().getAttribute("poll");

                for (int i = 1; i < lines.size(); i++) {
                    poll.addAnswer(lines.get(i));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("index.html").include(request, response);
    }

    public void destroy() {
    }
}