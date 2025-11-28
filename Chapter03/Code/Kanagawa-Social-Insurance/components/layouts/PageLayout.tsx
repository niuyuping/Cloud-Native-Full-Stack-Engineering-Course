import React from 'react';
import Head from 'next/head';

interface PageLayoutProps {
  title?: string;
  description?: string;
  children: React.ReactNode;
}

export const PageLayout: React.FC<PageLayoutProps> = ({
  title = '社会保险费 & 到手收入计算器',
  description = '2025年3月・神奈川县支部的社会保险费和到手收入计算。使用Next.js、Redux、Tailwind CSS构建。',
  children,
}) => {
  return (
    <>
      <Head>
        <title>{title}</title>
        <meta name="description" content={description} />
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className="min-h-screen bg-slate-900 flex flex-col items-center justify-center p-4 font-sans text-white">
        <div className="w-full max-w-lg mx-auto">
          <div className="text-center mb-8">
            <h1 className="text-3xl font-bold text-white sm:text-4xl tracking-tight">
              社会保险费 & 到手收入计算器
            </h1>
            <p className="mt-2 text-md text-slate-400">
              2025年3月・神奈川县支部
            </p>
          </div>

          <div className="relative overflow-hidden rounded-2xl bg-slate-800/50 backdrop-blur-xl border border-slate-700 shadow-2xl shadow-blue-500/10">
            {children}
          </div>
        </div>
      </main>
    </>
  );
};

