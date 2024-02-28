//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.08 at 04:23:51 PM EEST 
//


package bg.duosoft.bpo.patent_classification.be.jaxb;

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
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;div xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;One IPC version in one language&lt;/div&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for IPCScheme complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPCScheme">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ipcEntry" type="{http://www.wipo.int/classifications/ipc/masterfiles}ipcEntry" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="edition" use="required" type="{http://www.wipo.int/classifications/ipc/masterfiles}edition" />
 *       &lt;attribute name="lang" use="required" type="{http://www.wipo.int/classifications/ipc/masterfiles}lang" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPCScheme", propOrder = {
    "ipcEntry"
})
public class IPCScheme {

    @XmlElement(required = true)
    protected List<IpcEntry> ipcEntry;
    @XmlAttribute(name = "edition", required = true)
    protected String edition;
    @XmlAttribute(name = "lang", required = true)
    protected Lang lang;

    /**
     * Gets the value of the ipcEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ipcEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIpcEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IpcEntry }
     * 
     * 
     */
    public List<IpcEntry> getIpcEntry() {
        if (ipcEntry == null) {
            ipcEntry = new ArrayList<IpcEntry>();
        }
        return this.ipcEntry;
    }

    /**
     * Gets the value of the edition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdition() {
        return edition;
    }

    /**
     * Sets the value of the edition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdition(String value) {
        this.edition = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link Lang }
     *     
     */
    public Lang getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lang }
     *     
     */
    public void setLang(Lang value) {
        this.lang = value;
    }

}