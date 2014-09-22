package com.bottinifuel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

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
public class FutureCallsMapper extends HttpServlet {
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

        InetAddress ipaddr = InetAddress.getByName(request.getRemoteHost());
        String ipname = ipaddr.getHostName();
        System.out.println(request.getRequestURL() + " : " +
                           new Date() + " : " +
                           ipname + "(" + request.getRemoteHost() + ") : " +
                           ((dateparm == null) ? "" : dateparm));

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
                    "          var star_green    = new YImage();\n" +  
                    "          star_green.src   = 'star_green.gif';\n" +  
                    "          star_green.size  = new YSize(16,16);\n" +
                    "          star_green.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var star_blue    = new YImage();\n" +  
                    "          star_blue.src    = 'star_blue.gif';\n" +  
                    "          star_blue.size   = new YSize(16,16);\n" +
                    "          star_blue.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var star_red     = new YImage();\n" +  
                    "          star_red.src     = 'star_red.gif';\n" +  
                    "          star_red.size    = new YSize(16,16);\n" +
                    "          star_red.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var star_grey    = new YImage();\n" +  
                    "          star_grey.src    = 'star_grey.gif';\n" +  
                    "          star_grey.size   = new YSize(16,16);\n" +
                    "          star_grey.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var star_yellow  = new YImage();\n" +  
                    "          star_yellow.src  = 'star_yellow.gif';\n" +  
                    "          star_yellow.size = new YSize(16,16);\n" +
                    "          star_yellow.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var star_orange  = new YImage();\n" +  
                    "          star_orange.src  = 'star_orange.gif';\n" +  
                    "          star_orange.size = new YSize(16,16);\n" +
                    "          star_orange.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var star_purple  = new YImage();\n" +  
                    "          star_purple.src  = 'star_purple.gif';\n" +  
                    "          star_purple.size = new YSize(16,16);\n" +
                    "          star_purple.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var wrench_red  = new YImage();\n" +  
                    "          wrench_red.src  = 'wrench_red.png';\n" +  
                    "          wrench_red.size = new YSize(16,16);\n" +
                    "          wrench_red.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var wrench_yellow  = new YImage();\n" +  
                    "          wrench_yellow.src  = 'wrench_yellow.png';\n" +  
                    "          wrench_yellow.size = new YSize(16,16);\n" +
                    "          wrench_yellow.offsetSmartWindow = new YCoordPoint(11,13);\n" +
                    "          var wrench_green  = new YImage();\n" +  
                    "          wrench_green.src  = 'wrench_green.png';\n" +  
                    "          wrench_green.size = new YSize(16,16);\n" +
                    "          wrench_green.offsetSmartWindow = new YCoordPoint(11,13);\n" +
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
        
        TreeMap<Integer, String> geocodeErrors = new TreeMap<Integer, String>(); 
        
        HashMap<String, GeocodedAddress> geocodeCache = (HashMap<String, GeocodedAddress>)sc.getAttribute("geocode_cache"); 
        if (geocodeCache == null)
        {
            ErrorPage(out, "Error: can't get geocodeCache");
            return;
        }
        
        int cachedCount = 0;
        int totalCount = 0;
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
                totalCount++;
                if (geocode == null)
                {
                    geocode = GeocodedAddress.GeocodeAddress(appid,
                                                             call.Location.ServiceAddress.Street1,
                                                             call.Location.ServiceAddress.City,
                                                             call.Location.ServiceAddress.State,
                                                             call.Location.ServiceAddress.Zip);
                    geocodeCache.put(addr, geocode);
                }
                else
                {
                    out.println("/* Cached! */");
                    cachedCount++;
                }
                String callReason = "" + ServiceCall.CallReasonDescription(call.Reason1);
                if (call.Reason2 != 0)
                    callReason += " / " + ServiceCall.CallReasonDescription(call.Reason2);
                if (call.Reason3 != 0)
                    callReason += " / " + ServiceCall.CallReasonDescription(call.Reason3);

                String callTime = ampm.format(call.SchedStartDateTime) + "-" + ampm.format(call.SchedEndDateTime);
                
