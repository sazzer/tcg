import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { reducers as userProfileReducer } from './userProfile';

/**
 * Reducers for the Users
 */
export const reducer = {
    users: handleActions({
        ...userProfileReducer
    }, Immutable.Map())
};
