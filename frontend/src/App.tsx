import React from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { loadStatus, CheckUser } from './actions';
import './App.scss';
import Header from './components/header';
import LoginPage from './pages/login';

const App = (props: any) => {
  document.body.classList.remove('loaded');
  let isLogin = props.user.isLogin;
  if(isLogin){
    return (
      <div>
        <Header />
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
    user: state.user,
    loading: state.global.loading
  };
};

const mapDispatchToProps = (dispatch: any) => {
  return {
    loadStatus: bindActionCreators(loadStatus, dispatch),
    CheckUser: bindActionCreators(CheckUser, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
