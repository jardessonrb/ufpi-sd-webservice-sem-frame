package sd.ufpi.core.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import sd.ufpi.core.forms.MensagemForm;
import sd.ufpi.core.rest.types.Request;

public class HttpParse {
    
    public Request parse(HttpExchange requisicao) throws IOException{
        List<String> paths = getPaths(requisicao.getRequestURI().toString());
        Map<String, String> queryParams = getQueryParams(requisicao.getRequestURI().toString());
        
        Request request = new Request();
        request.setBody(parseBody(requisicao));
        request.setMethod(requisicao.getRequestMethod());
        request.setQueryParams(queryParams);
        request.setPaths(paths);
        return request;
    }

    public List<String> getPaths(String path){
        String paths[] = path.split("/");
        List<String> pathsList = new ArrayList<>();
        for (int i = 0; i < paths.length; i++) {
            if(!paths[i].equals("")){
                if(isContainsQueryParams(paths[i])){
                    String pathNotQuery = paths[i].split("\\?")[0];
                    pathsList.add(pathNotQuery);
                }else {
                    pathsList.add(paths[i]);
                }
            }
        }

        return pathsList;
    }

    public boolean isContainsQueryParams(String path){
        for (int i = 0; i < path.length(); i++) {
            if(path.charAt(i) == '?'){
                return true;
            }
        }

        return false;
    }

    public Map<String, String> getQueryParams(String path){
        String pathAndQuery[] = path.split("\\?");
        String query[];
        Map<String, String> queryParams = new HashMap<>();
        if(pathAndQuery.length > 1){
            query = pathAndQuery[1].split("&");
            for (int i = 0; i < query.length; i++) {
                String queryAndValue[] = query[i].split("=");
                if(queryAndValue.length > 1){
                    queryParams.put(queryAndValue[0], queryAndValue[1]);
                }else if(queryAndValue.length == 1){
                    queryParams.put(queryAndValue[0], null);
                }
            }
        }

        return queryParams;
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
        // Gson gson = new Gson();
        // MensagemForm body = gson.fromJson(request, MensagemForm.class);
        return request;
    }

    public List<PathParams> tranformUriInPathParams(String uri){
        List<String> paths = getPaths(uri);
        List<PathParams> pathParams = new ArrayList<>();

        paths.forEach(path -> {
            pathParams.add(new PathParams(containsPathParam(path), path));
        });

        return pathParams;
    }

    public boolean containsPathParam(String path){
        if(path.charAt(0) == '{' && path.charAt(path.length() - 1) == '}'){
            return true;
        }else if((path.charAt(0) == '{' && path.charAt(path.length() - 1) != '}') || (path.charAt(0) != '{' && path.charAt(path.length() - 1) == '}')){
            throw new Error("Erro ao montar os paths params: ["+path+"]");
        }else if(path.charAt(0) != '{' && path.charAt(path.length() - 1) != '}'){
            return false;
        }

        return false;
    }


    public static void main(String[] args) {
        HttpParse parse = new HttpParse();
        UUID uuid = UUID.randomUUID();

        String url = "casa/"+uuid.toString()+"/cozinha/sala/teste?page=0&limit=10&";
        List<String> paths = parse.getPaths(url);
        Map<String, String> queryParams= parse.getQueryParams(url);
        String uri = "/teste/{ispathparam}/algumacoisa";

        List<PathParams> listPathParams = parse.tranformUriInPathParams(uri);

        listPathParams.forEach(path -> {
            System.out.println("Valor: "+path.getValue()+" is path param "+path.isParams());
        });

        paths.forEach(path -> {
            System.out.println("Path: "+path);
        });

        queryParams.entrySet().forEach(entry -> {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        });
    }
}
