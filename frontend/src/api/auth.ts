export async function authLogin(email: string, password: string): Promise<any> {
    const response = await fetch('./api/auth', {
        method: 'POST',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        body: 'email=' + email + '&password=' + password
    });
    return response;
}

export async function getUser(): Promise<any> {
    const response = await fetch('./api/user', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    });
    return response;
}

export async function Logout(): Promise<any> {
    const response = await fetch('./api/logout', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    });
    return response;
}