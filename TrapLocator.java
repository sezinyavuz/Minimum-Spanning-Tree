import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }

    public List<List<Integer>> revealTraps() {

        // Trap positions for each colony, should contain an empty array if the colony is safe.
        // I.e.:
        // 0 -> [2, 15, 16, 31]
        // 1 -> [4, 13, 22]
        // 3 -> []
        // ...
        List<List<Integer>> traps = new ArrayList<>();

        for (Colony colony : colonies) {
            List<Integer> colonyTraps = new ArrayList<>();
            for (Map.Entry<Integer, List<Integer>> entry : colony.roadNetwork.entrySet()) {
                int city = entry.getKey();
                List<Integer> neighbors = entry.getValue();
                for (int neighbor : neighbors) {
                    List<Integer> visited = new ArrayList<>();
                    visited.add(city);
                    visited.add(neighbor);
                    findCycle(colony, neighbor, visited, colonyTraps);
                }
            }
            traps.add(colonyTraps);
        }

        return traps;
    }

    private void findCycle(Colony colony, int neighbor, List<Integer> visited, List<Integer> colonyTraps) {
        List<Integer> neighbors = colony.roadNetwork.get(neighbor);
        if (neighbors == null || neighbors.size() == 0) {
            return;
        }
        for (int n : neighbors) {
            if (visited.contains(n)) {
                if (visited.size() == 3 && visited.get(0) == n) {

                    colonyTraps.addAll(visited);
                    break;
                }
            } else {
                List<Integer> newVisited = new ArrayList<>(visited);
                newVisited.add(n);
                findCycle(colony, n, newVisited, colonyTraps);
            }
        }
    }


    public void printTraps(List<List<Integer>> traps) {
        // For each colony, if you have encountered a time trap, then print the cities that create the trap.
        // If you have not encountered a time trap in this colony, then print "Safe".
        // Print the your findings conforming to the given output format.
        // TODO: Your code here

        System.out.println("Danger exploration conclusions:");

        for (int i = 0; i < colonies.size(); i++) {
            //Colony colony = colonies.get(i);
            HashSet<Integer> trp =new HashSet();
            for (int j : traps.get(i)){
                trp.add(j);
            }
            List<Integer> colonyTraps =new ArrayList<>();
            for(int k:trp){
                colonyTraps.add(k);
            }
            Collections.sort(colonyTraps);
            if (colonyTraps.isEmpty()) {
                System.out.println("Colony " + (i + 1) + ": Safe");
            } else {
                System.out.println("Colony " + (i + 1) + ": Dangerous. Cities on the dangerous path: " + colonyTraps);
            }
        }
    }









}
