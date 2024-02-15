import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
public class Kingdom {

    // TODO: You should add appropriate instance variables.
    List<String[]> directedMatrix = new ArrayList<>();
    int undMatrix[][] ;
    HashSet<Integer> visited =new HashSet();

    public void initializeKingdom(String filename) {
        // Read the txt file and fill your instance variables
        // TODO: Your code here

        File file1 = new File(filename);
        Scanner scanner1 = null;

        try {
            scanner1 = new Scanner(file1);
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner1.hasNext()) {
            String[] line = scanner1.nextLine().split(" ");
            directedMatrix.add((line));

        }

        this.undMatrix = new int[directedMatrix.size()][directedMatrix.get(0).length];
        for(int i = 0; i< directedMatrix.size();i++){
            for(int j = 0 ; j< directedMatrix.get(i).length;j++){
                if(Integer.parseInt(directedMatrix.get(i)[j])==1){
                    undMatrix[i][j] = 1;
                    undMatrix[j][i] = 1;
                }
            }
            }
    }



    public void depthFirstSearch(int[][] matrix, Colony colony,int startvertex) {
        // Create a stack for DFS

        Stack<Integer> stack = new Stack<>();


        this.visited.add(startvertex);
        stack.push(startvertex);


        while (!stack.isEmpty()) {

            int currentVertex = stack.pop();
            colony.cities.add((currentVertex+1));

            int[] neighbors = matrix[currentVertex];


            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] == 1 && !visited.contains(i)) {
                    visited.add(i);
                    stack.push(i);
                }
            }
        }
    }


    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        // TODO: DON'T READ THE .TXT FILE HERE!
        // Identify the colonies using the given input file.
        // TODO: Your code here

        for(int i =0; i< undMatrix.length;i++){
            if(!visited.contains(i)){
                Colony colony = new Colony();
                depthFirstSearch(this.undMatrix,colony,i);
                colonies.add(colony);
                Collections.sort(colony.cities);

                for(int j :colony.cities){
                    List<Integer> adjacencyList = new ArrayList<>();
                    for (int k = 0;k< directedMatrix.size();k++){
                        if(Integer.parseInt(directedMatrix.get(j-1)[k])==1){
                            adjacencyList.add(k+1);
                        }

                    }
                    colony.roadNetwork.put(j,adjacencyList);


                }
            }
        }

        return colonies;
    }

    public void printColonies(List<Colony> discoveredColonies) {
        // Print the given list of discovered colonies conforming to the given output format.
        // TODO: Your code here

        int count = 0;
        System.out.println("Discovered colonies are:");

        for(Colony colony : discoveredColonies){
            count++;
            System.out.println("Colony "+count+": "+colony.cities);
        }

    }
}
