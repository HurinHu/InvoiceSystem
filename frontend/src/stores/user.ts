export interface User {
  user: string,
  email: string,
  role: string,
  isLogin: boolean
}

export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';

interface LoginAction {
  type: typeof LOGIN,
  payload: User
}

interface LogoutAction {
  type: typeof LOGOUT
}

export type UserActionTypes = LoginAction | LogoutAction;