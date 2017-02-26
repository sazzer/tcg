import Immutable from 'immutable';
import { createAction } from 'redux-actions';

/**
 * Action Creator for fetching the current user profile
 */
export const fetchUserProfile = createAction('FETCH_USER', () => ({
    promise: Promise.resolve({
        id: '123',
        name: 'Graham',
        email: 'graham@grahamcox.co.uk'
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
    )
};
