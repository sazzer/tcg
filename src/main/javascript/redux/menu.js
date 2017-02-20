import Immutable from 'immutable';
import { createAction, handleActions } from 'redux-actions';

/** Timeout for loading menu items */
const LOAD_TIMEOUT = 3000;

/**
 * Reducers for the Menu state
 */
export const reducer = handleActions({
        'LOAD_MENU_RESOLVED': (state, action) => Immutable.List(action.payload)
}, Immutable.List());

/**
 * Action Creator for loading the menu state
 */
export const loadMenu = createAction('LOAD_MENU', () => ({
        promise: new Promise((resolve) => {
            setTimeout(() => resolve(['Item 1', 'Item 2', 'Item 3']), LOAD_TIMEOUT);
        })
    })
);
