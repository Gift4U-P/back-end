package Gift4U.Backend.survey.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Gift4U.Backend.survey.domain.AskRecommendation;

public interface SurveyRepository extends JpaRepository<AskRecommendation, Long>, SurveyRepositoryCustom{

	Optional<AskRecommendation> findById(Long id);

	List<AskRecommendation> findAllByUserId(Long userId);

	Optional<AskRecommendation> findByIdAndUserId(Long surveyId, Long userId);
}
