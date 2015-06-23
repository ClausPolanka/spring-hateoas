package com.msg.xt.spring.hateoas.resource;

/*
**  Spring Hateoas Samples 
**  Design and Development by msg Applied Technology Research
**  Copyright (c) 2013 msg systems ag (http://www.msg-systems.com/)
**  All Rights Reserved.
*/

import com.msg.xt.spring.hateoas.controller.ArticleResourceController;
import com.msg.xt.spring.hateoas.controller.JavaMagazineResourceController;
import com.msg.xt.spring.hateoas.entity.Article;
import com.msg.xt.spring.hateoas.entity.JavaMagazine;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Java Magazine Resource Assembler
 *
 * @author Michael Schäfer, Peter Huber
 */
@Component
public class JavaMagazineResourceAssembler extends ResourceAssemblerSupport<JavaMagazine, JavaMagazineResource> {

    public JavaMagazineResourceAssembler() {
        super(JavaMagazineResourceController.class, JavaMagazineResource.class);
    }

    public JavaMagazineResourceAssembler(Class<?> controllerClass, Class<JavaMagazineResource> resourceType) {
        super(JavaMagazineResourceController.class, JavaMagazineResource.class);
    }

    @Override
    public JavaMagazineResource toResource(JavaMagazine jm) {
        JavaMagazineResource resource = new JavaMagazineResource(jm);

        // Link auf mich selbst
        Link selfLink = linkTo(JavaMagazineResourceController.class)
            .slash(jm.getId())
            .withSelfRel();
        resource.add(selfLink);

        // Link auf die Liste meiner Referenzen
        Link articlesLink = linkTo(methodOn(ArticleResourceController.class)
            .articleByJavaMagazine(jm.getId()))
            .withRel("_articles");
        resource.add(articlesLink);

        // Linke auf jedes Elemente meiner Liste
        Set<Article> articles = jm.getArticles();

        for (Article article : articles) {
            Link articleLink = linkTo(methodOn(ArticleResourceController.class)
                .articleByJavaMagazine(jm.getId(), article.getId()))
                .withRel("_articles");
            resource.add(articleLink);
        }

        return resource;
    }
}
