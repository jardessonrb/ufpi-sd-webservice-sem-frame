package sd.ufpi.core.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import sd.ufpi.core.forms.MensagemForm;

public class HttpParse {
    
    public Request parse(HttpExchange requisicao) throws IOException{
        Request request = new Request();
        request.setBody(parseBody(requisicao));
        request.setMethod(requisicao.getRequestMethod());
        request.setPaths(getPaths(requisicao.getRequestURI().toString()));
        request.setQueryParams(request.getPaths());

        return request;
    }

    public List<String> getPaths(String path){
        String paths[] = path.split("/");
        List<String> pathsList = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            if(!paths[i].equals("")){
                pathsList.add(paths[i]);
            }
        }

        return pathsList;
    }

    public Object parseBody(HttpExchange requisicao) throws IOException{
        Charset charset = StandardCharsets.UTF_8;
        InputStream input = requisicao.getRequestBody();
        StringBuilder builder = new StringBuilder();
        int content;
        while ((content = input.read()) != -1) {
            builder.append((char)content);
        }

        String request = new String(builder.toString().getBytes(), charset);
        Gson gson = new Gson();
        MensagemForm body = gson.fromJson(request, MensagemForm.class);
        return body;
    }
}
