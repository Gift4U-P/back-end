package Gift4U.Backend.keyword.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeywordRecommendation is a Querydsl query type for KeywordRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeywordRecommendation extends EntityPathBase<KeywordRecommendation> {

    private static final long serialVersionUID = -1506387916L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKeywordRecommendation keywordRecommendation = new QKeywordRecommendation("keywordRecommendation");

    public final Gift4U.Backend.common.base.QBaseEntity _super = new Gift4U.Backend.common.base.QBaseEntity(this);

    public final StringPath age = createString("age");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath keywordText = createString("keywordText");

    public final StringPath presentRecommend = createString("presentRecommend");

    public final StringPath recommendText = createString("recommendText");

    public final StringPath relationship = createString("relationship");

    public final StringPath savedName = createString("savedName");

    public final StringPath situation = createString("situation");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final Gift4U.Backend.user.domain.QUser user;

    public QKeywordRecommendation(String variable) {
        this(KeywordRecommendation.class, forVariable(variable), INITS);
    }

    public QKeywordRecommendation(Path<? extends KeywordRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKeywordRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKeywordRecommendation(PathMetadata metadata, PathInits inits) {
        this(KeywordRecommendation.class, metadata, inits);
    }

    public QKeywordRecommendation(Class<? extends KeywordRecommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new Gift4U.Backend.user.domain.QUser(forProperty("user")) : null;
    }

}

