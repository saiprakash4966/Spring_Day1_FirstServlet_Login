import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = {"/LoginServlet"},

        // Setting a couple of initial parameter for the servlet
        initParams = { @WebInitParam(name = "user", value = "Sai"),
                @WebInitParam(name = "password", value = "Prakash@123") })

public class LoginServlet extends HttpServlet {
    // Define the post method for when api calls the endpoint
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // The user and password are taken from the request
        String userInput = request.getParameter("user");
        String pwdInput = request.getParameter("pwd");

        // The user and password are retrieved from the initial params of the servlet
        String user = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");

        Pattern pattern = Pattern.compile("^([A-Z][a-zA-Z]{2,}[ ]?)+$");
        Matcher matcher = pattern.matcher(userInput);
        if(!matcher.matches()) {
            PrintWriter out = response.getWriter();
            out.println("<font color = red> Incorrect UserId. Please try again!!<font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            rd.include(request, response);
            return;
        }

        if (user.equals(userInput) && password.equals(pwdInput)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/LoginSuccess.jsp").forward(request, response);
        }

        else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color = red> Incorrect UserId or Password. Please try again!!<font>");
            rd.include(request, response);
        }
    }
}