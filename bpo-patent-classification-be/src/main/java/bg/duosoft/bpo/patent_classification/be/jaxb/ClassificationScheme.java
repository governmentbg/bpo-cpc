//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.08 at 04:23:51 PM EEST 
//


package bg.duosoft.bpo.patent_classification.be.jaxb;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for classificationScheme.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="classificationScheme">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="ipc"/>
 *     &lt;enumeration value="cpc"/>
 *     &lt;enumeration value="fi"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "classificationScheme")
@XmlEnum
public enum ClassificationScheme {

    @XmlEnumValue("ipc")
    IPC("ipc"),
    @XmlEnumValue("cpc")
    CPC("cpc"),
    @XmlEnumValue("fi")
    FI("fi");
    private final String value;

    ClassificationScheme(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ClassificationScheme fromValue(String v) {
        for (ClassificationScheme c: ClassificationScheme.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
