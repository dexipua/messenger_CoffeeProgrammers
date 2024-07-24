import Cookies from 'js-cookie';

export const useAuth = () => {
    const isAuthenticated = () => {
        return Cookies.get('token') !== undefined;
    };

    return { isAuthenticated};
};
