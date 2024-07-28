package com.authorization.jwttoken

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class JwtTokenApplication

fun main(args: Array<String>) {
	runApplication<JwtTokenApplication>(*args)
	println("we in")
}
