package hac;

import java.io.*;
import java.util.ArrayList;
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
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals(""))
                    lines.add(line);
            }

            getServletContext().setAttribute("poll", new Poll(lines.get(0)));
            Poll poll = (Poll) getServletContext().getAttribute("poll");

            for (int i = 1; i < lines.size(); i++) {
                poll.addAnswer(lines.get(i));
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