package com.github.sstg.kotlinbbs.web

import com.github.sstg.kotlinbbs.domain.Article
import com.github.sstg.kotlinbbs.domain.ArticleRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
class ArticleController(val articleRepository: ArticleRepository) {

    @GetMapping("/articles")
    fun getArticles(): ModelAndView {
        val model = mutableMapOf<String, Any>()
        model["data"] = articleRepository.findAll()
        return ModelAndView("article/list", model)
    }

    @GetMapping("/articles/new")
    fun newArticle(): String {
        val article = Article()
        articleRepository.save(article)
        return "redirect:/articles/${article.id}/edit"
    }

    @GetMapping("/articles/{id}")
    fun getArticle(@PathVariable id: Long): ModelAndView {
        val model = mutableMapOf<String, Any>()
        model["id"] = id
        return ModelAndView("article/detail", model)
    }

    @GetMapping("/articles/{id}/edit")
    fun editArticle(@PathVariable id: Long): ModelAndView {
        val model = mutableMapOf<String, Any>()
        model["article"] = articleRepository.findById(id).get()
        return ModelAndView("article/edit", model)
    }

    @PostMapping("/api/article/{id}")
    @ResponseBody
    fun postArticle(@PathVariable id: Long, @RequestBody article: Article) = articleRepository.save(article)


}