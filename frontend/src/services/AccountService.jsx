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

    async getAllContacts() {
        const userId = Cookies.get('id');
        return await this.handleRequest(
            () => apiClient.get(`/getAllContacts/${userId}`));
    }

    async getById(id) {
        return await this.handleRequest(
            () =>
                apiClient.get(`/getById/${id}`));
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

    async addToContact(accountId, contactId){
        return await this.handleRequest(
            () =>
                apiClient.post(`/${accountId}/addContact/${contactId}`));
    }

    async getAllAccountWithoutContacts(accountId, page, size){
        return await this.handleRequest(
            () =>
                apiClient.get(`/notInContactList/${accountId}`,  {
                    params: {
                        page: page,
                        size: size
                    }
                }));
    }

    async getAllAccountWithoutContactsWithSearch(accountId, page, size, firstName, lastName){
        return await this.handleRequest(
            () =>
                apiClient.get(`/notInContactListWithSearch/${accountId}`,  {
                    params: {
                        page: page,
                        size: size,
                        firstName: firstName,
                        lastName: lastName
                    }
                }));
    }
}

export default new AccountService();