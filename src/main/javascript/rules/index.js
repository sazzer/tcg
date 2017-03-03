import Immutable from 'immutable';
import { handleActions } from 'redux-actions';
import { initialiseRaces, reducers as racesReducer } from './races';
export { selectRaces } from './races';
import { initialiseClasses, reducers as classesReducer } from './classes';
export { selectClasses } from './classes';
import { initialiseAttributes, reducers as attributesReducer } from './attributes';
export { selectAttributes } from './attributes';
import { initialiseSkills, reducers as skillsReducer } from './skills';
export { selectSkills } from './skills';
import { initialiseAbilities, reducers as abilitiesReducer } from './abilities';
export { selectAbilities } from './abilities';

/** The overall reducer to use */
export const reducer = {
    rules: handleActions({
        ...racesReducer,
        ...classesReducer,
        ...attributesReducer,
        ...skillsReducer,
        ...abilitiesReducer
    }, Immutable.Map())
};

/**
 * Action to initialise all of the rules data
 */
export const initialise = () => ((dispatch) => {
    dispatch(initialiseRaces());
    dispatch(initialiseClasses());
    dispatch(initialiseAttributes());
    dispatch(initialiseSkills());
    dispatch(initialiseAbilities());
});
