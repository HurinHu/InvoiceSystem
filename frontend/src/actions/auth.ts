import { User, LOGIN, LOGOUT, UserActionTypes } from '../stores/user';

export function login(user: User): UserActionTypes {
  return {
    type: LOGIN,
    payload: user
  }
}

export function logout(): UserActionTypes {
  return {
    type: LOGOUT
  }
}