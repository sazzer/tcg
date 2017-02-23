import Immutable from 'immutable';
import { combineReducers } from 'redux-immutable';
import reducers from './reducers';

/**
 * Helper function to test that the Redux Reducer works as expected
 * @param {Object} input The initial shape of the Redux State
 * @param {Object} action The action to perform
 * @param {Object} expected The expected output shape of the Redux State
 * @return {Immutable.Map} The new state
 */
export function testReducer({ input = {}, action } = {}) {
    const testSubject = combineReducers(reducers);

    const output = testSubject(Immutable.fromJS(input), action);
    return output;
}
