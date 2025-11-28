
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { YenIcon, CalculatorIcon } from './Icons';
import { AppDispatch, RootState } from '../store/store';
import { setSalary, setBirthDate, calculateResult } from '../store/calculatorSlice';

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
            <div>
                <label htmlFor="salary" className="block text-sm font-medium text-slate-300">
                    月收入
                </label>
                <div className="mt-1 relative rounded-md shadow-sm">
                    <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                        <YenIcon />
                    </div>
                    <input
                        type="number"
                        name="salary"
                        id="salary"
                        className="bg-slate-700/50 focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 pr-3 py-3 sm:text-sm border-slate-600 rounded-md placeholder-slate-400"
                        placeholder="例如: 300000"
                        value={salary}
                        onChange={(e) => dispatch(setSalary(e.target.value))}
                        required
                        aria-label="月收入"
                    />
                </div>
            </div>

            <div>
                 <label htmlFor="birthDate" className="block text-sm font-medium text-slate-300">
                    出生日期
                </label>
                <div className="mt-1">
                    <input
                        type="date"
                        name="birthDate"
                        id="birthDate"
                        className="bg-slate-700/50 focus:ring-blue-500 focus:border-blue-500 block w-full py-3 px-3 sm:text-sm border-slate-600 rounded-md placeholder-slate-400"
                        value={birthDate}
                        onChange={(e) => dispatch(setBirthDate(e.target.value))}
                        required
                        aria-label="出生日期"
                        max="2025-03-01"
                    />
                </div>
                <p id="birth-date-description" className="mt-2 text-xs text-slate-400">
                    年龄在40至64岁之间（以2025年3月1日为准）的个人将计算护理保险费。
                </p>
            </div>


            {error && status === 'failed' && <p className="text-red-400 text-sm" role="alert">{error}</p>}

            <div>
                <button
                    type="submit"
                    disabled={isLoading}
                    className="w-full flex justify-center items-center py-3 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-slate-900 focus:ring-blue-500 disabled:bg-blue-800/50 disabled:cursor-not-allowed transition-colors"
                >
                    {isLoading ? (
                        <>
                            <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                            </svg>
                            计算中...
                        </>
                    ) : (
                        <>
                            <CalculatorIcon />
                            开始计算
                        </>
                    )}
                </button>
            </div>
        </form>
    );
};

export default CalculatorForm;