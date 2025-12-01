import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { YenIcon, CalculatorIcon } from './Icons';
import { AppDispatch, RootState } from '../store/store';
import { setSalary, setBirthDate, calculateResult } from '../store/calculatorSlice';
import { Input } from './shared/Input';
import { Button } from './shared/Button';
import { CALCULATION_DATE } from '../utils/constants';
import { useState } from 'react';

const CalculatorForm: React.FC = () => {
    const dispatch = useDispatch<AppDispatch>();
    const { salary, birthDate, status, error } = useSelector((state: RootState) => state.calculator);
    const isLoading = status === 'loading';
        
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        dispatch(calculateResult({ salaryStr: salary, birthDateStr: birthDate }));
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-6">
            <Input
                type="number"
                name="salary"
                label="月收入"
                placeholder="例如: 300000"
                value={salary}
                onChange={(e) => dispatch(setSalary(e.target.value))}
                icon={<YenIcon />}
                required
                aria-label="月收入"
            />

            <Input
                type="date"
                name="birthDate"
                label="出生日期"
                value={birthDate}
                onChange={(e) => dispatch(setBirthDate(e.target.value))}
                max={CALCULATION_DATE}
                required
                aria-label="出生日期"
                helperText={`年龄在40至64岁之间（以${CALCULATION_DATE}为准）的个人将计算护理保险费。`}
            />

            {error && status === 'failed' && (
                <p className="text-red-400 text-sm" role="alert">
                    {error}
                </p>
            )}

            <Button
                type="submit"
                isLoading={isLoading}
                icon={<CalculatorIcon />}
            >
                开始计算
            </Button>
        </form>
    );
};

export default CalculatorForm;