import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = 'http://localhost:8080/chats';

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

class ChatService {
    async handleRequest(request) {
        try {
            const response = await request();
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }

    async getAllByUserId() {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () => apiClient.get(`/findByAccountId/${userId}`));
    }
}

export default new ChatService();
