package Gift4U.Backend.external.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FastApiResponse {

	// 홈 선물 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class HomePresentList {
		private List<GiftItem> randomGifts;
		private List<GiftItem> luxuryGifts;
		private List<GiftItem> budgetGifts;
	}

	// 선물 아이템 DTO
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GiftItem {
		private String title;
		private String lprice;
		private String link;
		private String image;
		private String mallName;
	}

	// 설문 추천 결과 조회 API
	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SearchSurveyList {
		private String analysis;
		private String reasoning;
		private String card_message;
		private List<GiftList> giftList;
	}

	@Builder
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GiftList {
		private String title;
		private String lprice;
		private String link;
		private String image;
		private String mallName;
	}
}
