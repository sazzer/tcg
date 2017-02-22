import { reducer as menu } from './menu';
import { reducer as authentication } from '../authentication';

/**
 * The actual reducers to use
 */
export default {
    ...menu,
    ...authentication
};
