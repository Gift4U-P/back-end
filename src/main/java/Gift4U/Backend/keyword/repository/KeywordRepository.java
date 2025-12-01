package Gift4U.Backend.keyword.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Gift4U.Backend.keyword.domain.KeywordRecommendation;
import Gift4U.Backend.survey.domain.AskRecommendation;

public interface KeywordRepository extends JpaRepository<KeywordRecommendation, Long>, KeywordRepositoryCustom {

	Optional<KeywordRecommendation> findById(Long id);

	List<KeywordRecommendation> findAllByUserId(Long userId);

	Optional<KeywordRecommendation> findByIdAndUserId(Long keywordId, Long userId);
}
