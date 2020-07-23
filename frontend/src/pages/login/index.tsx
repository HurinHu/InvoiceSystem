import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Card, Form, Button } from 'react-bootstrap';
import './login.scss';

class LoginPage extends Component<{}, { hide: boolean }> {
  constructor(props: any) {
    super(props);
    this.state = {
      hide: true
    }
  }

  handleShow = (event: any) => {
   if (event.type === "mousedown") {
      this.setState({ hide: false });
    } else {
      this.setState({ hide: true });
    }
  }

  render() {
    return (
      <div className="login">
        <Card className="loginForm">
          <Card.Body>
            <div className="center font-16 font-weight-6">Login</div>
            <Form>
              <Form.Group controlId="formEmail">
                <i className="icon fas fa-envelope"></i>
                <Form.Control type="email" placeholder="Enter email" />
              </Form.Group>

              <Form.Group controlId="formPassword">
                <i className="icon fas fa-key"></i>
                <Form.Control type={this.state.hide ? 'password' : 'text'} placeholder="Password" />
                { this.state.hide ? (<i className="showPassword fas fa-eye-slash" onMouseDown={this.handleShow}></i>) : (<i className="showPassword fas fa-eye" onMouseUp={this.handleShow}></i>) }
              </Form.Group>
              <Form.Group controlId="formCheckbox">
                <Form.Check type="switch" label="Remember" />
              </Form.Group>
              <Button variant="primary" type="submit">
                Register
              </Button>
              <Button variant="primary" type="submit">
                Login
              </Button>
            </Form>
          </Card.Body>
        </Card>
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

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);