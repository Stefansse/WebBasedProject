package com.example.webbaziraniproekt.service;

import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;
import org.apache.jena.rdf.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class SparqlToRDF {

    public static Model convertToRDF(String sparqlResultJson) {

        Model model = ModelFactory.createDefaultModel();


        InputStream inputStream = new ByteArrayInputStream(sparqlResultJson.getBytes(StandardCharsets.UTF_8));


        try {

            model.read(inputStream, null, "JSON-LD");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}