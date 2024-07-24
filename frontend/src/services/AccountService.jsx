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

    async getAll(page, size) {
        return await this.handleRequest(
            () => apiClient.get(`${API_URL}/getAll`, {
                params: {
                    page: page,
                    size: size
                }
            }));
    }

    async getAllContacts() {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () => apiClient.get(`${API_URL}/getAllContacts/${userId}`));
    }

    async getAllByName(firstName, lastName,
                       page, size) {
        return await this.handleRequest(
            () =>
                apiClient.get(`${API_URL}/getAllByName`, {
                    params: {
                        firstName: firstName,
                        lastName: lastName,
                        page: page,
                        size: size
                    }
                }));
    }

    async getById(id) {
        return await this.handleRequest(
            () =>
                apiClient.get(`${API_URL}/getById/${id}`));
    }

    async update(firstName, lastName, description) {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () => apiClient.post(`/update/${userId}`,
                {
                    firstName: firstName,
                    lastName: lastName,
                    description: description
                }
            ));
    }

    async removeFromContact(accountId, contactId) {
        return await this.handleRequest(
            () =>
                apiClient.delete(`/${accountId}/removeContact/${contactId}`));
    }
}

export default new AccountService();