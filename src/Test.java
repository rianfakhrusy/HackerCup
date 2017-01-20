

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Vertek implements Comparable<Vertek>
{
    public final String name;
    public Edgy[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertek previous;
    public Vertek(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertek other)
    {
        return Double.compare(minDistance, other.minDistance);
    }

}


class Edgy
{
    public final Vertek target;
    public final double weight;
    public Edgy(Vertek argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class Test
{
    public static void computePaths(Vertek source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertek> vertexQueue = new PriorityQueue<Vertek>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
        Vertek u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edgy e : u.adjacencies)
            {
                Vertek v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
        if (distanceThroughU < v.minDistance) {
            vertexQueue.remove(v);

            v.minDistance = distanceThroughU ;
            v.previous = u;
            vertexQueue.add(v);
        }
            }
        }
    }

    public static List<Vertek> getShortestPathTo(Vertek target)
    {
        List<Vertek> path = new ArrayList<Vertek>();
        for (Vertek vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
        // mark all the vertices 
        
        Vertek[] v = new Vertek[11];
        for (int i=0;i<11;i++)
            v[i] = new Vertek(""+i);

        // set the edges and weight
        v[0].adjacencies = new Edgy[]{ new Edgy(v[6], 8) };
        v[1].adjacencies = new Edgy[]{ new Edgy(v[2], 11) };
        v[2].adjacencies = new Edgy[]{ new Edgy(v[1], 11) };
        v[3].adjacencies = new Edgy[]{ new Edgy(v[4], 23) };
        v[4].adjacencies = new Edgy[]{ new Edgy(v[7], 40) };
        v[5].adjacencies = new Edgy[]{ new Edgy(v[4], 25) };
        v[6].adjacencies = new Edgy[]{ new Edgy(v[9], 8) };
        v[7].adjacencies = new Edgy[]{ new Edgy(v[4], 40) };
        v[8].adjacencies = new Edgy[]{ new Edgy(v[10], 18) };
        v[9].adjacencies = new Edgy[]{ new Edgy(v[8], 15) };
        v[10].adjacencies = new Edgy[]{ new Edgy(v[8], 18) };
        
        for (int i=0;i<11;i++)
            for (int j=0;j<11;j++)
                System.out.println(v[i].adjacencies[j].weight);

        computePaths(v[0]); // run Dijkstra
        System.out.println("Distance to " + v[10] + ": " + v[10].minDistance);
        //List<Vertek> path = getShortestPathTo(v[10]);
        //System.out.println("Path: " + path);
    }
}
