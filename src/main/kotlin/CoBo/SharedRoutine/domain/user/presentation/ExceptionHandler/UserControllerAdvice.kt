package CoBo.SharedRoutine.domain.user.presentation.ExceptionHandler

import CoBo.SharedRoutine.domain.user.presentation.UserController
import CoBo.SharedRoutine.global.config.response.CoBoResponse
import CoBo.SharedRoutine.global.config.response.CoBoResponseDto
import CoBo.SharedRoutine.global.config.response.CoBoResponseStatus
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.NoSuchElementException

@RestControllerAdvice(basePackageClasses = [UserController::class])
class UserControllerAdvice {

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionHandler(noSuchElementException: NoSuchElementException): ResponseEntity<CoBoResponseDto<String?>> {
        return CoBoResponse(noSuchElementException.message, CoBoResponseStatus.NOT_FOUND_ELEMENT).getResponseEntity()
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun dataIntegrityViolationExceptionHandler(dataIntegrityViolationException: DataIntegrityViolationException): ResponseEntity<CoBoResponseDto<String>> {
        return CoBoResponse("닉네임이 중복됩니다.", CoBoResponseStatus.CONFLICT).getResponseEntity()
    }
}