package com.example.soap;

import org.springframework.beans.factory.annotation.Value;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Created by mileslux on 11/7/2015.
 */

public class SOAPClientImpl implements SOAPClient{
    @Value("${soapclient.url}")
    private String url;
    @Value("${soapclient.connecttimeout}")
    private int connectTimeout;
    @Value("${soapclient.readtimeout}")
    private int readTimeout;

    public String getUrl() {
        return url;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    @Override
    public SOAPMessage send(SOAPMessage soapMessage) throws SOAPException, MalformedURLException {
        SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
        URL endpoint =
                new URL(null,
                        this.url,
                        new URLStreamHandler() {
                            @Override
                            protected URLConnection openConnection(URL url) throws IOException {
                                URL target = new URL(url.toString());
                                URLConnection connection = target.openConnection();
                                // Connection settings
                                connection.setConnectTimeout(connectTimeout);
                                connection.setReadTimeout(readTimeout);
                                return(connection);
                            }
                        });

        try {
            return connection.call(soapMessage, endpoint);
        } finally {
            connection.close();
        }
    }
}
