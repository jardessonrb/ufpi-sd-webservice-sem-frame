package sd.ufpi.core.database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private Connection connection;

    public Connection getConnection() throws SQLException, IOException {
        Properties props = getProperties();

        String url = "jdbc:postgresql://localhost:"+props.getProperty("port")+"/"+props.getProperty("name");

        this.connection = DriverManager.getConnection(url, props);

        return this.connection;
    }

    private Properties getProperties() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("./apisemframe.properties", StandardCharsets.UTF_8));
        Properties properties = new Properties();
        String linha;
        while( (linha = reader.readLine()) != null){
            String propertie[] = linha.split("=");
            String valores[] = propertie[0].split("\\.");
            if(valores[0].equals("database")){
                properties.put(valores[1], propertie[1]);
            }
            
        }

        reader.close();
        return properties;
    }
}
