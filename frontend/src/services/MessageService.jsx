import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = 'http://localhost:8080/messages';

const apiClient = axios.create({
    baseURL: API_URL,
    withCredentials: true
});

apiClient.interceptors.request.use(
    (config) => {
        const token = Cookies.get('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

class MessageService {
    async handleRequest(request) {
        try {
            const response = await request();
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }

    async getAllByChatId(chatId) {
        return await this.handleRequest(
            () => apiClient.get(`/getAllByChat/${chatId}`));
    }
}

export default new MessageService();