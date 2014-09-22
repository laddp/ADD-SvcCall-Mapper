/*
 * Created on Jan 14, 2010 by pladd
 *
 */
package com.bottinifuel;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

import com.yahoo.maps.Result;
import com.yahoo.maps.ResultSet;

/**
 * @author pladd
 *
 */
public class GeocodedAddress implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4245507723419753024L;
    public final String     Address;
    public final BigDecimal Latitude;
    public final BigDecimal Longitude;
    public final String     Precision;
    public final String     Warning;

    private GeocodedAddress(String addr, BigDecimal lat, BigDecimal lon,
                           String precision, String warning)
    {
        Address = addr;
        Latitude = lat;
        Longitude = lon;
        Precision = precision;
        Warning = warning;
    }
    
    public static String AddressString(String street, String city, String state, String zip)
    {
        String address = "";
        if (street != null && street.length() != 0)
        {
            address += street;
        }
        if (city != null && city.length() != 0)
        {
            if (address.length() > 0)
                address += ", ";
            address += city;
        }
        if (state != null && state.length() != 0)
        {
            if (address.length() > 0)
                address += " ";
            address += state;
        }
        if (zip != null && zip.length() != 0)
        {
            if (address.length() > 0)
                address += " ";
            address += zip;
        }
        return address;
    }
    
    public static GeocodedAddress GeocodeAddress(String appid,                                                 
                                                 String street, String city, String state, String zip)
        throws Exception
    {
        String query = "appid=" + appid;
        String address = "";
        if (street != null && street.length() != 0)
        {
            query += "&street=" + street;
            address += street;
        }
        if (city != null && city.length() != 0)
        {
            query += "&city=" + city;
            if (address.length() > 0)
                address += ", ";
            address += city;
        }
        if (state != null && state.length() != 0)
        {
            query += "&state=" + state;
            if (address.length() > 0)
                address += " ";
            address += state;
        }
        if (zip != null && zip.length() != 0)
        {
            query += "&zip=" + zip;
            if (address.length() > 0)
                address += " ";
            address += zip;
        }
        URI request = new URI("http", "api.local.yahoo.com", "/MapsService/V1/geocode", query, null);
        
        InputStreamReader r = new InputStreamReader(request.toURL().openStream());
        ResultSet resultSet = (ResultSet)ResultSet.unmarshal(r);
        Result [] results = resultSet.getResult();
        return new GeocodedAddress(address, 
                                   results[0].getLatitude(),  results[0].getLongitude(),
                                   results[0].getPrecision(), results[0].getWarning());
    }
}
