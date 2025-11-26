package jp.asatex.niuyuping.social_insurance_backend_service.domain.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 社会保险金额查询结果 DTO
 */
public class SocialInsuranceDomainDto {

    /**
     * 雇员承担的费用
     */
    private EmployeeCost employeeCost;

    /**
     * 雇主承担的费用
     */
    private EmployerCost employerCost;

    // 默认构造函数
    public SocialInsuranceDomainDto() {
    }

    // 全参构造函数
    public SocialInsuranceDomainDto(EmployeeCost employeeCost, EmployerCost employerCost) {
        this.employeeCost = employeeCost;
        this.employerCost = employerCost;
    }

    // Getter 和 Setter 方法（流式风格）
    public EmployeeCost getEmployeeCost() {
        return employeeCost;
    }

    public SocialInsuranceDomainDto setEmployeeCost(EmployeeCost employeeCost) {
        this.employeeCost = employeeCost;
        return this;
    }

    public EmployerCost getEmployerCost() {
        return employerCost;
    }

    public SocialInsuranceDomainDto setEmployerCost(EmployerCost employerCost) {
        this.employerCost = employerCost;
        return this;
    }

    // 流式编程风格的 with 方法，返回新实例
    public SocialInsuranceDomainDto withEmployeeCost(EmployeeCost employeeCost) {
        return new SocialInsuranceDomainDto(employeeCost, this.employerCost);
    }

    public SocialInsuranceDomainDto withEmployerCost(EmployerCost employerCost) {
        return new SocialInsuranceDomainDto(this.employeeCost, employerCost);
    }

    // 流式编程风格的 builder 方法
    public static SocialInsuranceDomainDtoBuilder builder() {
        return new SocialInsuranceDomainDtoBuilder();
    }

    public static class SocialInsuranceDomainDtoBuilder {
        private EmployeeCost employeeCost;
        private EmployerCost employerCost;

        public SocialInsuranceDomainDtoBuilder employeeCost(EmployeeCost employeeCost) {
            this.employeeCost = employeeCost;
            return this;
        }

        public SocialInsuranceDomainDtoBuilder employerCost(EmployerCost employerCost) {
            this.employerCost = employerCost;
            return this;
        }

        public SocialInsuranceDomainDto build() {
            return new SocialInsuranceDomainDto(employeeCost, employerCost);
        }
    }

    /**
     * 雇员承担的费用结构体
     */
    public static class EmployeeCost {
        /**
         * 无介护健康保险金额
         */
        private BigDecimal healthCostWithNoCare;

        /**
         * 介护保险金额
         */
        private BigDecimal careCost;

        /**
         * 厚生年金金额
         */
        private BigDecimal pension;

        // 默认构造函数
        public EmployeeCost() {
        }

        // 全参构造函数
        public EmployeeCost(BigDecimal healthCostWithNoCare, BigDecimal careCost, BigDecimal pension) {
            this.healthCostWithNoCare = healthCostWithNoCare;
            this.careCost = careCost;
            this.pension = pension;
        }

        // Getter 和 Setter 方法（流式风格）
        public BigDecimal getHealthCostWithNoCare() {
            return healthCostWithNoCare;
        }

        public EmployeeCost setHealthCostWithNoCare(BigDecimal healthCostWithNoCare) {
            this.healthCostWithNoCare = healthCostWithNoCare;
            return this;
        }

        public BigDecimal getCareCost() {
            return careCost;
        }

        public EmployeeCost setCareCost(BigDecimal careCost) {
            this.careCost = careCost;
            return this;
        }

        public BigDecimal getPension() {
            return pension;
        }

        public EmployeeCost setPension(BigDecimal pension) {
            this.pension = pension;
            return this;
        }

        // 流式编程风格的 builder 方法
        public static EmployeeCostBuilder builder() {
            return new EmployeeCostBuilder();
        }

        public static class EmployeeCostBuilder {
            private BigDecimal healthCostWithNoCare;
            private BigDecimal careCost;
            private BigDecimal pension;

            public EmployeeCostBuilder healthCostWithNoCare(BigDecimal healthCostWithNoCare) {
                this.healthCostWithNoCare = healthCostWithNoCare;
                return this;
            }

