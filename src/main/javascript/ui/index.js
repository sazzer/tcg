/*eslint-disable no-unused-vars*/
import 'bootstrap';
import React from 'react';
import Header from './header';
import Body from './body';
/*eslint-enable no-unused-vars*/

/**
 * The component that represents the main UI of the entire application
 * @return {React.Component} the React Component
 */
function UI() {
    return <div>
        <Header />
        <Body />
    </div>;
}

export default UI;
