import { reducer as authentication } from '../authentication';
import { reducer as users } from '../users';
import { reducer as rules } from '../rules';

/**
 * The actual reducers to use
 */
export default {
    ...authentication,
    ...users,
    ...rules
};
