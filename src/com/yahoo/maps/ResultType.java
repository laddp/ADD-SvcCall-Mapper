/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.6</a>, using an XML
 * Schema.
 * $Id$
 */

package com.yahoo.maps;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ResultType.
 * 
 * @version $Revision$ $Date$
 */
public class ResultType implements java.io.Serializable {


      /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _precision
     */
    private java.lang.String _precision;

    /**
     * Field _warning
     */
    private java.lang.String _warning;

    /**
     * Field _latitude
     */
    private java.math.BigDecimal _latitude;

    /**
     * Field _longitude
     */
    private java.math.BigDecimal _longitude;

    /**
     * Field _address
     */
    private java.lang.String _address;

    /**
     * Field _city
     */
    private java.lang.String _city;

    /**
     * Field _state
     */
    private java.lang.String _state;

    /**
     * Field _zip
     */
    private java.lang.String _zip;

    /**
     * Field _country
     */
    private java.lang.String _country;


      //----------------/
     //- Constructors -/
    //----------------/

    public ResultType() {
        super();
    } //-- com.yahoo.maps.ResultType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'address'.
     * 
     * @return String
     * @return the value of field 'address'.
     */
    public java.lang.String getAddress()
    {
        return this._address;
    } //-- java.lang.String getAddress() 

    /**
     * Returns the value of field 'city'.
     * 
     * @return String
     * @return the value of field 'city'.
     */
    public java.lang.String getCity()
    {
        return this._city;
    } //-- java.lang.String getCity() 

    /**
     * Returns the value of field 'country'.
     * 
     * @return String
     * @return the value of field 'country'.
     */
    public java.lang.String getCountry()
    {
        return this._country;
    } //-- java.lang.String getCountry() 

    /**
     * Returns the value of field 'latitude'.
     * 
     * @return BigDecimal
     * @return the value of field 'latitude'.
     */
    public java.math.BigDecimal getLatitude()
    {
        return this._latitude;
    } //-- java.math.BigDecimal getLatitude() 

    /**
     * Returns the value of field 'longitude'.
     * 
     * @return BigDecimal
     * @return the value of field 'longitude'.
     */
    public java.math.BigDecimal getLongitude()
    {
        return this._longitude;
    } //-- java.math.BigDecimal getLongitude() 

    /**
     * Returns the value of field 'precision'.
     * 
     * @return String
     * @return the value of field 'precision'.
     */
    public java.lang.String getPrecision()
    {
        return this._precision;
    } //-- java.lang.String getPrecision() 

    /**
     * Returns the value of field 'state'.
     * 
     * @return String
     * @return the value of field 'state'.
     */
    public java.lang.String getState()
    {
        return this._state;
    } //-- java.lang.String getState() 

    /**
     * Returns the value of field 'warning'.
     * 
     * @return String
     * @return the value of field 'warning'.
     */
    public java.lang.String getWarning()
    {
        return this._warning;
    } //-- java.lang.String getWarning() 

    /**
     * Returns the value of field 'zip'.
     * 
     * @return String
     * @return the value of field 'zip'.
     */
    public java.lang.String getZip()
    {
        return this._zip;
    } //-- java.lang.String getZip() 

    /**
     * Method isValid
     * 
     * 
     * 
     * @return boolean
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * 
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Sets the value of field 'address'.
     * 
     * @param address the value of field 'address'.
     */
    public void setAddress(java.lang.String address)
    {
        this._address = address;
    } //-- void setAddress(java.lang.String) 

    /**
     * Sets the value of field 'city'.
     * 
     * @param city the value of field 'city'.
     */
    public void setCity(java.lang.String city)
    {
        this._city = city;
    } //-- void setCity(java.lang.String) 

    /**
     * Sets the value of field 'country'.
     * 
     * @param country the value of field 'country'.
     */
    public void setCountry(java.lang.String country)
    {
        this._country = country;
    } //-- void setCountry(java.lang.String) 

    /**
     * Sets the value of field 'latitude'.
     * 
     * @param latitude the value of field 'latitude'.
     */
    public void setLatitude(java.math.BigDecimal latitude)
    {
        this._latitude = latitude;
    } //-- void setLatitude(java.math.BigDecimal) 

    /**
     * Sets the value of field 'longitude'.
     * 
     * @param longitude the value of field 'longitude'.
     */
    public void setLongitude(java.math.BigDecimal longitude)
    {
        this._longitude = longitude;
    } //-- void setLongitude(java.math.BigDecimal) 

    /**
     * Sets the value of field 'precision'.
     * 
     * @param precision the value of field 'precision'.
     */
    public void setPrecision(java.lang.String precision)
    {
        this._precision = precision;
    } //-- void setPrecision(java.lang.String) 

    /**
     * Sets the value of field 'state'.
     * 
     * @param state the value of field 'state'.
     */
    public void setState(java.lang.String state)
    {
        this._state = state;
    } //-- void setState(java.lang.String) 

    /**
     * Sets the value of field 'warning'.
     * 
     * @param warning the value of field 'warning'.
     */
    public void setWarning(java.lang.String warning)
    {
        this._warning = warning;
    } //-- void setWarning(java.lang.String) 

    /**
     * Sets the value of field 'zip'.
     * 
     * @param zip the value of field 'zip'.
     */
    public void setZip(java.lang.String zip)
    {
        this._zip = zip;
    } //-- void setZip(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * 
     * 
     * @param reader
     * @return Object
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (com.yahoo.maps.ResultType) Unmarshaller.unmarshal(com.yahoo.maps.ResultType.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     * 
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
