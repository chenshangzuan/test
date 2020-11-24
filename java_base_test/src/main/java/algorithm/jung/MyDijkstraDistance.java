package algorithm.jung;

import algorithm.jung.transformer.MyTransformer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.Distance;
import edu.uci.ics.jung.algorithms.util.BasicMapEntry;
import edu.uci.ics.jung.algorithms.util.MapBinaryHeap;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ConstantTransformer;

import java.util.*;

/**
 * @author: Kled
 * @version: MyDijkstraDistance.java, v0.1 2020-10-14 22:28 Kled
 */
public class MyDijkstraDistance<V, E> implements Distance<V> {
    protected Hypergraph<V, E> g;
    protected Transformer<E, ? extends Number> nev;
    protected Map<V, SourceData> sourceMap;
    protected boolean cached;
    protected double max_distance;
    protected int max_targets;

    public MyDijkstraDistance(Hypergraph<V, E> g, Transformer<E, ? extends Number> nev, boolean cached) {
        this.g = g;
        this.nev = nev;
        this.sourceMap = new HashMap();
        this.cached = cached;
        this.max_distance = 1.0D / 0.0;
        this.max_targets = 2147483647;
    }

    public MyDijkstraDistance(Hypergraph<V, E> g, Transformer<E, ? extends Number> nev) {
        this(g, nev, true);
    }

    public MyDijkstraDistance(Graph<V, E> g) {
        this(g, new ConstantTransformer(1), true);
    }

    public MyDijkstraDistance(Graph<V, E> g, boolean cached) {
        this(g, new ConstantTransformer(1), cached);
    }

    protected LinkedHashMap<V, Number> singleSourceShortestPath(V source, Collection<V> targets, int numDests) {
        SourceData sd = this.getSourceData(source);
        Set<V> to_get = new HashSet();
        if (targets != null) {
            to_get.addAll(targets);
            Set<V> existing_dists = sd.distances.keySet();
            Iterator i$ = targets.iterator();

            while(i$.hasNext()) {
                V o = (V) i$.next();
                if (existing_dists.contains(o)) {
                    to_get.remove(o);
                }
            }
        }

        if (!sd.reached_max && (targets == null || !to_get.isEmpty()) && sd.distances.size() < numDests) {
            while(!sd.unknownVertices.isEmpty() && (sd.distances.size() < numDests || !to_get.isEmpty())) {
                Map.Entry<V, Number> p = sd.getNextVertex();
                V v = p.getKey();
                double v_dist = ((Number)p.getValue()).doubleValue();
                to_get.remove(v);
                if (v_dist > this.max_distance) {
                    sd.restoreVertex(v, v_dist);
                    sd.reached_max = true;
                    break;
                }

                sd.dist_reached = v_dist;
                if (sd.distances.size() >= this.max_targets) {
                    sd.reached_max = true;
                    break;
                }

                Iterator i$ = this.getEdgesToCheck(v).iterator();

                while(i$.hasNext()) {
                    E e = (E) i$.next();
                    Iterator it = this.g.getIncidentVertices(e).iterator();

                    while(it.hasNext()) {
                        V w = (V) it.next();
                        if (!sd.distances.containsKey(w)) {
                            double new_dist;
                            if (nev instanceof MyTransformer){
                                new_dist = ((MyTransformer)nev).calculateNewDist(e, v_dist);
                            }else {
                                double edge_weight = ((Number)this.nev.transform(e)).doubleValue();
                                if (edge_weight < 0.0D) {
                                    throw new IllegalArgumentException("Edges weights must be non-negative");
                                }

                                new_dist = v_dist + edge_weight;
                            }
                            if (!sd.estimatedDistances.containsKey(w)) {
                                sd.createRecord(w, e, new_dist);
                            } else {
                                double w_dist = (Double)sd.estimatedDistances.get(w);
                                if (new_dist < w_dist) {
                                    sd.update(w, e, new_dist);
                                }
                            }
                        }
                    }
                }
            }

            return sd.distances;
        } else {
            return sd.distances;
        }
    }

    protected MyDijkstraDistance<V, E>.SourceData getSourceData(V source) {
        SourceData sd = this.sourceMap.get(source);
        if (sd == null) {
            sd = new MyDijkstraDistance.SourceData(source);
        }

        return sd;
    }

    protected Collection<E> getEdgesToCheck(V v) {
        return this.g instanceof Graph ? ((Graph)this.g).getOutEdges(v) : this.g.getIncidentEdges(v);
    }

