/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.studioblueplanet.rdinfodownload;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.studioblueplanet.mapdatumconvert.MapDatumConvert;
import net.studioblueplanet.mapdatumconvert.LatLonCoordinate;
import net.studioblueplanet.mapdatumconvert.DatumCoordinate;
/**
 *
 * @author jorgen
 */
public class RdInfoParser
{
    private final static Logger LOGGER      = LogManager.getLogger(RdInfoParser.class); 
    private static final String USER_AGENT  = "Mozilla/5.0";

    private static final String POST_PARAMS = "userName=scubajorgen";    

    
    public RdInfoParser()
    {
    }

    /**
     * Parse the XML response from the RD Info service
     * @param doc The response
     * @param wpts Array List of  waypoints to add waypoints to
     * @throws Exception 
     */
    private void parseResponsePoints(Document doc, List<Waypoint> wpts) throws Exception
    {
        NamespaceResolver   nsc;
        Waypoint            wpt;
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        nsc=new NamespaceResolver(doc);
        xpath.setNamespaceContext(nsc);

        XPathExpression exprMember  = xpath.compile("/wfs:FeatureCollection/wfs:member/rdinfo:punten");
        XPathExpression exprName    = xpath.compile("rdinfo:benaming/text()");
        XPathExpression exprYear    = xpath.compile("rdinfo:uitgavejaarext/text()");
        XPathExpression exprXrd     = xpath.compile("rdinfo:xrd/text()");
        XPathExpression exprYrd     = xpath.compile("rdinfo:yrd/text()");
        XPathExpression exprBlad    = xpath.compile("rdinfo:blad/text()");
        XPathExpression exprPunt    = xpath.compile("rdinfo:punt/text()");
        XPathExpression exprBladdeel= xpath.compile("rdinfo:bladdeel/text()");
        XPathExpression exprGps     = xpath.compile("rdinfo:gps/text()");
        NodeList nodes = (NodeList)exprMember.evaluate(doc, XPathConstants.NODESET);
        LOGGER.info("Got {} nodes", nodes.getLength());
        int j=0;
        while (j<nodes.getLength())
        {
            wpt=new Waypoint();
            wpt.name     = (String)exprName.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.year      = (String)exprYear.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.xrd      = (String)exprXrd.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.yrd      = (String)exprYrd.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.gps      = (String)exprGps.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.blad     = (String)exprBlad.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.punt     = (String)exprPunt.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.bladdeel = (String)exprBladdeel.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpts.add(wpt);
            j++;
        }
    }


    /**
     * Parse the XML response from the RD Info service
     * @param doc The response
     * @param wpts Array List of  waypoints to add waypoints to
     * @throws Exception 
     */
    private void parseResponseStation(Document doc, List<Waypoint> wpts) throws Exception
    {
        NamespaceResolver   nsc;
        Waypoint            wpt;
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        nsc=new NamespaceResolver(doc);
        xpath.setNamespaceContext(nsc);

        XPathExpression exprMember  = xpath.compile("/wfs:FeatureCollection/wfs:member/rdinfo:stations");
        XPathExpression exprNameExt = xpath.compile("rdinfo:omschrext/text()");
        XPathExpression exprNameInt = xpath.compile("rdinfo:omschrint/text()");
        XPathExpression exprYear    = xpath.compile("rdinfo:jaar/text()");
        XPathExpression exprMonth   = xpath.compile("rdinfo:maand/text()");
        XPathExpression exprDay     = xpath.compile("rdinfo:dag/text()");
        XPathExpression exprXrd     = xpath.compile("rdinfo:xrd/text()");
        XPathExpression exprYrd     = xpath.compile("rdinfo:yrd/text()");
        XPathExpression exprGps     = xpath.compile("rdinfo:gps/text()");
        XPathExpression exprBlad    = xpath.compile("rdinfo:blad/text()");
        XPathExpression exprPunt    = xpath.compile("rdinfo:punt/text()");
        XPathExpression exprStation = xpath.compile("rdinfo:station/text()");
        NodeList nodes = (NodeList)exprMember.evaluate(doc, XPathConstants.NODESET);
        LOGGER.info("Got {} nodes", nodes.getLength());
        int j=0;
        while (j<nodes.getLength())
        {
            wpt=new Waypoint();
            
            wpt.name     = (String)exprNameInt.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            if (wpt.name.length()==0)
            {
                wpt.name     = (String)exprNameExt.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            }
            wpt. year    = (String)exprYear.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.xrd      = (String)exprXrd.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.yrd      = (String)exprYrd.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.gps      = (String)exprGps.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.blad     = (String)exprBlad.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.punt     = (String)exprPunt.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpt.station  = (String)exprStation.evaluate((Element)nodes.item(j), XPathConstants.STRING);
            wpts.add(wpt);
            j++;
        }
    }

    /**
     * Executes a HTTP request
     * @param getUrl The URL to request
     * @return The response as list of strings
     * @throws IOException 
     */
    private StringBuffer sendGet(String getUrl) throws IOException 
    {
        StringBuffer response = new StringBuffer();
        URL obj = new URL(getUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        LOGGER.info("GET Response Code :: {}", responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) 
        { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                response.append(inputLine);
            }
            in.close();
        } 
        else 
        {
            LOGGER.error("GET request not worked, got responsecode ", responseCode);
        }
        return response;
    }
    
