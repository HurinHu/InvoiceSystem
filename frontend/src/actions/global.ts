import { LOADING, LOADED } from '../stores/global';

export const loadStatus = (val: boolean) => (dispatch: any) => {
  if(val){
    document.body.classList.remove('loaded');
  }else{
    document.body.classList.add('loaded');
  }
  return dispatch({ type: val?LOADING:LOADED });
}