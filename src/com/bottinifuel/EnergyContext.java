/*
 * Created on Jan 5, 2010 by pladd
 *
 */
package com.bottinifuel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bottinifuel.Energy.Info.InfoFactory;
import com.bottinifuel.Energy.Info.ServiceCall;

/**
 * @author pladd
 *
 */
public class EnergyContext implements ServletContextListener
{
    private ServletContext Context;
    private HashMap<String, GeocodedAddress> GeocodedAddresses;
    
    public void contextDestroyed(ServletContextEvent sc)
    {
        String cache_filename = sc.getServletContext().getInitParameter("geocode_cache_file");
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try
        {
            fos = new FileOutputStream(cache_filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(GeocodedAddresses);
            out.close();
            System.out.println("ServiceCallMapper: contextDestroyed(): Wrote " +
                               GeocodedAddresses.size() + " geocodes to cache file: " + cache_filename);
        }
        catch(IOException ex)
        {
            System.out.println("ServiceCallMapper: contextDestroyed()" + ex);
        }
    }

    private String ReadParm(ServletContext sc, String parm) throws Exception
    {
        String rc = sc.getInitParameter(parm);
        if (rc == null || rc.trim().length() == 0)
            throw new Exception("Error reading \"" + parm + "\" InitParameter");
        return rc;
    }
    
    private Set<Integer> ReadCallReasons(ServletContext sc, String type)
    {
        Set<Integer> rc = new HashSet<Integer>();
        String reasons = sc.getInitParameter(type);
        String [] groups = reasons.split(",");
        for (String group : groups)
        {
            String [] components = group.split("-");
            switch (components.length)
            {
            case 1:
                rc.add(Integer.valueOf(components[0]));
                break;
            case 2:
                for (int i = Integer.valueOf(components[0]); i <= Integer.valueOf(components[1]); i++)
                    rc.add(i);
                break;
            default:
                System.out.println("ServiceCallMapper: Invalid " + type + " string specified" + reasons);
                rc.clear();
                return rc;
            }
        }
        System.out.println("ServiceCallMapper: contextInitialized(): " + type + " = " + reasons);
        return rc;
    }
    
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent arg0)
    {
        try {
            ServletContext sc = Context = arg0.getServletContext();

            String appid = ReadParm(sc, "yahoo_app_id");
            sc.setAttribute("yahoo_app_id", appid);
            System.out.println("ServiceCallMapper: contextInitialized(): yahoo_app_id    = " + appid);
            
            GeocodedAddresses = new HashMap<String, GeocodedAddress>();
            String cache_filename = sc.getInitParameter("geocode_cache_file");
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try
            {
                fis = new FileInputStream(cache_filename);
                in = new ObjectInputStream(fis);
                GeocodedAddresses = (HashMap<String, GeocodedAddress>)in.readObject();
                in.close();
                System.out.println("ServiceCallMapper: contextInitialized(): Read " +
                                   GeocodedAddresses.size() + " geocodes from cache file: " + cache_filename);
            }
            catch(IOException ex)
            {
                System.out.println("ServiceCallMapper: contextInitialized()" + ex);
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println("ServiceCallMapper: contextInitialized()" + ex);
            }
            sc.setAttribute("geocode_cache", GeocodedAddresses);

            String server = ReadParm(sc, "energy_server");
            String ports  = ReadParm(sc, "energy_port");
            String db     = ReadParm(sc, "energy_db");
            String user   = ReadParm(sc, "energy_user");
            String pw     = ReadParm(sc, "energy_password");
            
            System.out.println("ServiceCallMapper: contextInitialized(): energy_server   = " + server);
            System.out.println("ServiceCallMapper: contextInitialized(): energy_port     = " + ports);
            System.out.println("ServiceCallMapper: contextInitialized(): energy_db       = " + db);
            System.out.println("ServiceCallMapper: contextInitialized(): energy_db       = " + user);
            System.out.println("ServiceCallMapper: contextInitialized(): energy_password = " + pw);
            int port = Integer.valueOf(ports);
            
            InfoFactory inf = new InfoFactory(server, port, db, user, pw);
            
            sc.setAttribute("energy_inf", inf);
            
            
            Set<Integer> estimateReasons = ReadCallReasons(sc, "estimate_reasons");
            ServiceCall.SetEstimateReasons(estimateReasons);
            
            Set<Integer> cleaningReasons = ReadCallReasons(sc, "cleaning_reasons");
            ServiceCall.SetCleaningReasons(cleaningReasons);

            Set<Integer> propaneReasons  = ReadCallReasons(sc, "propane_reasons");
            ServiceCall.SetPropaneReasons(propaneReasons);

            Set<Integer> installReasons  = ReadCallReasons(sc, "install_reasons");
            ServiceCall.SetInstallReasons(installReasons);
        }
        catch (Exception e)
        {
            System.out.println("ServiceCallMapper: contextInitialized(): " + e);
        }
    }
}
