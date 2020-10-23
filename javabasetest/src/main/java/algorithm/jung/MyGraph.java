package algorithm.jung;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Kled
 * @version: MyGraph.java, v0.1 2020-10-14 14:20 Kled
 */
public class MyGraph<V, E> extends AbstractGraph<V, E> {

    private Map<V, Pair<Set<E>>> vertices;     // Map of vertices to Pair of adjacency sets {inComing, outComing}

    private Map<E, Pair<V>>      edges;        // Map of edges to incident vertex pairs

    private Set<E>               directedEdges;

    public MyGraph() {
        vertices = Maps.newHashMap();
        edges = Maps.newHashMap();
        directedEdges = Sets.newHashSet();
    }

    @Override
    public boolean addEdge(E e, Pair<? extends V> pair, EdgeType edgeType) {
        Pair<V> new_endpoints = getValidatedEndpoints(e, pair);
        if (new_endpoints == null)
            return false;

        V v1 = new_endpoints.getFirst();
        V v2 = new_endpoints.getSecond();

        if (!vertices.containsKey(v1))
            this.addVertex(v1);

        if (!vertices.containsKey(v2))
            this.addVertex(v2);

        vertices.get(v1).getSecond().add(e);
        vertices.get(v2).getFirst().add(e);
        edges.put(e, new_endpoints);
        if (edgeType == EdgeType.DIRECTED) {
            directedEdges.add(e);
        } else {
            vertices.get(v1).getFirst().add(e);
            vertices.get(v2).getSecond().add(e);
        }
        return true;
    }

    @Override
    public Collection<E> getEdges() {
        return Collections.unmodifiableCollection(edges.keySet());
    }

    @Override
    public Collection<V> getVertices() {
        return Collections.unmodifiableCollection(vertices.keySet());
    }

    @Override
    public boolean containsVertex(V v) {
        return vertices.keySet().contains(v);
    }

    @Override
    public boolean containsEdge(E e) {
        return edges.keySet().contains(e);
    }

    @Override
    public int getEdgeCount() {
        return edges.size();
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    @Override
    public Collection<V> getNeighbors(V v) {
        if (!containsVertex(v)){
            return Collections.EMPTY_SET;
        }
        Collection<V> neighbors = new HashSet<>();
        neighbors.addAll(this.getPredecessors(v));
        neighbors.addAll(this.getSuccessors(v));
        return neighbors;
    }

    @Override
    public Collection<E> getIncidentEdges(V v) {
        if (!containsVertex(v)){
            return Collections.EMPTY_SET;
        }
        Collection<E> incidentEdges = new HashSet<>();
        incidentEdges.addAll(this.getInEdges(v));
        incidentEdges.addAll(this.getOutEdges(v));
        return incidentEdges;
    }

    @Override
    public boolean addVertex(V v) {
        vertices.putIfAbsent(v, new Pair<>(Sets.newHashSet(), Sets.newHashSet()));
        return true;
    }

    @Override
    public boolean removeVertex(V v) {
        vertices.remove(v);
        return true;
    }

    @Override
    public boolean removeEdge(E e) {
        if (!containsEdge(e)) {
            return false;
        }

        Pair<V> endpoints = getEndpoints(e);
        V v1 = endpoints.getFirst();
        V v2 = endpoints.getSecond();

        // remove edge from incident vertices' adjacency sets
        vertices.get(v1).getSecond().remove(e);
        vertices.get(v1).getFirst().remove(e);

        vertices.get(v2).getFirst().remove(e);
        vertices.get(v2).getSecond().remove(e);

        directedEdges.remove(e);
        edges.remove(e);
        return true;
    }

    @Override
    public EdgeType getEdgeType(E e) {
        return directedEdges.contains(e) ? EdgeType.DIRECTED : EdgeType.UNDIRECTED;
    }

    @Override
    public EdgeType getDefaultEdgeType() {
        //默认无向
        return EdgeType.UNDIRECTED;
    }

    @Override
    public Collection<E> getEdges(EdgeType edgeType) {
        if (edgeType == EdgeType.DIRECTED) {
            return Collections.unmodifiableSet(this.directedEdges);
        } else if (edgeType == EdgeType.UNDIRECTED) {
            return getEdges();
        } else {
            return Collections.EMPTY_SET;
        }
    }

    @Override
    public int getEdgeCount(EdgeType edgeType) {
        return getEdges(edgeType).size();
    }

    @Override
    public Collection<E> getInEdges(V v) {
        if (!containsVertex(v)){
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableCollection(vertices.get(v).getFirst());
    }

    @Override
    public Collection<E> getOutEdges(V v) {
        if (!containsVertex(v)){
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableCollection(vertices.get(v).getSecond());
    }

    @Override
    public Collection<V> getPredecessors(V v) {
        if (!containsVertex(v))
            return Collections.EMPTY_SET;

        Set<V> predecessors = vertices.get(v).getFirst().stream().map(x -> getOpposite(v, x)).collect(Collectors.toSet());
        return Collections.unmodifiableCollection(predecessors);
    }

    @Override
    public Collection<V> getSuccessors(V v) {
        if (!containsVertex(v))
            return Collections.EMPTY_SET;

        Set<V> successors = vertices.get(v).getSecond().stream().map(x -> getOpposite(v, x)).collect(Collectors.toSet());
        return Collections.unmodifiableCollection(successors);
    }

    @Override
    public V getSource(E e) {
        if (directedEdges.contains(e)) {
            return this.getEndpoints(e).getFirst();
        }
        return null;
    }

    @Override
    public V getDest(E e) {
        if (directedEdges.contains(e)) {
            return this.getEndpoints(e).getSecond();
        }
        return null;
    }

    @Override
    public boolean isSource(V v, E e) {
        if (directedEdges.contains(e)) {
            return this.getEndpoints(e).getFirst().equals(v);
        }
        return false;
    }

    @Override
    public boolean isDest(V v, E e) {
        if (directedEdges.contains(e)) {
            return this.getEndpoints(e).getSecond().equals(v);
        }
        return false;
    }

    @Override
    public Pair<V> getEndpoints(E e) {
        return edges.get(e);
    }
}
