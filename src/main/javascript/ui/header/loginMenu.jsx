import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import Immutable from 'immutable';
import LoginMenuItem from './loginMenuItem';
import { authenticated } from '../../authentication';

/**
 * React Redux function to take the Redux state and produce an object of Props that this Component needs
 * @param {Immutable.Map} state The state to consume
 * @returns {Object} The props to pass into the component
 */
function mapStateToProps(state) {
    return {
        menu: state.getIn(['authentication', 'providers'], Immutable.List()).toJS()
    };
}

/**
 * React Redux function to produce Props that represent Action Creators to use
 * @param {any} The Redux Dispatch mechanism
 * @returns {Object} The props to pass into the component
 */
function mapDispatchToProps(dispatch) {
    return {
        authenticated: (accessToken, expiry) => dispatch(authenticated(accessToken, expiry))
    };
}

/**
 * Component representing the login menu
 */
@translate()
@connect(mapStateToProps, mapDispatchToProps)
export default class LoginMenu extends React.Component {
    /**
     * Actually render the Login menu
     * @returns {React.Component} the Login menu
     */
    render() {
        const { t, menu } = this.props;

        const menuItems = menu.map((provider) => <LoginMenuItem provider={ provider } onClick={ this.handleClick.bind(this) }/>);

        return <li className="nav-item dropdown">
            <a className="nav-link dropdown-toggle" id="headerLoginMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                { t('authentication.menu') }
            </a>
            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="headerLoginMenu">
                { menuItems }
            </div>
        </li>;
    }

    handleClick(provider) {
        window.handleAuthentication = (accessToken, expiry) => {
            this.props.authenticated(accessToken, expiry);
        };

        window.open(provider.url, null, 'menubar=no,toolbar=no,location=no,resizable=yes,scrollbars=yes,status=yes');
    }
};