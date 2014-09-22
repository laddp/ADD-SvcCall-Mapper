package com.bottinifuel;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bottinifuel.Energy.Info.InfoFactory;
import com.bottinifuel.Energy.Info.ServiceCall;

/**
 * Servlet implementation class FutureCallsMapper
 */
public class FutureCallsMapper2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException {
	}

	
	private void ErrorPage(PrintWriter out, String error)
	{
        out.println("<html>" +
                    "<head>" +
                    "<title>Future Service Calls</title>" + 
                    "</head>" +
                    "<body>" +
                    error +
                    "</body></html>");
        out.close();
	}
	
	private void HandleEmptyRequest(PrintWriter out)
	{
	    out.println("<html>" +
	                "<head>" +
	                "<title>Future Service Calls</title>" + 
	                "<script type=\"text/javascript\" src=\"calendarDateInput.js\">" +
	                "/***********************************************" +
	                "* Jason's Date Input Calendar- By Jason Moon http://calendar.moonscript.com/dateinput.cfm" +
	                "* Script featured on and available at http://www.dynamicdrive.com" +
	                "* Keep this notice intact for use." +
	                "***********************************************/" +
	                "</script>" +
	                "</head>" +
	                "<body>");

	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	    df.setLenient(false);
	    Calendar date = new GregorianCalendar();
	    date.set(Calendar.HOUR_OF_DAY, 0);
	    date.set(Calendar.MINUTE, 0);
	    date.set(Calendar.SECOND, 0);
	    date.set(Calendar.MILLISECOND, 0);
	    if (date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
	        date.add(Calendar.DAY_OF_MONTH, 3);
	    else
	        date.add(Calendar.DAY_OF_MONTH, 1);

	    out.println("<img src=\"bottini.jpg\" width=\"300\">");
        out.println("<h1>Future Service Call Mapper</h1>");
	    out.println("<form method=\"post\">");
	    out.println("<LABEL for=\"date\">Date:</LABEL>");
	    out.println("<script>DateInput('date', true, 'MM/DD/YYYY', '" + df.format(date.getTime()) +
	    "')</script>");
	    out.println("<br/>");
	    out.println("<INPUT type=\"submit\" value=\"Map Calls\">");
	    out.println("</form>");

	    out.println("</body></html>");
	    out.close();
	}
	
	@SuppressWarnings("unchecked")
    private void HandleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        response.setContentType("text/html");
        response.setBufferSize(8192);
        PrintWriter out = response.getWriter();

        String dateparm = request.getParameter("date");
        if (dateparm == null || dateparm.trim().length() == 0)
        {
            HandleEmptyRequest(out);
            return;
        }

        ServletContext sc = getServletContext();
        String appid = (String)sc.getAttribute("yahoo_app_id");
        if (appid == null)
        {
            ErrorPage(out, "Error: can't get yahoo_app_id");
            return;
        }

        InfoFactory energyInfo = (InfoFactory)getServletContext().getAttribute("energy_inf");
        if (energyInfo == null)
        {
            ErrorPage(out, "Error: can't get database connection.");
            return;
        }

        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        df.setLenient(false);
        Date d;
        try {
            d = df.parse(dateparm);
        }
        catch (ParseException e)
        {
            ErrorPage(out, "Error: Invalid date: \"" + dateparm + "\"");
            return;
        }

        out.println("<html>" +
                    "<head>" +
                    "     <title>Future Service Calls - " + dateparm + "</title>" + 
                    "     <script src=\"http://api.maps.yahoo.com/ajaxymap?v=3.8&appid=" +
                    appid + "\">"+  
                    "     </script>\n" +
                    "     <style type=\"text/css\">\n" +  
                    "         #map{\n" +  
                    "           height:100%;\n" +  
                    "           width: 100%;\n" +  
                    "         }\n" +
                    "     </style>\n" +
                    "</head>" +
                    "<body>");

        out.println("      <div id=\"map\"></div>\n" +
                    "      <script type=\"text/javascript\">\n" +
                    "          var green    = new YImage();\n" +  
                    "          green.src   = 'star_green.gif';\n" +  
                    "          green.size  = new YSize(15,15);\n" +
                    "          green.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var blue    = new YImage();\n" +  
                    "          blue.src    = 'star_blue.gif';\n" +  
                    "          blue.size   = new YSize(15,15);\n" +
                    "          blue.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var red     = new YImage();\n" +  
                    "          red.src     = 'star_red.gif';\n" +  
                    "          red.size    = new YSize(15,15);\n" +
                    "          red.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var grey    = new YImage();\n" +  
                    "          grey.src    = 'star_grey.gif';\n" +  
                    "          grey.size   = new YSize(15,15);\n" +
                    "          grey.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var yellow  = new YImage();\n" +  
                    "          yellow.src  = 'star_yellow.gif';\n" +  
                    "          yellow.size = new YSize(15,15);\n" +
                    "          yellow.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var orange  = new YImage();\n" +  
                    "          orange.src  = 'star_orange.gif';\n" +  
                    "          orange.size = new YSize(15,15);\n" +
                    "          orange.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var purple  = new YImage();\n" +  
                    "          purple.src  = 'star_purple.gif';\n" +  
                    "          purple.size = new YSize(15,15);\n" +
                    "          purple.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var map = new YMap(document.getElementById('map'));\n" +  
                    "          map.addTypeControl();\n" +
                    "          map.addZoomLong();\n" +
                    "          map.addZoomScale();\n" +
                    "          map.addPanControl();\n" +
                    "          map.setMapType(YAHOO_MAP_REG);\n");

        Set<ServiceCall> calls = energyInfo.CallsForDate(d);

        DateFormat ampm = new SimpleDateFormat("h:mma");

        Calendar noon = Calendar.getInstance();
        noon.setTime(d);
        noon.set(Calendar.HOUR_OF_DAY, 12);
        noon.set(Calendar.MINUTE, 0);
        noon.set(Calendar.SECOND, 0);
        
        int alldayclean = 0;
        int amclean = 0;
        int pmclean = 0;
        int estimate = 0;
        int install = 0;
        int propane = 0;
        int everything = 0;
        
        HashMap<String, GeocodedAddress> geocodeCache = (HashMap<String, GeocodedAddress>)sc.getAttribute("geocode_cache");
        if (geocodeCache == null)
        {
            ErrorPage(out, "Error: can't get geocodeCache");
            return;
        }
        for (ServiceCall call : calls)
        {
            if (call.Location.ServiceAddress == null)
                continue;
            try {
                String addr = GeocodedAddress.AddressString(call.Location.ServiceAddress.Street1,
                                                            call.Location.ServiceAddress.City,
                                                            call.Location.ServiceAddress.State,
                                                            call.Location.ServiceAddress.Zip);
                GeocodedAddress geocode = geocodeCache.get(addr);
                if (geocode == null)
                {
                    geocode = GeocodedAddress.GeocodeAddress((String)sc.getAttribute("yahoo_app_id"),
                                                             call.Location.ServiceAddress.Street1,
                                                             call.Location.ServiceAddress.City,
                                                             call.Location.ServiceAddress.State,
                                                             call.Location.ServiceAddress.Zip);
                    geocodeCache.put(addr, geocode);
                }
                String callReason = "" + call.Reason1;
                if (call.Reason2 != 0)
                    callReason += " / " + call.Reason2;
                if (call.Reason3 != 0)
                    callReason += " / " + call.Reason3;

                String callTime = ampm.format(call.SchedStartDateTime) + "-" + ampm.format(call.SchedEndDateTime);
                
                String comment = call.Comment.trim();
                comment = comment.replaceAll("  ", " ");
                comment = EscapeChars.forHTML(comment);
                comment = comment.replaceAll("\r\n|\n", "<br/>");

                String color = "red";
                String windowColor = "maroon";
                if (call.IsEstimate())
                {
                    estimate++;
                    color = "grey";
                }
                else if (call.IsInstall())
                {
                    install++;
                    color = "purple";
                }
                else if (call.IsCleaning())
                {
                    if (call.SchedStartDateTime.before(noon.getTime()))
                    {
                        if (call.SchedEndDateTime.after(noon.getTime()))
                        {
                            alldayclean++;
                            windowColor = color = "green";
                        }
                        else
                        {
                            amclean++;
                            windowColor = color = "orange";
                        }
                    }
                    else
                    {
                        pmclean++;
                        windowColor = "ocre";
                        color = "yellow";
                    }
                }
                else if (call.IsPropane())
                {
                    propane++;
                    windowColor = color = "blue";
                }
                else
                {
                    everything++;
                }
                out.println("  var marker" + call.WorkorderNum + " = new YMarker(new YGeoPoint(" +
                            geocode.Latitude + ", " + geocode.Longitude + "), " + color + ");");
                out.println("  marker" + call.WorkorderNum + ".addAutoExpand(\"<html>" + call.WorkorderNum + 
                            (geocode.Precision.equals("address") ? "" : "*") +
                            (geocode.Warning == null ? "" : "!") +                                                                
                            " - " + callReason +
                            "<br/>" + callTime + "<br/>" + comment + "</html>\");\n" +
                            "  marker" + call.WorkorderNum + ".setSmartWindowColor(\"" + windowColor + "\");\n" +
                            "  map.addOverlay(marker" + call.WorkorderNum + ");\n");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        out.println("          map.drawZoomAndCenter(\"Wappingers Falls, NY\", 8.5);\n" +
                    "      </script>\n");
        out.println("<br/><br/><br/><a href=\"/ServiceCallMapper/FutureCallsMapper\">New map</a><br/>");
        out.println("<br/>Key:<br/>" +
                    "<table border=1>\n" +
                    "  <tr><td>Cleaning</td><td><img src=\"star_green.gif\"></td><td>" + alldayclean + "</td></tr>\n" +
                    "  <tr><td>AM Cleaning</td><td><img src=\"star_orange.gif\"></td><td>" + amclean + "</td></tr>\n" +
                    "  <tr><td>PM Cleaning</td><td><img src=\"star_yellow.gif\"></td><td>" + pmclean + "</td></tr>\n" +
                    "  <tr><td>Propane</td><td><img src=\"star_blue.gif\"></td><td>" + propane + "</td></tr>\n" +
                    "  <tr><td>Everything else</td><td><img src=\"star_red.gif\"></td><td>" + everything + "</td></tr>\n" +
                    "  <tr><td>Estimate</td><td><img src=\"star_grey.gif\"></td><td>" + estimate + "</td></tr>\n" +
                    "  <tr><td>Install</td><td><img src=\"star_purple.gif\"></td><td>" + install + "</td></tr>\n" +
                    "</table>\n");
        
        SortedMap<Integer, String> reasons = ServiceCall.AllCallReasons();
        out.println("<br/><br/>\n    <table border=1>\n" +
                    "      <tr><th>Reason</th><th>Description</th></tr>");
        for (int callnum : reasons.keySet())
        {
            out.println("      <tr><td>" + callnum + "</td><td>" + reasons.get(callnum) + "</td></tr>");
        }
        out.println("    </table>\n");
        out.println("  </body>\n" +
                    "</html>");
        out.close();
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        HandleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        HandleRequest(request, response);
	}

}
