//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.08 at 04:23:51 PM EEST 
//


package bg.duosoft.bpo.patent_classification.be.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;div xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;sequence of embedded noteParagraph-s&lt;/div&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for schemeSubNote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="schemeSubNote">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="noteParagraph" type="{http://www.wipo.int/classifications/ipc/masterfiles}schemeNoteParagraph"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.wipo.int/classifications/ipc/masterfiles}subnoteType" default="none" />
 *       &lt;attribute name="indent" type="{http://www.wipo.int/classifications/ipc/masterfiles}indent" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schemeSubNote", propOrder = {
    "noteParagraph"
})
public class SchemeSubNote {

    @XmlElement(required = true)
    protected List<SchemeNoteParagraph> noteParagraph;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "indent")
    protected BigInteger indent;

    /**
     * Gets the value of the noteParagraph property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noteParagraph property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoteParagraph().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SchemeNoteParagraph }
     * 
     * 
     */
    public List<SchemeNoteParagraph> getNoteParagraph() {
        if (noteParagraph == null) {
            noteParagraph = new ArrayList<SchemeNoteParagraph>();
        }
        return this.noteParagraph;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        if (type == null) {
            return "none";
        } else {
            return type;
        }
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the indent property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIndent() {
        return indent;
    }

    /**
     * Sets the value of the indent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIndent(BigInteger value) {
        this.indent = value;
    }

}
