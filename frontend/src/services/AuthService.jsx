import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = 'http://localhost:8080/auth';

const apiClient = axios.create({
    baseURL: API_URL,
});

class AuthService {
    async handleRequest(request) {
        try {
            const response = await request();
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }

    async login(username) {
        const data = await this.handleRequest(
            () => apiClient.post(`/login`, {
                message: username
            }));

        Cookies.set('email', username);

        return data;
    }

    async logVer(username, password, code) {
        const data = await this.handleRequest(
            () => apiClient.post(`/verification/login`, {
                username: username,
                password: password,
                code: code
            }));

        Cookies.set('token', data.token);
        Cookies.set('id', data.userId);
        Cookies.set('firstName', data.firstName);
        Cookies.set('lastName', data.lastName);

        return data;
    }

    async reg(username) {
        const data = await this.handleRequest(
            () => apiClient.post(`/registration`, {
                params: {
                    username: username,
                }
            }));

        Cookies.set('email', username)
        return data;
    }

    async regVer(username, password, code, description, firstName, lastName,) {
        const data = await this.handleRequest(
            () => apiClient.post(`/verification/regis`, {
                description: description,
                firstName: firstName,
                lastName: lastName,
                username: username,
                password: password,
                code: code
            }));
        Cookies.set('token', data.token);
        Cookies.set('id', data.userId);
        Cookies.set('firstName', data.firstName);
        Cookies.set('lastName', data.lastName);
        return data;
    }

    async check() {
        const token = Cookies.get('token')
        return await this.handleRequest(
            () => apiClient.get(`/check`, {
                message: token
            }));
    }

    async refresh() {
        const token = Cookies.get('token')
        const data = await this.handleRequest(
            () => apiClient.get(`/refresh`, {
                message: token
            }));
        Cookies.set('token', data.token)
    }

    async regValid(username, password, code, description, firstName, lastName,) {
        const data = await this.handleRequest(
            () => apiClient.post(`/check/reg`, {
                description: description,
                firstName: firstName,
                lastName: lastName,
                username: username,
                password: password,
                code: code
            }));
    }

    async logValid(username, password, code) {
        const data = await this.handleRequest(
            () => apiClient.post(`/check/login`, {
                username: username,
                password: password,
                code: code
            }));
    }
}

export default new AuthService();
