import React from 'react';
import { translate } from 'react-i18next';

/**
 * Component representing a single item on the login menu
 */
@translate()
export default class LoginMenuItem extends React.Component {
    /**
     * Actually render the Login menu Item
     * @returns {React.Component} the Login menu Item
     */
    render() {
        const { t, provider } = this.props;

        return <a className="dropdown-item" onClick={this.handleClick.bind(this)}>
            { t(`authentication.${provider}`) }
        </a>;
    }

    handleClick() {
        const { provider } = this.props;
        alert(provider);
    }
};