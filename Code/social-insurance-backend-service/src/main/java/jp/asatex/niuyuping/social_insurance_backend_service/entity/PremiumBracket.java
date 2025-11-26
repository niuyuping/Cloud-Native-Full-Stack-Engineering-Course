package jp.asatex.niuyuping.social_insurance_backend_service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 保险费等级实体类
 * 对应 premium_bracket 表
 * 用于健康保险、厚生年金保险的计算
 */
@Table("premium_bracket")
public class PremiumBracket {

    @Id
    private Long id;

    @Column("grade")
    private String grade;

    @Column("std_rem")
    private Integer stdRem;

    @Column("min_amount")
    private Integer minAmount;

    @Column("max_amount")
    private Integer maxAmount;

    @Column("health_no_care")
    private BigDecimal healthNoCare;

    @Column("health_care")
    private BigDecimal healthCare;

    @Column("pension")
    private BigDecimal pension;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    // 默认构造函数
    public PremiumBracket() {
    }

    // 全参构造函数
    public PremiumBracket(Long id, String grade, Integer stdRem, Integer minAmount, Integer maxAmount,
                          BigDecimal healthNoCare, BigDecimal healthCare, BigDecimal pension,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.grade = grade;
        this.stdRem = stdRem;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.healthNoCare = healthNoCare;
        this.healthCare = healthCare;
        this.pension = pension;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public PremiumBracket setId(Long id) {
        this.id = id;
        return this;
    }

    public String getGrade() {
        return grade;
    }

    public PremiumBracket setGrade(String grade) {
        this.grade = grade;
        return this;
    }

    public Integer getStdRem() {
        return stdRem;
    }

    public PremiumBracket setStdRem(Integer stdRem) {
        this.stdRem = stdRem;
        return this;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public PremiumBracket setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public PremiumBracket setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
        return this;
    }

    public BigDecimal getHealthNoCare() {
        return healthNoCare;
    }

    public PremiumBracket setHealthNoCare(BigDecimal healthNoCare) {
        this.healthNoCare = healthNoCare;
        return this;
    }

    public BigDecimal getHealthCare() {
        return healthCare;
    }

    public PremiumBracket setHealthCare(BigDecimal healthCare) {
        this.healthCare = healthCare;
        return this;
    }

    public BigDecimal getPension() {
        return pension;
    }

    public PremiumBracket setPension(BigDecimal pension) {
        this.pension = pension;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PremiumBracket setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public PremiumBracket setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    // 流式编程风格的 with 方法，返回新实例
    public PremiumBracket withId(Long id) {
        return new PremiumBracket(id, this.grade, this.stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withGrade(String grade) {
        return new PremiumBracket(this.id, grade, this.stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withStdRem(Integer stdRem) {
        return new PremiumBracket(this.id, this.grade, stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withMinAmount(Integer minAmount) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withMaxAmount(Integer maxAmount) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, this.minAmount, maxAmount,
                this.healthNoCare, this.healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withHealthNoCare(BigDecimal healthNoCare) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, this.minAmount, this.maxAmount,
                healthNoCare, this.healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withHealthCare(BigDecimal healthCare) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, healthCare, this.pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withPension(BigDecimal pension) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, pension, this.createdAt, this.updatedAt);
    }

    public PremiumBracket withCreatedAt(LocalDateTime createdAt) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, this.pension, createdAt, this.updatedAt);
    }

    public PremiumBracket withUpdatedAt(LocalDateTime updatedAt) {
        return new PremiumBracket(this.id, this.grade, this.stdRem, this.minAmount, this.maxAmount,
                this.healthNoCare, this.healthCare, this.pension, this.createdAt, updatedAt);
    }

    // 流式编程风格的 builder 方法
    public static PremiumBracketBuilder builder() {
        return new PremiumBracketBuilder();
    }

    public static class PremiumBracketBuilder {
        private Long id;
        private String grade;
        private Integer stdRem;
        private Integer minAmount;
        private Integer maxAmount;
        private BigDecimal healthNoCare;
        private BigDecimal healthCare;
        private BigDecimal pension;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PremiumBracketBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PremiumBracketBuilder grade(String grade) {
            this.grade = grade;
            return this;
        }

        public PremiumBracketBuilder stdRem(Integer stdRem) {
            this.stdRem = stdRem;
            return this;
        }

        public PremiumBracketBuilder minAmount(Integer minAmount) {
            this.minAmount = minAmount;
            return this;
        }

        public PremiumBracketBuilder maxAmount(Integer maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public PremiumBracketBuilder healthNoCare(BigDecimal healthNoCare) {
            this.healthNoCare = healthNoCare;
            return this;
        }

        public PremiumBracketBuilder healthCare(BigDecimal healthCare) {
            this.healthCare = healthCare;
            return this;
        }

        public PremiumBracketBuilder pension(BigDecimal pension) {
            this.pension = pension;
            return this;
        }

        public PremiumBracketBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PremiumBracketBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PremiumBracket build() {
            return new PremiumBracket(id, grade, stdRem, minAmount, maxAmount,
                    healthNoCare, healthCare, pension, createdAt, updatedAt);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PremiumBracket that = (PremiumBracket) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(stdRem, that.stdRem) &&
                Objects.equals(minAmount, that.minAmount) &&
                Objects.equals(maxAmount, that.maxAmount) &&
                Objects.equals(healthNoCare, that.healthNoCare) &&
                Objects.equals(healthCare, that.healthCare) &&
                Objects.equals(pension, that.pension) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade, stdRem, minAmount, maxAmount, healthNoCare, healthCare, pension, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "PremiumBracket{" +
                "id=" + id +
                ", grade='" + grade + '\'' +
                ", stdRem=" + stdRem +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", healthNoCare=" + healthNoCare +
                ", healthCare=" + healthCare +
                ", pension=" + pension +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

