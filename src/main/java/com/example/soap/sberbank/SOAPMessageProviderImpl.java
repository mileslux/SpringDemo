package com.example.soap.sberbank;

import javax.xml.soap.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by mileslux on 11/7/2015.
 */
public class SOAPMessageProviderImpl implements SOAPMessageProvider {
    @Override
    public SOAPMessage createGetCursOnDateXML(LocalDate date) throws SOAPException {
        SOAPMessage soapMessage = createBaseMessage();
        SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();

        SOAPElement soapBodyElem0 = body.addChildElement("GetCursOnDateXML", "", "http://web.cbr.ru/");
        SOAPElement soapBodyElem1 = soapBodyElem0.addChildElement("On_date");
        soapBodyElem1.addTextNode(date.format(DateTimeFormatter.ISO_DATE));

        return soapMessage;
    }

    @Override
    public SOAPMessage createEnumValutesXML(boolean daily) throws SOAPException {
        SOAPMessage soapMessage = createBaseMessage();
        SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();

        SOAPElement soapBodyElem0 = body.addChildElement("EnumValutesXML", "", "http://web.cbr.ru/");
        SOAPElement soapBodyElem1 = soapBodyElem0.addChildElement("Seld");
        soapBodyElem1.addTextNode(Boolean.toString(!daily));

        return soapMessage;
    }

    protected SOAPMessage createBaseMessage() throws SOAPException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMessage = factory.createMessage();
        SOAPPart part = soapMessage.getSOAPPart();

        SOAPEnvelope envelope = part.getEnvelope();
        envelope.removeNamespaceDeclaration("SOAP-ENV");
        envelope.getHeader().detachNode();
        envelope.setPrefix("soap");
        envelope.addNamespaceDeclaration("soap", "http://schemas.xmlsoap.org/soap/envelope");
        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");

        SOAPBody body = envelope.getBody();
        body.setPrefix("soap");

        return soapMessage;
    }
}
