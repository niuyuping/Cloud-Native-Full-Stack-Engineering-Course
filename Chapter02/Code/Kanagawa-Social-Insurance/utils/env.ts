/**
 * 获取运行环境
 * 从环境变量 NODE_ENV 或 NEXT_PUBLIC_ENV 获取
 */
export const getRuntimeEnv = (): 'dev' | 'test' | 'prod' => {
  // 优先使用 NEXT_PUBLIC_ENV（可以在客户端访问）
  const env = process.env.NEXT_PUBLIC_ENV || process.env.NODE_ENV;
  
  if (env === 'production' || env === 'prod') {
    return 'prod';
  }
  if (env === 'test') {
    return 'test';
  }
  // 默认为 dev
  return 'dev';
};

/**
 * 根据运行环境获取 API 基地址
 */
export const getApiBaseUrl = (): string => {
  const env = getRuntimeEnv();
  
  const apiUrls = {
    prod: 'https://social-insurance-backend-chapter03-811681750681.asia-northeast1.run.app',
    test: 'http://localhost',
    dev: 'http://localhost:9002',
  };
  
  return apiUrls[env];
};

