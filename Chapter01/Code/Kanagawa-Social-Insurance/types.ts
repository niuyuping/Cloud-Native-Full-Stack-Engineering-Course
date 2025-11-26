
export interface InsuranceDataRow {
  grade: string;
  standardRemuneration: number;
  salaryMin: number;
  salaryMax: number;
  healthInsuranceNoCare: number; // Employee's share
  healthInsuranceWithCare: number; // Employee's share
  pensionInsurance: number; // Employee's share
}

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
