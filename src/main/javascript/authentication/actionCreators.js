import { createAction } from 'redux-actions';
import { AUTHENTICATE, INITIALISE_AUTHENTICATION_PROVIDERS } from './actions';

/**
 * Redux Action to initialise the list of authenticaiton providers
 */
export const initialise = createAction(INITIALISE_AUTHENTICATION_PROVIDERS, () => ({
    promise: Promise.resolve([
        { provider: 'google' }
    ])
}));

/**
 * Redux Action to store the fact that we've just authenticated
 */
export const authenticated = createAction(AUTHENTICATE, (accessToken, expiry) => ({
    accessToken,
    expiry
}));
