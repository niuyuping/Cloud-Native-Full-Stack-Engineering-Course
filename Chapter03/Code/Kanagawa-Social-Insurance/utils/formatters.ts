/**
 * 格式化货币金额
 * @param amount 金额
 * @returns 格式化后的货币字符串
 */
export const formatCurrency = (amount: number): string => {
  return `¥${amount.toLocaleString()}`;
};

