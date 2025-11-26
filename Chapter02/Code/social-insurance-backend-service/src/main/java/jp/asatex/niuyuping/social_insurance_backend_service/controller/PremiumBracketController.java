package jp.asatex.niuyuping.social_insurance_backend_service.controller;

import jp.asatex.niuyuping.social_insurance_backend_service.application.PremiumBracketApplicationService;
import jp.asatex.niuyuping.social_insurance_backend_service.application.dto.SocialInsuranceApplicationDto;
import jp.asatex.niuyuping.social_insurance_backend_service.controller.dto.SocialInsuranceDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 保险费等级 Controller
 * 提供 RESTful API 接口，采用 WebFlux 响应式编程风格
 */
@RestController
@RequestMapping("/")
public class PremiumBracketController {

    private final PremiumBracketApplicationService premiumBracketApplicationService;

    public PremiumBracketController(PremiumBracketApplicationService premiumBracketApplicationService) {
        this.premiumBracketApplicationService = premiumBracketApplicationService;
    }

    /**
     * 查询社会保险金额
     * GET /socialInsuranceQuery?monthlySalary=650000&age=35
     *
     * @param monthlySalary 月薪
     * @param age 年龄
     * @return Mono<SocialInsuranceDto> 社会保险金额 DTO
     */
    @GetMapping("/socialInsuranceQuery")
    public Mono<SocialInsuranceDto> socialInsuranceQuery(
            @RequestParam("monthlySalary") Integer monthlySalary,
            @RequestParam("age") Integer age) {
        return premiumBracketApplicationService.socialInsuranceQuery(monthlySalary, age)
                .map(this::convertToDto);
    }

    /**
     * 将 Application DTO 转换为 Controller DTO
     * 采用流式编程风格进行数据转换
     *
     * @param applicationDto Application DTO
     * @return Controller DTO
     */
    private SocialInsuranceDto convertToDto(SocialInsuranceApplicationDto applicationDto) {
        // 转换雇员费用结构体
        SocialInsuranceApplicationDto.EmployeeCost applicationEmployeeCost = applicationDto.getEmployeeCost();
        SocialInsuranceDto.EmployeeCost controllerEmployeeCost = SocialInsuranceDto.EmployeeCost.builder()
                .healthCostWithNoCare(applicationEmployeeCost.getHealthCostWithNoCare())
                .careCost(applicationEmployeeCost.getCareCost())
                .pension(applicationEmployeeCost.getPension())
                .build();

        // 转换雇主费用结构体
        SocialInsuranceApplicationDto.EmployerCost applicationEmployerCost = applicationDto.getEmployerCost();
        SocialInsuranceDto.EmployerCost controllerEmployerCost = SocialInsuranceDto.EmployerCost.builder()
                .healthCostWithNoCare(applicationEmployerCost.getHealthCostWithNoCare())
                .careCost(applicationEmployerCost.getCareCost())
                .pension(applicationEmployerCost.getPension())
                .build();

        // 构建控制层 DTO
        return SocialInsuranceDto.builder()
                .employeeCost(controllerEmployeeCost)
                .employerCost(controllerEmployerCost)
                .build();
    }
}

