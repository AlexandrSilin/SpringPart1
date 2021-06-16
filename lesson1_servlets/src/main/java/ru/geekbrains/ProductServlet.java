package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private ProductRepository repository;

    @Override
    public void init() throws ServletException {
        repository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = repository.findAll();
        resp.getWriter().println("<table border=\"1\">");
        resp.getWriter().println("<tr>");
        resp.getWriter().println("<td>");
        resp.getWriter().println("ID");
        resp.getWriter().println("</td>");
        resp.getWriter().println("<td>");
        resp.getWriter().println("Title");
        resp.getWriter().println("</td>");
        resp.getWriter().println("<td>");
        resp.getWriter().println("Cost");
        resp.getWriter().println("</td>");
        resp.getWriter().println("</tr>");
        for (Product product : products) {
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(product.getId());
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(product.getTitle());
            resp.getWriter().println("</td>");
            resp.getWriter().println("<td>");
            resp.getWriter().println(product.getCost());
            resp.getWriter().println("</td>");
            resp.getWriter().println("</tr>");
        }
        resp.getWriter().println("</table>");
    }
}
