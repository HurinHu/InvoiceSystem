import { User, LOGIN, LOGOUT, REMEMBER, UserActionTypes } from '../stores/user';

const initialUserState: User = {
  user: '',
  email: '',
  role: '',
  password: '',
  remember: true,
  isLogin: false
}

export function userReducer(
  state = initialUserState,
  action: UserActionTypes
): User {
  switch (action.type) {
    case LOGIN:
      return { ...state, user: action.payload.user, email: action.payload.email?action.payload.email:state.email, role: action.payload.role, password: action.payload.password?action.payload.password:state.password, remember: action.payload.remember?action.payload.remember:state.remember, isLogin: action.payload.isLogin };
    case LOGOUT:
      return { ...state, user: '', email: '', role: '', password: '', remember: true, isLogin: false };
    case REMEMBER:
      return { ...state, remember: action.payload };
    default:
      return state;
  }
}