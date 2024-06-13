package com.authorization.jwttoken

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtTokenApplication

fun main(args: Array<String>) {
	runApplication<JwtTokenApplication>(*args)
	print("we in")
}
