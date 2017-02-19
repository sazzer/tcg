import React from 'react';
import injectSheet from 'react-jss';

const styles = {
    container: {
        backgroundColor: 'yellow'
    },
    text: {
        color: 'red'
    }
}

/**
 * Main UI for the entire application
 */
@injectSheet(styles)
export default class UI extends React.Component {
    /**
     * Render the UI
     * @returns {React.Component} The React DOM to render
     */
    render() {
        const { classes } = this.props;

        return <p className={classes.container}>
            <span className={classes.text}>Hello React!</span>
        </p>;
    }
}