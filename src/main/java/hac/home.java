package hac;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "home", urlPatterns = {""})
public class home extends HttpServlet {
    private final String PARAM_NAME = "POLLFILE";

    public void init() {

        String fileName = getServletContext().getInitParameter(PARAM_NAME);
        String filePath = getServletContext().getRealPath(fileName);

        // read line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            getServletContext().setAttribute("poll", new Poll(br.readLine()));
            Poll poll = (Poll) getServletContext().getAttribute("poll");

            String line;
            while ((line = br.readLine()) != null) {
                poll.addAnswer(line);
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