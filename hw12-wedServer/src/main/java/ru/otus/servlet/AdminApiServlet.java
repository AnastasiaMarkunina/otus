package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import ru.otus.crm.service.DBServiceClient;

@RequiredArgsConstructor
public class AdminApiServlet extends HttpServlet {
    private final transient DBServiceClient dbServiceClient;
    private final transient Gson gson;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalClients", dbServiceClient.findAll().size());
        stats.put("serverTime", System.currentTimeMillis());
        stats.put("status", "active");

        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(stats));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Административные операции
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Административная операция выполнена");
        result.put("success", true);

        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(result));
    }
}
