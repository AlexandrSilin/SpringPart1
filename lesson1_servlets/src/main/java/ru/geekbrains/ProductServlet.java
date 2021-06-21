package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        if (req.getPathInfo() == null) {
            List<Product> products = productRepository.findAll();
            writer.println("<table border=\"1\">");
            writer.println("<tr>");
            writer.println("<td>");
            writer.println("ID");
            writer.println("</td>");
            writer.println("<td>");
            writer.println("Title");
            writer.println("</td>");
            writer.println("<td>");
            writer.println("Cost");
            writer.println("</td>");
            writer.println("</tr>");
            for (Product product : products) {
                writer.println("<tr>");
                writer.println("<td>");
                writer.println(product.getId());
                writer.println("</td>");
                writer.println("<td>");
                writer.println("<a href='" + req.getContextPath() + req.getServletPath() + "/" + product.getId() + "'>");
                writer.println(product.getTitle());
                writer.println("</a>");
                writer.println("</td>");
                writer.println("<td>");
                writer.println(product.getCost());
                writer.println("</td>");
                writer.println("</tr>");
            }
            writer.println("</table>");
        } else {
            String pathInfo = req.getPathInfo();
            long id;
            try {
                id = Long.parseLong(pathInfo.substring(pathInfo.lastIndexOf("/") + 1));
            } catch (NumberFormatException exception) {
                id = -1;
            }
            Product product = productRepository.findById(id);
            if (product == null) {
                writer.println("<span>");
                writer.println("No such product");
                writer.println("</span>");
            } else {
                writer.println("<div>");
                writer.println("<span>");
                writer.println("ID: ");
                writer.println(product.getId());
                writer.println("</span>");
                writer.println("</div>");
                writer.println("<div>");
                writer.println("<span>");
                writer.println("Title: ");
                writer.println(product.getTitle());
                writer.println("</span>");
                writer.println("</div>");
                writer.println("<div>");
                writer.println("<span>");
                writer.println("Cost: ");
                writer.println(product.getCost());
                writer.println("</span>");
                writer.println("</div>");
            }
        }
    }
}
