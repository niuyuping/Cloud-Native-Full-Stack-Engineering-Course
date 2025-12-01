import axios from 'axios';
import { getApiBaseUrl } from '../utils/env';

const apiClient = axios.create({
  baseURL: getApiBaseUrl(),
});

export default apiClient;

