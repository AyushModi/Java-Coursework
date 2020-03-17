package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Patient;
import uk.ac.ucl.model.SearchRequestHolder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The servlet invoked to perform a search.
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/runsearch.html")
public class SearchServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    try {
      // Use the model to do the search and put the results into the request object sent to the
      // Java Server Page used to display the results.
      Model model = ModelFactory.getModel();
      List<String> columnNames = model.getColumns();
      SearchRequestHolder srh = new SearchRequestHolder();
      for (var colName : columnNames) {
        if (request.getParameter(colName) == null) continue;
        else if (request.getParameter(colName).equals("(empty)")) {
          srh.addItem(colName,"",true);
        } else if (!request.getParameter(colName).equals("")) {
          String matchWholeName = "matchWhole"+colName;
          srh.addItem(colName, request.getParameter(colName),
                  request.getParameter(matchWholeName) != null && request.getParameter(matchWholeName).equals("on"));

        }
      }
      List<Patient> searchResult = model.searchFor(srh);
      request.setAttribute("patientNames", searchResult);

      // Invoke the JSP page.
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/patientList.jsp");
      dispatch.forward(request, response);
    } catch (Exception e) {
      System.out.println("Problem loading model");
      response.setStatus(500);
    }
  }
}