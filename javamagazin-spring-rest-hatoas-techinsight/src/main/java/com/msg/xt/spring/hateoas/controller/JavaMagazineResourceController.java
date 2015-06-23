package com.msg.xt.spring.hateoas.controller;
/*
**  Spring Hateoas Samples 
**  Design and Development by msg Applied Technology Research
**  Copyright (c) 2013 msg systems ag (http://www.msg-systems.com/)
**  All Rights Reserved.
*/
import com.msg.xt.spring.hateoas.entity.JavaMagazine;
import com.msg.xt.spring.hateoas.repository.JavaMagazineRepository;
import com.msg.xt.spring.hateoas.resource.JavaMagazinePageResourceAssembler;
import com.msg.xt.spring.hateoas.resource.JavaMagazineResource;
import com.msg.xt.spring.hateoas.resource.JavaMagazineResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
/**
 * REST Interface für die JavaMagazine Resource
 *
 * @author Michael Schäfer, Peter Huber
 */
@RestController
@RequestMapping("javaMagazines")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class JavaMagazineResourceController {

    @Autowired
    private JavaMagazineRepository repo;

    @Autowired
    private JavaMagazineResourceAssembler resourceAssembler;

    @Autowired
    private JavaMagazinePageResourceAssembler pageAssembler;

    @RequestMapping("{id}")
    public ResponseEntity<JavaMagazine> get(@PathVariable Long id) {
        JavaMagazine jm = repo.findOne(id);
        return new ResponseEntity<JavaMagazine>(jm, HttpStatus.OK);
    }

    @RequestMapping("hateoas/{id}")
    public ResponseEntity<JavaMagazineResource> getHateoas(@PathVariable Long id) {
        JavaMagazine jm = repo.findOne(id);
        JavaMagazineResource jmResource = new JavaMagazineResource(jm);
        return new ResponseEntity<JavaMagazineResource>(jmResource, HttpStatus.OK);
    }

    @RequestMapping("hateoas/relations/{id}")
    public ResponseEntity<JavaMagazineResource> getHateoasRelations(@PathVariable Long id) {
        JavaMagazine jm = repo.findOne(id);
        JavaMagazineResource resource = resourceAssembler.toResource(jm);
        return new ResponseEntity<JavaMagazineResource>(resource, HttpStatus.OK);
    }

    @RequestMapping("hateoas/relations")
    public ResponseEntity<PagedResources<JavaMagazineResource>> getHateoasRelationsPaging(
            @RequestParam Integer size,
            @RequestParam Integer page) {
        PageRequest request = new PageRequest(size, page);
        Page p = repo.findAll(request);
        PagedResources resource = pageAssembler.toResource(p, resourceAssembler);
        return new ResponseEntity<PagedResources<JavaMagazineResource>>(resource, HttpStatus.OK);
    }
}
