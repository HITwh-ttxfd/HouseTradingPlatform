package com.hit;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.jws.WebService;
import javax.jws.WebParam;
import java.util.List;

@WebService
@CrossOriginResourceSharing(
        allowAllOrigins = true,
        allowOrigins = { "*" },
        allowCredentials = true
)
public interface IReaderService{
    public Reader getReader(@WebParam(name="name") String name,@WebParam(name="password") String password);
    public List<Reader> getReaders();
    public String helloWorld();
}