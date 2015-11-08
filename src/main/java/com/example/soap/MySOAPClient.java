package com.example.soap;

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
public class MySOAPClient implements SOAPClient{
    private final String destination;
    private final int connectTimeout;
    private final int readTimeout;

    public MySOAPClient(String destination, int connectTimeout, int readTimeout) {
        this.destination = destination;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public String getDestination() {
        return destination;
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
                        this.destination,
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
