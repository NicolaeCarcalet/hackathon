package com.hackathon.demo.controller;

import com.hackathon.demo.service.DbPediaSparqlService;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class SparqlController {

    @Autowired
    DbPediaSparqlService dbPediaSparqlService;

    @PostMapping("nameCheck")
    public List<String> getData(@RequestBody String name) {
        return dbPediaSparqlService.getPersonsWithSameName(name);
    }

    @PostMapping("dataCheck")
    public List<String> getBandsAndArtists(@RequestBody String musicType) {
        List<Resource> representativeMusicArtist = dbPediaSparqlService.getRepresentativeMusicArtist(musicType);
        List<Resource> representativeBands = dbPediaSparqlService.getRepresentativeBands(musicType);
        List<String> bands = representativeBands
                .stream()
                .filter(Objects::nonNull)
                .map(Resource::getURI)
                .collect(Collectors.toList());
        List<String> musicArtists = representativeMusicArtist
                .stream()
                .filter(Objects::nonNull)
                .map(Resource::getURI)
                .collect(Collectors.toList());
        bands.addAll(musicArtists);
        return bands;
    }
}