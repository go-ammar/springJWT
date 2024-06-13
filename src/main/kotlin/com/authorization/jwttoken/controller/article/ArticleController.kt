package com.authorization.jwttoken.controller.article

import com.authorization.jwttoken.model.Article
import com.authorization.jwttoken.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/getarticle")
class ArticleController(private val articleService: ArticleService) {


    @GetMapping
    fun listAll(): List<ArticleResponse> =
        articleService.findAll()
            .map {
                it.toResponse()
            }

    private fun Article.toResponse(): ArticleResponse =
        ArticleResponse(
            this.id,
            this.title,
            this.content
        )

}