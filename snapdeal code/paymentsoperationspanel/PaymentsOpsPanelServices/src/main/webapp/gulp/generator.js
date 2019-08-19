'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')({
    pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license', 'del']
});
var constantsJsonFilePath = 'src/app/seed-module/seed-module.url.json';
var fs = require('fs');


gulp.task('generateService', function() {
    var config = JSON.parse(fs.readFileSync(constantsJsonFilePath));
    console.log(config);
});


gulp.task('create', function () {
    var gulpUtil = require("gulp-util");
    if(gulpUtil.env.m === undefined){
        console.log('Please provide module in command "gulp create --m moduleName"');
        return;
    }
    if(gulpUtil.env.d === undefined){
        gulpUtil.env.d = '';
        //console.log('Please provide directory in command "gulp create --D directoryPath"');
        //return;
    }
    var dummySrcPath = 'src/app/dummy/';
    var module = gulpUtil.env.m;
    var destDir = 'src/app/'+gulpUtil.env.d+'/';
    var fileFilterList = [];
    var options = null;
    var fileCommandMap = {
        'm': '*.module.*',
        'c': '*.controller.*',
        's': '*.service.*',
        'u': '*.url.*',
        't': '*.tmpl.*'
    };
    if(gulpUtil.env.o !== undefined){
        options = gulpUtil.env.o.split(',');
    }
    for(var fileCommand in  fileCommandMap){
        if(gulpUtil.env.o !== undefined && options.length > 0){
            if(options.indexOf(fileCommand) != -1){
                fileFilterList.push(fileCommandMap[fileCommand]);
            }
        } else {
            fileFilterList.push(fileCommandMap[fileCommand]);
        }
    }
    var createFileFilter = $.filter(fileFilterList);
    gulp.src(dummySrcPath+'**/*')
        .pipe(createFileFilter)
        .pipe($.replace('dummy', module))
        .pipe($.replace('DUMMY', module.toUpperCase()))
        .pipe($.replace('Dummy', module.replace(/^./, module[0].toUpperCase())))
        .pipe($.rename(function (path) {
            path.basename = path.basename.replace('dummy', module);
            console.log('** File "'+destDir+module+'/'+path.basename+path.extname+'" created successfully.');
        }))
        .pipe(gulp.dest(destDir + module));
});

gulp.task('generator', ['generateService']);
