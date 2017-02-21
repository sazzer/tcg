/* eslint-disable */
var taskMaster = require('task-master');

module.exports = function(grunt) {
  require('time-grunt')(grunt);
  taskMaster(grunt, {
    jit: {
    }
  });
};
