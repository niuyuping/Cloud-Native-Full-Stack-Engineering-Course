package jp.asatex.niuyuping.social_insurance_backend_service.domain;

import jp.asatex.niuyuping.social_insurance_backend_service.domain.dto.SocialInsuranceDomainDto;
import jp.asatex.niuyuping.social_insurance_backend_service.entity.PremiumBracket;
import jp.asatex.niuyuping.social_insurance_backend_service.repository.PremiumBracketRepository;
import jp.asatex.niuyuping.social_insurance_backend_service.repository.impl.PremiumBracketRepositoryImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 保险费等级 Domain Service
 * 提供业务逻辑处理和流式编程风格的操作方法
 */
@Service
public class PremiumBracketDomainService {

    private final PremiumBracketRepository premiumBracketRepository;
    private final PremiumBracketRepositoryImpl premiumBracketRepositoryImpl;

    public PremiumBracketDomainService(PremiumBracketRepository premiumBracketRepository,
                                       PremiumBracketRepositoryImpl premiumBracketRepositoryImpl) {
        this.premiumBracketRepository = premiumBracketRepository;
        this.premiumBracketRepositoryImpl = premiumBracketRepositoryImpl;
    }

    // ==================== 基本 CRUD 方法 ====================

    /**
     * 流式保存保险费等级
     *
     * @param premiumBracket 保险费等级实体
     * @return Mono<PremiumBracket> 保存后的实体
     */
    public Mono<PremiumBracket> save(PremiumBracket premiumBracket) {
        return premiumBracketRepositoryImpl.saveWithTimestamp(premiumBracket);
    }

    /**
     * 流式更新保险费等级
     *
     * @param premiumBracket 保险费等级实体
     * @return Mono<PremiumBracket> 更新后的实体
     */
    public Mono<PremiumBracket> update(PremiumBracket premiumBracket) {
        return premiumBracketRepositoryImpl.updateWithTimestamp(premiumBracket);
    }

    /**
     * 流式查询：根据ID查找保险费等级
     *
     * @param id 主键ID
     * @return Mono<PremiumBracket> 保险费等级实体
     */
    public Mono<PremiumBracket> findById(Long id) {
        return premiumBracketRepository.findById(id);
    }

    /**
     * 流式查询：查找所有保险费等级
     *
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    public Flux<PremiumBracket> findAll() {
        return premiumBracketRepository.findAll();
    }

    /**
     * 流式查询：根据等级查找保险费等级
     *
     * @param grade 等级
     * @return Mono<PremiumBracket> 保险费等级实体
     */
    public Mono<PremiumBracket> findByGrade(String grade) {
        return premiumBracketRepository.findByGrade(grade);
    }

    /**
     * 流式删除：根据ID删除保险费等级
     *
     * @param id 主键ID
     * @return Mono<Void>
     */
    public Mono<Void> deleteById(Long id) {
        return premiumBracketRepository.deleteById(id);
    }

    /**
     * 流式删除：根据等级删除保险费等级
     *
     * @param grade 等级
     * @return Mono<Void>
     */
    public Mono<Void> deleteByGrade(String grade) {
        return premiumBracketRepository.deleteByGrade(grade);
    }

    /**
     * 流式检查：检查ID是否存在
     *
     * @param id 主键ID
     * @return Mono<Boolean> 是否存在
     */
    public Mono<Boolean> existsById(Long id) {
        return premiumBracketRepository.existsById(id);
    }

    /**
     * 流式检查：检查等级是否存在
     *
     * @param grade 等级
     * @return Mono<Boolean> 是否存在
     */
    public Mono<Boolean> existsByGrade(String grade) {
        return premiumBracketRepository.existsByGrade(grade);
    }

    /**
     * 流式统计：统计保险费等级总数
     *
     * @return Mono<Long> 总数
     */
    public Mono<Long> count() {
        return premiumBracketRepository.count();
    }

    // ==================== 业务逻辑方法 ====================

