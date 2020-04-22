package com.hit;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class ClientRun {
    public static void main(String[] args) {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient("http://localhost:8080/service/reader?wsdl");
        Object[] objects;
        try{
            objects = client.invoke("getReaders");
            System.out.println(objects[0].toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}