    /**
     * Parse the XML into a document
     * @param xml XML string
     * @return The document
     * @throws Exception 
     */
    private Document parseXml(String xml) throws Exception
    {
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         dbFactory.setNamespaceAware(true);
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
         return doc;
    }    
    
    /**
     * Writes the waypoints to file in the Ozi wpt format, except that the
     * coordinates are writen as RD coordinates.
     * @throws Exception When something goes wrong
     */
    private void writeFile(List<Waypoint> wpts) throws Exception
    {
        MapDatumConvert     mdc         =new MapDatumConvert();
        FileWriter          myWriter    = new FileWriter("waypoints.wpt");
        DatumCoordinate     dc;
        LatLonCoordinate    llc;
        
        myWriter.write("OziExplorer Waypoint File Version 1.1\n");
        myWriter.write("WGS 84\n");
        myWriter.write("Reserved 2\n");
        myWriter.write("garmin\n");       
        
        int wptCount=1;
        for(Waypoint wpt : wpts)
        {
            dc=new DatumCoordinate();
            dc.easting=Double.parseDouble(wpt.xrd);
            dc.northing=Double.parseDouble(wpt.yrd);
            llc=mdc.rdToWgs84(dc);
            
            String name=wpt.name.replace(",", ":")+" - "+wpt.year;
            
            myWriter.write(Integer.toString(wptCount)+","+name+","+llc.phi+","+llc.lambda+",44848.7500,");
            if (wpt.gps.equals("1"))
            {
                myWriter.write("165");
            }
            else
            {
                myWriter.write("167");
            }
            myWriter.write(",0,3,0,65535,,0,0,0,-777,6,0,17,0,10,2,,,,120\n"); 
            LOGGER.debug("{}: [{}, {}], ({},{})", 
                       name, dc.easting, dc.northing, llc.phi, llc.lambda);
            wptCount++;
        }
        
        myWriter.flush();
        myWriter.close();        
    }
    
    /**
     * Find the stations based on p.blad and p.punt, which appears to be the 
     * link between stations en punten
     * @param stations Full list of stations
     * @param point Specific point
     * @return List of stations that correspond to a point
     */
    private List<Waypoint> getPointStations(List<Waypoint> stations, Waypoint point)
    {
        List<Waypoint> pointStations=new  ArrayList<>();
        
        String blad=point.blad;
        for(Waypoint s : stations)
        {
            if (s.blad.equals(point.blad) && s.punt.equals(point.punt))
            {
                pointStations.add(s);
            }
        }
        pointStations.sort((a,b) -> a.station.compareTo(b.station));
        return pointStations;        
    }
    
    /**
     * 
     * @param points
     * @param stations
     * @return 
     */
    private List<Waypoint> combineDataSets(List<Waypoint> points, List<Waypoint> stations)
    {
        List<Waypoint> waypoints=new  ArrayList<>();

        for(Waypoint p : points)
        {
            Waypoint newPoint   =new Waypoint();
            newPoint.name       =p.name;
            
            newPoint.gps        =p.gps;
            List<Waypoint> waypointStations=getPointStations(stations, p);
            newPoint.xrd        =waypointStations.get(0).xrd;
            newPoint.yrd        =waypointStations.get(0).yrd;
            newPoint.name       += " - "+waypointStations.get(0).name;
            newPoint.year       =waypointStations.get(0).year;
            waypoints.add(newPoint);
        }
        return waypoints;
    }
    
    
    
    /**
     * The main function: downloads the RD Info and parses the responss.
     */
    public void downloadAndParse()
    {
        List<Waypoint>  points;
        List<Waypoint>  stations;
        StringBuffer    response;
        Document        doc;
        NodeList        list;
        String          url;
        NamespaceResolver  nsc;
        
        try
        {
            // The 'punten' datasets contain the list of reference points. However, the coordinates
            // associated with a point is the average coordinate of the stations making up the reference point.
            // Refer to: https://www.nsgi.nl/geodetische-infrastructuur/referentiestelsels/rdinfo
            url="https://service.pdok.nl/kadaster/rdinfo/wfs/v1_0?request=GetFeature&service=WFS&version=2.0.0&typenames=punten";
            points=new ArrayList<>();
            while (url!=null && !url.equals(""))
            {
                response=sendGet(url);
                doc=parseXml(response.toString());
                LOGGER.info("Doc "+doc.getDocumentElement().getNodeName());
                LOGGER.info(url);
                url=doc.getDocumentElement().getAttribute("next");
                parseResponsePoints(doc, points);
            }

            // Get the full list of stations
            url="https://service.pdok.nl/kadaster/rdinfo/wfs/v1_0?request=GetFeature&service=WFS&version=2.0.0&typenames=stations";
            stations=new ArrayList<>();
            while (url!=null && !url.equals(""))
            {
                response=sendGet(url);
                doc=parseXml(response.toString());
                LOGGER.info("Doc {}", doc.getDocumentElement().getNodeName());
                LOGGER.info(url);
                url=doc.getDocumentElement().getAttribute("next");
                parseResponseStation(doc, stations);
            }
            
            // Now find add the coordinates of the station with the least station number
            List<Waypoint> newPoints=combineDataSets(points, stations);
            writeFile(newPoints);

        }
        catch (Exception e)
        {
            System.err.println("ERROR "+e.getMessage());
        }
        
    }
}
