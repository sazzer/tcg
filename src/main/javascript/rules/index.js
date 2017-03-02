import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { initialiseRaces, reducers as racesReducer } from './races';

/** The overall reducer to use */
export const reducer = {
    rules: handleActions({
        ...racesReducer
    }, Immutable.Map())
};

/**
 * Action to initialise all of the rules data
 */
export const initialise = () => ((dispatch) => {
    dispatch(initialiseRaces());
});
