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