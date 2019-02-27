package hu.codecool.annotation_assignment;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyHandler implements HttpHandler {

    private Routes routes;

    public MyHandler(Routes routes) {
        this.routes = routes;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response;
        String path = httpExchange.getRequestURI().getPath();

        for(Method m: Routes.class.getMethods()) {
            if(m.isAnnotationPresent(WebRoute.class)) {
                WebRoute webRoute = m.getAnnotation(WebRoute.class);

                if (webRoute.path().equals(path)) {
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
