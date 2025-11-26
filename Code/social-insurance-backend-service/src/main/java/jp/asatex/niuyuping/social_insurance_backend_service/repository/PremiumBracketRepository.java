package jp.asatex.niuyuping.social_insurance_backend_service.repository;

import jp.asatex.niuyuping.social_insurance_backend_service.entity.PremiumBracket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 保险费等级 Repository 接口
 * 提供响应式数据访问方法
 * Spring Data R2DBC 会自动实现基础 CRUD 方法
 */
@Repository
public interface PremiumBracketRepository extends ReactiveCrudRepository<PremiumBracket, Long>,
        ReactiveSortingRepository<PremiumBracket, Long> {

    /**
     * 根据等级查找保险费等级信息
     *
     * @param grade 等级
     * @return Mono<PremiumBracket> 保险费等级信息
     */
    Mono<PremiumBracket> findByGrade(String grade);

    /**
     * 根据标准报酬查找保险费等级信息
     *
     * @param stdRem 标准报酬
     * @return Mono<PremiumBracket> 保险费等级信息
     */
    Mono<PremiumBracket> findByStdRem(Integer stdRem);

    /**
     * 根据金额范围查找对应的保险费等级
     * 查找 min_amount <= amount < max_amount 的记录
     *
     * @param amount 金额
     * @return Mono<PremiumBracket> 保险费等级信息
     */
    @Query("SELECT * FROM premium_bracket WHERE $1 >= min_amount AND $1 < max_amount LIMIT 1")
    Mono<PremiumBracket> findByAmount(Integer amount);

    /**
     * 查找所有有效的保险费等级（按标准报酬排序）
     *
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    @Query("SELECT * FROM premium_bracket ORDER BY std_rem ASC")
    Flux<PremiumBracket> findAllOrderByStdRemAsc();

    /**
     * 根据最小金额和最大金额范围查找保险费等级
     *
     * @param minAmount 最小金额
     * @param maxAmount 最大金额
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    Flux<PremiumBracket> findByMinAmountLessThanEqualAndMaxAmountGreaterThan(Integer minAmount, Integer maxAmount);

    /**
     * 检查等级是否存在
     *
     * @param grade 等级
     * @return Mono<Boolean> 是否存在
     */
    Mono<Boolean> existsByGrade(String grade);

    /**
     * 根据等级删除保险费等级信息
     *
     * @param grade 等级
     * @return Mono<Void>
     */
    Mono<Void> deleteByGrade(String grade);
}

