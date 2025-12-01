import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { ResultRow } from './shared/ResultRow';
import { formatCurrency } from '../utils/formatters';

const ResultsDisplay: React.FC = () => {
    const { result, status } = useSelector((state: RootState) => state.calculator);

    if (status !== 'succeeded' || !result) {
        return null;
    }

    return (
        <div className="border-t border-slate-700 mt-6 pt-6 animate-fade-in">
            <h3 className="text-lg leading-6 font-medium text-white text-center mb-6">
                计算结果
            </h3>
            
            <dl className="space-y-2 px-6 sm:px-8 pb-6">
                <ResultRow label="您的月收入" value={formatCurrency(result.salary)} />
                {result.standardRemuneration > 0 && (
                    <ResultRow 
                        label="标准报酬月额" 
                        value={formatCurrency(result.standardRemuneration)} 
                    />
                )}

                <div className="pt-4">
                    <h4 className="text-md font-medium text-slate-300 border-b border-slate-600 pb-2 mb-2">
                        社会保险费（个人负担）
                    </h4>
                    <ResultRow 
                        label="健康保险费" 
                        value={formatCurrency(result.healthInsurance)} 
                    />
                    {result.longTermCareInsurance > 0 && (
                        <ResultRow 
                            label="护理保险费" 
                            value={formatCurrency(result.longTermCareInsurance)} 
                        />
                    )}
                    <ResultRow 
                        label="厚生年金保险费" 
                        value={formatCurrency(result.pensionInsurance)} 
                    />
                    <ResultRow 
                        label="社会保险费合计" 
                        value={formatCurrency(result.totalDeduction)} 
                        isHighlighted={true} 
                    />
                </div>

                <div className="pt-4">
                    <div className="flex justify-between py-4 px-4 bg-blue-900/50 rounded-lg items-center">
                        <dt className="text-lg font-bold text-white">到手收入（估算）</dt>
                        <dd className="text-2xl font-bold text-blue-300">
                            {formatCurrency(result.takeHomePay)}
                        </dd>
                    </div>
                </div>
            </dl>
        </div>
    );
};

export default ResultsDisplay;