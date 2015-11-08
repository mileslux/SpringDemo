package com.example.soap;

import com.example.soap.response.EnumValutesXMLData;
import com.example.soap.response.GetCursOnDateXMLData;

import javax.xml.soap.SOAPMessage;
import java.util.Collection;

/**
 * Created by mileslux on 11/8/2015.
 */
public interface SOAPMessageParser {
    Collection<EnumValutesXMLData.EnumValutes> parseEnumValutesXML(SOAPMessage soapMessage) throws Exception;
    Collection<GetCursOnDateXMLData.ValuteCursOnDate> parseGetCursOnDateXML(SOAPMessage soapMessage) throws Exception;
}
