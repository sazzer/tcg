import { createStore, applyMiddleware, compose } from 'redux';
import createLogger from 'redux-logger';
import promiseMiddleware from 'redux-simple-promise';
import { combineReducers } from 'redux-immutable';
import Immutable from 'immutable';
import reducers from './reducers';

import { loadMenu } from './menu';

/**
 * Compose Enhancers together. This added in Redux Devtools if it's available
 */
/* eslint-disable no-undef */
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
/* eslint-enable no-undef */

/**
 * The actual Redux Store to use
 */
const store = createStore(
    combineReducers(reducers), // Reducer
    Immutable.Map(), // Initial state
    composeEnhancers(
        applyMiddleware(
            createLogger({
                duration: true
            }),
            promiseMiddleware()
        )
    )
);

store.dispatch(loadMenu());

export default store;
