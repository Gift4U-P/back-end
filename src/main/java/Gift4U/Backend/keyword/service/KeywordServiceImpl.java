package Gift4U.Backend.keyword.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Gift4U.Backend.apiPayload.code.status.ErrorStatus;
import Gift4U.Backend.apiPayload.exception.GeneralException;
import Gift4U.Backend.keyword.converter.KeywordConverter;
import Gift4U.Backend.keyword.domain.KeywordRecommendation;
import Gift4U.Backend.keyword.repository.KeywordRepository;
import Gift4U.Backend.keyword.web.dto.KeywordResponseDTO;
import Gift4U.Backend.survey.converter.SurveyConverter;
import Gift4U.Backend.survey.domain.AskRecommendation;
import Gift4U.Backend.survey.web.dto.SurveyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KeywordServiceImpl implements KeywordService {

	private final KeywordRepository keywordRepository;

	// 키워드 결과 목록 조회 API
	@Override
	public KeywordResponseDTO.KeywordResultList searchKeywordList(Long userId) {
		List<KeywordRecommendation> keywordList = keywordRepository.findAllByUserId(userId);
		List<KeywordResponseDTO.KeywordResult> results = keywordList.stream()
			.map(KeywordConverter::toKeywordListResult)
			.collect(Collectors.toList());

		return KeywordResponseDTO.KeywordResultList.builder()
			.result(results)
			.build();
	}

	// 키워드 결과 상세 조회 API
	@Override
	public KeywordResponseDTO.KeywordDetailResult searchKeywordDetail(Long userId, Long keywordId) {
		KeywordRecommendation keyword = keywordRepository.findByIdAndUserId(keywordId, userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.KEYWORD_NOT_EXIST_ERROR));

		return KeywordConverter.toKeywordDetailResult(keyword);
	}
}
