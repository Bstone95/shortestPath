import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShortestPathFinder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        String fileName = scanner.nextLine();
        try {
            WeightedGraph<Integer> graph = readGraphFromFile(fileName);
            scanner.nextLine(); // Consume the newline character left by nextLine()

            System.out.println("The number of vertices is " + graph.getSize());
            graph.printWeightedEdges();

            System.out.print("Enter two vertices (integer indexes): ");
            int v1 = scanner.nextInt();
            int v2 = scanner.nextInt();

            WeightedGraph<Integer>.ShortestPathTree shortestPathTree = graph.getShortestPath(v1);
            List<Integer> path = shortestPathTree.getPath(v2);

            System.out.print("A path from " + v1 + " to " + v2 + ": ");
            for (Integer vertex : path) {
                System.out.print(vertex + " ");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while processing the graph: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static WeightedGraph<Integer> readGraphFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner fileScanner = new Scanner(file);

        int numVertices = Integer.parseInt(fileScanner.nextLine());
        Integer[] vertices = new Integer[numVertices];
        List<WeightedEdge> edges = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            vertices[i] = i;
        }

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] triplets = line.split(",\\s+");
            int u = Integer.parseInt(triplets[0]);
            for (int i = 1; i < triplets.length; i += 3) {
                int v = Integer.parseInt(triplets[i]);
                int weight = Integer.parseInt(triplets[i + 2]);
                edges.add(new WeightedEdge(u, v, weight));
                edges.add(new WeightedEdge(v, u, weight)); // Add the reverse edge (undirected graph assumption)
            }
        }

        fileScanner.close();
        return new WeightedGraph<>(vertices, edges);
    }
}