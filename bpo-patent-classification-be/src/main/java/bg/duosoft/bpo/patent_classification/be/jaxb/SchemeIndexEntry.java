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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;div xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;one paragraph of an index, containing a text and list of references&lt;/div&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h3 xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;Comments&lt;/h3&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ul xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;&lt;li&gt;References should not be empty on the leaves.&lt;/li&gt;&lt;li&gt;Embedding means also: deeper indentation level in presentation and subdivision of subject matter.&lt;/li&gt;&lt;li&gt;List of IPC symbol intervals is not necessarily sequential; inner symbols may appear at a different indexEntry.&lt;/li&gt;
 * 				&lt;/ul&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for schemeIndexEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="schemeIndexEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="text" type="{http://www.wipo.int/classifications/ipc/masterfiles}schemeText"/>
 *         &lt;element name="references" type="{http://www.wipo.int/classifications/ipc/masterfiles}schemeReferences" minOccurs="0"/>
 *         &lt;element name="indexEntry" type="{http://www.wipo.int/classifications/ipc/masterfiles}schemeIndexEntry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schemeIndexEntry", propOrder = {
    "text",
    "references",
    "indexEntry"
})
public class SchemeIndexEntry {

    @XmlElement(required = true)
    protected SchemeText text;
    protected SchemeReferences references;
    protected List<SchemeIndexEntry> indexEntry;

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeText }
     *     
     */
    public SchemeText getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeText }
     *     
     */
    public void setText(SchemeText value) {
        this.text = value;
    }

    /**
     * Gets the value of the references property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeReferences }
     *     
     */
    public SchemeReferences getReferences() {
        return references;
    }

    /**
     * Sets the value of the references property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeReferences }
     *     
     */
    public void setReferences(SchemeReferences value) {
        this.references = value;
    }

    /**
     * Gets the value of the indexEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indexEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndexEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SchemeIndexEntry }
     * 
     * 
     */
    public List<SchemeIndexEntry> getIndexEntry() {
        if (indexEntry == null) {
            indexEntry = new ArrayList<SchemeIndexEntry>();
        }
        return this.indexEntry;
    }

}