            public EmployeeCostBuilder careCost(BigDecimal careCost) {
                this.careCost = careCost;
                return this;
            }

            public EmployeeCostBuilder pension(BigDecimal pension) {
                this.pension = pension;
                return this;
            }

            public EmployeeCost build() {
                return new EmployeeCost(healthCostWithNoCare, careCost, pension);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmployeeCost that = (EmployeeCost) o;
            return Objects.equals(healthCostWithNoCare, that.healthCostWithNoCare) &&
                    Objects.equals(careCost, that.careCost) &&
                    Objects.equals(pension, that.pension);
        }

        @Override
        public int hashCode() {
            return Objects.hash(healthCostWithNoCare, careCost, pension);
        }

        @Override
        public String toString() {
            return "EmployeeCost{" +
                    "healthCostWithNoCare=" + healthCostWithNoCare +
                    ", careCost=" + careCost +
                    ", pension=" + pension +
                    '}';
        }
    }

    /**
     * 雇主承担的费用结构体
     */
    public static class EmployerCost {
        /**
         * 无介护健康保险金额
         */
        private BigDecimal healthCostWithNoCare;

        /**
         * 介护保险金额
         */
        private BigDecimal careCost;

        /**
         * 厚生年金金额
         */
        private BigDecimal pension;

        // 默认构造函数
        public EmployerCost() {
        }

        // 全参构造函数
        public EmployerCost(BigDecimal healthCostWithNoCare, BigDecimal careCost, BigDecimal pension) {
            this.healthCostWithNoCare = healthCostWithNoCare;
            this.careCost = careCost;
            this.pension = pension;
        }

        // Getter 和 Setter 方法（流式风格）
        public BigDecimal getHealthCostWithNoCare() {
            return healthCostWithNoCare;
        }

        public EmployerCost setHealthCostWithNoCare(BigDecimal healthCostWithNoCare) {
            this.healthCostWithNoCare = healthCostWithNoCare;
            return this;
        }

        public BigDecimal getCareCost() {
            return careCost;
        }

        public EmployerCost setCareCost(BigDecimal careCost) {
            this.careCost = careCost;
            return this;
        }

        public BigDecimal getPension() {
            return pension;
        }

        public EmployerCost setPension(BigDecimal pension) {
            this.pension = pension;
            return this;
        }

        // 流式编程风格的 builder 方法
        public static EmployerCostBuilder builder() {
            return new EmployerCostBuilder();
        }

        public static class EmployerCostBuilder {
            private BigDecimal healthCostWithNoCare;
            private BigDecimal careCost;
            private BigDecimal pension;

            public EmployerCostBuilder healthCostWithNoCare(BigDecimal healthCostWithNoCare) {
                this.healthCostWithNoCare = healthCostWithNoCare;
                return this;
            }

            public EmployerCostBuilder careCost(BigDecimal careCost) {
                this.careCost = careCost;
                return this;
            }

            public EmployerCostBuilder pension(BigDecimal pension) {
                this.pension = pension;
                return this;
            }

            public EmployerCost build() {
                return new EmployerCost(healthCostWithNoCare, careCost, pension);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmployerCost that = (EmployerCost) o;
            return Objects.equals(healthCostWithNoCare, that.healthCostWithNoCare) &&
                    Objects.equals(careCost, that.careCost) &&
                    Objects.equals(pension, that.pension);
        }

        @Override
        public int hashCode() {
            return Objects.hash(healthCostWithNoCare, careCost, pension);
        }

        @Override
        public String toString() {
            return "EmployerCost{" +
                    "healthCostWithNoCare=" + healthCostWithNoCare +
                    ", careCost=" + careCost +
                    ", pension=" + pension +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialInsuranceDomainDto that = (SocialInsuranceDomainDto) o;
        return Objects.equals(employeeCost, that.employeeCost) &&
                Objects.equals(employerCost, that.employerCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeCost, employerCost);
    }

    @Override
    public String toString() {
        return "SocialInsuranceDomainDto{" +
                "employeeCost=" + employeeCost +
                ", employerCost=" + employerCost +
                '}';
    }
}

