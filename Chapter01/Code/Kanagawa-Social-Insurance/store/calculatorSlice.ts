
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { CalculationResult, CalculatorState } from '../types';
import { insuranceData } from '../data/insuranceData';

/**
 * Implements the specific rounding rule for Japanese social insurance premiums.
 * - If the decimal part is <= 0.5, it's rounded down (truncated).
 * - If the decimal part is > 0.5, it's rounded up.
 * @param amount The amount to round.
 * @returns The rounded integer amount.
 */
const japaneseRounding = (amount: number): number => {
    const integerPart = Math.floor(amount);
    const decimalPart = amount - integerPart;
    if (decimalPart <= 0.5) {
        return integerPart;
    }
    return integerPart + 1;
};


const initialState: CalculatorState = {
    salary: '',
    birthDate: '',
    result: null,
    status: 'idle',
    error: null,
};

export const calculateResult = createAsyncThunk<
    CalculationResult,
    { salaryStr: string; birthDateStr: string },
    { rejectValue: string }
>(
    'calculator/calculate',
    async ({ salaryStr, birthDateStr }, { rejectWithValue }) => {
        // Simulate a small delay for better UX
        await new Promise(resolve => setTimeout(resolve, 300));

        const salary = Number(salaryStr);
        if (isNaN(salary) || salary <= 0) {
            return rejectWithValue('请输入有效的月收入。');
        }
        
        if (!birthDateStr) {
            return rejectWithValue('请输入您的出生日期。');
        }

        const calculationDate = new Date('2025-03-01');
        const birthDate = new Date(birthDateStr);
        
        if (isNaN(birthDate.getTime())) {
             return rejectWithValue('请输入有效的出生日期。');
        }

        let age = calculationDate.getFullYear() - birthDate.getFullYear();
        const m = calculationDate.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && calculationDate.getDate() < birthDate.getDate())) {
            age--;
        }
        
        const isLongTermCareApplicable = age >= 40 && age <= 64;

        const row = insuranceData.find(r => salary >= r.salaryMin && salary < r.salaryMax);

        if (!row) {
            return rejectWithValue('未找到该月收入对应的保险费数据，请核对输入值。');
        }

        const healthInsuranceRaw = row.healthInsuranceNoCare;
        const longTermCareInsuranceRaw = isLongTermCareApplicable ? (row.healthInsuranceWithCare - row.healthInsuranceNoCare) : 0;
        const pensionRaw = row.pensionInsurance;

        const healthInsurance = japaneseRounding(healthInsuranceRaw);
        const longTermCareInsurance = japaneseRounding(longTermCareInsuranceRaw);
        const pensionInsurance = japaneseRounding(pensionRaw);

        const totalDeduction = healthInsurance + longTermCareInsurance + pensionInsurance;

        return {
            salary: salary,
            standardRemuneration: row.standardRemuneration,
            healthInsurance: healthInsurance,
            longTermCareInsurance: longTermCareInsurance,
            pensionInsurance: pensionInsurance,
            totalDeduction: totalDeduction,
            takeHomePay: salary - totalDeduction,
        };
    }
);

const calculatorSlice = createSlice({
    name: 'calculator',
    initialState,
    reducers: {
        setSalary: (state, action: PayloadAction<string>) => {
            state.salary = action.payload;
        },
        setBirthDate: (state, action: PayloadAction<string>) => {
            state.birthDate = action.payload;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(calculateResult.pending, (state) => {
                state.status = 'loading';
                state.result = null;
                state.error = null;
            })
            .addCase(calculateResult.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.result = action.payload;
            })
            .addCase(calculateResult.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload ?? '发生未知错误。';
            });
    },
});

export const { setSalary, setBirthDate } = calculatorSlice.actions;

export default calculatorSlice.reducer;