package Gift4U.Backend.ask.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAskRecommendation is a Querydsl query type for AskRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAskRecommendation extends EntityPathBase<AskRecommendation> {

    private static final long serialVersionUID = 634956468L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAskRecommendation askRecommendation = new QAskRecommendation("askRecommendation");

    public final Gift4U.Backend.common.base.QBaseEntity _super = new Gift4U.Backend.common.base.QBaseEntity(this);

    public final StringPath characterText = createString("characterText");

    public final StringPath characterType = createString("characterType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath presentRecommend = createString("presentRecommend");

    public final StringPath recommendText = createString("recommendText");

    public final StringPath savedName = createString("savedName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final Gift4U.Backend.user.domain.QUser user;

    public QAskRecommendation(String variable) {
        this(AskRecommendation.class, forVariable(variable), INITS);
    }

    public QAskRecommendation(Path<? extends AskRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAskRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAskRecommendation(PathMetadata metadata, PathInits inits) {
        this(AskRecommendation.class, metadata, inits);
    }

    public QAskRecommendation(Class<? extends AskRecommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new Gift4U.Backend.user.domain.QUser(forProperty("user")) : null;
    }

}

