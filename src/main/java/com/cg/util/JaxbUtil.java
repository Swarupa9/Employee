package com.cg.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class JaxbUtil {

    public static <T> String toJson(T object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object result = unmarshaller.unmarshal(new StringReader(json));
        return clazz.cast(result);
        //return (T) unmarshaller.unmarshal(new StringReader(json));
    }
}
//@Configuration
//public class JaxbConfig {
//
//    @Bean
//    public JAXBContext jaxbContext() throws JAXBException {
//        return JAXBContext.newInstance(com.cg.BO.EmpBO.class, com.cg.EO.EmpEO.class);
//    }
//
//    @Bean
//    public Unmarshaller unmarshaller(JAXBContext jaxbContext) throws JAXBException {
//        return jaxbContext.createUnmarshaller();
//    }
//
//    @Bean
//    public Marshaller marshaller(JAXBContext jaxbContext) throws JAXBException {
//    	Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // Enable formatting
//        return marshaller;
//    }
//}

