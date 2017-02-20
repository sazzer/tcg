import React from 'react';
import { translate } from 'react-i18next';

/**
 * Component representing the header of the application
 */
@translate()
export default class Header extends React.Component {
    /**
     * Construct the header
     * @param {Object} props the props for the component
     */
    constructor(props) {
        super(props);
        this.state = {
            open: false
        };
    }

    /**
     * Toggle whether the right menu is open or not
     */
    toggleRightMenu() {
        this.setState((prevState) => ({
            open: !prevState.open
        }));
    }
    /**
     * Actually render the header bar
     * @returns {React.Component} the header bar
     */
    render() {
        const { t } = this.props;

        const rightMenuClasses = [
            "nav-right",
            "nav-menu",
            this.state.open ? "is-active" : ""
        ].join(" ");

        return <nav className="nav">
            <div className="nav-left">
                <h1 className="nav-item title">{ t('page.title') }</h1>
            </div>

            <div className="nav-center">
            </div>

            <div className="nav-toggle" onClick={ this.toggleRightMenu.bind(this) }>
                <span></span>
                <span></span>
                <span></span>
            </div>

            <div className={ rightMenuClasses }>
                <a className="nav-item">Item 1</a>
                <a className="nav-item">Item 2</a>
                <a className="nav-item">Item 3</a>
            </div>
        </nav>;
    }
};