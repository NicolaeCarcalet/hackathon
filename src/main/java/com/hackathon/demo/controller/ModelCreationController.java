package com.hackathon.demo.controller;

import com.hackathon.demo.service.DbPediaSparqlService;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModelCreationController {

    @Autowired
    DbPediaSparqlService dbPediaSparqlService;

    @PostMapping(name = "/generate")
    public void generateMusicRdfModel() {
        Model rockMusicModel = buildModel("Rock_music");
        Model countryMusicModel = buildModel("Country_music");
        Model popMusicMusicModel = buildModel("Pop_music");

        Model fullModel = rockMusicModel.union(countryMusicModel).union(popMusicMusicModel);
        RDFDataMgr.write(System.out, fullModel, Lang.TURTLE);
    }

    private Model buildModel(String musicCategory) {
        Resource musicCategoryResource = ResourceFactory.createResource("http://somethign.org/" + musicCategory);
        List<Resource> subcategories = dbPediaSparqlService.getMusicSubCategories(musicCategory);
        List<Resource> mainInfluences = dbPediaSparqlService.getMainInfluences(musicCategory);
        List<Resource> representativeBands = dbPediaSparqlService.getRepresentativeBands(musicCategory);
        List<Resource> representativeMusicArtist = dbPediaSparqlService.getRepresentativeMusicArtist(musicCategory);
        Model model = ModelFactory.createDefaultModel();
        Property subCategory = model.createProperty("http://music.org/core#subCategory");
        Property influencedBy = model.createProperty("http://music.org/core#influencedBy");
        Property mainBand = model.createProperty("http://music.org/core#mainBand");
        Property mainArtist = model.createProperty("http://music.org/core#mainArtist");
        for (Resource subcategory : subcategories) {
            model.add(musicCategoryResource, subCategory, subcategory);
        }
        for (Resource mainInfluence : mainInfluences) {
            model.add(musicCategoryResource, influencedBy, mainInfluence);
        }
        for (Resource representativeBand : representativeBands) {
            model.add(musicCategoryResource, mainBand, representativeBand);
        }
        for (Resource resource : representativeMusicArtist) {
            model.add(musicCategoryResource, mainArtist, resource);
        }
        return model;
    }
}
