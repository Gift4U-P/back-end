package Gift4U.Backend.keyword.converter;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.keyword.domain.KeywordRecommendation;
import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;

public class KeywordConverter {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	// 키워드 결과 목록 조회 API
	public static KeywordResponseDTO.KeywordResult toKeywordListResult(KeywordRecommendation keyword) {
		return KeywordResponseDTO.KeywordResult.builder()
			.keywordId(keyword.getId())
			.savedName(keyword.getSavedName())
			.createdAt(keyword.getCreatedAt())
			.build();
	}

	// 키워드 결과 상세 조회 API
	public static KeywordResponseDTO.KeywordDetailResult toKeywordDetailResult(KeywordRecommendation keyword) {
		try {
			// JSON 파싱
			List<KeywordResponseDTO.KeywordDetailResult.GiftList> giftLists =
				objectMapper.readValue(keyword.getPresentRecommend(), new TypeReference<>() {
				});

			// DTO 반환
			return KeywordResponseDTO.KeywordDetailResult.builder()
				.savedName(keyword.getSavedName())
				.age(keyword.getAge())
				.gender(keyword.getGender())
				.relationship(keyword.getRelationship())
				.situation(keyword.getSituation())
				.keywordText(keyword.getKeywordText())
				.card_message(keyword.getRecommendText())
				.giftList(giftLists)
				.build();

		} catch (JsonProcessingException e) {
			throw new GeneralException(ErrorStatus.JSON_PARSE_ERROR);
		}
	}

	// 키워드 추천 결과 저장 API
	public static KeywordResponseDTO.KeywordSaveResponse toKeywordSaveResponse(KeywordRecommendation keyword) {
		return KeywordResponseDTO.KeywordSaveResponse.builder()
			.id(keyword.getId())
			.savedName(keyword.getSavedName())
			.createdAt(LocalDateTime.from(keyword.getCreatedAt()))
			.build();
	}
}
