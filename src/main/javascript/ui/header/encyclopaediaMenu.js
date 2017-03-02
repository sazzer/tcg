/*eslint-disable no-unused-vars*/
import React from 'react';
import { translate } from 'react-i18next';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { authenticated, selectProviders } from '../../authentication';
/*eslint-enable no-unused-vars*/

@translate()
/**
 * Component representing the Encyclopaedia menu
 */
export default class EncyclopaediaMenu extends React.Component {
    /**
     * Actually render the Encyclopaedia menu
     * @returns {React.Component} the Encyclopaedia menu
     */
    render() {
        const { t } = this.props;

        return <li className="nav-item dropdown tcg-test-encyclopaediaMenu">
            <a className="nav-link dropdown-toggle" id="headerEncyclopaediaMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                { t('encyclopaedia.menu.label') }
            </a>
            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="headerEncyclopaediaMenu">
                <Link to="/encyclopaedia/races" className="dropdown-item">{ t('encyclopaedia.menu.races') }</Link>
                <Link to="/encyclopaedia/classes" className="dropdown-item">{ t('encyclopaedia.menu.classes') }</Link>
                <Link to="/encyclopaedia/attributes" className="dropdown-item">{ t('encyclopaedia.menu.attributes') }</Link>
                <Link to="/encyclopaedia/skills" className="dropdown-item">{ t('encyclopaedia.menu.skills') }</Link>
                <Link to="/encyclopaedia/abilities" className="dropdown-item">{ t('encyclopaedia.menu.abilities') }</Link>
            </div>
        </li>;
    }
}
