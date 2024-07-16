package com;

import com.sun.javaws.Main;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.*;

public class TestSecretClass {
    private static Logger logger = Logger.getLogger(TestSecretClass.class);

    public static void main(String args[]) {
        String skey = getSecrets();
        System.out.println("S key is  " + skey);
    }

    public static String getSecrets() {
        String secretKey = null;

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
                    System.out.println("jobs values are " + yamlValues);
                }
            }
            Iterator<Map.Entry<String, Object>> new_Iterator = yamlValues.entrySet().iterator();

            while (new_Iterator.hasNext()) {
                Map.Entry<String, Object> Java_new_Map = (Map.Entry<String, Object>) new_Iterator.next();
                System.out.println(" hash values " + Java_new_Map.getKey().toString() + " = " + Java_new_Map.getValue().toString());
                logger.info(" hash values " + Java_new_Map.getKey().toString() + " = " + Java_new_Map.getValue().toString());
                if (Java_new_Map.getKey().equals("build")) {
                    javaMap = (HashMap<String, Object>) Java_new_Map.getValue();
                    System.out.println("JavaMap is " + javaMap.toString());
                    logger.info("build is " + javaMap.toString());
                }
            }

            ArrayList stepArayList = null;
            LinkedHashMap finalMap=null;
            Iterator<Map.Entry<String, Object>> javaMapItr = javaMap.entrySet().iterator();
            ArrayList<Object> arrayStepList = null;
            while (javaMapItr.hasNext()) {
                Map.Entry<String, Object> new_Map = (Map.Entry<String, Object>) javaMapItr.next();
                System.out.println("new_Map values are " + new_Map);

                if (new_Map.getKey().toString().equals("steps")) {
                    System.out.println("step values class type" + new_Map.getValue().getClass());
                    stepArayList = (ArrayList) new_Map.getValue();
                    System.out.println("array List of steps are " + stepArayList);
                }
            }
            System.out.println("stepArayList.size() is " +stepArayList.size());

            for (int i = 0; i < stepArayList.size(); i++){


                if(stepArayList.get(i).toString().contains("EMAIL_SECRET_PASSPHRASE")){
                    System.out.println("EMAIL_SECRET_PASSPHRASE values is " +stepArayList.get(i));
                    System.out.println(" class is "+stepArayList.get(i).getClass());
                    finalMap= (LinkedHashMap) stepArayList.get(i);
                }
            }



            LinkedHashMap withMap=null;
            Iterator<Map.Entry<String, Object>> finalMap_Iterator = finalMap.entrySet().iterator();

            while (finalMap_Iterator.hasNext()) {
                Map.Entry<String, Object> JfinalMap_Iterator_new_Map = (Map.Entry<String, Object>) finalMap_Iterator.next();
                System.out.println(" JfinalMap_Iterator_new_Map  " + JfinalMap_Iterator_new_Map.getKey().toString() + " = " + JfinalMap_Iterator_new_Map.getValue().toString());
                logger.info(" JfinalMap_Iterator_new_Map " + JfinalMap_Iterator_new_Map.getKey().toString() + " = " + JfinalMap_Iterator_new_Map.getValue().toString());
                if (JfinalMap_Iterator_new_Map.getKey().equals("with")) {

                   System.out.println("with Map  values class type "+ JfinalMap_Iterator_new_Map.getValue().getClass());
                    withMap = (LinkedHashMap) JfinalMap_Iterator_new_Map.getValue();
                    System.out.println("withMap is " + withMap.toString());
                  //  logger.info("build is " + javaMap.toString());
                }
            }




            String secretValue =null;
            Iterator<Map.Entry<String, Object>> withMap_Iterator = withMap.entrySet().iterator();

            while (withMap_Iterator.hasNext()) {
                Map.Entry<String, Object> withMap_new_Map = (Map.Entry<String, Object>) withMap_Iterator.next();
                System.out.println(" JfinalMap_Iterator_new_Map  " + withMap_new_Map.getKey().toString() + " = " + withMap_new_Map.getValue().toString());
                if (withMap_new_Map.getKey().equals("EMAIL_SECRET_PASSPHRASE")) {
                    System.out.println("with Map  values class type "+ withMap_new_Map.getValue().getClass());
                    secretValue = (String) withMap_new_Map.getValue();
                    System.out.println("secretValue is " + secretValue.toString());
                    //  logger.info("build is " + javaMap.toString());
                }
            }

            FileWriter myWriter = new FileWriter("secretFileWriteTest.txt" ,true);
            myWriter.write("secreet value is "+secretValue.toString());
            myWriter.close();


            System.out.println("setting to the property is done");
              logger.info("setting to the property is done");


        } catch (Exception e) {
            //   inputStream.
            e.printStackTrace();
        }
        return secretKey;
    }
}
