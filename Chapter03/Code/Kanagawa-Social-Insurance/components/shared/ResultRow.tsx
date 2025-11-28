import React from 'react';

interface ResultRowProps {
  label: string;
  value: string;
  isHighlighted?: boolean;
}

export const ResultRow: React.FC<ResultRowProps> = ({ 
  label, 
  value, 
  isHighlighted = false 
}) => (
  <div className={`flex justify-between py-3 px-4 rounded-lg ${isHighlighted ? 'bg-slate-700/50' : ''}`}>
    <dt className={`text-sm ${isHighlighted ? 'font-semibold text-white' : 'text-slate-400'}`}>
      {label}
    </dt>
    <dd className={`text-sm font-medium ${isHighlighted ? 'text-blue-400' : 'text-slate-200'}`}>
      {value}
    </dd>
  </div>
);

