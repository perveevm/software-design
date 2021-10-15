package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HTMLBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private static final HTMLBuilder htmlBuilder = new HTMLBuilder();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                    htmlBuilder.setHeader("Product with max price: ", 1);

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        htmlBuilder.addBodyLine(String.format("%s\t%d", name, price));
                    }
                    response.getWriter().println(htmlBuilder.getHtmlAndClear());

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                htmlBuilder.clear();
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                    htmlBuilder.setHeader("Product with min price: ", 1);

                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        htmlBuilder.addBodyLine(String.format("%s\t%d", name, price));
                    }
                    response.getWriter().println(htmlBuilder.getHtmlAndClear());

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                htmlBuilder.clear();
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                    htmlBuilder.setHeader("Summary price: ", 0);

                    if (rs.next()) {
                        htmlBuilder.addBodyLine(String.valueOf(rs.getInt(1)));
                    }
                    response.getWriter().println(htmlBuilder.getHtmlAndClear());

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                htmlBuilder.clear();
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                    htmlBuilder.setHeader("Number of products: ", 0);

                    if (rs.next()) {
                        htmlBuilder.addBodyLine(String.valueOf(rs.getInt(1)));
                    }
                    response.getWriter().println(htmlBuilder.getHtmlAndClear());

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                htmlBuilder.clear();
                throw new RuntimeException(e);
            }
        } else {
            htmlBuilder.setHeader("Unknown command: " + command, 0);
            response.getWriter().println(htmlBuilder.getHtmlAndClear());
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
