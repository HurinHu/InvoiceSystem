export interface User {
  user: string,
  email: string,
  role: string,
  password: string,
  remember: boolean,
  isLogin: boolean
}

export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';
export const REMEMBER = 'REMEMBER';

interface LoginAction {
  type: typeof LOGIN,
  payload: User
}

interface LogoutAction {
  type: typeof LOGOUT
}

interface RememberAction {
  type: typeof REMEMBER,
  payload: boolean
}

export type UserActionTypes = LoginAction | LogoutAction | RememberAction;