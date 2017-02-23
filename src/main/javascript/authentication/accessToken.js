import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import { createSelector } from 'reselect';

/** Action key for recording the fact that we've just authenticated */
const AUTHENTICATED = 'AUTHENTICATED';

/** The default entry for the Access Token  */
const DEFAULT_ACCESS_TOKEN = Immutable.Map({ authenticated: false });

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
        expiry: action.payload.expiry,
        authenticated: true
    }));

/**
 * Selector to get the Access Token out of the store
 * @param {Immutable.Map} state The Redux state to get the values from
 * @return {Object} The Access Token
 */
export const selectAccessToken = (state) => state.getIn(['authentication', 'accessToken'], DEFAULT_ACCESS_TOKEN).toJS();

/**
 * Selector to determine if we are authenticated or not
 */
export const selectAuthenticated = createSelector(selectAccessToken,
    (accessToken) => accessToken.authenticated
);
