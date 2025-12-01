
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { CalculationResult, CalculatorState } from '../types';
import { ApiClient } from '../api/ApiClient';
import { CALCULATION_DATE } from '../utils/constants';


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
        const salary = Number(salaryStr);
        if (isNaN(salary) || salary <= 0) {
            return rejectWithValue('请输入有效的月收入。');
        }
        
        if (!birthDateStr) {
            return rejectWithValue('请输入您的出生日期。');
        }

        const calculationDate = new Date(CALCULATION_DATE);
        const birthDate = new Date(birthDateStr);
        
        if (isNaN(birthDate.getTime())) {
             return rejectWithValue('请输入有效的出生日期。');
        }

        let age = calculationDate.getFullYear() - birthDate.getFullYear();
        const m = calculationDate.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && calculationDate.getDate() < birthDate.getDate())) {
            age--;
        }

        try {
            const apiClient = new ApiClient();
            const result = await apiClient.getSocialInsurance(salary, age);

            const healthInsurance = result.employeeCost.healthCostWithNoCare;
            const longTermCareInsurance = result.employeeCost.careCost;
            const pensionInsurance = result.employeeCost.pension;

            const totalDeduction = healthInsurance + longTermCareInsurance + pensionInsurance;

            return {
                salary: salary,
                standardRemuneration: 0, // API 未返回此字段，设为 0
                healthInsurance: healthInsurance,
                longTermCareInsurance: longTermCareInsurance,
                pensionInsurance: pensionInsurance,
                totalDeduction: totalDeduction,
                takeHomePay: salary - totalDeduction,
            };
        } catch (error: any) {
            if (error.response) {
                return rejectWithValue(error.response.data?.message || '获取社会保险数据失败，请稍后重试。');
            } else if (error.request) {
                return rejectWithValue('无法连接到服务器，请检查网络连接。');
            } else {
                return rejectWithValue('发生未知错误，请稍后重试。');
            }
        }
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