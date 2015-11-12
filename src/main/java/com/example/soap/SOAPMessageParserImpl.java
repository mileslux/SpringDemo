package com.example.soap;

import com.example.soap.response.EnumValutesXMLData;
import com.example.soap.response.GetCursOnDateXMLData;
import com.example.soap.response.GetCursOnDateXMLFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPMessage;
import java.util.*;

/**
 * Created by mileslux on 11/8/2015.
 */
public class SOAPMessageParserImpl implements SOAPMessageParser {
    @Override
    public Collection<EnumValutesXMLData.EnumValutes> parseEnumValutesXML(SOAPMessage soapMessage) throws Exception {
        return new ResponseTraverse<EnumValutesXMLData.EnumValutes>() {

            @Override
            protected Collection<EnumValutesXMLData.EnumValutes> onElementVisit(Node currentNode) throws Exception {
                if (!currentNode.getNodeName().equals("ValuteData")) return empty;

                JAXBContext jaxbContext = JAXBContext.newInstance(GetCursOnDateXMLFactory.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return ((EnumValutesXMLData) unmarshaller.unmarshal(currentNode)).getEnumValutes();
            }

        }.traverse(soapMessage.getSOAPBody().extractContentAsDocument());
    }

    @Override
    public Collection<GetCursOnDateXMLData.ValuteCursOnDate> parseGetCursOnDateXML(SOAPMessage soapMessage) throws Exception {
        return new ResponseTraverse<GetCursOnDateXMLData.ValuteCursOnDate>() {

            @Override
            protected Collection<GetCursOnDateXMLData.ValuteCursOnDate> onElementVisit(Node currentNode) throws Exception {
                if (!currentNode.getNodeName().equals("ValuteData")) return empty;

                JAXBContext jaxbContext = JAXBContext.newInstance(GetCursOnDateXMLFactory.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return ((GetCursOnDateXMLData) unmarshaller.unmarshal(currentNode)).getValuteCursOnDate();
            }

        }.traverse(soapMessage.getSOAPBody().extractContentAsDocument());
    }

    protected abstract class ResponseTraverse<D> {
        protected final Collection<D> empty = new ArrayList<D>(0);

        protected Collection<D> traverse(Node root) throws Exception {
            List<D> result = new ArrayList<D>();
            Deque<Node> stack = new ArrayDeque<Node>();
            stack.add(root);

            while (!stack.isEmpty()) {
                Node node = stack.removeFirst();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Collection<D> items = onElementVisit(node);
                    if (items != empty) {
                        result.addAll(items);
                        continue;
                    }
                }

                NodeList nodeList = node.getChildNodes();
                for (int len = nodeList.getLength(), i = len - 1; i >= 0; i--)
                    stack.addFirst(nodeList.item(i));
            }

            return result;
        }

        protected abstract Collection<D> onElementVisit(Node currentNode) throws Exception;
    }
}
