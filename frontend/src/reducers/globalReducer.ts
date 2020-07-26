import { Global, LOADING, LOADED, GlobalActionTypes } from '../stores/global';

const initialGlobalState: Global = {
  loading: true
}

export function globalReducer(
  state = initialGlobalState,
  action: GlobalActionTypes
): Global {
  switch (action.type) {
    case LOADING:
      return { ...state, loading: true };
    case LOADED:
      return { ...state, loading: false };
    default:
      return state;
  }
}