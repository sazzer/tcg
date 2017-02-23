import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import SelectUserMenu from './selectUserMenu';

/**
 * Component representing the navigation bar that is the header of the application
 */
@translate()
export default class NavigationBar extends React.Component {
    /**
     * Actually render the header bar
     * @returns {React.Component} the header bar
     */
    render() {
        const { t } = this.props;

        return <nav className="navbar navbar-toggleable-md navbar-light bg-faded fixed-top">
            <button className="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <a className="navbar-brand">{ t('page.title') }</a>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">
                </ul>
                <ul className="navbar-nav">
                    <SelectUserMenu />
                </ul>
            </div>
        </nav>;
    }
};