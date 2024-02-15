import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename) {
        // Read the XML file and fill the instance variables locationMap, locations and trails.
        // TODO: Your code here


        try
        {
            //creating a constructor of file class and parsing an XML file
            File file = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//dbf
            DocumentBuilder builder = factory.newDocumentBuilder();//db

            Document document = builder.parse(file);//doc

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("Location");
            // nodeList is not iterable, so we are using for loop
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element location = (Element) nodeList.item(i);
                if (location.getNodeType() == Node.ELEMENT_NODE) {
                    String name = location.getElementsByTagName("Name").item(0).getTextContent();
                    int id = Integer.parseInt(location.getElementsByTagName("Id").item(0).getTextContent());
                    Location location1 = new Location(name, id);
                    locations.add(location1);
                    locationMap.put(id, location1);
                }
            }
            NodeList nodeList1 = document.getElementsByTagName("Trail");

            for (int itr = 0; itr < nodeList1.getLength(); itr++)
            {
                Node node = nodeList1.item(itr);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    int source = Integer.parseInt(eElement.getElementsByTagName("Source").item(0).getTextContent());
                    int destination = Integer.parseInt(eElement.getElementsByTagName("Destination").item(0).getTextContent());
                    int danger = Integer.parseInt(eElement.getElementsByTagName("Danger").item(0).getTextContent());

                    ArrayList<Location> loc = new ArrayList<>();
                    for(Location location : locations) {
                        if (location.id == source) {
                            loc.add(location);

                        }
                    }
                    for(Location location : locations) {
                        if(location.id == destination){
                            loc.add(location);

                        }
                    }
                    Trail trail = new Trail(loc.get(0),loc.get(1),danger);
                    trails.add(trail);

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        // Fill the safestTrail list and return it.
        // Select the optimal Trails from the Trail list that you have read.
        // TODO: Your code here


        List<Trail> allTrails = new ArrayList<>(trails);
        Collections.sort(allTrails, Comparator.comparingInt(t -> t.danger));


        Map<Location, Location> parent = new HashMap<>();
        for (Location location : locations) {
            parent.put(location, location);
        }


        for (Trail trail : allTrails) {
            Location sourceRoot = findRoot(trail.source, parent);
            Location destinationRoot = findRoot(trail.destination, parent);
            if (sourceRoot != destinationRoot) {

                safestTrails.add(trail);
                parent.put(sourceRoot, destinationRoot);
            }
        }

        return safestTrails;
    }

    private Location findRoot(Location location, Map<Location, Location> p) {

        Location root = location;
        while (root != p.get(root)) {
            root = p.get(root);
        }

        while (location != root) {
            Location next = p.get(location);
            p.put(location, root);
            location = next;
        }

        return root;
    }

    public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        // TODO: Your code here


        int total = 0;
        System.out.println("Safest trails are:");
        for(Trail trail:safestTrails){
            total+=trail.danger;
            System.out.println("The trail from " +trail.source.name+" to "+trail.destination.name+" with danger "+trail.danger);
        }
        System.out.println("Total danger: "+total);
    }
}
