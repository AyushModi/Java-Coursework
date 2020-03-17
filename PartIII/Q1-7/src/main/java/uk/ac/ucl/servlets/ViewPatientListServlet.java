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

// The servlet invoked to display a list of patients.
// The url http://localhost:8080/patientList.html is mapped to calling doGet on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/patientList.html")
public class ViewPatientListServlet extends HttpServlet
{

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
    List<Patient> patientNames;
    try {
      // Get the data from the model
      Model model = ModelFactory.getModel();
      patientNames = model.getPatientsSummary();
      // Then add the data to the request object that will be sent to the Java Server Page, so that
      // the JSP can access the data (a Java data structure).
      request.setAttribute("patientNames", patientNames);

      // Invoke the JSP.
      // A JSP page is actually converted into a Java class, so behind the scenes everything is Java.
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
      dispatch.forward(request, response);
    } catch (FileNotFoundException e) {
      System.out.println("File not found\n" + e.toString());
      response.setStatus(500);
    } catch (Exception e) {
      System.out.println("Problem while loading file");
      response.setStatus(500);
    }
  }
}
