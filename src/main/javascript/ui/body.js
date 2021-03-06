/*eslint-disable no-unused-vars*/
import React from 'react';
import injectSheet from 'react-jss';
import { Route, Switch } from 'react-router-dom';
import Encyclopaedia from './encyclopaedia';
import NotFound from './notFound';
/*eslint-enable no-unused-vars*/

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
};

@injectSheet(styles)
/**
 * Component representing the body of the application
 */
export default class Body extends React.Component {
    /**
     * Actually render the body
     * @returns {React.Component} the body
     */
    render() {
        const { classes } = this.props;

        return <div className={ classes.body }>
            <Switch>
                <Route path="/encyclopaedia" component={Encyclopaedia} />
                <Route component={NotFound} />
            </Switch>
        </div>;
    }
}
