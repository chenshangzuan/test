package algorithm.jung;

import edu.uci.ics.jung.algorithms.shortestpath.ShortestPath;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Transformer;

import java.util.*;

/**
 * @author: Kled
 * @version: MyDijkstraShortestPath.java, v0.1 2020-10-14 22:44 Kled
 */
public class MyDijkstraShortestPath<V,E> extends MyDijkstraDistance<V,E> implements ShortestPath<V, E> {
    public MyDijkstraShortestPath(Graph<V, E> g, Transformer<E, ? extends Number> nev, boolean cached) {
        super(g, nev, cached);
    }

    public MyDijkstraShortestPath(Graph<V, E> g, Transformer<E, ? extends Number> nev) {
        super(g, nev);
    }

    public MyDijkstraShortestPath(Graph<V, E> g) {
        super(g);
    }

    public MyDijkstraShortestPath(Graph<V, E> g, boolean cached) {
        super(g, cached);
    }

    protected MyDijkstraDistance<V, E>.SourceData getSourceData(V source) {
        MyDijkstraDistance<V, E>.SourceData sd = this.sourceMap.get(source);
        if (sd == null) {
            sd = new MyDijkstraShortestPath.SourcePathData(source);
        }

        return sd;
    }

    public E getIncomingEdge(V source, V target) {
        if (!this.g.containsVertex(source)) {
            throw new IllegalArgumentException("Specified source vertex " + source + " is not part of graph " + this.g);
        } else if (!this.g.containsVertex(target)) {
            throw new IllegalArgumentException("Specified target vertex " + target + " is not part of graph " + this.g);
        } else {
            Set<V> targets = new HashSet();
            targets.add(target);
            this.singleSourceShortestPath(source, targets, this.g.getVertexCount());
            Map<V, E> incomingEdgeMap = ((MyDijkstraShortestPath.SourcePathData)this.sourceMap.get(source)).incomingEdges;
            E incomingEdge = incomingEdgeMap.get(target);
            if (!this.cached) {
                this.reset(source);
            }

            return incomingEdge;
        }
    }

    public Map<V, E> getIncomingEdgeMap(V source) {
        return this.getIncomingEdgeMap(source, this.g.getVertexCount());
    }

    public List<E> getPath(V source, V target) {
        if (!this.g.containsVertex(source)) {
            throw new IllegalArgumentException("Specified source vertex " + source + " is not part of graph " + this.g);
        } else if (!this.g.containsVertex(target)) {
            throw new IllegalArgumentException("Specified target vertex " + target + " is not part of graph " + this.g);
        } else {
            LinkedList<E> path = new LinkedList();
            Set<V> targets = new HashSet();
            targets.add(target);
            this.singleSourceShortestPath(source, targets, this.g.getVertexCount());
            Map<V, E> incomingEdges = ((MyDijkstraShortestPath.SourcePathData)this.sourceMap.get(source)).incomingEdges;
            if (!incomingEdges.isEmpty() && incomingEdges.get(target) != null) {
                Object incoming;
                for(Object current = target; !current.equals(source); current = ((Graph)this.g).getOpposite(current, incoming)) {
                    incoming = incomingEdges.get(current);
                    path.addFirst((E) incoming);
                }

                return path;
            } else {
                return path;
            }
        }
    }

    public LinkedHashMap<V, E> getIncomingEdgeMap(V source, int numDests) {
        if (!this.g.getVertices().contains(source)) {
            throw new IllegalArgumentException("Specified source vertex " + source + " is not part of graph " + this.g);
        } else if (numDests >= 1 && numDests <= this.g.getVertexCount()) {
            this.singleSourceShortestPath(source, (Collection)null, numDests);
            LinkedHashMap<V, E> incomingEdgeMap = ((MyDijkstraShortestPath.SourcePathData)this.sourceMap.get(source)).incomingEdges;
            if (!this.cached) {
                this.reset(source);
            }

            return incomingEdgeMap;
        } else {
            throw new IllegalArgumentException("numDests must be >= 1 and <= g.numVertices()");
        }
    }

    protected class SourcePathData extends SourceData {
        protected Map<V, E> tentativeIncomingEdges = new HashMap();
        protected LinkedHashMap<V, E> incomingEdges = new LinkedHashMap();

        protected SourcePathData(V source) {
            super(source);
        }

        @Override
        public void update(V dest, E tentative_edge, double new_dist) {
            super.update(dest, tentative_edge, new_dist);
            this.tentativeIncomingEdges.put(dest, tentative_edge);
        }

        @Override
        public Map.Entry<V, Number> getNextVertex() {
            Map.Entry<V, Number> p = super.getNextVertex();
            V v = p.getKey();
            E incoming = this.tentativeIncomingEdges.remove(v);
            this.incomingEdges.put(v, incoming);
            return p;
        }

        @Override
        public void restoreVertex(V v, double dist) {
            super.restoreVertex(v, dist);
            E incoming = this.incomingEdges.get(v);
            this.tentativeIncomingEdges.put(v, incoming);
        }

        @Override
        public void createRecord(V w, E e, double new_dist) {
            super.createRecord(w, e, new_dist);
            this.tentativeIncomingEdges.put(w, e);
        }
    }
}
