export interface CalculationResult {
  salary: number;
  standardRemuneration: number;
  healthInsurance: number;
  longTermCareInsurance: number;
  pensionInsurance: number;
  totalDeduction: number;
  takeHomePay: number;
}

export interface CalculatorState {
    salary: string;
    birthDate: string;
    result: CalculationResult | null;
    status: 'idle' | 'loading' | 'succeeded' | 'failed';
    error: string | null;
}

export interface InsuranceCost {
  healthCostWithNoCare: number;
  careCost: number;
  pension: number;
}

export interface SocialInsuranceDTO {
  employeeCost: InsuranceCost;
  employerCost: InsuranceCost;
}
