package com.example.webbaziraniproekt.service;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparqlService {

    private static final String SPARQL_ENDPOINT = "https://query.wikidata.org/sparql";

    public ResultSet executeQuery(String query) {
        try {
            QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT, query);
            return queryExecution.execSelect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing SPARQL query", e);
        }
    }
}