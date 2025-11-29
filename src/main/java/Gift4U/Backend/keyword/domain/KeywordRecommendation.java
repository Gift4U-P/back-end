package Gift4U.Backend.keyword.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import Gift4U.Backend.common.base.BaseEntity;
import Gift4U.Backend.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KeywordRecommendation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@Column(nullable = false, length = 50)
	private String savedName;

	@Column(nullable = false, length = 100)
	private String age;

	@Column(nullable = false, length = 100)
	private String gender;

	@Column(nullable = false, length = 100)
	private String relationship;

	@Column(nullable = false, length = 100)
	private String situation;

	@Column(nullable = false, length = 250)
	private String keywordText;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String recommendText;

	@Column(columnDefinition = "json", nullable = false)
	private String presentRecommend;
}
