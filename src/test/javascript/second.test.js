import { logger } from './second';
import test from 'ava';

test('second.logger', (t) => {
    t.true('Hello, Graham' === logger('Graham'));
});
