package cn.garden.message.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author liwei
 */
public class XmlUtil {

    public static <T> T toObject(String xmlString, Class<T> className) {
        try {
            JAXBContext context = JAXBContext.newInstance(className);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xmlString));

        } catch (JAXBException e) {
            throw ExceptionUtil.createDefaultException("xml转对象失败", e);
        }
    }

    public static String toXml(Object object) {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            throw ExceptionUtil.createDefaultException("对象转xml失败", e);
        }
    }
}
