package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Patient;

import javax.naming.NameNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

// The servlet invoked to display a list of patients.
// The url http://localhost:8080/patientList.html is mapped to calling doGet on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/patient/*")
public class ViewPatientServlet extends HttpServlet
{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String pathInfo = request.getPathInfo();
        try {
            if (pathInfo != null && pathInfo.length() > 0) {
                Model model = ModelFactory.getModel();
                Optional<Patient> pat = model.getPatientByID(pathInfo.substring(1));
                if (pat.isPresent()) {
                    request.setAttribute("patient", pat.get());
                    ServletContext context = getServletContext();
                    RequestDispatcher dispatch = context.getRequestDispatcher("/patient.jsp");
                    dispatch.forward(request, response);
                } else {
                    response.sendRedirect("/patientList.html");
                }
            } else {
                response.sendRedirect("/patientList.html");
            }

        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.toString());
            } catch (Exception f) {}
        }

    }
}
