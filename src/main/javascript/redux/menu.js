import Immutable from 'immutable';
import { createAction, handleActions } from 'redux-actions';

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
        promise: fetch('/health')
            .then((response) => {
                /* eslint-disable no-console */
                console.log(response);
                return [
                    'Item 1',
                    'Item 3',
                    'Item 5'
                ];
            })
    })
);
