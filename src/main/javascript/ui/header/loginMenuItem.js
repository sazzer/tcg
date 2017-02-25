/*eslint-disable no-unused-vars*/
import React from 'react';
import { translate } from 'react-i18next';
/*eslint-enable no-unused-vars*/

@translate()
/**
 * Component representing a single item on the login menu
 */
export default class LoginMenuItem extends React.Component {
    /**
     * Actually render the Login menu Item
     * @returns {React.Component} the Login menu Item
     */
    render() {
        const { t, provider } = this.props;

        return <a className="dropdown-item tcg-test-loginMenuItem" data-provider={ provider.provider } onClick={ this.handleClick.bind(this) }>
            { t(`authentication.${provider.provider}`) }
        </a>;
    }

    /**
     * Handler for when the item is clicked, calling back to the handler we were given
     */
    handleClick() {
        const { provider, onClick } = this.props;
        onClick(provider);
    }
}

LoginMenuItem.propTypes = {
    provider: React.PropTypes.object.isRequired,
    onClick: React.PropTypes.func.isRequired
};
