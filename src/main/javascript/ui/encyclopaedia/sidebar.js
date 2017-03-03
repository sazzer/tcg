/*eslint-disable no-unused-vars*/
import React from 'react';
import injectSheet from 'react-jss';
import { connect } from 'react-redux';
import { NavLink } from 'react-router-dom';
import { translate } from 'react-i18next';
import { selectRaces, selectClasses, selectAttributes, selectSkills, selectAbilities } from '../../rules';
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

/**
 * React Redux function to take the Redux state and produce an object of Props that this Component needs
 * @param {Immutable.Map} state The state to consume
 * @returns {Object} The props to pass into the component
 */
function mapStateToProps(state, ownProps) {
    return {
        menuEntries: ownProps.menuSelector(state)
    };
}

@translate()
@injectSheet(styles)
@connect(mapStateToProps)
/**
 * Component representing a single section of the sidebar
 */
class SidebarSection extends React.Component {
    /**
     * Render the sidebar section
     * @returns {React.Component} the sidebar section
     */
    render() {
        const { t, classes, section, openSection, menuEntries } = this.props;

        let nestedMenu = '';
        if (section === openSection) {
            const menuItems = menuEntries.map((entry) => (
                <li className="nav-item">
                    <NavLink to={`/encyclopaedia/${section}/${entry.id}`} exact className="nav-link" activeClassName="active">{entry.name}</NavLink>
                </li>
            ));

            nestedMenu = <ul className={ [classes.nestedNav, 'nav', 'nav-pills', 'flex-column'].join(' ') }>
                { menuItems }
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
                <SidebarSection section="races" openSection={openSection} menuSelector={selectRaces} />
                <SidebarSection section="classes" openSection={openSection} menuSelector={selectClasses} />
                <SidebarSection section="attributes" openSection={openSection} menuSelector={selectAttributes} />
                <SidebarSection section="skills" openSection={openSection} menuSelector={selectSkills} />
                <SidebarSection section="abilities" openSection={openSection} menuSelector={selectAbilities} />
            </ul>
        </nav>;
    }
}
