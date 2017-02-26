/*eslint-disable no-unused-vars*/
import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import { logout } from '../../authentication';
import { selectCurrentUser } from '../../users';
/*eslint-enable no-unused-vars*/

/**
 * React Redux function to take the Redux state and produce an object of Props that this Component needs
 * @param {Immutable.Map} state The state to consume
 * @returns {Object} The props to pass into the component
 */
function mapStateToProps(state) {
    return {
        currentUser: selectCurrentUser(state)
    };
}

/**
 * React Redux function to produce Props that represent Action Creators to use
 * @param {function} dispatch The Redux Dispatch mechanism
 * @returns {Object} The props to pass into the component
 */
function mapDispatchToProps(dispatch) {
    return {
        logout: () => dispatch(logout())
    };
}

@translate()
@connect(mapStateToProps, mapDispatchToProps)
/**
 * Component representing the User menu
 */
export default class UserMenu extends React.Component {
    /**
     * Actually render the User menu
     * @returns {React.Component} the User menu
     */
    render() {
        const { t, currentUser } = this.props;
        let menuTitle;
        if (currentUser) {
            menuTitle = currentUser.get('name');
        } else {
            menuTitle = t('user.menu');
        }


        return <li className="nav-item dropdown tcg-test-userMenu">
            <a className="nav-link dropdown-toggle" id="headerUserMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                { menuTitle }
            </a>
            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="headerUserMenu">
                <a className="dropdown-item" onClick={ this.handleClickLogout.bind(this) }>{ t('authentication.logout') }</a>
            </div>
        </li>;
    }

    /**
     * Handler for when the Logout link is clicked
     */
    handleClickLogout() {
        this.props.logout();
    }
}
