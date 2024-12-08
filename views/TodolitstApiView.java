package views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dto.ApiResponse;
import entities.Todolist;
import services.TodolistService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class TodolitstApiView {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String host;
    private final Integer port;
    private final TodolistService todolistService;
    private HttpServer server;

    public TodolitstApiView(final String host,final Integer port,final TodolistService todolistService) {
        this.host = host;
        this.port = port;
        this.todolistService = todolistService;
    }
    public void init() {
        try {
            server = HttpServer.create(new InetSocketAddress(host, port),0);
            server.createContext("/todo", (exchange -> {
            setJsonContentType(exchange);
                ApiResponse apiResponse;
                switch (exchange.getRequestMethod()){
                    case "GET":
                        apiResponse = getTodoList();
                        break;
                    case "POST":
                        apiResponse = addTodoList(exchange);
                        break;
                    case "DELETE":
                        apiResponse = deleteTodoList(exchange);
                        break;
                    default:
                        String responseText = "Cannot process request";
                        Integer responseCode = 400;
                        apiResponse = new ApiResponse(responseText, responseCode);
                }
                sendResponse(exchange, apiResponse);
                exchange.close();
                 }));

        } catch (Exception e) {
                System.out.println(e);
            }
            startServer();
    }

    private ApiResponse deleteTodoList(final HttpExchange exchange) {
        String pathParameter = getTodoListId(exchange);
        ApiResponse apiResponse = new ApiResponse();

        var id = Integer.valueOf(pathParameter);
        Boolean isSuccess = todolistService.removeTodolist(id, true);
        String response;
        int responseCode;
        if(Boolean.TRUE.equals(isSuccess)){
            response = String.format("Delete todo list with id %s", id);
            responseCode = 200;
        }else {
            response = String.format("Failed to delete todo list with id %s", id);
            responseCode = 400;
        }
        apiResponse.setResponseCode(responseCode);
        apiResponse.setResponse(response);
        return apiResponse;
    }

    private ApiResponse addTodoList(final HttpExchange exchange) throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        InputStream inputStream = exchange.getRequestBody();

        Todolist todolist = getRequestBody(inputStream, Todolist.class);
        todolistService.addTodolist(todolist);

        String response = "Successful to add todo";
        apiResponse.setResponseCode(200);
        apiResponse.setResponse(response);
        return apiResponse;
    }


    private ApiResponse getTodoList() throws JsonProcessingException {
            ApiResponse apiResponse = new ApiResponse();
            Todolist[] todolists = todolistService.getTodolist();
            String response = objectMapper.writeValueAsString(todolists);

            apiResponse.setResponse(response);
            apiResponse.setResponseCode(200);
            return apiResponse;
        }

    private static void sendResponse(final HttpExchange exchange, final ApiResponse apiResponse) throws IOException {
        exchange.sendResponseHeaders(apiResponse.getResponseCode(), apiResponse.getResponse().getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(apiResponse.getResponse().getBytes());
        output.flush();
    }

    private void startServer() {
        server.setExecutor(null);
        server.start();
        System.out.println(String.format("Server running on port : %s", port));
    }

    private <T> T getRequestBody(final InputStream inputStream, Class<T> type) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        String jsonBody = requestBody.toString();
        return objectMapper.readValue(jsonBody, type);
    }

    private static String getTodoListId(final HttpExchange exchange) {
        URI requestURI = exchange.getRequestURI();
        String path = requestURI.getPath();
        String[] segments = path.split("/");
        String pathParameter = "";
        if (segments.length > 2) {
            pathParameter = segments[2]; // Assuming the parameter is the third segment
        }
        return pathParameter;
    }

    private static void setJsonContentType(final HttpExchange exchange) {
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
    }
}
