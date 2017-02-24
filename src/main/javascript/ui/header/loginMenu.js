/*eslint-disable no-unused-vars*/
import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import LoginMenuItem from './loginMenuItem';
import { authenticated, selectProviders } from '../../authentication';
/*eslint-enable no-unused-vars*/

/**
 * React Redux function to take the Redux state and produce an object of Props that this Component needs
 * @param {Immutable.Map} state The state to consume
 * @returns {Object} The props to pass into the component
 */
function mapStateToProps(state) {
    return {
        menu: selectProviders(state)
    };
}

/**
 * React Redux function to produce Props that represent Action Creators to use
 * @param {any} dispatch The Redux Dispatch mechanism
 * @returns {Object} The props to pass into the component
 */
function mapDispatchToProps(dispatch) {
    return {
        authenticated: (accessToken, expiry) => dispatch(authenticated(accessToken, expiry))
    };
}

@translate()
@connect(mapStateToProps, mapDispatchToProps)
/**
 * Component representing the login menu
 */
export default class LoginMenu extends React.Component {
    /**
     * Actually render the Login menu
     * @returns {React.Component} the Login menu
     */
    render() {
        const { t, menu } = this.props;

        const menuItems = menu.map((provider) => <LoginMenuItem key={ provider.provider } provider={ provider } onClick={ this.handleClick.bind(this) }/>);

        if (menuItems.length === 1) {
            return menuItems[0];
        }
        return <li className="nav-item dropdown">
            <a className="nav-link dropdown-toggle" id="headerLoginMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                { t('authentication.menu') }
            </a>
            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="headerLoginMenu">
                { menuItems }
            </div>
        </li>;
    }

    /**
     * Handler for when one of the providers is clicked
     * @param {Object} provider the provider that was clicked
     */
    handleClick(provider) {
        window.handleAuthentication = (accessToken, expiry) => {
            this.props.authenticated(accessToken, expiry);
        };

        window.open(provider.url, null, 'menubar=no,toolbar=no,location=no,resizable=yes,scrollbars=yes,status=yes');
    }
}
