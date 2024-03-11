package CoBo.SharedRoutine.domain.user.application

import CoBo.SharedRoutine.domain.user.data.dto.Res.UserGetRes
import CoBo.SharedRoutine.global.config.response.CoBoResponseDto
import CoBo.SharedRoutine.global.config.response.CoBoResponseStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun postImage(multipartFile: MultipartFile, authentication: Authentication): ResponseEntity<CoBoResponseDto<CoBoResponseStatus>>
    fun patch(newName: String, authentication: Authentication): ResponseEntity<CoBoResponseDto<CoBoResponseStatus>>
    fun getExist(newName: String): ResponseEntity<CoBoResponseDto<Boolean>>
    fun get(authentication: Authentication): ResponseEntity<CoBoResponseDto<UserGetRes>>
}