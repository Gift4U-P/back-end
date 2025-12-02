package Gift4U.Backend.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import Gift4U.Backend.apiPayload.code.BaseErrorCode;
import Gift4U.Backend.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	// 가장 일반적인 응답
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	//JSON 파싱 에러
	JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "JSON4001", "JSON 파싱에 실패했습니다."),

	// FastAPI 연동 에러
	FASTAPI_COMMUNICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FASTAPI5001", "FastAPI 서버 통신 중 오류가 발생했습니다."),

	// 사용자 에러
	EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "USER4001", "이미 사용된 이메일입니다."),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER4002", "비밀번호가 일치하지 않습니다."),
	EMAIL_USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER4003", "해당 이메일을 가진 사용자가 존재하지 않습니다."),
	REFRESHTOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "USER4004", "refreshToken 값이 일치하지 않습니다."),
	LOGIN_UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "USER4005", "로그인 중 알 수 없는 오류가 발생했습니다."),
	LOGIN_PARSING_FAIL(HttpStatus.BAD_REQUEST, "USER4006", "로그인 DTO 변환을 실패했습니다."),

	// 토큰 에러
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN4002", "리프레시 토큰을 입력해야 합니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4003", "만료된 토큰입니다."),
	LOGOUT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4004", "로그아웃한 액세스 토큰입니다."),
	MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4005", "토큰 구조가 잘못됐습니다."),
	UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4006", "지원하지 않는 토큰 형식입니다."),
	INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "TOKEN4007", "토큰의 서명이 잘못됐습니다."),
	TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4008", "토큰이 없습니다."),

	// 설문 추천 에러
	SURVEY_NOT_EXIST_ERROR(HttpStatus.BAD_REQUEST, "SURVEY4001", "존재하지 않는 surveyId 이거나 본인의 survey가 아닙니다."),

	// 키워드 추천 에러
	KEYWORD_NOT_EXIST_ERROR(HttpStatus.BAD_REQUEST, "KEYWORD4001", "존재하지 않는 keywordId 이거나 본인의 keyword가 아닙니다."),

	// 예시,,,
	ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다.");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDTO getReason() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.build();
	}

	@Override
	public ErrorReasonDTO getReasonHttpStatus() {
		return ErrorReasonDTO.builder()
			.message(message)
			.code(code)
			.isSuccess(false)
			.httpStatus(httpStatus)
			.build()
			;
	}
}
