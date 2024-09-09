//package com.example.webbaziraniproekt.web;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/graph")
//public class GraphController {
//
//    @Autowired
//    private RdfService rdfService;
//
//    @GetMapping("/view")
//    public String getGraph(Model model) {
//        String rdfData = rdfService.getGraphData();
//
//        // Here, you would process the RDF data and transform it into a format suitable for the graph visualization library
//        // For simplicity, we'll just send the RDF data to the view
//
//        model.addAttribute("rdfData", rdfData);
//
//        return "graph-view";
//    }
//}
