import { createStore, applyMiddleware, compose } from 'redux';
import promiseMiddleware from 'redux-promise-middleware';
import thunk from 'redux-thunk';
import { combineReducers } from 'redux-immutable';
import Immutable from 'immutable';
import reducers from './reducers';

import initialisers from './initialise';

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
            thunk,
            promiseMiddleware()
        )
    )
);

// Dispatch all of the initialisers to the store straight away
initialisers.forEach((i) => store.dispatch(i()));

export default store;
