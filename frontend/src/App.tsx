import React from 'react';
import { connect } from 'react-redux';
import './App.scss';
import LoginPage from './pages/login';

const App = (props: any) => {
  let isLogin = props.user.isLogin;
  if(isLogin){
    return (
      <div>
        Login
      </div>
    );
  }else{
    return (
      <div>
        <LoginPage />
      </div>
    );
  }
}

const mapStateToProps = (state: any) => {
  return {
    user: state.user
  };
};

const mapDispatchToProps = (dispatch: any) => {
  return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
