import java.util.*;
import java.io.*;

class Vertex {
        final private String id;
        final private String name;


        public Vertex(String id, String name) {
                this.id = id;
                this.name = name;
        }
        public String getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((id == null) ? 0 : id.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Vertex other = (Vertex) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

        @Override
        public String toString() {
                return name;
        }

}

class Edge  {
        private final String id;
        private final Vertex source;
        private final Vertex destination;
        private int weight;

        public Edge(String id, Vertex source, Vertex destination, int weight) {
                this.id = id;
                this.source = source;
                this.destination = destination;
                this.weight = weight;
        }

        public String getId() {
                return id;
        }
        public Vertex getDestination() {
                return destination;
        }

        public Vertex getSource() {
                return source;
        }
        public int getWeight() {
                return weight;
        }
        public void setWeight(int i) {
            this.weight = i;
        }

        @Override
        public String toString() {
                return source + " " + destination;
        }


}

class Graph {
        private final List<Vertex> vertexes;
        private final List<Edge> edges;

        public Graph(List<Vertex> vertexes, List<Edge> edges) {
                this.vertexes = vertexes;
                this.edges = edges;
        }

        public List<Vertex> getVertexes() {
                return vertexes;
        }

        public List<Edge> getEdges() {
                return edges;
        }
}

class DijkstraAlgorithm {

        private final List<Vertex> nodes;
        private final List<Edge> edges;
        private Set<Vertex> settledNodes;
        private Set<Vertex> unSettledNodes;
        private Map<Vertex, Vertex> predecessors;
        private Map<Vertex, Integer> distance;

        public DijkstraAlgorithm(Graph graph) {
                // create a copy of the array so that we can operate on this array
                this.nodes = new ArrayList<Vertex>(graph.getVertexes());
                this.edges = new ArrayList<Edge>(graph.getEdges());
        }

        public void execute(Vertex source) {
                settledNodes = new HashSet<Vertex>();
                unSettledNodes = new HashSet<Vertex>();
                distance = new HashMap<Vertex, Integer>();
                predecessors = new HashMap<Vertex, Vertex>();
                distance.put(source, 0);
                unSettledNodes.add(source);
                while (unSettledNodes.size() > 0) {
                        Vertex node = getMinimum(unSettledNodes);
                        settledNodes.add(node);
                        unSettledNodes.remove(node);
                        findMinimalDistances(node);
                }
        }

        public void findMinimalDistances(Vertex node) {
                List<Vertex> adjacentNodes = getNeighbors(node);
                for (Vertex target : adjacentNodes) {
                        if (getShortestDistance(target) > getShortestDistance(node)
                                        + getDistance(node, target)) {
                                distance.put(target, getShortestDistance(node)
                                                + getDistance(node, target));
                                predecessors.put(target, node);
                                unSettledNodes.add(target);
                        }
                }

        }

        public int getDistance(Vertex node, Vertex target) {
                for (Edge edge : edges) {
                        if (edge.getSource().equals(node)
                                        && edge.getDestination().equals(target)) {
                                return edge.getWeight();
                        }
                }
                throw new RuntimeException("Should not happen");
        }

        private List<Vertex> getNeighbors(Vertex node) {
                List<Vertex> neighbors = new ArrayList<Vertex>();
                for (Edge edge : edges) {
                        if (edge.getSource().equals(node)
                                        && !isSettled(edge.getDestination())) {
                                neighbors.add(edge.getDestination());
                        }
                }
                return neighbors;
        }

        public Vertex getMinimum(Set<Vertex> vertexes) {
                Vertex minimum = null;
                for (Vertex vertex : vertexes) {
                        if (minimum == null) {
                                minimum = vertex;
                        } else {
                                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                                        minimum = vertex;
                                }
                        }
                }
                return minimum;
        }

        private boolean isSettled(Vertex vertex) {
                return settledNodes.contains(vertex);
        }

        public int getShortestDistance(Vertex destination) {
                Integer d = distance.get(destination);
                if (d == null) {
                        return Integer.MAX_VALUE;
                } else {
                        return d;
                }
        }

