package com.hit;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface="com.hit.IReaderService",serviceName="1")
@CrossOriginResourceSharing(
        allowAllOrigins = true,
        allowOrigins = { "*" },
        allowCredentials = true
)
public class ReaderService implements IReaderService{
    public Reader getReader(@WebParam(name="name") String name,@WebParam(name="password") String password){
        return new Reader(name,password);
    }
    public List<Reader> getReaders(){
        List<Reader> readerList=new ArrayList<Reader>();
        readerList.add(new Reader("张三","123"));
        readerList.add(new Reader("李四","456"));
        return readerList;
    }

    @Override
    @POST
    public String helloWorld() {
        return "HelloWorld!";
    }
}
