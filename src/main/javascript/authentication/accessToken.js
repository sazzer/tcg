import Immutable from 'immutable';
import { createAction } from 'redux-actions';

/** Action key for recording the fact that we've just authenticated */
const AUTHENTICATED = 'AUTHENTICATED';

/**
 * Redux Action to store the fact that we've just authenticated
 */
export const authenticated = createAction(AUTHENTICATED, (accessToken, expiry) => ({
    accessToken,
    expiry
}));


/** The actual reducers */
export const reducers = {};
reducers[AUTHENTICATED] = (state, action) =>
    state.set('accessToken', Immutable.Map({
        accessToken: action.payload.accessToken,
        expiry: action.payload.expiry
    }));
