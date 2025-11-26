
import React from 'react';
import Head from 'next/head';
import CalculatorForm from '../components/CalculatorForm';
import ResultsDisplay from '../components/ResultsDisplay';

const HomePage: React.FC = () => {
    return (
        <>
            <Head>
                <title>社会保险费 & 到手收入计算器 | Next.js & Redux</title>
                <meta name="description" content="2025年3月・神奈川县支部的社会保险费和到手收入计算。使用Next.js、Redux、Tailwind CSS构建。" />
                <link rel="icon" href="/favicon.ico" />
            </Head>

            <main className="min-h-screen bg-slate-900 flex flex-col items-center justify-center p-4 font-sans text-white">
                <div className="w-full max-w-lg mx-auto">
                    <div className="text-center mb-8">
                        <h1 className="text-3xl font-bold text-white sm:text-4xl tracking-tight">社会保险费 & 到手收入计算器</h1>
                        <p className="mt-2 text-md text-slate-400">2025年3月・神奈川县支部</p>
                    </div>

                    <div className="relative overflow-hidden rounded-2xl bg-slate-800/50 backdrop-blur-xl border border-slate-700 shadow-2xl shadow-blue-500/10">
                         <div className="p-6 sm:p-8">
                            <CalculatorForm />
                        </div>
                        <ResultsDisplay />
                    </div>

                    <footer className="text-center mt-10 text-xs text-slate-500">
                        <p>※ 此计算为估算值。准确金额请参照您的工资单。</p>
                        <p>※ 未包含雇佣保险费、所得税及居民税。</p>
                        <p>※ 儿童及育儿支持金(0.36%)由雇主承担，不包含在到手收入计算中。</p>
                    </footer>
                </div>
            </main>
        </>
    );
};

export default HomePage;