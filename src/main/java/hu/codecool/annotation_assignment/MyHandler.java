package hu.codecool.annotation_assignment;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyHandler implements HttpHandler {

    private Object routes;

    public MyHandler() {
        try {
            Constructor<?> constructor = Routes.class.getConstructor();
            this.routes = constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response;
        HttpRequestMethod method;
        String path = httpExchange.getRequestURI().getPath();
        if (httpExchange.getRequestMethod().equals("GET")) {
            method = HttpRequestMethod.GET;
        } else {
            method = HttpRequestMethod.POST;
        }

        for(Method m: Routes.class.getMethods()) {
            if(m.isAnnotationPresent(WebRoute.class)) {
                WebRoute webRoute = m.getAnnotation(WebRoute.class);

                if (webRoute.path().equals(path) && webRoute.method().equals(method)) {
                    try {
                        response = m.invoke(routes).toString();
                        sendResponse(httpExchange, response);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
