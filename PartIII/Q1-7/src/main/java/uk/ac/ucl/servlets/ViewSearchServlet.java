package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class ViewSearchServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        try {
            Model model = ModelFactory.getModel();
            request.setAttribute("columnNames", model.getColumns());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/search.jsp");
            dispatch.forward(request, response);

        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.toString());
            } catch (Exception f) {}
        }

    }
}
