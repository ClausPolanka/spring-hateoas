package com.msg.xt.spring.hateoas.entity;
/*
**  Spring Hateoas Samples 
**  Design and Development by msg Applied Technology Research
**  Copyright (c) 2013 msg systems ag (http://www.msg-systems.com/)
**  All Rights Reserved.
*/
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * JavaMagazine Entity Class 
 * 
 * @author @author Michael Schäfer, Peter Huber 
 * 
 * */
@Entity
public class JavaMagazine extends AbstractEntity {

	private String name;

	public JavaMagazine() {
	}

	public JavaMagazine(String name) {
		this.name=name;
	}

	@OneToMany(cascade=CascadeType.ALL)
	private Set<Article> articles = new HashSet<Article>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public void add(Article newArticle) {
		articles.add(newArticle);
	}
}
