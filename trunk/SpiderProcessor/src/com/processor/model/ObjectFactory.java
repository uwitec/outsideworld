//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.12 at 10:00:00 下午 CST 
//


package com.processor.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.processor.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Processors_QNAME = new QName("http://www.example.org/demo/", "processors");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.processor.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessorFactoryType }
     * 
     */
    public ProcessorFactoryType createProcessorFactoryType() {
        return new ProcessorFactoryType();
    }

    /**
     * Create an instance of {@link FilterType }
     * 
     */
    public FilterType createFilterType() {
        return new FilterType();
    }

    /**
     * Create an instance of {@link ProcessorsType }
     * 
     */
    public ProcessorsType createProcessorsType() {
        return new ProcessorsType();
    }

    /**
     * Create an instance of {@link InjectorType }
     * 
     */
    public InjectorType createInjectorType() {
        return new InjectorType();
    }

    /**
     * Create an instance of {@link ElementType }
     * 
     */
    public ElementType createElementType() {
        return new ElementType();
    }

    /**
     * Create an instance of {@link ParamType }
     * 
     */
    public ParamType createParamType() {
        return new ParamType();
    }

    /**
     * Create an instance of {@link ProcessorType }
     * 
     */
    public ProcessorType createProcessorType() {
        return new ProcessorType();
    }

    /**
     * Create an instance of {@link CollectorType }
     * 
     */
    public CollectorType createCollectorType() {
        return new CollectorType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessorsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/demo/", name = "processors")
    public JAXBElement<ProcessorsType> createProcessors(ProcessorsType value) {
        return new JAXBElement<ProcessorsType>(_Processors_QNAME, ProcessorsType.class, null, value);
    }

}
