import React from 'react';
import { translate } from 'react-i18next';
import injectSheet from 'react-jss';


/**
 * Styles to use for this component
 * @type {Object}
 */
const styles = {
    loginLink: {
        extend: 'dropdown-item',
        cursor: 'pointer'
    }
};

/**
 * Component representing a single item on the login menu
 */
@translate()
@injectSheet(styles)
export default class LoginMenuItem extends React.Component {
    /**
     * Actually render the Login menu Item
     * @returns {React.Component} the Login menu Item
     */
    render() {
        const { t, provider, classes, onClick } = this.props;

        return <a className={ classes.loginLink } onClick={this.handleClick.bind(this)}>
            { t(`authentication.${provider.provider}`) }
        </a>;
    }

    handleClick() {
        const { provider, onClick } = this.props;
        onClick(provider);
    }
};

LoginMenuItem.propTypes = {
    provider: React.PropTypes.object.isRequired,
    onClick: React.PropTypes.func.isRequired
}