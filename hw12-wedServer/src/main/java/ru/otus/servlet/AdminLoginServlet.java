package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.otus.services.TemplateProcessor;

@RequiredArgsConstructor
public class AdminLoginServlet extends HttpServlet {
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";

    private final transient TemplateProcessor templateProcessor;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("pageTitle", "Аутентификация администратора");

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, paramsMap));
    }
}
