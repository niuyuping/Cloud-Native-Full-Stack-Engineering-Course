
import React from 'react';
import CalculatorForm from './components/CalculatorForm';
import ResultsDisplay from './components/ResultsDisplay';

// FIX: Refactored the component to align with the Redux architecture.
// Removed local state (useState), calculation logic, and props from child components.
// CalculatorForm and ResultsDisplay are now connected to the Redux store and
// get data from there. This resolves the TypeScript errors related to incorrect props.
// The JSX structure has also been updated to correctly contain both components within a single card.
const App: React.FC = () => {
    return (
        <div className="min-h-screen bg-gray-900 flex flex-col items-center justify-center p-4 font-sans">
            <div className="w-full max-w-lg mx-auto">
                <div className="text-center mb-8">
                    <h1 className="text-3xl font-bold text-white sm:text-4xl">社会保险费 & 到手收入计算器</h1>
                    <p className="mt-2 text-md text-gray-400">2025年11月・神奈川县支部</p>
                </div>
                
                <div className="bg-gray-800 border border-gray-700 rounded-lg shadow-2xl shadow-cyan-500/10 overflow-hidden">
                    <div className="p-6 sm:p-8">
                        <CalculatorForm />
                    </div>
                    <ResultsDisplay />
                </div>

                <footer className="text-center mt-10 text-xs text-gray-500">
                    <p>※ 此计算为估算值。准确金额请参照您的工资单。</p>
                    <p>※ 未包含雇佣保险费、所得税及居民税。</p>
                    <p>※ 儿童及育儿支持金(0.36%)由雇主承担，不包含在到手收入计算中。</p>
                </footer>
            </div>
        </div>
    );
};

export default App;