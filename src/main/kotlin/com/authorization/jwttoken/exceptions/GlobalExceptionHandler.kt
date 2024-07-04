package com.authorization.jwttoken.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserExistsException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.CONFLICT.value(), ex.message.toString())
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    // Handle other exceptions here...
}