    public Number getDistance(V source, V target) {
        if (!this.g.containsVertex(target)) {
            throw new IllegalArgumentException("Specified target vertex " + target + " is not part of graph " + this.g);
        } else if (!this.g.containsVertex(source)) {
            throw new IllegalArgumentException("Specified source vertex " + source + " is not part of graph " + this.g);
        } else {
            Set<V> targets = new HashSet();
            targets.add(target);
            Map<V, Number> distanceMap = this.getDistanceMap(source, targets);
            return (Number)distanceMap.get(target);
        }
    }

    public Map<V, Number> getDistanceMap(V source, Collection<V> targets) {
        if (!this.g.containsVertex(source)) {
            throw new IllegalArgumentException("Specified source vertex " + source + " is not part of graph " + this.g);
        } else if (targets.size() > this.max_targets) {
            throw new IllegalArgumentException("size of target set exceeds maximum number of targets allowed: " + this.max_targets);
        } else {
            Map<V, Number> distanceMap = this.singleSourceShortestPath(source, targets, Math.min(this.g.getVertexCount(), this.max_targets));
            if (!this.cached) {
                this.reset(source);
            }

            return distanceMap;
        }
    }

    public Map<V, Number> getDistanceMap(V source) {
        return this.getDistanceMap(source, Math.min(this.g.getVertexCount(), this.max_targets));
    }

    public LinkedHashMap<V, Number> getDistanceMap(V source, int numDests) {
        if (!this.g.getVertices().contains(source)) {
            throw new IllegalArgumentException("Specified source vertex " + source + " is not part of graph " + this.g);
        } else if (numDests >= 1 && numDests <= this.g.getVertexCount()) {
            if (numDests > this.max_targets) {
                throw new IllegalArgumentException("numDests must be <= the maximum number of targets allowed: " + this.max_targets);
            } else {
                LinkedHashMap<V, Number> distanceMap = this.singleSourceShortestPath(source, (Collection)null, numDests);
                if (!this.cached) {
                    this.reset(source);
                }

                return distanceMap;
            }
        } else {
            throw new IllegalArgumentException("numDests must be >= 1 and <= g.numVertices()");
        }
    }

    public void setMaxDistance(double max_dist) {
        this.max_distance = max_dist;

        SourceData sd;
        for(Iterator i$ = this.sourceMap.keySet().iterator(); i$.hasNext(); sd.reached_max = this.max_distance <= sd.dist_reached || sd.distances.size() >= this.max_targets) {
            V v = (V) i$.next();
            sd = this.sourceMap.get(v);
        }

    }

    public void setMaxTargets(int max_targets) {
        this.max_targets = max_targets;

        SourceData sd;
        for(Iterator i$ = this.sourceMap.keySet().iterator(); i$.hasNext(); sd.reached_max = this.max_distance <= sd.dist_reached || sd.distances.size() >= max_targets) {
            V v = (V) i$.next();
            sd = this.sourceMap.get(v);
        }

    }

    public void reset() {
        this.sourceMap = new HashMap();
    }

    public void enableCaching(boolean enable) {
        this.cached = enable;
    }

    public void reset(V source) {
        this.sourceMap.put(source, null);
    }

    protected class SourceData {
        protected LinkedHashMap<V, Number> distances = new LinkedHashMap();
        protected Map<V, Number> estimatedDistances = new HashMap();
        protected MapBinaryHeap<V> unknownVertices;
        protected boolean reached_max = false;
        protected double dist_reached = 0.0D;

        protected SourceData(V source) {
            this.unknownVertices = new MapBinaryHeap(new MyDijkstraDistance.VertexComparator(this.estimatedDistances));
            MyDijkstraDistance.this.sourceMap.put(source, this);
            this.estimatedDistances.put(source, new Double(0.0D));
            this.unknownVertices.add(source);
            this.reached_max = false;
            this.dist_reached = 0.0D;
        }

        protected Map.Entry<V, Number> getNextVertex() {
            V v = this.unknownVertices.remove();
            Double dist = (Double)this.estimatedDistances.remove(v);
            this.distances.put(v, dist);
            return new BasicMapEntry(v, dist);
        }

        protected void update(V dest, E tentative_edge, double new_dist) {
            this.estimatedDistances.put(dest, new_dist);
            this.unknownVertices.update(dest);
        }

        protected void createRecord(V w, E e, double new_dist) {
            this.estimatedDistances.put(w, new_dist);
            this.unknownVertices.add(w);
        }

        protected void restoreVertex(V v, double dist) {
            this.estimatedDistances.put(v, dist);
            this.unknownVertices.add(v);
            this.distances.remove(v);
        }
    }

    protected static class VertexComparator<V> implements Comparator<V> {
        private Map<V, Number> distances;

        protected VertexComparator(Map<V, Number> distances) {
            this.distances = distances;
        }

        @Override
        public int compare(V o1, V o2) {
            return ((Double)this.distances.get(o1)).compareTo((Double)this.distances.get(o2));
        }
    }
}
