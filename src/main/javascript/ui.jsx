import React from 'react';
import injectSheet from 'react-jss';
import { translate } from 'react-i18next';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

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
class Page1 extends React.Component {
    render() {
        const { classes } = this.props;

        return <p className={ classes.container }>
            <span className={ classes.text }>
                <ul>
                    <li className="button is-primary"><Link to="/page2">Page 2</Link></li>
                    <li className="button"><Link to="/page3">Page 3</Link></li>
                </ul>
            </span>
        </p>;
    }
}

/**
 * Main UI for the entire application
 */
@injectSheet(styles)
@translate()
class Page2 extends React.Component {
    render() {
        const { t, classes } = this.props;

        return <p className={ classes.container }>
            <span className={ classes.text }>
                Page 2: { t('hello') }
            </span>
        </p>;
    }
}

/**
 * Main UI for the entire application
 */
@injectSheet(styles)
@translate()
class Page3 extends React.Component {
    render() {
        const { t, classes } = this.props;

        return <p className={ classes.container }>
            <span className={ classes.text }>
                Page 3: { t('hello') }
            </span>
        </p>;
    }
}

/**
 * Main UI for the entire application
 */
export default class UI extends React.Component {
    /**
     * Render the UI
     * @returns {React.Component} The React DOM to render
     */
    render() {
        return <Router basename="/game" forceRefresh={false}>
            <div>
                <Route exact path="/" component={Page1} />
                <Route exact path="/page2" component={Page2} />
                <Route exact path="/page3" component={Page3} />
            </div>
        </Router>;
    }
}