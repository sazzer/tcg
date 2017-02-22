import 'bootstrap';
import React from 'react';
import Header from './header';
import Body from './body';

/**
 * The component that represents the main UI of the entire application
 */
function UI() {
    return <div>
        <Header />
        <Body />
    </div>;
}

export default UI;