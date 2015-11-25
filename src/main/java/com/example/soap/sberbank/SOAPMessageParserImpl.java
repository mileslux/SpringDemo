package com.example.soap.sberbank;

import com.example.soap.sberbank.response.EnumValutesFactory;
import com.example.soap.sberbank.response.EnumValutesXMLResponse;
import com.example.soap.sberbank.response.GetCursOnDateFactory;
import com.example.soap.sberbank.response.GetCursOnDateXMLResponse;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPMessage;

/**
 * Created by mileslux on 11/8/2015.
 */
public class SOAPMessageParserImpl implements SOAPMessageParser {
    @Override
    public EnumValutesXMLResponse parseEnumValutesXMLResponse(SOAPMessage soapMessage) throws Exception {
        Node root = soapMessage.getSOAPBody().extractContentAsDocument();
        JAXBContext jaxbContext = JAXBContext.newInstance(EnumValutesFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (EnumValutesXMLResponse) unmarshaller.unmarshal(root);
    }

    @Override
    public GetCursOnDateXMLResponse parseGetCursOnDateXMLResponse(SOAPMessage soapMessage) throws Exception {
        Node root = soapMessage.getSOAPBody().extractContentAsDocument();
        JAXBContext jaxbContext = JAXBContext.newInstance(GetCursOnDateFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        GetCursOnDateXMLResponse result = (GetCursOnDateXMLResponse) unmarshaller.unmarshal(root);
        return result;
    }
}
