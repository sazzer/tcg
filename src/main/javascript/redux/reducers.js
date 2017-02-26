import { reducer as authentication } from '../authentication';
import { reducer as users } from '../users';

/**
 * The actual reducers to use
 */
export default {
    ...authentication,
    ...users
};
