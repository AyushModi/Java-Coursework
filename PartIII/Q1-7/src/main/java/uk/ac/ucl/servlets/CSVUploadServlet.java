package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/upload")
public class CSVUploadServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            ModelFactory.readStream(request.getInputStream());
            response.sendRedirect("/index.html");
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.toString());
            } catch (Exception f) {}
        }
    }
}
