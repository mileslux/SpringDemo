package com.example.soap;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.time.LocalDate;

/**
 * Created by mileslux on 11/7/2015.
 */
public interface SOAPMessageProvider {
    SOAPMessage createGetCursOnDateXML(String date) throws SOAPException;
    SOAPMessage createEnumValutesXML(boolean daily) throws SOAPException;
}
