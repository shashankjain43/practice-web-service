<%@ include file="/tld_includes.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var env = '${env}';
	var staticResourcePath = '${staticResourcePath}';
	
    if (typeof Snapdeal == 'undefined') {Snapdeal = {};};Snapdeal.getStaticPath = function(path) {return '${path.http}' + path;};
    
	Snapdeal.getResourcePath = function(path) {	
        var rpath = "";
        if( path == undefined || path == "" ) { path = "img/no-image.gif"; }
        
        if( env == "prod" ) {
            if(path.indexOf("imgs/a/") == 0) {
                rpath += "http://n" + (Math.abs(hash(path)) % 4 + 1) + ".sdlcdn.com/";
            } else {
                rpath += "http://i" + (Math.abs(hash(path)) % 4 + 1) + ".sdlcdn.com/";
            }	
        } else if( env == "staging" ) {
        	if(path.indexOf("imgs/a/") == 0) {
                rpath += "http://stg" + (Math.abs(hash(path)) % 4 + 1) + ".sdlcdn.com/";
            } else {
                rpath += "http" + staticResourcePath;
            }
        } else {
        	if( staticResourcePath == undefined || staticResourcePath == "" ) {
        		rpath += "${path.http}";	
        	} else {
        		rpath += "http" + staticResourcePath;
        	}
        }
        
        rpath += path;
        return rpath;
    };

   
    var hash = function(string) {
        var string = string.toString(), hash = 0, i;
        for (i = 0; i < string.length; i++) {
            hash = (((hash << 5) - hash) + string.charCodeAt(i)) & 0xFFFFFFFF;
        }

        return hash;
    }
</script>