import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import request from '../request';

/**
 * Action Creator for fetching the current user profile
 */
export const fetchUserProfile = createAction('FETCH_USER', () => ({
    promise: request('/api/users/me')
        .then((response) => response.body)
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
