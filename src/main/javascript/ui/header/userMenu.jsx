import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import { logout } from '../../authentication';

/**
 * React Redux function to take the Redux state and produce an object of Props that this Component needs
 * @returns {Object} The props to pass into the component
 */
function mapStateToProps() {
    return {};
}

/**
 * React Redux function to produce Props that represent Action Creators to use
 * @param {any} The Redux Dispatch mechanism
 * @returns {Object} The props to pass into the component
 */
function mapDispatchToProps(dispatch) {
    return {
        logout: () => dispatch(logout())
    };
}

/**
 * Component representing the User menu
 */
@translate()
@connect(mapStateToProps, mapDispatchToProps)
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
                <a className="dropdown-item" onClick={ this.handleClickLogout.bind(this) }>{ t(`authentication.logout`) }</a>
            </div>
        </li>;
    }

    handleClickLogout() {
        this.props.logout();
    }
};