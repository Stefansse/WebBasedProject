package com.example.webbaziraniproekt.service;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RDFGraphService {

    /**
     * Converts the RDF model into a list of nodes and edges suitable for graph visualization tools like Cytoscape.js.
     *
     * @param rdfModel The RDF model containing data about athletes.
     * @return A list of nodes and edges in JSON format for Cytoscape.js.
     */
    public List<Map<String, Object>> convertRDFToGraphData(Model rdfModel) {
        List<Map<String, Object>> nodesAndEdges = new ArrayList<>();

        // SPARQL query to extract triples from RDF model
        String sparqlQuery = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";
        try (QueryExecution qe = QueryExecutionFactory.create(sparqlQuery, rdfModel)) {
            ResultSet results = qe.execSelect();

            // Process each RDF triple to generate nodes and edges
            while (results.hasNext()) {
                QuerySolution solution = results.next();
                Resource subject = solution.getResource("subject");
                Resource object = solution.getResource("object");

                // Create nodes for subject and object if they haven't been added already
                Map<String, Object> subjectNode = new HashMap<>();
                subjectNode.put("data", Map.of("id", subject.toString(), "label", subject.getLocalName()));
                nodesAndEdges.add(subjectNode);

                Map<String, Object> objectNode = new HashMap<>();
                objectNode.put("data", Map.of("id", object.toString(), "label", object.getLocalName()));
                nodesAndEdges.add(objectNode);

                // Create an edge between the subject and object
                Map<String, Object> edge = new HashMap<>();
                edge.put("data", Map.of(
                        "source", subject.toString(),
                        "target", object.toString(),
                        "label", solution.getResource("predicate").getLocalName()));
                nodesAndEdges.add(edge);
            }
        }

        return nodesAndEdges;
    }
}
