import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = 'http://localhost:8080/accounts';

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

class AccountService {
    async handleRequest(request) {
        try {
            const response = await request();
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }

    async getAll() {
        return await this.handleRequest(
            () => apiClient.get(`${API_URL}/getAll`));
    }

    async getAllContacts() {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () => apiClient.get(`${API_URL}/getAllContacts/${id}`));
    }

    async getAllByName({firstName, lastName}) {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () =>
                apiClient.get(`${API_URL}/getAllByName/?firstName=${firstName}&lastName=${lastName}`));
    }

    async update(firstName, lastName, description) {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () => apiClient.post(`${API_URL}/update/${id}`,
                {
                    param: {
                        firstName: firstName,
                        lastName: lastName,
                        description: description
                    }
                }
            ));
    }
}

export default new AccountService();