import { LOGIN, LOGOUT, REMEMBER } from '../stores/user';
import { authLogin } from '../api';

export const Login = (email: string, password: string) => async (dispatch: any) => {
  let res = await authLogin(email, password);
  let data = await res.json();
  if(res.status === 200){
    return dispatch({ type: LOGIN, payload: {
      user: data.name,
      email: data.email,
      role: data.role,
      isLogin: true
    } });
  }else{
    return dispatch({ type: LOGIN, payload: {
      user: '',
      role: '',
      isLogin: false
    } });
  }
};

export const authLogout = () => (dispatch: any) => {return dispatch({ type: LOGOUT });};

export const authRemember = (val: boolean) => (dispatch: any) => {return dispatch({ type: REMEMBER, payload: val });};