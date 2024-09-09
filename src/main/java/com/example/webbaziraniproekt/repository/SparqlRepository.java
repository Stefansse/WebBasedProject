//package com.example.webbaziraniproekt.repository;
//
//import org.apache.jena.query.Query;
//import org.apache.jena.query.QueryExecution;
//import org.apache.jena.query.QueryExecutionFactory;
//import org.apache.jena.query.QueryFactory;
//import org.apache.jena.query.ResultSet;
//import org.apache.jena.query.ResultSetFormatter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class SparqlRepository {
//
//    @Value("${sparql.endpoint.url}")
//    private String sparqlEndpointUrl;
//
//    public String executeQuery(String sparqlQuery) {
//        Query query = QueryFactory.create(sparqlQuery);
//        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(sparqlEndpointUrl, query)) {
//            ResultSet results = queryExecution.execSelect();
//            return ResultSetFormatter.asText(results); // Return results as text, adjust as needed
//        }
//    }
//}