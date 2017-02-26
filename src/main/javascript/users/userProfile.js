import Immutable from 'immutable';
import { createAction } from 'redux-actions';

/** Timeout for the promise */
const PROMISE_TIMEOUT = 3000;

/**
 * Action Creator for fetching the current user profile
 */
export const fetchUserProfile = createAction('FETCH_USER', () => ({
    promise: new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                id: '123',
                name: 'Graham',
                email: 'graham@grahamcox.co.uk'
            });
        }, PROMISE_TIMEOUT);
    })
}));

/** The reducers relevant to the Authentication Providers */
export const reducers = {
    'FETCH_USER_FULFILLED': (state, action) => state.set('currentUser',
        Immutable.fromJS({
            id: action.payload.id,
            name: action.payload.name,
            email: action.payload.email
        })
    ),
    'LOGOUT': (state) => state.delete('currentUser')
};

/**
 * Selector to get the Current User from the store
 * @param {Immutable.Map} state The Redux state to get the values from
 * @return {Object} The current user
 */
export const selectCurrentUser = (state) => state.getIn(['users', 'currentUser']);
