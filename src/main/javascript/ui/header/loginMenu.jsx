import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import Immutable from 'immutable';

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

        const menuItems = menu.map((i) => <a className="dropdown-item">{ t(`authentication.${i.provider}`) }</a>);

        return <li className="nav-item dropdown">
            <a className="nav-link dropdown-toggle" id="headerLoginMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Login
            </a>
            <div className="dropdown-menu dropdown-menu-right" aria-labelledBy="headerLoginMenu">
                { menuItems }
            </div>
        </li>;
    }
};