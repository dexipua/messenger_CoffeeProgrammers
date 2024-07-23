import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = 'http://localhost:8080/auth';

const apiClient = axios.create({
    baseURL: API_URL,
    withCredentials: true
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

    async login(username, password) {
        return await this.handleRequest(
            () => apiClient.post(`${API_URL}/login`, {
                params: {
                    username: username
                }
            }));
    }

    async logVer(username, password, code) {
        const data = await this.handleRequest(
            () => apiClient.post(`${API_URL}/verification/login`, {
                username: username,
                password: password,
                code: code
            }));
        Cookies.set('token', data.token);
        Cookies.set('id', data.userId);
        Cookies.set('firstName', data.firstName);
        Cookies.set('lastName', data.lastName);

        console.log(Cookies.get('token'))
        console.log(Cookies.get('id'))
        console.log(Cookies.get('firstName'))
        console.log(Cookies.get('lastName'))
        return data;
    }

    async reg(username, password) {
        return await this.handleRequest(
            () => apiClient.post(`${API_URL}/login`, {
                params: {
                    username: username,
                }
            }));
    }

    async regVer(username, password, code, description, firstName, lastName,) {
        const data = await this.handleRequest(
            () => apiClient.post(`${API_URL}/verification/regis`, {
                params: {
                    description: description,
                    firstName: firstName,
                    lastName: lastName,
                    username: username,
                    password: password,
                    code: code
                }
            }));
        Cookies.set('token', data.token);
        Cookies.set('id', data.userId);
        Cookies.set('firstName', data.firstName);
        Cookies.set('lastName', data.lastName);

        console.log(Cookies.get('token'))
        console.log(Cookies.get('id'))
        console.log(Cookies.get('firstName'))
        console.log(Cookies.get('lastName'))
        return data;
    }

    async check(){
        const token = Cookies.get('token')
        return await this.handleRequest(
            () => apiClient.get(`${API_URL}/check`, {
                message: token
            }));
    }

    async refresh(){
        const token = Cookies.get('token')
        const data =  await this.handleRequest(
            () => apiClient.get(`${API_URL}/refresh`, {
                message: token
            }));
        Cookies.set('token', data.token)
    }
}

export default new AuthService();
