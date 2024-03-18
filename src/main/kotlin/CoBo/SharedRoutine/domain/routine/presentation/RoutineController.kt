package CoBo.SharedRoutine.domain.routine.presentation

import CoBo.SharedRoutine.domain.routine.Data.Dto.Req.RoutinePostParticipationReq
import CoBo.SharedRoutine.domain.routine.Data.Dto.Req.RoutinePostReq
import CoBo.SharedRoutine.domain.routine.Data.Dto.Res.RoutineGetParticipationElementRes
import CoBo.SharedRoutine.domain.routine.Data.Dto.Res.RoutineGetRankAndSearchElementRes
import CoBo.SharedRoutine.domain.routine.Data.Dto.Res.RoutineGetRes
import CoBo.SharedRoutine.domain.routine.Data.Dto.Res.RoutineGetWeekElementRes
import CoBo.SharedRoutine.domain.routine.application.RoutineService
import CoBo.SharedRoutine.global.config.response.CoBoResponseDto
import CoBo.SharedRoutine.global.config.response.CoBoResponseStatus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/routine")
@Tag(name = "루틴")
class RoutineController (
    private val routineService: RoutineService
){

    @PostMapping
    @Operation(summary = "루틴 등록 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun post(@RequestBody routinePostReq: RoutinePostReq, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.post(routinePostReq, authentication)
    }

    @PostMapping("/participate")
    @Operation(summary = "루틴 참여 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content())),
        ApiResponse(responseCode = "409", description = "이미 참여한 루틴입니다.", content = arrayOf(Content()))
    )
    fun postParticipation(@RequestBody routinePostParticipationReq: RoutinePostParticipationReq,
                          @Parameter(hidden = true) authentication: Authentication)
            : ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.postParticipation(routinePostParticipationReq, authentication)
    }

    @PatchMapping
    @Operation(summary = "루틴 설명 수정 API (방장만 가능)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content())),
        ApiResponse(responseCode = "401", description = "수정 권한이 없습니다.", content = arrayOf(Content()))
    )
    fun patch(@RequestParam routineId: Int, @RequestParam description: String,
              @Parameter(hidden = true) authentication: Authentication)
            : ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.patch(routineId, description, authentication)
    }

    @PatchMapping("/participate")
    @Operation(summary = "루틴 목표 날짜 수정 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content())),
    )
    fun patchParticipation(@RequestParam routineId: Int,
                           @Schema(example = "2024-08-15") @RequestParam goalDate: LocalDate,
                           @Parameter(hidden = true) authentication: Authentication)
            : ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.patchParticipation(routineId, goalDate, authentication)
    }

    @PostMapping("/check")
    @Operation(summary = "루틴 완료 체크 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content())),
        ApiResponse(responseCode = "409", description = "오늘 이미 완료한 루틴입니다.", content = arrayOf(Content()))
    )
    fun postCheck(@RequestParam routineId: Int,
                  @Parameter(hidden = true) authentication: Authentication)
            : ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.postCheck(routineId, authentication)
    }

    @GetMapping("/participate")
    @Operation(summary = "각 사용자 별 가입한 루틴 조회 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun getParticipation(@Parameter(hidden = true) authentication: Authentication): ResponseEntity<CoBoResponseDto<ArrayList<RoutineGetParticipationElementRes>>> {
        return routineService.getParticipation(authentication)
    }

    @GetMapping
    @Operation(summary = "루틴 그룹 조회 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun get(@RequestParam routineId: Int,
            @Parameter(hidden = true) authentication: Authentication)
            : ResponseEntity<CoBoResponseDto<RoutineGetRes>> {
        return routineService.get(routineId, authentication)
    }

    @GetMapping("/rank")
    @Operation(summary = "루틴 순위 조회 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun getRank(): ResponseEntity<CoBoResponseDto<ArrayList<RoutineGetRankAndSearchElementRes>>> {
        return routineService.getRank()
    }

    @PatchMapping("/admin")
    @Operation(summary = "루틴 방장 변경 API (방장만 가능)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content())),
        ApiResponse(responseCode = "401", description = "수정 권한이 없습니다.", content = arrayOf(Content()))
    )
    fun patchAdmin(@RequestParam routineId: Int, @RequestParam newAdminId: Int,
                   @Parameter(hidden = true) authentication: Authentication): ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.patchAdmin(routineId, newAdminId, authentication)
    }

    @GetMapping("/search")
    @Operation(summary = "루틴 키워드 검색 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun getSearch(@RequestParam keyword: String): ResponseEntity<CoBoResponseDto<ArrayList<RoutineGetRankAndSearchElementRes>>> {
        return routineService.getSearch(keyword)
    }

    @DeleteMapping
    @Operation(summary = "루틴 탈퇴 API (방장이 탈퇴할 시 랜덤 위임, 루틴 인원 0명일 시 루틴 삭제)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun deleteParticipation(routineId: Int, authentication: Authentication): ResponseEntity<CoBoResponseDto<CoBoResponseStatus>> {
        return routineService.deleteParticipation(routineId, authentication)
    }

    @GetMapping("/week")
    @Operation(summary = "일주일 간 루틴 달성 상태 조회 (해당 요일 달성 시 1, 미달성 시 0)")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content())),
        ApiResponse(responseCode = "403", description = "인증 실패", content = arrayOf(Content()))
    )
    fun getWeek(@Parameter(hidden = true) authentication: Authentication): ResponseEntity<CoBoResponseDto<ArrayList<RoutineGetWeekElementRes>>> {
        return routineService.getWeek(authentication)
    }
}