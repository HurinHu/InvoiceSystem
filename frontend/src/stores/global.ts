export interface Global {
  loading: boolean
}

export const LOADING = 'LOADING';
export const LOADED = 'LOADED';

interface LoadingAction {
  type: typeof LOADING,
  payload: boolean
}

interface LoadedAction {
  type: typeof LOADED,
  payload: boolean
}

export type GlobalActionTypes = LoadingAction | LoadedAction;