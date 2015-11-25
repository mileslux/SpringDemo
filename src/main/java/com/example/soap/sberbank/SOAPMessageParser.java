package com.example.soap.sberbank;

import com.example.soap.sberbank.response.EnumValutesXMLResponse;
import com.example.soap.sberbank.response.GetCursOnDateXMLResponse;
import javax.xml.soap.SOAPMessage;

/**
 * Created by mileslux on 11/8/2015.
 */
public interface SOAPMessageParser {
    EnumValutesXMLResponse parseEnumValutesXMLResponse(SOAPMessage soapMessage) throws Exception;
    GetCursOnDateXMLResponse parseGetCursOnDateXMLResponse(SOAPMessage soapMessage) throws Exception;
}
