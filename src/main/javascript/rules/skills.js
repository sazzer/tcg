import Immutable from 'immutable';
import { createAction } from 'redux-actions';
import { createSelector } from 'reselect';
import request from '../request';

/** Redux action to initialise the list of skills that we can use */
export const initialiseSkills = createAction('SKILLS:INITIALISE', () => ({
    promise: request('/api/skills')
        .then((response) => response.body.entries)
}));

/** The reducers relevant to the Skills */
export const reducers = {
    'SKILLS:INITIALISE_FULFILLED': (state, action) => {
        const skills = {};
        action.payload.forEach((skill) => {
            skills[skill.id] = skill;
        });

        return state.set('skills', Immutable.Map(skills));
    }
};

/**
 * Internal selector to extract the skills as-is from the state
 * @param {Immutable.Map} state The state to get the skills from
 * @return {Immutable.Seq} The skills, as a sequence
 */
const extractSkills = (state) => state.getIn(['rules', 'skills'], Immutable.Map()).valueSeq();

/**
 * Selector to get the skills, as a list sorted by name
 */
export const selectSkills = createSelector(extractSkills,
    (skills) => skills.sortBy((skill) => skill.name)
);
