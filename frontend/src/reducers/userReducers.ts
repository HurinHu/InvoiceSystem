import { User, LOGIN, LOGOUT, UserActionTypes } from '../stores/user';

const initialUserState: User = {
  user: '',
  email: '',
  role: '',
  isLogin: false
}

export function userReducer(
  state = initialUserState,
  action: UserActionTypes
): User {
  switch (action.type) {
    case LOGIN:
      return { ...state, user: action.payload.user, email: action.payload.email, role: action.payload.role, isLogin: true };
    case LOGOUT:
      return { ...state, user: '', email: '', role: '', isLogin: false };
    default:
      return state;
  }
}