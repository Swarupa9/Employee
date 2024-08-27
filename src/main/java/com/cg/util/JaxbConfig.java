package com.cg.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBContextFactory;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxbConfig {

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(com.cg.BO.EmpBO.class, com.cg.EO.EmpEO.class);
    }

    @Bean
    public Unmarshaller unmarshaller(JAXBContext jaxbContext) throws JAXBException {
        return jaxbContext.createUnmarshaller();
    }

    @Bean
    public Marshaller marshaller(JAXBContext jaxbContext) throws JAXBException {
    	Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // Enable formatting
        return marshaller;
    }
}

