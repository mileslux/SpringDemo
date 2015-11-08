package com.example.soap;

import javax.xml.soap.SOAPMessage;

/**
 * Created by mileslux on 11/8/2015.
 */
public interface SOAPClient {
    SOAPMessage send(SOAPMessage soapMessage) throws Exception;
}
