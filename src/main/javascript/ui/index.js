/*eslint-disable no-unused-vars*/
import 'bootstrap';
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Header from './header';
import Body from './body';
/*eslint-enable no-unused-vars*/

/**
 * The component that represents the main UI of the entire application
 * @return {React.Component} the React Component
 */
function UI() {
    return (
    <Router basename="/game">
        <div>
            <Header />
            <Body />
        </div>
    </Router>);
}

export default UI;
