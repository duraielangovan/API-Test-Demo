package com.veyyon.at.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;


public class SerializationUtil {
    protected static Logger logger = LoggerFactory.getLogger(SerializationUtil.class);

    @SuppressWarnings("unchecked")
    public static <T> T deserializeByteArray(byte[] byteArray, T clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getClass());
        Unmarshaller unmarshaller  = jaxbContext.createUnmarshaller();
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);

        if(Objects.equals(bis,null) || bis.available()==0){
            return null;
        }else {
            return (T) unmarshaller.unmarshal(bis);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializeString(String inData, T clazz) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getClass());
        Unmarshaller unmarshaller  = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(inData);
        int readValue = reader.read();
        reader.reset();
        if(Objects.equals(reader,null) || readValue==-1){
            return null;
        }else {
            return (T) unmarshaller.unmarshal(reader);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializeStringObjectMapper(String inData, T clazz) throws JAXBException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            clazz = (T) mapper.readValue(inData,clazz.getClass());
        }catch (Exception e){
            e.printStackTrace();
        }
        return clazz;
    }
    public <T> String serializeObject(T inObject){
        String json ="";
        ObjectMapper mapper = new ObjectMapper();

        try{
            json = mapper.writeValueAsString(inObject);
        }catch(Exception e){
            e.printStackTrace();
        }
        return json;
    }
    public <T> String serializeObjectWithCustomCharacterEscape(T inObject){
        String json ="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().setCharacterEscapes(new CustomCharacterEscapes());
        try{
            json = mapper.writeValueAsString(inObject);
        }catch(Exception e){
            e.printStackTrace();
        }
        return json;
    }

}
