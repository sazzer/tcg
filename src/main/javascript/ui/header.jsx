import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import Immutable from 'immutable';

/**
 * Component representing the header of the application
 */
@translate()
@connect(state => ({
    menu: state.get('menu', Immutable.List()).toJS(),
}))
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
        const { t, menu } = this.props;

        const rightMenuClasses = [
            "nav-right",
            "nav-menu",
            this.state.open ? "is-active" : ""
        ].join(" ");

        const menuItems = menu.map((i) =>
            <a className="nav-item">{i}</a>
        );

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
                { menuItems }
            </div>
        </nav>;
    }
};