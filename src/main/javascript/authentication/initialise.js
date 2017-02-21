import { createAction } from 'redux-actions';
import { INITIALISE_AUTHENTICATION_PROVIDERS } from './actions';

/**
 * Redux Action to initialise the list of authenticaiton providers
 */
export const initialise = createAction(INITIALISE_AUTHENTICATION_PROVIDERS, () => ({
        promise: Promise.resolve([
            { provider: 'google' }
        ])
}));
