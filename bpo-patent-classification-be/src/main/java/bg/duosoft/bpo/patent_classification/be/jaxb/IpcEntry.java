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
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;div xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;representation of structured and non-structured elements of the IPC text&lt;/div&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h3 xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;Comments&lt;/h3&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;ol xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;&lt;li&gt;non-structured entries are: subsection titles, indexes, notes, guide headings&lt;/li&gt;&lt;li&gt;for structured entries the symbol attribute must be unique in one language&lt;/li&gt;&lt;li&gt;non-structured entries are identified by symbolTo attribute in case the scope of the non-structured entry overlaps more structured entries that are on the same level as the start of the scope&lt;/li&gt;&lt;li&gt;a note that belongs to a sequence of ipcEntries is presented in the list 
 * 					of the parent of the highest entry within its scope&lt;/li&gt;&lt;li&gt;a non-structured entry (note, guidance heading, subsection title)
 * 						must not contain embedded ipcEntry entries&lt;/li&gt;&lt;li&gt;ipcEntry in compilation files has a different structure&lt;/li&gt;
 * 				&lt;/ol&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h3 xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;History&lt;/h3&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;dl xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;&lt;dt&gt;
 * 						20051001 TA&lt;/dt&gt;&lt;dd&gt;language code allows any two-letter language code&lt;/dd&gt;&lt;dd&gt;obsolete EF language code maybe used at language-independent cases, e.g. deletions; EF is a non-existing 2-letter language code&lt;/dd&gt;
 * 				&lt;/dl&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for ipcEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ipcEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="textBody" type="{http://www.wipo.int/classifications/ipc/masterfiles}schemeTextBody" minOccurs="0"/>
 *         &lt;element name="ipcEntry" type="{http://www.wipo.int/classifications/ipc/masterfiles}ipcEntry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.wipo.int/classifications/ipc/masterfiles}ipcEntryAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ipcEntry", propOrder = {
    "textBody",
    "ipcEntry"
})
public class IpcEntry {

    protected SchemeTextBody textBody;
    protected List<IpcEntry> ipcEntry;
    @XmlAttribute(name = "symbol", required = true)
    protected String symbol;
    @XmlAttribute(name = "endSymbol")
    protected String endSymbol;
    @XmlAttribute(name = "entryType")
    protected EntryType entryType;
    @XmlAttribute(name = "kind")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String kind;
    @XmlAttribute(name = "edition")
    protected String edition;
    @XmlAttribute(name = "scheme")
    protected ClassificationScheme scheme;

    /**
     * Gets the value of the textBody property.
     * 
     * @return
     *     possible object is
     *     {@link SchemeTextBody }
     *     
     */
    public SchemeTextBody getTextBody() {
        return textBody;
    }

    /**
     * Sets the value of the textBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemeTextBody }
     *     
     */
    public void setTextBody(SchemeTextBody value) {
        this.textBody = value;
    }

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
     * Gets the value of the symbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the value of the symbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSymbol(String value) {
        this.symbol = value;
    }

    /**
     * Gets the value of the endSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndSymbol() {
        return endSymbol;
    }

    /**
     * Sets the value of the endSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndSymbol(String value) {
        this.endSymbol = value;
    }

    /**
     * Gets the value of the entryType property.
     * 
     * @return
     *     possible object is
     *     {@link EntryType }
     *     
     */
    public EntryType getEntryType() {
        if (entryType == null) {
            return EntryType.K;
        } else {
            return entryType;
        }
    }

    /**
     * Sets the value of the entryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntryType }
     *     
     */
    public void setEntryType(EntryType value) {
        this.entryType = value;
    }

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKind(String value) {
        this.kind = value;
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
     * Gets the value of the scheme property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationScheme }
     *     
     */
    public ClassificationScheme getScheme() {
        if (scheme == null) {
            return ClassificationScheme.IPC;
        } else {
            return scheme;
        }
    }

    /**
     * Sets the value of the scheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationScheme }
     *     
     */
    public void setScheme(ClassificationScheme value) {
        this.scheme = value;
    }

}