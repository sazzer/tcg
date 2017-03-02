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
 * Component representing a single section of the sidebar
 */
class SidebarSection extends React.Component {
    /**
     * Render the sidebar section
     * @returns {React.Component} the sidebar section
     */
    render() {
        const { t, classes, section, openSection } = this.props;

        let nestedMenu = '';
        if (section === openSection) {
            nestedMenu = <ul className={ [classes.nestedNav, 'nav', 'nav-pills', 'flex-column'].join(' ') }>
                <li className="nav-item">
                    <NavLink to={`/encyclopaedia/${section}/human`} exact className="nav-link" activeClassName="active">Human</NavLink>
                </li>
                <li className="nav-item">
                    <NavLink to={`/encyclopaedia/${section}/dwarf`} exact className="nav-link" activeClassName="active">Dwarf</NavLink>
                </li>
                <li className="nav-item">
                    <NavLink to={`/encyclopaedia/${section}/elf`} exact className="nav-link" activeClassName="active">Elf</NavLink>
                </li>
            </ul>;
        }

        return <li className="nav-item">
            <NavLink to={`/encyclopaedia/${section}`} exact className="nav-link" activeClassName="active">{ t(`encyclopaedia.menu.${section}`) }</NavLink>
            { nestedMenu }
        </li>;
    }
}

/**
 * Component representing the sidebar for the encyclopaedia
 */
export default class Sidebar extends React.Component {
    /**
     * Actually render the sidebar
     * @returns {React.Component} the sidebar
     */
    render() {
        const { openSection } = this.props;

        return <nav className="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
            <ul className="nav nav-pills flex-column">
                <SidebarSection section="races" openSection={openSection} />
                <SidebarSection section="classes" openSection={openSection} />
                <SidebarSection section="attributes" openSection={openSection} />
                <SidebarSection section="skills" openSection={openSection} />
                <SidebarSection section="abilities" openSection={openSection} />
            </ul>
        </nav>;
    }
}