    /**
     * 查询社会保险金额
     * 根据月薪和年龄计算社会保险费用
     *
     * @param monthlySalary 月薪
     * @param age 年龄
     * @return Mono<SocialInsuranceDomainDto> 社会保险金额DTO
     */
    public Mono<SocialInsuranceDomainDto> socialInsuranceQuery(Integer monthlySalary, Integer age) {
        return Mono.just(monthlySalary)
                .flatMap(salary -> premiumBracketRepository.findByAmount(salary))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        String.format("未找到月薪 %d 对应的保险费等级", monthlySalary))))
                .flatMap(bracket -> calculateSocialInsurance(bracket, age));
    }

    /**
     * 流式计算社会保险金额
     * 健康保险、介护保险、厚生年金的花费由雇员和雇主双方各承担50%
     *
     * @param bracket 保险费等级实体
     * @param age 年龄
     * @return Mono<SocialInsuranceDomainDto> 社会保险金额DTO
     */
    private Mono<SocialInsuranceDomainDto> calculateSocialInsurance(PremiumBracket bracket, Integer age) {
        return Mono.just(bracket)
                .map(b -> {
                    BigDecimal healthCostWithNoCare = b.getHealthNoCare();
                    BigDecimal healthCostWithCare = b.getHealthCare();
                    BigDecimal pension = b.getPension();

                    // 计算介护保险金额
                    BigDecimal careCost = BigDecimal.ZERO;
                    if (age != null && age >= 40) {
                        // 年龄 >= 40岁：介护保险金额 = 有介护健康保险金额 - 无介护健康保险金额
                        careCost = healthCostWithCare.subtract(healthCostWithNoCare);
                    }
                    // 年龄 < 40岁：介护保险金额 = 0（已在初始化时设置）

                    // 费用分配比例：雇员和雇主各承担50%
                    BigDecimal half = new BigDecimal("0.5");
                    int scale = 2; // 保留2位小数

                    // 计算雇员承担的费用（50%）
                    BigDecimal employeeHealthCostWithNoCare = healthCostWithNoCare.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employeeCareCost = careCost.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employeePension = pension.multiply(half).setScale(scale, RoundingMode.HALF_UP);

                    // 计算雇主承担的费用（50%）
                    BigDecimal employerHealthCostWithNoCare = healthCostWithNoCare.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employerCareCost = careCost.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employerPension = pension.multiply(half).setScale(scale, RoundingMode.HALF_UP);

                    // 构建雇员费用结构体
                    SocialInsuranceDomainDto.EmployeeCost employeeCost = SocialInsuranceDomainDto.EmployeeCost.builder()
                            .healthCostWithNoCare(employeeHealthCostWithNoCare)
                            .careCost(employeeCareCost)
                            .pension(employeePension)
                            .build();

                    // 构建雇主费用结构体
                    SocialInsuranceDomainDto.EmployerCost employerCost = SocialInsuranceDomainDto.EmployerCost.builder()
                            .healthCostWithNoCare(employerHealthCostWithNoCare)
                            .careCost(employerCareCost)
                            .pension(employerPension)
                            .build();

                    return SocialInsuranceDomainDto.builder()
                            .employeeCost(employeeCost)
                            .employerCost(employerCost)
                            .build();
                });
    }

    /**
     * 流式查询：根据金额范围查找保险费等级
     *
     * @param amount 金额
     * @return Mono<PremiumBracket> 保险费等级实体
     */
    public Mono<PremiumBracket> findBracketByAmount(Integer amount) {
        return premiumBracketRepositoryImpl.findBracketByAmount(amount);
    }

    /**
     * 流式查询：根据标准报酬范围查找保险费等级列表
     *
     * @param minStdRem 最小标准报酬
     * @param maxStdRem 最大标准报酬
     * @return Flux<PremiumBracket> 保险费等级列表
     */
    public Flux<PremiumBracket> findByStdRemRange(Integer minStdRem, Integer maxStdRem) {
        return premiumBracketRepositoryImpl.findByStdRemRange(minStdRem, maxStdRem);
    }

    /**
     * 流式操作：保存或更新保险费等级（根据等级）
     *
     * @param premiumBracket 保险费等级实体
     * @return Mono<PremiumBracket> 保存或更新后的实体
     */
    public Mono<PremiumBracket> saveOrUpdate(PremiumBracket premiumBracket) {
        return premiumBracketRepositoryImpl.upsertByGrade(premiumBracket);
    }

    /**
     * 流式操作：如果不存在则保存
     *
     * @param premiumBracket 保险费等级实体
     * @return Mono<PremiumBracket> 保存后的实体或已存在的实体
     */
    public Mono<PremiumBracket> saveIfNotExists(PremiumBracket premiumBracket) {
        return premiumBracketRepositoryImpl.saveIfNotExists(premiumBracket);
    }
}

