package com.hackathon.demo.service;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbPediaSparqlService {

    public List<Resource> getMusicSubCategories(String musicCategory) {
        String queryStringTemplate = "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo:     <http://dbpedia.org/ontology/>" +
                "PREFIX dbr:     <http://dbpedia.org/resource/>" +
                "select * where {dbr:%s dbo:musicSubgenre ?o} ";
        Query query = QueryFactory.create(String.format(queryStringTemplate, musicCategory));
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = exec.execSelect();
        List<Resource> resources = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            resources.add(next.getResource("o"));
        }
        return resources;
    }

    public List<Resource> getMainInfluences(String musicCategory) {
        String queryStringTemplate = "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo:     <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbr:     <http://dbpedia.org/resource/>\n" +
                "select * where {dbr:%s dbo:stylisticOrigin ?o} ";
        Query query = QueryFactory.create(String.format(queryStringTemplate, musicCategory));
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = exec.execSelect();
        List<Resource> resources = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            resources.add(next.getResource("o"));
        }
        return resources;
    }

    public List<Resource> getRepresentativeMusicArtist(String musicCategory) {
        String queryStringTemplate = "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema/>\n" +
                "PREFIX dbo:     <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbr:     <http://dbpedia.org/resource/>\n" +
                "PREFIX rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#/>\n" +
                "select * where {?s dbo:genre dbr:%s; rdf:type dbo:MusicalArtist}  ";
        Query query = QueryFactory.create(String.format(queryStringTemplate, musicCategory));
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = exec.execSelect();
        List<Resource> resources = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            Resource resource = next.getResource("o");
            if (resource != null) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public List<Resource> getRepresentativeBands(String musicCategory) {
        String queryStringTemplate = "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo:     <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbr:     <http://dbpedia.org/resource/>\n" +
                "select * where {?s dbo:genre dbr:%s; <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> dbo:Band} ";
        Query query = QueryFactory.create(String.format(queryStringTemplate, musicCategory));
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = exec.execSelect();
        List<Resource> resources = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            Resource resource = next.getResource("o");
            if (resource != null) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public List<String> getPersonsWithSameName(String nameToken) {
        String queryStringTemplate = "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX dbo:     <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbr:     <http://dbpedia.org/resource/>\n" +
                "select * where {?s dbo:birthName ?o filter contains(?o, \"%s\")}\n";
        Query query = QueryFactory.create(String.format(queryStringTemplate, nameToken));
        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = exec.execSelect();
        List<String> resources = new ArrayList<>();
        while (results.hasNext()) {
            QuerySolution next = results.next();
            Resource resource = next.getResource("s");
            if (resource != null) {
                resources.add(resource.getURI());
            }
        }
        return resources;
    }
}
