var gulp = require("gulp");

var jshint = require("gulp-jshint");
var concat = require("gulp-concat");
var uglify = require("gulp-uglify");
var rename = require("gulp-rename");
var cssclean = require("gulp-clean-css");
var requirejsOptimize = require("gulp-requirejs-optimize");

gulp.task("fonts",function(){
	gulp.src("./static/css/fonts/*.css").pipe(concat("fonts.css")).pipe(gulp.dest("./static/css"))
		.pipe(rename("fonts.min.css")).pipe(cssclean()).pipe(gulp.dest("./static/css"));
})


gulp.task("default",function(){
	gulp.run("fonts");
	
	gulp.watch(["./framework/plugins/*/*.js","./framework/plugins/*/*.css"],function(){
		//gulp.run("plugins")
	})
});
