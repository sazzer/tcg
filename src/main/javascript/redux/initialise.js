import { initialise as authentication } from '../authentication';
import { loadMenu } from './menu';

/** The initialisers to support */
export default [
    loadMenu,
    authentication
];
