import React from 'react';
import injectSheet from 'react-jss';

/**
 * Styles to use for this component
 * @type {Object}
 */
const styles = {
    body: {
        position: 'absolute',
        left: 0,
        right: 0,
        bottom: 0,
        top: '3.5rem'
    }
}

/**
 * Component representing the body of the application
 */
@injectSheet(styles)
export default class Body extends React.Component {
    /**
     * Actually render the body
     * @returns {React.Component} the body
     */
    render() {
        const { classes } = this.props;

        return <div className={ classes.body }>
            Hello there
        </div>;
    }
};