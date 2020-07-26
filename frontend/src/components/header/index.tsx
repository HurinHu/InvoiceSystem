import React, { Component, createRef } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { BASE_URL } from '../../constants';
import { CheckUser, UserLogout } from '../../actions';
import { User } from '../../stores';
import './header.scss';

class Header extends Component<{ user: User, UserLogout: any, CheckUser: any }, {  }> {

  public refs: any;
  public userdropdownRef: any;

  constructor(props: any) {
    super(props);
    this.refs = createRef();
  }

  setDivRef = (element: any) => {
    this.userdropdownRef = element;
  }

  userDropdown = () => {
    this.userdropdownRef.classList.toggle('show');
  }

  handleLogout = () => {
    this.props.UserLogout();
  }

  render() {
    this.props.CheckUser();
    document.body.classList.add('loaded');
    return (
      <div>
        <Navbar bg="light" expand="lg">
          <Navbar.Brand href={ BASE_URL+'/' } >InvoiceSystem</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link href="#home">Home</Nav.Link>
              <Nav.Link href="#link">Link</Nav.Link>
              <NavDropdown title="Dropdown" id="basic-nav-dropdown">
                <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
              </NavDropdown>
            </Nav>
            <Nav className="show dropdown nav-item">
              <div aria-haspopup="true" aria-expanded="true" onClick={ this.userDropdown } className="nav-link" role="button"><i className="fas fa-user-circle font-16 padding-right-6"></i>{ this.props.user.user }</div>
              <div aria-labelledby="basic-nav-dropdown" ref={ this.setDivRef } className="dropdown-menu user-dropdown">
                <div onClick={ this.handleLogout } className="dropdown-item pointer"><i className="fas fa-sign-out-alt padding-right-6"></i>Logout</div>
              </div>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
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
  return {
    CheckUser: bindActionCreators(CheckUser, dispatch),
    UserLogout: bindActionCreators(UserLogout, dispatch)
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Header);