package com.runninghi.runninghibackv2.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1103881695L;

    public static final QMember member = new QMember("member1");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath account = createString("account");

    public final BooleanPath alarmConsent = createBoolean("alarmConsent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DateTimePath<java.time.LocalDateTime> deactivateDate = createDateTime("deactivateDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> distanceToNextLevel = createNumber("distanceToNextLevel", Integer.class);

    public final StringPath fcmToken = createString("fcmToken");

    public final BooleanPath isActive = createBoolean("isActive");

    public final BooleanPath isBlacklisted = createBoolean("isBlacklisted");

    public final StringPath kakaoId = createString("kakaoId");

    public final StringPath kakaoName = createString("kakaoName");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Long> memberNo = createNumber("memberNo", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Integer> reportCnt = createNumber("reportCnt", Integer.class);

    public final EnumPath<com.runninghi.runninghibackv2.domain.enumtype.Role> role = createEnum("role", com.runninghi.runninghibackv2.domain.enumtype.Role.class);

    public final NumberPath<Double> totalDistance = createNumber("totalDistance", Double.class);

    public final NumberPath<Double> totalKcal = createNumber("totalKcal", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate = _super.updateDate;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

