import React from 'react';

export const Footer: React.FC = () => {
  return (
    <footer className="text-center mt-10 text-xs text-slate-500">
      <p>※ 此计算为估算值。准确金额请参照您的工资单。</p>
      <p>※ 未包含雇佣保险费、所得税及居民税。</p>
      <p>※ 儿童及育儿支持金(0.36%)由雇主承担，不包含在到手收入计算中。</p>
    </footer>
  );
};

