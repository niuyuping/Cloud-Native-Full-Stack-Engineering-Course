/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  // 启用 standalone 输出模式，用于 Docker 部署
  output: 'standalone',
  typescript: {
    ignoreBuildErrors: false,
  },
  env: {
    // 将环境变量暴露给客户端（需要 NEXT_PUBLIC_ 前缀）
    // 注意：NEXT_PUBLIC_* 变量在构建时嵌入，运行时无法更改
    // 如需在运行时更改，需要在构建时通过 ARG 传递
    NEXT_PUBLIC_ENV: process.env.NEXT_PUBLIC_ENV || process.env.NODE_ENV || 'dev',
  },
};

export default nextConfig;

