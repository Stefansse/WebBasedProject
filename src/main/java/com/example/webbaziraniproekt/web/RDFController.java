package com.example.webbaziraniproekt.web;

import com.example.webbaziraniproekt.model.Athlete;
import com.example.webbaziraniproekt.repository.AthleteRepository;
import com.example.webbaziraniproekt.service.RDFGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.StringWriter;
import java.util.List;

@Controller
public class RDFController {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private RDFGraphService rdfGraphService;

    @GetMapping("/rdf-data")
    public String getRDFData(Model model) {
        // Create RDF model using fully qualified name
        org.apache.jena.rdf.model.Model rdfModel = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();
        String baseURI = "http://example.org/athletes/";
        String namespace = "http://example.org/ontology/";
        org.apache.jena.rdf.model.Property nameProp = rdfModel.createProperty(namespace, "name");
        org.apache.jena.rdf.model.Property careerGoalsProp = rdfModel.createProperty(namespace, "careerGoals");
        org.apache.jena.rdf.model.Property careerMatchesProp = rdfModel.createProperty(namespace, "careerMatches");

        List<Athlete> athletes = athleteRepository.findAll();

        // Add data to RDF model
        for (Athlete athlete : athletes) {
            addAthleteData(rdfModel, baseURI, nameProp, careerGoalsProp, careerMatchesProp,
                    athlete.getId().toString(), athlete.getName(), athlete.getCareerGoals().toString(), athlete.getCareerMatches().toString());
        }

        // Serialize RDF model to RDF/XML format
        StringWriter rdfWriter = new StringWriter();
        org.apache.jena.riot.RDFDataMgr.write(rdfWriter, rdfModel, org.apache.jena.riot.RDFFormat.RDFXML);
        String rdfData = rdfWriter.toString();

        // Add RDF data to the model for the view
        model.addAttribute("rdfData", rdfData);
        return "rdf-view";
    }

    private void addAthleteData(org.apache.jena.rdf.model.Model rdfModel, String baseURI,
                                org.apache.jena.rdf.model.Property nameProp,
                                org.apache.jena.rdf.model.Property careerGoalsProp,
                                org.apache.jena.rdf.model.Property careerMatchesProp,
                                String id, String name, String careerGoals, String careerMatches) {
        org.apache.jena.rdf.model.Resource athlete = rdfModel.createResource(baseURI + id);
        athlete.addProperty(nameProp, name)
                .addProperty(careerGoalsProp, careerGoals)
                .addProperty(careerMatchesProp, careerMatches);
    }

    @GetMapping("/rdf-graph")
    public String getRDFGraphData(Model model) {
        // Create RDF model using fully qualified name
        org.apache.jena.rdf.model.Model rdfModel = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();
        String baseURI = "http://example.org/athletes/";
        String namespace = "http://example.org/ontology/";
        org.apache.jena.rdf.model.Property nameProp = rdfModel.createProperty(namespace, "name");
        org.apache.jena.rdf.model.Property careerGoalsProp = rdfModel.createProperty(namespace, "careerGoals");
        org.apache.jena.rdf.model.Property careerMatchesProp = rdfModel.createProperty(namespace, "careerMatches");

        List<Athlete> athletes = athleteRepository.findAll();

        // Add data to RDF model
        for (Athlete athlete : athletes) {
            addAthleteData(rdfModel, baseURI, nameProp, careerGoalsProp, careerMatchesProp,
                    athlete.getId().toString(), athlete.getName(), athlete.getCareerGoals().toString(), athlete.getCareerMatches().toString());
        }

        // Serialize RDF model to JSON-LD format for graph visualization
        StringWriter jsonLdWriter = new StringWriter();
        org.apache.jena.riot.RDFDataMgr.write(jsonLdWriter, rdfModel, org.apache.jena.riot.RDFFormat.JSONLD);
        String rdfJsonLdData = jsonLdWriter.toString();

        // Pass the JSON-LD RDF data to the model for graph visualization
        model.addAttribute("rdfJsonLdData", rdfJsonLdData);
        return "rdf-graph-view";
    }





}
