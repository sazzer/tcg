/*eslint-disable no-unused-vars*/
import React from 'react';
import { connect } from 'react-redux';
import LoginMenu from './loginMenu';
import UserMenu from './userMenu';
import { selectAuthenticated } from '../../authentication';
/*eslint-enable no-unused-vars*/

/**
 * Component to selectively render the Login Menu or the User Menu depending on whether we are currently logged in
 * @param {boolean} loggedIn whether we are currently logged in
 * @returns {React.Component} the component to render
 */
function SelectUserMenu({ loggedIn }) {
    if (loggedIn) {
        return <UserMenu />;
    }
    return <LoginMenu/>;
}

/**
 * Mapping between the Redux State and the Props needed for this component
 * @param {Immutable.Map} state The Redux State
 * @returns {Object} The props for the component
 */
function mapStateToProps(state) {
    return {
        loggedIn: selectAuthenticated(state)
    };
}

export default connect(mapStateToProps)(SelectUserMenu);
