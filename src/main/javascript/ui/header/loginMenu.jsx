import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import Immutable from 'immutable';
import LoginMenuItem from './loginMenuItem';

/**
 * Component representing the login menu
 */
@translate()
@connect((state) => ({
    menu: state.get('authentication', Immutable.List()).toJS(),
}))
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
        window.open(provider.url, null, 'menubar=no,toolbar=no,location=no,resizable=yes,scrollbars=yes,status=yes');
    }
};