                String comment = call.Comment.trim();
                comment = comment.replaceAll("  ", " ");
                comment = EscapeChars.forHTML(comment);
                comment = comment.replaceAll("\r\n|\n", "<br/>");

                String color = "red";
                String windowColor = "maroon";
                String shape = "star_";
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
                    callReason = "";
                    shape = "wrench_";
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
                            geocode.Latitude + ", " + geocode.Longitude + "), " + shape + color + ");");
                out.println("/* Address: " + addr + "*/");

                String geocodeWarning = null;
                if (!geocode.Precision.equals("address"))
                {
                        out.println("/* Precision: " + geocode.Precision + "*/");
                        geocodeWarning = "<td>" + energyInfo.InternalToFullAccount(call.Location.ShortAcct) + "</td>" +
                                         "<td>" + call.Location.ServiceNum + "</td>" +
                                         "<td>" + geocode.Precision + "</td><td>" + addr + "</td>";
                }
                if (geocode.Warning != null)
                {
                    out.println("/* Geocode Warning: " + geocode.Warning + "*/");
                    geocodeWarning = "<td>" + energyInfo.InternalToFullAccount(call.Location.ShortAcct) + "</td>" +
                                     "<td>" + call.Location.ServiceNum + "</td>" +
                                     "<td>" + geocode.Precision + "</td><td>" + geocode.Warning + "<br/>" + addr + "</td>";
                }
                if (geocodeWarning != null)
                    geocodeErrors.put(call.WorkorderNum, geocodeWarning);
                out.println("  marker" + call.WorkorderNum + ".addAutoExpand(\"<html>" + call.WorkorderNum + 
                            (geocode.Precision.equals("address") ? "" : "*") +
                            (geocode.Warning == null ? "" : "!") +                                                                
                            " - " + callReason +
                            "<br/>--" + callTime +
                            "<br/>--" + comment +
                            "<br/>--" + addr +
                            "</html>\");\n" +
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
                    "  <tr><td>All day Cleaning</td><td><img src=\"wrench_green.png\"></td><td>" + alldayclean + "</td></tr>\n" +
                    "  <tr><td>AM Cleaning</td><td><img src=\"wrench_red.png\"></td><td>" + amclean + "</td></tr>\n" +
                    "  <tr><td>PM Cleaning</td><td><img src=\"wrench_yellow.png\"></td><td>" + pmclean + "</td></tr>\n" +
                    "  <tr><td>Propane</td><td><img src=\"star_blue.gif\"></td><td>" + propane + "</td></tr>\n" +
                    "  <tr><td>Everything else</td><td><img src=\"star_red.gif\"></td><td>" + everything + "</td></tr>\n" +
                    "  <tr><td>Estimate</td><td><img src=\"star_grey.gif\"></td><td>" + estimate + "</td></tr>\n" +
                    "  <tr><td>Install</td><td><img src=\"star_purple.gif\"></td><td>" + install + "</td></tr>\n" +
                    "</table>\n");
        
        System.out.println("FutureCallsMapper: total calls = " + totalCount + " / cached = " + cachedCount);
        if (geocodeErrors.size() > 0)
        {
            out.println("<br/><br/>\n    <h2>Geocode Errors</h2>\n    <table border=1>\n" +
            "      <tr><th>Call #</th><th>Acct #</th><th>Svc #</th><th>Precision</th><th>Message</th></tr>");
            for (int callnum : geocodeErrors.keySet())
            {
                out.println("<tr><td>" + callnum + "</td>" + geocodeErrors.get(callnum) + "</tr>");
            }
            out.println("    </table>\n");
            System.out.println("FutureCallsMapper: geocode errors = " + geocodeErrors.size());
        }

//        SortedMap<Integer, String> reasons = ServiceCall.AllCallReasons();
//        out.println("<br/><br/>\n    <table border=1>\n" +
//                    "      <tr><th>Reason</th><th>Description</th></tr>");
//        for (int callnum : reasons.keySet())
//        {
//            out.println("      <tr><td>" + callnum + "</td><td>" + reasons.get(callnum) + "</td></tr>");
//        }
//        out.println("    </table>\n");
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
