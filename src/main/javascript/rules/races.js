import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import request from '../request';

/** Redux action to initialise the list of races that we can use */
export const initialiseRaces = createAction('RACES:INITIALISE', () => ({
    promise: request('/api/races')
        .then((response) => response.body.entries)
}));

/** The reducers relevant to the Races */
export const reducers = {
    'RACES:INITIALISE_FULFILLED': (state, action) => {
        const races = {};
        action.payload.forEach((race) => {
            races[race.id] = race;
        });

        return state.set('races', Immutable.Map(races));
    }
};
