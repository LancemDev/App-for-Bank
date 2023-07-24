package com.example.demo3;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/secondservlet")
public class secondservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        PrintWriter  out = response.getWriter();
        response.setContentType("text/html");

        ServletContext context = getServletContext();
        Object obj = context.getAttribute("accno");
        String value = obj.toString();


        out.println("<html>");
        out.println("<body bgcolor=pink>");

        out.println("<center>");
        out.println("<h2>Ebank</h2>");
        out.println("<center>");

        out.println("<Form method=post action=servlet3>");
        out.println("<b> Click the Deposit button</b>");

        out.println("<table>");

        out.println("<tr>");
        out.println("<td>");
        out.println("Account Number : " + value);
        out.println("</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>");
        out.println("Deposit Amount : </td>  <td> <input type=text name=amount value=0>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<input type=submit value=deposit>");
        out.println("</br>");
        out.println("</Form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

}