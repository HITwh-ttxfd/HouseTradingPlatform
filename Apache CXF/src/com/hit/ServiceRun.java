package com.hit;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class ServiceRun {
    public static void main(String[] args){
        JaxWsServerFactoryBean factory=new JaxWsServerFactoryBean();
        factory.setServiceClass(ReaderService.class);
        factory.setAddress("http://localhost:8090/service/reader");
        Server server = factory.create();
        server.start();
    }
}
