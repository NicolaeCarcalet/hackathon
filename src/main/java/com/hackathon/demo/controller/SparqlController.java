package com.hackathon.demo.controller;

import org.apache.jena.query.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SparqlController {

    @GetMapping
    public String getData() {
        String queryString = "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo:     <http://dbpedia.org/ontology/>" +
                "PREFIX dbr:     <http://dbpedia.org/resource/>" +
                "select ?s where {?s dbo:movement dbr:Impressionism}";
        Query query = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = exec.execSelect();
        StringBuilder stringBuilder = new StringBuilder();
        ResultSetFormatter.out(System.out, results, query);
        return stringBuilder.toString();
    }
}