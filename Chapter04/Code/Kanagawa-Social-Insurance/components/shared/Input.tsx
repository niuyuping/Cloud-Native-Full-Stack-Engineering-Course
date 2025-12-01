import React from 'react';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  error?: string;
  icon?: React.ReactNode;
  helperText?: string;
}

export const Input: React.FC<InputProps> = ({
  label,
  error,
  icon,
  helperText,
  className = '',
  id,
  ...props
}) => {
  const inputId = id || `input-${label}`;
  const hasError = !!error;

  return (
    <div>
      <label 
        htmlFor={inputId} 
        className="block text-sm font-medium text-slate-300"
      >
        {label}
      </label>
      <div className="mt-1 relative rounded-md shadow-sm">
        {icon && (
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            {icon}
          </div>
        )}
        <input
          id={inputId}
          className={`
            bg-slate-700/50 
            focus:ring-blue-500 
            focus:border-blue-500 
            block 
            w-full 
            ${icon ? 'pl-10' : 'pl-3'} 
            pr-3 
            py-3 
            sm:text-sm 
            border-slate-600 
            rounded-md 
            placeholder-slate-400
            ${hasError ? 'border-red-500' : ''}
            ${className}
          `}
          aria-invalid={hasError}
          aria-describedby={helperText ? `${inputId}-helper` : undefined}
          {...props}
        />
      </div>
      {helperText && !error && (
        <p id={`${inputId}-helper`} className="mt-2 text-xs text-slate-400">
          {helperText}
        </p>
      )}
      {error && (
        <p className="mt-2 text-xs text-red-400" role="alert">
          {error}
        </p>
      )}
    </div>
  );
};

