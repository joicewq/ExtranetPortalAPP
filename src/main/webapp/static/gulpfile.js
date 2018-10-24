var gulp = require("gulp");

var jshint = require("gulp-jshint");
var concat = require("gulp-concat");
var uglify = require("gulp-uglify");
var rename = require("gulp-rename");
var cssclean = require("gulp-clean-css");
var sass = require("gulp-sass");
var sourcemaps = require('gulp-sourcemaps');
var gulpif = require("gulp-if");
var plumber = require("gulp-plumber");
var livereload = require("gulp-livereload");
var notify = require("gulp-notify");
var clean = require("gulp-clean");

let isBuild = false;

gulp.task("fonts", function() {
	gulp.src("./css/fonts/*.css")
		.pipe(concat("fonts.css"))
		.pipe(gulp.dest("./css"))
		.pipe(rename({
			suffix: ".min"
		}))
		.pipe(cssclean())
		.pipe(gulp.dest("./dist/css"));
})

gulp.task("sass", function() {
	gulp.src("./sass/*.scss")
		.pipe(gulpif(!isBuild, sourcemaps.init()))
		.pipe(sass())//编译sass
		.pipe(gulp.dest("./css"))
		.pipe(cssclean())//压缩css
		.pipe(gulpif(!isBuild, sourcemaps.write(".")))//当不是生产环境时才生成sourcemaps
		.pipe(gulp.dest("./dist/css"))
		//.pipe(livereload())
		//.pipe(notify("sass已编译"));
	//gulp.src("./sass/*.scss").pipe(sass({outputStyle: 'compressed'})).pipe(gulp.dest("./dist/css"));
})

gulp.task("css", function() {
	gulp.src("./css/*.css")
		.pipe(cssclean())
		.pipe(gulp.dest("./dist/css"));
})

gulp.task("js", function() {
	gulp.src("./js/*.js")
		//.pipe(jshint())
		.pipe(uglify())
		.pipe(gulp.dest("./dist/js"));

	gulp.src("./model/*/*.js")
		/*.pipe(jshint({
			"eqeqeq":false,
			
		}))*/
		.pipe(plumber())
		//.pipe(jshint.reporter('default'))
		.pipe(uglify())
		.pipe(gulp.dest("./dist/model"));
})

gulp.task("clean", function() {
	gulp.src("./dist/css/*.css")
		.pipe(clean());
})


gulp.task("default", ["fonts", "js", "sass", "clean"], function() {
	//livereload.listen();
	gulp.watch(["./sass/*.scss", "./sass/*/*.scss"], ["sass"],function(){
	});
});