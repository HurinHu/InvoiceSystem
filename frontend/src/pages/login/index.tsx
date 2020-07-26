import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { Card, Form, Button } from 'react-bootstrap';
import { User } from '../../stores';
import { Login, authRemember } from '../../actions';
import './login.scss';

class LoginPage extends Component<{ user: User, authRemember: any, Login: any }, { hide: boolean, register: boolean, email: string, password: string }> {

  public pwdInputRef: any;
  public loginBtnRef: any;

  constructor(props: any) {
    super(props);
    this.state = {
      hide: true,
      register: false,
      email: '',
      password: ''
    }
  }

  handleShow = (event: any) => {
   if (event.type === "mousedown") {
      this.setState({ hide: false });
    } else {
      this.setState({ hide: true });
    }
  }

  handleRegister = (event: any) => {
    event.preventDefault();
    this.setState({ register: !this.state.register });
  }

  handleRemember = (event: any) => {
    this.props.authRemember(event.target.checked);
  }

  handleLogin = (event: any) => {
    this.props.Login(this.state.email, this.state.password);
  }

  setDivRef = (name: string, element: any) => {
    if(name === 'password'){
      this.pwdInputRef = element;
    }else if(name === 'login'){
      this.loginBtnRef = element;
    }
  }

  updateInput = (name: string, value: any) => {
    if(name === 'email'){
      this.setState({ email: value });
    }else if(name === 'password'){
      this.setState({ password: value });
    }
  }

  onKeyPress = (name: string, code: number) => {
    if(code === 13){
      if(name === 'email') {
        this.pwdInputRef.focus();
      }else if(name === 'password') {
        this.loginBtnRef.click();
      }
    }
  }

  render() {
    document.body.classList.add('loaded');
    if(!this.state.register){
      return (
        <div className="login">
          <div className="font-20 font-weight-6 center title padding-bottom-12">Invoice System</div>
          <Card className="loginForm">
            <Card.Body>
              <div className="center font-16 font-weight-6 padding-bottom-6">Login</div>
              <Form>
                <Form.Group controlId="formEmail">
                  <i className="icon fas fa-envelope"></i>
                  <Form.Control type="email" value={ this.state.email } onChange={ (event: any) => this.updateInput('email', event.target.value) }  onKeyPress={ (event: any) => this.onKeyPress('email', event.charCode) } placeholder="Enter email" />
                </Form.Group>

                <Form.Group controlId="formPassword">
                  <i className="icon fas fa-key"></i>
                  <Form.Control type={this.state.hide ? 'password' : 'text'} value={ this.state.password } onChange={ (event) => this.updateInput('password', event.target.value) } ref={ (element: any) => this.setDivRef('password', element) } onKeyPress={ (event: any) => this.onKeyPress('password', event.charCode) } placeholder="Password" />
                  { this.state.hide ? (<i className="showPassword fas fa-eye-slash" onMouseDown={this.handleShow}></i>) : (<i className="showPassword fas fa-eye" onMouseUp={this.handleShow}></i>) }
                </Form.Group>
                <Form.Group controlId="formCheckbox">
                  <Form.Check type="switch" label="Remember" checked={this.props.user.remember} onChange={this.handleRemember} />
                </Form.Group>
                <div className="center">
                  <Button variant="success" type="button" onClick={this.handleRegister} size="sm">
                    Register
                  </Button>
                  &nbsp;&nbsp;
                  <Button variant="primary" ref={ (element: any) => this.setDivRef('login', element) } type="button" onClick={this.handleLogin} size="sm">
                    Login
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </div>
      );
    }else{
      return (
        <div className="login">
          <Card className="loginForm">
            <div className="font-20 font-weight-6 center title padding-bottom-12">Invoice System</div>
            <Card.Body>
              <div className="center font-16 font-weight-6">Register</div>
              <Form>

                <div className="center">
                  <Button variant="success" type="submit" size="sm">
                    Register
                  </Button>
                  <br />
                  <div className="font-8 padding-top-8 pointer fg-grey1" onClick={this.handleRegister}>Already have account?</div>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </div>
      );
    }
  }
}

const mapStateToProps = (state: any) => {
  return {
    user: state.user
  };
};

const mapDispatchToProps = (dispatch: any) => {
  return {
    authRemember: bindActionCreators(authRemember, dispatch),
    Login: bindActionCreators(Login, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);