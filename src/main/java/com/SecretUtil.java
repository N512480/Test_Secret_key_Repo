package com;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class SecretUtil {

    private static Logger logger = Logger.getLogger(SecretUtil.class);

    public static void main() {

        String skey=   getSecrets();
        System.out.println("S key is  "+ skey);
    }

    public static String getSecrets() {
        String secretKey=null;

        Yaml yaml = new Yaml();
        Properties propsConfig = new Properties();
        try {
            InputStream inputStream = new FileInputStream(".github/workflows/ci-cd-java-maven.yml");
            HashMap hsmp = yaml.load(inputStream);
            HashMap<String, Object> javaMap = null;
            HashMap yamlValues = null;
            for (Object o : hsmp.entrySet()) {

                if (o.toString().contains("jobs")) {
                    System.out.println("yaml values are " + o.toString());
                    logger.info("yaml values are " + o.toString());
                    yamlValues = (HashMap) hsmp.get("jobs");
                }
            }
            Iterator<Map.Entry<String, Object>> new_Iterator = yamlValues.entrySet().iterator();

            while (new_Iterator.hasNext()) {
                Map.Entry<String, Object> Java_new_Map = (Map.Entry<String, Object>) new_Iterator.next();
                System.out.println(" hash values " + Java_new_Map.getKey().toString() + " = " + Java_new_Map.getValue().toString());
                logger.info(" hash values " + Java_new_Map.getKey().toString() + " = " + Java_new_Map.getValue().toString());
                if (Java_new_Map.getKey().equals("JAVA")) {
                    javaMap = (HashMap<String, Object>) Java_new_Map.getValue();
                    System.out.println("JavaMap is " + javaMap.toString());
                    logger.info("JavaMap is " + javaMap.toString());
                }
            }
            Iterator<Map.Entry<String, Object>> javaMapItr = javaMap.entrySet().iterator();
            Object secretMap = null;
            while (javaMapItr.hasNext()) {
                Map.Entry<String, Object> new_Map = (Map.Entry<String, Object>) javaMapItr.next();
                if (new_Map.getKey().toString().equals("secrets"))
                    System.out.println(" Java secrets values " + new_Map.getValue().toString());
                logger.info(" Java secrets values " + new_Map.getValue().toString());
                secretMap = new_Map.getValue();
            }
            System.out.println("Secret class is " + secretMap.getClass());
            logger.info("Secret class is " + secretMap.getClass());
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap = (LinkedHashMap<String, Object>) secretMap;
            System.out.println("linkedHashMap class is " + linkedHashMap.getClass());
            logger.info("linkedHashMap class is " + linkedHashMap.getClass());
            Object ob = linkedHashMap.get("EMAIL_SECRET_PASSPHRASE");
            System.out.println("final secret value is " + ob.toString());
            logger.info("final secret value is " + ob.toString());
            secretKey=ob.toString();
          //  InputStream iberiaHandProp = Main.class.getClassLoader().getResourceAsStream("iberia-handling.properties");
          //  propsConfig.load(iberiaHandProp);
          //  logger.info("setting password to the property object ");
          //  propsConfig.setProperty("iberia_handling_email_password", ob.toString());
          //  System.out.println("setting to the property is done");
          //  logger.info("setting to the property is done");
          //  inputStream.close();
        } catch (Exception e) {
            //   inputStream.
            e.printStackTrace();
        }
        return secretKey;
    }
}
