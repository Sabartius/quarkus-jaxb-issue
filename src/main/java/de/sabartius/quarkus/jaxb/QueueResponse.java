package de.sabartius.quarkus.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "message"
})
@XmlRootElement(name = "QueueResponse")
public class QueueResponse {

    protected List<QueueMessage> message;

    public List<QueueMessage> getMessage() {
        if (message == null) {
            message = new ArrayList<QueueMessage>();
        }
        return this.message;
    }
}
