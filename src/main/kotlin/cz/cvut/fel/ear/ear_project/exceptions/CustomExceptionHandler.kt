package cz.cvut.fel.ear.ear_project.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(value = [ItemAlreadyPresentException::class])
    fun handleItemAlreadyPresentException(ex: ItemAlreadyPresentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ItemNotFoundException::class])
    fun handleItemNotFoundException(ex: ItemNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [WrongStateChangeException::class])
    fun handleWrongStateChangeException(ex: WrongStateChangeException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleAllOtherExceptions(ex: Exception): ResponseEntity<String> {
        return ResponseEntity(ex.message ?: "An error occurred", HttpStatus.BAD_REQUEST)
    }
}