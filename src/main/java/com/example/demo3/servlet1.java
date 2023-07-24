package com.example.demo3;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet1")
public class servlet1 extends HttpServlet {
    PrintWriter out = null;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    private interface ResponseDecorator {
        String decorate(String content);
    }

    private static class TimestampDecorator implements ResponseDecorator {
        @Override
        public String decorate(String content) {
            return content + " (" + new Date() + ")";
        }
    }

    private ResponseDecorator responseDecorator;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the decorator here, you can change it based on your requirements.
        responseDecorator = new TimestampDecorator();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String result;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bar", "root", "");
            ServletContext context = getServletContext();
            context.setAttribute("accno", "");
            String accno = request.getParameter("accno");
            String pinno = request.getParameter("pinno");
            pst = con.prepareStatement("select * from login where accno = ? and pinno = ?");
            pst.setString(1, accno);
            pst.setString(2, pinno);
            rs = pst.executeQuery();
            boolean row = false;
            row = rs.next();

            if (row == true) {
                result = rs.getString(2);
                context.setAttribute("accno", result);

                // Get the PrintWriter after all the database operations
                PrintWriter out = response.getWriter();
                response.setContentType("text/plain");
                String message = "Hello, World!";
                String decoratedMessage = responseDecorator.decorate(message);
                out.write(decoratedMessage);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/secondservlet");
                if (dispatcher != null) {
                    dispatcher.forward(request, response);
                } else {
                    // Handle dispatcher not found scenario if necessary
                }
                con.close();
            } else {
                out = response.getWriter();
                response.setContentType("text/html");
                out.println("<html>");
                out.println("<body bgcolor=pink>");
                out.println("Please check the Accno and Balance");
                out.println("</body");
                out.println("</html");
                out.close();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
