import React from 'react';
import { translate } from 'react-i18next';

/**
 * Component representing the User menu
 */
@translate()
export default class UserMenu extends React.Component {
    /**
     * Actually render the User menu
     * @returns {React.Component} the User menu
     */
    render() {
        const { t, classes } = this.props;

        return <li className="nav-item dropdown">
            <a className="nav-link dropdown-toggle" id="headerUserMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                { t('user.menu') }
            </a>
            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="headerUserMenu">
                <a className="dropdown-item">{ t(`authentication.logout`) }</a>
            </div>
        </li>;
    }
};