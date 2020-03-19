package uk.ac.ucl.servlets;

import uk.ac.ucl.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/analytics")
public class AnalyticsServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            // Use the model to do the search and put the results into the request object sent to the
            // Java Server Page used to display the results.
            Model model = ModelFactory.getModel();
            AnalyticsData aData = model.getAnalytics();
            request.setAttribute("analyticsData", aData);

            // Invoke the JSP page.
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/analytics.jsp");
            dispatch.forward(request, response);
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.toString());
            } catch (Exception f) {}
        }
    }
}