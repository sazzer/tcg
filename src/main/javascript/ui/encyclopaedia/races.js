/*eslint-disable no-unused-vars*/
import React from 'react';
import injectSheet from 'react-jss';
import { NavLink } from 'react-router-dom';
import { translate } from 'react-i18next';
/*eslint-enable no-unused-vars*/

/**
 * Styles to use for this component
 * @type {Object}
 */
const styles = {
    nestedNav: {
        'padding-left': '2rem'
    }
};

@translate()
@injectSheet(styles)
/**
 * Component representing the encyclopaedia for Races
 */
export default class Races extends React.Component {
    /**
     * Actually render the body
     * @returns {React.Component} the body
     */
    render() {
        const { t, classes } = this.props;

        return <div className="tcg-test-encyclopaedia-races">
            <nav className="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
                <ul className="nav nav-pills flex-column">
                    <li className="nav-item">
                        <NavLink to="/encyclopaedia/races" exact className="nav-link" activeClassName="active">{ t('encyclopaedia.menu.races') }</NavLink>
                        <ul className={ [classes.nestedNav, 'nav', 'nav-pills', 'flex-column'].join(' ') }>
                            <li className="nav-item">
                                <NavLink to="/encyclopaedia/races/human" exact className="nav-link" activeClassName="active">Human</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to="/encyclopaedia/races/dwarf" exact className="nav-link" activeClassName="active">Dwarf</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink to="/encyclopaedia/races/elf" exact className="nav-link" activeClassName="active">Elf</NavLink>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/encyclopaedia/classes" exact className="nav-link" activeClassName="active">{ t('encyclopaedia.menu.classes') }</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/encyclopaedia/attributes" exact className="nav-link" activeClassName="active">{ t('encyclopaedia.menu.attributes') }</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/encyclopaedia/skills" exact className="nav-link" activeClassName="active">{ t('encyclopaedia.menu.skills') }</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/encyclopaedia/abilities" exact className="nav-link" activeClassName="active">{ t('encyclopaedia.menu.abilities') }</NavLink>
                    </li>
                </ul>
            </nav>
        </div>;
    }
}
