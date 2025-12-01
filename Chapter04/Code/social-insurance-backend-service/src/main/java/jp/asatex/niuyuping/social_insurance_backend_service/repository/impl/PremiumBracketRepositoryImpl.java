package jp.asatex.niuyuping.social_insurance_backend_service.repository.impl;

import jp.asatex.niuyuping.social_insurance_backend_service.entity.PremiumBracket;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 保险费等级 Repository 自定义实现类
 * 提供流式编程风格的自定义查询和操作方法
 * 注意：基础 CRUD 方法由 Spring Data R2DBC 自动实现
 */
@Repository
public class PremiumBracketRepositoryImpl {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public PremiumBracketRepositoryImpl(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    /**
     * 流式保存方法，自动设置创建时间和更新时间
     *
     * @param entity 保险费等级实体
     * @return Mono<PremiumBracket> 保存后的实体
     */
    public Mono<PremiumBracket> saveWithTimestamp(PremiumBracket entity) {
        LocalDateTime now = LocalDateTime.now();
        return Mono.just(entity)
                .map(e -> {
                    if (e.getId() == null) {
                        return e.setCreatedAt(now).setUpdatedAt(now);
                    }
                    return e.setUpdatedAt(now);
                })
                .flatMap(e -> r2dbcEntityTemplate.insert(PremiumBracket.class).using(e));
    }

    /**
     * 流式更新方法，自动更新 updated_at 字段
     *
     * @param entity 保险费等级实体
     * @return Mono<PremiumBracket> 更新后的实体
     */
    public Mono<PremiumBracket> updateWithTimestamp(PremiumBracket entity) {
        return Mono.just(entity)
                .map(e -> e.setUpdatedAt(LocalDateTime.now()))
                .flatMap(e -> {
                    Update update = Update.update("updated_at", e.getUpdatedAt())
                            .set("grade", e.getGrade())
                            .set("std_rem", e.getStdRem())
                            .set("min_amount", e.getMinAmount())
                            .set("max_amount", e.getMaxAmount())
                            .set("health_no_care", e.getHealthNoCare())
                            .set("health_care", e.getHealthCare())
                            .set("pension", e.getPension());
                    return r2dbcEntityTemplate.update(PremiumBracket.class)
                            .matching(Query.query(Criteria.where("id").is(e.getId())))
                            .apply(update)
                            .thenReturn(e);
                });
    }

    /**
     * 根据金额范围查找对应的保险费等级（流式查询）
     *
     * @param amount 金额
     * @return Mono<PremiumBracket> 保险费等级信息
     */
    public Mono<PremiumBracket> findBracketByAmount(Integer amount) {
        return r2dbcEntityTemplate.select(PremiumBracket.class)
                .matching(Query.query(
                        Criteria.where("min_amount").lessThanOrEquals(amount)
                                .and(Criteria.where("max_amount").greaterThan(amount))
                ))
                .first();
    }

    /**
     * 流式查询：根据标准报酬范围查找保险费等级列表
     *
     * @param minStdRem 最小标准报酬
     * @param maxStdRem 最大标准报酬
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    public Flux<PremiumBracket> findByStdRemRange(Integer minStdRem, Integer maxStdRem) {
        return r2dbcEntityTemplate.select(PremiumBracket.class)
                .matching(Query.query(
                        Criteria.where("std_rem").greaterThanOrEquals(minStdRem)
                                .and(Criteria.where("std_rem").lessThanOrEquals(maxStdRem))
                ).sort(org.springframework.data.domain.Sort.by("std_rem").ascending()))
                .all();
    }

    /**
     * 流式更新：批量更新保险费等级的标准报酬
     *
     * @param grade 等级
     * @param newStdRem 新的标准报酬
     * @return Mono<Long> 更新的记录数
     */
    public Mono<Long> updateStdRemByGrade(String grade, Integer newStdRem) {
        return r2dbcEntityTemplate.update(PremiumBracket.class)
                .matching(Query.query(Criteria.where("grade").is(grade)))
                .apply(Update.update("std_rem", newStdRem)
                        .set("updated_at", LocalDateTime.now()));
    }

    /**
     * 流式查询：查找所有有厚生年金保险费的等级（pension > 0）
     *
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    public Flux<PremiumBracket> findBracketsWithPension() {
        return r2dbcEntityTemplate.select(PremiumBracket.class)
                .matching(Query.query(
                        Criteria.where("pension").greaterThan(0)
                ).sort(org.springframework.data.domain.Sort.by("std_rem").ascending()))
                .all();
    }

    /**
     * 流式查询：根据健康保险费范围查找等级
     *
     * @param minHealthNoCare 最小健康保险费（无护理）
     * @param maxHealthNoCare 最大健康保险费（无护理）
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    public Flux<PremiumBracket> findByHealthNoCareRange(java.math.BigDecimal minHealthNoCare,
                                                         java.math.BigDecimal maxHealthNoCare) {
        return r2dbcEntityTemplate.select(PremiumBracket.class)
                .matching(Query.query(
                        Criteria.where("health_no_care").greaterThanOrEquals(minHealthNoCare)
                                .and(Criteria.where("health_no_care").lessThanOrEquals(maxHealthNoCare))
                ))
                .all();
    }

    /**
     * 流式操作：检查并保存（如果不存在）
     *
     * @param entity 保险费等级实体
     * @return Mono<PremiumBracket> 保存后的实体
     */
    public Mono<PremiumBracket> saveIfNotExists(PremiumBracket entity) {
        return r2dbcEntityTemplate.select(PremiumBracket.class)
                .matching(Query.query(Criteria.where("grade").is(entity.getGrade())))
                .one()
                .cast(PremiumBracket.class)
                .switchIfEmpty(saveWithTimestamp(entity));
    }

    /**
     * 流式操作：根据等级更新或创建
     *
     * @param entity 保险费等级实体
     * @return Mono<PremiumBracket> 更新或创建后的实体
     */
    public Mono<PremiumBracket> upsertByGrade(PremiumBracket entity) {
        return r2dbcEntityTemplate.select(PremiumBracket.class)
                .matching(Query.query(Criteria.where("grade").is(entity.getGrade())))
                .one()
                .cast(PremiumBracket.class)
                .flatMap(existing -> {
                    PremiumBracket updated = existing
                            .setStdRem(entity.getStdRem())
                            .setMinAmount(entity.getMinAmount())
                            .setMaxAmount(entity.getMaxAmount())
                            .setHealthNoCare(entity.getHealthNoCare())
                            .setHealthCare(entity.getHealthCare())
                            .setPension(entity.getPension());
                    return updateWithTimestamp(updated);
                })
                .switchIfEmpty(saveWithTimestamp(entity));
    }
}

