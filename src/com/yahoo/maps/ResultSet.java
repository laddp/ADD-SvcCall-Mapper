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

import java.util.ArrayList;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class ResultSet.
 * 
 * @version $Revision$ $Date$
 */
public class ResultSet implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Field _resultList
     */
    private java.util.ArrayList<Result> _resultList;


      //----------------/
     //- Constructors -/
    //----------------/

    public ResultSet() {
        super();
        _resultList = new ArrayList<Result>();
    } //-- com.yahoo.maps.ResultSet()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addResult
     * 
     * 
     * 
     * @param vResult
     */
    public void addResult(com.yahoo.maps.Result vResult)
        throws java.lang.IndexOutOfBoundsException
    {
        if (!(_resultList.size() < 50)) {
            throw new IndexOutOfBoundsException();
        }
        _resultList.add(vResult);
    } //-- void addResult(com.yahoo.maps.Result) 

    /**
     * Method addResult
     * 
     * 
     * 
     * @param index
     * @param vResult
     */
    public void addResult(int index, com.yahoo.maps.Result vResult)
        throws java.lang.IndexOutOfBoundsException
    {
        if (!(_resultList.size() < 50)) {
            throw new IndexOutOfBoundsException();
        }
        _resultList.add(index, vResult);
    } //-- void addResult(int, com.yahoo.maps.Result) 

    /**
     * Method clearResult
     * 
     */
    public void clearResult()
    {
        _resultList.clear();
    } //-- void clearResult() 

    /**
     * Method enumerateResult
     * 
     * 
     * 
     * @return Enumeration
     */
    public java.util.Enumeration< ? > enumerateResult()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_resultList.iterator());
    } //-- java.util.Enumeration enumerateResult() 

    /**
     * Method getResult
     * 
     * 
     * 
     * @param index
     * @return Result
     */
    public com.yahoo.maps.Result getResult(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _resultList.size())) {
            throw new IndexOutOfBoundsException();
        }
        
        return _resultList.get(index);
    } //-- com.yahoo.maps.Result getResult(int) 

    /**
     * Method getResult
     * 
     * 
     * 
     * @return Result
     */
    public com.yahoo.maps.Result[] getResult()
    {
        int size = _resultList.size();
        com.yahoo.maps.Result[] mArray = new com.yahoo.maps.Result[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = _resultList.get(index);
        }
        return mArray;
    } //-- com.yahoo.maps.Result[] getResult() 

    /**
     * Method getResultCount
     * 
     * 
     * 
     * @return int
     */
    public int getResultCount()
    {
        return _resultList.size();
    } //-- int getResultCount() 

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
     * Method removeResult
     * 
     * 
     * 
     * @param vResult
     * @return boolean
     */
    public boolean removeResult(com.yahoo.maps.Result vResult)
    {
        boolean removed = _resultList.remove(vResult);
        return removed;
    } //-- boolean removeResult(com.yahoo.maps.Result) 

    /**
     * Method setResult
     * 
     * 
     * 
     * @param index
     * @param vResult
     */
    public void setResult(int index, com.yahoo.maps.Result vResult)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _resultList.size())) {
            throw new IndexOutOfBoundsException();
        }
        if (!(index < 50)) {
            throw new IndexOutOfBoundsException();
        }
        _resultList.set(index, vResult);
    } //-- void setResult(int, com.yahoo.maps.Result) 

    /**
     * Method setResult
     * 
     * 
     * 
     * @param resultArray
     */
    public void setResult(com.yahoo.maps.Result[] resultArray)
    {
        //-- copy array
        _resultList.clear();
        for (int i = 0; i < resultArray.length; i++) {
            _resultList.add(resultArray[i]);
        }
    } //-- void setResult(com.yahoo.maps.Result) 

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
        return (com.yahoo.maps.ResultSet) Unmarshaller.unmarshal(com.yahoo.maps.ResultSet.class, reader);
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
