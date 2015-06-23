package com.msg.xt.spring.hateoas.controller;

/*
**  Spring Hateoas Samples 
**  Design and Development by msg Applied Technology Research
**  Copyright (c) 2013 msg systems ag (http://www.msg-systems.com/)
**  All Rights Reserved.
*/
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.msg.xt.spring.hateoas.entity.Article;
import com.msg.xt.spring.hateoas.entity.JavaMagazine;
import com.msg.xt.spring.hateoas.repository.ArticleRepository;
import com.msg.xt.spring.hateoas.repository.JavaMagazineRepository;
import com.msg.xt.spring.hateoas.resource.ArticleResource;
import com.msg.xt.spring.hateoas.resource.ArticleResourceAssembler;

/**
 * REST Interface für die Article Resource 
 * 
 * @author Michael Schäfer, Peter Huber
 */



@RestController
@RequestMapping("articles")
public class ArticleResourceController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleResourceAssembler articleResourceAssembler;

	@Autowired
	private JavaMagazineRepository javaMagazineRepository;

	

	@RequestMapping(value = "/javamagazine/{id}/articles", method = RequestMethod.GET)
	public ResponseEntity<Iterable<ArticleResource>> articleByJavaMagazine(@PathVariable Long id) {
		JavaMagazine javaMagazine = javaMagazineRepository.findOne(id);

		List<ArticleResource> articleResources = articleResourceAssembler
				.toResources(javaMagazine.getArticles());

		return new ResponseEntity(articleResources,HttpStatus.OK);

	}

	@RequestMapping(value = "/javamagazine/{mid}/articles/{aid}", method = RequestMethod.GET)
	public ResponseEntity<ArticleResource> articleByJavaMagazine(@PathVariable Long mid,
			@PathVariable Long aid) {
		JavaMagazine javaMagazine = javaMagazineRepository.findOne(mid);
		Set<Article> articles = javaMagazine.getArticles();
		for (Article article : articles) {
			if (aid.equals(article.getId())) {
				ArticleResource articleResources = articleResourceAssembler.toResource(article);
				return new ResponseEntity(articleResources,HttpStatus.OK);
			}
		}
		throw new ResourceAccessException(String.format(
				"Article Not Found; mag=%d; article=%d", mid, aid));
	}
}
