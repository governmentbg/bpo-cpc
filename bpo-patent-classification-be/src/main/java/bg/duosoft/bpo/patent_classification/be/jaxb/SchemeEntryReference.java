//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.08 at 04:23:51 PM EEST 
//


package bg.duosoft.bpo.patent_classification.be.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;div xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;a titlePart may have a sequence of entryReferences (i.e. those presented in text of IPC symbols in parentheses), separated by semicolons&lt;/div&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h3 xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;Comment&lt;/h3&gt;
 * </pre>
 * 
 * 				
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;dl xmlns="http://www.wipo.int/classifications/ipc/masterfiles" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt;&lt;dt&gt;usual format of entry references&lt;/dt&gt;&lt;dd&gt;text followed by a sequence of references&lt;/dd&gt;&lt;dt&gt;take precedences and some others&lt;/dt&gt;&lt;dd&gt;free order of text and references&lt;/dd&gt;
 * 				&lt;/dl&gt;
 * </pre>
 * 
 * 			
 * 
 * <p>Java class for schemeEntryReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="schemeEntryReference">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.wipo.int/classifications/ipc/masterfiles}schemeText">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schemeEntryReference")
public class SchemeEntryReference
    extends SchemeText
{


}
