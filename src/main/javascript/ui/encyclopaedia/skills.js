/*eslint-disable no-unused-vars*/
import React from 'react';
import Sidebar from './sidebar';
/*eslint-enable no-unused-vars*/

/**
 * Component representing the encyclopaedia for Skills
 */
export default class Skills extends React.Component {
    /**
     * Actually render the body
     * @returns {React.Component} the body
     */
    render() {
        return <div className="tcg-test-encyclopaedia-skills">
            <Sidebar openSection="skills"/>
        </div>;
    }
}
