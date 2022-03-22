package hac;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


// a simple demo servlet to remove from your repo upon submission!
@WebServlet(name = "helloServlet", urlPatterns = {""})
public class HelloServlet extends HttpServlet {

    public void init() {

    }
@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("index.html").include(request, response);
    }

    public void destroy() {
    }
}