package cz.zcu.jet.ws.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "HelloPortType")
public interface Hello {

  @WebMethod
  @WebResult(name = "answer")
  String sayHello(@WebParam(mode = Mode.IN, name = "name") final String name);
}