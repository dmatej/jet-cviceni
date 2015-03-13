package cz.zcu.jet.ws.soap;

import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService(
    endpointInterface = "cz.zcu.jet.ws.soap.Hello",
    serviceName = "HelloService",
    portName = "HelloServicePortType")
@Stateless
public class HelloImpl {
  public String sayHello(final String name) {
    return "Hello, " + name + ".";
  }
}
