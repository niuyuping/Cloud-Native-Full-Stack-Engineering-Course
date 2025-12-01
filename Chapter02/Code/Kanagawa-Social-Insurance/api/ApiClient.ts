import apiClient from './axios';
import { SocialInsuranceDTO } from '../types';

export class ApiClient {
  /**
   * 获取社会保险数据
   * @param monthlySalary 月薪
   * @param age 年龄
   * @returns Promise<SocialInsuranceDTO>
   */
  async getSocialInsurance(
    monthlySalary: number,
    age: number
  ): Promise<SocialInsuranceDTO> {
    const response = await apiClient.get<SocialInsuranceDTO>(
      '/socialInsuranceQuery',
      {
        params: {
          monthlySalary,
          age,
        },
      } 
    );
    return response.data;
  }
}