        /*
         * This method returns the path from the source to the selected target and
         * NULL if no path exists
         */
        public LinkedList<Vertex> getPath(Vertex target) {
                LinkedList<Vertex> path = new LinkedList<Vertex>();
                Vertex step = target;
                // check if a path exists
                if (predecessors.get(step) == null) {
                        return null;
                }
                path.add(step);
                while (predecessors.get(step) != null) {
                        step = predecessors.get(step);
                        path.add(step);
                }
                // Put it into the correct order
                Collections.reverse(path);
                return path;
        }

}

public class MovingCompany{ 
    static String inname = "input.txt"; 
    static String outname = "output.txt"; 
    private static List<Vertex> nodes;
    private static List<Edge> edges;
    private static void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
    public static void main(String[] args){
        try{
            Scanner in = new Scanner(new BufferedReader(new FileReader(inname)));
            //Scanner in = new Scanner(System.in);
            BufferedWriter out = new BufferedWriter(new FileWriter(outname));
            int t = in.nextInt();
            in.nextLine();
            for (int cas = 1; cas <= t; cas++){
                int ans = 0;
                boolean end = false;
                int kota = in.nextInt();
                int road = in.nextInt();
                int dest = in.nextInt();
                //System.out.println(kota + " " + road + " " + dest);
                /*
                int[][] jarak = new int[kota][kota];
                for (int i=0;i<kota;i++)
                    for (int j=0;j<kota;j++)
                            jarak[i][j] = (i==j)?0:1001;
                
                for (int i = 0; i < road;i++){
                    int x = in.nextInt()-1;
                    int y = in.nextInt()-1;
                    int v = in.nextInt();
                    jarak[x][y] = v;
                    jarak[y][x] = v;
                }
                
                for (int i=0;i<kota;i++)
                    for (int j=0;j<kota;j++)
                        System.out.println(jarak[i][j]);*/
                nodes = new ArrayList<Vertex>();
                edges = new ArrayList<Edge>();
                
                for (int i = 0; i < kota; i++) {
                    Vertex location = new Vertex("" + i, "" + i);
                    nodes.add(location);
                }
                
                
                /*
                Vertex[] v = new Vertex[kota];
                for (int i=0;i<kota;i++)
                    v[i] = new Vertex(""+i);
                
                for (int i=0;i<kota;i++)
                    for (int j=0;j<kota;j++)
                        v[i].adjacencies = new Edge[]{new Edge(v[j],(i==j)?0:1001)};*/
                
                for (int i=0;i<road;i++){
                    int x = in.nextInt()-1;
                    int y = in.nextInt()-1;
                    int val = in.nextInt();
                    
                    int wong = -1;
                    int wang = -1;
                    //System.out.println(x + " " + y + " " + val);
                    for (int j=0;j<i;j++){
                        if ((Integer.parseInt(edges.get(j).getSource().getName())==x)&&
                                (Integer.parseInt(edges.get(j).getDestination().getName())==y)){
                            wong = j;
                            //System.out.println("wow");
                        }
                        if ((Integer.parseInt(edges.get(j).getSource().getName())==y)&&
                                (Integer.parseInt(edges.get(j).getDestination().getName())==x)){
                            wang = j;
                            //System.out.println("wow");
                        }
                    }
                    if (wong>-1){
                        if (val<edges.get(wong).getWeight()){
                            edges.get(wong).setWeight(val);
                            edges.get(wang).setWeight(val);
                            //System.out.println("newval set");
                        }
                    } else {
                        addLane(""+i, x, y, val);
                        addLane(""+i, y, x, val);
                    }
                    //v[x].adjacencies = new Edge[]{new Edge(v[y], val) };
                    //v[y].adjacencies = new Edge[]{new Edge(v[x], val) };
                }
                
                //for (int i=0;i<road*2;i++)
                //    System.out.println(edges.get(i).toString()+" "+edges.get(i).getWeight());
                
                //System.out.println(v[asal]);
                //computePaths(v[asal]);
                Graph graph = new Graph(nodes, edges);
                DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
                
                int[] town = new int[4];
                dijkstra.execute(nodes.get(0));
                for (int i=0;i<4;i++){
                    town[i] = in.nextInt()-1;
                    //System.out.print(town[i] + " ");
                }
                //System.out.println();
                ans += dijkstra.getShortestDistance(nodes.get(town[0])); 
                
                for (int i=0;i<dest;i++){
                    if ((town[0]==town[2])&&(town[1]==town[3])){
                        i++;
                    } else {
                        System.out.print(1+town[0] + " to ");
                        //computePaths(v[asal]);

                        dijkstra.execute(nodes.get(town[0]));

                        System.out.print(1+town[1] + " = ");
                        //System.out.println(v[asal].minDistance);
                        if (town[0]!=town[1]){
                            //ans += v[asal].minDistance;
                            List<Vertex> path = dijkstra.getPath(nodes.get(town[1]));/*
                            try {
                                for (int j=0;j<path.size();j++)
                                    System.out.print(path.get(j)+" ");
                                System.out.println();
                            } catch (Exception e){
                                ans = -1;
                                continue;
                            }  */
                            
                            boolean ada = false;
                            try {
                                for (int k=0;k<path.size();k++){
                                    int coba = Integer.parseInt(path.get(k).getName());
                                    if(town[2]==coba){
                                        ada = true;
                                    }
                                }
                            } catch (Exception e){
                                ans = -1;
                                end = true;
                                //continue;
                            }
                            
                            if (ada){
                                int temp = town[2];
                                town[2] = town[1];
                                town[1] = temp;
                                //System.out.println("Baa");
                            } //else System.out.println(path.toString() + " "+ town[2]);
                            /*
                            //boolean wang = false;
                            boolean wong = false;
                            try {
                                dijkstra.execute(nodes.get(town[2]));
                                List<Vertex> path3 = dijkstra.getPath(nodes.get(town[3]));
                                
                                for (int k=0;k<path3.size();k++){
                                    int coba = Integer.parseInt(path3.get(k).getName());
                                    if(town[0]==coba){
                                        wong = true;
                                    }
                                }
                            } catch (Exception e){
                               // ans = -1;
                                //end = true;
                                //continue;
                            }
                            if (wong){
                                int temp = town[2];
                                town[2] = town[1];
                                town[1] = temp;
                                //System.out.println("Baa");
                            } */
                            
                            boolean abra = false;
                            boolean kadabra = false;
                            try {
                                dijkstra.execute(nodes.get(town[2]));
                                List<Vertex> path2 = dijkstra.getPath(nodes.get(town[3]));
                                
                                for (int k=0;k<path2.size();k++){
                                    int coba = Integer.parseInt(path2.get(k).getName());
                                    if ((abra)&&(town[1]==coba))
                                        kadabra = true;
                                    if(town[0]==coba){
                                        abra = true;
                                    }
                                }
                            } catch (Exception e){
                               // ans = -1;
                                //end = true;
                                //continue;
                            }
                            if (kadabra){
                                int temp = town[0];
                                town[0] = town[2];
                                town[2] = town[1];
                                town[1] = temp;
                                //System.out.println("Baa");
                            } 
                            dijkstra.execute(nodes.get(town[0]));
                        }
                        if (!end)
                            ans += dijkstra.getShortestDistance(nodes.get(town[1])); 
                        System.out.println(ans);
                    }
                    if (town[2]!=-1){
                        int t5 = -1, t6 = -1;
                        if (i<dest-2){
                            t5 = in.nextInt()-1;
                            t6 = in.nextInt()-1;
                            //System.out.println(t5 + " " + t6);
                        }
                        System.out.print(1+town[1] + " to ");
                        //computePaths(v[asal]);

                        dijkstra.execute(nodes.get((int)town[1]));
                        
                        System.out.print(1+town[2] + " = ");
                        //System.out.println(v[asal].minDistance);
                        
                        if (town[1]!=town[2]){
                            LinkedList<Vertex> path = dijkstra.getPath(nodes.get(town[2]));
                            /*//try {
                                for (int j=0;j<path.size();j++)
                                    System.out.print(path.get(j)+" ");
                                System.out.println("wkwkw");
                            //} catch (Exception e){}*/
                            
                            //List<Vertex> path = dijkstra.getPath(nodes.get(town[2]));
                        }
                        
                        //ans += v[asal].minDistance;
                        if (!end)
                            ans += dijkstra.getShortestDistance(nodes.get(town[2])); 
                        System.out.println(ans);

                        town[0] = town[2];
                        town[1] = town[3];
                        town[2] = t5;
                        town[3] = t6;
                    }
                }
                    
                
                System.out.print("Case #" + cas + ": " + ans + "\n");
                //out.write("Case #" + cas + ": " + ans + "\n");
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
