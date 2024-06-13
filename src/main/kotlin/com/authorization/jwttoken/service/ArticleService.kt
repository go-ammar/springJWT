package com.authorization.jwttoken.service

import com.authorization.jwttoken.model.Article
import com.authorization.jwttoken.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun findAll(): List<Article> = articleRepository.findAll()

}