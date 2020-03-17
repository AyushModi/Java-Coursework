package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Patient;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
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

        } catch (FileNotFoundException e) {
            System.out.println("File not found\n" + e.toString());
            response.setStatus(500);
        } catch (Exception e) {
            System.out.println("Problem while loading file1");
            response.setStatus(500);
        }

    }
}
