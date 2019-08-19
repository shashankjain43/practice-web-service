/*! jQuery v1.7.1 jquery.com | jquery.org/license */
(function(a,b){function cy(a){return f.isWindow(a)?a:a.nodeType===9?a.defaultView||a.parentWindow:!1}function cv(a){if(!ck[a]){var b=c.body,d=f("<"+a+">").appendTo(b),e=d.css("display");d.remove();if(e==="none"||e===""){cl||(cl=c.createElement("iframe"),cl.frameBorder=cl.width=cl.height=0),b.appendChild(cl);if(!cm||!cl.createElement)cm=(cl.contentWindow||cl.contentDocument).document,cm.write((c.compatMode==="CSS1Compat"?"<!doctype html>":"")+"<html><body>"),cm.close();d=cm.createElement(a),cm.body.appendChild(d),e=f.css(d,"display"),b.removeChild(cl)}ck[a]=e}return ck[a]}function cu(a,b){var c={};f.each(cq.concat.apply([],cq.slice(0,b)),function(){c[this]=a});return c}function ct(){cr=b}function cs(){setTimeout(ct,0);return cr=f.now()}function cj(){try{return new a.ActiveXObject("Microsoft.XMLHTTP")}catch(b){}}function ci(){try{return new a.XMLHttpRequest}catch(b){}}function cc(a,c){a.dataFilter&&(c=a.dataFilter(c,a.dataType));var d=a.dataTypes,e={},g,h,i=d.length,j,k=d[0],l,m,n,o,p;for(g=1;g<i;g++){if(g===1)for(h in a.converters)typeof h=="string"&&(e[h.toLowerCase()]=a.converters[h]);l=k,k=d[g];if(k==="*")k=l;else if(l!=="*"&&l!==k){m=l+" "+k,n=e[m]||e["* "+k];if(!n){p=b;for(o in e){j=o.split(" ");if(j[0]===l||j[0]==="*"){p=e[j[1]+" "+k];if(p){o=e[o],o===!0?n=p:p===!0&&(n=o);break}}}}!n&&!p&&f.error("No conversion from "+m.replace(" "," to ")),n!==!0&&(c=n?n(c):p(o(c)))}}return c}function cb(a,c,d){var e=a.contents,f=a.dataTypes,g=a.responseFields,h,i,j,k;for(i in g)i in d&&(c[g[i]]=d[i]);while(f[0]==="*")f.shift(),h===b&&(h=a.mimeType||c.getResponseHeader("content-type"));if(h)for(i in e)if(e[i]&&e[i].test(h)){f.unshift(i);break}if(f[0]in d)j=f[0];else{for(i in d){if(!f[0]||a.converters[i+" "+f[0]]){j=i;break}k||(k=i)}j=j||k}if(j){j!==f[0]&&f.unshift(j);return d[j]}}function ca(a,b,c,d){if(f.isArray(b))f.each(b,function(b,e){c||bE.test(a)?d(a,e):ca(a+"["+(typeof e=="object"||f.isArray(e)?b:"")+"]",e,c,d)});else if(!c&&b!=null&&typeof b=="object")for(var e in b)ca(a+"["+e+"]",b[e],c,d);else d(a,b)}function b_(a,c){var d,e,g=f.ajaxSettings.flatOptions||{};for(d in c)c[d]!==b&&((g[d]?a:e||(e={}))[d]=c[d]);e&&f.extend(!0,a,e)}function b$(a,c,d,e,f,g){f=f||c.dataTypes[0],g=g||{},g[f]=!0;var h=a[f],i=0,j=h?h.length:0,k=a===bT,l;for(;i<j&&(k||!l);i++)l=h[i](c,d,e),typeof l=="string"&&(!k||g[l]?l=b:(c.dataTypes.unshift(l),l=b$(a,c,d,e,l,g)));(k||!l)&&!g["*"]&&(l=b$(a,c,d,e,"*",g));return l}function bZ(a){return function(b,c){typeof b!="string"&&(c=b,b="*");if(f.isFunction(c)){var d=b.toLowerCase().split(bP),e=0,g=d.length,h,i,j;for(;e<g;e++)h=d[e],j=/^\+/.test(h),j&&(h=h.substr(1)||"*"),i=a[h]=a[h]||[],i[j?"unshift":"push"](c)}}}function bC(a,b,c){var d=b==="width"?a.offsetWidth:a.offsetHeight,e=b==="width"?bx:by,g=0,h=e.length;if(d>0){if(c!=="border")for(;g<h;g++)c||(d-=parseFloat(f.css(a,"padding"+e[g]))||0),c==="margin"?d+=parseFloat(f.css(a,c+e[g]))||0:d-=parseFloat(f.css(a,"border"+e[g]+"Width"))||0;return d+"px"}d=bz(a,b,b);if(d<0||d==null)d=a.style[b]||0;d=parseFloat(d)||0;if(c)for(;g<h;g++)d+=parseFloat(f.css(a,"padding"+e[g]))||0,c!=="padding"&&(d+=parseFloat(f.css(a,"border"+e[g]+"Width"))||0),c==="margin"&&(d+=parseFloat(f.css(a,c+e[g]))||0);return d+"px"}function bp(a,b){b.src?f.ajax({url:b.src,async:!1,dataType:"script"}):f.globalEval((b.text||b.textContent||b.innerHTML||"").replace(bf,"/*$0*/")),b.parentNode&&b.parentNode.removeChild(b)}function bo(a){var b=c.createElement("div");bh.appendChild(b),b.innerHTML=a.outerHTML;return b.firstChild}function bn(a){var b=(a.nodeName||"").toLowerCase();b==="input"?bm(a):b!=="script"&&typeof a.getElementsByTagName!="undefined"&&f.grep(a.getElementsByTagName("input"),bm)}function bm(a){if(a.type==="checkbox"||a.type==="radio")a.defaultChecked=a.checked}function bl(a){return typeof a.getElementsByTagName!="undefined"?a.getElementsByTagName("*"):typeof a.querySelectorAll!="undefined"?a.querySelectorAll("*"):[]}function bk(a,b){var c;if(b.nodeType===1){b.clearAttributes&&b.clearAttributes(),b.mergeAttributes&&b.mergeAttributes(a),c=b.nodeName.toLowerCase();if(c==="object")b.outerHTML=a.outerHTML;else if(c!=="input"||a.type!=="checkbox"&&a.type!=="radio"){if(c==="option")b.selected=a.defaultSelected;else if(c==="input"||c==="textarea")b.defaultValue=a.defaultValue}else a.checked&&(b.defaultChecked=b.checked=a.checked),b.value!==a.value&&(b.value=a.value);b.removeAttribute(f.expando)}}function bj(a,b){if(b.nodeType===1&&!!f.hasData(a)){var c,d,e,g=f._data(a),h=f._data(b,g),i=g.events;if(i){delete h.handle,h.events={};for(c in i)for(d=0,e=i[c].length;d<e;d++)f.event.add(b,c+(i[c][d].namespace?".":"")+i[c][d].namespace,i[c][d],i[c][d].data)}h.data&&(h.data=f.extend({},h.data))}}function bi(a,b){return f.nodeName(a,"table")?a.getElementsByTagName("tbody")[0]||a.appendChild(a.ownerDocument.createElement("tbody")):a}function U(a){var b=V.split("|"),c=a.createDocumentFragment();if(c.createElement)while(b.length)c.createElement(b.pop());return c}function T(a,b,c){b=b||0;if(f.isFunction(b))return f.grep(a,function(a,d){var e=!!b.call(a,d,a);return e===c});if(b.nodeType)return f.grep(a,function(a,d){return a===b===c});if(typeof b=="string"){var d=f.grep(a,function(a){return a.nodeType===1});if(O.test(b))return f.filter(b,d,!c);b=f.filter(b,d)}return f.grep(a,function(a,d){return f.inArray(a,b)>=0===c})}function S(a){return!a||!a.parentNode||a.parentNode.nodeType===11}function K(){return!0}function J(){return!1}function n(a,b,c){var d=b+"defer",e=b+"queue",g=b+"mark",h=f._data(a,d);h&&(c==="queue"||!f._data(a,e))&&(c==="mark"||!f._data(a,g))&&setTimeout(function(){!f._data(a,e)&&!f._data(a,g)&&(f.removeData(a,d,!0),h.fire())},0)}function m(a){for(var b in a){if(b==="data"&&f.isEmptyObject(a[b]))continue;if(b!=="toJSON")return!1}return!0}function l(a,c,d){if(d===b&&a.nodeType===1){var e="data-"+c.replace(k,"-$1").toLowerCase();d=a.getAttribute(e);if(typeof d=="string"){try{d=d==="true"?!0:d==="false"?!1:d==="null"?null:f.isNumeric(d)?parseFloat(d):j.test(d)?f.parseJSON(d):d}catch(g){}f.data(a,c,d)}else d=b}return d}function h(a){var b=g[a]={},c,d;a=a.split(/\s+/);for(c=0,d=a.length;c<d;c++)b[a[c]]=!0;return b}var c=a.document,d=a.navigator,e=a.location,f=function(){function J(){if(!e.isReady){try{c.documentElement.doScroll("left")}catch(a){setTimeout(J,1);return}e.ready()}}var e=function(a,b){return new e.fn.init(a,b,h)},f=a.jQuery,g=a.$,h,i=/^(?:[^#<]*(<[\w\W]+>)[^>]*$|#([\w\-]*)$)/,j=/\S/,k=/^\s+/,l=/\s+$/,m=/^<(\w+)\s*\/?>(?:<\/\1>)?$/,n=/^[\],:{}\s]*$/,o=/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,p=/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,q=/(?:^|:|,)(?:\s*\[)+/g,r=/(webkit)[ \/]([\w.]+)/,s=/(opera)(?:.*version)?[ \/]([\w.]+)/,t=/(msie) ([\w.]+)/,u=/(mozilla)(?:.*? rv:([\w.]+))?/,v=/-([a-z]|[0-9])/ig,w=/^-ms-/,x=function(a,b){return(b+"").toUpperCase()},y=d.userAgent,z,A,B,C=Object.prototype.toString,D=Object.prototype.hasOwnProperty,E=Array.prototype.push,F=Array.prototype.slice,G=String.prototype.trim,H=Array.prototype.indexOf,I={};e.fn=e.prototype={constructor:e,init:function(a,d,f){var g,h,j,k;if(!a)return this;if(a.nodeType){this.context=this[0]=a,this.length=1;return this}if(a==="body"&&!d&&c.body){this.context=c,this[0]=c.body,this.selector=a,this.length=1;return this}if(typeof a=="string"){a.charAt(0)!=="<"||a.charAt(a.length-1)!==">"||a.length<3?g=i.exec(a):g=[null,a,null];if(g&&(g[1]||!d)){if(g[1]){d=d instanceof e?d[0]:d,k=d?d.ownerDocument||d:c,j=m.exec(a),j?e.isPlainObject(d)?(a=[c.createElement(j[1])],e.fn.attr.call(a,d,!0)):a=[k.createElement(j[1])]:(j=e.buildFragment([g[1]],[k]),a=(j.cacheable?e.clone(j.fragment):j.fragment).childNodes);return e.merge(this,a)}h=c.getElementById(g[2]);if(h&&h.parentNode){if(h.id!==g[2])return f.find(a);this.length=1,this[0]=h}this.context=c,this.selector=a;return this}return!d||d.jquery?(d||f).find(a):this.constructor(d).find(a)}if(e.isFunction(a))return f.ready(a);a.selector!==b&&(this.selector=a.selector,this.context=a.context);return e.makeArray(a,this)},selector:"",jquery:"1.7.1",length:0,size:function(){return this.length},toArray:function(){return F.call(this,0)},get:function(a){return a==null?this.toArray():a<0?this[this.length+a]:this[a]},pushStack:function(a,b,c){var d=this.constructor();e.isArray(a)?E.apply(d,a):e.merge(d,a),d.prevObject=this,d.context=this.context,b==="find"?d.selector=this.selector+(this.selector?" ":"")+c:b&&(d.selector=this.selector+"."+b+"("+c+")");return d},each:function(a,b){return e.each(this,a,b)},ready:function(a){e.bindReady(),A.add(a);return this},eq:function(a){a=+a;return a===-1?this.slice(a):this.slice(a,a+1)},first:function(){return this.eq(0)},last:function(){return this.eq(-1)},slice:function(){return this.pushStack(F.apply(this,arguments),"slice",F.call(arguments).join(","))},map:function(a){return this.pushStack(e.map(this,function(b,c){return a.call(b,c,b)}))},end:function(){return this.prevObject||this.constructor(null)},push:E,sort:[].sort,splice:[].splice},e.fn.init.prototype=e.fn,e.extend=e.fn.extend=function(){var a,c,d,f,g,h,i=arguments[0]||{},j=1,k=arguments.length,l=!1;typeof i=="boolean"&&(l=i,i=arguments[1]||{},j=2),typeof i!="object"&&!e.isFunction(i)&&(i={}),k===j&&(i=this,--j);for(;j<k;j++)if((a=arguments[j])!=null)for(c in a){d=i[c],f=a[c];if(i===f)continue;l&&f&&(e.isPlainObject(f)||(g=e.isArray(f)))?(g?(g=!1,h=d&&e.isArray(d)?d:[]):h=d&&e.isPlainObject(d)?d:{},i[c]=e.extend(l,h,f)):f!==b&&(i[c]=f)}return i},e.extend({noConflict:function(b){a.$===e&&(a.$=g),b&&a.jQuery===e&&(a.jQuery=f);return e},isReady:!1,readyWait:1,holdReady:function(a){a?e.readyWait++:e.ready(!0)},ready:function(a){if(a===!0&&!--e.readyWait||a!==!0&&!e.isReady){if(!c.body)return setTimeout(e.ready,1);e.isReady=!0;if(a!==!0&&--e.readyWait>0)return;A.fireWith(c,[e]),e.fn.trigger&&e(c).trigger("ready").off("ready")}},bindReady:function(){if(!A){A=e.Callbacks("once memory");if(c.readyState==="complete")return setTimeout(e.ready,1);if(c.addEventListener)c.addEventListener("DOMContentLoaded",B,!1),a.addEventListener("load",e.ready,!1);else if(c.attachEvent){c.attachEvent("onreadystatechange",B),a.attachEvent("onload",e.ready);var b=!1;try{b=a.frameElement==null}catch(d){}c.documentElement.doScroll&&b&&J()}}},isFunction:function(a){return e.type(a)==="function"},isArray:Array.isArray||function(a){return e.type(a)==="array"},isWindow:function(a){return a&&typeof a=="object"&&"setInterval"in a},isNumeric:function(a){return!isNaN(parseFloat(a))&&isFinite(a)},type:function(a){return a==null?String(a):I[C.call(a)]||"object"},isPlainObject:function(a){if(!a||e.type(a)!=="object"||a.nodeType||e.isWindow(a))return!1;try{if(a.constructor&&!D.call(a,"constructor")&&!D.call(a.constructor.prototype,"isPrototypeOf"))return!1}catch(c){return!1}var d;for(d in a);return d===b||D.call(a,d)},isEmptyObject:function(a){for(var b in a)return!1;return!0},error:function(a){throw new Error(a)},parseJSON:function(b){if(typeof b!="string"||!b)return null;b=e.trim(b);if(a.JSON&&a.JSON.parse)return a.JSON.parse(b);if(n.test(b.replace(o,"@").replace(p,"]").replace(q,"")))return(new Function("return "+b))();e.error("Invalid JSON: "+b)},parseXML:function(c){var d,f;try{a.DOMParser?(f=new DOMParser,d=f.parseFromString(c,"text/xml")):(d=new ActiveXObject("Microsoft.XMLDOM"),d.async="false",d.loadXML(c))}catch(g){d=b}(!d||!d.documentElement||d.getElementsByTagName("parsererror").length)&&e.error("Invalid XML: "+c);return d},noop:function(){},globalEval:function(b){b&&j.test(b)&&(a.execScript||function(b){a.eval.call(a,b)})(b)},camelCase:function(a){return a.replace(w,"ms-").replace(v,x)},nodeName:function(a,b){return a.nodeName&&a.nodeName.toUpperCase()===b.toUpperCase()},each:function(a,c,d){var f,g=0,h=a.length,i=h===b||e.isFunction(a);if(d){if(i){for(f in a)if(c.apply(a[f],d)===!1)break}else for(;g<h;)if(c.apply(a[g++],d)===!1)break}else if(i){for(f in a)if(c.call(a[f],f,a[f])===!1)break}else for(;g<h;)if(c.call(a[g],g,a[g++])===!1)break;return a},trim:G?function(a){return a==null?"":G.call(a)}:function(a){return a==null?"":(a+"").replace(k,"").replace(l,"")},makeArray:function(a,b){var c=b||[];if(a!=null){var d=e.type(a);a.length==null||d==="string"||d==="function"||d==="regexp"||e.isWindow(a)?E.call(c,a):e.merge(c,a)}return c},inArray:function(a,b,c){var d;if(b){if(H)return H.call(b,a,c);d=b.length,c=c?c<0?Math.max(0,d+c):c:0;for(;c<d;c++)if(c in b&&b[c]===a)return c}return-1},merge:function(a,c){var d=a.length,e=0;if(typeof c.length=="number")for(var f=c.length;e<f;e++)a[d++]=c[e];else while(c[e]!==b)a[d++]=c[e++];a.length=d;return a},grep:function(a,b,c){var d=[],e;c=!!c;for(var f=0,g=a.length;f<g;f++)e=!!b(a[f],f),c!==e&&d.push(a[f]);return d},map:function(a,c,d){var f,g,h=[],i=0,j=a.length,k=a instanceof e||j!==b&&typeof j=="number"&&(j>0&&a[0]&&a[j-1]||j===0||e.isArray(a));if(k)for(;i<j;i++)f=c(a[i],i,d),f!=null&&(h[h.length]=f);else for(g in a)f=c(a[g],g,d),f!=null&&(h[h.length]=f);return h.concat.apply([],h)},guid:1,proxy:function(a,c){if(typeof c=="string"){var d=a[c];c=a,a=d}if(!e.isFunction(a))return b;var f=F.call(arguments,2),g=function(){return a.apply(c,f.concat(F.call(arguments)))};g.guid=a.guid=a.guid||g.guid||e.guid++;return g},access:function(a,c,d,f,g,h){var i=a.length;if(typeof c=="object"){for(var j in c)e.access(a,j,c[j],f,g,d);return a}if(d!==b){f=!h&&f&&e.isFunction(d);for(var k=0;k<i;k++)g(a[k],c,f?d.call(a[k],k,g(a[k],c)):d,h);return a}return i?g(a[0],c):b},now:function(){return(new Date).getTime()},uaMatch:function(a){a=a.toLowerCase();var b=r.exec(a)||s.exec(a)||t.exec(a)||a.indexOf("compatible")<0&&u.exec(a)||[];return{browser:b[1]||"",version:b[2]||"0"}},sub:function(){function a(b,c){return new a.fn.init(b,c)}e.extend(!0,a,this),a.superclass=this,a.fn=a.prototype=this(),a.fn.constructor=a,a.sub=this.sub,a.fn.init=function(d,f){f&&f instanceof e&&!(f instanceof a)&&(f=a(f));return e.fn.init.call(this,d,f,b)},a.fn.init.prototype=a.fn;var b=a(c);return a},browser:{}}),e.each("Boolean Number String Function Array Date RegExp Object".split(" "),function(a,b){I["[object "+b+"]"]=b.toLowerCase()}),z=e.uaMatch(y),z.browser&&(e.browser[z.browser]=!0,e.browser.version=z.version),e.browser.webkit&&(e.browser.safari=!0),j.test(" ")&&(k=/^[\s\xA0]+/,l=/[\s\xA0]+$/),h=e(c),c.addEventListener?B=function(){c.removeEventListener("DOMContentLoaded",B,!1),e.ready()}:c.attachEvent&&(B=function(){c.readyState==="complete"&&(c.detachEvent("onreadystatechange",B),e.ready())});return e}(),g={};f.Callbacks=function(a){a=a?g[a]||h(a):{};var c=[],d=[],e,i,j,k,l,m=function(b){var d,e,g,h,i;for(d=0,e=b.length;d<e;d++)g=b[d],h=f.type(g),h==="array"?m(g):h==="function"&&(!a.unique||!o.has(g))&&c.push(g)},n=function(b,f){f=f||[],e=!a.memory||[b,f],i=!0,l=j||0,j=0,k=c.length;for(;c&&l<k;l++)if(c[l].apply(b,f)===!1&&a.stopOnFalse){e=!0;break}i=!1,c&&(a.once?e===!0?o.disable():c=[]:d&&d.length&&(e=d.shift(),o.fireWith(e[0],e[1])))},o={add:function(){if(c){var a=c.length;m(arguments),i?k=c.length:e&&e!==!0&&(j=a,n(e[0],e[1]))}return this},remove:function(){if(c){var b=arguments,d=0,e=b.length;for(;d<e;d++)for(var f=0;f<c.length;f++)if(b[d]===c[f]){i&&f<=k&&(k--,f<=l&&l--),c.splice(f--,1);if(a.unique)break}}return this},has:function(a){if(c){var b=0,d=c.length;for(;b<d;b++)if(a===c[b])return!0}return!1},empty:function(){c=[];return this},disable:function(){c=d=e=b;return this},disabled:function(){return!c},lock:function(){d=b,(!e||e===!0)&&o.disable();return this},locked:function(){return!d},fireWith:function(b,c){d&&(i?a.once||d.push([b,c]):(!a.once||!e)&&n(b,c));return this},fire:function(){o.fireWith(this,arguments);return this},fired:function(){return!!e}};return o};var i=[].slice;f.extend({Deferred:function(a){var b=f.Callbacks("once memory"),c=f.Callbacks("once memory"),d=f.Callbacks("memory"),e="pending",g={resolve:b,reject:c,notify:d},h={done:b.add,fail:c.add,progress:d.add,state:function(){return e},isResolved:b.fired,isRejected:c.fired,then:function(a,b,c){i.done(a).fail(b).progress(c);return this},always:function(){i.done.apply(i,arguments).fail.apply(i,arguments);return this},pipe:function(a,b,c){return f.Deferred(function(d){f.each({done:[a,"resolve"],fail:[b,"reject"],progress:[c,"notify"]},function(a,b){var c=b[0],e=b[1],g;f.isFunction(c)?i[a](function(){g=c.apply(this,arguments),g&&f.isFunction(g.promise)?g.promise().then(d.resolve,d.reject,d.notify):d[e+"With"](this===i?d:this,[g])}):i[a](d[e])})}).promise()},promise:function(a){if(a==null)a=h;else for(var b in h)a[b]=h[b];return a}},i=h.promise({}),j;for(j in g)i[j]=g[j].fire,i[j+"With"]=g[j].fireWith;i.done(function(){e="resolved"},c.disable,d.lock).fail(function(){e="rejected"},b.disable,d.lock),a&&a.call(i,i);return i},when:function(a){function m(a){return function(b){e[a]=arguments.length>1?i.call(arguments,0):b,j.notifyWith(k,e)}}function l(a){return function(c){b[a]=arguments.length>1?i.call(arguments,0):c,--g||j.resolveWith(j,b)}}var b=i.call(arguments,0),c=0,d=b.length,e=Array(d),g=d,h=d,j=d<=1&&a&&f.isFunction(a.promise)?a:f.Deferred(),k=j.promise();if(d>1){for(;c<d;c++)b[c]&&b[c].promise&&f.isFunction(b[c].promise)?b[c].promise().then(l(c),j.reject,m(c)):--g;g||j.resolveWith(j,b)}else j!==a&&j.resolveWith(j,d?[a]:[]);return k}}),f.support=function(){var b,d,e,g,h,i,j,k,l,m,n,o,p,q=c.createElement("div"),r=c.documentElement;q.setAttribute("className","t"),q.innerHTML="   <link/><table></table><a href='/a' style='top:1px;float:left;opacity:.55;'>a</a><input type='checkbox'/>",d=q.getElementsByTagName("*"),e=q.getElementsByTagName("a")[0];if(!d||!d.length||!e)return{};g=c.createElement("select"),h=g.appendChild(c.createElement("option")),i=q.getElementsByTagName("input")[0],b={leadingWhitespace:q.firstChild.nodeType===3,tbody:!q.getElementsByTagName("tbody").length,htmlSerialize:!!q.getElementsByTagName("link").length,style:/top/.test(e.getAttribute("style")),hrefNormalized:e.getAttribute("href")==="/a",opacity:/^0.55/.test(e.style.opacity),cssFloat:!!e.style.cssFloat,checkOn:i.value==="on",optSelected:h.selected,getSetAttribute:q.className!=="t",enctype:!!c.createElement("form").enctype,html5Clone:c.createElement("nav").cloneNode(!0).outerHTML!=="<:nav></:nav>",submitBubbles:!0,changeBubbles:!0,focusinBubbles:!1,deleteExpando:!0,noCloneEvent:!0,inlineBlockNeedsLayout:!1,shrinkWrapBlocks:!1,reliableMarginRight:!0},i.checked=!0,b.noCloneChecked=i.cloneNode(!0).checked,g.disabled=!0,b.optDisabled=!h.disabled;try{delete q.test}catch(s){b.deleteExpando=!1}!q.addEventListener&&q.attachEvent&&q.fireEvent&&(q.attachEvent("onclick",function(){b.noCloneEvent=!1}),q.cloneNode(!0).fireEvent("onclick")),i=c.createElement("input"),i.value="t",i.setAttribute("type","radio"),b.radioValue=i.value==="t",i.setAttribute("checked","checked"),q.appendChild(i),k=c.createDocumentFragment(),k.appendChild(q.lastChild),b.checkClone=k.cloneNode(!0).cloneNode(!0).lastChild.checked,b.appendChecked=i.checked,k.removeChild(i),k.appendChild(q),q.innerHTML="",a.getComputedStyle&&(j=c.createElement("div"),j.style.width="0",j.style.marginRight="0",q.style.width="2px",q.appendChild(j),b.reliableMarginRight=(parseInt((a.getComputedStyle(j,null)||{marginRight:0}).marginRight,10)||0)===0);if(q.attachEvent)for(o in{submit:1,change:1,focusin:1})n="on"+o,p=n in q,p||(q.setAttribute(n,"return;"),p=typeof q[n]=="function"),b[o+"Bubbles"]=p;k.removeChild(q),k=g=h=j=q=i=null,f(function(){var a,d,e,g,h,i,j,k,m,n,o,r=c.getElementsByTagName("body")[0];!r||(j=1,k="position:absolute;top:0;left:0;width:1px;height:1px;margin:0;",m="visibility:hidden;border:0;",n="style='"+k+"border:5px solid #000;padding:0;'",o="<div "+n+"><div></div></div>"+"<table "+n+" cellpadding='0' cellspacing='0'>"+"<tr><td></td></tr></table>",a=c.createElement("div"),a.style.cssText=m+"width:0;height:0;position:static;top:0;margin-top:"+j+"px",r.insertBefore(a,r.firstChild),q=c.createElement("div"),a.appendChild(q),q.innerHTML="<table><tr><td style='padding:0;border:0;display:none'></td><td>t</td></tr></table>",l=q.getElementsByTagName("td"),p=l[0].offsetHeight===0,l[0].style.display="",l[1].style.display="none",b.reliableHiddenOffsets=p&&l[0].offsetHeight===0,q.innerHTML="",q.style.width=q.style.paddingLeft="1px",f.boxModel=b.boxModel=q.offsetWidth===2,typeof q.style.zoom!="undefined"&&(q.style.display="inline",q.style.zoom=1,b.inlineBlockNeedsLayout=q.offsetWidth===2,q.style.display="",q.innerHTML="<div style='width:4px;'></div>",b.shrinkWrapBlocks=q.offsetWidth!==2),q.style.cssText=k+m,q.innerHTML=o,d=q.firstChild,e=d.firstChild,h=d.nextSibling.firstChild.firstChild,i={doesNotAddBorder:e.offsetTop!==5,doesAddBorderForTableAndCells:h.offsetTop===5},e.style.position="fixed",e.style.top="20px",i.fixedPosition=e.offsetTop===20||e.offsetTop===15,e.style.position=e.style.top="",d.style.overflow="hidden",d.style.position="relative",i.subtractsBorderForOverflowNotVisible=e.offsetTop===-5,i.doesNotIncludeMarginInBodyOffset=r.offsetTop!==j,r.removeChild(a),q=a=null,f.extend(b,i))});return b}();var j=/^(?:\{.*\}|\[.*\])$/,k=/([A-Z])/g;f.extend({cache:{},uuid:0,expando:"jQuery"+(f.fn.jquery+Math.random()).replace(/\D/g,""),noData:{embed:!0,object:"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000",applet:!0},hasData:function(a){a=a.nodeType?f.cache[a[f.expando]]:a[f.expando];return!!a&&!m(a)},data:function(a,c,d,e){if(!!f.acceptData(a)){var g,h,i,j=f.expando,k=typeof c=="string",l=a.nodeType,m=l?f.cache:a,n=l?a[j]:a[j]&&j,o=c==="events";if((!n||!m[n]||!o&&!e&&!m[n].data)&&k&&d===b)return;n||(l?a[j]=n=++f.uuid:n=j),m[n]||(m[n]={},l||(m[n].toJSON=f.noop));if(typeof c=="object"||typeof c=="function")e?m[n]=f.extend(m[n],c):m[n].data=f.extend(m[n].data,c);g=h=m[n],e||(h.data||(h.data={}),h=h.data),d!==b&&(h[f.camelCase(c)]=d);if(o&&!h[c])return g.events;k?(i=h[c],i==null&&(i=h[f.camelCase(c)])):i=h;return i}},removeData:function(a,b,c){if(!!f.acceptData(a)){var d,e,g,h=f.expando,i=a.nodeType,j=i?f.cache:a,k=i?a[h]:h;if(!j[k])return;if(b){d=c?j[k]:j[k].data;if(d){f.isArray(b)||(b in d?b=[b]:(b=f.camelCase(b),b in d?b=[b]:b=b.split(" ")));for(e=0,g=b.length;e<g;e++)delete d[b[e]];if(!(c?m:f.isEmptyObject)(d))return}}if(!c){delete j[k].data;if(!m(j[k]))return}f.support.deleteExpando||!j.setInterval?delete j[k]:j[k]=null,i&&(f.support.deleteExpando?delete a[h]:a.removeAttribute?a.removeAttribute(h):a[h]=null)}},_data:function(a,b,c){return f.data(a,b,c,!0)},acceptData:function(a){if(a.nodeName){var b=f.noData[a.nodeName.toLowerCase()];if(b)return b!==!0&&a.getAttribute("classid")===b}return!0}}),f.fn.extend({data:function(a,c){var d,e,g,h=null;if(typeof a=="undefined"){if(this.length){h=f.data(this[0]);if(this[0].nodeType===1&&!f._data(this[0],"parsedAttrs")){e=this[0].attributes;for(var i=0,j=e.length;i<j;i++)g=e[i].name,g.indexOf("data-")===0&&(g=f.camelCase(g.substring(5)),l(this[0],g,h[g]));f._data(this[0],"parsedAttrs",!0)}}return h}if(typeof a=="object")return this.each(function(){f.data(this,a)});d=a.split("."),d[1]=d[1]?"."+d[1]:"";if(c===b){h=this.triggerHandler("getData"+d[1]+"!",[d[0]]),h===b&&this.length&&(h=f.data(this[0],a),h=l(this[0],a,h));return h===b&&d[1]?this.data(d[0]):h}return this.each(function(){var b=f(this),e=[d[0],c];b.triggerHandler("setData"+d[1]+"!",e),f.data(this,a,c),b.triggerHandler("changeData"+d[1]+"!",e)})},removeData:function(a){return this.each(function(){f.removeData(this,a)})}}),f.extend({_mark:function(a,b){a&&(b=(b||"fx")+"mark",f._data(a,b,(f._data(a,b)||0)+1))},_unmark:function(a,b,c){a!==!0&&(c=b,b=a,a=!1);if(b){c=c||"fx";var d=c+"mark",e=a?0:(f._data(b,d)||1)-1;e?f._data(b,d,e):(f.removeData(b,d,!0),n(b,c,"mark"))}},queue:function(a,b,c){var d;if(a){b=(b||"fx")+"queue",d=f._data(a,b),c&&(!d||f.isArray(c)?d=f._data(a,b,f.makeArray(c)):d.push(c));return d||[]}},dequeue:function(a,b){b=b||"fx";var c=f.queue(a,b),d=c.shift(),e={};d==="inprogress"&&(d=c.shift()),d&&(b==="fx"&&c.unshift("inprogress"),f._data(a,b+".run",e),d.call(a,function(){f.dequeue(a,b)},e)),c.length||(f.removeData(a,b+"queue "+b+".run",!0),n(a,b,"queue"))}}),f.fn.extend({queue:function(a,c){typeof a!="string"&&(c=a,a="fx");if(c===b)return f.queue(this[0],a);return this.each(function(){var b=f.queue(this,a,c);a==="fx"&&b[0]!=="inprogress"&&f.dequeue(this,a)})},dequeue:function(a){return this.each(function(){f.dequeue(this,a)})},delay:function(a,b){a=f.fx?f.fx.speeds[a]||a:a,b=b||"fx";return this.queue(b,function(b,c){var d=setTimeout(b,a);c.stop=function(){clearTimeout(d)}})},clearQueue:function(a){return this.queue(a||"fx",[])},promise:function(a,c){function m(){--h||d.resolveWith(e,[e])}typeof a!="string"&&(c=a,a=b),a=a||"fx";var d=f.Deferred(),e=this,g=e.length,h=1,i=a+"defer",j=a+"queue",k=a+"mark",l;while(g--)if(l=f.data(e[g],i,b,!0)||(f.data(e[g],j,b,!0)||f.data(e[g],k,b,!0))&&f.data(e[g],i,f.Callbacks("once memory"),!0))h++,l.add(m);m();return d.promise()}});var o=/[\n\t\r]/g,p=/\s+/,q=/\r/g,r=/^(?:button|input)$/i,s=/^(?:button|input|object|select|textarea)$/i,t=/^a(?:rea)?$/i,u=/^(?:autofocus|autoplay|async|checked|controls|defer|disabled|hidden|loop|multiple|open|readonly|required|scoped|selected)$/i,v=f.support.getSetAttribute,w,x,y;f.fn.extend({attr:function(a,b){return f.access(this,a,b,!0,f.attr)},removeAttr:function(a){return this.each(function(){f.removeAttr(this,a)})},prop:function(a,b){return f.access(this,a,b,!0,f.prop)},removeProp:function(a){a=f.propFix[a]||a;return this.each(function(){try{this[a]=b,delete this[a]}catch(c){}})},addClass:function(a){var b,c,d,e,g,h,i;if(f.isFunction(a))return this.each(function(b){f(this).addClass(a.call(this,b,this.className))});if(a&&typeof a=="string"){b=a.split(p);for(c=0,d=this.length;c<d;c++){e=this[c];if(e.nodeType===1)if(!e.className&&b.length===1)e.className=a;else{g=" "+e.className+" ";for(h=0,i=b.length;h<i;h++)~g.indexOf(" "+b[h]+" ")||(g+=b[h]+" ");e.className=f.trim(g)}}}return this},removeClass:function(a){var c,d,e,g,h,i,j;if(f.isFunction(a))return this.each(function(b){f(this).removeClass(a.call(this,b,this.className))});if(a&&typeof a=="string"||a===b){c=(a||"").split(p);for(d=0,e=this.length;d<e;d++){g=this[d];if(g.nodeType===1&&g.className)if(a){h=(" "+g.className+" ").replace(o," ");for(i=0,j=c.length;i<j;i++)h=h.replace(" "+c[i]+" "," ");g.className=f.trim(h)}else g.className=""}}return this},toggleClass:function(a,b){var c=typeof a,d=typeof b=="boolean";if(f.isFunction(a))return this.each(function(c){f(this).toggleClass(a.call(this,c,this.className,b),b)});return this.each(function(){if(c==="string"){var e,g=0,h=f(this),i=b,j=a.split(p);while(e=j[g++])i=d?i:!h.hasClass(e),h[i?"addClass":"removeClass"](e)}else if(c==="undefined"||c==="boolean")this.className&&f._data(this,"__className__",this.className),this.className=this.className||a===!1?"":f._data(this,"__className__")||""})},hasClass:function(a){var b=" "+a+" ",c=0,d=this.length;for(;c<d;c++)if(this[c].nodeType===1&&(" "+this[c].className+" ").replace(o," ").indexOf(b)>-1)return!0;return!1},val:function(a){var c,d,e,g=this[0];{if(!!arguments.length){e=f.isFunction(a);return this.each(function(d){var g=f(this),h;if(this.nodeType===1){e?h=a.call(this,d,g.val()):h=a,h==null?h="":typeof h=="number"?h+="":f.isArray(h)&&(h=f.map(h,function(a){return a==null?"":a+""})),c=f.valHooks[this.nodeName.toLowerCase()]||f.valHooks[this.type];if(!c||!("set"in c)||c.set(this,h,"value")===b)this.value=h}})}if(g){c=f.valHooks[g.nodeName.toLowerCase()]||f.valHooks[g.type];if(c&&"get"in c&&(d=c.get(g,"value"))!==b)return d;d=g.value;return typeof d=="string"?d.replace(q,""):d==null?"":d}}}}),f.extend({valHooks:{option:{get:function(a){var b=a.attributes.value;return!b||b.specified?a.value:a.text}},select:{get:function(a){var b,c,d,e,g=a.selectedIndex,h=[],i=a.options,j=a.type==="select-one";if(g<0)return null;c=j?g:0,d=j?g+1:i.length;for(;c<d;c++){e=i[c];if(e.selected&&(f.support.optDisabled?!e.disabled:e.getAttribute("disabled")===null)&&(!e.parentNode.disabled||!f.nodeName(e.parentNode,"optgroup"))){b=f(e).val();if(j)return b;h.push(b)}}if(j&&!h.length&&i.length)return f(i[g]).val();return h},set:function(a,b){var c=f.makeArray(b);f(a).find("option").each(function(){this.selected=f.inArray(f(this).val(),c)>=0}),c.length||(a.selectedIndex=-1);return c}}},attrFn:{val:!0,css:!0,html:!0,text:!0,data:!0,width:!0,height:!0,offset:!0},attr:function(a,c,d,e){var g,h,i,j=a.nodeType;if(!!a&&j!==3&&j!==8&&j!==2){if(e&&c in f.attrFn)return f(a)[c](d);if(typeof a.getAttribute=="undefined")return f.prop(a,c,d);i=j!==1||!f.isXMLDoc(a),i&&(c=c.toLowerCase(),h=f.attrHooks[c]||(u.test(c)?x:w));if(d!==b){if(d===null){f.removeAttr(a,c);return}if(h&&"set"in h&&i&&(g=h.set(a,d,c))!==b)return g;a.setAttribute(c,""+d);return d}if(h&&"get"in h&&i&&(g=h.get(a,c))!==null)return g;g=a.getAttribute(c);return g===null?b:g}},removeAttr:function(a,b){var c,d,e,g,h=0;if(b&&a.nodeType===1){d=b.toLowerCase().split(p),g=d.length;for(;h<g;h++)e=d[h],e&&(c=f.propFix[e]||e,f.attr(a,e,""),a.removeAttribute(v?e:c),u.test(e)&&c in a&&(a[c]=!1))}},attrHooks:{type:{set:function(a,b){if(r.test(a.nodeName)&&a.parentNode)f.error("type property can't be changed");else if(!f.support.radioValue&&b==="radio"&&f.nodeName(a,"input")){var c=a.value;a.setAttribute("type",b),c&&(a.value=c);return b}}},value:{get:function(a,b){if(w&&f.nodeName(a,"button"))return w.get(a,b);return b in a?a.value:null},set:function(a,b,c){if(w&&f.nodeName(a,"button"))return w.set(a,b,c);a.value=b}}},propFix:{tabindex:"tabIndex",readonly:"readOnly","for":"htmlFor","class":"className",maxlength:"maxLength",cellspacing:"cellSpacing",cellpadding:"cellPadding",rowspan:"rowSpan",colspan:"colSpan",usemap:"useMap",frameborder:"frameBorder",contenteditable:"contentEditable"},prop:function(a,c,d){var e,g,h,i=a.nodeType;if(!!a&&i!==3&&i!==8&&i!==2){h=i!==1||!f.isXMLDoc(a),h&&(c=f.propFix[c]||c,g=f.propHooks[c]);return d!==b?g&&"set"in g&&(e=g.set(a,d,c))!==b?e:a[c]=d:g&&"get"in g&&(e=g.get(a,c))!==null?e:a[c]}},propHooks:{tabIndex:{get:function(a){var c=a.getAttributeNode("tabindex");return c&&c.specified?parseInt(c.value,10):s.test(a.nodeName)||t.test(a.nodeName)&&a.href?0:b}}}}),f.attrHooks.tabindex=f.propHooks.tabIndex,x={get:function(a,c){var d,e=f.prop(a,c);return e===!0||typeof e!="boolean"&&(d=a.getAttributeNode(c))&&d.nodeValue!==!1?c.toLowerCase():b},set:function(a,b,c){var d;b===!1?f.removeAttr(a,c):(d=f.propFix[c]||c,d in a&&(a[d]=!0),a.setAttribute(c,c.toLowerCase()));return c}},v||(y={name:!0,id:!0},w=f.valHooks.button={get:function(a,c){var d;d=a.getAttributeNode(c);return d&&(y[c]?d.nodeValue!=="":d.specified)?d.nodeValue:b},set:function(a,b,d){var e=a.getAttributeNode(d);e||(e=c.createAttribute(d),a.setAttributeNode(e));return e.nodeValue=b+""}},f.attrHooks.tabindex.set=w.set,f.each(["width","height"],function(a,b){f.attrHooks[b]=f.extend(f.attrHooks[b],{set:function(a,c){if(c===""){a.setAttribute(b,"auto");return c}}})}),f.attrHooks.contenteditable={get:w.get,set:function(a,b,c){b===""&&(b="false"),w.set(a,b,c)}}),f.support.hrefNormalized||f.each(["href","src","width","height"],function(a,c){f.attrHooks[c]=f.extend(f.attrHooks[c],{get:function(a){var d=a.getAttribute(c,2);return d===null?b:d}})}),f.support.style||(f.attrHooks.style={get:function(a){return a.style.cssText.toLowerCase()||b},set:function(a,b){return a.style.cssText=""+b}}),f.support.optSelected||(f.propHooks.selected=f.extend(f.propHooks.selected,{get:function(a){var b=a.parentNode;b&&(b.selectedIndex,b.parentNode&&b.parentNode.selectedIndex);return null}})),f.support.enctype||(f.propFix.enctype="encoding"),f.support.checkOn||f.each(["radio","checkbox"],function(){f.valHooks[this]={get:function(a){return a.getAttribute("value")===null?"on":a.value}}}),f.each(["radio","checkbox"],function(){f.valHooks[this]=f.extend(f.valHooks[this],{set:function(a,b){if(f.isArray(b))return a.checked=f.inArray(f(a).val(),b)>=0}})});var z=/^(?:textarea|input|select)$/i,A=/^([^\.]*)?(?:\.(.+))?$/,B=/\bhover(\.\S+)?\b/,C=/^key/,D=/^(?:mouse|contextmenu)|click/,E=/^(?:focusinfocus|focusoutblur)$/,F=/^(\w*)(?:#([\w\-]+))?(?:\.([\w\-]+))?$/,G=function(a){var b=F.exec(a);b&&(b[1]=(b[1]||"").toLowerCase(),b[3]=b[3]&&new RegExp("(?:^|\\s)"+b[3]+"(?:\\s|$)"));return b},H=function(a,b){var c=a.attributes||{};return(!b[1]||a.nodeName.toLowerCase()===b[1])&&(!b[2]||(c.id||{}).value===b[2])&&(!b[3]||b[3].test((c["class"]||{}).value))},I=function(a){return f.event.special.hover?a:a.replace(B,"mouseenter$1 mouseleave$1")};
f.event={add:function(a,c,d,e,g){var h,i,j,k,l,m,n,o,p,q,r,s;if(!(a.nodeType===3||a.nodeType===8||!c||!d||!(h=f._data(a)))){d.handler&&(p=d,d=p.handler),d.guid||(d.guid=f.guid++),j=h.events,j||(h.events=j={}),i=h.handle,i||(h.handle=i=function(a){return typeof f!="undefined"&&(!a||f.event.triggered!==a.type)?f.event.dispatch.apply(i.elem,arguments):b},i.elem=a),c=f.trim(I(c)).split(" ");for(k=0;k<c.length;k++){l=A.exec(c[k])||[],m=l[1],n=(l[2]||"").split(".").sort(),s=f.event.special[m]||{},m=(g?s.delegateType:s.bindType)||m,s=f.event.special[m]||{},o=f.extend({type:m,origType:l[1],data:e,handler:d,guid:d.guid,selector:g,quick:G(g),namespace:n.join(".")},p),r=j[m];if(!r){r=j[m]=[],r.delegateCount=0;if(!s.setup||s.setup.call(a,e,n,i)===!1)a.addEventListener?a.addEventListener(m,i,!1):a.attachEvent&&a.attachEvent("on"+m,i)}s.add&&(s.add.call(a,o),o.handler.guid||(o.handler.guid=d.guid)),g?r.splice(r.delegateCount++,0,o):r.push(o),f.event.global[m]=!0}a=null}},global:{},remove:function(a,b,c,d,e){var g=f.hasData(a)&&f._data(a),h,i,j,k,l,m,n,o,p,q,r,s;if(!!g&&!!(o=g.events)){b=f.trim(I(b||"")).split(" ");for(h=0;h<b.length;h++){i=A.exec(b[h])||[],j=k=i[1],l=i[2];if(!j){for(j in o)f.event.remove(a,j+b[h],c,d,!0);continue}p=f.event.special[j]||{},j=(d?p.delegateType:p.bindType)||j,r=o[j]||[],m=r.length,l=l?new RegExp("(^|\\.)"+l.split(".").sort().join("\\.(?:.*\\.)?")+"(\\.|$)"):null;for(n=0;n<r.length;n++)s=r[n],(e||k===s.origType)&&(!c||c.guid===s.guid)&&(!l||l.test(s.namespace))&&(!d||d===s.selector||d==="**"&&s.selector)&&(r.splice(n--,1),s.selector&&r.delegateCount--,p.remove&&p.remove.call(a,s));r.length===0&&m!==r.length&&((!p.teardown||p.teardown.call(a,l)===!1)&&f.removeEvent(a,j,g.handle),delete o[j])}f.isEmptyObject(o)&&(q=g.handle,q&&(q.elem=null),f.removeData(a,["events","handle"],!0))}},customEvent:{getData:!0,setData:!0,changeData:!0},trigger:function(c,d,e,g){if(!e||e.nodeType!==3&&e.nodeType!==8){var h=c.type||c,i=[],j,k,l,m,n,o,p,q,r,s;if(E.test(h+f.event.triggered))return;h.indexOf("!")>=0&&(h=h.slice(0,-1),k=!0),h.indexOf(".")>=0&&(i=h.split("."),h=i.shift(),i.sort());if((!e||f.event.customEvent[h])&&!f.event.global[h])return;c=typeof c=="object"?c[f.expando]?c:new f.Event(h,c):new f.Event(h),c.type=h,c.isTrigger=!0,c.exclusive=k,c.namespace=i.join("."),c.namespace_re=c.namespace?new RegExp("(^|\\.)"+i.join("\\.(?:.*\\.)?")+"(\\.|$)"):null,o=h.indexOf(":")<0?"on"+h:"";if(!e){j=f.cache;for(l in j)j[l].events&&j[l].events[h]&&f.event.trigger(c,d,j[l].handle.elem,!0);return}c.result=b,c.target||(c.target=e),d=d!=null?f.makeArray(d):[],d.unshift(c),p=f.event.special[h]||{};if(p.trigger&&p.trigger.apply(e,d)===!1)return;r=[[e,p.bindType||h]];if(!g&&!p.noBubble&&!f.isWindow(e)){s=p.delegateType||h,m=E.test(s+h)?e:e.parentNode,n=null;for(;m;m=m.parentNode)r.push([m,s]),n=m;n&&n===e.ownerDocument&&r.push([n.defaultView||n.parentWindow||a,s])}for(l=0;l<r.length&&!c.isPropagationStopped();l++)m=r[l][0],c.type=r[l][1],q=(f._data(m,"events")||{})[c.type]&&f._data(m,"handle"),q&&q.apply(m,d),q=o&&m[o],q&&f.acceptData(m)&&q.apply(m,d)===!1&&c.preventDefault();c.type=h,!g&&!c.isDefaultPrevented()&&(!p._default||p._default.apply(e.ownerDocument,d)===!1)&&(h!=="click"||!f.nodeName(e,"a"))&&f.acceptData(e)&&o&&e[h]&&(h!=="focus"&&h!=="blur"||c.target.offsetWidth!==0)&&!f.isWindow(e)&&(n=e[o],n&&(e[o]=null),f.event.triggered=h,e[h](),f.event.triggered=b,n&&(e[o]=n));return c.result}},dispatch:function(c){c=f.event.fix(c||a.event);var d=(f._data(this,"events")||{})[c.type]||[],e=d.delegateCount,g=[].slice.call(arguments,0),h=!c.exclusive&&!c.namespace,i=[],j,k,l,m,n,o,p,q,r,s,t;g[0]=c,c.delegateTarget=this;if(e&&!c.target.disabled&&(!c.button||c.type!=="click")){m=f(this),m.context=this.ownerDocument||this;for(l=c.target;l!=this;l=l.parentNode||this){o={},q=[],m[0]=l;for(j=0;j<e;j++)r=d[j],s=r.selector,o[s]===b&&(o[s]=r.quick?H(l,r.quick):m.is(s)),o[s]&&q.push(r);q.length&&i.push({elem:l,matches:q})}}d.length>e&&i.push({elem:this,matches:d.slice(e)});for(j=0;j<i.length&&!c.isPropagationStopped();j++){p=i[j],c.currentTarget=p.elem;for(k=0;k<p.matches.length&&!c.isImmediatePropagationStopped();k++){r=p.matches[k];if(h||!c.namespace&&!r.namespace||c.namespace_re&&c.namespace_re.test(r.namespace))c.data=r.data,c.handleObj=r,n=((f.event.special[r.origType]||{}).handle||r.handler).apply(p.elem,g),n!==b&&(c.result=n,n===!1&&(c.preventDefault(),c.stopPropagation()))}}return c.result},props:"attrChange attrName relatedNode srcElement altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),fixHooks:{},keyHooks:{props:"char charCode key keyCode".split(" "),filter:function(a,b){a.which==null&&(a.which=b.charCode!=null?b.charCode:b.keyCode);return a}},mouseHooks:{props:"button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),filter:function(a,d){var e,f,g,h=d.button,i=d.fromElement;a.pageX==null&&d.clientX!=null&&(e=a.target.ownerDocument||c,f=e.documentElement,g=e.body,a.pageX=d.clientX+(f&&f.scrollLeft||g&&g.scrollLeft||0)-(f&&f.clientLeft||g&&g.clientLeft||0),a.pageY=d.clientY+(f&&f.scrollTop||g&&g.scrollTop||0)-(f&&f.clientTop||g&&g.clientTop||0)),!a.relatedTarget&&i&&(a.relatedTarget=i===a.target?d.toElement:i),!a.which&&h!==b&&(a.which=h&1?1:h&2?3:h&4?2:0);return a}},fix:function(a){if(a[f.expando])return a;var d,e,g=a,h=f.event.fixHooks[a.type]||{},i=h.props?this.props.concat(h.props):this.props;a=f.Event(g);for(d=i.length;d;)e=i[--d],a[e]=g[e];a.target||(a.target=g.srcElement||c),a.target.nodeType===3&&(a.target=a.target.parentNode),a.metaKey===b&&(a.metaKey=a.ctrlKey);return h.filter?h.filter(a,g):a},special:{ready:{setup:f.bindReady},load:{noBubble:!0},focus:{delegateType:"focusin"},blur:{delegateType:"focusout"},beforeunload:{setup:function(a,b,c){f.isWindow(this)&&(this.onbeforeunload=c)},teardown:function(a,b){this.onbeforeunload===b&&(this.onbeforeunload=null)}}},simulate:function(a,b,c,d){var e=f.extend(new f.Event,c,{type:a,isSimulated:!0,originalEvent:{}});d?f.event.trigger(e,null,b):f.event.dispatch.call(b,e),e.isDefaultPrevented()&&c.preventDefault()}},f.event.handle=f.event.dispatch,f.removeEvent=c.removeEventListener?function(a,b,c){a.removeEventListener&&a.removeEventListener(b,c,!1)}:function(a,b,c){a.detachEvent&&a.detachEvent("on"+b,c)},f.Event=function(a,b){if(!(this instanceof f.Event))return new f.Event(a,b);a&&a.type?(this.originalEvent=a,this.type=a.type,this.isDefaultPrevented=a.defaultPrevented||a.returnValue===!1||a.getPreventDefault&&a.getPreventDefault()?K:J):this.type=a,b&&f.extend(this,b),this.timeStamp=a&&a.timeStamp||f.now(),this[f.expando]=!0},f.Event.prototype={preventDefault:function(){this.isDefaultPrevented=K;var a=this.originalEvent;!a||(a.preventDefault?a.preventDefault():a.returnValue=!1)},stopPropagation:function(){this.isPropagationStopped=K;var a=this.originalEvent;!a||(a.stopPropagation&&a.stopPropagation(),a.cancelBubble=!0)},stopImmediatePropagation:function(){this.isImmediatePropagationStopped=K,this.stopPropagation()},isDefaultPrevented:J,isPropagationStopped:J,isImmediatePropagationStopped:J},f.each({mouseenter:"mouseover",mouseleave:"mouseout"},function(a,b){f.event.special[a]={delegateType:b,bindType:b,handle:function(a){var c=this,d=a.relatedTarget,e=a.handleObj,g=e.selector,h;if(!d||d!==c&&!f.contains(c,d))a.type=e.origType,h=e.handler.apply(this,arguments),a.type=b;return h}}}),f.support.submitBubbles||(f.event.special.submit={setup:function(){if(f.nodeName(this,"form"))return!1;f.event.add(this,"click._submit keypress._submit",function(a){var c=a.target,d=f.nodeName(c,"input")||f.nodeName(c,"button")?c.form:b;d&&!d._submit_attached&&(f.event.add(d,"submit._submit",function(a){this.parentNode&&!a.isTrigger&&f.event.simulate("submit",this.parentNode,a,!0)}),d._submit_attached=!0)})},teardown:function(){if(f.nodeName(this,"form"))return!1;f.event.remove(this,"._submit")}}),f.support.changeBubbles||(f.event.special.change={setup:function(){if(z.test(this.nodeName)){if(this.type==="checkbox"||this.type==="radio")f.event.add(this,"propertychange._change",function(a){a.originalEvent.propertyName==="checked"&&(this._just_changed=!0)}),f.event.add(this,"click._change",function(a){this._just_changed&&!a.isTrigger&&(this._just_changed=!1,f.event.simulate("change",this,a,!0))});return!1}f.event.add(this,"beforeactivate._change",function(a){var b=a.target;z.test(b.nodeName)&&!b._change_attached&&(f.event.add(b,"change._change",function(a){this.parentNode&&!a.isSimulated&&!a.isTrigger&&f.event.simulate("change",this.parentNode,a,!0)}),b._change_attached=!0)})},handle:function(a){var b=a.target;if(this!==b||a.isSimulated||a.isTrigger||b.type!=="radio"&&b.type!=="checkbox")return a.handleObj.handler.apply(this,arguments)},teardown:function(){f.event.remove(this,"._change");return z.test(this.nodeName)}}),f.support.focusinBubbles||f.each({focus:"focusin",blur:"focusout"},function(a,b){var d=0,e=function(a){f.event.simulate(b,a.target,f.event.fix(a),!0)};f.event.special[b]={setup:function(){d++===0&&c.addEventListener(a,e,!0)},teardown:function(){--d===0&&c.removeEventListener(a,e,!0)}}}),f.fn.extend({on:function(a,c,d,e,g){var h,i;if(typeof a=="object"){typeof c!="string"&&(d=c,c=b);for(i in a)this.on(i,c,d,a[i],g);return this}d==null&&e==null?(e=c,d=c=b):e==null&&(typeof c=="string"?(e=d,d=b):(e=d,d=c,c=b));if(e===!1)e=J;else if(!e)return this;g===1&&(h=e,e=function(a){f().off(a);return h.apply(this,arguments)},e.guid=h.guid||(h.guid=f.guid++));return this.each(function(){f.event.add(this,a,e,d,c)})},one:function(a,b,c,d){return this.on.call(this,a,b,c,d,1)},off:function(a,c,d){if(a&&a.preventDefault&&a.handleObj){var e=a.handleObj;f(a.delegateTarget).off(e.namespace?e.type+"."+e.namespace:e.type,e.selector,e.handler);return this}if(typeof a=="object"){for(var g in a)this.off(g,c,a[g]);return this}if(c===!1||typeof c=="function")d=c,c=b;d===!1&&(d=J);return this.each(function(){f.event.remove(this,a,d,c)})},bind:function(a,b,c){return this.on(a,null,b,c)},unbind:function(a,b){return this.off(a,null,b)},live:function(a,b,c){f(this.context).on(a,this.selector,b,c);return this},die:function(a,b){f(this.context).off(a,this.selector||"**",b);return this},delegate:function(a,b,c,d){return this.on(b,a,c,d)},undelegate:function(a,b,c){return arguments.length==1?this.off(a,"**"):this.off(b,a,c)},trigger:function(a,b){return this.each(function(){f.event.trigger(a,b,this)})},triggerHandler:function(a,b){if(this[0])return f.event.trigger(a,b,this[0],!0)},toggle:function(a){var b=arguments,c=a.guid||f.guid++,d=0,e=function(c){var e=(f._data(this,"lastToggle"+a.guid)||0)%d;f._data(this,"lastToggle"+a.guid,e+1),c.preventDefault();return b[e].apply(this,arguments)||!1};e.guid=c;while(d<b.length)b[d++].guid=c;return this.click(e)},hover:function(a,b){return this.mouseenter(a).mouseleave(b||a)}}),f.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "),function(a,b){f.fn[b]=function(a,c){c==null&&(c=a,a=null);return arguments.length>0?this.on(b,null,a,c):this.trigger(b)},f.attrFn&&(f.attrFn[b]=!0),C.test(b)&&(f.event.fixHooks[b]=f.event.keyHooks),D.test(b)&&(f.event.fixHooks[b]=f.event.mouseHooks)}),function(){function x(a,b,c,e,f,g){for(var h=0,i=e.length;h<i;h++){var j=e[h];if(j){var k=!1;j=j[a];while(j){if(j[d]===c){k=e[j.sizset];break}if(j.nodeType===1){g||(j[d]=c,j.sizset=h);if(typeof b!="string"){if(j===b){k=!0;break}}else if(m.filter(b,[j]).length>0){k=j;break}}j=j[a]}e[h]=k}}}function w(a,b,c,e,f,g){for(var h=0,i=e.length;h<i;h++){var j=e[h];if(j){var k=!1;j=j[a];while(j){if(j[d]===c){k=e[j.sizset];break}j.nodeType===1&&!g&&(j[d]=c,j.sizset=h);if(j.nodeName.toLowerCase()===b){k=j;break}j=j[a]}e[h]=k}}}var a=/((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^\[\]]*\]|['"][^'"]*['"]|[^\[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g,d="sizcache"+(Math.random()+"").replace(".",""),e=0,g=Object.prototype.toString,h=!1,i=!0,j=/\\/g,k=/\r\n/g,l=/\W/;[0,0].sort(function(){i=!1;return 0});var m=function(b,d,e,f){e=e||[],d=d||c;var h=d;if(d.nodeType!==1&&d.nodeType!==9)return[];if(!b||typeof b!="string")return e;var i,j,k,l,n,q,r,t,u=!0,v=m.isXML(d),w=[],x=b;do{a.exec(""),i=a.exec(x);if(i){x=i[3],w.push(i[1]);if(i[2]){l=i[3];break}}}while(i);if(w.length>1&&p.exec(b))if(w.length===2&&o.relative[w[0]])j=y(w[0]+w[1],d,f);else{j=o.relative[w[0]]?[d]:m(w.shift(),d);while(w.length)b=w.shift(),o.relative[b]&&(b+=w.shift()),j=y(b,j,f)}else{!f&&w.length>1&&d.nodeType===9&&!v&&o.match.ID.test(w[0])&&!o.match.ID.test(w[w.length-1])&&(n=m.find(w.shift(),d,v),d=n.expr?m.filter(n.expr,n.set)[0]:n.set[0]);if(d){n=f?{expr:w.pop(),set:s(f)}:m.find(w.pop(),w.length===1&&(w[0]==="~"||w[0]==="+")&&d.parentNode?d.parentNode:d,v),j=n.expr?m.filter(n.expr,n.set):n.set,w.length>0?k=s(j):u=!1;while(w.length)q=w.pop(),r=q,o.relative[q]?r=w.pop():q="",r==null&&(r=d),o.relative[q](k,r,v)}else k=w=[]}k||(k=j),k||m.error(q||b);if(g.call(k)==="[object Array]")if(!u)e.push.apply(e,k);else if(d&&d.nodeType===1)for(t=0;k[t]!=null;t++)k[t]&&(k[t]===!0||k[t].nodeType===1&&m.contains(d,k[t]))&&e.push(j[t]);else for(t=0;k[t]!=null;t++)k[t]&&k[t].nodeType===1&&e.push(j[t]);else s(k,e);l&&(m(l,h,e,f),m.uniqueSort(e));return e};m.uniqueSort=function(a){if(u){h=i,a.sort(u);if(h)for(var b=1;b<a.length;b++)a[b]===a[b-1]&&a.splice(b--,1)}return a},m.matches=function(a,b){return m(a,null,null,b)},m.matchesSelector=function(a,b){return m(b,null,null,[a]).length>0},m.find=function(a,b,c){var d,e,f,g,h,i;if(!a)return[];for(e=0,f=o.order.length;e<f;e++){h=o.order[e];if(g=o.leftMatch[h].exec(a)){i=g[1],g.splice(1,1);if(i.substr(i.length-1)!=="\\"){g[1]=(g[1]||"").replace(j,""),d=o.find[h](g,b,c);if(d!=null){a=a.replace(o.match[h],"");break}}}}d||(d=typeof b.getElementsByTagName!="undefined"?b.getElementsByTagName("*"):[]);return{set:d,expr:a}},m.filter=function(a,c,d,e){var f,g,h,i,j,k,l,n,p,q=a,r=[],s=c,t=c&&c[0]&&m.isXML(c[0]);while(a&&c.length){for(h in o.filter)if((f=o.leftMatch[h].exec(a))!=null&&f[2]){k=o.filter[h],l=f[1],g=!1,f.splice(1,1);if(l.substr(l.length-1)==="\\")continue;s===r&&(r=[]);if(o.preFilter[h]){f=o.preFilter[h](f,s,d,r,e,t);if(!f)g=i=!0;else if(f===!0)continue}if(f)for(n=0;(j=s[n])!=null;n++)j&&(i=k(j,f,n,s),p=e^i,d&&i!=null?p?g=!0:s[n]=!1:p&&(r.push(j),g=!0));if(i!==b){d||(s=r),a=a.replace(o.match[h],"");if(!g)return[];break}}if(a===q)if(g==null)m.error(a);else break;q=a}return s},m.error=function(a){throw new Error("Syntax error, unrecognized expression: "+a)};var n=m.getText=function(a){var b,c,d=a.nodeType,e="";if(d){if(d===1||d===9){if(typeof a.textContent=="string")return a.textContent;if(typeof a.innerText=="string")return a.innerText.replace(k,"");for(a=a.firstChild;a;a=a.nextSibling)e+=n(a)}else if(d===3||d===4)return a.nodeValue}else for(b=0;c=a[b];b++)c.nodeType!==8&&(e+=n(c));return e},o=m.selectors={order:["ID","NAME","TAG"],match:{ID:/#((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,CLASS:/\.((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,NAME:/\[name=['"]*((?:[\w\u00c0-\uFFFF\-]|\\.)+)['"]*\]/,ATTR:/\[\s*((?:[\w\u00c0-\uFFFF\-]|\\.)+)\s*(?:(\S?=)\s*(?:(['"])(.*?)\3|(#?(?:[\w\u00c0-\uFFFF\-]|\\.)*)|)|)\s*\]/,TAG:/^((?:[\w\u00c0-\uFFFF\*\-]|\\.)+)/,CHILD:/:(only|nth|last|first)-child(?:\(\s*(even|odd|(?:[+\-]?\d+|(?:[+\-]?\d*)?n\s*(?:[+\-]\s*\d+)?))\s*\))?/,POS:/:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^\-]|$)/,PSEUDO:/:((?:[\w\u00c0-\uFFFF\-]|\\.)+)(?:\((['"]?)((?:\([^\)]+\)|[^\(\)]*)+)\2\))?/},leftMatch:{},attrMap:{"class":"className","for":"htmlFor"},attrHandle:{href:function(a){return a.getAttribute("href")},type:function(a){return a.getAttribute("type")}},relative:{"+":function(a,b){var c=typeof b=="string",d=c&&!l.test(b),e=c&&!d;d&&(b=b.toLowerCase());for(var f=0,g=a.length,h;f<g;f++)if(h=a[f]){while((h=h.previousSibling)&&h.nodeType!==1);a[f]=e||h&&h.nodeName.toLowerCase()===b?h||!1:h===b}e&&m.filter(b,a,!0)},">":function(a,b){var c,d=typeof b=="string",e=0,f=a.length;if(d&&!l.test(b)){b=b.toLowerCase();for(;e<f;e++){c=a[e];if(c){var g=c.parentNode;a[e]=g.nodeName.toLowerCase()===b?g:!1}}}else{for(;e<f;e++)c=a[e],c&&(a[e]=d?c.parentNode:c.parentNode===b);d&&m.filter(b,a,!0)}},"":function(a,b,c){var d,f=e++,g=x;typeof b=="string"&&!l.test(b)&&(b=b.toLowerCase(),d=b,g=w),g("parentNode",b,f,a,d,c)},"~":function(a,b,c){var d,f=e++,g=x;typeof b=="string"&&!l.test(b)&&(b=b.toLowerCase(),d=b,g=w),g("previousSibling",b,f,a,d,c)}},find:{ID:function(a,b,c){if(typeof b.getElementById!="undefined"&&!c){var d=b.getElementById(a[1]);return d&&d.parentNode?[d]:[]}},NAME:function(a,b){if(typeof b.getElementsByName!="undefined"){var c=[],d=b.getElementsByName(a[1]);for(var e=0,f=d.length;e<f;e++)d[e].getAttribute("name")===a[1]&&c.push(d[e]);return c.length===0?null:c}},TAG:function(a,b){if(typeof b.getElementsByTagName!="undefined")return b.getElementsByTagName(a[1])}},preFilter:{CLASS:function(a,b,c,d,e,f){a=" "+a[1].replace(j,"")+" ";if(f)return a;for(var g=0,h;(h=b[g])!=null;g++)h&&(e^(h.className&&(" "+h.className+" ").replace(/[\t\n\r]/g," ").indexOf(a)>=0)?c||d.push(h):c&&(b[g]=!1));return!1},ID:function(a){return a[1].replace(j,"")},TAG:function(a,b){return a[1].replace(j,"").toLowerCase()},CHILD:function(a){if(a[1]==="nth"){a[2]||m.error(a[0]),a[2]=a[2].replace(/^\+|\s*/g,"");var b=/(-?)(\d*)(?:n([+\-]?\d*))?/.exec(a[2]==="even"&&"2n"||a[2]==="odd"&&"2n+1"||!/\D/.test(a[2])&&"0n+"+a[2]||a[2]);a[2]=b[1]+(b[2]||1)-0,a[3]=b[3]-0}else a[2]&&m.error(a[0]);a[0]=e++;return a},ATTR:function(a,b,c,d,e,f){var g=a[1]=a[1].replace(j,"");!f&&o.attrMap[g]&&(a[1]=o.attrMap[g]),a[4]=(a[4]||a[5]||"").replace(j,""),a[2]==="~="&&(a[4]=" "+a[4]+" ");return a},PSEUDO:function(b,c,d,e,f){if(b[1]==="not")if((a.exec(b[3])||"").length>1||/^\w/.test(b[3]))b[3]=m(b[3],null,null,c);else{var g=m.filter(b[3],c,d,!0^f);d||e.push.apply(e,g);return!1}else if(o.match.POS.test(b[0])||o.match.CHILD.test(b[0]))return!0;return b},POS:function(a){a.unshift(!0);return a}},filters:{enabled:function(a){return a.disabled===!1&&a.type!=="hidden"},disabled:function(a){return a.disabled===!0},checked:function(a){return a.checked===!0},selected:function(a){a.parentNode&&a.parentNode.selectedIndex;return a.selected===!0},parent:function(a){return!!a.firstChild},empty:function(a){return!a.firstChild},has:function(a,b,c){return!!m(c[3],a).length},header:function(a){return/h\d/i.test(a.nodeName)},text:function(a){var b=a.getAttribute("type"),c=a.type;return a.nodeName.toLowerCase()==="input"&&"text"===c&&(b===c||b===null)},radio:function(a){return a.nodeName.toLowerCase()==="input"&&"radio"===a.type},checkbox:function(a){return a.nodeName.toLowerCase()==="input"&&"checkbox"===a.type},file:function(a){return a.nodeName.toLowerCase()==="input"&&"file"===a.type},password:function(a){return a.nodeName.toLowerCase()==="input"&&"password"===a.type},submit:function(a){var b=a.nodeName.toLowerCase();return(b==="input"||b==="button")&&"submit"===a.type},image:function(a){return a.nodeName.toLowerCase()==="input"&&"image"===a.type},reset:function(a){var b=a.nodeName.toLowerCase();return(b==="input"||b==="button")&&"reset"===a.type},button:function(a){var b=a.nodeName.toLowerCase();return b==="input"&&"button"===a.type||b==="button"},input:function(a){return/input|select|textarea|button/i.test(a.nodeName)},focus:function(a){return a===a.ownerDocument.activeElement}},setFilters:{first:function(a,b){return b===0},last:function(a,b,c,d){return b===d.length-1},even:function(a,b){return b%2===0},odd:function(a,b){return b%2===1},lt:function(a,b,c){return b<c[3]-0},gt:function(a,b,c){return b>c[3]-0},nth:function(a,b,c){return c[3]-0===b},eq:function(a,b,c){return c[3]-0===b}},filter:{PSEUDO:function(a,b,c,d){var e=b[1],f=o.filters[e];if(f)return f(a,c,b,d);if(e==="contains")return(a.textContent||a.innerText||n([a])||"").indexOf(b[3])>=0;if(e==="not"){var g=b[3];for(var h=0,i=g.length;h<i;h++)if(g[h]===a)return!1;return!0}m.error(e)},CHILD:function(a,b){var c,e,f,g,h,i,j,k=b[1],l=a;switch(k){case"only":case"first":while(l=l.previousSibling)if(l.nodeType===1)return!1;if(k==="first")return!0;l=a;case"last":while(l=l.nextSibling)if(l.nodeType===1)return!1;return!0;case"nth":c=b[2],e=b[3];if(c===1&&e===0)return!0;f=b[0],g=a.parentNode;if(g&&(g[d]!==f||!a.nodeIndex)){i=0;for(l=g.firstChild;l;l=l.nextSibling)l.nodeType===1&&(l.nodeIndex=++i);g[d]=f}j=a.nodeIndex-e;return c===0?j===0:j%c===0&&j/c>=0}},ID:function(a,b){return a.nodeType===1&&a.getAttribute("id")===b},TAG:function(a,b){return b==="*"&&a.nodeType===1||!!a.nodeName&&a.nodeName.toLowerCase()===b},CLASS:function(a,b){return(" "+(a.className||a.getAttribute("class"))+" ").indexOf(b)>-1},ATTR:function(a,b){var c=b[1],d=m.attr?m.attr(a,c):o.attrHandle[c]?o.attrHandle[c](a):a[c]!=null?a[c]:a.getAttribute(c),e=d+"",f=b[2],g=b[4];return d==null?f==="!=":!f&&m.attr?d!=null:f==="="?e===g:f==="*="?e.indexOf(g)>=0:f==="~="?(" "+e+" ").indexOf(g)>=0:g?f==="!="?e!==g:f==="^="?e.indexOf(g)===0:f==="$="?e.substr(e.length-g.length)===g:f==="|="?e===g||e.substr(0,g.length+1)===g+"-":!1:e&&d!==!1},POS:function(a,b,c,d){var e=b[2],f=o.setFilters[e];if(f)return f(a,c,b,d)}}},p=o.match.POS,q=function(a,b){return"\\"+(b-0+1)};for(var r in o.match)o.match[r]=new RegExp(o.match[r].source+/(?![^\[]*\])(?![^\(]*\))/.source),o.leftMatch[r]=new RegExp(/(^(?:.|\r|\n)*?)/.source+o.match[r].source.replace(/\\(\d+)/g,q));var s=function(a,b){a=Array.prototype.slice.call(a,0);if(b){b.push.apply(b,a);return b}return a};try{Array.prototype.slice.call(c.documentElement.childNodes,0)[0].nodeType}catch(t){s=function(a,b){var c=0,d=b||[];if(g.call(a)==="[object Array]")Array.prototype.push.apply(d,a);else if(typeof a.length=="number")for(var e=a.length;c<e;c++)d.push(a[c]);else for(;a[c];c++)d.push(a[c]);return d}}var u,v;c.documentElement.compareDocumentPosition?u=function(a,b){if(a===b){h=!0;return 0}if(!a.compareDocumentPosition||!b.compareDocumentPosition)return a.compareDocumentPosition?-1:1;return a.compareDocumentPosition(b)&4?-1:1}:(u=function(a,b){if(a===b){h=!0;return 0}if(a.sourceIndex&&b.sourceIndex)return a.sourceIndex-b.sourceIndex;var c,d,e=[],f=[],g=a.parentNode,i=b.parentNode,j=g;if(g===i)return v(a,b);if(!g)return-1;if(!i)return 1;while(j)e.unshift(j),j=j.parentNode;j=i;while(j)f.unshift(j),j=j.parentNode;c=e.length,d=f.length;for(var k=0;k<c&&k<d;k++)if(e[k]!==f[k])return v(e[k],f[k]);return k===c?v(a,f[k],-1):v(e[k],b,1)},v=function(a,b,c){if(a===b)return c;var d=a.nextSibling;while(d){if(d===b)return-1;d=d.nextSibling}return 1}),function(){var a=c.createElement("div"),d="script"+(new Date).getTime(),e=c.documentElement;a.innerHTML="<a name='"+d+"'/>",e.insertBefore(a,e.firstChild),c.getElementById(d)&&(o.find.ID=function(a,c,d){if(typeof c.getElementById!="undefined"&&!d){var e=c.getElementById(a[1]);return e?e.id===a[1]||typeof e.getAttributeNode!="undefined"&&e.getAttributeNode("id").nodeValue===a[1]?[e]:b:[]}},o.filter.ID=function(a,b){var c=typeof a.getAttributeNode!="undefined"&&a.getAttributeNode("id");return a.nodeType===1&&c&&c.nodeValue===b}),e.removeChild(a),e=a=null}(),function(){var a=c.createElement("div");a.appendChild(c.createComment("")),a.getElementsByTagName("*").length>0&&(o.find.TAG=function(a,b){var c=b.getElementsByTagName(a[1]);if(a[1]==="*"){var d=[];for(var e=0;c[e];e++)c[e].nodeType===1&&d.push(c[e]);c=d}return c}),a.innerHTML="<a href='#'></a>",a.firstChild&&typeof a.firstChild.getAttribute!="undefined"&&a.firstChild.getAttribute("href")!=="#"&&(o.attrHandle.href=function(a){return a.getAttribute("href",2)}),a=null}(),c.querySelectorAll&&function(){var a=m,b=c.createElement("div"),d="__sizzle__";b.innerHTML="<p class='TEST'></p>";if(!b.querySelectorAll||b.querySelectorAll(".TEST").length!==0){m=function(b,e,f,g){e=e||c;if(!g&&!m.isXML(e)){var h=/^(\w+$)|^\.([\w\-]+$)|^#([\w\-]+$)/.exec(b);if(h&&(e.nodeType===1||e.nodeType===9)){if(h[1])return s(e.getElementsByTagName(b),f);if(h[2]&&o.find.CLASS&&e.getElementsByClassName)return s(e.getElementsByClassName(h[2]),f)}if(e.nodeType===9){if(b==="body"&&e.body)return s([e.body],f);if(h&&h[3]){var i=e.getElementById(h[3]);if(!i||!i.parentNode)return s([],f);if(i.id===h[3])return s([i],f)}try{return s(e.querySelectorAll(b),f)}catch(j){}}else if(e.nodeType===1&&e.nodeName.toLowerCase()!=="object"){var k=e,l=e.getAttribute("id"),n=l||d,p=e.parentNode,q=/^\s*[+~]/.test(b);l?n=n.replace(/'/g,"\\$&"):e.setAttribute("id",n),q&&p&&(e=e.parentNode);try{if(!q||p)return s(e.querySelectorAll("[id='"+n+"'] "+b),f)}catch(r){}finally{l||k.removeAttribute("id")}}}return a(b,e,f,g)};for(var e in a)m[e]=a[e];b=null}}(),function(){var a=c.documentElement,b=a.matchesSelector||a.mozMatchesSelector||a.webkitMatchesSelector||a.msMatchesSelector;if(b){var d=!b.call(c.createElement("div"),"div"),e=!1;try{b.call(c.documentElement,"[test!='']:sizzle")}catch(f){e=!0}m.matchesSelector=function(a,c){c=c.replace(/\=\s*([^'"\]]*)\s*\]/g,"='$1']");if(!m.isXML(a))try{if(e||!o.match.PSEUDO.test(c)&&!/!=/.test(c)){var f=b.call(a,c);if(f||!d||a.document&&a.document.nodeType!==11)return f}}catch(g){}return m(c,null,null,[a]).length>0}}}(),function(){var a=c.createElement("div");a.innerHTML="<div class='test e'></div><div class='test'></div>";if(!!a.getElementsByClassName&&a.getElementsByClassName("e").length!==0){a.lastChild.className="e";if(a.getElementsByClassName("e").length===1)return;o.order.splice(1,0,"CLASS"),o.find.CLASS=function(a,b,c){if(typeof b.getElementsByClassName!="undefined"&&!c)return b.getElementsByClassName(a[1])},a=null}}(),c.documentElement.contains?m.contains=function(a,b){return a!==b&&(a.contains?a.contains(b):!0)}:c.documentElement.compareDocumentPosition?m.contains=function(a,b){return!!(a.compareDocumentPosition(b)&16)}:m.contains=function(){return!1},m.isXML=function(a){var b=(a?a.ownerDocument||a:0).documentElement;return b?b.nodeName!=="HTML":!1};var y=function(a,b,c){var d,e=[],f="",g=b.nodeType?[b]:b;while(d=o.match.PSEUDO.exec(a))f+=d[0],a=a.replace(o.match.PSEUDO,"");a=o.relative[a]?a+"*":a;for(var h=0,i=g.length;h<i;h++)m(a,g[h],e,c);return m.filter(f,e)};m.attr=f.attr,m.selectors.attrMap={},f.find=m,f.expr=m.selectors,f.expr[":"]=f.expr.filters,f.unique=m.uniqueSort,f.text=m.getText,f.isXMLDoc=m.isXML,f.contains=m.contains}();var L=/Until$/,M=/^(?:parents|prevUntil|prevAll)/,N=/,/,O=/^.[^:#\[\.,]*$/,P=Array.prototype.slice,Q=f.expr.match.POS,R={children:!0,contents:!0,next:!0,prev:!0};f.fn.extend({find:function(a){var b=this,c,d;if(typeof a!="string")return f(a).filter(function(){for(c=0,d=b.length;c<d;c++)if(f.contains(b[c],this))return!0});var e=this.pushStack("","find",a),g,h,i;for(c=0,d=this.length;c<d;c++){g=e.length,f.find(a,this[c],e);if(c>0)for(h=g;h<e.length;h++)for(i=0;i<g;i++)if(e[i]===e[h]){e.splice(h--,1);break}}return e},has:function(a){var b=f(a);return this.filter(function(){for(var a=0,c=b.length;a<c;a++)if(f.contains(this,b[a]))return!0})},not:function(a){return this.pushStack(T(this,a,!1),"not",a)},filter:function(a){return this.pushStack(T(this,a,!0),"filter",a)},is:function(a){return!!a&&(typeof a=="string"?Q.test(a)?f(a,this.context).index(this[0])>=0:f.filter(a,this).length>0:this.filter(a).length>0)},closest:function(a,b){var c=[],d,e,g=this[0];if(f.isArray(a)){var h=1;while(g&&g.ownerDocument&&g!==b){for(d=0;d<a.length;d++)f(g).is(a[d])&&c.push({selector:a[d],elem:g,level:h});g=g.parentNode,h++}return c}var i=Q.test(a)||typeof a!="string"?f(a,b||this.context):0;for(d=0,e=this.length;d<e;d++){g=this[d];while(g){if(i?i.index(g)>-1:f.find.matchesSelector(g,a)){c.push(g);break}g=g.parentNode;if(!g||!g.ownerDocument||g===b||g.nodeType===11)break}}c=c.length>1?f.unique(c):c;return this.pushStack(c,"closest",a)},index:function(a){if(!a)return this[0]&&this[0].parentNode?this.prevAll().length:-1;if(typeof a=="string")return f.inArray(this[0],f(a));return f.inArray(a.jquery?a[0]:a,this)},add:function(a,b){var c=typeof a=="string"?f(a,b):f.makeArray(a&&a.nodeType?[a]:a),d=f.merge(this.get(),c);return this.pushStack(S(c[0])||S(d[0])?d:f.unique(d))},andSelf:function(){return this.add(this.prevObject)}}),f.each({parent:function(a){var b=a.parentNode;return b&&b.nodeType!==11?b:null},parents:function(a){return f.dir(a,"parentNode")},parentsUntil:function(a,b,c){return f.dir(a,"parentNode",c)},next:function(a){return f.nth(a,2,"nextSibling")},prev:function(a){return f.nth(a,2,"previousSibling")},nextAll:function(a){return f.dir(a,"nextSibling")},prevAll:function(a){return f.dir(a,"previousSibling")},nextUntil:function(a,b,c){return f.dir(a,"nextSibling",c)},prevUntil:function(a,b,c){return f.dir(a,"previousSibling",c)},siblings:function(a){return f.sibling(a.parentNode.firstChild,a)},children:function(a){return f.sibling(a.firstChild)},contents:function(a){return f.nodeName(a,"iframe")?a.contentDocument||a.contentWindow.document:f.makeArray(a.childNodes)}},function(a,b){f.fn[a]=function(c,d){var e=f.map(this,b,c);L.test(a)||(d=c),d&&typeof d=="string"&&(e=f.filter(d,e)),e=this.length>1&&!R[a]?f.unique(e):e,(this.length>1||N.test(d))&&M.test(a)&&(e=e.reverse());return this.pushStack(e,a,P.call(arguments).join(","))}}),f.extend({filter:function(a,b,c){c&&(a=":not("+a+")");return b.length===1?f.find.matchesSelector(b[0],a)?[b[0]]:[]:f.find.matches(a,b)},dir:function(a,c,d){var e=[],g=a[c];while(g&&g.nodeType!==9&&(d===b||g.nodeType!==1||!f(g).is(d)))g.nodeType===1&&e.push(g),g=g[c];return e},nth:function(a,b,c,d){b=b||1;var e=0;for(;a;a=a[c])if(a.nodeType===1&&++e===b)break;return a},sibling:function(a,b){var c=[];for(;a;a=a.nextSibling)a.nodeType===1&&a!==b&&c.push(a);return c}});var V="abbr|article|aside|audio|canvas|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",W=/ jQuery\d+="(?:\d+|null)"/g,X=/^\s+/,Y=/<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/ig,Z=/<([\w:]+)/,$=/<tbody/i,_=/<|&#?\w+;/,ba=/<(?:script|style)/i,bb=/<(?:script|object|embed|option|style)/i,bc=new RegExp("<(?:"+V+")","i"),bd=/checked\s*(?:[^=]|=\s*.checked.)/i,be=/\/(java|ecma)script/i,bf=/^\s*<!(?:\[CDATA\[|\-\-)/,bg={option:[1,"<select multiple='multiple'>","</select>"],legend:[1,"<fieldset>","</fieldset>"],thead:[1,"<table>","</table>"],tr:[2,"<table><tbody>","</tbody></table>"],td:[3,"<table><tbody><tr>","</tr></tbody></table>"],col:[2,"<table><tbody></tbody><colgroup>","</colgroup></table>"],area:[1,"<map>","</map>"],_default:[0,"",""]},bh=U(c);bg.optgroup=bg.option,bg.tbody=bg.tfoot=bg.colgroup=bg.caption=bg.thead,bg.th=bg.td,f.support.htmlSerialize||(bg._default=[1,"div<div>","</div>"]),f.fn.extend({text:function(a){if(f.isFunction(a))return this.each(function(b){var c=f(this);c.text(a.call(this,b,c.text()))});if(typeof a!="object"&&a!==b)return this.empty().append((this[0]&&this[0].ownerDocument||c).createTextNode(a));return f.text(this)},wrapAll:function(a){if(f.isFunction(a))return this.each(function(b){f(this).wrapAll(a.call(this,b))});if(this[0]){var b=f(a,this[0].ownerDocument).eq(0).clone(!0);this[0].parentNode&&b.insertBefore(this[0]),b.map(function(){var a=this;while(a.firstChild&&a.firstChild.nodeType===1)a=a.firstChild;return a}).append(this)}return this},wrapInner:function(a){if(f.isFunction(a))return this.each(function(b){f(this).wrapInner(a.call(this,b))});return this.each(function(){var b=f(this),c=b.contents();c.length?c.wrapAll(a):b.append(a)})},wrap:function(a){var b=f.isFunction(a);return this.each(function(c){f(this).wrapAll(b?a.call(this,c):a)})},unwrap:function(){return this.parent().each(function(){f.nodeName(this,"body")||f(this).replaceWith(this.childNodes)}).end()},append:function(){return this.domManip(arguments,!0,function(a){this.nodeType===1&&this.appendChild(a)})},prepend:function(){return this.domManip(arguments,!0,function(a){this.nodeType===1&&this.insertBefore(a,this.firstChild)})},before:function(){if(this[0]&&this[0].parentNode)return this.domManip(arguments,!1,function(a){this.parentNode.insertBefore(a,this)});if(arguments.length){var a=f.clean(arguments);a.push.apply(a,this.toArray());return this.pushStack(a,"before",arguments)}},after:function(){if(this[0]&&this[0].parentNode)return this.domManip(arguments,!1,function(a){this.parentNode.insertBefore(a,this.nextSibling)});if(arguments.length){var a=this.pushStack(this,"after",arguments);a.push.apply(a,f.clean(arguments));return a}},remove:function(a,b){for(var c=0,d;(d=this[c])!=null;c++)if(!a||f.filter(a,[d]).length)!b&&d.nodeType===1&&(f.cleanData(d.getElementsByTagName("*")),f.cleanData([d])),d.parentNode&&d.parentNode.removeChild(d);return this},empty:function()
{for(var a=0,b;(b=this[a])!=null;a++){b.nodeType===1&&f.cleanData(b.getElementsByTagName("*"));while(b.firstChild)b.removeChild(b.firstChild)}return this},clone:function(a,b){a=a==null?!1:a,b=b==null?a:b;return this.map(function(){return f.clone(this,a,b)})},html:function(a){if(a===b)return this[0]&&this[0].nodeType===1?this[0].innerHTML.replace(W,""):null;if(typeof a=="string"&&!ba.test(a)&&(f.support.leadingWhitespace||!X.test(a))&&!bg[(Z.exec(a)||["",""])[1].toLowerCase()]){a=a.replace(Y,"<$1></$2>");try{for(var c=0,d=this.length;c<d;c++)this[c].nodeType===1&&(f.cleanData(this[c].getElementsByTagName("*")),this[c].innerHTML=a)}catch(e){this.empty().append(a)}}else f.isFunction(a)?this.each(function(b){var c=f(this);c.html(a.call(this,b,c.html()))}):this.empty().append(a);return this},replaceWith:function(a){if(this[0]&&this[0].parentNode){if(f.isFunction(a))return this.each(function(b){var c=f(this),d=c.html();c.replaceWith(a.call(this,b,d))});typeof a!="string"&&(a=f(a).detach());return this.each(function(){var b=this.nextSibling,c=this.parentNode;f(this).remove(),b?f(b).before(a):f(c).append(a)})}return this.length?this.pushStack(f(f.isFunction(a)?a():a),"replaceWith",a):this},detach:function(a){return this.remove(a,!0)},domManip:function(a,c,d){var e,g,h,i,j=a[0],k=[];if(!f.support.checkClone&&arguments.length===3&&typeof j=="string"&&bd.test(j))return this.each(function(){f(this).domManip(a,c,d,!0)});if(f.isFunction(j))return this.each(function(e){var g=f(this);a[0]=j.call(this,e,c?g.html():b),g.domManip(a,c,d)});if(this[0]){i=j&&j.parentNode,f.support.parentNode&&i&&i.nodeType===11&&i.childNodes.length===this.length?e={fragment:i}:e=f.buildFragment(a,this,k),h=e.fragment,h.childNodes.length===1?g=h=h.firstChild:g=h.firstChild;if(g){c=c&&f.nodeName(g,"tr");for(var l=0,m=this.length,n=m-1;l<m;l++)d.call(c?bi(this[l],g):this[l],e.cacheable||m>1&&l<n?f.clone(h,!0,!0):h)}k.length&&f.each(k,bp)}return this}}),f.buildFragment=function(a,b,d){var e,g,h,i,j=a[0];b&&b[0]&&(i=b[0].ownerDocument||b[0]),i.createDocumentFragment||(i=c),a.length===1&&typeof j=="string"&&j.length<512&&i===c&&j.charAt(0)==="<"&&!bb.test(j)&&(f.support.checkClone||!bd.test(j))&&(f.support.html5Clone||!bc.test(j))&&(g=!0,h=f.fragments[j],h&&h!==1&&(e=h)),e||(e=i.createDocumentFragment(),f.clean(a,i,e,d)),g&&(f.fragments[j]=h?e:1);return{fragment:e,cacheable:g}},f.fragments={},f.each({appendTo:"append",prependTo:"prepend",insertBefore:"before",insertAfter:"after",replaceAll:"replaceWith"},function(a,b){f.fn[a]=function(c){var d=[],e=f(c),g=this.length===1&&this[0].parentNode;if(g&&g.nodeType===11&&g.childNodes.length===1&&e.length===1){e[b](this[0]);return this}for(var h=0,i=e.length;h<i;h++){var j=(h>0?this.clone(!0):this).get();f(e[h])[b](j),d=d.concat(j)}return this.pushStack(d,a,e.selector)}}),f.extend({clone:function(a,b,c){var d,e,g,h=f.support.html5Clone||!bc.test("<"+a.nodeName)?a.cloneNode(!0):bo(a);if((!f.support.noCloneEvent||!f.support.noCloneChecked)&&(a.nodeType===1||a.nodeType===11)&&!f.isXMLDoc(a)){bk(a,h),d=bl(a),e=bl(h);for(g=0;d[g];++g)e[g]&&bk(d[g],e[g])}if(b){bj(a,h);if(c){d=bl(a),e=bl(h);for(g=0;d[g];++g)bj(d[g],e[g])}}d=e=null;return h},clean:function(a,b,d,e){var g;b=b||c,typeof b.createElement=="undefined"&&(b=b.ownerDocument||b[0]&&b[0].ownerDocument||c);var h=[],i;for(var j=0,k;(k=a[j])!=null;j++){typeof k=="number"&&(k+="");if(!k)continue;if(typeof k=="string")if(!_.test(k))k=b.createTextNode(k);else{k=k.replace(Y,"<$1></$2>");var l=(Z.exec(k)||["",""])[1].toLowerCase(),m=bg[l]||bg._default,n=m[0],o=b.createElement("div");b===c?bh.appendChild(o):U(b).appendChild(o),o.innerHTML=m[1]+k+m[2];while(n--)o=o.lastChild;if(!f.support.tbody){var p=$.test(k),q=l==="table"&&!p?o.firstChild&&o.firstChild.childNodes:m[1]==="<table>"&&!p?o.childNodes:[];for(i=q.length-1;i>=0;--i)f.nodeName(q[i],"tbody")&&!q[i].childNodes.length&&q[i].parentNode.removeChild(q[i])}!f.support.leadingWhitespace&&X.test(k)&&o.insertBefore(b.createTextNode(X.exec(k)[0]),o.firstChild),k=o.childNodes}var r;if(!f.support.appendChecked)if(k[0]&&typeof (r=k.length)=="number")for(i=0;i<r;i++)bn(k[i]);else bn(k);k.nodeType?h.push(k):h=f.merge(h,k)}if(d){g=function(a){return!a.type||be.test(a.type)};for(j=0;h[j];j++)if(e&&f.nodeName(h[j],"script")&&(!h[j].type||h[j].type.toLowerCase()==="text/javascript"))e.push(h[j].parentNode?h[j].parentNode.removeChild(h[j]):h[j]);else{if(h[j].nodeType===1){var s=f.grep(h[j].getElementsByTagName("script"),g);h.splice.apply(h,[j+1,0].concat(s))}d.appendChild(h[j])}}return h},cleanData:function(a){var b,c,d=f.cache,e=f.event.special,g=f.support.deleteExpando;for(var h=0,i;(i=a[h])!=null;h++){if(i.nodeName&&f.noData[i.nodeName.toLowerCase()])continue;c=i[f.expando];if(c){b=d[c];if(b&&b.events){for(var j in b.events)e[j]?f.event.remove(i,j):f.removeEvent(i,j,b.handle);b.handle&&(b.handle.elem=null)}g?delete i[f.expando]:i.removeAttribute&&i.removeAttribute(f.expando),delete d[c]}}}});var bq=/alpha\([^)]*\)/i,br=/opacity=([^)]*)/,bs=/([A-Z]|^ms)/g,bt=/^-?\d+(?:px)?$/i,bu=/^-?\d/,bv=/^([\-+])=([\-+.\de]+)/,bw={position:"absolute",visibility:"hidden",display:"block"},bx=["Left","Right"],by=["Top","Bottom"],bz,bA,bB;f.fn.css=function(a,c){if(arguments.length===2&&c===b)return this;return f.access(this,a,c,!0,function(a,c,d){return d!==b?f.style(a,c,d):f.css(a,c)})},f.extend({cssHooks:{opacity:{get:function(a,b){if(b){var c=bz(a,"opacity","opacity");return c===""?"1":c}return a.style.opacity}}},cssNumber:{fillOpacity:!0,fontWeight:!0,lineHeight:!0,opacity:!0,orphans:!0,widows:!0,zIndex:!0,zoom:!0},cssProps:{"float":f.support.cssFloat?"cssFloat":"styleFloat"},style:function(a,c,d,e){if(!!a&&a.nodeType!==3&&a.nodeType!==8&&!!a.style){var g,h,i=f.camelCase(c),j=a.style,k=f.cssHooks[i];c=f.cssProps[i]||i;if(d===b){if(k&&"get"in k&&(g=k.get(a,!1,e))!==b)return g;return j[c]}h=typeof d,h==="string"&&(g=bv.exec(d))&&(d=+(g[1]+1)*+g[2]+parseFloat(f.css(a,c)),h="number");if(d==null||h==="number"&&isNaN(d))return;h==="number"&&!f.cssNumber[i]&&(d+="px");if(!k||!("set"in k)||(d=k.set(a,d))!==b)try{j[c]=d}catch(l){}}},css:function(a,c,d){var e,g;c=f.camelCase(c),g=f.cssHooks[c],c=f.cssProps[c]||c,c==="cssFloat"&&(c="float");if(g&&"get"in g&&(e=g.get(a,!0,d))!==b)return e;if(bz)return bz(a,c)},swap:function(a,b,c){var d={};for(var e in b)d[e]=a.style[e],a.style[e]=b[e];c.call(a);for(e in b)a.style[e]=d[e]}}),f.curCSS=f.css,f.each(["height","width"],function(a,b){f.cssHooks[b]={get:function(a,c,d){var e;if(c){if(a.offsetWidth!==0)return bC(a,b,d);f.swap(a,bw,function(){e=bC(a,b,d)});return e}},set:function(a,b){if(!bt.test(b))return b;b=parseFloat(b);if(b>=0)return b+"px"}}}),f.support.opacity||(f.cssHooks.opacity={get:function(a,b){return br.test((b&&a.currentStyle?a.currentStyle.filter:a.style.filter)||"")?parseFloat(RegExp.$1)/100+"":b?"1":""},set:function(a,b){var c=a.style,d=a.currentStyle,e=f.isNumeric(b)?"alpha(opacity="+b*100+")":"",g=d&&d.filter||c.filter||"";c.zoom=1;if(b>=1&&f.trim(g.replace(bq,""))===""){c.removeAttribute("filter");if(d&&!d.filter)return}c.filter=bq.test(g)?g.replace(bq,e):g+" "+e}}),f(function(){f.support.reliableMarginRight||(f.cssHooks.marginRight={get:function(a,b){var c;f.swap(a,{display:"inline-block"},function(){b?c=bz(a,"margin-right","marginRight"):c=a.style.marginRight});return c}})}),c.defaultView&&c.defaultView.getComputedStyle&&(bA=function(a,b){var c,d,e;b=b.replace(bs,"-$1").toLowerCase(),(d=a.ownerDocument.defaultView)&&(e=d.getComputedStyle(a,null))&&(c=e.getPropertyValue(b),c===""&&!f.contains(a.ownerDocument.documentElement,a)&&(c=f.style(a,b)));return c}),c.documentElement.currentStyle&&(bB=function(a,b){var c,d,e,f=a.currentStyle&&a.currentStyle[b],g=a.style;f===null&&g&&(e=g[b])&&(f=e),!bt.test(f)&&bu.test(f)&&(c=g.left,d=a.runtimeStyle&&a.runtimeStyle.left,d&&(a.runtimeStyle.left=a.currentStyle.left),g.left=b==="fontSize"?"1em":f||0,f=g.pixelLeft+"px",g.left=c,d&&(a.runtimeStyle.left=d));return f===""?"auto":f}),bz=bA||bB,f.expr&&f.expr.filters&&(f.expr.filters.hidden=function(a){var b=a.offsetWidth,c=a.offsetHeight;return b===0&&c===0||!f.support.reliableHiddenOffsets&&(a.style&&a.style.display||f.css(a,"display"))==="none"},f.expr.filters.visible=function(a){return!f.expr.filters.hidden(a)});var bD=/%20/g,bE=/\[\]$/,bF=/\r?\n/g,bG=/#.*$/,bH=/^(.*?):[ \t]*([^\r\n]*)\r?$/mg,bI=/^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|range|search|tel|text|time|url|week)$/i,bJ=/^(?:about|app|app\-storage|.+\-extension|file|res|widget):$/,bK=/^(?:GET|HEAD)$/,bL=/^\/\//,bM=/\?/,bN=/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi,bO=/^(?:select|textarea)/i,bP=/\s+/,bQ=/([?&])_=[^&]*/,bR=/^([\w\+\.\-]+:)(?:\/\/([^\/?#:]*)(?::(\d+))?)?/,bS=f.fn.load,bT={},bU={},bV,bW,bX=["*/"]+["*"];try{bV=e.href}catch(bY){bV=c.createElement("a"),bV.href="",bV=bV.href}bW=bR.exec(bV.toLowerCase())||[],f.fn.extend({load:function(a,c,d){if(typeof a!="string"&&bS)return bS.apply(this,arguments);if(!this.length)return this;var e=a.indexOf(" ");if(e>=0){var g=a.slice(e,a.length);a=a.slice(0,e)}var h="GET";c&&(f.isFunction(c)?(d=c,c=b):typeof c=="object"&&(c=f.param(c,f.ajaxSettings.traditional),h="POST"));var i=this;f.ajax({url:a,type:h,dataType:"html",data:c,complete:function(a,b,c){c=a.responseText,a.isResolved()&&(a.done(function(a){c=a}),i.html(g?f("<div>").append(c.replace(bN,"")).find(g):c)),d&&i.each(d,[c,b,a])}});return this},serialize:function(){return f.param(this.serializeArray())},serializeArray:function(){return this.map(function(){return this.elements?f.makeArray(this.elements):this}).filter(function(){return this.name&&!this.disabled&&(this.checked||bO.test(this.nodeName)||bI.test(this.type))}).map(function(a,b){var c=f(this).val();return c==null?null:f.isArray(c)?f.map(c,function(a,c){return{name:b.name,value:a.replace(bF,"\r\n")}}):{name:b.name,value:c.replace(bF,"\r\n")}}).get()}}),f.each("ajaxStart ajaxStop ajaxComplete ajaxError ajaxSuccess ajaxSend".split(" "),function(a,b){f.fn[b]=function(a){return this.on(b,a)}}),f.each(["get","post"],function(a,c){f[c]=function(a,d,e,g){f.isFunction(d)&&(g=g||e,e=d,d=b);return f.ajax({type:c,url:a,data:d,success:e,dataType:g})}}),f.extend({getScript:function(a,c){return f.get(a,b,c,"script")},getJSON:function(a,b,c){return f.get(a,b,c,"json")},ajaxSetup:function(a,b){b?b_(a,f.ajaxSettings):(b=a,a=f.ajaxSettings),b_(a,b);return a},ajaxSettings:{url:bV,isLocal:bJ.test(bW[1]),global:!0,type:"GET",contentType:"application/x-www-form-urlencoded",processData:!0,async:!0,accepts:{xml:"application/xml, text/xml",html:"text/html",text:"text/plain",json:"application/json, text/javascript","*":bX},contents:{xml:/xml/,html:/html/,json:/json/},responseFields:{xml:"responseXML",text:"responseText"},converters:{"* text":a.String,"text html":!0,"text json":f.parseJSON,"text xml":f.parseXML},flatOptions:{context:!0,url:!0}},ajaxPrefilter:bZ(bT),ajaxTransport:bZ(bU),ajax:function(a,c){function w(a,c,l,m){if(s!==2){s=2,q&&clearTimeout(q),p=b,n=m||"",v.readyState=a>0?4:0;var o,r,u,w=c,x=l?cb(d,v,l):b,y,z;if(a>=200&&a<300||a===304){if(d.ifModified){if(y=v.getResponseHeader("Last-Modified"))f.lastModified[k]=y;if(z=v.getResponseHeader("Etag"))f.etag[k]=z}if(a===304)w="notmodified",o=!0;else try{r=cc(d,x),w="success",o=!0}catch(A){w="parsererror",u=A}}else{u=w;if(!w||a)w="error",a<0&&(a=0)}v.status=a,v.statusText=""+(c||w),o?h.resolveWith(e,[r,w,v]):h.rejectWith(e,[v,w,u]),v.statusCode(j),j=b,t&&g.trigger("ajax"+(o?"Success":"Error"),[v,d,o?r:u]),i.fireWith(e,[v,w]),t&&(g.trigger("ajaxComplete",[v,d]),--f.active||f.event.trigger("ajaxStop"))}}typeof a=="object"&&(c=a,a=b),c=c||{};var d=f.ajaxSetup({},c),e=d.context||d,g=e!==d&&(e.nodeType||e instanceof f)?f(e):f.event,h=f.Deferred(),i=f.Callbacks("once memory"),j=d.statusCode||{},k,l={},m={},n,o,p,q,r,s=0,t,u,v={readyState:0,setRequestHeader:function(a,b){if(!s){var c=a.toLowerCase();a=m[c]=m[c]||a,l[a]=b}return this},getAllResponseHeaders:function(){return s===2?n:null},getResponseHeader:function(a){var c;if(s===2){if(!o){o={};while(c=bH.exec(n))o[c[1].toLowerCase()]=c[2]}c=o[a.toLowerCase()]}return c===b?null:c},overrideMimeType:function(a){s||(d.mimeType=a);return this},abort:function(a){a=a||"abort",p&&p.abort(a),w(0,a);return this}};h.promise(v),v.success=v.done,v.error=v.fail,v.complete=i.add,v.statusCode=function(a){if(a){var b;if(s<2)for(b in a)j[b]=[j[b],a[b]];else b=a[v.status],v.then(b,b)}return this},d.url=((a||d.url)+"").replace(bG,"").replace(bL,bW[1]+"//"),d.dataTypes=f.trim(d.dataType||"*").toLowerCase().split(bP),d.crossDomain==null&&(r=bR.exec(d.url.toLowerCase()),d.crossDomain=!(!r||r[1]==bW[1]&&r[2]==bW[2]&&(r[3]||(r[1]==="http:"?80:443))==(bW[3]||(bW[1]==="http:"?80:443)))),d.data&&d.processData&&typeof d.data!="string"&&(d.data=f.param(d.data,d.traditional)),b$(bT,d,c,v);if(s===2)return!1;t=d.global,d.type=d.type.toUpperCase(),d.hasContent=!bK.test(d.type),t&&f.active++===0&&f.event.trigger("ajaxStart");if(!d.hasContent){d.data&&(d.url+=(bM.test(d.url)?"&":"?")+d.data,delete d.data),k=d.url;if(d.cache===!1){var x=f.now(),y=d.url.replace(bQ,"$1_="+x);d.url=y+(y===d.url?(bM.test(d.url)?"&":"?")+"_="+x:"")}}(d.data&&d.hasContent&&d.contentType!==!1||c.contentType)&&v.setRequestHeader("Content-Type",d.contentType),d.ifModified&&(k=k||d.url,f.lastModified[k]&&v.setRequestHeader("If-Modified-Since",f.lastModified[k]),f.etag[k]&&v.setRequestHeader("If-None-Match",f.etag[k])),v.setRequestHeader("Accept",d.dataTypes[0]&&d.accepts[d.dataTypes[0]]?d.accepts[d.dataTypes[0]]+(d.dataTypes[0]!=="*"?", "+bX+"; q=0.01":""):d.accepts["*"]);for(u in d.headers)v.setRequestHeader(u,d.headers[u]);if(d.beforeSend&&(d.beforeSend.call(e,v,d)===!1||s===2)){v.abort();return!1}for(u in{success:1,error:1,complete:1})v[u](d[u]);p=b$(bU,d,c,v);if(!p)w(-1,"No Transport");else{v.readyState=1,t&&g.trigger("ajaxSend",[v,d]),d.async&&d.timeout>0&&(q=setTimeout(function(){v.abort("timeout")},d.timeout));try{s=1,p.send(l,w)}catch(z){if(s<2)w(-1,z);else throw z}}return v},param:function(a,c){var d=[],e=function(a,b){b=f.isFunction(b)?b():b,d[d.length]=encodeURIComponent(a)+"="+encodeURIComponent(b)};c===b&&(c=f.ajaxSettings.traditional);if(f.isArray(a)||a.jquery&&!f.isPlainObject(a))f.each(a,function(){e(this.name,this.value)});else for(var g in a)ca(g,a[g],c,e);return d.join("&").replace(bD,"+")}}),f.extend({active:0,lastModified:{},etag:{}});var cd=f.now(),ce=/(\=)\?(&|$)|\?\?/i;f.ajaxSetup({jsonp:"callback",jsonpCallback:function(){return f.expando+"_"+cd++}}),f.ajaxPrefilter("json jsonp",function(b,c,d){var e=b.contentType==="application/x-www-form-urlencoded"&&typeof b.data=="string";if(b.dataTypes[0]==="jsonp"||b.jsonp!==!1&&(ce.test(b.url)||e&&ce.test(b.data))){var g,h=b.jsonpCallback=f.isFunction(b.jsonpCallback)?b.jsonpCallback():b.jsonpCallback,i=a[h],j=b.url,k=b.data,l="$1"+h+"$2";b.jsonp!==!1&&(j=j.replace(ce,l),b.url===j&&(e&&(k=k.replace(ce,l)),b.data===k&&(j+=(/\?/.test(j)?"&":"?")+b.jsonp+"="+h))),b.url=j,b.data=k,a[h]=function(a){g=[a]},d.always(function(){a[h]=i,g&&f.isFunction(i)&&a[h](g[0])}),b.converters["script json"]=function(){g||f.error(h+" was not called");return g[0]},b.dataTypes[0]="json";return"script"}}),f.ajaxSetup({accepts:{script:"text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},contents:{script:/javascript|ecmascript/},converters:{"text script":function(a){f.globalEval(a);return a}}}),f.ajaxPrefilter("script",function(a){a.cache===b&&(a.cache=!1),a.crossDomain&&(a.type="GET",a.global=!1)}),f.ajaxTransport("script",function(a){if(a.crossDomain){var d,e=c.head||c.getElementsByTagName("head")[0]||c.documentElement;return{send:function(f,g){d=c.createElement("script"),d.async="async",a.scriptCharset&&(d.charset=a.scriptCharset),d.src=a.url,d.onload=d.onreadystatechange=function(a,c){if(c||!d.readyState||/loaded|complete/.test(d.readyState))d.onload=d.onreadystatechange=null,e&&d.parentNode&&e.removeChild(d),d=b,c||g(200,"success")},e.insertBefore(d,e.firstChild)},abort:function(){d&&d.onload(0,1)}}}});var cf=a.ActiveXObject?function(){for(var a in ch)ch[a](0,1)}:!1,cg=0,ch;f.ajaxSettings.xhr=a.ActiveXObject?function(){return!this.isLocal&&ci()||cj()}:ci,function(a){f.extend(f.support,{ajax:!!a,cors:!!a&&"withCredentials"in a})}(f.ajaxSettings.xhr()),f.support.ajax&&f.ajaxTransport(function(c){if(!c.crossDomain||f.support.cors){var d;return{send:function(e,g){var h=c.xhr(),i,j;c.username?h.open(c.type,c.url,c.async,c.username,c.password):h.open(c.type,c.url,c.async);if(c.xhrFields)for(j in c.xhrFields)h[j]=c.xhrFields[j];c.mimeType&&h.overrideMimeType&&h.overrideMimeType(c.mimeType),!c.crossDomain&&!e["X-Requested-With"]&&(e["X-Requested-With"]="XMLHttpRequest");try{for(j in e)h.setRequestHeader(j,e[j])}catch(k){}h.send(c.hasContent&&c.data||null),d=function(a,e){var j,k,l,m,n;try{if(d&&(e||h.readyState===4)){d=b,i&&(h.onreadystatechange=f.noop,cf&&delete ch[i]);if(e)h.readyState!==4&&h.abort();else{j=h.status,l=h.getAllResponseHeaders(),m={},n=h.responseXML,n&&n.documentElement&&(m.xml=n),m.text=h.responseText;try{k=h.statusText}catch(o){k=""}!j&&c.isLocal&&!c.crossDomain?j=m.text?200:404:j===1223&&(j=204)}}}catch(p){e||g(-1,p)}m&&g(j,k,m,l)},!c.async||h.readyState===4?d():(i=++cg,cf&&(ch||(ch={},f(a).unload(cf)),ch[i]=d),h.onreadystatechange=d)},abort:function(){d&&d(0,1)}}}});var ck={},cl,cm,cn=/^(?:toggle|show|hide)$/,co=/^([+\-]=)?([\d+.\-]+)([a-z%]*)$/i,cp,cq=[["height","marginTop","marginBottom","paddingTop","paddingBottom"],["width","marginLeft","marginRight","paddingLeft","paddingRight"],["opacity"]],cr;f.fn.extend({show:function(a,b,c){var d,e;if(a||a===0)return this.animate(cu("show",3),a,b,c);for(var g=0,h=this.length;g<h;g++)d=this[g],d.style&&(e=d.style.display,!f._data(d,"olddisplay")&&e==="none"&&(e=d.style.display=""),e===""&&f.css(d,"display")==="none"&&f._data(d,"olddisplay",cv(d.nodeName)));for(g=0;g<h;g++){d=this[g];if(d.style){e=d.style.display;if(e===""||e==="none")d.style.display=f._data(d,"olddisplay")||""}}return this},hide:function(a,b,c){if(a||a===0)return this.animate(cu("hide",3),a,b,c);var d,e,g=0,h=this.length;for(;g<h;g++)d=this[g],d.style&&(e=f.css(d,"display"),e!=="none"&&!f._data(d,"olddisplay")&&f._data(d,"olddisplay",e));for(g=0;g<h;g++)this[g].style&&(this[g].style.display="none");return this},_toggle:f.fn.toggle,toggle:function(a,b,c){var d=typeof a=="boolean";f.isFunction(a)&&f.isFunction(b)?this._toggle.apply(this,arguments):a==null||d?this.each(function(){var b=d?a:f(this).is(":hidden");f(this)[b?"show":"hide"]()}):this.animate(cu("toggle",3),a,b,c);return this},fadeTo:function(a,b,c,d){return this.filter(":hidden").css("opacity",0).show().end().animate({opacity:b},a,c,d)},animate:function(a,b,c,d){function g(){e.queue===!1&&f._mark(this);var b=f.extend({},e),c=this.nodeType===1,d=c&&f(this).is(":hidden"),g,h,i,j,k,l,m,n,o;b.animatedProperties={};for(i in a){g=f.camelCase(i),i!==g&&(a[g]=a[i],delete a[i]),h=a[g],f.isArray(h)?(b.animatedProperties[g]=h[1],h=a[g]=h[0]):b.animatedProperties[g]=b.specialEasing&&b.specialEasing[g]||b.easing||"swing";if(h==="hide"&&d||h==="show"&&!d)return b.complete.call(this);c&&(g==="height"||g==="width")&&(b.overflow=[this.style.overflow,this.style.overflowX,this.style.overflowY],f.css(this,"display")==="inline"&&f.css(this,"float")==="none"&&(!f.support.inlineBlockNeedsLayout||cv(this.nodeName)==="inline"?this.style.display="inline-block":this.style.zoom=1))}b.overflow!=null&&(this.style.overflow="hidden");for(i in a)j=new f.fx(this,b,i),h=a[i],cn.test(h)?(o=f._data(this,"toggle"+i)||(h==="toggle"?d?"show":"hide":0),o?(f._data(this,"toggle"+i,o==="show"?"hide":"show"),j[o]()):j[h]()):(k=co.exec(h),l=j.cur(),k?(m=parseFloat(k[2]),n=k[3]||(f.cssNumber[i]?"":"px"),n!=="px"&&(f.style(this,i,(m||1)+n),l=(m||1)/j.cur()*l,f.style(this,i,l+n)),k[1]&&(m=(k[1]==="-="?-1:1)*m+l),j.custom(l,m,n)):j.custom(l,h,""));return!0}var e=f.speed(b,c,d);if(f.isEmptyObject(a))return this.each(e.complete,[!1]);a=f.extend({},a);return e.queue===!1?this.each(g):this.queue(e.queue,g)},stop:function(a,c,d){typeof a!="string"&&(d=c,c=a,a=b),c&&a!==!1&&this.queue(a||"fx",[]);return this.each(function(){function h(a,b,c){var e=b[c];f.removeData(a,c,!0),e.stop(d)}var b,c=!1,e=f.timers,g=f._data(this);d||f._unmark(!0,this);if(a==null)for(b in g)g[b]&&g[b].stop&&b.indexOf(".run")===b.length-4&&h(this,g,b);else g[b=a+".run"]&&g[b].stop&&h(this,g,b);for(b=e.length;b--;)e[b].elem===this&&(a==null||e[b].queue===a)&&(d?e[b](!0):e[b].saveState(),c=!0,e.splice(b,1));(!d||!c)&&f.dequeue(this,a)})}}),f.each({slideDown:cu("show",1),slideUp:cu("hide",1),slideToggle:cu("toggle",1),fadeIn:{opacity:"show"},fadeOut:{opacity:"hide"},fadeToggle:{opacity:"toggle"}},function(a,b){f.fn[a]=function(a,c,d){return this.animate(b,a,c,d)}}),f.extend({speed:function(a,b,c){var d=a&&typeof a=="object"?f.extend({},a):{complete:c||!c&&b||f.isFunction(a)&&a,duration:a,easing:c&&b||b&&!f.isFunction(b)&&b};d.duration=f.fx.off?0:typeof d.duration=="number"?d.duration:d.duration in f.fx.speeds?f.fx.speeds[d.duration]:f.fx.speeds._default;if(d.queue==null||d.queue===!0)d.queue="fx";d.old=d.complete,d.complete=function(a){f.isFunction(d.old)&&d.old.call(this),d.queue?f.dequeue(this,d.queue):a!==!1&&f._unmark(this)};return d},easing:{linear:function(a,b,c,d){return c+d*a},swing:function(a,b,c,d){return(-Math.cos(a*Math.PI)/2+.5)*d+c}},timers:[],fx:function(a,b,c){this.options=b,this.elem=a,this.prop=c,b.orig=b.orig||{}}}),f.fx.prototype={update:function(){this.options.step&&this.options.step.call(this.elem,this.now,this),(f.fx.step[this.prop]||f.fx.step._default)(this)},cur:function(){if(this.elem[this.prop]!=null&&(!this.elem.style||this.elem.style[this.prop]==null))return this.elem[this.prop];var a,b=f.css(this.elem,this.prop);return isNaN(a=parseFloat(b))?!b||b==="auto"?0:b:a},custom:function(a,c,d){function h(a){return e.step(a)}var e=this,g=f.fx;this.startTime=cr||cs(),this.end=c,this.now=this.start=a,this.pos=this.state=0,this.unit=d||this.unit||(f.cssNumber[this.prop]?"":"px"),h.queue=this.options.queue,h.elem=this.elem,h.saveState=function(){e.options.hide&&f._data(e.elem,"fxshow"+e.prop)===b&&f._data(e.elem,"fxshow"+e.prop,e.start)},h()&&f.timers.push(h)&&!cp&&(cp=setInterval(g.tick,g.interval))},show:function(){var a=f._data(this.elem,"fxshow"+this.prop);this.options.orig[this.prop]=a||f.style(this.elem,this.prop),this.options.show=!0,a!==b?this.custom(this.cur(),a):this.custom(this.prop==="width"||this.prop==="height"?1:0,this.cur()),f(this.elem).show()},hide:function(){this.options.orig[this.prop]=f._data(this.elem,"fxshow"+this.prop)||f.style(this.elem,this.prop),this.options.hide=!0,this.custom(this.cur(),0)},step:function(a){var b,c,d,e=cr||cs(),g=!0,h=this.elem,i=this.options;if(a||e>=i.duration+this.startTime){this.now=this.end,this.pos=this.state=1,this.update(),i.animatedProperties[this.prop]=!0;for(b in i.animatedProperties)i.animatedProperties[b]!==!0&&(g=!1);if(g){i.overflow!=null&&!f.support.shrinkWrapBlocks&&f.each(["","X","Y"],function(a,b){h.style["overflow"+b]=i.overflow[a]}),i.hide&&f(h).hide();if(i.hide||i.show)for(b in i.animatedProperties)f.style(h,b,i.orig[b]),f.removeData(h,"fxshow"+b,!0),f.removeData(h,"toggle"+b,!0);d=i.complete,d&&(i.complete=!1,d.call(h))}return!1}i.duration==Infinity?this.now=e:(c=e-this.startTime,this.state=c/i.duration,this.pos=f.easing[i.animatedProperties[this.prop]](this.state,c,0,1,i.duration),this.now=this.start+(this.end-this.start)*this.pos),this.update();return!0}},f.extend(f.fx,{tick:function(){var a,b=f.timers,c=0;for(;c<b.length;c++)a=b[c],!a()&&b[c]===a&&b.splice(c--,1);b.length||f.fx.stop()},interval:13,stop:function(){clearInterval(cp),cp=null},speeds:{slow:600,fast:200,_default:400},step:{opacity:function(a){f.style(a.elem,"opacity",a.now)},_default:function(a){a.elem.style&&a.elem.style[a.prop]!=null?a.elem.style[a.prop]=a.now+a.unit:a.elem[a.prop]=a.now}}}),f.each(["width","height"],function(a,b){f.fx.step[b]=function(a){f.style(a.elem,b,Math.max(0,a.now)+a.unit)}}),f.expr&&f.expr.filters&&(f.expr.filters.animated=function(a){return f.grep(f.timers,function(b){return a===b.elem}).length});var cw=/^t(?:able|d|h)$/i,cx=/^(?:body|html)$/i;"getBoundingClientRect"in c.documentElement?f.fn.offset=function(a){var b=this[0],c;if(a)return this.each(function(b){f.offset.setOffset(this,a,b)});if(!b||!b.ownerDocument)return null;if(b===b.ownerDocument.body)return f.offset.bodyOffset(b);try{c=b.getBoundingClientRect()}catch(d){}var e=b.ownerDocument,g=e.documentElement;if(!c||!f.contains(g,b))return c?{top:c.top,left:c.left}:{top:0,left:0};var h=e.body,i=cy(e),j=g.clientTop||h.clientTop||0,k=g.clientLeft||h.clientLeft||0,l=i.pageYOffset||f.support.boxModel&&g.scrollTop||h.scrollTop,m=i.pageXOffset||f.support.boxModel&&g.scrollLeft||h.scrollLeft,n=c.top+l-j,o=c.left+m-k;return{top:n,left:o}}:f.fn.offset=function(a){var b=this[0];if(a)return this.each(function(b){f.offset.setOffset(this,a,b)});if(!b||!b.ownerDocument)return null;if(b===b.ownerDocument.body)return f.offset.bodyOffset(b);var c,d=b.offsetParent,e=b,g=b.ownerDocument,h=g.documentElement,i=g.body,j=g.defaultView,k=j?j.getComputedStyle(b,null):b.currentStyle,l=b.offsetTop,m=b.offsetLeft;while((b=b.parentNode)&&b!==i&&b!==h){if(f.support.fixedPosition&&k.position==="fixed")break;c=j?j.getComputedStyle(b,null):b.currentStyle,l-=b.scrollTop,m-=b.scrollLeft,b===d&&(l+=b.offsetTop,m+=b.offsetLeft,f.support.doesNotAddBorder&&(!f.support.doesAddBorderForTableAndCells||!cw.test(b.nodeName))&&(l+=parseFloat(c.borderTopWidth)||0,m+=parseFloat(c.borderLeftWidth)||0),e=d,d=b.offsetParent),f.support.subtractsBorderForOverflowNotVisible&&c.overflow!=="visible"&&(l+=parseFloat(c.borderTopWidth)||0,m+=parseFloat(c.borderLeftWidth)||0),k=c}if(k.position==="relative"||k.position==="static")l+=i.offsetTop,m+=i.offsetLeft;f.support.fixedPosition&&k.position==="fixed"&&(l+=Math.max(h.scrollTop,i.scrollTop),m+=Math.max(h.scrollLeft,i.scrollLeft));return{top:l,left:m}},f.offset={bodyOffset:function(a){var b=a.offsetTop,c=a.offsetLeft;f.support.doesNotIncludeMarginInBodyOffset&&(b+=parseFloat(f.css(a,"marginTop"))||0,c+=parseFloat(f.css(a,"marginLeft"))||0);return{top:b,left:c}},setOffset:function(a,b,c){var d=f.css(a,"position");d==="static"&&(a.style.position="relative");var e=f(a),g=e.offset(),h=f.css(a,"top"),i=f.css(a,"left"),j=(d==="absolute"||d==="fixed")&&f.inArray("auto",[h,i])>-1,k={},l={},m,n;j?(l=e.position(),m=l.top,n=l.left):(m=parseFloat(h)||0,n=parseFloat(i)||0),f.isFunction(b)&&(b=b.call(a,c,g)),b.top!=null&&(k.top=b.top-g.top+m),b.left!=null&&(k.left=b.left-g.left+n),"using"in b?b.using.call(a,k):e.css(k)}},f.fn.extend({position:function(){if(!this[0])return null;var a=this[0],b=this.offsetParent(),c=this.offset(),d=cx.test(b[0].nodeName)?{top:0,left:0}:b.offset();c.top-=parseFloat(f.css(a,"marginTop"))||0,c.left-=parseFloat(f.css(a,"marginLeft"))||0,d.top+=parseFloat(f.css(b[0],"borderTopWidth"))||0,d.left+=parseFloat(f.css(b[0],"borderLeftWidth"))||0;return{top:c.top-d.top,left:c.left-d.left}},offsetParent:function(){return this.map(function(){var a=this.offsetParent||c.body;while(a&&!cx.test(a.nodeName)&&f.css(a,"position")==="static")a=a.offsetParent;return a})}}),f.each(["Left","Top"],function(a,c){var d="scroll"+c;f.fn[d]=function(c){var e,g;if(c===b){e=this[0];if(!e)return null;g=cy(e);return g?"pageXOffset"in g?g[a?"pageYOffset":"pageXOffset"]:f.support.boxModel&&g.document.documentElement[d]||g.document.body[d]:e[d]}return this.each(function(){g=cy(this),g?g.scrollTo(a?f(g).scrollLeft():c,a?c:f(g).scrollTop()):this[d]=c})}}),f.each(["Height","Width"],function(a,c){var d=c.toLowerCase();f.fn["inner"+c]=function(){var a=this[0];return a?a.style?parseFloat(f.css(a,d,"padding")):this[d]():null},f.fn["outer"+c]=function(a){var b=this[0];return b?b.style?parseFloat(f.css(b,d,a?"margin":"border")):this[d]():null},f.fn[d]=function(a){var e=this[0];if(!e)return a==null?null:this;if(f.isFunction(a))return this.each(function(b){var c=f(this);c[d](a.call(this,b,c[d]()))});if(f.isWindow(e)){var g=e.document.documentElement["client"+c],h=e.document.body;return e.document.compatMode==="CSS1Compat"&&g||h&&h["client"+c]||g}if(e.nodeType===9)return Math.max(e.documentElement["client"+c],e.body["scroll"+c],e.documentElement["scroll"+c],e.body["offset"+c],e.documentElement["offset"+c]);if(a===b){var i=f.css(e,d),j=parseFloat(i);return f.isNumeric(j)?j:i}return this.css(d,typeof a=="string"?a:a+"px")}}),a.jQuery=a.$=f,typeof define=="function"&&define.amd&&define.amd.jQuery&&define("jquery",[],function(){return f})})(window);
/*
 * jQuery Form Plugin
 * version: 2.45 (09-AUG-2010)
 * @requires jQuery v1.3.2 or later
 *
 * Examples and documentation at: http://malsup.com/jquery/form/
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
(function(B){B.fn.ajaxSubmit=function(P){if(!this.length){A("ajaxSubmit: skipping submit process - no element selected");
return this
}if(typeof P=="function"){P={success:P}
}var D=B.trim(this.attr("action"));
if(D){D=(D.match(/^([^#]+)/)||[])[1]
}D=D||window.location.href||"";
P=B.extend(true,{url:D,type:this.attr("method")||"GET",iframeSrc:/^https/i.test(window.location.href||"")?"javascript:false":"about:blank"},P);
var Q={};
this.trigger("form-pre-serialize",[this,P,Q]);
if(Q.veto){A("ajaxSubmit: submit vetoed via form-pre-serialize trigger");
return this
}if(P.beforeSerialize&&P.beforeSerialize(this,P)===false){A("ajaxSubmit: submit aborted via beforeSerialize callback");
return this
}var F,M,K=this.formToArray(P.semantic);
if(P.data){P.extraData=P.data;
for(F in P.data){if(P.data[F] instanceof Array){for(var H in P.data[F]){K.push({name:F,value:P.data[F][H]})
}}else{M=P.data[F];
M=B.isFunction(M)?M():M;
K.push({name:F,value:M})
}}}if(P.beforeSubmit&&P.beforeSubmit(K,this,P)===false){A("ajaxSubmit: submit aborted via beforeSubmit callback");
return this
}this.trigger("form-submit-validate",[K,this,P,Q]);
if(Q.veto){A("ajaxSubmit: submit vetoed via form-submit-validate trigger");
return this
}var C=B.param(K);
if(P.type.toUpperCase()=="GET"){P.url+=(P.url.indexOf("?")>=0?"&":"?")+C;
P.data=null
}else{P.data=C
}var O=this,J=[];
if(P.resetForm){J.push(function(){O.resetForm()
})
}if(P.clearForm){J.push(function(){O.clearForm()
})
}if(!P.dataType&&P.target){var N=P.success||function(){};
J.push(function(S){var R=P.replaceTarget?"replaceWith":"html";
B(P.target)[R](S).each(N,arguments)
})
}else{if(P.success){J.push(P.success)
}}P.success=function(V,S,W){var U=P.context||P;
for(var T=0,R=J.length;
T<R;
T++){J[T].apply(U,[V,S,W||O,O])
}};
var G=B("input:file",this).length>0;
var E="multipart/form-data";
var I=(O.attr("enctype")==E||O.attr("encoding")==E);
if(P.iframe!==false&&(G||P.iframe||I)){if(P.closeKeepAlive){B.get(P.closeKeepAlive,L)
}else{L()
}}else{B.ajax(P)
}this.trigger("form-submit-notify",[this,P]);
return this;
function L(){var U=O[0];
if(B(":input[name=submit],:input[id=submit]",U).length){alert('Error: Form elements must not have name or id of "submit".');
return 
}var i=B.extend(true,{},B.ajaxSettings,P);
i.context=i.context||i;
var T="jqFormIO"+(new Date().getTime());
var c=B('<iframe id="'+T+'" name="'+T+'" src="'+i.iframeSrc+'" onload="var f = jQuery(this).data(\'form-plugin-onload\'); if (f) f();" />');
var e=c[0];
c.css({position:"absolute",top:"-1000px",left:"-1000px"});
var h={aborted:0,responseText:null,responseXML:null,status:0,statusText:"n/a",getAllResponseHeaders:function(){},getResponseHeader:function(){},setRequestHeader:function(){},abort:function(){this.aborted=1;
c.attr("src",i.iframeSrc)
}};
var d=i.global;
if(d&&!B.active++){B.event.trigger("ajaxStart")
}if(d){B.event.trigger("ajaxSend",[h,i])
}if(i.beforeSend&&i.beforeSend.call(i.context,h,i)===false){if(i.global){B.active--
}return 
}if(h.aborted){return 
}var S=false;
var Y=0;
var R=U.clk;
if(R){var W=R.name;
if(W&&!R.disabled){i.extraData=i.extraData||{};
i.extraData[W]=R.value;
if(R.type=="image"){i.extraData[W+".x"]=U.clk_x;
i.extraData[W+".y"]=U.clk_y
}}}function V(){var k=O.attr("target"),g=O.attr("action");
U.setAttribute("target",T);
if(U.getAttribute("method")!="POST"){U.setAttribute("method","POST")
}if(U.getAttribute("action")!=i.url){U.setAttribute("action",i.url)
}if(!i.skipEncodingOverride){O.attr({encoding:"multipart/form-data",enctype:"multipart/form-data"})
}if(i.timeout){setTimeout(function(){Y=true;
Z()
},i.timeout)
}var j=[];
try{if(i.extraData){for(var l in i.extraData){j.push(B('<input type="hidden" name="'+l+'" value="'+i.extraData[l]+'" />').appendTo(U)[0])
}}c.appendTo("body");
c.data("form-plugin-onload",Z);
U.submit()
}finally{U.setAttribute("action",g);
if(k){U.setAttribute("target",k)
}else{O.removeAttr("target")
}B(j).remove()
}}if(i.forceSync){V()
}else{setTimeout(V,10)
}var a,f,X=100;
function Z(){if(S){return 
}c.removeData("form-plugin-onload");
var j=true;
try{if(Y){throw"timeout"
}f=e.contentWindow?e.contentWindow.document:e.contentDocument?e.contentDocument:e.document;
var n=i.dataType=="xml"||f.XMLDocument||B.isXMLDoc(f);
A("isXml="+n);
if(!n&&(f.body==null||f.body.innerHTML=="")){if(--X){A("requeing onLoad callback, DOM not available");
setTimeout(Z,250);
return 
}A("Could not access iframe DOM after 100 tries.");
throw"DOMException: not available"
}A("response detected");
S=true;
h.responseText=f.documentElement?f.documentElement.innerHTML:null;
h.responseXML=f.XMLDocument?f.XMLDocument:f;
h.getResponseHeader=function(p){var o={"content-type":i.dataType};
return o[p]
};
var m=/(json|script)/.test(i.dataType);
if(m||i.textarea){var g=f.getElementsByTagName("textarea")[0];
if(g){h.responseText=g.value
}else{if(m){var l=f.getElementsByTagName("pre")[0];
if(l){h.responseText=l.innerHTML
}}}}else{if(i.dataType=="xml"&&!h.responseXML&&h.responseText!=null){h.responseXML=b(h.responseText)
}}a=B.httpData(h,i.dataType)
}catch(k){A("error caught:",k);
j=false;
h.error=k;
B.handleError(i,h,"error",k)
}if(j){i.success.call(i.context,a,"success");
if(d){B.event.trigger("ajaxSuccess",[h,i])
}}if(d){B.event.trigger("ajaxComplete",[h,i])
}if(d&&!--B.active){B.event.trigger("ajaxStop")
}if(i.complete){i.complete.call(i.context,h,j?"success":"error")
}setTimeout(function(){c.removeData("form-plugin-onload");
c.remove();
h.responseXML=null
},100)
}function b(g,j){if(window.ActiveXObject){j=new ActiveXObject("Microsoft.XMLDOM");
j.async="false";
j.loadXML(g)
}else{j=(new DOMParser()).parseFromString(g,"text/xml")
}return(j&&j.documentElement&&j.documentElement.tagName!="parsererror")?j:null
}}};
B.fn.ajaxForm=function(C){if(this.length===0){var D={s:this.selector,c:this.context};
if(!B.isReady&&D.s){A("DOM not ready, queuing ajaxForm");
B(function(){B(D.s,D.c).ajaxForm(C)
});
return this
}A("terminating; zero elements found by selector"+(B.isReady?"":" (DOM not ready)"));
return this
}return this.ajaxFormUnbind().bind("submit.form-plugin",function(E){if(!E.isDefaultPrevented()){E.preventDefault();
B(this).ajaxSubmit(C)
}}).bind("click.form-plugin",function(I){var H=I.target;
var F=B(H);
if(!(F.is(":submit,input:image"))){var E=F.closest(":submit");
if(E.length==0){return 
}H=E[0]
}var G=this;
G.clk=H;
if(H.type=="image"){if(I.offsetX!=undefined){G.clk_x=I.offsetX;
G.clk_y=I.offsetY
}else{if(typeof B.fn.offset=="function"){var J=F.offset();
G.clk_x=I.pageX-J.left;
G.clk_y=I.pageY-J.top
}else{G.clk_x=I.pageX-H.offsetLeft;
G.clk_y=I.pageY-H.offsetTop
}}}setTimeout(function(){G.clk=G.clk_x=G.clk_y=null
},100)
})
};
B.fn.ajaxFormUnbind=function(){return this.unbind("submit.form-plugin click.form-plugin")
};
B.fn.formToArray=function(L){var K=[];
if(this.length===0){return K
}var C=this[0];
var F=L?C.getElementsByTagName("*"):C.elements;
if(!F){return K
}var H,G,E,M,D;
for(H=0,max=F.length;
H<max;
H++){D=F[H];
E=D.name;
if(!E){continue
}if(L&&C.clk&&D.type=="image"){if(!D.disabled&&C.clk==D){K.push({name:E,value:B(D).val()});
K.push({name:E+".x",value:C.clk_x},{name:E+".y",value:C.clk_y})
}continue
}M=B.fieldValue(D,true);
if(M&&M.constructor==Array){for(G=0,jmax=M.length;
G<jmax;
G++){K.push({name:E,value:M[G]})
}}else{if(M!==null&&typeof M!="undefined"){K.push({name:E,value:M})
}}}if(!L&&C.clk){var I=B(C.clk),J=I[0];
E=J.name;
if(E&&!J.disabled&&J.type=="image"){K.push({name:E,value:I.val()});
K.push({name:E+".x",value:C.clk_x},{name:E+".y",value:C.clk_y})
}}return K
};
B.fn.formSerialize=function(C){return B.param(this.formToArray(C))
};
B.fn.fieldSerialize=function(D){var C=[];
this.each(function(){var H=this.name;
if(!H){return 
}var F=B.fieldValue(this,D);
if(F&&F.constructor==Array){for(var G=0,E=F.length;
G<E;
G++){C.push({name:H,value:F[G]})
}}else{if(F!==null&&typeof F!="undefined"){C.push({name:this.name,value:F})
}}});
return B.param(C)
};
B.fn.fieldValue=function(H){for(var G=[],E=0,C=this.length;
E<C;
E++){var F=this[E];
var D=B.fieldValue(F,H);
if(D===null||typeof D=="undefined"||(D.constructor==Array&&!D.length)){continue
}D.constructor==Array?B.merge(G,D):G.push(D)
}return G
};
B.fieldValue=function(C,I){var E=C.name,N=C.type,O=C.tagName.toLowerCase();
if(I===undefined){I=true
}if(I&&(!E||C.disabled||N=="reset"||N=="button"||(N=="checkbox"||N=="radio")&&!C.checked||(N=="submit"||N=="image")&&C.form&&C.form.clk!=C||O=="select"&&C.selectedIndex==-1)){return null
}if(O=="select"){var J=C.selectedIndex;
if(J<0){return null
}var L=[],D=C.options;
var G=(N=="select-one");
var K=(G?J+1:D.length);
for(var F=(G?J:0);
F<K;
F++){var H=D[F];
if(H.selected){var M=H.value;
if(!M){M=(H.attributes&&H.attributes.value&&!(H.attributes.value.specified))?H.text:H.value
}if(G){return M
}L.push(M)
}}return L
}return B(C).val()
};
B.fn.clearForm=function(){return this.each(function(){B("input,select,textarea",this).clearFields()
})
};
B.fn.clearFields=B.fn.clearInputs=function(){return this.each(function(){var D=this.type,C=this.tagName.toLowerCase();
if(D=="text"||D=="password"||C=="textarea"){this.value=""
}else{if(D=="checkbox"||D=="radio"){this.checked=false
}else{if(C=="select"){this.selectedIndex=-1
}}}})
};
B.fn.resetForm=function(){return this.each(function(){if(typeof this.reset=="function"||(typeof this.reset=="object"&&!this.reset.nodeType)){this.reset()
}})
};
B.fn.enable=function(C){if(C===undefined){C=true
}return this.each(function(){this.disabled=!C
})
};
B.fn.selected=function(C){if(C===undefined){C=true
}return this.each(function(){var D=this.type;
if(D=="checkbox"||D=="radio"){this.checked=C
}else{if(this.tagName.toLowerCase()=="option"){var E=B(this).parent("select");
if(C&&E[0]&&E[0].type=="select-one"){E.find("option").selected(false)
}this.selected=C
}}})
};
function A(){if(B.fn.ajaxSubmit.debug){var C="[jquery.form] "+Array.prototype.join.call(arguments,"");
if(window.console&&window.console.log){window.console.log(C)
}else{if(window.opera&&window.opera.postError){window.opera.postError(C)
}}}}})(jQuery);
(function(A){A.fn.bxSlider=function(B){var C={alignment:"horizontal",controls:true,speed:500,pager:true,margin:0,next_text:"next",next_image:"",prev_text:"prev",prev_image:"",auto:false,pause:3500,auto_direction:"next",auto_hover:true,auto_controls:false,ticker:false,ticker_controls:false,ticker_direction:"next",ticker_hover:true,stop_text:"stop",start_text:"start",wrapper_class:"bxslider_wrap"};
var D=A.extend(C,B);
return this.each(function(){var K=A(this);
var V=K.children();
var T=K.children().length;
var L=K.children(":first").clone();
var M=K.children(":last").clone();
var N=0,U=0,Z=0,J=0,X=0;
var I=false,H=true,Y=true;
var R=1;
var S="swing",E="",P="";
var W={};
K.append(L).prepend(M);
K.wrap('<div class="bxslider_container"></div>');
K.parent().wrap('<div class="'+D.wrapper_class+'"></div>');
if(D.alignment=="horizontal"){K.children().css({"float":"left",listStyle:"none",marginRight:D.margin});
N=L.outerWidth(true);
K.css({width:"99999px",position:"relative",left:-N});
K.parent().css({position:"relative",overflow:"hidden",width:N-D.margin})
}else{if(D.alignment=="vertical"){V.each(function(){if(A(this).height()>U){U=A(this).height()
}});
N=L.outerWidth();
K.children().css({height:U,listStyle:"none",marginBottom:D.margin});
K.css({height:"99999px",width:N,position:"relative",top:-(U+D.margin)});
K.parent().css({position:"relative",overflow:"hidden",height:U})
}}if(D.pager&&!D.ticker){K.parent().after('<div class="bx_pager"></div>');
var F;
V.each(function(a){F=A('<a href="#">'+(a+1)+"</a>");
K.parent().siblings(".bx_pager").append(F);
F.click(function(){I=false;
Y=false;
K.stop();
O(a+1);
R=a+1;
if(D.auto){clearInterval(P);
K.parent().siblings(".auto_controls").find("a").html(D.start_text);
H=false
}else{if(D.ticker){K.parent().siblings(".ticker_controls").find("a").html(D.start_text);
H=false
}}return false
})
});
G(1)
}if(D.controls&&!D.ticker){if(D.next_image!=""||D.prev_image!=""){K.parent().after('<a class="prev" href="#"><img src="'+D.prev_image+'" /></a><a class="next" href="#"><img src="'+D.next_image+'" /></a>')
}else{K.parent().after('<a class="prev" href="#">'+D.prev_text+'</a><a class="next" href="#">'+D.next_text+"</a>")
}K.parent().siblings(".next").click(function(){if(!I){O(++R)
}if(D.auto){clearInterval(P);
K.parent().siblings(".auto_controls").find("a").html(D.start_text);
H=false
}return false
});
K.parent().siblings(".prev").click(function(){if(!I){O(--R)
}if(D.auto){clearInterval(P);
K.parent().siblings(".auto_controls").find("a").html(D.start_text);
H=false
}return false
})
}if(D.auto&&!D.ticker){P=setInterval(function(){if(D.auto_direction=="next"){O(++R)
}else{O(--R)
}},D.pause);
if(D.auto_hover){K.hover(function(){clearInterval(P)
},function(){if(H){P=setInterval(function(){if(D.auto_direction=="next"){O(++R)
}else{O(--R)
}},D.pause)
}})
}if(D.auto_controls){K.parent().after('<div class="auto_controls"><a class="auto_link" href="#">'+D.stop_text+"</a></div>");
K.parent().siblings(".auto_controls").find("a").click(function(){if(H){clearInterval(P);
A(this).html(D.start_text);
H=false
}else{P=setInterval(function(){if(D.auto_direction=="next"){O(++R)
}else{O(--R)
}},D.pause);
A(this).html(D.stop_text);
H=true
}return false
})
}}if(D.ticker){var Y=true;
Q();
K.hover(function(){K.stop()
},function(){if(Y){Q()
}});
if(D.ticker_controls){K.parent().after('<div class="ticker_controls"><a class="ticker_link" href="#">'+D.stop_text+"</a></div>");
K.parent().siblings(".ticker_controls").find("a").click(function(){if(Y){K.stop();
A(this).html(D.start_text);
Y=false
}else{I=false;
A(this).html(D.stop_text);
Q();
Y=true
}return false
})
}}function Q(){if(D.ticker_direction=="next"&&D.alignment=="horizontal"){K.animate({left:"-=5px"},D.speed/5,"linear",function(){if(parseInt(K.css("left"))<=-((T+1)*N)){K.css("left",-N)
}Q()
})
}else{if(D.ticker_direction=="prev"&&D.alignment=="horizontal"){K.animate({left:"+=5px"},D.speed/5,"linear",function(){if(parseInt(K.css("left"))>=-(N)){K.css("left",-((T+1)*N))
}Q()
})
}else{if(D.ticker_direction=="next"&&D.alignment=="vertical"){K.animate({top:"-=5px"},D.speed/5,"linear",function(){if(parseInt(K.css("top"))<=-((T+1)*(U+D.margin))){K.css("top",-(U+D.margin))
}Q()
})
}else{if(D.ticker_direction=="prev"&&D.alignment=="vertical"){K.animate({top:"+=4px"},D.speed/5,"linear",function(){if(parseInt(K.css("top"))>-(U+D.margin)){K.css("top",-((T+1)*(U+D.margin-1)))
}Q()
})
}}}}}function O(a){if(D.ticker){S="linear"
}if(!I){if(D.alignment=="horizontal"){X=N;
E="left"
}else{if(D.alignment=="vertical"){X=U+D.margin;
E="top"
}}J=a*X;
W[E]=-J;
I=true;
K.animate(W,D.speed,S,function(){I=false;
if(R>T){K.css(E,-X);
R=1
}else{if(R<1){K.css(E,-(X*T));
R=T
}}G(R)
})
}}function G(a){if(D.pager){A(".bx_pager a").removeClass("active").eq(a-1).addClass("active")
}}})
}
})(jQuery);
(function(A){A.fn.lightbox=function(H){var Q=A.extend({},A.fn.lightbox.defaults,H);
A(window).resize(T);
return A(this).live("click",function(){E();
P(this);
return false
});
function E(){A("#overlay, #lightbox").remove();
Q.inprogress=false;
if(Q.jsonData&&Q.jsonData.length>0){var X=Q.jsonDataParser?Q.jsonDataParser:A.fn.lightbox.parseJsonData;
Q.imageArray=[];
Q.imageArray=X(Q.jsonData)
}var U='<div id="outerImageContainer"><div id="imageContainer"><iframe id="lightboxIframe"></iframe><img id="lightboxImage" /><div id="hoverNav"><a href="javascript://" title="'+Q.strings.prevLinkTitle+'" id="prevLink"></a><a href="javascript://" id="nextLink" title="'+Q.strings.nextLinkTitle+'"></a></div><div id="loading"><a href="javascript://" id="loadingLink"><img src="'+Q.fileLoadingImage+'"></a></div></div></div>';
var W='<div id="imageDataContainer" class="clearfix"><div id="imageData"><div id="imageDetails"><span id="caption"></span><span id="numberDisplay"></span></div><div id="bottomNav">';
if(Q.displayHelp){W+='<span id="helpDisplay">'+Q.strings.help+"</span>"
}W+='<a href="javascript://" id="bottomNavClose" title="'+Q.strings.closeTitle+'"><img src="'+Q.fileBottomNavCloseImage+'"></a></div></div></div>';
var V;
if(Q.navbarOnTop){V='<div id="overlay"></div><div id="lightbox">'+W+U+"</div>";
A("body").append(V);
A("#imageDataContainer").addClass("ontop")
}else{V='<div id="overlay"></div><div id="lightbox">'+U+W+"</div>";
A("body").append(V)
}A("#overlay, #lightbox").click(function(){J()
}).hide();
A("#loadingLink, #bottomNavClose").click(function(){J();
return false
});
A("#outerImageContainer").width(Q.widthCurrent).height(Q.heightCurrent);
A("#imageDataContainer").width(Q.widthCurrent);
if(!Q.imageClickClose){A("#lightboxImage").click(function(){return false
});
A("#hoverNav").click(function(){return false
})
}return true
}function S(){var U=new Array(A(document).width(),A(document).height(),A(window).width(),A(window).height());
return U
}function G(){var W,U;
if(self.pageYOffset){U=self.pageYOffset;
W=self.pageXOffset
}else{if(document.documentElement&&document.documentElement.scrollTop){U=document.documentElement.scrollTop;
W=document.documentElement.scrollLeft
}else{if(document.body){U=document.body.scrollTop;
W=document.body.scrollLeft
}}}var V=new Array(W,U);
return V
}function L(W){var V=new Date();
var U=null;
do{U=new Date()
}while(U-V<W)
}function P(V){A("select, embed, object").hide();
T();
A("#overlay").hide().css({opacity:Q.overlayOpacity}).fadeIn();
imageNum=0;
if(!Q.jsonData){Q.imageArray=[];
if((!V.rel||(V.rel==""))&&!Q.allSet){Q.imageArray.push(new Array(V.href,Q.displayTitle?V.title:""))
}else{A("a").each(function(){if(this.href&&(this.rel==V.rel)){Q.imageArray.push(new Array(this.href,Q.displayTitle?this.title:""))
}})
}}if(Q.imageArray.length>1){for(i=0;
i<Q.imageArray.length;
i++){for(j=Q.imageArray.length-1;
j>i;
j--){if(Q.imageArray[i][0]==Q.imageArray[j][0]){Q.imageArray.splice(j,1)
}}}while(Q.imageArray[imageNum][0]!=V.href){imageNum++
}}var U=G();
var X=U[1]+(A(window).height()/10);
var W=U[0];
A("#lightbox").css({top:X+"px",left:W+"px"}).show();
if(!Q.slideNavBar){A("#imageData").hide()
}R(imageNum)
}function R(U){if(Q.inprogress==false){Q.inprogress=true;
Q.activeImage=U;
A("#loading").show();
A("#lightboxImage, #hoverNav, #prevLink, #nextLink").hide();
if(Q.slideNavBar){A("#imageDataContainer").hide();
A("#imageData").hide()
}I()
}}function I(){imgPreloader=new Image();
imgPreloader.onload=function(){var Z=imgPreloader.width;
var U=imgPreloader.height;
if(Q.scaleImages){Z=parseInt(Q.xScale*Z);
U=parseInt(Q.yScale*U)
}if(Q.fitToScreen){var W=S();
var Y;
var V=W[2]-2*Q.borderSize;
var a=W[3]-200;
var X=V/a;
var b=imgPreloader.width/imgPreloader.height;
if((imgPreloader.height>a)||(imgPreloader.width>V)){if(X>b){Z=parseInt((a/imgPreloader.height)*imgPreloader.width);
U=a
}else{U=parseInt((V/imgPreloader.width)*imgPreloader.height);
Z=V
}}}A("#lightboxImage").attr("src",Q.imageArray[Q.activeImage][0]).width(Z).height(U);
K(Z,U)
};
imgPreloader.src=Q.imageArray[Q.activeImage][0]
}function J(){M();
A("#lightbox").hide();
A("#overlay").fadeOut();
A("select, object, embed").show()
}function F(){if(Q.loopImages&&Q.imageArray.length>1){preloadNextImage=new Image();
preloadNextImage.src=Q.imageArray[(Q.activeImage==(Q.imageArray.length-1))?0:Q.activeImage+1][0];
preloadPrevImage=new Image();
preloadPrevImage.src=Q.imageArray[(Q.activeImage==0)?(Q.imageArray.length-1):Q.activeImage-1][0]
}else{if((Q.imageArray.length-1)>Q.activeImage){preloadNextImage=new Image();
preloadNextImage.src=Q.imageArray[Q.activeImage+1][0]
}if(Q.activeImage>0){preloadPrevImage=new Image();
preloadPrevImage.src=Q.imageArray[Q.activeImage-1][0]
}}}function K(X,V){Q.widthCurrent=A("#outerImageContainer").outerWidth();
Q.heightCurrent=A("#outerImageContainer").outerHeight();
var U=Math.max(350,X+(Q.borderSize*2));
var W=(V+(Q.borderSize*2));
wDiff=Q.widthCurrent-U;
hDiff=Q.heightCurrent-W;
A("#imageDataContainer").animate({width:U},Q.resizeSpeed,"linear");
A("#outerImageContainer").animate({width:U},Q.resizeSpeed,"linear",function(){A("#outerImageContainer").animate({height:W},Q.resizeSpeed,"linear",function(){D()
})
});
if((hDiff==0)&&(wDiff==0)){if(jQuery.browser.msie){L(250)
}else{L(100)
}}A("#prevLink").height(V);
A("#nextLink").height(V)
}function D(){A("#loading").hide();
A("#lightboxImage").fadeIn("fast");
C();
F();
Q.inprogress=false
}function C(){A("#numberDisplay").html("");
if(Q.imageArray[Q.activeImage][1]){A("#caption").html(Q.imageArray[Q.activeImage][1]).show()
}if(Q.imageArray.length>1){var U;
U=Q.strings.image+(Q.activeImage+1)+Q.strings.of+Q.imageArray.length;
if(Q.displayDownloadLink){U+="<a href='"+Q.imageArray[Q.activeImage][0]+"'>"+Q.strings.download+"</a>"
}if(!Q.disableNavbarLinks){if((Q.activeImage)>0||Q.loopImages){U='<a title="'+Q.strings.prevLinkTitle+'" href="#" id="prevLinkText">'+Q.strings.prevLinkText+"</a>"+U
}if(((Q.activeImage+1)<Q.imageArray.length)||Q.loopImages){U+='<a title="'+Q.strings.nextLinkTitle+'" href="#" id="nextLinkText">'+Q.strings.nextLinkText+"</a>"
}}A("#numberDisplay").html(U).show()
}if(Q.slideNavBar){A("#imageData").slideDown(Q.navBarSlideSpeed)
}else{A("#imageData").show()
}T();
N()
}function T(){A("#overlay").css({width:A(document).width(),height:A(document).height()})
}alert(imageArray.length);
function N(){if(Q.imageArray.length>1){A("#hoverNav").show();
if(Q.loopImages){A("#prevLink,#prevLinkText").show().click(function(){R((Q.activeImage==0)?(Q.imageArray.length-1):Q.activeImage-1);
return false
});
A("#nextLink,#nextLinkText").show().click(function(){R((Q.activeImage==(Q.imageArray.length-1))?0:Q.activeImage+1);
return false
})
}else{if(Q.activeImage!=0){A("#prevLink,#prevLinkText").show().click(function(){R(Q.activeImage-1);
return false
})
}if(Q.activeImage!=(Q.imageArray.length-1)){A("#nextLink,#nextLinkText").show().click(function(){R(Q.activeImage+1);
return false
})
}}B()
}}function O(X){var Y=X.data.opts;
var U=X.keyCode;
var V=27;
var W=String.fromCharCode(U).toLowerCase();
if((W=="x")||(W=="o")||(W=="c")||(U==V)){J()
}else{if((W=="p")||(U==37)){if(Y.loopImages){M();
R((Y.activeImage==0)?(Y.imageArray.length-1):Y.activeImage-1)
}else{if(Y.activeImage!=0){M();
R(Y.activeImage-1)
}}}else{if((W=="n")||(U==39)){if(Q.loopImages){M();
R((Y.activeImage==(Y.imageArray.length-1))?0:Y.activeImage+1)
}else{if(Y.activeImage!=(Y.imageArray.length-1)){M();
R(Y.activeImage+1)
}}}}}}function B(){A(document).bind("keydown",{opts:Q},O)
}function M(){A(document).unbind("keydown")
}};
A.fn.lightbox.parseJsonData=function(C){var B=[];
A.each(C,function(){B.push(new Array(this.url,this.title))
});
return B
};
A.fn.lightbox.defaults={allSet:false,fileLoadingImage:"/img/loading.gif",fileBottomNavCloseImage:"/img/closelabel.gif",overlayOpacity:0.6,borderSize:10,imageArray:new Array,activeImage:null,inprogress:false,resizeSpeed:350,widthCurrent:250,heightCurrent:250,scaleImages:false,xScale:1,yScale:1,displayTitle:true,navbarOnTop:false,displayDownloadLink:false,slideNavBar:false,navBarSlideSpeed:350,displayHelp:false,strings:{help:" \u2190 / P - previous image\u00a0\u00a0\u00a0\u00a0\u2192 / N - next image\u00a0\u00a0\u00a0\u00a0ESC / X - close image gallery",prevLinkTitle:"previous image",nextLinkTitle:"next image",prevLinkText:"&laquo; Previous",nextLinkText:"Next &raquo;",closeTitle:"close image gallery",image:"Image ",of:" of ",download:"Download"},fitToScreen:false,disableNavbarLinks:false,loopImages:false,imageClickClose:true,jsonData:null,jsonDataParser:null}
})(jQuery);
(function(A){A.fn.featureList=function(C){var D=A(this);
var B=A(C.output);
new jQuery.featureList(D,B,C);
return this
};
A.featureList=function(F,D,E){function B(I){if(typeof I=="undefined"){I=G+1;
I=I>=C?0:I
}F.removeClass("current").filter(":eq("+I+")").addClass("current");
D.stop(true,true).filter(":visible").fadeOut();
D.filter(":eq("+I+")").fadeIn(function(){G=I
})
}var E=E||{};
var C=F.length;
var G=E.start_item||0;
E.pause_on_hover=E.pause_on_hover||true;
E.transition_interval=E.transition_interval||50000;
D.hide().eq(G).show();
F.eq(G).addClass("current");
F.click(function(){if(A(this).hasClass("current")){return false
}B(F.index(this))
});
if(E.transition_interval>0){var H=setInterval(function(){B()
},E.transition_interval);
if(E.pause_on_hover){F.mouseenter(function(){clearInterval(H)
}).mouseleave(function(){clearInterval(H);
H=setInterval(function(){B()
},E.transition_interval)
})
}}}
})(jQuery);
(function(A){A.fn.orphans=function(){var B=[];
this.each(function(){A.each(this.childNodes,function(){if(this.nodeType==3&&A.trim(this.nodeValue)){B.push(this)
}})
});
return A(B)
};
A.fn.accordion=function(B){var C={obj:"ul",objClass:".accordion",objID:"",wrapper:"li",el:"li",head:"",next:"ul",initShow:"",showMethod:"slideDown",hideMethod:"slideUp",showSpeed:400,hideSpeed:800,activeLink:true,event:"click",collapsible:true,standardExpansible:false,shift:false,elToWrap:null};
var D=A.extend({},C,B);
return this.each(function(){var E="#"+this.id,F=E+" "+D.obj+D.objID+D.objClass,H=F+" "+D.el,I=window.location.href;
A(F).find(D.head).addClass("h");
A(H).each(function(){var K=A(this);
if(K.find(D.next).length||K.next(D.next).length){if(K.find("> a").length){K.find("> a").addClass("trigger").css("display","block").attr("title","open/close")
}else{var L='<a class="trigger" style="display:block" href="#" title="open/close" />';
if(D.elToWrap){var M=K.orphans(),J=K.find(D.elToWrap);
M.add(J).wrapAll(L)
}else{K.orphans().wrap(L)
}}}else{K.addClass("last-child")
}});
A(H+"+ div:not(.outer)").wrap('<div class="outer" />');
A(F+" .h").each(function(){var J=A(this);
if(D.wrapper=="div"){J.add(J.next("div.outer")).wrapAll('<div class="new"></div>')
}});
A(H+" a.trigger").closest(D.wrapper).find("> "+D.next).hide();
if(D.activeLink){A(F+' a:not([href $= "#"])[href="'+I+'"]').addClass("active").closest(D.next).addClass("current")
}A(F).find(D.initShow).show().addClass("shown").parents(D.next).show().addClass("shown").end().parents(D.wrapper).find("> a.trigger, > "+D.el+" a.trigger").addClass("open");
if(D.event=="click"){var G="click"
}else{var G=[D.event]+" focus"
}A(H).find("a.trigger").bind(G,function(){var M=A(this),O=M.closest(D.wrapper),K=O.find("> "+D.next),L=O.siblings(D.wrapper);
if((K).length&&(K.is(":visible"))&&(D.collapsible)){A(this).removeClass("open");
K.filter(":visible")[D.hideMethod](D.hideSpeed)
}if((K).length&&(K.is(":hidden"))){if(!D.standardExpansible){L.find("> a.open, >"+D.el+" a.open").removeClass("open").end().find("> "+D.next+":visible")[D.hideMethod](D.hideSpeed)
}if(D.shift&&O.prev(D.wrapper).length){var J=O.nextAll().andSelf(),N=L.filter(":first");
if(D.shift=="clicked"||(D.shift=="all"&&L.length)){O.insertBefore(N)
}if(D.shift=="all"&&L.length>1){J.insertBefore(N)
}}A(this).addClass("open");
K[D.showMethod](D.showSpeed)
}if(D.event!="click"){M.click(function(){M.blur();
if(M.attr("href")=="#"){M.blur();
return false
}})
}if(D.event=="click"){return false
}})
})
}
})(jQuery);
(function(A){A.extend(A.fn,{validate:function(B){if(!this.length){B&&B.debug&&window.console&&console.warn("nothing selected, can't validate, returning nothing");
return 
}var C=A.data(this[0],"validator");
if(C){return C
}this.attr("novalidate","novalidate");
C=new A.validator(B,this[0]);
A.data(this[0],"validator",C);
if(C.settings.onsubmit){var D=this.find("input, button");
D.filter(".cancel").click(function(){C.cancelSubmit=true
});
if(C.settings.submitHandler){D.filter(":submit").click(function(){C.submitButton=this
})
}this.submit(function(E){if(C.settings.debug){E.preventDefault()
}function F(){if(C.settings.submitHandler){if(C.submitButton){var G=A("<input type='hidden'/>").attr("name",C.submitButton.name).val(C.submitButton.value).appendTo(C.currentForm)
}C.settings.submitHandler.call(C,C.currentForm);
if(C.submitButton){G.remove()
}return false
}return true
}if(C.cancelSubmit){C.cancelSubmit=false;
return F()
}if(C.form()){if(C.pendingRequest){C.formSubmitted=true;
return false
}return F()
}else{C.focusInvalid();
return false
}})
}return C
},valid:function(){if(A(this[0]).is("form")){return this.validate().form()
}else{var C=true;
var B=A(this[0].form).validate();
this.each(function(){C&=B.element(this)
});
return C
}},removeAttrs:function(D){var B={},C=this;
A.each(D.split(/\s/),function(E,F){B[F]=C.attr(F);
C.removeAttr(F)
});
return B
},rules:function(E,B){var G=this[0];
if(E){var D=A.data(G.form,"validator").settings;
var I=D.rules;
var J=A.validator.staticRules(G);
switch(E){case"add":A.extend(J,A.validator.normalizeRule(B));
I[G.name]=J;
if(B.messages){D.messages[G.name]=A.extend(D.messages[G.name],B.messages)
}break;
case"remove":if(!B){delete I[G.name];
return J
}var H={};
A.each(B.split(/\s/),function(K,L){H[L]=J[L];
delete J[L]
});
return H
}}var F=A.validator.normalizeRules(A.extend({},A.validator.metadataRules(G),A.validator.classRules(G),A.validator.attributeRules(G),A.validator.staticRules(G)),G);
if(F.required){var C=F.required;
delete F.required;
F=A.extend({required:C},F)
}return F
}});
A.extend(A.expr[":"],{blank:function(B){return !A.trim(""+B.value)
},filled:function(B){return !!A.trim(""+B.value)
},unchecked:function(B){return !B.checked
}});
A.validator=function(B,C){this.settings=A.extend(true,{},A.validator.defaults,B);
this.currentForm=C;
this.init()
};
A.validator.format=function(B,C){if(arguments.length==1){return function(){var D=A.makeArray(arguments);
D.unshift(B);
return A.validator.format.apply(this,D)
}
}if(arguments.length>2&&C.constructor!=Array){C=A.makeArray(arguments).slice(1)
}if(C.constructor!=Array){C=[C]
}A.each(C,function(D,E){B=B.replace(new RegExp("\\{"+D+"\\}","g"),E)
});
return B
};
A.extend(A.validator,{defaults:{messages:{},groups:{},rules:{},errorClass:"error",validClass:"valid",errorElement:"label",focusInvalid:true,errorContainer:A([]),errorLabelContainer:A([]),onsubmit:true,ignore:":hidden",ignoreTitle:false,onfocusin:function(B,C){this.lastActive=B;
if(this.settings.focusCleanup&&!this.blockFocusCleanup){this.settings.unhighlight&&this.settings.unhighlight.call(this,B,this.settings.errorClass,this.settings.validClass);
this.addWrapper(this.errorsFor(B)).hide()
}},onfocusout:function(B,C){if(!this.checkable(B)&&(B.name in this.submitted||!this.optional(B))){this.element(B)
}},onkeyup:function(B,C){if(B.name in this.submitted||B==this.lastElement){this.element(B)
}},onclick:function(B,C){if(B.name in this.submitted){this.element(B)
}else{if(B.parentNode.name in this.submitted){this.element(B.parentNode)
}}},highlight:function(D,B,C){if(D.type==="radio"){this.findByName(D.name).addClass(B).removeClass(C)
}else{A(D).addClass(B).removeClass(C)
}},unhighlight:function(D,B,C){if(D.type==="radio"){this.findByName(D.name).removeClass(B).addClass(C)
}else{A(D).removeClass(B).addClass(C)
}}},setDefaults:function(B){A.extend(A.validator.defaults,B)
},messages:{required:"This field is required.",remote:"Please fix this field.",email:"Please enter a valid email address.",url:"Please enter a valid URL.",date:"Please enter a valid date.",dateISO:"Please enter a valid date (ISO).",number:"Please enter a valid number.",digits:"Please enter only digits.",creditcard:"Please enter a valid credit card number.",equalTo:"Please enter the same value again.",accept:"Please enter a value with a valid extension.",maxlength:A.validator.format("Please enter no more than {0} characters."),minlength:A.validator.format("Please enter at least {0} characters."),rangelength:A.validator.format("Please enter a value between {0} and {1} characters long."),range:A.validator.format("Please enter a value between {0} and {1}."),max:A.validator.format("Please enter a value less than or equal to {0}."),min:A.validator.format("Please enter a value greater than or equal to {0}.")},autoCreateRanges:false,prototype:{init:function(){this.labelContainer=A(this.settings.errorLabelContainer);
this.errorContext=this.labelContainer.length&&this.labelContainer||A(this.currentForm);
this.containers=A(this.settings.errorContainer).add(this.settings.errorLabelContainer);
this.submitted={};
this.valueCache={};
this.pendingRequest=0;
this.pending={};
this.invalid={};
this.reset();
var B=(this.groups={});
A.each(this.settings.groups,function(E,F){A.each(F.split(/\s/),function(H,G){B[G]=E
})
});
var D=this.settings.rules;
A.each(D,function(E,F){D[E]=A.validator.normalizeRule(F)
});
function C(G){var F=A.data(this[0].form,"validator"),E="on"+G.type.replace(/^validate/,"");
F.settings[E]&&F.settings[E].call(F,this[0],G)
}A(this.currentForm).validateDelegate("[type='text'], [type='password'], [type='file'], select, textarea, [type='number'], [type='search'] ,[type='tel'], [type='url'], [type='email'], [type='datetime'], [type='date'], [type='month'], [type='week'], [type='time'], [type='datetime-local'], [type='range'], [type='color'] ","focusin focusout keyup",C).validateDelegate("[type='radio'], [type='checkbox'], select, option","click",C);
if(this.settings.invalidHandler){A(this.currentForm).bind("invalid-form.validate",this.settings.invalidHandler)
}},form:function(){this.checkForm();
A.extend(this.submitted,this.errorMap);
this.invalid=A.extend({},this.errorMap);
if(!this.valid()){A(this.currentForm).triggerHandler("invalid-form",[this])
}this.showErrors();
return this.valid()
},checkForm:function(){this.prepareForm();
for(var B=0,C=(this.currentElements=this.elements());
C[B];
B++){this.check(C[B])
}return this.valid()
},element:function(C){C=this.validationTargetFor(this.clean(C));
this.lastElement=C;
this.prepareElement(C);
this.currentElements=A(C);
var B=this.check(C);
if(B){delete this.invalid[C.name]
}else{this.invalid[C.name]=true
}if(!this.numberOfInvalids()){this.toHide=this.toHide.add(this.containers)
}this.showErrors();
return B
},showErrors:function(C){if(C){A.extend(this.errorMap,C);
this.errorList=[];
for(var B in C){this.errorList.push({message:C[B],element:this.findByName(B)[0]})
}this.successList=A.grep(this.successList,function(D){return !(D.name in C)
})
}this.settings.showErrors?this.settings.showErrors.call(this,this.errorMap,this.errorList):this.defaultShowErrors()
},resetForm:function(){if(A.fn.resetForm){A(this.currentForm).resetForm()
}this.submitted={};
this.lastElement=null;
this.prepareForm();
this.hideErrors();
this.elements().removeClass(this.settings.errorClass)
},numberOfInvalids:function(){return this.objectLength(this.invalid)
},objectLength:function(D){var C=0;
for(var B in D){C++
}return C
},hideErrors:function(){this.addWrapper(this.toHide).hide()
},valid:function(){return this.size()==0
},size:function(){return this.errorList.length
},focusInvalid:function(){if(this.settings.focusInvalid){try{A(this.findLastActive()||this.errorList.length&&this.errorList[0].element||[]).filter(":visible").focus().trigger("focusin")
}catch(B){}}},findLastActive:function(){var B=this.lastActive;
return B&&A.grep(this.errorList,function(C){return C.element.name==B.name
}).length==1&&B
},elements:function(){var C=this,B={};
return A(this.currentForm).find("input, select, textarea").not(":submit, :reset, :image, [disabled]").not(this.settings.ignore).filter(function(){!this.name&&C.settings.debug&&window.console&&console.error("%o has no name assigned",this);
if(this.name in B||!C.objectLength(A(this).rules())){return false
}B[this.name]=true;
return true
})
},clean:function(B){return A(B)[0]
},errors:function(){return A(this.settings.errorElement+"."+this.settings.errorClass,this.errorContext)
},reset:function(){this.successList=[];
this.errorList=[];
this.errorMap={};
this.toShow=A([]);
this.toHide=A([]);
this.currentElements=A([])
},prepareForm:function(){this.reset();
this.toHide=this.errors().add(this.containers)
},prepareElement:function(B){this.reset();
this.toHide=this.errorsFor(B)
},check:function(C){C=this.validationTargetFor(this.clean(C));
var G=A(C).rules();
var D=false;
for(var H in G){var F={method:H,parameters:G[H]};
try{var B=A.validator.methods[H].call(this,C.value.replace(/\r/g,""),C,F.parameters);
if(B=="dependency-mismatch"){D=true;
continue
}D=false;
if(B=="pending"){this.toHide=this.toHide.not(this.errorsFor(C));
return 
}if(!B){this.formatAndAdd(C,F);
return false
}}catch(E){this.settings.debug&&window.console&&console.log("exception occured when checking element "+C.id+", check the '"+F.method+"' method",E);
throw E
}}if(D){return 
}if(this.objectLength(G)){this.successList.push(C)
}return true
},customMetaMessage:function(B,D){if(!A.metadata){return 
}var C=this.settings.meta?A(B).metadata()[this.settings.meta]:A(B).metadata();
return C&&C.messages&&C.messages[D]
},customMessage:function(C,D){var B=this.settings.messages[C];
return B&&(B.constructor==String?B:B[D])
},findDefined:function(){for(var B=0;
B<arguments.length;
B++){if(arguments[B]!==undefined){return arguments[B]
}}return undefined
},defaultMessage:function(B,C){return this.findDefined(this.customMessage(B.name,C),this.customMetaMessage(B,C),!this.settings.ignoreTitle&&B.title||undefined,A.validator.messages[C],"<strong>Warning: No message defined for "+B.name+"</strong>")
},formatAndAdd:function(C,E){var D=this.defaultMessage(C,E.method),B=/\$?\{(\d+)\}/g;
if(typeof D=="function"){D=D.call(this,E.parameters,C)
}else{if(B.test(D)){D=jQuery.format(D.replace(B,"{$1}"),E.parameters)
}}this.errorList.push({message:D,element:C});
this.errorMap[C.name]=D;
this.submitted[C.name]=D
},addWrapper:function(B){if(this.settings.wrapper){B=B.add(B.parent(this.settings.wrapper))
}return B
},defaultShowErrors:function(){for(var C=0;
this.errorList[C];
C++){var B=this.errorList[C];
this.settings.highlight&&this.settings.highlight.call(this,B.element,this.settings.errorClass,this.settings.validClass);
this.showLabel(B.element,B.message)
}if(this.errorList.length){this.toShow=this.toShow.add(this.containers)
}if(this.settings.success){for(var C=0;
this.successList[C];
C++){this.showLabel(this.successList[C])
}}if(this.settings.unhighlight){for(var C=0,D=this.validElements();
D[C];
C++){this.settings.unhighlight.call(this,D[C],this.settings.errorClass,this.settings.validClass)
}}this.toHide=this.toHide.not(this.toShow);
this.hideErrors();
this.addWrapper(this.toShow).show()
},validElements:function(){return this.currentElements.not(this.invalidElements())
},invalidElements:function(){return A(this.errorList).map(function(){return this.element
})
},showLabel:function(C,D){var B=this.errorsFor(C);
if(B.length){B.removeClass(this.settings.validClass).addClass(this.settings.errorClass);
B.attr("generated")&&B.html(D)
}else{B=A("<"+this.settings.errorElement+"/>").attr({"for":this.idOrName(C),generated:true}).addClass(this.settings.errorClass).html(D||"");
if(this.settings.wrapper){B=B.hide().show().wrap("<"+this.settings.wrapper+"/>").parent()
}if(!this.labelContainer.append(B).length){this.settings.errorPlacement?this.settings.errorPlacement(B,A(C)):B.insertAfter(C)
}}if(!D&&this.settings.success){B.text("");
typeof this.settings.success=="string"?B.addClass(this.settings.success):this.settings.success(B)
}this.toShow=this.toShow.add(B)
},errorsFor:function(C){var B=this.idOrName(C);
return this.errors().filter(function(){return A(this).attr("for")==B
})
},idOrName:function(B){return this.groups[B.name]||(this.checkable(B)?B.name:B.id||B.name)
},validationTargetFor:function(B){if(this.checkable(B)){B=this.findByName(B.name).not(this.settings.ignore)[0]
}return B
},checkable:function(B){return/radio|checkbox/i.test(B.type)
},findByName:function(B){var C=this.currentForm;
return A(document.getElementsByName(B)).map(function(D,E){return E.form==C&&E.name==B&&E||null
})
},getLength:function(C,B){switch(B.nodeName.toLowerCase()){case"select":return A("option:selected",B).length;
case"input":if(this.checkable(B)){return this.findByName(B.name).filter(":checked").length
}}return C.length
},depend:function(C,B){return this.dependTypes[typeof C]?this.dependTypes[typeof C](C,B):true
},dependTypes:{"boolean":function(C,B){return C
},string:function(C,B){return !!A(C,B.form).length
},"function":function(C,B){return C(B)
}},optional:function(B){return !A.validator.methods.required.call(this,A.trim(B.value),B)&&"dependency-mismatch"
},startRequest:function(B){if(!this.pending[B.name]){this.pendingRequest++;
this.pending[B.name]=true
}},stopRequest:function(B,C){this.pendingRequest--;
if(this.pendingRequest<0){this.pendingRequest=0
}delete this.pending[B.name];
if(C&&this.pendingRequest==0&&this.formSubmitted&&this.form()){A(this.currentForm).submit();
this.formSubmitted=false
}else{if(!C&&this.pendingRequest==0&&this.formSubmitted){A(this.currentForm).triggerHandler("invalid-form",[this]);
this.formSubmitted=false
}}},previousValue:function(B){return A.data(B,"previousValue")||A.data(B,"previousValue",{old:null,valid:true,message:this.defaultMessage(B,"remote")})
}},classRuleSettings:{required:{required:true},email:{email:true},url:{url:true},date:{date:true},dateISO:{dateISO:true},dateDE:{dateDE:true},number:{number:true},numberDE:{numberDE:true},digits:{digits:true},creditcard:{creditcard:true}},addClassRules:function(B,C){B.constructor==String?this.classRuleSettings[B]=C:A.extend(this.classRuleSettings,B)
},classRules:function(C){var D={};
var B=A(C).attr("class");
B&&A.each(B.split(" "),function(){if(this in A.validator.classRuleSettings){A.extend(D,A.validator.classRuleSettings[this])
}});
return D
},attributeRules:function(C){var E={};
var B=A(C);
for(var F in A.validator.methods){var D;
if(F==="required"&&typeof A.fn.prop==="function"){D=B.prop(F)
}else{D=B.attr(F)
}if(D){E[F]=D
}else{if(B[0].getAttribute("type")===F){E[F]=true
}}}if(E.maxlength&&/-1|2147483647|524288/.test(E.maxlength)){delete E.maxlength
}return E
},metadataRules:function(B){if(!A.metadata){return{}
}var C=A.data(B.form,"validator").settings.meta;
return C?A(B).metadata()[C]:A(B).metadata()
},staticRules:function(C){var D={};
var B=A.data(C.form,"validator");
if(B.settings.rules){D=A.validator.normalizeRule(B.settings.rules[C.name])||{}
}return D
},normalizeRules:function(C,B){A.each(C,function(F,E){if(E===false){delete C[F];
return 
}if(E.param||E.depends){var D=true;
switch(typeof E.depends){case"string":D=!!A(E.depends,B.form).length;
break;
case"function":D=E.depends.call(B,B);
break
}if(D){C[F]=E.param!==undefined?E.param:true
}else{delete C[F]
}}});
A.each(C,function(D,E){C[D]=A.isFunction(E)?E(B):E
});
A.each(["minlength","maxlength","min","max"],function(){if(C[this]){C[this]=Number(C[this])
}});
A.each(["rangelength","range"],function(){if(C[this]){C[this]=[Number(C[this][0]),Number(C[this][1])]
}});
if(A.validator.autoCreateRanges){if(C.min&&C.max){C.range=[C.min,C.max];
delete C.min;
delete C.max
}if(C.minlength&&C.maxlength){C.rangelength=[C.minlength,C.maxlength];
delete C.minlength;
delete C.maxlength
}}if(C.messages){delete C.messages
}return C
},normalizeRule:function(C){if(typeof C=="string"){var B={};
A.each(C.split(/\s/),function(){B[this]=true
});
C=B
}return C
},addMethod:function(B,D,C){A.validator.methods[B]=D;
A.validator.messages[B]=C!=undefined?C:A.validator.messages[B];
if(D.length<3){A.validator.addClassRules(B,A.validator.normalizeRule(B))
}},methods:{required:function(C,B,E){if(!this.depend(E,B)){return"dependency-mismatch"
}switch(B.nodeName.toLowerCase()){case"select":var D=A(B).val();
return D&&D.length>0;
case"input":if(this.checkable(B)){return this.getLength(C,B)>0
}default:return A.trim(C).length>0
}},remote:function(F,C,G){if(this.optional(C)){return"dependency-mismatch"
}var D=this.previousValue(C);
if(!this.settings.messages[C.name]){this.settings.messages[C.name]={}
}D.originalMessage=this.settings.messages[C.name].remote;
this.settings.messages[C.name].remote=D.message;
G=typeof G=="string"&&{url:G}||G;
if(this.pending[C.name]){return"pending"
}if(D.old===F){return D.valid
}D.old=F;
var B=this;
this.startRequest(C);
var E={};
E[C.name]=F;
A.ajax(A.extend(true,{url:G,mode:"abort",port:"validate"+C.name,dataType:"json",data:E,success:function(I){B.settings.messages[C.name].remote=D.originalMessage;
var K=I===true;
if(K){var H=B.formSubmitted;
B.prepareElement(C);
B.formSubmitted=H;
B.successList.push(C);
B.showErrors()
}else{var L={};
var J=I||B.defaultMessage(C,"remote");
L[C.name]=D.message=A.isFunction(J)?J(F):J;
B.showErrors(L)
}D.valid=K;
B.stopRequest(C,K)
}},G));
return"pending"
},minlength:function(C,B,D){return this.optional(B)||this.getLength(A.trim(C),B)>=D
},maxlength:function(C,B,D){return this.optional(B)||this.getLength(A.trim(C),B)<=D
},rangelength:function(D,B,E){var C=this.getLength(A.trim(D),B);
return this.optional(B)||(C>=E[0]&&C<=E[1])
},min:function(C,B,D){return this.optional(B)||C>=D
},max:function(C,B,D){return this.optional(B)||C<=D
},range:function(C,B,D){return this.optional(B)||(C>=D[0]&&C<=D[1])
},email:function(C,B){return this.optional(B)||/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(C)
},url:function(C,B){return this.optional(B)||/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(C)
},date:function(C,B){return this.optional(B)||!/Invalid|NaN/.test(new Date(C))
},dateISO:function(C,B){return this.optional(B)||/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(C)
},number:function(C,B){return this.optional(B)||/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(C)
},digits:function(C,B){return this.optional(B)||/^\d+$/.test(C)
},creditcard:function(F,C){if(this.optional(C)){return"dependency-mismatch"
}if(/[^0-9 -]+/.test(F)){return false
}var G=0,E=0,B=false;
F=F.replace(/\D/g,"");
for(var H=F.length-1;
H>=0;
H--){var D=F.charAt(H);
var E=parseInt(D,10);
if(B){if((E*=2)>9){E-=9
}}G+=E;
B=!B
}return(G%10)==0
},accept:function(C,B,D){D=typeof D=="string"?D.replace(/,/g,"|"):"png|jpe?g|gif";
return this.optional(B)||C.match(new RegExp(".("+D+")$","i"))
},equalTo:function(C,B,E){var D=A(E).unbind(".validate-equalTo").bind("blur.validate-equalTo",function(){A(B).valid()
});
return C==D.val()
}}});
A.format=A.validator.format
})(jQuery);
(function(C){var A={};
if(C.ajaxPrefilter){C.ajaxPrefilter(function(F,E,G){var D=F.port;
if(F.mode=="abort"){if(A[D]){A[D].abort()
}A[D]=G
}})
}else{var B=C.ajax;
C.ajax=function(E){var F=("mode" in E?E:C.ajaxSettings).mode,D=("port" in E?E:C.ajaxSettings).port;
if(F=="abort"){if(A[D]){A[D].abort()
}return(A[D]=B.apply(this,arguments))
}return B.apply(this,arguments)
}
}})(jQuery);
(function(A){if(!jQuery.event.special.focusin&&!jQuery.event.special.focusout&&document.addEventListener){A.each({focus:"focusin",blur:"focusout"},function(C,B){A.event.special[B]={setup:function(){this.addEventListener(C,D,true)
},teardown:function(){this.removeEventListener(C,D,true)
},handler:function(E){arguments[0]=A.event.fix(E);
arguments[0].type=B;
return A.event.handle.apply(this,arguments)
}};
function D(E){E=A.event.fix(E);
E.type=B;
return A.event.handle.call(this,E)
}})
}A.extend(A.fn,{validateDelegate:function(D,C,B){return this.bind(C,function(E){var F=A(E.target);
if(F.is(D)){return B.apply(F,arguments)
}})
}})
})(jQuery);
(function(E){var D=E.scrollTo=function(A,B,C){E(window).scrollTo(A,B,C)
};
D.defaults={axis:"xy",duration:parseFloat(E.fn.jquery)>=1.3?0:1};
D.window=function(A){return E(window)._scrollable()
};
E.fn._scrollable=function(){return this.map(function(){var B=this,C=!B.nodeName||E.inArray(B.nodeName.toLowerCase(),["iframe","#document","html","body"])!=-1;
if(!C){return B
}var A=(B.contentWindow||B).document||B.ownerDocument||B;
return E.browser.safari||A.compatMode=="BackCompat"?A.body:A.documentElement
})
};
E.fn.scrollTo=function(A,B,C){if(typeof B=="object"){C=B;
B=0
}if(typeof C=="function"){C={onAfter:C}
}if(A=="max"){A=9000000000
}C=E.extend({},D.defaults,C);
B=B||C.speed||C.duration;
C.queue=C.queue&&C.axis.length>1;
if(C.queue){B/=2
}C.offset=F(C.offset);
C.over=F(C.over);
return this._scrollable().each(function(){var N=this,P=E(N),O=A,Q,S={},T=P.is("html,body");
switch(typeof O){case"number":case"string":if(/^([+-]=)?\d+(\.\d+)?(px|%)?$/.test(O)){O=F(O);
break
}O=E(O,this);
case"object":if(O.is||O.style){Q=(O=E(O)).offset()
}}E.each(C.axis.split(""),function(K,J){var I=J=="x"?"Left":"Top",G=I.toLowerCase(),L="scroll"+I,M=N[L],V=D.max(N,J);
if(Q){S[L]=Q[G]+(T?0:M-P.offset()[G]);
if(C.margin){S[L]-=parseInt(O.css("margin"+I))||0;
S[L]-=parseInt(O.css("border"+I+"Width"))||0
}S[L]+=C.offset[G]||0;
if(C.over[G]){S[L]+=O[J=="x"?"width":"height"]()*C.over[G]
}}else{var H=O[G];
S[L]=H.slice&&H.slice(-1)=="%"?parseFloat(H)/100*V:H
}if(/^\d+$/.test(S[L])){S[L]=S[L]<=0?0:Math.min(S[L],V)
}if(!K&&C.queue){if(M!=S[L]){R(C.onAfterFirst)
}delete S[L]
}});
R(C.onAfter);
function R(G){P.animate(S,B,C.easing,G&&function(){G.call(this,A,C)
})
}}).end()
};
D.max=function(A,B){var C=B=="x"?"Width":"Height",M="scroll"+C;
if(!E(A).is("html,body")){return A[M]-E(A)[C.toLowerCase()]()
}var K="client"+C,L=A.ownerDocument.documentElement,N=A.ownerDocument.body;
return Math.max(L[M],N[M])-Math.min(L[K],N[K])
};
function F(A){return typeof A=="object"?A:{top:A,left:A}
}})(jQuery);
(function($){$.tiny=$.tiny||{};$.tiny.scrollbar={options:{axis:'y',wheel:40,scroll:true,size:'auto',sizethumb:'auto'}};$.fn.tinyscrollbar=function(options){var options=$.extend({},$.tiny.scrollbar.options,options);this.each(function(){$(this).data('tsb',new Scrollbar($(this),options));});return this;};$.fn.tinyscrollbar_update=function(sScroll){return $(this).data('tsb').update(sScroll);};function Scrollbar(root,options){var oSelf=this;var oWrapper=root;var oViewport={obj:$('.viewport',root)};var oContent={obj:$('.overview',root)};var oScrollbar={obj:$('.scrollbar',root)};var oTrack={obj:$('.track',oScrollbar.obj)};var oThumb={obj:$('.thumb',oScrollbar.obj)};var sAxis=options.axis=='x',sDirection=sAxis?'left':'top',sSize=sAxis?'Width':'Height';var iScroll,iPosition={start:0,now:0},iMouse={};function initialize(){oSelf.update();setEvents();return oSelf;}
this.update=function(sScroll){oViewport[options.axis]=oViewport.obj[0]['offset'+sSize];oContent[options.axis]=oContent.obj[0]['scroll'+sSize];oContent.ratio=oViewport[options.axis]/oContent[options.axis];oScrollbar.obj.toggleClass('disable',oContent.ratio>=1);oTrack[options.axis]=options.size=='auto'?oViewport[options.axis]:options.size;oThumb[options.axis]=Math.min(oTrack[options.axis],Math.max(0,(options.sizethumb=='auto'?(oTrack[options.axis]*oContent.ratio):options.sizethumb)));oScrollbar.ratio=options.sizethumb=='auto'?(oContent[options.axis]/oTrack[options.axis]):(oContent[options.axis]-oViewport[options.axis])/(oTrack[options.axis]-oThumb[options.axis]);iScroll=(sScroll=='relative'&&oContent.ratio<=1)?Math.min((oContent[options.axis]-oViewport[options.axis]),Math.max(0,iScroll)):0;iScroll=(sScroll=='bottom'&&oContent.ratio<=1)?(oContent[options.axis]-oViewport[options.axis]):isNaN(parseInt(sScroll))?iScroll:parseInt(sScroll);setSize();};function setSize(){oThumb.obj.css(sDirection,iScroll/oScrollbar.ratio);oContent.obj.css(sDirection,-iScroll);iMouse['start']=oThumb.obj.offset()[sDirection];var sCssSize=sSize.toLowerCase();oScrollbar.obj.css(sCssSize,oTrack[options.axis]);oTrack.obj.css(sCssSize,oTrack[options.axis]);oThumb.obj.css(sCssSize,oThumb[options.axis]);};function setEvents(){oThumb.obj.bind('mousedown',start);oThumb.obj[0].ontouchstart=function(oEvent){oEvent.preventDefault();oThumb.obj.unbind('mousedown');start(oEvent.touches[0]);return false;};oTrack.obj.bind('mouseup',drag);if(options.scroll&&this.addEventListener){oWrapper[0].addEventListener('DOMMouseScroll',wheel,false);oWrapper[0].addEventListener('mousewheel',wheel,false);}
else if(options.scroll){oWrapper[0].onmousewheel=wheel;}};function start(oEvent){iMouse.start=sAxis?oEvent.pageX:oEvent.pageY;var oThumbDir=parseInt(oThumb.obj.css(sDirection));iPosition.start=oThumbDir=='auto'?0:oThumbDir;$(document).bind('mousemove',drag);document.ontouchmove=function(oEvent){$(document).unbind('mousemove');drag(oEvent.touches[0]);};$(document).bind('mouseup',end);oThumb.obj.bind('mouseup',end);oThumb.obj[0].ontouchend=document.ontouchend=function(oEvent){$(document).unbind('mouseup');oThumb.obj.unbind('mouseup');end(oEvent.touches[0]);};return false;};function wheel(oEvent){if(!(oContent.ratio>=1)){oEvent=$.event.fix(oEvent||window.event);var iDelta=oEvent.wheelDelta?oEvent.wheelDelta/120:-oEvent.detail/3;iScroll-=iDelta*options.wheel;iScroll=Math.min((oContent[options.axis]-oViewport[options.axis]),Math.max(0,iScroll));oThumb.obj.css(sDirection,iScroll/oScrollbar.ratio);oContent.obj.css(sDirection,-iScroll);oEvent.preventDefault();};};function end(oEvent){$(document).unbind('mousemove',drag);$(document).unbind('mouseup',end);oThumb.obj.unbind('mouseup',end);document.ontouchmove=oThumb.obj[0].ontouchend=document.ontouchend=null;return false;};function drag(oEvent){if(!(oContent.ratio>=1)){iPosition.now=Math.min((oTrack[options.axis]-oThumb[options.axis]),Math.max(0,(iPosition.start+((sAxis?oEvent.pageX:oEvent.pageY)-iMouse.start))));iScroll=iPosition.now*oScrollbar.ratio;oContent.obj.css(sDirection,-iScroll);oThumb.obj.css(sDirection,iPosition.now);;}
return false;};return initialize();};})(jQuery);
(function(A){A.fn.extend({autocomplete:function(B,C){var D=typeof B=="string";
C=A.extend({},A.Autocompleter.defaults,{url:D?B:null,data:D?null:B,delay:D?A.Autocompleter.defaults.delay:10,max:C&&!C.scroll?10:150},C);
C.highlight=C.highlight||function(E){return E
};
C.formatMatch=C.formatMatch||C.formatItem;
return this.each(function(){new A.Autocompleter(this,C)
})
},result:function(B){return this.bind("result",B)
},search:function(B){return this.trigger("search",[B])
},flushCache:function(){return this.trigger("flushCache")
},setOptions:function(B){return this.trigger("setOptions",[B])
},unautocomplete:function(){return this.trigger("unautocomplete")
}});
A.Autocompleter=function(L,G){var C={UP:38,DOWN:40,DEL:46,TAB:9,RETURN:13,ESC:27,COMMA:188,PAGEUP:33,PAGEDOWN:34,BACKSPACE:8};
var B=A(L).attr("autocomplete","off").addClass(G.inputClass);
var J;
var P="";
var M=A.Autocompleter.Cache(G);
var E=0;
var U;
var X={mouseDownOnSelect:false};
var R=A.Autocompleter.Select(G,L,D,X);
var W;
A.browser.opera&&A(L.form).bind("submit.autocomplete",function(){if(W){W=false;
return false
}});
B.bind((A.browser.opera?"keypress":"keydown")+".autocomplete",function(Y){E=1;
U=Y.keyCode;
switch(Y.keyCode){case C.UP:Y.preventDefault();
if(R.visible()){R.prev()
}else{T(0,true)
}break;
case C.DOWN:Y.preventDefault();
if(R.visible()){R.next()
}else{T(0,true)
}break;
case C.PAGEUP:Y.preventDefault();
if(R.visible()){R.pageUp()
}else{T(0,true)
}break;
case C.PAGEDOWN:Y.preventDefault();
if(R.visible()){R.pageDown()
}else{T(0,true)
}break;
case G.multiple&&A.trim(G.multipleSeparator)==","&&C.COMMA:case C.TAB:case C.RETURN:if(D()){Y.preventDefault();
W=true;
return false
}break;
case C.ESC:R.hide();
break;
default:clearTimeout(J);
J=setTimeout(T,G.delay);
break
}}).focus(function(){E++
}).blur(function(){E=0;
if(!X.mouseDownOnSelect){S()
}}).click(function(){if(E++>1&&!R.visible()){T(0,true)
}}).bind("search",function(){var Y=(arguments.length>1)?arguments[1]:null;
function Z(d,c){var a;
if(c&&c.length){for(var b=0;
b<c.length;
b++){if(c[b].result.toLowerCase()==d.toLowerCase()){a=c[b];
break
}}}if(typeof Y=="function"){Y(a)
}else{B.trigger("result",a&&[a.data,a.value])
}}A.each(H(B.val()),function(a,b){F(b,Z,Z)
})
}).bind("flushCache",function(){M.flush()
}).bind("setOptions",function(){A.extend(G,arguments[1]);
if("data" in arguments[1]){M.populate()
}}).bind("unautocomplete",function(){R.unbind();
B.unbind();
A(L.form).unbind(".autocomplete")
});
function D(){var b=R.selected();
if(!b){return false
}var Y=b.result;
P=Y;
if(G.multiple){var e=H(B.val());
if(e.length>1){var a=G.multipleSeparator.length;
var d=A(L).selection().start;
var c,Z=0;
A.each(e,function(f,g){Z+=g.length;
if(d<=Z){c=f;
return false
}Z+=a
});
e[c]=Y;
Y=e.join(G.multipleSeparator)
}Y+=G.multipleSeparator
}B.val(Y);
V();
B.trigger("result",[b.data,b.value]);
return true
}function T(a,Z){if(U==C.DEL){R.hide();
return 
}var Y=B.val();
if(!Z&&Y==P){return 
}P=Y;
Y=I(Y);
if(Y.length>=G.minChars){B.addClass(G.loadingClass);
if(!G.matchCase){Y=Y.toLowerCase()
}F(Y,K,V)
}else{N();
R.hide()
}}function H(Y){if(!Y){return[""]
}if(!G.multiple){return[A.trim(Y)]
}return A.map(Y.split(G.multipleSeparator),function(Z){return A.trim(Y).length?A.trim(Z):null
})
}function I(Y){if(!G.multiple){return Y
}var a=H(Y);
if(a.length==1){return a[0]
}var Z=A(L).selection().start;
if(Z==Y.length){a=H(Y)
}else{a=H(Y.replace(Y.substring(Z),""))
}return a[a.length-1]
}function Q(Y,Z){if(G.autoFill&&(I(B.val()).toLowerCase()==Y.toLowerCase())&&U!=C.BACKSPACE){B.val(B.val()+Z.substring(I(P).length));
A(L).selection(P.length,P.length+Z.length)
}}function S(){clearTimeout(J);
J=setTimeout(V,200);
A(".citylist-outer .city-search-drop").css("margin-bottom",0)
}function V(){var Y=R.visible();
R.hide();
clearTimeout(J);
N();
if(G.mustMatch){B.search(function(Z){if(!Z){if(G.multiple){var a=H(B.val()).slice(0,-1);
B.val(a.join(G.multipleSeparator)+(a.length?G.multipleSeparator:""))
}else{B.val("");
B.trigger("result",null)
}}})
}}function K(Z,Y){if(Y&&Y.length&&E){N();
R.display(Y,Z);
Q(Z,Y[0].value);
R.show()
}else{V()
}}function F(Z,b,Y){if(!G.matchCase){Z=Z.toLowerCase()
}var a=M.load(Z);
if(a&&a.length){b(Z,a)
}else{if((typeof G.url=="string")&&(G.url.length>0)){var c={timestamp:+new Date()};
A.each(G.extraParams,function(d,e){c[d]=typeof e=="function"?e():e
});
A.ajax({mode:"abort",port:"autocomplete"+L.name,dataType:G.dataType,url:G.url,data:A.extend({q:I(Z),limit:G.max},c),success:function(e){var d=G.parse&&G.parse(e)||O(e);
M.add(Z,d);
b(Z,d)
}})
}else{R.emptyList();
Y(Z)
}}}function O(b){var Y=[];
var a=b.split("\n");
for(var Z=0;
Z<a.length;
Z++){var c=A.trim(a[Z]);
if(c){c=c.split("|");
Y[Y.length]={data:c,value:c[0],result:G.formatResult&&G.formatResult(c,c[0])||c[0]}
}}return Y
}function N(){B.removeClass(G.loadingClass)
}};
A.Autocompleter.defaults={inputClass:"ac_input",resultsClass:"ac_results",loadingClass:"ac_loading",minChars:1,delay:400,matchCase:false,matchSubset:true,matchContains:false,cacheLength:10,max:100,mustMatch:false,extraParams:{},selectFirst:true,formatItem:function(B){return B[0]
},formatMatch:null,autoFill:false,width:0,multiple:false,multipleSeparator:", ",highlight:function(C,B){return C.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)("+B.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi,"\\$1")+")(?![^<>]*>)(?![^&;]+;)","gi"),"<strong>$1</strong>")
},scroll:true,scrollHeight:180};
A.Autocompleter.Cache=function(C){var F={};
var D=0;
function H(K,J){if(!C.matchCase){K=K.toLowerCase()
}var I=K.indexOf(J);
if(C.matchContains=="word"){I=K.toLowerCase().search("\\b"+J.toLowerCase())
}if(I==-1){return false
}return I==0||C.matchContains
}function G(J,I){if(D>C.cacheLength){B()
}if(!F[J]){D++
}F[J]=I
}function E(){if(!C.data){return false
}var J={},I=0;
if(!C.url){C.cacheLength=1
}J[""]=[];
for(var L=0,K=C.data.length;
L<K;
L++){var O=C.data[L];
O=(typeof O=="string")?[O]:O;
var N=C.formatMatch(O,L+1,C.data.length);
if(N===false){continue
}var M=N.charAt(0).toLowerCase();
if(!J[M]){J[M]=[]
}var P={value:N,data:O,result:C.formatResult&&C.formatResult(O)||N};
J[M].push(P);
if(I++<C.max){J[""].push(P)
}}A.each(J,function(Q,R){C.cacheLength++;
G(Q,R)
})
}setTimeout(E,25);
function B(){F={};
D=0
}return{flush:B,add:G,populate:E,load:function(L){if(!C.cacheLength||!D){return null
}if(!C.url&&C.matchContains){var K=[];
for(var I in F){if(I.length>0){var M=F[I];
A.each(M,function(O,N){if(H(N.value,L)){K.push(N)
}})
}}return K
}else{if(F[L]){return F[L]
}else{if(C.matchSubset){for(var J=L.length-1;
J>=C.minChars;
J--){var M=F[L.substr(0,J)];
if(M){var K=[];
A.each(M,function(O,N){if(H(N.value,L)){K[K.length]=N
}});
return K
}}}}}return null
}}
};
A.Autocompleter.Select=function(E,J,L,P){var I={ACTIVE:"ac_over"};
var K,F=-1,R,M="",S=true,C,O;
function N(){if(!S){return 
}C=A("<div/>").hide().addClass(E.resultsClass).css("position","absolute").appendTo(document.body);
O=A("<ul/>").appendTo(C).mouseover(function(T){if(Q(T).nodeName&&Q(T).nodeName.toUpperCase()=="LI"){F=A("li",O).removeClass(I.ACTIVE).index(Q(T));
A(Q(T)).addClass(I.ACTIVE)
}}).click(function(T){A(Q(T)).addClass(I.ACTIVE);
L();
J.focus();
return false
}).mousedown(function(){P.mouseDownOnSelect=true
}).mouseup(function(){P.mouseDownOnSelect=false
});
if(E.width>0){C.css("width",E.width)
}S=false
}function Q(U){var T=U.target;
while(T&&T.tagName!="LI"){T=T.parentNode
}if(!T){return[]
}return T
}function H(T){K.slice(F,F+1).removeClass(I.ACTIVE);
G(T);
var V=K.slice(F,F+1).addClass(I.ACTIVE);
if(E.scroll){var U=0;
K.slice(0,F).each(function(){U+=this.offsetHeight
});
if((U+V[0].offsetHeight-O.scrollTop())>O[0].clientHeight){O.scrollTop(U+V[0].offsetHeight-O.innerHeight())
}else{if(U<O.scrollTop()){O.scrollTop(U)
}}}}function G(T){F+=T;
if(F<0){F=K.size()-1
}else{if(F>=K.size()){F=0
}}}function B(T){return E.max&&E.max<T?E.max:T
}function D(){O.empty();
var U=B(R.length);
for(var W=0;
W<U;
W++){if(!R[W]){continue
}var Y=E.formatItem(R[W].data,W+1,U,R[W].value,M);
if(Y===false){continue
}var T=A("<li/>").html(E.highlight(Y,M)).addClass(W%2==0?"ac_even":"ac_odd").appendTo(O)[0];
A.data(T,"ac_data",R[W])
}K=O.find("li");
if(E.selectFirst){K.slice(0,1).addClass(I.ACTIVE);
F=0
}var V=K.size();
if(V>5){V=5
}var X=V*22+5;
A(".citylist-outer .city-search-drop").css("margin-bottom",X);
if(A.fn.bgiframe){O.bgiframe()
}}return{display:function(U,T){N();
R=U;
M=T;
D()
},next:function(){H(1)
},prev:function(){H(-1)
},pageUp:function(){if(F!=0&&F-8<0){H(-F)
}else{H(-8)
}},pageDown:function(){if(F!=K.size()-1&&F+8>K.size()){H(K.size()-1-F)
}else{H(8)
}},hide:function(){C&&C.hide();
K&&K.removeClass(I.ACTIVE);
F=-1;
A(".citylist-outer .city-search-drop").css("margin-bottom",0)
},visible:function(){return C&&C.is(":visible")
},current:function(){return this.visible()&&(K.filter("."+I.ACTIVE)[0]||E.selectFirst&&K[0])
},show:function(){var V=A(J).offset();
C.css({width:typeof E.width=="string"||E.width>0?E.width:A(J).width(),top:V.top+J.offsetHeight,left:V.left}).show();
if(E.scroll){O.scrollTop(0);
O.css({maxHeight:E.scrollHeight,overflow:"auto"});
if(A.browser.msie&&typeof document.body.style.maxHeight==="undefined"){var T=0;
K.each(function(){T+=this.offsetHeight
});
var U=T>E.scrollHeight;
O.css("height",U?E.scrollHeight:T);
if(!U){K.width(O.width()-parseInt(K.css("padding-left"))-parseInt(K.css("padding-right")))
}}}},selected:function(){var T=K&&K.filter("."+I.ACTIVE).removeClass(I.ACTIVE);
return T&&T.length&&A.data(T[0],"ac_data")
},emptyList:function(){O&&O.empty()
},unbind:function(){C&&C.remove()
}}
};
A.fn.selection=function(I,B){if(I!==undefined){return this.each(function(){if(this.createTextRange){var J=this.createTextRange();
if(B===undefined||I==B){J.move("character",I);
J.select()
}else{J.collapse(true);
J.moveStart("character",I);
J.moveEnd("character",B);
J.select()
}}else{if(this.setSelectionRange){this.setSelectionRange(I,B)
}else{if(this.selectionStart){this.selectionStart=I;
this.selectionEnd=B
}}}})
}var G=this[0];
if(G.createTextRange){var C=document.selection.createRange(),H=G.value,F="<->",D=C.text.length;
C.text=F;
var E=G.value.indexOf(F);
G.value=H;
this.selection(E,E+D);
return{start:E,end:E+D}
}else{if(G.selectionStart!==undefined){return{start:G.selectionStart,end:G.selectionEnd}
}}}
})(jQuery);
var JSON;
if(!JSON){JSON={}
}(function(){function f(n){return n<10?"0"+n:n
}if(typeof Date.prototype.toJSON!=="function"){Date.prototype.toJSON=function(key){return isFinite(this.valueOf())?this.getUTCFullYear()+"-"+f(this.getUTCMonth()+1)+"-"+f(this.getUTCDate())+"T"+f(this.getUTCHours())+":"+f(this.getUTCMinutes())+":"+f(this.getUTCSeconds())+"Z":null
};
String.prototype.toJSON=Number.prototype.toJSON=Boolean.prototype.toJSON=function(key){return this.valueOf()
}
}var cx=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,escapable=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,gap,indent,meta={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"},rep;
function quote(string){escapable.lastIndex=0;
return escapable.test(string)?'"'+string.replace(escapable,function(a){var c=meta[a];
return typeof c==="string"?c:"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)
})+'"':'"'+string+'"'
}function str(key,holder){var i,k,v,length,mind=gap,partial,value=holder[key];
if(value&&typeof value==="object"&&typeof value.toJSON==="function"){value=value.toJSON(key)
}if(typeof rep==="function"){value=rep.call(holder,key,value)
}switch(typeof value){case"string":return quote(value);
case"number":return isFinite(value)?String(value):"null";
case"boolean":case"null":return String(value);
case"object":if(!value){return"null"
}gap+=indent;
partial=[];
if(Object.prototype.toString.apply(value)==="[object Array]"){length=value.length;
for(i=0;
i<length;
i+=1){partial[i]=str(i,value)||"null"
}v=partial.length===0?"[]":gap?"[\n"+gap+partial.join(",\n"+gap)+"\n"+mind+"]":"["+partial.join(",")+"]";
gap=mind;
return v
}if(rep&&typeof rep==="object"){length=rep.length;
for(i=0;
i<length;
i+=1){if(typeof rep[i]==="string"){k=rep[i];
v=str(k,value);
if(v){partial.push(quote(k)+(gap?": ":":")+v)
}}}}else{for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=str(k,value);
if(v){partial.push(quote(k)+(gap?": ":":")+v)
}}}}v=partial.length===0?"{}":gap?"{\n"+gap+partial.join(",\n"+gap)+"\n"+mind+"}":"{"+partial.join(",")+"}";
gap=mind;
return v
}}if(typeof JSON.stringify!=="function"){JSON.stringify=function(value,replacer,space){var i;
gap="";
indent="";
if(typeof space==="number"){for(i=0;
i<space;
i+=1){indent+=" "
}}else{if(typeof space==="string"){indent=space
}}rep=replacer;
if(replacer&&typeof replacer!=="function"&&(typeof replacer!=="object"||typeof replacer.length!=="number")){throw new Error("JSON.stringify")
}return str("",{"":value})
}
}if(typeof JSON.parse!=="function"){JSON.parse=function(text,reviver){var j;
function walk(holder,key){var k,v,value=holder[key];
if(value&&typeof value==="object"){for(k in value){if(Object.prototype.hasOwnProperty.call(value,k)){v=walk(value,k);
if(v!==undefined){value[k]=v
}else{delete value[k]
}}}}return reviver.call(holder,key,value)
}text=String(text);
cx.lastIndex=0;
if(cx.test(text)){text=text.replace(cx,function(a){return"\\u"+("0000"+a.charCodeAt(0).toString(16)).slice(-4)
})
}if(/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,"@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,"]").replace(/(?:^|:|,)(?:\s*\[)+/g,""))){j=eval("("+text+")");
return typeof reviver==="function"?walk({"":j},""):j
}throw new SyntaxError("JSON.parse")
}
}}());
eval(function(J,G,H,L,K,I){K=function(A){return(A<G?"":K(parseInt(A/G)))+((A=A%G)>35?String.fromCharCode(A+29):A.toString(36))
};
if(!"".replace(/^/,String)){while(H--){I[K(H)]=L[H]||K(H)
}L=[function(A){return I[A]
}];
K=function(){return"\\w+"
};
H=1
}while(H--){if(L[H]){J=J.replace(new RegExp("\\b"+K(H)+"\\b","g"),L[H])
}}return J
}('D=(6($){6 D(2A,w){j(1T(w)!="2D")w={};$.2C(5,D.1Y,w);5.n=$(2A);5.1Q("1l","L","11","10","E");5.1P();5.E();5.L()};D.1Y={1G:["2V","2M","2S","2Q","2g","2N","2O","2P","2R","2L","2G","2F"],1v:["2H","2I","2K","2J","2g","2T","2U","36","35","3a","3b","3d"],1F:["3c","32","31","2W","2E","2X","2Y"],N:1};D.30={1P:6(){b R=$(\'<p h="2Z">\'+\'<m h="P 17" Y="[1g-1U]">&#2u;</m>\'+\' <m h="K"></m> \'+\'<m h="P 1a" Y="[1g-2h]">&#2n;</m>\'+\'</p>\');5.2v=$(".K",R);$(".17",R).F(5.u(6(){5.Q(-1)}));$(".1a",R).F(5.u(6(){5.Q(1)}));b V=$(\'<p h="3e">\'+\'<m h="P 17" Y="[2x+1g-1U]">&#2u;</m>\'+\' <m h="2t"></m> \'+\'<m h="P 1a" Y="[2x+1g-2h]">&#2n;</m>\'+\'</p>\');5.2q=$(".2t",V);$(".17",V).F(5.u(6(){5.Q(-12)}));$(".1a",V).F(5.u(6(){5.Q(12)}));b T=$(\'<Z h="T"></Z>\').S(R,V);b 15="<26><1W><1b>";$(5.1S(5.1F)).2k(6(){15+="<2l>"+5+"</2l>"});15+="</1b></1W><o></o></26>";5.x=5.O=$(\'<Z h="3m"></Z>\').S(T,15).3I(5.n);j($.2B.3K&&$.2B.3L<7){5.19=$(\'<2m h="3H" 3y="0" 3B="#"></2m>\').3F(5.x);5.O=5.O.3E(5.19);$(".P",T).23(6(){$(5).1d("1h")});$(".P",T).2f(6(){$(5).1t("1h")})};5.o=$("o",5.x);5.n.25(5.u(6(){5.E()}));5.E()},1D:6(8){b J=l g(8.f(),8.c(),1);j(!5.v||!(5.v.f()==J.f()&&5.v.c()==J.c())){5.v=J;b C=5.C(8),1e=5.1e(8);b 2o=5.29(C,1e);b M="";1f(b i=0;i<=2o;i++){b A=l g(C.f(),C.c(),C.q()+i,12,2i);j(5.2e(A))M+="<1b>";j(A.c()==8.c()){M+=\'<y h="1j" 8="\'+5.U(A)+\'">\'+A.q()+\'</y>\'}2j{M+=\'<y h="3J" 8="\'+5.U(A)+\'">\'+A.q()+\'</y>\'};j(5.2p(A))M+="</1b>"};5.o.1y().S(M);5.2v.1y().S(5.1J(8));5.2q.1y().S(5.v.f());$(".1j",5.o).F(5.u(6(e){5.1x($(e.1R).3D("8"))}));$("y[8="+5.U(l g())+"]",5.o).1d("3z");$("y.1j",5.o).23(6(){$(5).1d("1h")});$("y.1j",5.o).2f(6(){$(5).1t("1h")})};$(\'.1s\',5.o).1t("1s");$(\'y[8=\'+5.1w+\']\',5.o).1d("1s")},E:6(8){j(1T(8)=="3A"){8=5.2d(5.n.1Z())};j(!8)8=l g();5.r=8;5.1w=5.U(5.r);5.1D(5.r)},1x:6(20){5.n.1Z(20).25();5.L()},1l:6(){5.O.18("24","3G");$([21,1k.1c]).F(5.11);5.n.1r("28",5.1l);$(1k.1c).1K(5.10);5.2b()},L:6(){5.O.18("24","3M");$([21,1k.1c]).1r("F",5.11);5.n.28(5.1l);$(1k.1c).1r("1K",5.10)},11:6(e){j(e.1R!=5.n[0]&&!5.1L(e)){5.L()}},1L:6(e){b k=5.x.3C();k.1N=k.16+5.x.2y();k.1O=k.14+5.x.1H();a e.1M<k.1O&&e.1M>k.14&&e.22<k.1N&&e.22>k.16},10:6(e){3w(e.3f){t 9:t 27:5.L();a;z;t 13:5.1x(5.1w);z;t 33:5.1B(e.2w?-12:-1);z;t 34:5.1B(e.2w?12:1);z;t 38:5.W(-7);z;t 3x:5.W(7);z;t 37:5.W(-1);z;t 39:5.W(1);z;3k:a}e.3j()},2d:6(2c){b X;j(X=2c.3g(/^(\\d{1,2}) ([^\\s]+) (\\d{4,4})$/)){a l g(X[3],5.1X(X[2]),X[1],12,2i)}2j{a 3h}},U:6(8){a 8.q()+" "+5.1v[8.c()]+" "+8.f()},2b:6(){b k=5.n.k();5.O.18({14:k.14+5.n.1H(),16:k.16});j(5.19){5.19.18({3i:5.x.2y(),3n:5.x.1H()})}},W:6(B){b I=l g(5.r.f(),5.r.c(),5.r.q()+B);5.E(I)},1B:6(B){b I=l g(5.r.f(),5.r.c()+B,5.r.q());j(I.c()==5.r.c()+B+1){I.3o(0)};5.E(I)},Q:6(B){b J=l g(5.v.f(),5.v.c()+B,5.v.q());5.1D(J)},1J:6(8){a 5.1G[8.c()]},u:6(1p){b 1I=5;a 6(){a 1p.3u(1I,1n)}},1Q:6(){1f(b i=0;i<1n.1q;i++){5[1n[i]]=5.u(5[1n[i]])}},1m:6(1E,1V){1f(b i=0;i<1E.1q;i++){j(1V==1E[i])a i}},3v:6(K){a 5.1m(5.1G,K)},1X:6(K){a 5.1m(5.1v,K)},3t:6(2z){a 5.1m(5.1F,2z)},29:6(H,G){H=g.2s(H.f(),H.c(),H.q());G=g.2s(G.f(),G.c(),G.q());a(G-H)/3s},1u:6(2r,8,1C){b 2a=1C*(3p.3q(8.1o()-2r-(1C*7))%7);a l g(8.f(),8.c(),8.q()+2a)},C:6(8){a 5.1u(5.N,l g(8.f(),8.c()),-1)},1e:6(8){a 5.1u((5.N-1)%7,l g(8.f(),8.c()+1,0),1)},2e:6(8){a 8.1o()==5.N},2p:6(8){a 8.1o()==(5.N-1)%7},1S:6(1z){b 1A=[];1f(b i=0;i<1z.1q;i++){1A[i]=1z[(i+5.N)%7]};a 1A}};$.1p.1i=6(w){a 5.2k(6(){l D(5,w)})};$.1i={3r:6(w){$("n.1i").1i(w)}};a D})(3l);',62,235,"|||||this|function||date||return|var|getMonth||event|getFullYear|Date|class||if|offset|new|span|input|tbody||getDate|selectedDate||case|bindToObj|currentMonth|opts|dateSelector|td|break|currentDay|amount|rangeStart|DateInput|selectDate|click|end|start|newDate|newMonth|month_name|hide|dayCells|start_of_week|rootLayers|button|moveMonthBy|monthNav|append|nav|dateToString|yearNav|moveDateBy|matches|title|div|keydownHandler|hideIfClickOutside|||top|tableShell|left|prev|css|ieframe|next|tr|body|addClass|rangeEnd|for|Page|hover|date_input|selectable_day|document|show|indexFor|arguments|getDay|fn|length|unbind|selected|removeClass|changeDayTo|short_month_names|selectedDateString|changeInput|empty|days|newDays|moveDateMonthBy|direction|selectMonth|array|short_day_names|month_names|outerHeight|self|monthName|keydown|insideSelector|pageY|right|bottom|build|bindMethodsToObj|target|adjustDays|typeof|Up|value|thead|shortMonthNum|DEFAULT_OPTS|val|dateString|window|pageX|mouseover|display|change|table||focus|daysBetween|difference|setPosition|string|stringToDate|isFirstDayOfWeek|mouseout|May|Down|00|else|each|th|iframe|187|numDays|isLastDayOfWeek|yearNameSpan|dayOfWeek|UTC|year_name|171|monthNameSpan|ctrlKey|Ctrl|outerWidth|day_name|el|browser|extend|object|Thu|December|November|Jan|Feb|Apr|Mar|October|February|June|July|August|April|September|March|Jun|Jul|January|Wed|Fri|Sat|month_nav|prototype|Tue|Mon|||Sep|Aug||||Oct|Nov|Sun|Dec|year_nav|keyCode|match|null|width|preventDefault|default|jQuery|date_selector|height|setDate|Math|abs|initialize|86400000|shortDayNum|apply|monthNum|switch|40|frameborder|today|undefined|src|position|attr|add|insertBefore|block|date_selector_ieframe|insertAfter|unselected_month|msie|version|none".split("|"),0,{}));
(function(A){A.jgrid={defaults:{recordtext:"View {0} - {1} of {2}",emptyrecords:"No records to view",loadtext:"Loading...",pgtext:"Page {0} of {1}"},search:{caption:"Search...",Find:"Find",Reset:"Reset",odata:["equal","not equal","less","less or equal","greater","greater or equal","begins with","does not begin with","is in","is not in","ends with","does not end with","contains","does not contain"],groupOps:[{op:"AND",text:"all"},{op:"OR",text:"any"}],matchText:" match",rulesText:" rules"},edit:{addCaption:"Add Record",editCaption:"Edit Record",bSubmit:"Submit",bCancel:"Cancel",bClose:"Close",saveData:"Data has been changed! Save changes?",bYes:"Yes",bNo:"No",bExit:"Cancel",msg:{required:"Field is required",number:"Please, enter valid number",minValue:"value must be greater than or equal to ",maxValue:"value must be less than or equal to",email:"is not a valid e-mail",integer:"Please, enter valid integer value",date:"Please, enter valid date value",url:"is not a valid URL. Prefix required ('http://' or 'https://')",nodefined:" is not defined!",novalue:" return value is required!",customarray:"Custom function should return array!",customfcheck:"Custom function should be present in case of custom checking!"}},view:{caption:"View Record",bClose:"Close"},del:{caption:"Delete",msg:"Delete selected record(s)?",bSubmit:"Delete",bCancel:"Cancel"},nav:{edittext:"",edittitle:"Edit selected row",addtext:"",addtitle:"Add new row",deltext:"",deltitle:"Delete selected row",searchtext:"",searchtitle:"Find records",refreshtext:"",refreshtitle:"Reload Grid",alertcap:"Warning",alerttext:"Please, select row",viewtext:"",viewtitle:"View selected row"},col:{caption:"Select columns",bSubmit:"Ok",bCancel:"Cancel"},errors:{errcap:"Error",nourl:"No url is set",norecords:"No records to process",model:"Length of colNames <> colModel!"},formatter:{integer:{thousandsSeparator:" ",defaultValue:"0"},number:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,defaultValue:"0.00"},currency:{decimalSeparator:".",thousandsSeparator:" ",decimalPlaces:2,prefix:"",suffix:"",defaultValue:"0.00"},date:{dayNames:["Sun","Mon","Tue","Wed","Thr","Fri","Sat","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],monthNames:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","January","February","March","April","May","June","July","August","September","October","November","December"],AmPm:["am","pm","AM","PM"],S:function(B){return B<11||B>13?["st","nd","rd","th"][Math.min((B-1)%10,3)]:"th"
},srcformat:"Y-m-d",newformat:"d/m/Y",masks:{ISO8601Long:"Y-m-d H:i:s",ISO8601Short:"Y-m-d",ShortDate:"n/j/Y",LongDate:"l, F d, Y",FullDateTime:"l, F d, Y g:i:s A",MonthDay:"F d",ShortTime:"g:i A",LongTime:"g:i:s A",SortableDateTime:"Y-m-d\\TH:i:s",UniversalSortableDateTime:"Y-m-d H:i:sO",YearMonth:"F, Y"},reformatAfterEdit:false},baseLinkUrl:"",showAction:"",target:"",checkbox:{disabled:true},idName:"id"}}
})(jQuery);
/* 
* jqGrid  4.2.0 - jQuery Grid 
* Copyright (c) 2008, Tony Tomov, tony@trirand.com 
* Dual licensed under the MIT and GPL licenses 
* http://www.opensource.org/licenses/mit-license.php 
* http://www.gnu.org/licenses/gpl-2.0.html 
* Date:2011-10-11 
* Modules: grid.base.js; jquery.fmatter.js; grid.custom.js; grid.common.js; grid.formedit.js; grid.filter.js; grid.inlinedit.js; grid.celledit.js; jqModal.js; jqDnR.js; grid.subgrid.js; grid.grouping.js; grid.treegrid.js; grid.import.js; JsonXml.js; grid.tbltogrid.js; grid.jqueryui.js; 
*/
/*
 jqGrid  4.2.0  - jQuery Grid
 Copyright (c) 2008, Tony Tomov, tony@trirand.com
 Dual licensed under the MIT and GPL licenses
 http://www.opensource.org/licenses/mit-license.php
 http://www.gnu.org/licenses/gpl-2.0.html
 Date: 2011-10-11
*/
(function(b){b.jgrid=b.jgrid||{};b.extend(b.jgrid,{htmlDecode:function(f){if(f&&(f=="&nbsp;"||f=="&#160;"||f.length==1&&f.charCodeAt(0)==160))return"";return!f?f:String(f).replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&quot;/g,'"').replace(/&amp;/g,"&")},htmlEncode:function(f){return!f?f:String(f).replace(/&/g,"&amp;").replace(/\"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;")},format:function(f){var i=b.makeArray(arguments).slice(1);if(f===undefined)f="";return f.replace(/\{(\d+)\}/g,
function(h,c){return i[c]})},getCellIndex:function(f){f=b(f);if(f.is("tr"))return-1;f=(!f.is("td")&&!f.is("th")?f.closest("td,th"):f)[0];if(b.browser.msie)return b.inArray(f,f.parentNode.cells);return f.cellIndex},stripHtml:function(f){f+="";var i=/<("[^"]*"|'[^']*'|[^'">])*>/gi;if(f)return(f=f.replace(i,""))&&f!=="&nbsp;"&&f!=="&#160;"?f.replace(/\"/g,"'"):"";else return f},stripPref:function(f,i){var h=Object.prototype.toString.call(f).slice(8,-1);if(h=="String"||h=="Number"){f=String(f);i=f!=""?
String(i).replace(String(f),""):i}return i},stringToDoc:function(f){var i;if(typeof f!=="string")return f;try{i=(new DOMParser).parseFromString(f,"text/xml")}catch(h){i=new ActiveXObject("Microsoft.XMLDOM");i.async=false;i.loadXML(f)}return i&&i.documentElement&&i.documentElement.tagName!="parsererror"?i:null},parse:function(f){if(f.substr(0,9)=="while(1);")f=f.substr(9);if(f.substr(0,2)=="/*")f=f.substr(2,f.length-4);f||(f="{}");return b.jgrid.useJSON===true&&typeof JSON==="object"&&typeof JSON.parse===
"function"?JSON.parse(f):eval("("+f+")")},parseDate:function(f,i){var h={m:1,d:1,y:1970,h:0,i:0,s:0},c,g,k;c=/[\\\/:_;.,\t\T\s-]/;if(i&&i!==null&&i!==undefined){i=b.trim(i);i=i.split(c);f=f.split(c);var l=b.jgrid.formatter.date.monthNames,a=b.jgrid.formatter.date.AmPm,r=function(x,y){if(x===0){if(y==12)y=0}else if(y!=12)y+=12;return y};c=0;for(g=f.length;c<g;c++){if(f[c]=="M"){k=b.inArray(i[c],l);if(k!==-1&&k<12)i[c]=k+1}if(f[c]=="F"){k=b.inArray(i[c],l);if(k!==-1&&k>11)i[c]=k+1-12}if(f[c]=="a"){k=
b.inArray(i[c],a);if(k!==-1&&k<2&&i[c]==a[k]){i[c]=k;h.h=r(i[c],h.h)}}if(f[c]=="A"){k=b.inArray(i[c],a);if(k!==-1&&k>1&&i[c]==a[k]){i[c]=k-2;h.h=r(i[c],h.h)}}if(i[c]!==undefined)h[f[c].toLowerCase()]=parseInt(i[c],10)}h.m=parseInt(h.m,10)-1;c=h.y;if(c>=70&&c<=99)h.y=1900+h.y;else if(c>=0&&c<=69)h.y=2E3+h.y}return new Date(h.y,h.m,h.d,h.h,h.i,h.s,0)},jqID:function(f){return String(f).replace(/[!"#$%&'()*+,.\/:;<=>?@\[\\\]\^`{|}~]/g,"\\$&")},guid:1,uidPref:"jqg",randId:function(f){return(f?f:b.jgrid.uidPref)+
b.jgrid.guid++},getAccessor:function(f,i){var h,c,g=[],k;if(typeof i==="function")return i(f);h=f[i];if(h===undefined)try{if(typeof i==="string")g=i.split(".");if(k=g.length)for(h=f;h&&k--;){c=g.shift();h=h[c]}}catch(l){}return h},ajaxOptions:{},from:function(f){return new function(i,h){if(typeof i=="string")i=b.data(i);var c=this,g=i,k=true,l=false,a=h,r=/[\$,%]/g,x=null,y=null,H=0,L=false,M="",P=[],U=true;if(typeof i=="object"&&i.push){if(i.length>0)U=typeof i[0]!="object"?false:true}else throw"data provides is not an array";
this._hasData=function(){return g===null?false:g.length===0?false:true};this._getStr=function(o){var n=[];l&&n.push("jQuery.trim(");n.push("String("+o+")");l&&n.push(")");k||n.push(".toLowerCase()");return n.join("")};this._strComp=function(o){return typeof o=="string"?".toString()":""};this._group=function(o,n){return{field:o.toString(),unique:n,items:[]}};this._toStr=function(o){if(l)o=b.trim(o);k||(o=o.toLowerCase());return o=o.toString().replace(/\\/g,"\\\\").replace(/\"/g,'\\"')};this._funcLoop=
function(o){var n=[];b.each(g,function(s,D){n.push(o(D))});return n};this._append=function(o){var n;if(a===null)a="";else a+=M===""?" && ":M;for(n=0;n<H;n++)a+="(";if(L)a+="!";a+="("+o+")";L=false;M="";H=0};this._setCommand=function(o,n){x=o;y=n};this._resetNegate=function(){L=false};this._repeatCommand=function(o,n){if(x===null)return c;if(o!==null&&n!==null)return x(o,n);if(y===null)return x(o);if(!U)return x(o);return x(y,o)};this._equals=function(o,n){return c._compare(o,n,1)===0};this._compare=
function(o,n,s){if(s===undefined)s=1;if(o===undefined)o=null;if(n===undefined)n=null;if(o===null&&n===null)return 0;if(o===null&&n!==null)return 1;if(o!==null&&n===null)return-1;if(!k&&typeof o!=="number"&&typeof n!=="number"){o=String(o).toLowerCase();n=String(n).toLowerCase()}if(o<n)return-s;if(o>n)return s;return 0};this._performSort=function(){if(P.length!==0)g=c._doSort(g,0)};this._doSort=function(o,n){var s=P[n].by,D=P[n].dir,T=P[n].type,J=P[n].datefmt;if(n==P.length-1)return c._getOrder(o,
s,D,T,J);n++;s=c._getGroup(o,s,D,T,J);D=[];for(T=0;T<s.length;T++){J=c._doSort(s[T].items,n);for(var C=0;C<J.length;C++)D.push(J[C])}return D};this._getOrder=function(o,n,s,D,T){var J=[],C=[],ca=s=="a"?1:-1,V,fa;if(D===undefined)D="text";fa=D=="float"||D=="number"||D=="currency"||D=="numeric"?function(R){R=parseFloat(String(R).replace(r,""));return isNaN(R)?0:R}:D=="int"||D=="integer"?function(R){return R?parseFloat(String(R).replace(r,"")):0}:D=="date"||D=="datetime"?function(R){return b.jgrid.parseDate(T,
R).getTime()}:b.isFunction(D)?D:function(R){R||(R="");return b.trim(String(R).toUpperCase())};b.each(o,function(R,$){V=n!==""?b.jgrid.getAccessor($,n):$;if(V===undefined)V="";V=fa(V,$);C.push({vSort:V,index:R})});C.sort(function(R,$){R=R.vSort;$=$.vSort;return c._compare(R,$,ca)});D=0;for(var oa=o.length;D<oa;){s=C[D].index;J.push(o[s]);D++}return J};this._getGroup=function(o,n,s,D,T){var J=[],C=null,ca=null,V;b.each(c._getOrder(o,n,s,D,T),function(fa,oa){V=b.jgrid.getAccessor(oa,n);if(V===undefined)V=
"";if(!c._equals(ca,V)){ca=V;C!==null&&J.push(C);C=c._group(n,V)}C.items.push(oa)});C!==null&&J.push(C);return J};this.ignoreCase=function(){k=false;return c};this.useCase=function(){k=true;return c};this.trim=function(){l=true;return c};this.noTrim=function(){l=false;return c};this.execute=function(){var o=a,n=[];if(o===null)return c;b.each(g,function(){eval(o)&&n.push(this)});g=n;return c};this.data=function(){return g};this.select=function(o){c._performSort();if(!c._hasData())return[];c.execute();
if(b.isFunction(o)){var n=[];b.each(g,function(s,D){n.push(o(D))});return n}return g};this.hasMatch=function(){if(!c._hasData())return false;c.execute();return g.length>0};this.andNot=function(o,n,s){L=!L;return c.and(o,n,s)};this.orNot=function(o,n,s){L=!L;return c.or(o,n,s)};this.not=function(o,n,s){return c.andNot(o,n,s)};this.and=function(o,n,s){M=" && ";if(o===undefined)return c;return c._repeatCommand(o,n,s)};this.or=function(o,n,s){M=" || ";if(o===undefined)return c;return c._repeatCommand(o,
n,s)};this.orBegin=function(){H++;return c};this.orEnd=function(){if(a!==null)a+=")";return c};this.isNot=function(o){L=!L;return c.is(o)};this.is=function(o){c._append("this."+o);c._resetNegate();return c};this._compareValues=function(o,n,s,D,T){var J;J=U?"jQuery.jgrid.getAccessor(this,'"+n+"')":"this";if(s===undefined)s=null;var C=s,ca=T.stype===undefined?"text":T.stype;if(s!==null)switch(ca){case "int":case "integer":C=isNaN(Number(C))||C===""?"0":C;J="parseInt("+J+",10)";C="parseInt("+C+",10)";
break;case "float":case "number":case "numeric":C=String(C).replace(r,"");C=isNaN(Number(C))||C===""?"0":C;J="parseFloat("+J+")";C="parseFloat("+C+")";break;case "date":case "datetime":C=String(b.jgrid.parseDate(T.newfmt||"Y-m-d",C).getTime());J='jQuery.jgrid.parseDate("'+T.srcfmt+'",'+J+").getTime()";break;default:J=c._getStr(J);C=c._getStr('"'+c._toStr(C)+'"')}c._append(J+" "+D+" "+C);c._setCommand(o,n);c._resetNegate();return c};this.equals=function(o,n,s){return c._compareValues(c.equals,o,n,
"==",s)};this.notEquals=function(o,n,s){return c._compareValues(c.equals,o,n,"!==",s)};this.isNull=function(o,n,s){return c._compareValues(c.equals,o,null,"===",s)};this.greater=function(o,n,s){return c._compareValues(c.greater,o,n,">",s)};this.less=function(o,n,s){return c._compareValues(c.less,o,n,"<",s)};this.greaterOrEquals=function(o,n,s){return c._compareValues(c.greaterOrEquals,o,n,">=",s)};this.lessOrEquals=function(o,n,s){return c._compareValues(c.lessOrEquals,o,n,"<=",s)};this.startsWith=
function(o,n){var s=n===undefined||n===null?o:n;s=l?b.trim(s.toString()).length:s.toString().length;if(U)c._append(c._getStr("jQuery.jgrid.getAccessor(this,'"+o+"')")+".substr(0,"+s+") == "+c._getStr('"'+c._toStr(n)+'"'));else{s=l?b.trim(n.toString()).length:n.toString().length;c._append(c._getStr("this")+".substr(0,"+s+") == "+c._getStr('"'+c._toStr(o)+'"'))}c._setCommand(c.startsWith,o);c._resetNegate();return c};this.endsWith=function(o,n){var s=n===undefined||n===null?o:n;s=l?b.trim(s.toString()).length:
s.toString().length;U?c._append(c._getStr("jQuery.jgrid.getAccessor(this,'"+o+"')")+".substr("+c._getStr("jQuery.jgrid.getAccessor(this,'"+o+"')")+".length-"+s+","+s+') == "'+c._toStr(n)+'"'):c._append(c._getStr("this")+".substr("+c._getStr("this")+'.length-"'+c._toStr(o)+'".length,"'+c._toStr(o)+'".length) == "'+c._toStr(o)+'"');c._setCommand(c.endsWith,o);c._resetNegate();return c};this.contains=function(o,n){U?c._append(c._getStr("jQuery.jgrid.getAccessor(this,'"+o+"')")+'.indexOf("'+c._toStr(n)+
'",0) > -1'):c._append(c._getStr("this")+'.indexOf("'+c._toStr(o)+'",0) > -1');c._setCommand(c.contains,o);c._resetNegate();return c};this.groupBy=function(o,n,s,D){if(!c._hasData())return null;return c._getGroup(g,o,n,s,D)};this.orderBy=function(o,n,s,D){n=n===undefined||n===null?"a":b.trim(n.toString().toLowerCase());if(s===null||s===undefined)s="text";if(D===null||D===undefined)D="Y-m-d";if(n=="desc"||n=="descending")n="d";if(n=="asc"||n=="ascending")n="a";P.push({by:o,dir:n,type:s,datefmt:D});
return c};return c}(f,null)},extend:function(f){b.extend(b.fn.jqGrid,f);this.no_legacy_api||b.fn.extend(f)}});b.fn.jqGrid=function(f){if(typeof f=="string"){var i=b.jgrid.getAccessor(b.fn.jqGrid,f);if(!i)throw"jqGrid - No such method: "+f;var h=b.makeArray(arguments).slice(1);return i.apply(this,h)}return this.each(function(){if(!this.grid){var c=b.extend(true,{url:"",height:150,page:1,rowNum:20,rowTotal:null,records:0,pager:"",pgbuttons:true,pginput:true,colModel:[],rowList:[],colNames:[],sortorder:"asc",
sortname:"",datatype:"xml",mtype:"GET",altRows:false,selarrrow:[],savedRow:[],shrinkToFit:true,xmlReader:{},jsonReader:{},subGrid:false,subGridModel:[],reccount:0,lastpage:0,lastsort:0,selrow:null,beforeSelectRow:null,onSelectRow:null,onSortCol:null,ondblClickRow:null,onRightClickRow:null,onPaging:null,onSelectAll:null,loadComplete:null,gridComplete:null,loadError:null,loadBeforeSend:null,afterInsertRow:null,beforeRequest:null,beforeProcessing:null,onHeaderClick:null,viewrecords:false,loadonce:false,
multiselect:false,multikey:false,editurl:null,search:false,caption:"",hidegrid:true,hiddengrid:false,postData:{},userData:{},treeGrid:false,treeGridModel:"nested",treeReader:{},treeANode:-1,ExpandColumn:null,tree_root_level:0,prmNames:{page:"page",rows:"rows",sort:"sidx",order:"sord",search:"_search",nd:"nd",id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del",subgridid:"id",npage:null,totalrows:"totalrows"},forceFit:false,gridstate:"visible",cellEdit:false,cellsubmit:"remote",nv:0,loadui:"enable",
toolbar:[false,""],scroll:false,multiboxonly:false,deselectAfterSort:true,scrollrows:false,autowidth:false,scrollOffset:18,cellLayout:5,subGridWidth:20,multiselectWidth:20,gridview:false,rownumWidth:25,rownumbers:false,pagerpos:"center",recordpos:"right",footerrow:false,userDataOnFooter:false,hoverrows:true,altclass:"ui-priority-secondary",viewsortcols:[false,"vertical",true],resizeclass:"",autoencode:false,remapColumns:[],ajaxGridOptions:{},direction:"ltr",toppager:false,headertitles:false,scrollTimeout:40,
data:[],_index:{},grouping:false,groupingView:{groupField:[],groupOrder:[],groupText:[],groupColumnShow:[],groupSummary:[],showSummaryOnHide:false,sortitems:[],sortnames:[],groupDataSorted:false,summary:[],summaryval:[],plusicon:"ui-icon-circlesmall-plus",minusicon:"ui-icon-circlesmall-minus"},ignoreCase:false,cmTemplate:{},idPrefix:""},b.jgrid.defaults,f||{}),g={headers:[],cols:[],footers:[],dragStart:function(d,e,j){this.resizing={idx:d,startX:e.clientX,sOL:j[0]};this.hDiv.style.cursor="col-resize";
this.curGbox=b("#rs_m"+b.jgrid.jqID(c.id),"#gbox_"+b.jgrid.jqID(c.id));this.curGbox.css({display:"block",left:j[0],top:j[1],height:j[2]});b.isFunction(c.resizeStart)&&c.resizeStart.call(this,e,d);document.onselectstart=function(){return false}},dragMove:function(d){if(this.resizing){var e=d.clientX-this.resizing.startX;d=this.headers[this.resizing.idx];var j=c.direction==="ltr"?d.width+e:d.width-e,m;if(j>33){this.curGbox.css({left:this.resizing.sOL+e});if(c.forceFit===true){m=this.headers[this.resizing.idx+
c.nv];e=c.direction==="ltr"?m.width-e:m.width+e;if(e>33){d.newWidth=j;m.newWidth=e}}else{this.newWidth=c.direction==="ltr"?c.tblwidth+e:c.tblwidth-e;d.newWidth=j}}}},dragEnd:function(){this.hDiv.style.cursor="default";if(this.resizing){var d=this.resizing.idx,e=this.headers[d].newWidth||this.headers[d].width;e=parseInt(e,10);this.resizing=false;b("#rs_m"+b.jgrid.jqID(c.id)).css("display","none");c.colModel[d].width=e;this.headers[d].width=e;this.headers[d].el.style.width=e+"px";this.cols[d].style.width=
e+"px";if(this.footers.length>0)this.footers[d].style.width=e+"px";if(c.forceFit===true){e=this.headers[d+c.nv].newWidth||this.headers[d+c.nv].width;this.headers[d+c.nv].width=e;this.headers[d+c.nv].el.style.width=e+"px";this.cols[d+c.nv].style.width=e+"px";if(this.footers.length>0)this.footers[d+c.nv].style.width=e+"px";c.colModel[d+c.nv].width=e}else{c.tblwidth=this.newWidth||c.tblwidth;b("table:first",this.bDiv).css("width",c.tblwidth+"px");b("table:first",this.hDiv).css("width",c.tblwidth+"px");
this.hDiv.scrollLeft=this.bDiv.scrollLeft;if(c.footerrow){b("table:first",this.sDiv).css("width",c.tblwidth+"px");this.sDiv.scrollLeft=this.bDiv.scrollLeft}}b.isFunction(c.resizeStop)&&c.resizeStop.call(this,e,d)}this.curGbox=null;document.onselectstart=function(){return true}},populateVisible:function(){g.timer&&clearTimeout(g.timer);g.timer=null;var d=b(g.bDiv).height();if(d){var e=b("table:first",g.bDiv),j,m;if(e[0].rows.length)try{m=(j=e[0].rows[1])?b(j).outerHeight()||g.prevRowHeight:g.prevRowHeight}catch(p){m=
g.prevRowHeight}if(m){g.prevRowHeight=m;var B=c.rowNum;j=g.scrollTop=g.bDiv.scrollTop;var t=Math.round(e.position().top)-j,E=t+e.height();m*=B;var v,z,u;if(E<d&&t<=0&&(c.lastpage===undefined||parseInt((E+j+m-1)/m,10)<=c.lastpage)){z=parseInt((d-E+m-1)/m,10);if(E>=0||z<2||c.scroll===true){v=Math.round((E+j)/m)+1;t=-1}else t=1}if(t>0){v=parseInt(j/m,10)+1;z=parseInt((j+d)/m,10)+2-v;u=true}if(z)if(!(c.lastpage&&v>c.lastpage||c.lastpage==1||v===c.page&&v===c.lastpage))if(g.hDiv.loading)g.timer=setTimeout(g.populateVisible,
c.scrollTimeout);else{c.page=v;if(u){g.selectionPreserver(e[0]);g.emptyRows(g.bDiv,false,false)}g.populate(z)}}}},scrollGrid:function(d){if(c.scroll){var e=g.bDiv.scrollTop;if(g.scrollTop===undefined)g.scrollTop=0;if(e!=g.scrollTop){g.scrollTop=e;g.timer&&clearTimeout(g.timer);g.timer=setTimeout(g.populateVisible,c.scrollTimeout)}}g.hDiv.scrollLeft=g.bDiv.scrollLeft;if(c.footerrow)g.sDiv.scrollLeft=g.bDiv.scrollLeft;d&&d.stopPropagation()},selectionPreserver:function(d){var e=d.p,j=e.selrow,m=e.selarrrow?
b.makeArray(e.selarrrow):null,p=d.grid.bDiv.scrollLeft,B=e.gridComplete;e.gridComplete=function(){e.selrow=null;e.selarrrow=[];if(e.multiselect&&m&&m.length>0)for(var t=0;t<m.length;t++)m[t]!=j&&b(d).jqGrid("setSelection",m[t],false);j&&b(d).jqGrid("setSelection",j,false);d.grid.bDiv.scrollLeft=p;e.gridComplete=B;e.gridComplete&&B()}}};if(this.tagName.toUpperCase()!="TABLE")alert("Element is not a table");else{b(this).empty().attr("tabindex","1");this.p=c;this.p.useProp=!!b.fn.prop;var k,l,a;if(this.p.colNames.length===
0)for(k=0;k<this.p.colModel.length;k++)this.p.colNames[k]=this.p.colModel[k].label||this.p.colModel[k].name;if(this.p.colNames.length!==this.p.colModel.length)alert(b.jgrid.errors.model);else{var r=b("<div class='ui-jqgrid-view'></div>"),x,y=b.browser.msie?true:false,H=b.browser.webkit||b.browser.safari?true:false;a=this;a.p.direction=b.trim(a.p.direction.toLowerCase());if(b.inArray(a.p.direction,["ltr","rtl"])==-1)a.p.direction="ltr";l=a.p.direction;b(r).insertBefore(this);b(this).appendTo(r).removeClass("scroll");
var L=b("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");b(L).insertBefore(r).attr({id:"gbox_"+this.id,dir:l});b(r).appendTo(L).attr("id","gview_"+this.id);x=y&&b.browser.version<=6?'<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>':"";b("<div class='ui-widget-overlay jqgrid-overlay' id='lui_"+this.id+"'></div>").append(x).insertBefore(r);b("<div class='loading ui-state-default ui-state-active' id='load_"+
this.id+"'>"+this.p.loadtext+"</div>").insertBefore(r);b(this).attr({cellspacing:"0",cellpadding:"0",border:"0",role:"grid","aria-multiselectable":!!this.p.multiselect,"aria-labelledby":"gbox_"+this.id});var M=function(d,e){d=parseInt(d,10);return isNaN(d)?e?e:0:d},P=function(d,e,j,m,p,B){var t=a.p.colModel[d],E=t.align,v='style="',z=t.classes,u=t.name,q=[];if(E)v+="text-align:"+E+";";if(t.hidden===true)v+="display:none;";if(e===0)v+="width: "+g.headers[d].width+"px;";else if(t.cellattr&&b.isFunction(t.cellattr))if((d=
t.cellattr.call(a,p,j,m,t,B))&&typeof d==="string"){d=d.replace(/style/i,"style").replace(/title/i,"title");if(d.indexOf("title")>-1)t.title=false;if(d.indexOf("class")>-1)z=undefined;q=d.split("style");if(q.length===2){q[1]=b.trim(q[1].replace("=",""));if(q[1].indexOf("'")===0||q[1].indexOf('"')===0)q[1]=q[1].substring(1);v+=q[1].replace(/'/gi,'"')}else v+='"'}if(!q.length){q[0]="";v+='"'}v+=(z!==undefined?' class="'+z+'"':"")+(t.title&&j?' title="'+b.jgrid.stripHtml(j)+'"':"");v+=' aria-describedby="'+
a.p.id+"_"+u+'"';return v+q[0]},U=function(d){return d===undefined||d===null||d===""?"&#160;":a.p.autoencode?b.jgrid.htmlEncode(d):d+""},o=function(d,e,j,m,p){var B=a.p.colModel[j];if(typeof B.formatter!=="undefined"){d={rowId:d,colModel:B,gid:a.p.id,pos:j};e=b.isFunction(B.formatter)?B.formatter.call(a,e,d,m,p):b.fmatter?b.fn.fmatter(B.formatter,e,d,m,p):U(e)}else e=U(e);return e},n=function(d,e,j,m,p){e=o(d,e,j,p,"add");return'<td role="gridcell" '+P(j,m,e,p,d,true)+">"+e+"</td>"},s=function(d,
e,j){var m='<input role="checkbox" type="checkbox" id="jqg_'+a.p.id+"_"+d+'" class="cbox" name="jqg_'+a.p.id+"_"+d+'"/>';return'<td role="gridcell" '+P(e,j,"",null,d,true)+">"+m+"</td>"},D=function(d,e,j,m){j=(parseInt(j,10)-1)*parseInt(m,10)+1+e;return'<td role="gridcell" class="ui-state-default jqgrid-rownum" '+P(d,e,j,null,e,true)+">"+j+"</td>"},T=function(d){var e,j=[],m=0,p;for(p=0;p<a.p.colModel.length;p++){e=a.p.colModel[p];if(e.name!=="cb"&&e.name!=="subgrid"&&e.name!=="rn"){j[m]=d=="local"?
e.name:d=="xml"?e.xmlmap||e.name:e.jsonmap||e.name;m++}}return j},J=function(d){var e=a.p.remapColumns;if(!e||!e.length)e=b.map(a.p.colModel,function(j,m){return m});if(d)e=b.map(e,function(j){return j<d?null:j-d});return e},C=function(d,e,j){if(a.p.deepempty)b("#"+b.jgrid.jqID(a.p.id)+" tbody:first tr:gt(0)").remove();else{var m=b("#"+b.jgrid.jqID(a.p.id)+" tbody:first tr:first")[0];b("#"+b.jgrid.jqID(a.p.id)+" tbody:first").empty().append(m)}if(e&&a.p.scroll){b(">div:first",d).css({height:"auto"}).children("div:first").css({height:0,
display:"none"});d.scrollTop=0}if(j===true)if(a.p.treeGrid===true){a.p.data=[];a.p._index={}}},ca=function(){var d=a.p.data.length,e,j,m;e=a.p.rownumbers===true?1:0;j=a.p.multiselect===true?1:0;m=a.p.subGrid===true?1:0;e=a.p.keyIndex===false||a.p.loadonce===true?a.p.localReader.id:a.p.colModel[a.p.keyIndex+j+m+e].name;for(j=0;j<d;j++){m=b.jgrid.getAccessor(a.p.data[j],e);a.p._index[m]=j}},V=function(d,e,j,m,p){var B=new Date,t=a.p.datatype!="local"&&a.p.loadonce||a.p.datatype=="xmlstring",E=a.p.datatype==
"local"?"local":"xml";if(t){a.p.data=[];a.p._index={};a.p.localReader.id="_id_"}a.p.reccount=0;if(b.isXMLDoc(d)){if(a.p.treeANode===-1&&!a.p.scroll){C(e,false,true);j=1}else j=j>1?j:1;var v,z,u=0,q,F=0,S=0,N=0,K,O=[],Y,I={},w,A,G=[],ia=a.p.altRows===true?" "+a.p.altclass:"";a.p.xmlReader.repeatitems||(O=T(E));K=a.p.keyIndex===false?a.p.xmlReader.id:a.p.keyIndex;if(O.length>0&&!isNaN(K)){if(a.p.remapColumns&&a.p.remapColumns.length)K=b.inArray(K,a.p.remapColumns);K=O[K]}E=(K+"").indexOf("[")===-1?
O.length?function(ga,aa){return b(K,ga).text()||aa}:function(ga,aa){return b(a.p.xmlReader.cell,ga).eq(K).text()||aa}:function(ga,aa){return ga.getAttribute(K.replace(/[\[\]]/g,""))||aa};a.p.userData={};b(a.p.xmlReader.page,d).each(function(){a.p.page=this.textContent||this.text||0});b(a.p.xmlReader.total,d).each(function(){a.p.lastpage=this.textContent||this.text;if(a.p.lastpage===undefined)a.p.lastpage=1});b(a.p.xmlReader.records,d).each(function(){a.p.records=this.textContent||this.text||0});b(a.p.xmlReader.userdata,
d).each(function(){a.p.userData[this.getAttribute("name")]=b(this).text()});(d=b(a.p.xmlReader.root+" "+a.p.xmlReader.row,d))||(d=[]);var ba=d.length,W=0,Z={},ha;if(d&&ba){ha=parseInt(a.p.rowNum,10);var pa=a.p.scroll?b.jgrid.randId():1;if(p)ha*=p+1;p=b.isFunction(a.p.afterInsertRow);var qa="";if(a.p.grouping&&a.p.groupingView.groupCollapse===true)qa=' style="display:none;"';for(;W<ba;){w=d[W];A=E(w,pa+W);A=a.p.idPrefix+A;v=j===0?0:j+1;v=(v+W)%2==1?ia:"";G.push("<tr"+qa+' id="'+A+'" tabindex="-1" role="row" class ="ui-widget-content jqgrow ui-row-'+
a.p.direction+""+v+'">');if(a.p.rownumbers===true){G.push(D(0,W,a.p.page,a.p.rowNum));N=1}if(a.p.multiselect===true){G.push(s(A,N,W));F=1}if(a.p.subGrid===true){G.push(b(a).jqGrid("addSubGridCell",F+N,W+j));S=1}if(a.p.xmlReader.repeatitems){Y||(Y=J(F+S+N));var Ba=b(a.p.xmlReader.cell,w);b.each(Y,function(ga){var aa=Ba[this];if(!aa)return false;q=aa.textContent||aa.text;I[a.p.colModel[ga+F+S+N].name]=q;G.push(n(A,q,ga+F+S+N,W+j,w))})}else for(v=0;v<O.length;v++){q=b(O[v],w).text();I[a.p.colModel[v+
F+S+N].name]=q;G.push(n(A,q,v+F+S+N,W+j,w))}G.push("</tr>");if(a.p.grouping){v=a.p.groupingView.groupField.length;for(var xa=[],ya=0;ya<v;ya++)xa.push(I[a.p.groupingView.groupField[ya]]);Z=b(a).jqGrid("groupingPrepare",G,xa,Z,I);G=[]}if(t||a.p.treeGrid===true){I._id_=A;a.p.data.push(I);a.p._index[A]=a.p.data.length-1}if(a.p.gridview===false){b("tbody:first",e).append(G.join(""));p&&a.p.afterInsertRow.call(a,A,I,w);G=[]}I={};u++;W++;if(u==ha)break}}if(a.p.gridview===true){z=a.p.treeANode>-1?a.p.treeANode:
0;if(a.p.grouping){b(a).jqGrid("groupingRender",Z,a.p.colModel.length);Z=null}else a.p.treeGrid===true&&z>0?b(a.rows[z]).after(G.join("")):b("tbody:first",e).append(G.join(""))}if(a.p.subGrid===true)try{b(a).jqGrid("addSubGrid",F+N)}catch(Ia){}a.p.totaltime=new Date-B;if(u>0)if(a.p.records===0)a.p.records=ba;G=null;if(a.p.treeGrid===true)try{b(a).jqGrid("setTreeNode",z+1,u+z+1)}catch(Ja){}if(!a.p.treeGrid&&!a.p.scroll)a.grid.bDiv.scrollTop=0;a.p.reccount=u;a.p.treeANode=-1;a.p.userDataOnFooter&&b(a).jqGrid("footerData",
"set",a.p.userData,true);if(t){a.p.records=ba;a.p.lastpage=Math.ceil(ba/ha)}m||a.updatepager(false,true);if(t)for(;u<ba;){w=d[u];A=E(w,u);A=a.p.idPrefix+A;if(a.p.xmlReader.repeatitems){Y||(Y=J(F+S+N));var Fa=b(a.p.xmlReader.cell,w);b.each(Y,function(ga){var aa=Fa[this];if(!aa)return false;q=aa.textContent||aa.text;I[a.p.colModel[ga+F+S+N].name]=q})}else for(v=0;v<O.length;v++){q=b(O[v],w).text();I[a.p.colModel[v+F+S+N].name]=q}I._id_=A;a.p.data.push(I);a.p._index[A]=a.p.data.length-1;I={};u++}}},
fa=function(d,e,j,m,p){var B=new Date;if(d){if(a.p.treeANode===-1&&!a.p.scroll){C(e,false,true);j=1}else j=j>1?j:1;var t,E=a.p.datatype!="local"&&a.p.loadonce||a.p.datatype=="jsonstring";if(E){a.p.data=[];a.p._index={};a.p.localReader.id="_id_"}a.p.reccount=0;if(a.p.datatype=="local"){e=a.p.localReader;t="local"}else{e=a.p.jsonReader;t="json"}var v=0,z,u,q=[],F,S=0,N=0,K=0,O,Y,I={},w,A,G=[],ia=a.p.altRows===true?" "+a.p.altclass:"";a.p.page=b.jgrid.getAccessor(d,e.page)||0;O=b.jgrid.getAccessor(d,
e.total);a.p.lastpage=O===undefined?1:O;a.p.records=b.jgrid.getAccessor(d,e.records)||0;a.p.userData=b.jgrid.getAccessor(d,e.userdata)||{};e.repeatitems||(F=q=T(t));t=a.p.keyIndex===false?e.id:a.p.keyIndex;if(q.length>0&&!isNaN(t)){if(a.p.remapColumns&&a.p.remapColumns.length)t=b.inArray(t,a.p.remapColumns);t=q[t]}(Y=b.jgrid.getAccessor(d,e.root))||(Y=[]);O=Y.length;d=0;var ba=parseInt(a.p.rowNum,10),W=a.p.scroll?b.jgrid.randId():1;if(p)ba*=p+1;var Z=b.isFunction(a.p.afterInsertRow),ha={},pa="";if(a.p.grouping&&
a.p.groupingView.groupCollapse===true)pa=' style="display:none;"';for(;d<O;){p=Y[d];A=b.jgrid.getAccessor(p,t);if(A===undefined){A=W+d;if(q.length===0)if(e.cell)A=b.jgrid.getAccessor(p,e.cell)[t]||A}A=a.p.idPrefix+A;z=j===1?0:j;z=(z+d)%2==1?ia:"";G.push("<tr"+pa+' id="'+A+'" tabindex="-1" role="row" class= "ui-widget-content jqgrow ui-row-'+a.p.direction+""+z+'">');if(a.p.rownumbers===true){G.push(D(0,d,a.p.page,a.p.rowNum));K=1}if(a.p.multiselect){G.push(s(A,K,d));S=1}if(a.p.subGrid){G.push(b(a).jqGrid("addSubGridCell",
S+K,d+j));N=1}if(e.repeatitems){if(e.cell)p=b.jgrid.getAccessor(p,e.cell);F||(F=J(S+N+K))}for(u=0;u<F.length;u++){z=b.jgrid.getAccessor(p,F[u]);G.push(n(A,z,u+S+N+K,d+j,p));I[a.p.colModel[u+S+N+K].name]=z}G.push("</tr>");if(a.p.grouping){z=a.p.groupingView.groupField.length;u=[];for(var qa=0;qa<z;qa++)u.push(I[a.p.groupingView.groupField[qa]]);ha=b(a).jqGrid("groupingPrepare",G,u,ha,I);G=[]}if(E||a.p.treeGrid===true){I._id_=A;a.p.data.push(I);a.p._index[A]=a.p.data.length-1}if(a.p.gridview===false){b("#"+
b.jgrid.jqID(a.p.id)+" tbody:first").append(G.join(""));Z&&a.p.afterInsertRow.call(a,A,I,p);G=[]}I={};v++;d++;if(v==ba)break}if(a.p.gridview===true){w=a.p.treeANode>-1?a.p.treeANode:0;if(a.p.grouping)b(a).jqGrid("groupingRender",ha,a.p.colModel.length);else a.p.treeGrid===true&&w>0?b(a.rows[w]).after(G.join("")):b("#"+b.jgrid.jqID(a.p.id)+" tbody:first").append(G.join(""))}if(a.p.subGrid===true)try{b(a).jqGrid("addSubGrid",S+K)}catch(Ba){}a.p.totaltime=new Date-B;if(v>0)if(a.p.records===0)a.p.records=
O;if(a.p.treeGrid===true)try{b(a).jqGrid("setTreeNode",w+1,v+w+1)}catch(xa){}if(!a.p.treeGrid&&!a.p.scroll)a.grid.bDiv.scrollTop=0;a.p.reccount=v;a.p.treeANode=-1;a.p.userDataOnFooter&&b(a).jqGrid("footerData","set",a.p.userData,true);if(E){a.p.records=O;a.p.lastpage=Math.ceil(O/ba)}m||a.updatepager(false,true);if(E)for(;v<O&&Y[v];){p=Y[v];A=b.jgrid.getAccessor(p,t);if(A===undefined){A=W+v;if(q.length===0)if(e.cell)A=b.jgrid.getAccessor(p,e.cell)[t]||A}if(p){A=a.p.idPrefix+A;if(e.repeatitems){if(e.cell)p=
b.jgrid.getAccessor(p,e.cell);F||(F=J(S+N+K))}for(u=0;u<F.length;u++){z=b.jgrid.getAccessor(p,F[u]);I[a.p.colModel[u+S+N+K].name]=z}I._id_=A;a.p.data.push(I);a.p._index[A]=a.p.data.length-1;I={}}v++}}},oa=function(){function d(w){var A=0,G,ia,ba,W,Z;if(w.groups!==undefined){(ia=w.groups.length&&w.groupOp.toString().toUpperCase()==="OR")&&q.orBegin();for(G=0;G<w.groups.length;G++){A>0&&ia&&q.or();try{d(w.groups[G])}catch(ha){alert(ha)}A++}ia&&q.orEnd()}if(w.rules!==undefined){if(A>0){ia=q.select();
q=b.jgrid.from(ia);if(a.p.ignoreCase)q=q.ignoreCase()}try{(ba=w.rules.length&&w.groupOp.toString().toUpperCase()==="OR")&&q.orBegin();for(G=0;G<w.rules.length;G++){Z=w.rules[G];W=w.groupOp.toString().toUpperCase();if(u[Z.op]&&Z.field){if(A>0&&W&&W==="OR")q=q.or();q=u[Z.op](q,W)(Z.field,Z.data,m[Z.field])}A++}ba&&q.orEnd()}catch(pa){alert(pa)}}}var e,j=false,m={},p=[],B=[],t,E,v;if(b.isArray(a.p.data)){var z=a.p.grouping?a.p.groupingView:false;b.each(a.p.colModel,function(){E=this.sorttype||"text";
if(E=="date"||E=="datetime"){if(this.formatter&&typeof this.formatter==="string"&&this.formatter=="date"){t=this.formatoptions&&this.formatoptions.srcformat?this.formatoptions.srcformat:b.jgrid.formatter.date.srcformat;v=this.formatoptions&&this.formatoptions.newformat?this.formatoptions.newformat:b.jgrid.formatter.date.newformat}else t=v=this.datefmt||"Y-m-d";m[this.name]={stype:E,srcfmt:t,newfmt:v}}else m[this.name]={stype:E,srcfmt:"",newfmt:""};if(a.p.grouping&&this.name==z.groupField[0]){var w=
this.name;if(typeof this.index!="undefined")w=this.index;p[0]=m[w];B.push(w)}if(!j&&(this.index==a.p.sortname||this.name==a.p.sortname)){e=this.name;j=true}});if(a.p.treeGrid)b(a).jqGrid("SortTree",e,a.p.sortorder,m[e].stype,m[e].srcfmt);else{var u={eq:function(w){return w.equals},ne:function(w){return w.notEquals},lt:function(w){return w.less},le:function(w){return w.lessOrEquals},gt:function(w){return w.greater},ge:function(w){return w.greaterOrEquals},cn:function(w){return w.contains},nc:function(w,
A){return A==="OR"?w.orNot().contains:w.andNot().contains},bw:function(w){return w.startsWith},bn:function(w,A){return A==="OR"?w.orNot().startsWith:w.andNot().startsWith},en:function(w,A){return A==="OR"?w.orNot().endsWith:w.andNot().endsWith},ew:function(w){return w.endsWith},ni:function(w,A){return A==="OR"?w.orNot().equals:w.andNot().equals},"in":function(w){return w.equals},nu:function(w){return w.isNull},nn:function(w,A){return A==="OR"?w.orNot().isNull:w.andNot().isNull}},q=b.jgrid.from(a.p.data);
if(a.p.ignoreCase)q=q.ignoreCase();if(a.p.search===true){var F=a.p.postData.filters;if(F){if(typeof F=="string")F=b.jgrid.parse(F);d(F)}else try{q=u[a.p.postData.searchOper](q)(a.p.postData.searchField,a.p.postData.searchString,m[a.p.postData.searchField])}catch(S){}}if(a.p.grouping){q.orderBy(B,z.groupOrder[0],p[0].stype,p[0].srcfmt);z.groupDataSorted=true}if(e&&a.p.sortorder&&j)a.p.sortorder.toUpperCase()=="DESC"?q.orderBy(a.p.sortname,"d",m[e].stype,m[e].srcfmt):q.orderBy(a.p.sortname,"a",m[e].stype,
m[e].srcfmt);F=q.select();var N=parseInt(a.p.rowNum,10),K=F.length,O=parseInt(a.p.page,10),Y=Math.ceil(K/N),I={};F=F.slice((O-1)*N,O*N);m=q=null;I[a.p.localReader.total]=Y;I[a.p.localReader.page]=O;I[a.p.localReader.records]=K;I[a.p.localReader.root]=F;I[a.p.localReader.userdata]=a.p.userData;F=null;return I}}},R=function(){a.grid.hDiv.loading=true;if(!a.p.hiddengrid)switch(a.p.loadui){case "enable":b("#load_"+b.jgrid.jqID(a.p.id)).show();break;case "block":b("#lui_"+b.jgrid.jqID(a.p.id)).show();
b("#load_"+b.jgrid.jqID(a.p.id)).show()}},$=function(){a.grid.hDiv.loading=false;switch(a.p.loadui){case "enable":b("#load_"+b.jgrid.jqID(a.p.id)).hide();break;case "block":b("#lui_"+b.jgrid.jqID(a.p.id)).hide();b("#load_"+b.jgrid.jqID(a.p.id)).hide()}},ja=function(d){if(!a.grid.hDiv.loading){var e=a.p.scroll&&d===false,j={},m,p=a.p.prmNames;if(a.p.page<=0)a.p.page=1;if(p.search!==null)j[p.search]=a.p.search;if(p.nd!==null)j[p.nd]=(new Date).getTime();if(p.rows!==null)j[p.rows]=a.p.rowNum;if(p.page!==
null)j[p.page]=a.p.page;if(p.sort!==null)j[p.sort]=a.p.sortname;if(p.order!==null)j[p.order]=a.p.sortorder;if(a.p.rowTotal!==null&&p.totalrows!==null)j[p.totalrows]=a.p.rowTotal;var B=a.p.loadComplete,t=b.isFunction(B);t||(B=null);var E=0;d=d||1;if(d>1)if(p.npage!==null){j[p.npage]=d;E=d-1;d=1}else B=function(z){a.p.page++;a.grid.hDiv.loading=false;t&&a.p.loadComplete.call(a,z);ja(d-1)};else p.npage!==null&&delete a.p.postData[p.npage];if(a.p.grouping){b(a).jqGrid("groupingSetup");if(a.p.groupingView.groupDataSorted===
true)j[p.sort]=a.p.groupingView.groupField[0]+" "+a.p.groupingView.groupOrder[0]+", "+j[p.sort]}b.extend(a.p.postData,j);var v=!a.p.scroll?1:a.rows.length-1;if(b.isFunction(a.p.datatype))a.p.datatype.call(a,a.p.postData,"load_"+a.p.id);else{if(b.isFunction(a.p.beforeRequest)){j=a.p.beforeRequest.call(a);if(j===undefined)j=true;if(j===false)return}m=a.p.datatype.toLowerCase();switch(m){case "json":case "jsonp":case "xml":case "script":b.ajax(b.extend({url:a.p.url,type:a.p.mtype,dataType:m,data:b.isFunction(a.p.serializeGridData)?
a.p.serializeGridData.call(a,a.p.postData):a.p.postData,success:function(z,u,q){b.isFunction(a.p.beforeProcessing)&&a.p.beforeProcessing.call(a,z,u,q);m==="xml"?V(z,a.grid.bDiv,v,d>1,E):fa(z,a.grid.bDiv,v,d>1,E);B&&B.call(a,z);e&&a.grid.populateVisible();if(a.p.loadonce||a.p.treeGrid)a.p.datatype="local";d===1&&$()},error:function(z,u,q){b.isFunction(a.p.loadError)&&a.p.loadError.call(a,z,u,q);d===1&&$()},beforeSend:function(z,u){var q=true;if(b.isFunction(a.p.loadBeforeSend))q=a.p.loadBeforeSend.call(a,
z,u);if(q===undefined)q=true;if(q===false)return false;else R()}},b.jgrid.ajaxOptions,a.p.ajaxGridOptions));break;case "xmlstring":R();j=b.jgrid.stringToDoc(a.p.datastr);V(j,a.grid.bDiv);t&&a.p.loadComplete.call(a,j);a.p.datatype="local";a.p.datastr=null;$();break;case "jsonstring":R();j=typeof a.p.datastr=="string"?b.jgrid.parse(a.p.datastr):a.p.datastr;fa(j,a.grid.bDiv);t&&a.p.loadComplete.call(a,j);a.p.datatype="local";a.p.datastr=null;$();break;case "local":case "clientside":R();a.p.datatype=
"local";j=oa();fa(j,a.grid.bDiv,v,d>1,E);B&&B.call(a,j);e&&a.grid.populateVisible();$()}}}};x=function(d,e){var j="",m="<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>",p="",B,t,E,v,z=function(u){var q;if(b.isFunction(a.p.onPaging))q=a.p.onPaging.call(a,u);a.p.selrow=null;if(a.p.multiselect){a.p.selarrrow=[];b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv)[a.p.useProp?"prop":"attr"]("checked",false)}a.p.savedRow=[];if(q=="stop")return false;return true};
d=d.substr(1);e+="_"+d;B="pg_"+d;t=d+"_left";E=d+"_center";v=d+"_right";b("#"+b.jgrid.jqID(d)).append("<div id='"+B+"' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;height:100%;' role='row'><tbody><tr><td id='"+t+"' align='left'></td><td id='"+E+"' align='center' style='white-space:pre;'></td><td id='"+v+"' align='right'></td></tr></tbody></table></div>").attr("dir","ltr");if(a.p.rowList.length>0){p=
"<td dir='"+l+"'>";p+="<select class='ui-pg-selbox' role='listbox'>";for(t=0;t<a.p.rowList.length;t++)p+='<option role="option" value="'+a.p.rowList[t]+'"'+(a.p.rowNum==a.p.rowList[t]?' selected="selected"':"")+">"+a.p.rowList[t]+"</option>";p+="</select></td>"}if(l=="rtl")m+=p;if(a.p.pginput===true)j="<td dir='"+l+"'>"+b.jgrid.format(a.p.pgtext||"","<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>","<span id='sp_1_"+b.jgrid.jqID(d)+"'></span>")+"</td>";if(a.p.pgbuttons===
true){t=["first"+e,"prev"+e,"next"+e,"last"+e];l=="rtl"&&t.reverse();m+="<td id='"+t[0]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>";m+="<td id='"+t[1]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>";m+=j!==""?"<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>"+j+"<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>":
"";m+="<td id='"+t[2]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>";m+="<td id='"+t[3]+"' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>"}else if(j!=="")m+=j;if(l=="ltr")m+=p;m+="</tr></tbody></table>";a.p.viewrecords===true&&b("td#"+d+"_"+a.p.recordpos,"#"+B).append("<div dir='"+l+"' style='text-align:"+a.p.recordpos+"' class='ui-paging-info'></div>");b("td#"+d+"_"+a.p.pagerpos,"#"+B).append(m);p=b(".ui-jqgrid").css("font-size")||
"11px";b(document.body).append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+p+";visibility:hidden;' ></div>");m=b(m).clone().appendTo("#testpg").width();b("#testpg").remove();if(m>0){if(j!=="")m+=50;b("td#"+d+"_"+a.p.pagerpos,"#"+B).width(m)}a.p._nvtd=[];a.p._nvtd[0]=m?Math.floor((a.p.width-m)/2):Math.floor(a.p.width/3);a.p._nvtd[1]=0;m=null;b(".ui-pg-selbox","#"+B).bind("change",function(){a.p.page=Math.round(a.p.rowNum*(a.p.page-1)/this.value-0.5)+1;a.p.rowNum=
this.value;if(e)b(".ui-pg-selbox",a.p.pager).val(this.value);else a.p.toppager&&b(".ui-pg-selbox",a.p.toppager).val(this.value);if(!z("records"))return false;ja();return false});if(a.p.pgbuttons===true){b(".ui-pg-button","#"+B).hover(function(){if(b(this).hasClass("ui-state-disabled"))this.style.cursor="default";else{b(this).addClass("ui-state-hover");this.style.cursor="pointer"}},function(){if(!b(this).hasClass("ui-state-disabled")){b(this).removeClass("ui-state-hover");this.style.cursor="default"}});
b("#first"+b.jgrid.jqID(e)+", #prev"+b.jgrid.jqID(e)+", #next"+b.jgrid.jqID(e)+", #last"+b.jgrid.jqID(e)).click(function(){var u=M(a.p.page,1),q=M(a.p.lastpage,1),F=false,S=true,N=true,K=true,O=true;if(q===0||q===1)O=K=N=S=false;else if(q>1&&u>=1)if(u===1)N=S=false;else{if(!(u>1&&u<q))if(u===q)O=K=false}else if(q>1&&u===0){O=K=false;u=q-1}if(this.id==="first"+e&&S){a.p.page=1;F=true}if(this.id==="prev"+e&&N){a.p.page=u-1;F=true}if(this.id==="next"+e&&K){a.p.page=u+1;F=true}if(this.id==="last"+e&&
O){a.p.page=q;F=true}if(F){if(!z(this.id))return false;ja()}return false})}a.p.pginput===true&&b("input.ui-pg-input","#"+B).keypress(function(u){if((u.charCode?u.charCode:u.keyCode?u.keyCode:0)==13){a.p.page=b(this).val()>0?b(this).val():a.p.page;if(!z("user"))return false;ja();return false}return this})};var Ca=function(d,e,j,m){if(a.p.colModel[e].sortable)if(!(a.p.savedRow.length>0)){if(!j){if(a.p.lastsort==e)if(a.p.sortorder=="asc")a.p.sortorder="desc";else{if(a.p.sortorder=="desc")a.p.sortorder=
"asc"}else a.p.sortorder=a.p.colModel[e].firstsortorder||"asc";a.p.page=1}if(m)if(a.p.lastsort==e&&a.p.sortorder==m&&!j)return;else a.p.sortorder=m;j=a.grid.headers[a.p.lastsort].el;m=a.grid.headers[e].el;b("span.ui-grid-ico-sort",j).addClass("ui-state-disabled");b(j).attr("aria-selected","false");b("span.ui-icon-"+a.p.sortorder,m).removeClass("ui-state-disabled");b(m).attr("aria-selected","true");if(!a.p.viewsortcols[0])if(a.p.lastsort!=e){b("span.s-ico",j).hide();b("span.s-ico",m).show()}d=d.substring(5+
a.p.id.length+1);a.p.sortname=a.p.colModel[e].index||d;j=a.p.sortorder;if(b.isFunction(a.p.onSortCol))if(a.p.onSortCol.call(a,d,e,j)=="stop"){a.p.lastsort=e;return}if(a.p.datatype=="local")a.p.deselectAfterSort&&b(a).jqGrid("resetSelection");else{a.p.selrow=null;if(a.p.multiselect)b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv)[a.p.useProp?"prop":"attr"]("checked",false);a.p.selarrrow=[];a.p.savedRow=[]}if(a.p.scroll){j=a.grid.bDiv.scrollLeft;C(a.grid.bDiv,true,false);a.grid.hDiv.scrollLeft=j}a.p.subGrid&&
a.p.datatype=="local"&&b("td.sgexpanded","#"+b.jgrid.jqID(a.p.id)).each(function(){b(this).trigger("click")});ja();a.p.lastsort=e;if(a.p.sortname!=d&&e)a.p.lastsort=e}},Ga=function(d){var e,j={},m=H?0:a.p.cellLayout;for(e=j[0]=j[1]=j[2]=0;e<=d;e++)if(a.p.colModel[e].hidden===false)j[0]+=a.p.colModel[e].width+m;if(a.p.direction=="rtl")j[0]=a.p.width-j[0];j[0]-=a.grid.bDiv.scrollLeft;if(b(a.grid.cDiv).is(":visible"))j[1]+=b(a.grid.cDiv).height()+parseInt(b(a.grid.cDiv).css("padding-top"),10)+parseInt(b(a.grid.cDiv).css("padding-bottom"),
10);if(a.p.toolbar[0]===true&&(a.p.toolbar[1]=="top"||a.p.toolbar[1]=="both"))j[1]+=b(a.grid.uDiv).height()+parseInt(b(a.grid.uDiv).css("border-top-width"),10)+parseInt(b(a.grid.uDiv).css("border-bottom-width"),10);if(a.p.toppager)j[1]+=b(a.grid.topDiv).height()+parseInt(b(a.grid.topDiv).css("border-bottom-width"),10);j[2]+=b(a.grid.bDiv).height()+b(a.grid.hDiv).height();return j},Da=function(d){var e,j=a.grid.headers,m=b.jgrid.getCellIndex(d);for(e=0;e<j.length;e++)if(d===j[e].el){m=e;break}return m};
this.p.id=this.id;if(b.inArray(a.p.multikey,["shiftKey","altKey","ctrlKey"])==-1)a.p.multikey=false;a.p.keyIndex=false;for(k=0;k<a.p.colModel.length;k++){a.p.colModel[k]=b.extend(true,{},a.p.cmTemplate,a.p.colModel[k].template||{},a.p.colModel[k]);if(a.p.keyIndex===false&&a.p.colModel[k].key===true)a.p.keyIndex=k}a.p.sortorder=a.p.sortorder.toLowerCase();if(a.p.grouping===true){a.p.scroll=false;a.p.rownumbers=false;a.p.subGrid=false;a.p.treeGrid=false;a.p.gridview=true}if(this.p.treeGrid===true){try{b(this).jqGrid("setTreeGrid")}catch(Ka){}if(a.p.datatype!=
"local")a.p.localReader={id:"_id_"}}if(this.p.subGrid)try{b(a).jqGrid("setSubGrid")}catch(La){}if(this.p.multiselect){this.p.colNames.unshift("<input role='checkbox' id='cb_"+this.p.id+"' class='cbox' type='checkbox'/>");this.p.colModel.unshift({name:"cb",width:H?a.p.multiselectWidth+a.p.cellLayout:a.p.multiselectWidth,sortable:false,resizable:false,hidedlg:true,search:false,align:"center",fixed:true})}if(this.p.rownumbers){this.p.colNames.unshift("");this.p.colModel.unshift({name:"rn",width:a.p.rownumWidth,
sortable:false,resizable:false,hidedlg:true,search:false,align:"center",fixed:true})}a.p.xmlReader=b.extend(true,{root:"rows",row:"row",page:"rows>page",total:"rows>total",records:"rows>records",repeatitems:true,cell:"cell",id:"[id]",userdata:"userdata",subgrid:{root:"rows",row:"row",repeatitems:true,cell:"cell"}},a.p.xmlReader);a.p.jsonReader=b.extend(true,{root:"rows",page:"page",total:"total",records:"records",repeatitems:true,cell:"cell",id:"id",userdata:"userdata",subgrid:{root:"rows",repeatitems:true,
cell:"cell"}},a.p.jsonReader);a.p.localReader=b.extend(true,{root:"rows",page:"page",total:"total",records:"records",repeatitems:false,cell:"cell",id:"id",userdata:"userdata",subgrid:{root:"rows",repeatitems:true,cell:"cell"}},a.p.localReader);if(a.p.scroll){a.p.pgbuttons=false;a.p.pginput=false;a.p.rowList=[]}a.p.data.length&&ca();var da="<thead><tr class='ui-jqgrid-labels' role='rowheader'>",Ea,ma,sa,ra,ta,X,Q,na;ma=na="";if(a.p.shrinkToFit===true&&a.p.forceFit===true)for(k=a.p.colModel.length-
1;k>=0;k--)if(!a.p.colModel[k].hidden){a.p.colModel[k].resizable=false;break}if(a.p.viewsortcols[1]=="horizontal"){na=" ui-i-asc";ma=" ui-i-desc"}Ea=y?"class='ui-th-div-ie'":"";na="<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc"+na+" ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-"+l+"'></span>";na+="<span sort='desc' class='ui-grid-ico-sort ui-icon-desc"+ma+" ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-"+l+"'></span></span>";for(k=
0;k<this.p.colNames.length;k++){ma=a.p.headertitles?' title="'+b.jgrid.stripHtml(a.p.colNames[k])+'"':"";da+="<th id='"+a.p.id+"_"+a.p.colModel[k].name+"' role='columnheader' class='ui-state-default ui-th-column ui-th-"+l+"'"+ma+">";ma=a.p.colModel[k].index||a.p.colModel[k].name;da+="<div id='jqgh_"+a.p.id+"_"+a.p.colModel[k].name+"' "+Ea+">"+a.p.colNames[k];a.p.colModel[k].width=a.p.colModel[k].width?parseInt(a.p.colModel[k].width,10):150;if(typeof a.p.colModel[k].title!=="boolean")a.p.colModel[k].title=
true;if(ma==a.p.sortname)a.p.lastsort=k;da+=na+"</div></th>"}da+="</tr></thead>";na=null;b(this).append(da);b("thead tr:first th",this).hover(function(){b(this).addClass("ui-state-hover")},function(){b(this).removeClass("ui-state-hover")});if(this.p.multiselect){var za=[],ua;b("#cb_"+b.jgrid.jqID(a.p.id),this).bind("click",function(){a.p.selarrrow=[];if(this.checked){b(a.rows).each(function(d){if(d>0)if(!b(this).hasClass("ui-subgrid")&&!b(this).hasClass("jqgroup")&&!b(this).hasClass("ui-state-disabled")){b("#jqg_"+
b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(this.id))[a.p.useProp?"prop":"attr"]("checked",true);b(this).addClass("ui-state-highlight").attr("aria-selected","true");a.p.selarrrow.push(this.id);a.p.selrow=this.id}});ua=true;za=[]}else{b(a.rows).each(function(d){if(d>0)if(!b(this).hasClass("ui-subgrid")&&!b(this).hasClass("ui-state-disabled")){b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(this.id))[a.p.useProp?"prop":"attr"]("checked",false);b(this).removeClass("ui-state-highlight").attr("aria-selected",
"false");za.push(this.id)}});a.p.selrow=null;ua=false}if(b.isFunction(a.p.onSelectAll))a.p.onSelectAll.call(a,ua?a.p.selarrrow:za,ua)})}if(a.p.autowidth===true){da=b(L).innerWidth();a.p.width=da>0?da:"nw"}(function(){var d=0,e=H?0:a.p.cellLayout,j=0,m,p=a.p.scrollOffset,B,t=false,E,v=0,z=0,u;b.each(a.p.colModel,function(){if(typeof this.hidden==="undefined")this.hidden=false;this.widthOrg=B=M(this.width,0);if(this.hidden===false){d+=B+e;if(this.fixed)v+=B+e;else j++;z++}});if(isNaN(a.p.width))a.p.width=
g.width=d;else g.width=a.p.width;a.p.tblwidth=d;if(a.p.shrinkToFit===false&&a.p.forceFit===true)a.p.forceFit=false;if(a.p.shrinkToFit===true&&j>0){E=g.width-e*j-v;if(!isNaN(a.p.height)){E-=p;t=true}d=0;b.each(a.p.colModel,function(q){if(this.hidden===false&&!this.fixed){this.width=B=Math.round(E*this.width/(a.p.tblwidth-e*j-v));d+=B;m=q}});u=0;if(t){if(g.width-v-(d+e*j)!==p)u=g.width-v-(d+e*j)-p}else if(!t&&Math.abs(g.width-v-(d+e*j))!==1)u=g.width-v-(d+e*j);a.p.colModel[m].width+=u;a.p.tblwidth=
d+u+e*j+v;if(a.p.tblwidth>a.p.width){a.p.colModel[m].width-=a.p.tblwidth-parseInt(a.p.width,10);a.p.tblwidth=a.p.width}}})();b(L).css("width",g.width+"px").append("<div class='ui-jqgrid-resize-mark' id='rs_m"+a.p.id+"'>&#160;</div>");b(r).css("width",g.width+"px");da=b("thead:first",a).get(0);var va="";if(a.p.footerrow)va+="<table role='grid' style='width:"+a.p.tblwidth+"px' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-"+
l+"'>";r=b("tr:first",da);var wa="<tr class='jqgfirstrow' role='row' style='height:auto'>";a.p.disableClick=false;b("th",r).each(function(d){sa=a.p.colModel[d].width;if(typeof a.p.colModel[d].resizable==="undefined")a.p.colModel[d].resizable=true;if(a.p.colModel[d].resizable){ra=document.createElement("span");b(ra).html("&#160;").addClass("ui-jqgrid-resize ui-jqgrid-resize-"+l);b.browser.opera||b(ra).css("cursor","col-resize");b(this).addClass(a.p.resizeclass)}else ra="";b(this).css("width",sa+"px").prepend(ra);
var e="";if(a.p.colModel[d].hidden){b(this).css("display","none");e="display:none;"}wa+="<td role='gridcell' style='height:0px;width:"+sa+"px;"+e+"'></td>";g.headers[d]={width:sa,el:this};ta=a.p.colModel[d].sortable;if(typeof ta!=="boolean")ta=a.p.colModel[d].sortable=true;e=a.p.colModel[d].name;e=="cb"||e=="subgrid"||e=="rn"||a.p.viewsortcols[2]&&b("div",this).addClass("ui-jqgrid-sortable");if(ta)if(a.p.viewsortcols[0]){b("div span.s-ico",this).show();d==a.p.lastsort&&b("div span.ui-icon-"+a.p.sortorder,
this).removeClass("ui-state-disabled")}else if(d==a.p.lastsort){b("div span.s-ico",this).show();b("div span.ui-icon-"+a.p.sortorder,this).removeClass("ui-state-disabled")}if(a.p.footerrow)va+="<td role='gridcell' "+P(d,0,"",null,"",false)+">&#160;</td>"}).mousedown(function(d){if(b(d.target).closest("th>span.ui-jqgrid-resize").length==1){var e=Da(this);if(a.p.forceFit===true){var j=a.p,m=e,p;for(p=e+1;p<a.p.colModel.length;p++)if(a.p.colModel[p].hidden!==true){m=p;break}j.nv=m-e}g.dragStart(e,d,Ga(e));
return false}}).click(function(d){if(a.p.disableClick)return a.p.disableClick=false;var e="th>div.ui-jqgrid-sortable",j,m;a.p.viewsortcols[2]||(e="th>div>span>span.ui-grid-ico-sort");d=b(d.target).closest(e);if(d.length==1){e=Da(this);if(!a.p.viewsortcols[2]){j=true;m=d.attr("sort")}Ca(b("div",this)[0].id,e,j,m);return false}});if(a.p.sortable&&b.fn.sortable)try{b(a).jqGrid("sortableColumns",r)}catch(Ma){}if(a.p.footerrow)va+="</tr></tbody></table>";wa+="</tr>";this.appendChild(document.createElement("tbody"));
b(this).addClass("ui-jqgrid-btable").append(wa);wa=null;r=b("<table class='ui-jqgrid-htable' style='width:"+a.p.tblwidth+"px' role='grid' aria-labelledby='gbox_"+this.id+"' cellspacing='0' cellpadding='0' border='0'></table>").append(da);var ea=a.p.caption&&a.p.hiddengrid===true?true:false;k=b("<div class='ui-jqgrid-hbox"+(l=="rtl"?"-rtl":"")+"'></div>");da=null;g.hDiv=document.createElement("div");b(g.hDiv).css({width:g.width+"px"}).addClass("ui-state-default ui-jqgrid-hdiv").append(k);b(k).append(r);
r=null;ea&&b(g.hDiv).hide();if(a.p.pager){if(typeof a.p.pager=="string"){if(a.p.pager.substr(0,1)!="#")a.p.pager="#"+a.p.pager}else a.p.pager="#"+b(a.p.pager).attr("id");b(a.p.pager).css({width:g.width+"px"}).appendTo(L).addClass("ui-state-default ui-jqgrid-pager ui-corner-bottom");ea&&b(a.p.pager).hide();x(a.p.pager,"")}a.p.cellEdit===false&&a.p.hoverrows===true&&b(a).bind("mouseover",function(d){Q=b(d.target).closest("tr.jqgrow");b(Q).attr("class")!=="ui-subgrid"&&b(Q).addClass("ui-state-hover")}).bind("mouseout",
function(d){Q=b(d.target).closest("tr.jqgrow");b(Q).removeClass("ui-state-hover")});var ka,la;b(a).before(g.hDiv).click(function(d){X=d.target;Q=b(X,a.rows).closest("tr.jqgrow");if(b(Q).length===0||Q[0].className.indexOf("ui-state-disabled")>-1)return this;var e=b(X).hasClass("cbox"),j=true;if(b.isFunction(a.p.beforeSelectRow))j=a.p.beforeSelectRow.call(a,Q[0].id,d);if(X.tagName=="A"||(X.tagName=="INPUT"||X.tagName=="TEXTAREA"||X.tagName=="OPTION"||X.tagName=="SELECT")&&!e)return this;if(j===true){if(a.p.cellEdit===
true)if(a.p.multiselect&&e)b(a).jqGrid("setSelection",Q[0].id,true);else{ka=Q[0].rowIndex;la=b.jgrid.getCellIndex(X);try{b(a).jqGrid("editCell",ka,la,true)}catch(m){}}else if(a.p.multikey)if(d[a.p.multikey])b(a).jqGrid("setSelection",Q[0].id,true);else{if(a.p.multiselect&&e){e=b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+Q[0].id).is(":checked");b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+Q[0].id)[a.p.useProp?"prop":"attr"]("checked",e)}}else{if(a.p.multiselect&&a.p.multiboxonly)if(!e){b(a.p.selarrrow).each(function(p,
B){var t=a.rows.namedItem(B);b(t).removeClass("ui-state-highlight");b("#jqg_"+b.jgrid.jqID(a.p.id)+"_"+b.jgrid.jqID(B))[a.p.useProp?"prop":"attr"]("checked",false)});a.p.selarrrow=[];b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv)[a.p.useProp?"prop":"attr"]("checked",false)}b(a).jqGrid("setSelection",Q[0].id,true)}if(b.isFunction(a.p.onCellSelect)){ka=Q[0].id;la=b.jgrid.getCellIndex(X);a.p.onCellSelect.call(a,ka,la,b(X).html(),d)}}return this}).bind("reloadGrid",function(d,e){if(a.p.treeGrid===true)a.p.datatype=
a.p.treedatatype;e&&e.current&&a.grid.selectionPreserver(a);if(a.p.datatype=="local"){b(a).jqGrid("resetSelection");a.p.data.length&&ca()}else if(!a.p.treeGrid){a.p.selrow=null;if(a.p.multiselect){a.p.selarrrow=[];b("#cb_"+b.jgrid.jqID(a.p.id),a.grid.hDiv)[a.p.useProp?"prop":"attr"]("checked",false)}a.p.savedRow=[]}a.p.scroll&&C(a.grid.bDiv,true,false);if(e&&e.page){var j=e.page;if(j>a.p.lastpage)j=a.p.lastpage;if(j<1)j=1;a.p.page=j;a.grid.bDiv.scrollTop=a.grid.prevRowHeight?(j-1)*a.grid.prevRowHeight*
a.p.rowNum:0}if(a.grid.prevRowHeight&&a.p.scroll){delete a.p.lastpage;a.grid.populateVisible()}else a.grid.populate();return false});b.isFunction(this.p.ondblClickRow)&&b(this).dblclick(function(d){X=d.target;Q=b(X,a.rows).closest("tr.jqgrow");if(b(Q).length===0)return false;ka=Q[0].rowIndex;la=b.jgrid.getCellIndex(X);a.p.ondblClickRow.call(a,b(Q).attr("id"),ka,la,d);return false});b.isFunction(this.p.onRightClickRow)&&b(this).bind("contextmenu",function(d){X=d.target;Q=b(X,a.rows).closest("tr.jqgrow");
if(b(Q).length===0)return false;a.p.multiselect||b(a).jqGrid("setSelection",Q[0].id,true);ka=Q[0].rowIndex;la=b.jgrid.getCellIndex(X);a.p.onRightClickRow.call(a,b(Q).attr("id"),ka,la,d);return false});g.bDiv=document.createElement("div");if(y)if(String(a.p.height).toLowerCase()==="auto")a.p.height="100%";b(g.bDiv).append(b('<div style="position:relative;'+(y&&b.browser.version<8?"height:0.01%;":"")+'"></div>').append("<div></div>").append(this)).addClass("ui-jqgrid-bdiv").css({height:a.p.height+(isNaN(a.p.height)?
"":"px"),width:g.width+"px"}).scroll(g.scrollGrid);b("table:first",g.bDiv).css({width:a.p.tblwidth+"px"});if(y){b("tbody",this).size()==2&&b("tbody:gt(0)",this).remove();a.p.multikey&&b(g.bDiv).bind("selectstart",function(){return false})}else a.p.multikey&&b(g.bDiv).bind("mousedown",function(){return false});ea&&b(g.bDiv).hide();g.cDiv=document.createElement("div");var Aa=a.p.hidegrid===true?b("<a role='link' href='javascript:void(0)'/>").addClass("ui-jqgrid-titlebar-close HeaderButton").hover(function(){Aa.addClass("ui-state-hover")},
function(){Aa.removeClass("ui-state-hover")}).append("<span class='ui-icon ui-icon-circle-triangle-n'></span>").css(l=="rtl"?"left":"right","0px"):"";b(g.cDiv).append(Aa).append("<span class='ui-jqgrid-title"+(l=="rtl"?"-rtl":"")+"'>"+a.p.caption+"</span>").addClass("ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix");b(g.cDiv).insertBefore(g.hDiv);if(a.p.toolbar[0]){g.uDiv=document.createElement("div");if(a.p.toolbar[1]=="top")b(g.uDiv).insertBefore(g.hDiv);else a.p.toolbar[1]==
"bottom"&&b(g.uDiv).insertAfter(g.hDiv);if(a.p.toolbar[1]=="both"){g.ubDiv=document.createElement("div");b(g.uDiv).insertBefore(g.hDiv).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id);b(g.ubDiv).insertAfter(g.hDiv).addClass("ui-userdata ui-state-default").attr("id","tb_"+this.id);ea&&b(g.ubDiv).hide()}else b(g.uDiv).width(g.width).addClass("ui-userdata ui-state-default").attr("id","t_"+this.id);ea&&b(g.uDiv).hide()}if(a.p.toppager){a.p.toppager=b.jgrid.jqID(a.p.id)+"_toppager";g.topDiv=
b("<div id='"+a.p.toppager+"'></div>")[0];a.p.toppager="#"+a.p.toppager;b(g.topDiv).insertBefore(g.hDiv).addClass("ui-state-default ui-jqgrid-toppager").width(g.width);x(a.p.toppager,"_t")}if(a.p.footerrow){g.sDiv=b("<div class='ui-jqgrid-sdiv'></div>")[0];k=b("<div class='ui-jqgrid-hbox"+(l=="rtl"?"-rtl":"")+"'></div>");b(g.sDiv).append(k).insertAfter(g.hDiv).width(g.width);b(k).append(va);g.footers=b(".ui-jqgrid-ftable",g.sDiv)[0].rows[0].cells;if(a.p.rownumbers)g.footers[0].className="ui-state-default jqgrid-rownum";
ea&&b(g.sDiv).hide()}k=null;if(a.p.caption){var Ha=a.p.datatype;if(a.p.hidegrid===true){b(".ui-jqgrid-titlebar-close",g.cDiv).click(function(d){var e=b.isFunction(a.p.onHeaderClick),j=".ui-jqgrid-bdiv, .ui-jqgrid-hdiv, .ui-jqgrid-pager, .ui-jqgrid-sdiv",m,p=this;if(a.p.toolbar[0]===true){if(a.p.toolbar[1]=="both")j+=", #"+b(g.ubDiv).attr("id");j+=", #"+b(g.uDiv).attr("id")}m=b(j,"#gview_"+b.jgrid.jqID(a.p.id)).length;if(a.p.gridstate=="visible")b(j,"#gbox_"+b.jgrid.jqID(a.p.id)).slideUp("fast",function(){m--;
if(m===0){b("span",p).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");a.p.gridstate="hidden";b("#gbox_"+b.jgrid.jqID(a.p.id)).hasClass("ui-resizable")&&b(".ui-resizable-handle","#gbox_"+b.jgrid.jqID(a.p.id)).hide();if(e)ea||a.p.onHeaderClick.call(a,a.p.gridstate,d)}});else a.p.gridstate=="hidden"&&b(j,"#gbox_"+b.jgrid.jqID(a.p.id)).slideDown("fast",function(){m--;if(m===0){b("span",p).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");if(ea){a.p.datatype=
Ha;ja();ea=false}a.p.gridstate="visible";b("#gbox_"+b.jgrid.jqID(a.p.id)).hasClass("ui-resizable")&&b(".ui-resizable-handle","#gbox_"+b.jgrid.jqID(a.p.id)).show();if(e)ea||a.p.onHeaderClick.call(a,a.p.gridstate,d)}});return false});if(ea){a.p.datatype="local";b(".ui-jqgrid-titlebar-close",g.cDiv).trigger("click")}}}else b(g.cDiv).hide();b(g.hDiv).after(g.bDiv).mousemove(function(d){if(g.resizing){g.dragMove(d);return false}});b(".ui-jqgrid-labels",g.hDiv).bind("selectstart",function(){return false});
b(document).mouseup(function(){if(g.resizing){g.dragEnd();return false}return true});a.formatCol=P;a.sortData=Ca;a.updatepager=function(d,e){var j,m,p,B,t,E,v,z="",u=a.p.pager?"_"+b.jgrid.jqID(a.p.pager.substr(1)):"",q=a.p.toppager?"_"+a.p.toppager.substr(1):"";p=parseInt(a.p.page,10)-1;if(p<0)p=0;p*=parseInt(a.p.rowNum,10);t=p+a.p.reccount;if(a.p.scroll){j=b("tbody:first > tr:gt(0)",a.grid.bDiv);p=t-j.length;a.p.reccount=j.length;if(m=j.outerHeight()||a.grid.prevRowHeight){j=p*m;m*=parseInt(a.p.records,
10);b(">div:first",a.grid.bDiv).css({height:m}).children("div:first").css({height:j,display:j?"":"none"})}a.grid.bDiv.scrollLeft=a.grid.hDiv.scrollLeft}z=a.p.pager?a.p.pager:"";z+=a.p.toppager?z?","+a.p.toppager:a.p.toppager:"";if(z){v=b.jgrid.formatter.integer||{};j=M(a.p.page);m=M(a.p.lastpage);b(".selbox",z)[this.p.useProp?"prop":"attr"]("disabled",false);if(a.p.pginput===true){b(".ui-pg-input",z).val(a.p.page);B=a.p.toppager?"#sp_1"+u+",#sp_1"+q:"#sp_1"+u;b(B).html(b.fmatter?b.fmatter.util.NumberFormat(a.p.lastpage,
v):a.p.lastpage)}if(a.p.viewrecords)if(a.p.reccount===0)b(".ui-paging-info",z).html(a.p.emptyrecords);else{B=p+1;E=a.p.records;if(b.fmatter){B=b.fmatter.util.NumberFormat(B,v);t=b.fmatter.util.NumberFormat(t,v);E=b.fmatter.util.NumberFormat(E,v)}b(".ui-paging-info",z).html(b.jgrid.format(a.p.recordtext,B,t,E))}if(a.p.pgbuttons===true){if(j<=0)j=m=0;if(j==1||j===0){b("#first"+u+", #prev"+u).addClass("ui-state-disabled").removeClass("ui-state-hover");a.p.toppager&&b("#first_t"+q+", #prev_t"+q).addClass("ui-state-disabled").removeClass("ui-state-hover")}else{b("#first"+
u+", #prev"+u).removeClass("ui-state-disabled");a.p.toppager&&b("#first_t"+q+", #prev_t"+q).removeClass("ui-state-disabled")}if(j==m||j===0){b("#next"+u+", #last"+u).addClass("ui-state-disabled").removeClass("ui-state-hover");a.p.toppager&&b("#next_t"+q+", #last_t"+q).addClass("ui-state-disabled").removeClass("ui-state-hover")}else{b("#next"+u+", #last"+u).removeClass("ui-state-disabled");a.p.toppager&&b("#next_t"+q+", #last_t"+q).removeClass("ui-state-disabled")}}}d===true&&a.p.rownumbers===true&&
b("td.jqgrid-rownum",a.rows).each(function(F){b(this).html(p+1+F)});e&&a.p.jqgdnd&&b(a).jqGrid("gridDnD","updateDnD");b.isFunction(a.p.gridComplete)&&a.p.gridComplete.call(a)};a.refreshIndex=ca;a.formatter=function(d,e,j,m,p){return o(d,e,j,m,p)};b.extend(g,{populate:ja,emptyRows:C});this.grid=g;a.addXmlData=function(d){V(d,a.grid.bDiv)};a.addJSONData=function(d){fa(d,a.grid.bDiv)};this.grid.cols=this.rows[0].cells;ja();a.p.hiddengrid=false;b(window).unload(function(){a=null})}}}})};b.jgrid.extend({getGridParam:function(f){var i=
this[0];if(i&&i.grid)return f?typeof i.p[f]!="undefined"?i.p[f]:null:i.p},setGridParam:function(f){return this.each(function(){this.grid&&typeof f==="object"&&b.extend(true,this.p,f)})},getDataIDs:function(){var f=[],i=0,h,c=0;this.each(function(){if((h=this.rows.length)&&h>0)for(;i<h;){if(b(this.rows[i]).hasClass("jqgrow")){f[c]=this.rows[i].id;c++}i++}});return f},setSelection:function(f,i){return this.each(function(){function h(a){var r=b(c.grid.bDiv)[0].clientHeight,x=b(c.grid.bDiv)[0].scrollTop,
y=c.rows[a].offsetTop;a=c.rows[a].clientHeight;if(y+a>=r+x)b(c.grid.bDiv)[0].scrollTop=y-(r+x)+a+x;else if(y<r+x)if(y<x)b(c.grid.bDiv)[0].scrollTop=y}var c=this,g,k,l;if(f!==undefined){i=i===false?false:true;k=c.rows.namedItem(f+"");if(!(!k||k.className.indexOf("ui-state-disabled")>-1)){if(c.p.scrollrows===true){g=c.rows.namedItem(f).rowIndex;g>=0&&h(g)}if(c.p.multiselect){b("#cb_"+b.jgrid.jqID(c.p.id),c.grid.hDiv)[c.p.useProp?"prop":"attr"]("checked",false);c.p.selrow=k.id;l=b.inArray(c.p.selrow,
c.p.selarrrow);if(l===-1){k.className!=="ui-subgrid"&&b(k).addClass("ui-state-highlight").attr("aria-selected","true");g=true;b("#jqg_"+b.jgrid.jqID(c.p.id)+"_"+b.jgrid.jqID(c.p.selrow))[c.p.useProp?"prop":"attr"]("checked",g);c.p.selarrrow.push(c.p.selrow)}else{k.className!=="ui-subgrid"&&b(k).removeClass("ui-state-highlight").attr("aria-selected","false");g=false;b("#jqg_"+b.jgrid.jqID(c.p.id)+"_"+b.jgrid.jqID(c.p.selrow))[c.p.useProp?"prop":"attr"]("checked",g);c.p.selarrrow.splice(l,1);l=c.p.selarrrow[0];
c.p.selrow=l===undefined?null:l}c.p.onSelectRow&&i&&c.p.onSelectRow.call(c,k.id,g)}else if(k.className!=="ui-subgrid"){if(c.p.selrow!=k.id){b(c.rows.namedItem(c.p.selrow)).removeClass("ui-state-highlight").attr({"aria-selected":"false",tabindex:"-1"});b(k).addClass("ui-state-highlight").attr({"aria-selected":"true",tabindex:"0"});g=true}else g=false;c.p.selrow=k.id;c.p.onSelectRow&&i&&c.p.onSelectRow.call(c,k.id,g)}}}})},resetSelection:function(f){return this.each(function(){var i=this,h,c;if(typeof f!==
"undefined"){c=f===i.p.selrow?i.p.selrow:f;b("#"+b.jgrid.jqID(i.p.id)+" tbody:first tr#"+b.jgrid.jqID(c)).removeClass("ui-state-highlight").attr("aria-selected","false");if(i.p.multiselect){b("#jqg_"+b.jgrid.jqID(i.p.id)+"_"+b.jgrid.jqID(c))[i.p.useProp?"prop":"attr"]("checked",false);b("#cb_"+b.jgrid.jqID(i.p.id))[i.p.useProp?"prop":"attr"]("checked",false)}c=null}else if(i.p.multiselect){b(i.p.selarrrow).each(function(g,k){h=i.rows.namedItem(k);b(h).removeClass("ui-state-highlight").attr("aria-selected",
"false");b("#jqg_"+b.jgrid.jqID(i.p.id)+"_"+b.jgrid.jqID(k))[i.p.useProp?"prop":"attr"]("checked",false)});b("#cb_"+b.jgrid.jqID(i.p.id))[i.p.useProp?"prop":"attr"]("checked",false);i.p.selarrrow=[]}else if(i.p.selrow){b("#"+b.jgrid.jqID(i.p.id)+" tbody:first tr#"+b.jgrid.jqID(i.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected","false");i.p.selrow=null}if(i.p.cellEdit===true)if(parseInt(i.p.iCol,10)>=0&&parseInt(i.p.iRow,10)>=0){b("td:eq("+i.p.iCol+")",i.rows[i.p.iRow]).removeClass("edit-cell ui-state-highlight");
b(i.rows[i.p.iRow]).removeClass("selected-row ui-state-hover")}i.p.savedRow=[]})},getRowData:function(f){var i={},h,c=false,g,k=0;this.each(function(){var l=this,a,r;if(typeof f=="undefined"){c=true;h=[];g=l.rows.length}else{r=l.rows.namedItem(f);if(!r)return i;g=2}for(;k<g;){if(c)r=l.rows[k];if(b(r).hasClass("jqgrow")){b("td",r).each(function(x){a=l.p.colModel[x].name;if(a!=="cb"&&a!=="subgrid"&&a!=="rn")if(l.p.treeGrid===true&&a==l.p.ExpandColumn)i[a]=b.jgrid.htmlDecode(b("span:first",this).html());
else try{i[a]=b.unformat(this,{rowId:r.id,colModel:l.p.colModel[x]},x)}catch(y){i[a]=b.jgrid.htmlDecode(b(this).html())}});if(c){h.push(i);i={}}}k++}});return h?h:i},delRowData:function(f){var i=false,h,c;this.each(function(){if(h=this.rows.namedItem(f)){b(h).remove();this.p.records--;this.p.reccount--;this.updatepager(true,false);i=true;if(this.p.multiselect){c=b.inArray(f,this.p.selarrrow);c!=-1&&this.p.selarrrow.splice(c,1)}if(f==this.p.selrow)this.p.selrow=null}else return false;if(this.p.datatype==
"local"){var g=this.p._index[f];if(typeof g!="undefined"){this.p.data.splice(g,1);this.refreshIndex()}}if(this.p.altRows===true&&i){var k=this.p.altclass;b(this.rows).each(function(l){l%2==1?b(this).addClass(k):b(this).removeClass(k)})}});return i},setRowData:function(f,i,h){var c,g=true,k;this.each(function(){if(!this.grid)return false;var l=this,a,r,x=typeof h,y={};r=l.rows.namedItem(f);if(!r)return false;if(i)try{b(this.p.colModel).each(function(P){c=this.name;if(i[c]!==undefined){y[c]=this.formatter&&
typeof this.formatter==="string"&&this.formatter=="date"?b.unformat.date(i[c],this):i[c];a=l.formatter(f,i[c],P,i,"edit");k=this.title?{title:b.jgrid.stripHtml(a)}:{};l.p.treeGrid===true&&c==l.p.ExpandColumn?b("td:eq("+P+") > span:first",r).html(a).attr(k):b("td:eq("+P+")",r).html(a).attr(k)}});if(l.p.datatype=="local"){var H=l.p._index[f];if(l.p.treeGrid)for(var L in l.p.treeReader)y.hasOwnProperty(l.p.treeReader[L])&&delete y[l.p.treeReader[L]];if(typeof H!="undefined")l.p.data[H]=b.extend(true,
l.p.data[H],y);y=null}}catch(M){g=false}if(g)if(x==="string")b(r).addClass(h);else x==="object"&&b(r).css(h)});return g},addRowData:function(f,i,h,c){h||(h="last");var g=false,k,l,a,r,x,y,H,L,M="",P,U,o,n,s;if(i){if(b.isArray(i)){P=true;h="last";U=f}else{i=[i];P=false}this.each(function(){var D=i.length;x=this.p.rownumbers===true?1:0;a=this.p.multiselect===true?1:0;r=this.p.subGrid===true?1:0;if(!P)if(typeof f!="undefined")f+="";else{f=b.jgrid.randId();if(this.p.keyIndex!==false){U=this.p.colModel[this.p.keyIndex+
a+r+x].name;if(typeof i[0][U]!="undefined")f=i[0][U]}}o=this.p.altclass;for(var T=0,J="",C={},ca=b.isFunction(this.p.afterInsertRow)?true:false;T<D;){n=i[T];l="";if(P){try{f=n[U]}catch(V){f=b.jgrid.randId()}J=this.p.altRows===true?(this.rows.length-1)%2===0?o:"":""}f=this.p.idPrefix+f;if(x){M=this.formatCol(0,1,"",null,f,true);l+='<td role="gridcell" aria-describedby="'+this.p.id+'_rn" class="ui-state-default jqgrid-rownum" '+M+">0</td>"}if(a){L='<input role="checkbox" type="checkbox" id="jqg_'+this.p.id+
"_"+f+'" class="cbox"/>';M=this.formatCol(x,1,"",null,f,true);l+='<td role="gridcell" aria-describedby="'+this.p.id+'_cb" '+M+">"+L+"</td>"}if(r)l+=b(this).jqGrid("addSubGridCell",a+x,1);for(H=a+r+x;H<this.p.colModel.length;H++){s=this.p.colModel[H];k=s.name;C[k]=s.formatter&&typeof s.formatter==="string"&&s.formatter=="date"?b.unformat.date(n[k],s):n[k];L=this.formatter(f,b.jgrid.getAccessor(n,k),H,n,"edit");M=this.formatCol(H,1,L,n,f,true);l+='<td role="gridcell" aria-describedby="'+this.p.id+"_"+
k+'" '+M+">"+L+"</td>"}l='<tr id="'+f+'" role="row" tabindex="-1" class="ui-widget-content jqgrow ui-row-'+this.p.direction+" "+J+'">'+l+"</tr>";if(this.rows.length===0)b("table:first",this.grid.bDiv).append(l);else switch(h){case "last":b(this.rows[this.rows.length-1]).after(l);y=this.rows.length-1;break;case "first":b(this.rows[0]).after(l);y=1;break;case "after":if(y=this.rows.namedItem(c))b(this.rows[y.rowIndex+1]).hasClass("ui-subgrid")?b(this.rows[y.rowIndex+1]).after(l):b(y).after(l);y++;break;
case "before":if(y=this.rows.namedItem(c)){b(y).before(l);y=y.rowIndex}y--}this.p.subGrid===true&&b(this).jqGrid("addSubGrid",a+x,y);this.p.records++;this.p.reccount++;ca&&this.p.afterInsertRow.call(this,f,n,n);T++;if(this.p.datatype=="local"){C[this.p.localReader.id]=f;this.p._index[f]=this.p.data.length;this.p.data.push(C);C={}}}if(this.p.altRows===true&&!P)if(h=="last")(this.rows.length-1)%2==1&&b(this.rows[this.rows.length-1]).addClass(o);else b(this.rows).each(function(fa){fa%2==1?b(this).addClass(o):
b(this).removeClass(o)});this.updatepager(true,true);g=true})}return g},footerData:function(f,i,h){function c(r){for(var x in r)if(r.hasOwnProperty(x))return false;return true}var g,k=false,l={},a;if(typeof f=="undefined")f="get";if(typeof h!="boolean")h=true;f=f.toLowerCase();this.each(function(){var r=this,x;if(!r.grid||!r.p.footerrow)return false;if(f=="set")if(c(i))return false;k=true;b(this.p.colModel).each(function(y){g=this.name;if(f=="set"){if(i[g]!==undefined){x=h?r.formatter("",i[g],y,i,
"edit"):i[g];a=this.title?{title:b.jgrid.stripHtml(x)}:{};b("tr.footrow td:eq("+y+")",r.grid.sDiv).html(x).attr(a);k=true}}else if(f=="get")l[g]=b("tr.footrow td:eq("+y+")",r.grid.sDiv).html()})});return f=="get"?l:k},showHideCol:function(f,i){return this.each(function(){var h=this,c=false,g=b.browser.webkit||b.browser.safari?0:h.p.cellLayout,k;if(h.grid){if(typeof f==="string")f=[f];i=i!="none"?"":"none";var l=i===""?true:false,a=h.p.groupHeader&&(typeof h.p.groupHeader==="object"||b.isFunction(h.p.groupHeader));
a&&b(h).jqGrid("destroyGroupHeader",false);b(this.p.colModel).each(function(r){if(b.inArray(this.name,f)!==-1&&this.hidden===l){b("tr",h.grid.hDiv).each(function(){b(this.cells[r]).css("display",i)});b(h.rows).each(function(){b(this.cells[r]).css("display",i)});h.p.footerrow&&b("tr.footrow td:eq("+r+")",h.grid.sDiv).css("display",i);k=this.widthOrg?this.widthOrg:parseInt(this.width,10);if(i==="none")h.p.tblwidth-=k+g;else h.p.tblwidth+=k+g;this.hidden=!l;c=true}});if(c===true)b(h).jqGrid("setGridWidth",
h.p.shrinkToFit===true?h.p.tblwidth:h.p.width);a&&b(h).jqGrid("setGroupHeaders",h.p.groupHeader)}})},hideCol:function(f){return this.each(function(){b(this).jqGrid("showHideCol",f,"none")})},showCol:function(f){return this.each(function(){b(this).jqGrid("showHideCol",f,"")})},remapColumns:function(f,i,h){function c(l){var a;a=l.length?b.makeArray(l):b.extend({},l);b.each(f,function(r){l[r]=a[this]})}function g(l,a){b(">tr"+(a||""),l).each(function(){var r=this,x=b.makeArray(r.cells);b.each(f,function(){var y=
x[this];y&&r.appendChild(y)})})}var k=this.get(0);c(k.p.colModel);c(k.p.colNames);c(k.grid.headers);g(b("thead:first",k.grid.hDiv),h&&":not(.ui-jqgrid-labels)");i&&g(b("#"+b.jgrid.jqID(k.p.id)+" tbody:first"),".jqgfirstrow, tr.jqgrow, tr.jqfoot");k.p.footerrow&&g(b("tbody:first",k.grid.sDiv));if(k.p.remapColumns)if(k.p.remapColumns.length)c(k.p.remapColumns);else k.p.remapColumns=b.makeArray(f);k.p.lastsort=b.inArray(k.p.lastsort,f);if(k.p.treeGrid)k.p.expColInd=b.inArray(k.p.expColInd,f)},setGridWidth:function(f,
i){return this.each(function(){if(this.grid){var h=this,c,g=0,k=b.browser.webkit||b.browser.safari?0:h.p.cellLayout,l,a=0,r=false,x=h.p.scrollOffset,y,H=0,L=0,M;if(typeof i!="boolean")i=h.p.shrinkToFit;if(!isNaN(f)){f=parseInt(f,10);h.grid.width=h.p.width=f;b("#gbox_"+b.jgrid.jqID(h.p.id)).css("width",f+"px");b("#gview_"+b.jgrid.jqID(h.p.id)).css("width",f+"px");b(h.grid.bDiv).css("width",f+"px");b(h.grid.hDiv).css("width",f+"px");h.p.pager&&b(h.p.pager).css("width",f+"px");h.p.toppager&&b(h.p.toppager).css("width",
f+"px");if(h.p.toolbar[0]===true){b(h.grid.uDiv).css("width",f+"px");h.p.toolbar[1]=="both"&&b(h.grid.ubDiv).css("width",f+"px")}h.p.footerrow&&b(h.grid.sDiv).css("width",f+"px");if(i===false&&h.p.forceFit===true)h.p.forceFit=false;if(i===true){b.each(h.p.colModel,function(){if(this.hidden===false){c=this.widthOrg?this.widthOrg:parseInt(this.width,10);g+=c+k;if(this.fixed)H+=c+k;else a++;L++}});if(a===0)return;h.p.tblwidth=g;y=f-k*a-H;if(!isNaN(h.p.height))if(b(h.grid.bDiv)[0].clientHeight<b(h.grid.bDiv)[0].scrollHeight||
h.rows.length===1){r=true;y-=x}g=0;var P=h.grid.cols.length>0;b.each(h.p.colModel,function(U){if(this.hidden===false&&!this.fixed){c=this.widthOrg?this.widthOrg:parseInt(this.width,10);c=Math.round(y*c/(h.p.tblwidth-k*a-H));if(!(c<0)){this.width=c;g+=c;h.grid.headers[U].width=c;h.grid.headers[U].el.style.width=c+"px";if(h.p.footerrow)h.grid.footers[U].style.width=c+"px";if(P)h.grid.cols[U].style.width=c+"px";l=U}}});if(!l)return;M=0;if(r){if(f-H-(g+k*a)!==x)M=f-H-(g+k*a)-x}else if(Math.abs(f-H-(g+
k*a))!==1)M=f-H-(g+k*a);h.p.colModel[l].width+=M;h.p.tblwidth=g+M+k*a+H;if(h.p.tblwidth>f){r=h.p.tblwidth-parseInt(f,10);h.p.tblwidth=f;c=h.p.colModel[l].width-=r}else c=h.p.colModel[l].width;h.grid.headers[l].width=c;h.grid.headers[l].el.style.width=c+"px";if(P)h.grid.cols[l].style.width=c+"px";if(h.p.footerrow)h.grid.footers[l].style.width=c+"px"}if(h.p.tblwidth){b("table:first",h.grid.bDiv).css("width",h.p.tblwidth+"px");b("table:first",h.grid.hDiv).css("width",h.p.tblwidth+"px");h.grid.hDiv.scrollLeft=
h.grid.bDiv.scrollLeft;h.p.footerrow&&b("table:first",h.grid.sDiv).css("width",h.p.tblwidth+"px")}}}})},setGridHeight:function(f){return this.each(function(){if(this.grid){b(this.grid.bDiv).css({height:f+(isNaN(f)?"":"px")});this.p.height=f;this.p.scroll&&this.grid.populateVisible()}})},setCaption:function(f){return this.each(function(){this.p.caption=f;b("span.ui-jqgrid-title",this.grid.cDiv).html(f);b(this.grid.cDiv).show()})},setLabel:function(f,i,h,c){return this.each(function(){var g=-1;if(this.grid)if(typeof f!=
"undefined"){b(this.p.colModel).each(function(a){if(this.name==f){g=a;return false}});if(g>=0){var k=b("tr.ui-jqgrid-labels th:eq("+g+")",this.grid.hDiv);if(i){var l=b(".s-ico",k);b("[id^=jqgh_]",k).empty().html(i).append(l);this.p.colNames[g]=i}if(h)typeof h==="string"?b(k).addClass(h):b(k).css(h);typeof c==="object"&&b(k).attr(c)}}})},setCell:function(f,i,h,c,g,k){return this.each(function(){var l=-1,a,r;if(this.grid){if(isNaN(i))b(this.p.colModel).each(function(y){if(this.name==i){l=y;return false}});
else l=parseInt(i,10);if(l>=0)if(a=this.rows.namedItem(f)){var x=b("td:eq("+l+")",a);if(h!==""||k===true){a=this.formatter(f,h,l,a,"edit");r=this.p.colModel[l].title?{title:b.jgrid.stripHtml(a)}:{};this.p.treeGrid&&b(".tree-wrap",b(x)).length>0?b("span",b(x)).html(a).attr(r):b(x).html(a).attr(r);if(this.p.datatype=="local"){a=this.p.colModel[l];h=a.formatter&&typeof a.formatter==="string"&&a.formatter=="date"?b.unformat.date(h,a):h;r=this.p._index[f];if(typeof r!="undefined")this.p.data[r][a.name]=
h}}if(typeof c==="string")b(x).addClass(c);else c&&b(x).css(c);typeof g==="object"&&b(x).attr(g)}}})},getCell:function(f,i){var h=false;this.each(function(){var c=-1;if(this.grid){if(isNaN(i))b(this.p.colModel).each(function(l){if(this.name===i){c=l;return false}});else c=parseInt(i,10);if(c>=0){var g=this.rows.namedItem(f);if(g)try{h=b.unformat(b("td:eq("+c+")",g),{rowId:g.id,colModel:this.p.colModel[c]},c)}catch(k){h=b.jgrid.htmlDecode(b("td:eq("+c+")",g).html())}}}});return h},getCol:function(f,
i,h){var c=[],g,k=0,l=0,a=0,r;i=typeof i!="boolean"?false:i;if(typeof h=="undefined")h=false;this.each(function(){var x=-1;if(this.grid){if(isNaN(f))b(this.p.colModel).each(function(M){if(this.name===f){x=M;return false}});else x=parseInt(f,10);if(x>=0){var y=this.rows.length,H=0;if(y&&y>0){for(;H<y;){if(b(this.rows[H]).hasClass("jqgrow")){try{g=b.unformat(b(this.rows[H].cells[x]),{rowId:this.rows[H].id,colModel:this.p.colModel[x]},x)}catch(L){g=b.jgrid.htmlDecode(this.rows[H].cells[x].innerHTML)}if(h){r=
parseFloat(g);k+=r;l=Math.min(l,r);a=Math.max(a,r)}else i?c.push({id:this.rows[H].id,value:g}):c.push(g)}H++}if(h)switch(h.toLowerCase()){case "sum":c=k;break;case "avg":c=k/y;break;case "count":c=y;break;case "min":c=l;break;case "max":c=a}}}}});return c},clearGridData:function(f){return this.each(function(){if(this.grid){if(typeof f!="boolean")f=false;if(this.p.deepempty)b("#"+b.jgrid.jqID(this.p.id)+" tbody:first tr:gt(0)").remove();else{var i=b("#"+b.jgrid.jqID(this.p.id)+" tbody:first tr:first")[0];
b("#"+b.jgrid.jqID(this.p.id)+" tbody:first").empty().append(i)}this.p.footerrow&&f&&b(".ui-jqgrid-ftable td",this.grid.sDiv).html("&#160;");this.p.selrow=null;this.p.selarrrow=[];this.p.savedRow=[];this.p.records=0;this.p.page=1;this.p.lastpage=0;this.p.reccount=0;this.p.data=[];this.p._index={};this.updatepager(true,false)}})},getInd:function(f,i){var h=false,c;this.each(function(){if(c=this.rows.namedItem(f))h=i===true?c:c.rowIndex});return h},bindKeys:function(f){var i=b.extend({onEnter:null,
onSpace:null,onLeftKey:null,onRightKey:null,scrollingRows:true},f||{});return this.each(function(){var h=this;b("body").is("[role]")||b("body").attr("role","application");h.p.scrollrows=i.scrollingRows;b(h).keydown(function(c){var g=b(h).find("tr[tabindex=0]")[0],k,l,a,r=h.p.treeReader.expanded_field;if(g){a=h.p._index[g.id];if(c.keyCode===37||c.keyCode===38||c.keyCode===39||c.keyCode===40){if(c.keyCode===38){l=g.previousSibling;k="";if(l)if(b(l).is(":hidden"))for(;l;){l=l.previousSibling;if(!b(l).is(":hidden")&&
b(l).hasClass("jqgrow")){k=l.id;break}}else k=l.id;b(h).jqGrid("setSelection",k)}if(c.keyCode===40){l=g.nextSibling;k="";if(l)if(b(l).is(":hidden"))for(;l;){l=l.nextSibling;if(!b(l).is(":hidden")&&b(l).hasClass("jqgrow")){k=l.id;break}}else k=l.id;b(h).jqGrid("setSelection",k)}if(c.keyCode===37){h.p.treeGrid&&h.p.data[a][r]&&b(g).find("div.treeclick").trigger("click");b.isFunction(i.onLeftKey)&&i.onLeftKey.call(h,h.p.selrow)}if(c.keyCode===39){h.p.treeGrid&&!h.p.data[a][r]&&b(g).find("div.treeclick").trigger("click");
b.isFunction(i.onRightKey)&&i.onRightKey.call(h,h.p.selrow)}}else if(c.keyCode===13)b.isFunction(i.onEnter)&&i.onEnter.call(h,h.p.selrow);else c.keyCode===32&&b.isFunction(i.onSpace)&&i.onSpace.call(h,h.p.selrow)}})})},unbindKeys:function(){return this.each(function(){b(this).unbind("keydown")})},getLocalRow:function(f){var i=false,h;this.each(function(){if(typeof f!=="undefined"){h=this.p._index[f];if(h>=0)i=this.p.data[h]}});return i}})})(jQuery);
(function(b){b.fmatter={};b.extend(b.fmatter,{isBoolean:function(a){return typeof a==="boolean"},isObject:function(a){return a&&(typeof a==="object"||b.isFunction(a))||false},isString:function(a){return typeof a==="string"},isNumber:function(a){return typeof a==="number"&&isFinite(a)},isNull:function(a){return a===null},isUndefined:function(a){return typeof a==="undefined"},isValue:function(a){return this.isObject(a)||this.isString(a)||this.isNumber(a)||this.isBoolean(a)},isEmpty:function(a){if(!this.isString(a)&&
this.isValue(a))return false;else if(!this.isValue(a))return true;a=b.trim(a).replace(/\&nbsp\;/ig,"").replace(/\&#160\;/ig,"");return a===""}});b.fn.fmatter=function(a,c,d,e,f){var g=c;d=b.extend({},b.jgrid.formatter,d);if(b.fn.fmatter[a])g=b.fn.fmatter[a](c,d,e,f);return g};b.fmatter.util={NumberFormat:function(a,c){b.fmatter.isNumber(a)||(a*=1);if(b.fmatter.isNumber(a)){var d=a<0,e=a+"",f=c.decimalSeparator?c.decimalSeparator:".",g;if(b.fmatter.isNumber(c.decimalPlaces)){var h=c.decimalPlaces;
e=Math.pow(10,h);e=Math.round(a*e)/e+"";g=e.lastIndexOf(".");if(h>0){if(g<0){e+=f;g=e.length-1}else if(f!==".")e=e.replace(".",f);for(;e.length-1-g<h;)e+="0"}}if(c.thousandsSeparator){h=c.thousandsSeparator;g=e.lastIndexOf(f);g=g>-1?g:e.length;f=e.substring(g);for(var i=-1,j=g;j>0;j--){i++;if(i%3===0&&j!==g&&(!d||j>1))f=h+f;f=e.charAt(j-1)+f}e=f}e=c.prefix?c.prefix+e:e;return e=c.suffix?e+c.suffix:e}else return a},DateFormat:function(a,c,d,e){var f=/^\/Date\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\)\/$/,
g=typeof c==="string"?c.match(f):null;f=function(m,r){m=String(m);for(r=parseInt(r,10)||2;m.length<r;)m="0"+m;return m};var h={m:1,d:1,y:1970,h:0,i:0,s:0,u:0},i=0,j,k=["i18n"];k.i18n={dayNames:e.dayNames,monthNames:e.monthNames};if(a in e.masks)a=e.masks[a];if(!isNaN(c-0)&&String(a).toLowerCase()=="u")i=new Date(parseFloat(c)*1E3);else if(c.constructor===Date)i=c;else if(g!==null){i=new Date(parseInt(g[1],10));if(g[3]){a=Number(g[5])*60+Number(g[6]);a*=g[4]=="-"?1:-1;a-=i.getTimezoneOffset();i.setTime(Number(Number(i)+
a*6E4))}}else{c=String(c).split(/[\\\/:_;.,\t\T\s-]/);a=a.split(/[\\\/:_;.,\t\T\s-]/);g=0;for(j=a.length;g<j;g++){if(a[g]=="M"){i=b.inArray(c[g],k.i18n.monthNames);if(i!==-1&&i<12)c[g]=i+1}if(a[g]=="F"){i=b.inArray(c[g],k.i18n.monthNames);if(i!==-1&&i>11)c[g]=i+1-12}if(c[g])h[a[g].toLowerCase()]=parseInt(c[g],10)}if(h.f)h.m=h.f;if(h.m===0&&h.y===0&&h.d===0)return"&#160;";h.m=parseInt(h.m,10)-1;i=h.y;if(i>=70&&i<=99)h.y=1900+h.y;else if(i>=0&&i<=69)h.y=2E3+h.y;i=new Date(h.y,h.m,h.d,h.h,h.i,h.s,h.u)}if(d in
e.masks)d=e.masks[d];else d||(d="Y-m-d");a=i.getHours();c=i.getMinutes();h=i.getDate();g=i.getMonth()+1;j=i.getTimezoneOffset();var l=i.getSeconds(),o=i.getMilliseconds(),n=i.getDay(),p=i.getFullYear(),q=(n+6)%7+1,s=(new Date(p,g-1,h)-new Date(p,0,1))/864E5,t={d:f(h),D:k.i18n.dayNames[n],j:h,l:k.i18n.dayNames[n+7],N:q,S:e.S(h),w:n,z:s,W:q<5?Math.floor((s+q-1)/7)+1:Math.floor((s+q-1)/7)||(((new Date(p-1,0,1)).getDay()+6)%7<4?53:52),F:k.i18n.monthNames[g-1+12],m:f(g),M:k.i18n.monthNames[g-1],n:g,t:"?",
L:"?",o:"?",Y:p,y:String(p).substring(2),a:a<12?e.AmPm[0]:e.AmPm[1],A:a<12?e.AmPm[2]:e.AmPm[3],B:"?",g:a%12||12,G:a,h:f(a%12||12),H:f(a),i:f(c),s:f(l),u:o,e:"?",I:"?",O:(j>0?"-":"+")+f(Math.floor(Math.abs(j)/60)*100+Math.abs(j)%60,4),P:"?",T:(String(i).match(/\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g)||[""]).pop().replace(/[^-+\dA-Z]/g,""),Z:"?",c:"?",r:"?",U:Math.floor(i/1E3)};return d.replace(/\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g,
function(m){return m in t?t[m]:m.substring(1)})}};b.fn.fmatter.defaultFormat=function(a,c){return b.fmatter.isValue(a)&&a!==""?a:c.defaultValue?c.defaultValue:"&#160;"};b.fn.fmatter.email=function(a,c){return b.fmatter.isEmpty(a)?b.fn.fmatter.defaultFormat(a,c):'<a href="mailto:'+a+'">'+a+"</a>"};b.fn.fmatter.checkbox=function(a,c){var d=b.extend({},c.checkbox),e;b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));e=d.disabled===true?'disabled="disabled"':
"";if(b.fmatter.isEmpty(a)||b.fmatter.isUndefined(a))a=b.fn.fmatter.defaultFormat(a,d);a+="";a=a.toLowerCase();return'<input type="checkbox" '+(a.search(/(false|0|no|off)/i)<0?" checked='checked' ":"")+' value="'+a+'" offval="no" '+e+"/>"};b.fn.fmatter.link=function(a,c){var d={target:c.target},e="";b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));if(d.target)e="target="+d.target;return b.fmatter.isEmpty(a)?b.fn.fmatter.defaultFormat(a,c):"<a "+e+' href="'+
a+'">'+a+"</a>"};b.fn.fmatter.showlink=function(a,c){var d={baseLinkUrl:c.baseLinkUrl,showAction:c.showAction,addParam:c.addParam||"",target:c.target,idName:c.idName},e="";b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));if(d.target)e="target="+d.target;d=d.baseLinkUrl+d.showAction+"?"+d.idName+"="+c.rowId+d.addParam;return b.fmatter.isString(a)||b.fmatter.isNumber(a)?"<a "+e+' href="'+d+'">'+a+"</a>":b.fn.fmatter.defaultFormat(a,c)};b.fn.fmatter.integer=
function(a,c){var d=b.extend({},c.integer);b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));if(b.fmatter.isEmpty(a))return d.defaultValue;return b.fmatter.util.NumberFormat(a,d)};b.fn.fmatter.number=function(a,c){var d=b.extend({},c.number);b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));if(b.fmatter.isEmpty(a))return d.defaultValue;return b.fmatter.util.NumberFormat(a,d)};b.fn.fmatter.currency=function(a,c){var d=
b.extend({},c.currency);b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));if(b.fmatter.isEmpty(a))return d.defaultValue;return b.fmatter.util.NumberFormat(a,d)};b.fn.fmatter.date=function(a,c,d,e){d=b.extend({},c.date);b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend({},d,c.colModel.formatoptions));return!d.reformatAfterEdit&&e=="edit"?b.fn.fmatter.defaultFormat(a,c):b.fmatter.isEmpty(a)?b.fn.fmatter.defaultFormat(a,c):b.fmatter.util.DateFormat(d.srcformat,
a,d.newformat,d)};b.fn.fmatter.select=function(a,c){a+="";var d=false,e=[];if(b.fmatter.isUndefined(c.colModel.formatoptions)){if(!b.fmatter.isUndefined(c.colModel.editoptions))d=c.colModel.editoptions.value}else d=c.colModel.formatoptions.value;if(d){var f=c.colModel.editoptions.multiple===true?true:false,g=[],h;if(f){g=a.split(",");g=b.map(g,function(l){return b.trim(l)})}if(b.fmatter.isString(d))for(var i=d.split(";"),j=0,k=0;k<i.length;k++){h=i[k].split(":");if(h.length>2)h[1]=jQuery.map(h,function(l,
o){if(o>0)return l}).join(":");if(f){if(jQuery.inArray(h[0],g)>-1){e[j]=h[1];j++}}else if(b.trim(h[0])==b.trim(a)){e[0]=h[1];break}}else if(b.fmatter.isObject(d))if(f)e=jQuery.map(g,function(l){return d[l]});else e[0]=d[a]||""}a=e.join(", ");return a===""?b.fn.fmatter.defaultFormat(a,c):a};b.fn.fmatter.rowactions=function(a,c,d,e){var f={keys:false,onEdit:null,onSuccess:null,afterSave:null,onError:null,afterRestore:null,extraparam:{oper:"edit"},url:null,delOptions:{},editOptions:{}};a=b.jgrid.jqID(a);
c=b.jgrid.jqID(c);e=b("#"+c)[0].p.colModel[e];b.fmatter.isUndefined(e.formatoptions)||(f=b.extend(f,e.formatoptions));if(!b.fmatter.isUndefined(b("#"+c)[0].p.editOptions))f.editOptions=b("#"+c)[0].p.editOptions;if(!b.fmatter.isUndefined(b("#"+c)[0].p.delOptions))f.delOptions=b("#"+c)[0].p.delOptions;e=function(h){f.afterSave&&f.afterSave(h);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel",
"#"+c+".ui-jqgrid-btable:first").hide()};var g=function(h){f.afterRestore&&f.afterRestore(h);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide()};switch(d){case "edit":b("#"+c).jqGrid("editRow",a,f.keys,f.onEdit,f.onSuccess,f.url,f.extraparam,e,f.onError,g);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").hide();
b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").show();break;case "save":if(b("#"+c).jqGrid("saveRow",a,f.onSuccess,f.url,f.extraparam,e,f.onError,g)){b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del","#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide()}break;case "cancel":b("#"+c).jqGrid("restoreRow",a,g);b("tr#"+a+" div.ui-inline-edit, tr#"+a+" div.ui-inline-del",
"#"+c+".ui-jqgrid-btable:first").show();b("tr#"+a+" div.ui-inline-save, tr#"+a+" div.ui-inline-cancel","#"+c+".ui-jqgrid-btable:first").hide();break;case "del":b("#"+c).jqGrid("delGridRow",a,f.delOptions);break;case "formedit":b("#"+c).jqGrid("setSelection",a);b("#"+c).jqGrid("editGridRow",a,f.editOptions)}};b.fn.fmatter.actions=function(a,c){var d={keys:false,editbutton:true,delbutton:true,editformbutton:false};b.fmatter.isUndefined(c.colModel.formatoptions)||(d=b.extend(d,c.colModel.formatoptions));
var e=c.rowId,f="",g;if(typeof e=="undefined"||b.fmatter.isEmpty(e))return"";if(d.editformbutton){g="onclick=jQuery.fn.fmatter.rowactions('"+e+"','"+c.gid+"','formedit',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";f=f+"<div title='"+b.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+g+"><span class='ui-icon ui-icon-pencil'></span></div>"}else if(d.editbutton){g="onclick=jQuery.fn.fmatter.rowactions('"+
e+"','"+c.gid+"','edit',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover') ";f=f+"<div title='"+b.jgrid.nav.edittitle+"' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "+g+"><span class='ui-icon ui-icon-pencil'></span></div>"}if(d.delbutton){g="onclick=jQuery.fn.fmatter.rowactions('"+e+"','"+c.gid+"','del',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";
f=f+"<div title='"+b.jgrid.nav.deltitle+"' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' "+g+"><span class='ui-icon ui-icon-trash'></span></div>"}g="onclick=jQuery.fn.fmatter.rowactions('"+e+"','"+c.gid+"','save',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";f=f+"<div title='"+b.jgrid.edit.bSubmit+"' style='float:left;display:none' class='ui-pg-div ui-inline-save' "+g+"><span class='ui-icon ui-icon-disk'></span></div>";
g="onclick=jQuery.fn.fmatter.rowactions('"+e+"','"+c.gid+"','cancel',"+c.pos+"); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ";f=f+"<div title='"+b.jgrid.edit.bCancel+"' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel' "+g+"><span class='ui-icon ui-icon-cancel'></span></div>";return"<div style='margin-left:8px;'>"+f+"</div>"};b.unformat=function(a,c,d,e){var f,g=c.colModel.formatter,h=c.colModel.formatoptions||
{},i=/([\.\*\_\'\(\)\{\}\+\?\\])/g,j=c.colModel.unformat||b.fn.fmatter[g]&&b.fn.fmatter[g].unformat;if(typeof j!=="undefined"&&b.isFunction(j))f=j(b(a).text(),c,a);else if(!b.fmatter.isUndefined(g)&&b.fmatter.isString(g)){f=b.jgrid.formatter||{};switch(g){case "integer":h=b.extend({},f.integer,h);c=h.thousandsSeparator.replace(i,"\\$1");f=b(a).text().replace(RegExp(c,"g"),"");break;case "number":h=b.extend({},f.number,h);c=h.thousandsSeparator.replace(i,"\\$1");f=b(a).text().replace(RegExp(c,"g"),
"").replace(h.decimalSeparator,".");break;case "currency":h=b.extend({},f.currency,h);c=h.thousandsSeparator.replace(i,"\\$1");f=b(a).text().replace(RegExp(c,"g"),"").replace(h.decimalSeparator,".").replace(h.prefix,"").replace(h.suffix,"");break;case "checkbox":h=c.colModel.editoptions?c.colModel.editoptions.value.split(":"):["Yes","No"];f=b("input",a).is(":checked")?h[0]:h[1];break;case "select":f=b.unformat.select(a,c,d,e);break;case "actions":return"";default:f=b(a).text()}}return f!==undefined?
f:e===true?b(a).text():b.jgrid.htmlDecode(b(a).html())};b.unformat.select=function(a,c,d,e){d=[];a=b(a).text();if(e===true)return a;c=b.extend({},c.colModel.editoptions);if(c.value){var f=c.value;c=c.multiple===true?true:false;e=[];var g;if(c){e=a.split(",");e=b.map(e,function(k){return b.trim(k)})}if(b.fmatter.isString(f))for(var h=f.split(";"),i=0,j=0;j<h.length;j++){g=h[j].split(":");if(g.length>2)g[1]=jQuery.map(g,function(k,l){if(l>0)return k}).join(":");if(c){if(jQuery.inArray(g[1],e)>-1){d[i]=
g[0];i++}}else if(b.trim(g[1])==b.trim(a)){d[0]=g[0];break}}else if(b.fmatter.isObject(f)||b.isArray(f)){c||(e[0]=a);d=jQuery.map(e,function(k){var l;b.each(f,function(o,n){if(n==k){l=o;return false}});if(typeof l!="undefined")return l})}return d.join(", ")}else return a||""};b.unformat.date=function(a,c){var d=b.jgrid.formatter.date||{};b.fmatter.isUndefined(c.formatoptions)||(d=b.extend({},d,c.formatoptions));return b.fmatter.isEmpty(a)?b.fn.fmatter.defaultFormat(a,c):b.fmatter.util.DateFormat(d.newformat,
a,d.srcformat,d)}})(jQuery);
(function(a){a.jgrid.extend({getColProp:function(c){var g={},b=this[0];if(!b.grid)return false;b=b.p.colModel;for(var i=0;i<b.length;i++)if(b[i].name==c){g=b[i];break}return g},setColProp:function(c,g){return this.each(function(){if(this.grid)if(g)for(var b=this.p.colModel,i=0;i<b.length;i++)if(b[i].name==c){a.extend(this.p.colModel[i],g);break}})},sortGrid:function(c,g,b){return this.each(function(){var i=-1;if(this.grid){if(!c)c=this.p.sortname;for(var l=0;l<this.p.colModel.length;l++)if(this.p.colModel[l].index==
c||this.p.colModel[l].name==c){i=l;break}if(i!=-1){l=this.p.colModel[i].sortable;if(typeof l!=="boolean")l=true;if(typeof g!=="boolean")g=false;l&&this.sortData("jqgh_"+this.p.id+"_"+c,i,g,b)}}})},GridDestroy:function(){return this.each(function(){if(this.grid){this.p.pager&&a(this.p.pager).remove();var c=this.id;try{a("#gbox_"+c).remove()}catch(g){}}})},GridUnload:function(){return this.each(function(){if(this.grid){var c={id:a(this).attr("id"),cl:a(this).attr("class")};this.p.pager&&a(this.p.pager).empty().removeClass("ui-state-default ui-jqgrid-pager corner-bottom");
var g=document.createElement("table");a(g).attr({id:c.id});g.className=c.cl;c=this.id;a(g).removeClass("ui-jqgrid-btable");if(a(this.p.pager).parents("#gbox_"+c).length===1){a(g).insertBefore("#gbox_"+c).show();a(this.p.pager).insertBefore("#gbox_"+c)}else a(g).insertBefore("#gbox_"+c).show();a("#gbox_"+c).remove()}})},setGridState:function(c){return this.each(function(){if(this.grid)if(c=="hidden"){a(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv","#gview_"+this.p.id).slideUp("fast");this.p.pager&&a(this.p.pager).slideUp("fast");
this.p.toppager&&a(this.p.toppager).slideUp("fast");if(this.p.toolbar[0]===true){this.p.toolbar[1]=="both"&&a(this.grid.ubDiv).slideUp("fast");a(this.grid.uDiv).slideUp("fast")}this.p.footerrow&&a(".ui-jqgrid-sdiv","#gbox_"+this.p.id).slideUp("fast");a(".ui-jqgrid-titlebar-close span",this.grid.cDiv).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");this.p.gridstate="hidden"}else if(c=="visible"){a(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv","#gview_"+this.p.id).slideDown("fast");
this.p.pager&&a(this.p.pager).slideDown("fast");this.p.toppager&&a(this.p.toppager).slideDown("fast");if(this.p.toolbar[0]===true){this.p.toolbar[1]=="both"&&a(this.grid.ubDiv).slideDown("fast");a(this.grid.uDiv).slideDown("fast")}this.p.footerrow&&a(".ui-jqgrid-sdiv","#gbox_"+this.p.id).slideDown("fast");a(".ui-jqgrid-titlebar-close span",this.grid.cDiv).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");this.p.gridstate="visible"}})},filterToolbar:function(c){c=a.extend({autosearch:true,
searchOnEnter:true,beforeSearch:null,afterSearch:null,beforeClear:null,afterClear:null,searchurl:"",stringResult:false,groupOp:"AND",defaultSearch:"bw"},c||{});return this.each(function(){function g(d,f){var j=a(d);j[0]&&jQuery.each(f,function(){this.data!==undefined?j.bind(this.type,this.data,this.fn):j.bind(this.type,this.fn)})}var b=this;if(!this.ftoolbar){var i=function(){var d={},f=0,j,e,h={},n;a.each(b.p.colModel,function(){e=this.index||this.name;switch(this.stype){case "select":n=this.searchoptions&&
this.searchoptions.sopt?this.searchoptions.sopt[0]:"eq";if(j=a("#gs_"+a.jgrid.jqID(this.name),b.grid.hDiv).val()){d[e]=j;h[e]=n;f++}else try{delete b.p.postData[e]}catch(r){}break;case "text":n=this.searchoptions&&this.searchoptions.sopt?this.searchoptions.sopt[0]:c.defaultSearch;if(j=a("#gs_"+a.jgrid.jqID(this.name),b.grid.hDiv).val()){d[e]=j;h[e]=n;f++}else try{delete b.p.postData[e]}catch(u){}}});var o=f>0?true:false;if(c.stringResult===true||b.p.datatype=="local"){var k='{"groupOp":"'+c.groupOp+
'","rules":[',t=0;a.each(d,function(r,u){if(t>0)k+=",";k+='{"field":"'+r+'",';k+='"op":"'+h[r]+'",';u+="";k+='"data":"'+u.replace(/\\/g,"\\\\").replace(/\"/g,'\\"')+'"}';t++});k+="]}";a.extend(b.p.postData,{filters:k});a.each(["searchField","searchString","searchOper"],function(r,u){b.p.postData.hasOwnProperty(u)&&delete b.p.postData[u]})}else a.extend(b.p.postData,d);var m;if(b.p.searchurl){m=b.p.url;a(b).jqGrid("setGridParam",{url:b.p.searchurl})}var q=false;if(a.isFunction(c.beforeSearch))q=c.beforeSearch.call(b);
q||a(b).jqGrid("setGridParam",{search:o}).trigger("reloadGrid",[{page:1}]);m&&a(b).jqGrid("setGridParam",{url:m});a.isFunction(c.afterSearch)&&c.afterSearch()},l=a("<tr class='ui-search-toolbar' role='rowheader'></tr>"),p;a.each(b.p.colModel,function(){var d=this,f,j,e,h;j=a("<th role='columnheader' class='ui-state-default ui-th-column ui-th-"+b.p.direction+"'></th>");f=a("<div style='width:100%;position:relative;height:100%;padding-right:0.3em;'></div>");this.hidden===true&&a(j).css("display","none");
this.search=this.search===false?false:true;if(typeof this.stype=="undefined")this.stype="text";e=a.extend({},this.searchoptions||{});if(this.search)switch(this.stype){case "select":if(h=this.surl||e.dataUrl)a.ajax(a.extend({url:h,dataType:"html",success:function(m){if(e.buildSelect!==undefined)(m=e.buildSelect(m))&&a(f).append(m);else a(f).append(m);e.defaultValue&&a("select",f).val(e.defaultValue);a("select",f).attr({name:d.index||d.name,id:"gs_"+d.name});e.attr&&a("select",f).attr(e.attr);a("select",
f).css({width:"100%"});e.dataInit!==undefined&&e.dataInit(a("select",f)[0]);e.dataEvents!==undefined&&g(a("select",f)[0],e.dataEvents);c.autosearch===true&&a("select",f).change(function(){i();return false});m=null}},a.jgrid.ajaxOptions,b.p.ajaxSelectOptions||{}));else{var n;if(d.searchoptions&&d.searchoptions.value)n=d.searchoptions.value;else if(d.editoptions&&d.editoptions.value)n=d.editoptions.value;if(n){h=document.createElement("select");h.style.width="100%";a(h).attr({name:d.index||d.name,id:"gs_"+
d.name});var o,k;if(typeof n==="string"){n=n.split(";");for(var t=0;t<n.length;t++){o=n[t].split(":");k=document.createElement("option");k.value=o[0];k.innerHTML=o[1];h.appendChild(k)}}else if(typeof n==="object")for(o in n)if(n.hasOwnProperty(o)){k=document.createElement("option");k.value=o;k.innerHTML=n[o];h.appendChild(k)}e.defaultValue&&a(h).val(e.defaultValue);e.attr&&a(h).attr(e.attr);e.dataInit!==undefined&&e.dataInit(h);e.dataEvents!==undefined&&g(h,e.dataEvents);a(f).append(h);c.autosearch===
true&&a(h).change(function(){i();return false})}}break;case "text":h=e.defaultValue?e.defaultValue:"";a(f).append("<input type='text' style='width:95%;padding:0px;' name='"+(d.index||d.name)+"' id='gs_"+d.name+"' value='"+h+"'/>");e.attr&&a("input",f).attr(e.attr);e.dataInit!==undefined&&e.dataInit(a("input",f)[0]);e.dataEvents!==undefined&&g(a("input",f)[0],e.dataEvents);if(c.autosearch===true)c.searchOnEnter?a("input",f).keypress(function(m){if((m.charCode?m.charCode:m.keyCode?m.keyCode:0)==13){i();
return false}return this}):a("input",f).keydown(function(m){switch(m.which){case 13:return false;case 9:case 16:case 37:case 38:case 39:case 40:case 27:break;default:p&&clearTimeout(p);p=setTimeout(function(){i()},500)}})}a(j).append(f);a(l).append(j)});a("table thead",b.grid.hDiv).append(l);this.ftoolbar=true;this.triggerToolbar=i;this.clearToolbar=function(d){var f={},j,e=0,h;d=typeof d!="boolean"?true:d;a.each(b.p.colModel,function(){j=this.searchoptions&&this.searchoptions.defaultValue?this.searchoptions.defaultValue:
"";h=this.index||this.name;switch(this.stype){case "select":var q;a("#gs_"+a.jgrid.jqID(h)+" option",b.grid.hDiv).each(function(s){if(s===0)this.selected=true;if(a(this).text()==j){this.selected=true;q=a(this).val();return false}});if(q){f[h]=q;e++}else try{delete b.p.postData[h]}catch(r){}break;case "text":a("#gs_"+a.jgrid.jqID(h),b.grid.hDiv).val(j);if(j){f[h]=j;e++}else try{delete b.p.postData[h]}catch(u){}}});var n=e>0?true:false;if(c.stringResult===true||b.p.datatype=="local"){var o='{"groupOp":"'+
c.groupOp+'","rules":[',k=0;a.each(f,function(q,r){if(k>0)o+=",";o+='{"field":"'+q+'",';o+='"op":"eq",';r+="";o+='"data":"'+r.replace(/\\/g,"\\\\").replace(/\"/g,'\\"')+'"}';k++});o+="]}";a.extend(b.p.postData,{filters:o});a.each(["searchField","searchString","searchOper"],function(q,r){b.p.postData.hasOwnProperty(r)&&delete b.p.postData[r]})}else a.extend(b.p.postData,f);var t;if(b.p.searchurl){t=b.p.url;a(b).jqGrid("setGridParam",{url:b.p.searchurl})}var m=false;if(a.isFunction(c.beforeClear))m=
c.beforeClear.call(b);m||d&&a(b).jqGrid("setGridParam",{search:n}).trigger("reloadGrid",[{page:1}]);t&&a(b).jqGrid("setGridParam",{url:t});a.isFunction(c.afterClear)&&c.afterClear()};this.toggleToolbar=function(){var d=a("tr.ui-search-toolbar",b.grid.hDiv);d.css("display")=="none"?d.show():d.hide()}}})},destroyGroupHeader:function(c){if(typeof c=="undefined")c=true;return this.each(function(){var g,b,i,l,p,d;b=this.grid;var f=a("table.ui-jqgrid-htable thead",b.hDiv),j=this.p.colModel;if(b){g=a("<tr>",
{role:"rowheader"}).addClass("ui-jqgrid-labels");l=b.headers;b=0;for(i=l.length;b<i;b++){p=j[b].hidden?"none":"";p=a(l[b].el).width(l[b].width).removeAttr("rowSpan").css("display",p);g.append(p);d=p.children("span.ui-jqgrid-resize");if(d.length>0)d[0].style.height="";p.children("div")[0].style.top=""}a(f).children("tr.ui-jqgrid-labels").remove();a(f).prepend(g);c===true&&a(this).jqGrid("setGridParam",{groupHeader:null})}})},setGroupHeaders:function(c){c=a.extend({useColSpanStyle:false,groupHeaders:[]},
c||{});return this.each(function(){this.p.groupHeader=c;var g=this,b,i,l=0,p,d,f,j,e,h=g.p.colModel,n=h.length,o=g.grid.headers,k=a("table.ui-jqgrid-htable",g.grid.hDiv),t=k.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header");p=k.children("thead");var m,q=k.find(".jqg-first-row-header");if(q.html()===null)q=a("<tr>",{role:"row","aria-hidden":"true"}).addClass("jqg-first-row-header").css("height","auto");else q.empty();var r,u=function(s,v){for(var w=0,x=v.length;w<
x;w++)if(v[w].startColumnName===s)return w;return-1};a(g).prepend(p);p=a("<tr>",{role:"rowheader"}).addClass("ui-jqgrid-labels jqg-third-row-header");for(b=0;b<n;b++){f=o[b].el;j=a(f);i=h[b];d={height:"0px",width:o[b].width+"px",display:i.hidden?"none":""};a("<th>",{role:"gridcell"}).css(d).addClass("ui-first-th-"+g.p.direction).appendTo(q);f.style.width="";d=u(i.name,c.groupHeaders);if(d>=0){d=c.groupHeaders[d];l=d.numberOfColumns;e=d.titleText;for(d=i=0;d<l&&b+d<n;d++)h[b+d].hidden||i++;d=a("<th>",
{colspan:String(i),role:"columnheader"}).addClass("ui-state-default ui-th-column-header ui-th-"+g.p.direction).css({height:"22px","border-top":"0px none"}).html(e);g.p.headertitles&&d.attr("title",d.text());i===0&&d.hide();j.before(d);p.append(f);l-=1}else if(l===0)if(c.useColSpanStyle)j.attr("rowspan","2");else{a("<th>",{role:"columnheader"}).addClass("ui-state-default ui-th-column-header ui-th-"+g.p.direction).css({display:i.hidden?"none":"","border-top":"0px none"}).insertBefore(j);p.append(f)}else{p.append(f);
l--}}h=a(g).children("thead");h.prepend(q);p.insertAfter(t);k.append(h);if(c.useColSpanStyle){k.find("span.ui-jqgrid-resize").each(function(){var s=a(this).parent();if(s.is(":visible"))this.style.cssText="height: "+s.height()+"px !important; cursor: col-resize;"});k.find("div.ui-jqgrid-sortable").each(function(){var s=a(this),v=s.parent();v.is(":visible")&&s.css("top",(v.height()-s.outerHeight())/2+"px")})}if(a.isFunction(g.p.resizeStop))m=g.p.resizeStop;r=h.find("tr.jqg-first-row-header");g.p.resizeStop=
function(s,v){r.find("th").eq(v).width(s);a.isFunction(m)&&m.call(g,s,v)}})}})})(jQuery);
(function(a){a.extend(a.jgrid,{showModal:function(b){b.w.show()},closeModal:function(b){b.w.hide().attr("aria-hidden","true");b.o&&b.o.remove()},hideModal:function(b,c){c=a.extend({jqm:true,gb:""},c||{});if(c.onClose){var d=c.onClose(b);if(typeof d=="boolean"&&!d)return}if(a.fn.jqm&&c.jqm===true)a(b).attr("aria-hidden","true").jqmHide();else{if(c.gb!=="")try{a(".jqgrid-overlay:first",c.gb).hide()}catch(f){}a(b).hide().attr("aria-hidden","true")}},findPos:function(b){var c=0,d=0;if(b.offsetParent){do{c+=
b.offsetLeft;d+=b.offsetTop}while(b=b.offsetParent)}return[c,d]},createModal:function(b,c,d,f,g,h,j){var e=document.createElement("div"),k,m=this;j=a.extend({},j||{});k=a(d.gbox).attr("dir")=="rtl"?true:false;e.className="ui-widget ui-widget-content ui-corner-all ui-jqdialog";e.id=b.themodal;var i=document.createElement("div");i.className="ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";i.id=b.modalhead;a(i).append("<span class='ui-jqdialog-title'>"+d.caption+"</span>");var q=
a("<a href='javascript:void(0)' class='ui-jqdialog-titlebar-close ui-corner-all'></a>").hover(function(){q.addClass("ui-state-hover")},function(){q.removeClass("ui-state-hover")}).append("<span class='ui-icon ui-icon-closethick'></span>");a(i).append(q);if(k){e.dir="rtl";a(".ui-jqdialog-title",i).css("float","right");a(".ui-jqdialog-titlebar-close",i).css("left","0.3em")}else{e.dir="ltr";a(".ui-jqdialog-title",i).css("float","left");a(".ui-jqdialog-titlebar-close",i).css("right","0.3em")}var l=document.createElement("div");
a(l).addClass("ui-jqdialog-content ui-widget-content").attr("id",b.modalcontent);a(l).append(c);e.appendChild(l);a(e).prepend(i);if(h===true)a("body").append(e);else typeof h=="string"?a(h).append(e):a(e).insertBefore(f);a(e).css(j);if(typeof d.jqModal==="undefined")d.jqModal=true;c={};if(a.fn.jqm&&d.jqModal===true){if(d.left===0&&d.top===0&&d.overlay){j=[];j=this.findPos(g);d.left=j[0]+4;d.top=j[1]+4}c.top=d.top+"px";c.left=d.left}else if(d.left!==0||d.top!==0){c.left=d.left;c.top=d.top+"px"}a("a.ui-jqdialog-titlebar-close",
i).click(function(){var p=a("#"+b.themodal).data("onClose")||d.onClose,o=a("#"+b.themodal).data("gbox")||d.gbox;m.hideModal("#"+b.themodal,{gb:o,jqm:d.jqModal,onClose:p});return false});if(d.width===0||!d.width)d.width=300;if(d.height===0||!d.height)d.height=200;if(!d.zIndex){f=a(f).parents("*[role=dialog]").filter(":first").css("z-index");d.zIndex=f?parseInt(f,10)+1:950}f=0;if(k&&c.left&&!h){f=a(d.gbox).width()-(!isNaN(d.width)?parseInt(d.width,10):0)-8;c.left=parseInt(c.left,10)+parseInt(f,10)}if(c.left)c.left+=
"px";a(e).css(a.extend({width:isNaN(d.width)?"auto":d.width+"px",height:isNaN(d.height)?"auto":d.height+"px",zIndex:d.zIndex,overflow:"hidden"},c)).attr({tabIndex:"-1",role:"dialog","aria-labelledby":b.modalhead,"aria-hidden":"true"});if(typeof d.drag=="undefined")d.drag=true;if(typeof d.resize=="undefined")d.resize=true;if(d.drag){a(i).css("cursor","move");if(a.fn.jqDrag)a(e).jqDrag(i);else try{a(e).draggable({handle:a("#"+i.id)})}catch(n){}}if(d.resize)if(a.fn.jqResize){a(e).append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se'></div>");
a("#"+b.themodal).jqResize(".jqResize",b.scrollelm?"#"+b.scrollelm:false)}else try{a(e).resizable({handles:"se, sw",alsoResize:b.scrollelm?"#"+b.scrollelm:false})}catch(r){}d.closeOnEscape===true&&a(e).keydown(function(p){if(p.which==27){p=a("#"+b.themodal).data("onClose")||d.onClose;m.hideModal(this,{gb:d.gbox,jqm:d.jqModal,onClose:p})}})},viewModal:function(b,c){c=a.extend({toTop:true,overlay:10,modal:false,overlayClass:"ui-widget-overlay",onShow:this.showModal,onHide:this.closeModal,gbox:"",jqm:true,
jqM:true},c||{});if(a.fn.jqm&&c.jqm===true)c.jqM?a(b).attr("aria-hidden","false").jqm(c).jqmShow():a(b).attr("aria-hidden","false").jqmShow();else{if(c.gbox!==""){a(".jqgrid-overlay:first",c.gbox).show();a(b).data("gbox",c.gbox)}a(b).show().attr("aria-hidden","false");try{a(":input:visible",b)[0].focus()}catch(d){}}},info_dialog:function(b,c,d,f){var g={width:290,height:"auto",dataheight:"auto",drag:true,resize:false,caption:"<b>"+b+"</b>",left:250,top:170,zIndex:1E3,jqModal:true,modal:false,closeOnEscape:true,
align:"center",buttonalign:"center",buttons:[]};a.extend(g,f||{});var h=g.jqModal,j=this;if(a.fn.jqm&&!h)h=false;b="";if(g.buttons.length>0)for(f=0;f<g.buttons.length;f++){if(typeof g.buttons[f].id=="undefined")g.buttons[f].id="info_button_"+f;b+="<a href='javascript:void(0)' id='"+g.buttons[f].id+"' class='fm-button ui-state-default ui-corner-all'>"+g.buttons[f].text+"</a>"}f=isNaN(g.dataheight)?g.dataheight:g.dataheight+"px";var e="<div id='info_id'>";e+="<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:"+
f+";"+("text-align:"+g.align+";")+"'>"+c+"</div>";e+=d?"<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+g.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a href='javascript:void(0)' id='closedialog' class='fm-button ui-state-default ui-corner-all'>"+d+"</a>"+b+"</div>":b!==""?"<div class='ui-widget-content ui-helper-clearfix' style='text-align:"+g.buttonalign+";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>"+
b+"</div>":"";e+="</div>";try{a("#info_dialog").attr("aria-hidden")=="false"&&this.hideModal("#info_dialog",{jqm:h});a("#info_dialog").remove()}catch(k){}this.createModal({themodal:"info_dialog",modalhead:"info_head",modalcontent:"info_content",scrollelm:"infocnt"},e,g,"","",true);b&&a.each(g.buttons,function(i){a("#"+this.id,"#info_id").bind("click",function(){g.buttons[i].onClick.call(a("#info_dialog"));return false})});a("#closedialog","#info_id").click(function(){j.hideModal("#info_dialog",{jqm:h});
return false});a(".fm-button","#info_dialog").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});a.isFunction(g.beforeOpen)&&g.beforeOpen();this.viewModal("#info_dialog",{onHide:function(i){i.w.hide().remove();i.o&&i.o.remove()},modal:g.modal,jqm:h});a.isFunction(g.afterOpen)&&g.afterOpen();try{a("#info_dialog").focus()}catch(m){}},createEl:function(b,c,d,f,g){function h(l,n){a.isFunction(n.dataInit)&&n.dataInit(l);n.dataEvents&&a.each(n.dataEvents,
function(){this.data!==undefined?a(l).bind(this.type,this.data,this.fn):a(l).bind(this.type,this.fn)});return n}function j(l,n,r){var p=["dataInit","dataEvents","dataUrl","buildSelect","sopt","searchhidden","defaultValue","attr"];if(typeof r!="undefined"&&a.isArray(r))p=a.extend(p,r);a.each(n,function(o,s){a.inArray(o,p)===-1&&a(l).attr(o,s)});n.hasOwnProperty("id")||a(l).attr("id",a.jgrid.randId())}var e="";switch(b){case "textarea":e=document.createElement("textarea");if(f)c.cols||a(e).css({width:"98%"});
else if(!c.cols)c.cols=20;if(!c.rows)c.rows=2;if(d=="&nbsp;"||d=="&#160;"||d.length==1&&d.charCodeAt(0)==160)d="";e.value=d;j(e,c);c=h(e,c);a(e).attr({role:"textbox",multiline:"true"});break;case "checkbox":e=document.createElement("input");e.type="checkbox";if(c.value){b=c.value.split(":");if(d===b[0]){e.checked=true;e.defaultChecked=true}e.value=b[0];a(e).attr("offval",b[1])}else{b=d.toLowerCase();if(b.search(/(false|0|no|off|undefined)/i)<0&&b!==""){e.checked=true;e.defaultChecked=true;e.value=
d}else e.value="on";a(e).attr("offval","off")}j(e,c,["value"]);c=h(e,c);a(e).attr("role","checkbox");break;case "select":e=document.createElement("select");e.setAttribute("role","select");f=[];if(c.multiple===true){b=true;e.multiple="multiple";a(e).attr("aria-multiselectable","true")}else b=false;if(typeof c.dataUrl!="undefined")a.ajax(a.extend({url:c.dataUrl,type:"GET",dataType:"html",context:{elem:e,options:c,vl:d},success:function(l){var n=[],r=this.elem,p=this.vl,o=a.extend({},this.options),s=
o.multiple===true;if(typeof o.buildSelect!="undefined")l=o.buildSelect(l);if(l=a(l).html()){a(r).append(l);j(r,o);o=h(r,o);if(typeof o.size==="undefined")o.size=s?3:1;if(s){n=p.split(",");n=a.map(n,function(t){return a.trim(t)})}else n[0]=a.trim(p);setTimeout(function(){a("option",r).each(function(){a(this).attr("role","option");if(a.inArray(a.trim(a(this).text()),n)>-1||a.inArray(a.trim(a(this).val()),n)>-1)this.selected="selected"})},0)}}},g||{}));else if(c.value){var k;if(typeof c.size==="undefined")c.size=
b?3:1;if(b){f=d.split(",");f=a.map(f,function(l){return a.trim(l)})}if(typeof c.value==="function")c.value=c.value();var m,i;if(typeof c.value==="string"){m=c.value.split(";");for(k=0;k<m.length;k++){i=m[k].split(":");if(i.length>2)i[1]=a.map(i,function(l,n){if(n>0)return l}).join(":");g=document.createElement("option");g.setAttribute("role","option");g.value=i[0];g.innerHTML=i[1];e.appendChild(g);if(!b&&(a.trim(i[0])==a.trim(d)||a.trim(i[1])==a.trim(d)))g.selected="selected";if(b&&(a.inArray(a.trim(i[1]),
f)>-1||a.inArray(a.trim(i[0]),f)>-1))g.selected="selected"}}else if(typeof c.value==="object"){m=c.value;for(k in m)if(m.hasOwnProperty(k)){g=document.createElement("option");g.setAttribute("role","option");g.value=k;g.innerHTML=m[k];e.appendChild(g);if(!b&&(a.trim(k)==a.trim(d)||a.trim(m[k])==a.trim(d)))g.selected="selected";if(b&&(a.inArray(a.trim(m[k]),f)>-1||a.inArray(a.trim(k),f)>-1))g.selected="selected"}}j(e,c,["value"]);c=h(e,c)}break;case "text":case "password":case "button":k=b=="button"?
"button":"textbox";e=document.createElement("input");e.type=b;e.value=d;j(e,c);c=h(e,c);if(b!="button")if(f)c.size||a(e).css({width:"98%"});else if(!c.size)c.size=20;a(e).attr("role",k);break;case "image":case "file":e=document.createElement("input");e.type=b;j(e,c);c=h(e,c);break;case "custom":e=document.createElement("span");try{if(a.isFunction(c.custom_element))if(m=c.custom_element.call(this,d,c)){m=a(m).addClass("customelement").attr({id:c.id,name:c.name});a(e).empty().append(m)}else throw"e2";
else throw"e1";}catch(q){q=="e1"&&this.info_dialog(a.jgrid.errors.errcap,"function 'custom_element' "+a.jgrid.edit.msg.nodefined,a.jgrid.edit.bClose);q=="e2"?this.info_dialog(a.jgrid.errors.errcap,"function 'custom_element' "+a.jgrid.edit.msg.novalue,a.jgrid.edit.bClose):this.info_dialog(a.jgrid.errors.errcap,typeof q==="string"?q:q.message,a.jgrid.edit.bClose)}}return e},checkDate:function(b,c){var d={},f;b=b.toLowerCase();f=b.indexOf("/")!=-1?"/":b.indexOf("-")!=-1?"-":b.indexOf(".")!=-1?".":"/";
b=b.split(f);c=c.split(f);if(c.length!=3)return false;f=-1;for(var g,h=-1,j=-1,e=0;e<b.length;e++){g=isNaN(c[e])?0:parseInt(c[e],10);d[b[e]]=g;g=b[e];if(g.indexOf("y")!=-1)f=e;if(g.indexOf("m")!=-1)j=e;if(g.indexOf("d")!=-1)h=e}g=b[f]=="y"||b[f]=="yyyy"?4:b[f]=="yy"?2:-1;e=function(m){for(var i=1;i<=m;i++){this[i]=31;if(i==4||i==6||i==9||i==11)this[i]=30;if(i==2)this[i]=29}return this}(12);var k;if(f===-1)return false;else{k=d[b[f]].toString();if(g==2&&k.length==1)g=1;if(k.length!=g||d[b[f]]===0&&
c[f]!="00")return false}if(j===-1)return false;else{k=d[b[j]].toString();if(k.length<1||d[b[j]]<1||d[b[j]]>12)return false}if(h===-1)return false;else{k=d[b[h]].toString();if(k.length<1||d[b[h]]<1||d[b[h]]>31||d[b[j]]==2&&d[b[h]]>(d[b[f]]%4===0&&(d[b[f]]%100!==0||d[b[f]]%400===0)?29:28)||d[b[h]]>e[d[b[j]]])return false}return true},isEmpty:function(b){return b.match(/^\s+$/)||b===""?true:false},checkTime:function(b){var c=/^(\d{1,2}):(\d{2})([ap]m)?$/;if(!this.isEmpty(b))if(b=b.match(c)){if(b[3]){if(b[1]<
1||b[1]>12)return false}else if(b[1]>23)return false;if(b[2]>59)return false}else return false;return true},checkValues:function(b,c,d,f,g){var h,j;if(typeof f==="undefined")if(typeof c=="string"){f=0;for(g=d.p.colModel.length;f<g;f++)if(d.p.colModel[f].name==c){h=d.p.colModel[f].editrules;c=f;try{j=d.p.colModel[f].formoptions.label}catch(e){}break}}else{if(c>=0)h=d.p.colModel[c].editrules}else{h=f;j=g===undefined?"_":g}if(h){j||(j=d.p.colNames[c]);if(h.required===true)if(this.isEmpty(b))return[false,
j+": "+a.jgrid.edit.msg.required,""];f=h.required===false?false:true;if(h.number===true)if(!(f===false&&this.isEmpty(b)))if(isNaN(b))return[false,j+": "+a.jgrid.edit.msg.number,""];if(typeof h.minValue!="undefined"&&!isNaN(h.minValue))if(parseFloat(b)<parseFloat(h.minValue))return[false,j+": "+a.jgrid.edit.msg.minValue+" "+h.minValue,""];if(typeof h.maxValue!="undefined"&&!isNaN(h.maxValue))if(parseFloat(b)>parseFloat(h.maxValue))return[false,j+": "+a.jgrid.edit.msg.maxValue+" "+h.maxValue,""];if(h.email===
true)if(!(f===false&&this.isEmpty(b))){g=/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
if(!g.test(b))return[false,j+": "+a.jgrid.edit.msg.email,""]}if(h.integer===true)if(!(f===false&&this.isEmpty(b))){if(isNaN(b))return[false,j+": "+a.jgrid.edit.msg.integer,""];if(b%1!==0||b.indexOf(".")!=-1)return[false,j+": "+a.jgrid.edit.msg.integer,""]}if(h.date===true)if(!(f===false&&this.isEmpty(b))){c=d.p.colModel[c].formatoptions&&d.p.colModel[c].formatoptions.newformat?d.p.colModel[c].formatoptions.newformat:d.p.colModel[c].datefmt||"Y-m-d";if(!this.checkDate(c,b))return[false,j+": "+a.jgrid.edit.msg.date+
" - "+c,""]}if(h.time===true)if(!(f===false&&this.isEmpty(b)))if(!this.checkTime(b))return[false,j+": "+a.jgrid.edit.msg.date+" - hh:mm (am/pm)",""];if(h.url===true)if(!(f===false&&this.isEmpty(b))){g=/^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;if(!g.test(b))return[false,j+": "+a.jgrid.edit.msg.url,""]}if(h.custom===true)if(!(f===false&&this.isEmpty(b)))if(a.isFunction(h.custom_func)){b=h.custom_func.call(d,b,j);return a.isArray(b)?
b:[false,a.jgrid.edit.msg.customarray,""]}else return[false,a.jgrid.edit.msg.customfcheck,""]}return[true,"",""]}})})(jQuery);
(function(a){var d={};a.jgrid.extend({searchGrid:function(f){f=a.extend({recreateFilter:false,drag:true,sField:"searchField",sValue:"searchString",sOper:"searchOper",sFilter:"filters",loadDefaults:true,beforeShowSearch:null,afterShowSearch:null,onInitializeSearch:null,afterRedraw:null,closeAfterSearch:false,closeAfterReset:false,closeOnEscape:false,multipleSearch:false,multipleGroup:false,top:0,left:0,jqModal:true,modal:false,resize:true,width:450,height:"auto",dataheight:"auto",showQuery:false,errorcheck:true,
sopt:null,stringResult:undefined,onClose:null,onSearch:null,onReset:null,toTop:true,overlay:30,columns:[],tmplNames:null,tmplFilters:null,tmplLabel:" Template: ",showOnLoad:false,layer:null},a.jgrid.search,f||{});return this.each(function(){function b(){if(a.isFunction(f.beforeShowSearch)){x=f.beforeShowSearch(a("#"+r));if(typeof x==="undefined")x=true}if(x){a.jgrid.viewModal("#"+D.themodal,{gbox:"#gbox_"+r,jqm:f.jqModal,modal:f.modal,overlay:f.overlay,toTop:f.toTop});a.isFunction(f.afterShowSearch)&&
f.afterShowSearch(a("#"+r))}}var e=this;if(e.grid){var r="fbox_"+e.p.id,x=true,D={themodal:"searchmod"+r,modalhead:"searchhd"+r,modalcontent:"searchcnt"+r,scrollelm:r},F=e.p.postData[f.sFilter];if(typeof F==="string")F=a.jgrid.parse(F);f.recreateFilter===true&&a("#"+D.themodal).remove();if(a("#"+D.themodal).html()!==null)b();else{var y=a("<div><div id='"+r+"' class='searchFilter' style='overflow:auto'></div></div>").insertBefore("#gview_"+e.p.id),o="left",k="";if(e.p.direction=="rtl"){o="right";k=
" style='text-align:left'";y.attr("dir","rtl")}if(a.isFunction(f.onInitializeSearch))f.onInitializeSearch(a("#"+r));var s=a.extend([],e.p.colModel),N="<a href='javascript:void(0)' id='"+r+"_search' class='fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset'><span class='ui-icon ui-icon-search'></span>"+f.Find+"</a>",c="<a href='javascript:void(0)' id='"+r+"_reset' class='fm-button ui-state-default ui-corner-all fm-button-icon-left ui-search'><span class='ui-icon ui-icon-arrowreturnthick-1-w'></span>"+
f.Reset+"</a>",t="",p="",j,n=false,G=-1;if(f.showQuery)t="<a href='javascript:void(0)' id='"+r+"_query' class='fm-button ui-state-default ui-corner-all fm-button-icon-left'><span class='ui-icon ui-icon-comment'></span>Query</a>";if(f.columns.length)s=f.columns;else a.each(s,function(u,E){if(!E.label)E.label=e.p.colNames[u];if(!n){var B=typeof E.search==="undefined"?true:E.search,m=E.hidden===true;if(E.searchoptions&&E.searchoptions.searchhidden===true&&B||B&&!m){n=true;j=E.index||E.name;G=u}}});if(!F&&
j||f.multipleSearch===false){var J="eq";if(G>=0&&s[G].searchoptions&&s[G].searchoptions.sopt)J=s[G].searchoptions.sopt[0];else if(f.sopt&&f.sopt.length)J=f.sopt[0];F={groupOp:"AND",rules:[{field:j,op:J,data:""}]}}n=false;if(f.tmplNames&&f.tmplNames.length){n=true;p=f.tmplLabel;p+="<select class='ui-template'>";p+="<option value='default'>Default</option>";a.each(f.tmplNames,function(u,E){p+="<option value='"+u+"'>"+E+"</option>"});p+="</select>"}o="<table class='EditTable' style='border:0px none;margin-top:5px' id='"+
r+"_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='EditButton' style='text-align:"+o+"'>"+c+p+"</td><td class='EditButton' "+k+">"+t+N+"</td></tr></tbody></table>";a("#"+r).jqFilter({columns:s,filter:f.loadDefaults?F:null,showQuery:f.showQuery,errorcheck:f.errorcheck,sopt:f.sopt,groupButton:f.multipleGroup,ruleButtons:f.multipleSearch,afterRedraw:f.afterRedraw,_gridsopt:a.jgrid.search.odata,onChange:function(){this.p.showQuery&&a(".query",
this).html(this.toUserFriendlyString())},direction:e.p.direction});y.append(o);n&&f.tmplFilters&&f.tmplFilters.length&&a(".ui-template",y).bind("change",function(){var u=a(this).val();u=="default"?a("#"+r).jqFilter("addFilter",F):a("#"+r).jqFilter("addFilter",f.tmplFilters[parseInt(u,10)]);return false});if(f.multipleGroup===true)f.multipleSearch=true;if(a.isFunction(f.onInitializeSearch))f.onInitializeSearch(a("#"+r));f.gbox="#gbox_"+r;f.layer?a.jgrid.createModal(D,y,f,"#gview_"+e.p.id,a("#gbox_"+
e.p.id)[0],"#"+f.layer,{position:"relative"}):a.jgrid.createModal(D,y,f,"#gview_"+e.p.id,a("#gbox_"+e.p.id)[0]);t&&a("#"+r+"_query").bind("click",function(){a(".queryresult",y).toggle();return false});if(f.stringResult===undefined)f.stringResult=f.multipleSearch;a("#"+r+"_search").bind("click",function(){var u=a("#"+r),E={},B,m=u.jqFilter("filterData");if(f.errorcheck){u[0].hideError();f.showQuery||u.jqFilter("toSQLString");if(u[0].p.error){u[0].showError();return false}}if(f.stringResult){try{B=
xmlJsonClass.toJson(m,"","",false)}catch(z){try{B=JSON.stringify(m)}catch(h){}}if(typeof B==="string"){E[f.sFilter]=B;a.each([f.sField,f.sValue,f.sOper],function(){E[this]=""})}}else if(f.multipleSearch){E[f.sFilter]=m;a.each([f.sField,f.sValue,f.sOper],function(){E[this]=""})}else{E[f.sField]=m.rules[0].field;E[f.sValue]=m.rules[0].data;E[f.sOper]=m.rules[0].op;E[f.sFilter]=""}e.p.search=true;a.extend(e.p.postData,E);if(a.isFunction(f.onSearch))f.onSearch();a(e).trigger("reloadGrid",[{page:1}]);
f.closeAfterSearch&&a.jgrid.hideModal("#"+D.themodal,{gb:"#gbox_"+e.p.id,jqm:f.jqModal,onClose:f.onClose});return false});a("#"+r+"_reset").bind("click",function(){var u={},E=a("#"+r);e.p.search=false;if(f.multipleSearch===false)u[f.sField]=u[f.sValue]=u[f.sOper]="";else u[f.sFilter]="";E[0].resetFilter();n&&a(".ui-template",y).val("default");a.extend(e.p.postData,u);if(a.isFunction(f.onReset))f.onReset();a(e).trigger("reloadGrid",[{page:1}]);return false});b();a(".fm-button:not(.ui-state-disabled)",
y).hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")})}}})},editGridRow:function(f,b){b=a.extend({top:0,left:0,width:300,height:"auto",dataheight:"auto",modal:false,overlay:30,drag:true,resize:true,url:null,mtype:"POST",clearAfterAdd:true,closeAfterEdit:false,reloadAfterSubmit:true,onInitializeForm:null,beforeInitData:null,beforeShowForm:null,afterShowForm:null,beforeSubmit:null,afterSubmit:null,onclickSubmit:null,afterComplete:null,onclickPgButtons:null,
afterclickPgButtons:null,editData:{},recreateForm:false,jqModal:true,closeOnEscape:false,addedrow:"first",topinfo:"",bottominfo:"",saveicon:[],closeicon:[],savekey:[false,13],navkeys:[false,38,40],checkOnSubmit:false,checkOnUpdate:false,_savedData:{},processing:false,onClose:null,ajaxEditOptions:{},serializeEditData:null,viewPagerButtons:true},a.jgrid.edit,b||{});d[a(this)[0].p.id]=b;return this.each(function(){function e(){a("#"+j+" > tbody > tr > td > .FormElement").each(function(){var g=a(".customelement",
this);if(g.length){var l=a(g[0]).attr("name");a.each(c.p.colModel,function(){if(this.name===l&&this.editoptions&&a.isFunction(this.editoptions.custom_value)){try{h[l]=this.editoptions.custom_value(a("#"+a.jgrid.jqID(l),"#"+j),"get");if(h[l]===undefined)throw"e1";}catch(q){q==="e1"?a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,q.message,jQuery.jgrid.edit.bClose)}return true}})}else{switch(a(this).get(0).type){case "checkbox":if(a(this).is(":checked"))h[this.name]=
a(this).val();else{g=a(this).attr("offval");h[this.name]=g}break;case "select-one":h[this.name]=a("option:selected",this).val();P[this.name]=a("option:selected",this).text();break;case "select-multiple":h[this.name]=a(this).val();h[this.name]=h[this.name]?h[this.name].join(","):"";var w=[];a("option:selected",this).each(function(q,H){w[q]=a(H).text()});P[this.name]=w.join(",");break;case "password":case "text":case "textarea":case "button":h[this.name]=a(this).val()}if(c.p.autoencode)h[this.name]=
a.jgrid.htmlEncode(h[this.name])}});return true}function r(g,l,w,q){var H,C,v,K=0,A,Q,I,U=[],M=false,aa="",S;for(S=1;S<=q;S++)aa+="<td class='CaptionTD'>&#160;</td><td class='DataTD'>&#160;</td>";if(g!="_empty")M=a(l).jqGrid("getInd",g);a(l.p.colModel).each(function(T){H=this.name;Q=(C=this.editrules&&this.editrules.edithidden===true?false:this.hidden===true?true:false)?"style='display:none'":"";if(H!=="cb"&&H!=="subgrid"&&this.editable===true&&H!=="rn"){if(M===false)A="";else if(H==l.p.ExpandColumn&&
l.p.treeGrid===true)A=a("td:eq("+T+")",l.rows[M]).text();else{try{A=a.unformat(a("td:eq("+T+")",l.rows[M]),{rowId:g,colModel:this},T)}catch(ga){A=this.edittype&&this.edittype=="textarea"?a("td:eq("+T+")",l.rows[M]).text():a("td:eq("+T+")",l.rows[M]).html()}if(!A||A=="&nbsp;"||A=="&#160;"||A.length==1&&A.charCodeAt(0)==160)A=""}var Y=a.extend({},this.editoptions||{},{id:H,name:H}),Z=a.extend({},{elmprefix:"",elmsuffix:"",rowabove:false,rowcontent:""},this.formoptions||{}),ea=parseInt(Z.rowpos,10)||
K+1,ha=parseInt((parseInt(Z.colpos,10)||1)*2,10);if(g=="_empty"&&Y.defaultValue)A=a.isFunction(Y.defaultValue)?Y.defaultValue():Y.defaultValue;if(!this.edittype)this.edittype="text";if(c.p.autoencode)A=a.jgrid.htmlDecode(A);I=a.jgrid.createEl(this.edittype,Y,A,false,a.extend({},a.jgrid.ajaxOptions,l.p.ajaxSelectOptions||{}));if(A===""&&this.edittype=="checkbox")A=a(I).attr("offval");if(A===""&&this.edittype=="select")A=a("option:eq(0)",I).text();if(d[c.p.id].checkOnSubmit||d[c.p.id].checkOnUpdate)d[c.p.id]._savedData[H]=
A;a(I).addClass("FormElement");if(this.edittype=="text"||this.edittype=="textarea")a(I).addClass("ui-widget-content ui-corner-all");v=a(w).find("tr[rowpos="+ea+"]");if(Z.rowabove){Y=a("<tr><td class='contentinfo' colspan='"+q*2+"'>"+Z.rowcontent+"</td></tr>");a(w).append(Y);Y[0].rp=ea}if(v.length===0){v=a("<tr "+Q+" rowpos='"+ea+"'></tr>").addClass("FormData").attr("id","tr_"+H);a(v).append(aa);a(w).append(v);v[0].rp=ea}a("td:eq("+(ha-2)+")",v[0]).html(typeof Z.label==="undefined"?l.p.colNames[T]:
Z.label);a("td:eq("+(ha-1)+")",v[0]).append(Z.elmprefix).append(I).append(Z.elmsuffix);U[K]=T;K++}});if(K>0){S=a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+(q*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='"+l.p.id+"_id' value='"+g+"'/></td></tr>");S[0].rp=K+999;a(w).append(S);if(d[c.p.id].checkOnSubmit||d[c.p.id].checkOnUpdate)d[c.p.id]._savedData[l.p.id+"_id"]=g}return U}function x(g,l,w){var q,H=0,C,v,K,A,Q;if(d[c.p.id].checkOnSubmit||
d[c.p.id].checkOnUpdate){d[c.p.id]._savedData={};d[c.p.id]._savedData[l.p.id+"_id"]=g}var I=l.p.colModel;if(g=="_empty"){a(I).each(function(){q=this.name;K=a.extend({},this.editoptions||{});if((v=a("#"+a.jgrid.jqID(q),"#"+w))&&v.length&&v[0]!==null){A="";if(K.defaultValue){A=a.isFunction(K.defaultValue)?K.defaultValue():K.defaultValue;if(v[0].type=="checkbox"){Q=A.toLowerCase();if(Q.search(/(false|0|no|off|undefined)/i)<0&&Q!==""){v[0].checked=true;v[0].defaultChecked=true;v[0].value=A}else{v[0].checked=
false;v[0].defaultChecked=false}}else v.val(A)}else if(v[0].type=="checkbox"){v[0].checked=false;v[0].defaultChecked=false;A=a(v).attr("offval")}else if(v[0].type&&v[0].type.substr(0,6)=="select")v[0].selectedIndex=0;else v.val(A);if(d[c.p.id].checkOnSubmit===true||d[c.p.id].checkOnUpdate)d[c.p.id]._savedData[q]=A}});a("#id_g","#"+w).val(g)}else{var U=a(l).jqGrid("getInd",g,true);if(U){a("td",U).each(function(M){q=I[M].name;if(q!=="cb"&&q!=="subgrid"&&q!=="rn"&&I[M].editable===true){if(q==l.p.ExpandColumn&&
l.p.treeGrid===true)C=a(this).text();else try{C=a.unformat(a(this),{rowId:g,colModel:I[M]},M)}catch(aa){C=I[M].edittype=="textarea"?a(this).text():a(this).html()}if(c.p.autoencode)C=a.jgrid.htmlDecode(C);if(d[c.p.id].checkOnSubmit===true||d[c.p.id].checkOnUpdate)d[c.p.id]._savedData[q]=C;q=a.jgrid.jqID(q);switch(I[M].edittype){case "password":case "text":case "button":case "image":case "textarea":if(C=="&nbsp;"||C=="&#160;"||C.length==1&&C.charCodeAt(0)==160)C="";a("#"+q,"#"+w).val(C);break;case "select":var S=
C.split(",");S=a.map(S,function(ga){return a.trim(ga)});a("#"+q+" option","#"+w).each(function(){this.selected=!I[M].editoptions.multiple&&(a.trim(C)==a.trim(a(this).text())||S[0]==a.trim(a(this).text())||S[0]==a.trim(a(this).val()))?true:I[M].editoptions.multiple?a.inArray(a.trim(a(this).text()),S)>-1||a.inArray(a.trim(a(this).val()),S)>-1?true:false:false});break;case "checkbox":C+="";if(I[M].editoptions&&I[M].editoptions.value)if(I[M].editoptions.value.split(":")[0]==C){a("#"+q,"#"+w)[c.p.useProp?
"prop":"attr"]("checked",true);a("#"+q,"#"+w)[c.p.useProp?"prop":"attr"]("defaultChecked",true)}else{a("#"+q,"#"+w)[c.p.useProp?"prop":"attr"]("checked",false);a("#"+q,"#"+w)[c.p.useProp?"prop":"attr"]("defaultChecked",false)}else{C=C.toLowerCase();if(C.search(/(false|0|no|off|undefined)/i)<0&&C!==""){a("#"+q,"#"+w)[c.p.useProp?"prop":"attr"]("checked",true);a("#"+q,"#"+w)[c.p.useProp?"prop":"attr"]("defaultChecked",true)}else{a("#"+q,"#"+w)[c.p.useProp?"prop":"attr"]("checked",false);a("#"+q,"#"+
w)[c.p.useProp?"prop":"attr"]("defaultChecked",false)}}break;case "custom":try{if(I[M].editoptions&&a.isFunction(I[M].editoptions.custom_value))I[M].editoptions.custom_value(a("#"+q,"#"+w),"set",C);else throw"e1";}catch(T){T=="e1"?a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose):a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,T.message,jQuery.jgrid.edit.bClose)}}H++}});H>0&&a("#id_g","#"+j).val(g)}}}function D(){var g,l=
[true,"",""],w={},q=c.p.prmNames,H,C,v,K;if(a.isFunction(d[c.p.id].beforeCheckValues)){var A=d[c.p.id].beforeCheckValues(h,a("#"+p),h[c.p.id+"_id"]=="_empty"?q.addoper:q.editoper);if(A&&typeof A==="object")h=A}for(v in h)if(h.hasOwnProperty(v)){l=a.jgrid.checkValues(h[v],v,c);if(l[0]===false)break}y();if(l[0]){if(a.isFunction(d[c.p.id].onclickSubmit))w=d[c.p.id].onclickSubmit(d[c.p.id],h)||{};if(a.isFunction(d[c.p.id].beforeSubmit))l=d[c.p.id].beforeSubmit(h,a("#"+p))}if(l[0]&&!d[c.p.id].processing){d[c.p.id].processing=
true;a("#sData","#"+j+"_2").addClass("ui-state-active");C=q.oper;H=q.id;h[C]=a.trim(h[c.p.id+"_id"])=="_empty"?q.addoper:q.editoper;if(h[C]!=q.addoper)h[H]=h[c.p.id+"_id"];else if(h[H]===undefined)h[H]=h[c.p.id+"_id"];delete h[c.p.id+"_id"];h=a.extend(h,d[c.p.id].editData,w);if(c.p.treeGrid===true){if(h[C]==q.addoper){K=a(c).jqGrid("getGridParam","selrow");h[c.p.treeGridModel=="adjacency"?c.p.treeReader.parent_id_field:"parent_id"]=K}for(i in c.p.treeReader){w=c.p.treeReader[i];if(h.hasOwnProperty(w))h[C]==
q.addoper&&i==="parent_id_field"||delete h[w]}}h[H]=a.jgrid.stripPref(c.p.idPrefix,h[H]);w=a.extend({url:d[c.p.id].url?d[c.p.id].url:a(c).jqGrid("getGridParam","editurl"),type:d[c.p.id].mtype,data:a.isFunction(d[c.p.id].serializeEditData)?d[c.p.id].serializeEditData(h):h,complete:function(Q,I){h[H]=c.p.idPrefix+h[H];if(I!="success"){l[0]=false;l[1]=a.isFunction(d[c.p.id].errorTextFormat)?d[c.p.id].errorTextFormat(Q):I+" Status: '"+Q.statusText+"'. Error code: "+Q.status}else if(a.isFunction(d[c.p.id].afterSubmit))l=
d[c.p.id].afterSubmit(Q,h);if(l[0]===false){a("#FormError>td","#"+j).html(l[1]);a("#FormError","#"+j).show()}else{a.each(c.p.colModel,function(){if(P[this.name]&&this.formatter&&this.formatter=="select")try{delete P[this.name]}catch(aa){}});h=a.extend(h,P);c.p.autoencode&&a.each(h,function(aa,S){h[aa]=a.jgrid.htmlDecode(S)});if(h[C]==q.addoper){l[2]||(l[2]=a.jgrid.randId());h[H]=l[2];if(d[c.p.id].closeAfterAdd){if(d[c.p.id].reloadAfterSubmit)a(c).trigger("reloadGrid");else if(c.p.treeGrid===true)a(c).jqGrid("addChildNode",
l[2],K,h);else{a(c).jqGrid("addRowData",l[2],h,b.addedrow);a(c).jqGrid("setSelection",l[2])}a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose})}else if(d[c.p.id].clearAfterAdd){if(d[c.p.id].reloadAfterSubmit)a(c).trigger("reloadGrid");else c.p.treeGrid===true?a(c).jqGrid("addChildNode",l[2],K,h):a(c).jqGrid("addRowData",l[2],h,b.addedrow);x("_empty",c,p)}else if(d[c.p.id].reloadAfterSubmit)a(c).trigger("reloadGrid");else c.p.treeGrid===true?a(c).jqGrid("addChildNode",
l[2],K,h):a(c).jqGrid("addRowData",l[2],h,b.addedrow)}else{if(d[c.p.id].reloadAfterSubmit){a(c).trigger("reloadGrid");d[c.p.id].closeAfterEdit||setTimeout(function(){a(c).jqGrid("setSelection",h[H])},1E3)}else c.p.treeGrid===true?a(c).jqGrid("setTreeRow",h[H],h):a(c).jqGrid("setRowData",h[H],h);d[c.p.id].closeAfterEdit&&a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose})}if(a.isFunction(d[c.p.id].afterComplete)){g=Q;setTimeout(function(){d[c.p.id].afterComplete(g,
h,a("#"+p));g=null},500)}if(d[c.p.id].checkOnSubmit||d[c.p.id].checkOnUpdate){a("#"+p).data("disabled",false);if(d[c.p.id]._savedData[c.p.id+"_id"]!="_empty")for(var U in d[c.p.id]._savedData)if(h[U])d[c.p.id]._savedData[U]=h[U]}}d[c.p.id].processing=false;a("#sData","#"+j+"_2").removeClass("ui-state-active");try{a(":input:visible","#"+p)[0].focus()}catch(M){}}},a.jgrid.ajaxOptions,d[c.p.id].ajaxEditOptions);if(!w.url&&!d[c.p.id].useDataProxy)if(a.isFunction(c.p.dataProxy))d[c.p.id].useDataProxy=
true;else{l[0]=false;l[1]+=" "+a.jgrid.errors.nourl}if(l[0])if(d[c.p.id].useDataProxy){v=c.p.dataProxy.call(c,w,"set_"+c.p.id);if(typeof v=="undefined")v=[true,""];if(v[0]===false){l[0]=false;l[1]=v[1]||"Error deleting the selected row!"}else{w.data.oper==q.addoper&&d[c.p.id].closeAfterAdd&&a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose});w.data.oper==q.editoper&&d[c.p.id].closeAfterEdit&&a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose})}}else a.ajax(w)}if(l[0]===
false){a("#FormError>td","#"+j).html(l[1]);a("#FormError","#"+j).show()}}function F(g,l){var w=false,q;for(q in g)if(g[q]!=l[q]){w=true;break}return w}function y(){a.each(c.p.colModel,function(g,l){if(l.editoptions&&l.editoptions.NullIfEmpty===true)if(h.hasOwnProperty(l.name)&&h[l.name]=="")h[l.name]="null"})}function o(){var g=true;a("#FormError","#"+j).hide();if(d[c.p.id].checkOnUpdate){h={};P={};e();O=a.extend({},h,P);if(V=F(O,d[c.p.id]._savedData)){a("#"+p).data("disabled",true);a(".confirm",
"#"+n.themodal).show();g=false}}return g}function k(){if(f!=="_empty"&&typeof c.p.savedRow!=="undefined"&&c.p.savedRow.length>0&&a.isFunction(a.fn.jqGrid.restoreRow))for(var g=0;g<c.p.savedRow.length;g++)if(c.p.savedRow[g].id==f){a(c).jqGrid("restoreRow",f);break}}function s(g,l){g===0?a("#pData","#"+j+"_2").addClass("ui-state-disabled"):a("#pData","#"+j+"_2").removeClass("ui-state-disabled");g==l?a("#nData","#"+j+"_2").addClass("ui-state-disabled"):a("#nData","#"+j+"_2").removeClass("ui-state-disabled")}
function N(){var g=a(c).jqGrid("getDataIDs"),l=a("#id_g","#"+j).val();return[a.inArray(l,g),g]}var c=this;if(c.grid&&f){var t=c.p.id,p="FrmGrid_"+t,j="TblGrid_"+t,n={themodal:"editmod"+t,modalhead:"edithd"+t,modalcontent:"editcnt"+t,scrollelm:p},G=a.isFunction(d[c.p.id].beforeShowForm)?d[c.p.id].beforeShowForm:false,J=a.isFunction(d[c.p.id].afterShowForm)?d[c.p.id].afterShowForm:false,u=a.isFunction(d[c.p.id].beforeInitData)?d[c.p.id].beforeInitData:false,E=a.isFunction(d[c.p.id].onInitializeForm)?
d[c.p.id].onInitializeForm:false,B=true,m=1,z=0,h,P,O,V;if(f==="new"){f="_empty";b.caption=d[c.p.id].addCaption}else b.caption=d[c.p.id].editCaption;b.recreateForm===true&&a("#"+n.themodal).html()!==null&&a("#"+n.themodal).remove();var R=true;if(b.checkOnUpdate&&b.jqModal&&!b.modal)R=false;if(a("#"+n.themodal).html()!==null){if(u){B=u(a("#"+p));if(typeof B=="undefined")B=true}if(B===false)return;k();a(".ui-jqdialog-title","#"+n.modalhead).html(b.caption);a("#FormError","#"+j).hide();if(d[c.p.id].topinfo){a(".topinfo",
"#"+j).html(d[c.p.id].topinfo);a(".tinfo","#"+j).show()}else a(".tinfo","#"+j).hide();if(d[c.p.id].bottominfo){a(".bottominfo","#"+j+"_2").html(d[c.p.id].bottominfo);a(".binfo","#"+j+"_2").show()}else a(".binfo","#"+j+"_2").hide();x(f,c,p);f=="_empty"||!d[c.p.id].viewPagerButtons?a("#pData, #nData","#"+j+"_2").hide():a("#pData, #nData","#"+j+"_2").show();if(d[c.p.id].processing===true){d[c.p.id].processing=false;a("#sData","#"+j+"_2").removeClass("ui-state-active")}if(a("#"+p).data("disabled")===
true){a(".confirm","#"+n.themodal).hide();a("#"+p).data("disabled",false)}G&&G(a("#"+p));a("#"+n.themodal).data("onClose",d[c.p.id].onClose);a.jgrid.viewModal("#"+n.themodal,{gbox:"#gbox_"+t,jqm:b.jqModal,jqM:false,overlay:b.overlay,modal:b.modal});R||a(".jqmOverlay").click(function(){if(!o())return false;a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose});return false});J&&J(a("#"+p))}else{var L=isNaN(b.dataheight)?b.dataheight:b.dataheight+"px";L=a("<form name='FormPost' id='"+
p+"' class='FormGrid' onSubmit='return false;' style='width:100%;overflow:auto;position:relative;height:"+L+";'></form>").data("disabled",false);var W=a("<table id='"+j+"' class='EditTable' cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>");if(u){B=u(a("#"+p));if(typeof B=="undefined")B=true}if(B===false)return;k();a(c.p.colModel).each(function(){var g=this.formoptions;m=Math.max(m,g?g.colpos||0:0);z=Math.max(z,g?g.rowpos||0:0)});a(L).append(W);u=a("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='"+
m*2+"'></td></tr>");u[0].rp=0;a(W).append(u);u=a("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='"+m*2+"'>"+d[c.p.id].topinfo+"</td></tr>");u[0].rp=0;a(W).append(u);B=(u=c.p.direction=="rtl"?true:false)?"nData":"pData";var X=u?"pData":"nData";r(f,c,W,m);B="<a href='javascript:void(0)' id='"+B+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>";X="<a href='javascript:void(0)' id='"+X+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>";
var ba="<a href='javascript:void(0)' id='sData' class='fm-button ui-state-default ui-corner-all'>"+b.bSubmit+"</a>",$="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+b.bCancel+"</a>";B="<table border='0' cellspacing='0' cellpadding='0' class='EditTable' id='"+j+"_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr id='Act_Buttons'><td class='navButton'>"+(u?X+B:B+X)+"</td><td class='EditButton'>"+ba+$+"</td></tr>";
B+="<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>"+d[c.p.id].bottominfo+"</td></tr>";B+="</tbody></table>";if(z>0){var ca=[],da={};a.each(a(W)[0].rows,function(g,l){ca[g]=l});ca.sort(function(g,l){if(g.rp>l.rp)return 1;if(g.rp<l.rp)return-1;return 0});a.each(ca,function(g,l){da.html+=l});a("tbody",W).append(da.html)}b.gbox="#gbox_"+t;var fa=false;if(b.closeOnEscape===true){b.closeOnEscape=false;fa=true}L=a("<span></span>").append(L).append(B);a.jgrid.createModal(n,L,b,
"#gview_"+c.p.id,a("#gbox_"+c.p.id)[0]);if(u){a("#pData, #nData","#"+j+"_2").css("float","right");a(".EditButton","#"+j+"_2").css("text-align","left")}d[c.p.id].topinfo&&a(".tinfo","#"+j).show();d[c.p.id].bottominfo&&a(".binfo","#"+j+"_2").show();B=L=null;a("#"+n.themodal).keydown(function(g){var l=g.target;if(a("#"+p).data("disabled")===true)return false;if(d[c.p.id].savekey[0]===true&&g.which==d[c.p.id].savekey[1])if(l.tagName!="TEXTAREA"){a("#sData","#"+j+"_2").trigger("click");return false}if(g.which===
27){if(!o())return false;fa&&a.jgrid.hideModal(this,{gb:b.gbox,jqm:b.jqModal,onClose:d[c.p.id].onClose});return false}if(d[c.p.id].navkeys[0]===true){if(a("#id_g","#"+j).val()=="_empty")return true;if(g.which==d[c.p.id].navkeys[1]){a("#pData","#"+j+"_2").trigger("click");return false}if(g.which==d[c.p.id].navkeys[2]){a("#nData","#"+j+"_2").trigger("click");return false}}});if(b.checkOnUpdate){a("a.ui-jqdialog-titlebar-close span","#"+n.themodal).removeClass("jqmClose");a("a.ui-jqdialog-titlebar-close",
"#"+n.themodal).unbind("click").click(function(){if(!o())return false;a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose});return false})}b.saveicon=a.extend([true,"left","ui-icon-disk"],b.saveicon);b.closeicon=a.extend([true,"left","ui-icon-close"],b.closeicon);if(b.saveicon[0]===true)a("#sData","#"+j+"_2").addClass(b.saveicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.saveicon[2]+"'></span>");if(b.closeicon[0]===
true)a("#cData","#"+j+"_2").addClass(b.closeicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.closeicon[2]+"'></span>");if(d[c.p.id].checkOnSubmit||d[c.p.id].checkOnUpdate){ba="<a href='javascript:void(0)' id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+b.bYes+"</a>";X="<a href='javascript:void(0)' id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+b.bNo+"</a>";$="<a href='javascript:void(0)' id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>"+
b.bExit+"</a>";L=b.zIndex||999;L++;a("<div class='ui-widget-overlay jqgrid-overlay confirm' style='z-index:"+L+";display:none;'>&#160;"+(a.browser.msie&&a.browser.version==6?'<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>':"")+"</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:"+(L+1)+"'>"+b.saveData+"<br/><br/>"+ba+X+$+"</div>").insertAfter("#"+p);a("#sNew","#"+n.themodal).click(function(){D();a("#"+
p).data("disabled",false);a(".confirm","#"+n.themodal).hide();return false});a("#nNew","#"+n.themodal).click(function(){a(".confirm","#"+n.themodal).hide();a("#"+p).data("disabled",false);setTimeout(function(){a(":input","#"+p)[0].focus()},0);return false});a("#cNew","#"+n.themodal).click(function(){a(".confirm","#"+n.themodal).hide();a("#"+p).data("disabled",false);a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose});return false})}E&&E(a("#"+p));f=="_empty"||
!d[c.p.id].viewPagerButtons?a("#pData,#nData","#"+j+"_2").hide():a("#pData,#nData","#"+j+"_2").show();G&&G(a("#"+p));a("#"+n.themodal).data("onClose",d[c.p.id].onClose);a.jgrid.viewModal("#"+n.themodal,{gbox:"#gbox_"+t,jqm:b.jqModal,overlay:b.overlay,modal:b.modal});R||a(".jqmOverlay").click(function(){if(!o())return false;a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose});return false});J&&J(a("#"+p));a(".fm-button","#"+n.themodal).hover(function(){a(this).addClass("ui-state-hover")},
function(){a(this).removeClass("ui-state-hover")});a("#sData","#"+j+"_2").click(function(){h={};P={};a("#FormError","#"+j).hide();e();if(h[c.p.id+"_id"]=="_empty")D();else if(b.checkOnSubmit===true){O=a.extend({},h,P);if(V=F(O,d[c.p.id]._savedData)){a("#"+p).data("disabled",true);a(".confirm","#"+n.themodal).show()}else D()}else D();return false});a("#cData","#"+j+"_2").click(function(){if(!o())return false;a.jgrid.hideModal("#"+n.themodal,{gb:"#gbox_"+t,jqm:b.jqModal,onClose:d[c.p.id].onClose});
return false});a("#nData","#"+j+"_2").click(function(){if(!o())return false;a("#FormError","#"+j).hide();var g=N();g[0]=parseInt(g[0],10);if(g[0]!=-1&&g[1][g[0]+1]){if(a.isFunction(b.onclickPgButtons))b.onclickPgButtons("next",a("#"+p),g[1][g[0]]);x(g[1][g[0]+1],c,p);a(c).jqGrid("setSelection",g[1][g[0]+1]);a.isFunction(b.afterclickPgButtons)&&b.afterclickPgButtons("next",a("#"+p),g[1][g[0]+1]);s(g[0]+1,g[1].length-1)}return false});a("#pData","#"+j+"_2").click(function(){if(!o())return false;a("#FormError",
"#"+j).hide();var g=N();if(g[0]!=-1&&g[1][g[0]-1]){if(a.isFunction(b.onclickPgButtons))b.onclickPgButtons("prev",a("#"+p),g[1][g[0]]);x(g[1][g[0]-1],c,p);a(c).jqGrid("setSelection",g[1][g[0]-1]);a.isFunction(b.afterclickPgButtons)&&b.afterclickPgButtons("prev",a("#"+p),g[1][g[0]-1]);s(g[0]-1,g[1].length-1)}return false})}G=N();s(G[0],G[1].length-1)}})},viewGridRow:function(f,b){b=a.extend({top:0,left:0,width:0,height:"auto",dataheight:"auto",modal:false,overlay:30,drag:true,resize:true,jqModal:true,
closeOnEscape:false,labelswidth:"30%",closeicon:[],navkeys:[false,38,40],onClose:null,beforeShowForm:null,beforeInitData:null,viewPagerButtons:true},a.jgrid.view,b||{});return this.each(function(){function e(){if(b.closeOnEscape===true||b.navkeys[0]===true)setTimeout(function(){a(".ui-jqdialog-titlebar-close","#"+N.modalhead).focus()},0)}function r(m,z,h,P){for(var O,V,R,L=0,W,X,ba=[],$=false,ca="<td class='CaptionTD form-view-label ui-widget-content' width='"+b.labelswidth+"'>&#160;</td><td class='DataTD form-view-data ui-helper-reset ui-widget-content'>&#160;</td>",
da="",fa=["integer","number","currency"],g=0,l=0,w,q,H,C=1;C<=P;C++)da+=C==1?ca:"<td class='CaptionTD form-view-label ui-widget-content'>&#160;</td><td class='DataTD form-view-data ui-widget-content'>&#160;</td>";a(z.p.colModel).each(function(){V=this.editrules&&this.editrules.edithidden===true?false:this.hidden===true?true:false;if(!V&&this.align==="right")if(this.formatter&&a.inArray(this.formatter,fa)!==-1)g=Math.max(g,parseInt(this.width,10));else l=Math.max(l,parseInt(this.width,10))});w=g!==
0?g:l!==0?l:0;$=a(z).jqGrid("getInd",m);a(z.p.colModel).each(function(v){O=this.name;q=false;X=(V=this.editrules&&this.editrules.edithidden===true?false:this.hidden===true?true:false)?"style='display:none'":"";H=typeof this.viewable!="boolean"?true:this.viewable;if(O!=="cb"&&O!=="subgrid"&&O!=="rn"&&H){W=$===false?"":O==z.p.ExpandColumn&&z.p.treeGrid===true?a("td:eq("+v+")",z.rows[$]).text():a("td:eq("+v+")",z.rows[$]).html();q=this.align==="right"&&w!==0?true:false;a.extend({},this.editoptions||
{},{id:O,name:O});var K=a.extend({},{rowabove:false,rowcontent:""},this.formoptions||{}),A=parseInt(K.rowpos,10)||L+1,Q=parseInt((parseInt(K.colpos,10)||1)*2,10);if(K.rowabove){var I=a("<tr><td class='contentinfo' colspan='"+P*2+"'>"+K.rowcontent+"</td></tr>");a(h).append(I);I[0].rp=A}R=a(h).find("tr[rowpos="+A+"]");if(R.length===0){R=a("<tr "+X+" rowpos='"+A+"'></tr>").addClass("FormData").attr("id","trv_"+O);a(R).append(da);a(h).append(R);R[0].rp=A}a("td:eq("+(Q-2)+")",R[0]).html("<b>"+(typeof K.label===
"undefined"?z.p.colNames[v]:K.label)+"</b>");a("td:eq("+(Q-1)+")",R[0]).append("<span>"+W+"</span>").attr("id","v_"+O);q&&a("td:eq("+(Q-1)+") span",R[0]).css({"text-align":"right",width:w+"px"});ba[L]=v;L++}});if(L>0){m=a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='"+(P*2-1)+"' class='DataTD'><input class='FormElement' id='id_g' type='text' name='id' value='"+m+"'/></td></tr>");m[0].rp=L+99;a(h).append(m)}return ba}function x(m,z){var h,P,O=0,V,R;if(R=a(z).jqGrid("getInd",
m,true)){a("td",R).each(function(L){h=z.p.colModel[L].name;P=z.p.colModel[L].editrules&&z.p.colModel[L].editrules.edithidden===true?false:z.p.colModel[L].hidden===true?true:false;if(h!=="cb"&&h!=="subgrid"&&h!=="rn"){V=h==z.p.ExpandColumn&&z.p.treeGrid===true?a(this).text():a(this).html();a.extend({},z.p.colModel[L].editoptions||{});h=a.jgrid.jqID("v_"+h);a("#"+h+" span","#"+s).html(V);P&&a("#"+h,"#"+s).parents("tr:first").hide();O++}});O>0&&a("#id_g","#"+s).val(m)}}function D(m,z){m===0?a("#pData",
"#"+s+"_2").addClass("ui-state-disabled"):a("#pData","#"+s+"_2").removeClass("ui-state-disabled");m==z?a("#nData","#"+s+"_2").addClass("ui-state-disabled"):a("#nData","#"+s+"_2").removeClass("ui-state-disabled")}function F(){var m=a(y).jqGrid("getDataIDs"),z=a("#id_g","#"+s).val();return[a.inArray(z,m),m]}var y=this;if(y.grid&&f){var o=y.p.id,k="ViewGrid_"+o,s="ViewTbl_"+o,N={themodal:"viewmod"+o,modalhead:"viewhd"+o,modalcontent:"viewcnt"+o,scrollelm:k},c=a.isFunction(b.beforeInitData)?b.beforeInitData:
false,t=true,p=1,j=0;if(a("#"+N.themodal).html()!==null){if(c){t=c(a("#"+k));if(typeof t=="undefined")t=true}if(t===false)return;a(".ui-jqdialog-title","#"+N.modalhead).html(b.caption);a("#FormError","#"+s).hide();x(f,y);a.isFunction(b.beforeShowForm)&&b.beforeShowForm(a("#"+k));a.jgrid.viewModal("#"+N.themodal,{gbox:"#gbox_"+o,jqm:b.jqModal,jqM:false,overlay:b.overlay,modal:b.modal});e()}else{var n=isNaN(b.dataheight)?b.dataheight:b.dataheight+"px";n=a("<form name='FormPost' id='"+k+"' class='FormGrid' style='width:100%;overflow:auto;position:relative;height:"+
n+";'></form>");var G=a("<table id='"+s+"' class='EditTable' cellspacing='1' cellpadding='2' border='0' style='table-layout:fixed'><tbody></tbody></table>");if(c){t=c(a("#"+k));if(typeof t=="undefined")t=true}if(t===false)return;a(y.p.colModel).each(function(){var m=this.formoptions;p=Math.max(p,m?m.colpos||0:0);j=Math.max(j,m?m.rowpos||0:0)});a(n).append(G);r(f,y,G,p);c=y.p.direction=="rtl"?true:false;t="<a href='javascript:void(0)' id='"+(c?"nData":"pData")+"' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>";
var J="<a href='javascript:void(0)' id='"+(c?"pData":"nData")+"' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>",u="<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>"+b.bClose+"</a>";if(j>0){var E=[];a.each(a(G)[0].rows,function(m,z){E[m]=z});E.sort(function(m,z){if(m.rp>z.rp)return 1;if(m.rp<z.rp)return-1;return 0});a.each(E,function(m,z){a("tbody",G).append(z)})}b.gbox="#gbox_"+o;var B=false;if(b.closeOnEscape===
true){b.closeOnEscape=false;B=true}n=a("<span></span>").append(n).append("<table border='0' class='EditTable' id='"+s+"_2'><tbody><tr id='Act_Buttons'><td class='navButton' width='"+b.labelswidth+"'>"+(c?J+t:t+J)+"</td><td class='EditButton'>"+u+"</td></tr></tbody></table>");a.jgrid.createModal(N,n,b,"#gview_"+y.p.id,a("#gview_"+y.p.id)[0]);if(c){a("#pData, #nData","#"+s+"_2").css("float","right");a(".EditButton","#"+s+"_2").css("text-align","left")}b.viewPagerButtons||a("#pData, #nData","#"+s+"_2").hide();
n=null;a("#"+N.themodal).keydown(function(m){if(m.which===27){B&&a.jgrid.hideModal(this,{gb:b.gbox,jqm:b.jqModal,onClose:b.onClose});return false}if(b.navkeys[0]===true){if(m.which===b.navkeys[1]){a("#pData","#"+s+"_2").trigger("click");return false}if(m.which===b.navkeys[2]){a("#nData","#"+s+"_2").trigger("click");return false}}});b.closeicon=a.extend([true,"left","ui-icon-close"],b.closeicon);if(b.closeicon[0]===true)a("#cData","#"+s+"_2").addClass(b.closeicon[1]=="right"?"fm-button-icon-right":
"fm-button-icon-left").append("<span class='ui-icon "+b.closeicon[2]+"'></span>");a.isFunction(b.beforeShowForm)&&b.beforeShowForm(a("#"+k));a.jgrid.viewModal("#"+N.themodal,{gbox:"#gbox_"+o,jqm:b.jqModal,modal:b.modal});a(".fm-button:not(.ui-state-disabled)","#"+s+"_2").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});e();a("#cData","#"+s+"_2").click(function(){a.jgrid.hideModal("#"+N.themodal,{gb:"#gbox_"+o,jqm:b.jqModal,onClose:b.onClose});
return false});a("#nData","#"+s+"_2").click(function(){a("#FormError","#"+s).hide();var m=F();m[0]=parseInt(m[0],10);if(m[0]!=-1&&m[1][m[0]+1]){if(a.isFunction(b.onclickPgButtons))b.onclickPgButtons("next",a("#"+k),m[1][m[0]]);x(m[1][m[0]+1],y);a(y).jqGrid("setSelection",m[1][m[0]+1]);a.isFunction(b.afterclickPgButtons)&&b.afterclickPgButtons("next",a("#"+k),m[1][m[0]+1]);D(m[0]+1,m[1].length-1)}e();return false});a("#pData","#"+s+"_2").click(function(){a("#FormError","#"+s).hide();var m=F();if(m[0]!=
-1&&m[1][m[0]-1]){if(a.isFunction(b.onclickPgButtons))b.onclickPgButtons("prev",a("#"+k),m[1][m[0]]);x(m[1][m[0]-1],y);a(y).jqGrid("setSelection",m[1][m[0]-1]);a.isFunction(b.afterclickPgButtons)&&b.afterclickPgButtons("prev",a("#"+k),m[1][m[0]-1]);D(m[0]-1,m[1].length-1)}e();return false})}n=F();D(n[0],n[1].length-1)}})},delGridRow:function(f,b){b=a.extend({top:0,left:0,width:240,height:"auto",dataheight:"auto",modal:false,overlay:30,drag:true,resize:true,url:"",mtype:"POST",reloadAfterSubmit:true,
beforeShowForm:null,beforeInitData:null,afterShowForm:null,beforeSubmit:null,onclickSubmit:null,afterSubmit:null,jqModal:true,closeOnEscape:false,delData:{},delicon:[],cancelicon:[],onClose:null,ajaxDelOptions:{},processing:false,serializeDelData:null,useDataProxy:false},a.jgrid.del,b||{});d[a(this)[0].p.id]=b;return this.each(function(){var e=this;if(e.grid)if(f){var r=a.isFunction(d[e.p.id].beforeShowForm),x=a.isFunction(d[e.p.id].afterShowForm),D=a.isFunction(d[e.p.id].beforeInitData)?d[e.p.id].beforeInitData:
false,F=e.p.id,y={},o=true,k="DelTbl_"+F,s,N,c,t,p={themodal:"delmod"+F,modalhead:"delhd"+F,modalcontent:"delcnt"+F,scrollelm:k};if(jQuery.isArray(f))f=f.join();if(a("#"+p.themodal).html()!==null){if(D){o=D(a("#"+k));if(typeof o=="undefined")o=true}if(o===false)return;a("#DelData>td","#"+k).text(f);a("#DelError","#"+k).hide();if(d[e.p.id].processing===true){d[e.p.id].processing=false;a("#dData","#"+k).removeClass("ui-state-active")}r&&d[e.p.id].beforeShowForm(a("#"+k));a.jgrid.viewModal("#"+p.themodal,
{gbox:"#gbox_"+F,jqm:d[e.p.id].jqModal,jqM:false,overlay:d[e.p.id].overlay,modal:d[e.p.id].modal})}else{var j=isNaN(d[e.p.id].dataheight)?d[e.p.id].dataheight:d[e.p.id].dataheight+"px";j="<div id='"+k+"' class='formdata' style='width:100%;overflow:auto;position:relative;height:"+j+";'>";j+="<table class='DelTable'><tbody>";j+="<tr id='DelError' style='display:none'><td class='ui-state-error'></td></tr>";j+="<tr id='DelData' style='display:none'><td >"+f+"</td></tr>";j+='<tr><td class="delmsg" style="white-space:pre;">'+
d[e.p.id].msg+"</td></tr><tr><td >&#160;</td></tr>";j+="</tbody></table></div>";j+="<table cellspacing='0' cellpadding='0' border='0' class='EditTable' id='"+k+"_2'><tbody><tr><td><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='DelButton EditButton'>"+("<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>"+b.bSubmit+"</a>")+"&#160;"+("<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>"+b.bCancel+
"</a>")+"</td></tr></tbody></table>";b.gbox="#gbox_"+F;a.jgrid.createModal(p,j,b,"#gview_"+e.p.id,a("#gview_"+e.p.id)[0]);if(D){o=D(a("#"+k));if(typeof o=="undefined")o=true}if(o===false)return;a(".fm-button","#"+k+"_2").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});b.delicon=a.extend([true,"left","ui-icon-scissors"],d[e.p.id].delicon);b.cancelicon=a.extend([true,"left","ui-icon-cancel"],d[e.p.id].cancelicon);if(b.delicon[0]===true)a("#dData",
"#"+k+"_2").addClass(b.delicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.delicon[2]+"'></span>");if(b.cancelicon[0]===true)a("#eData","#"+k+"_2").addClass(b.cancelicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.cancelicon[2]+"'></span>");a("#dData","#"+k+"_2").click(function(){var n=[true,""];y={};var G=a("#DelData>td","#"+k).text();if(a.isFunction(d[e.p.id].onclickSubmit))y=d[e.p.id].onclickSubmit(d[e.p.id],
G)||{};if(a.isFunction(d[e.p.id].beforeSubmit))n=d[e.p.id].beforeSubmit(G);if(n[0]&&!d[e.p.id].processing){d[e.p.id].processing=true;a(this).addClass("ui-state-active");c=e.p.prmNames;s=a.extend({},d[e.p.id].delData,y);t=c.oper;s[t]=c.deloper;N=c.id;G=G.split(",");for(var J in G)if(G.hasOwnProperty(J))G[J]=a.jgrid.stripPref(e.p.idPrefix,G[J]);s[N]=G.join();J=a.extend({url:d[e.p.id].url?d[e.p.id].url:a(e).jqGrid("getGridParam","editurl"),type:d[e.p.id].mtype,data:a.isFunction(d[e.p.id].serializeDelData)?
d[e.p.id].serializeDelData(s):s,complete:function(u,E){if(E!="success"){n[0]=false;n[1]=a.isFunction(d[e.p.id].errorTextFormat)?d[e.p.id].errorTextFormat(u):E+" Status: '"+u.statusText+"'. Error code: "+u.status}else if(a.isFunction(d[e.p.id].afterSubmit))n=d[e.p.id].afterSubmit(u,s);if(n[0]===false){a("#DelError>td","#"+k).html(n[1]);a("#DelError","#"+k).show()}else{if(d[e.p.id].reloadAfterSubmit&&e.p.datatype!="local")a(e).trigger("reloadGrid");else{var B=[];B=G.split(",");if(e.p.treeGrid===true)try{a(e).jqGrid("delTreeNode",
e.p.idPrefix+B[0])}catch(m){}else for(var z=0;z<B.length;z++)a(e).jqGrid("delRowData",e.p.idPrefix+B[z]);e.p.selrow=null;e.p.selarrrow=[]}a.isFunction(d[e.p.id].afterComplete)&&setTimeout(function(){d[e.p.id].afterComplete(u,G)},500)}d[e.p.id].processing=false;a("#dData","#"+k+"_2").removeClass("ui-state-active");n[0]&&a.jgrid.hideModal("#"+p.themodal,{gb:"#gbox_"+F,jqm:b.jqModal,onClose:d[e.p.id].onClose})}},a.jgrid.ajaxOptions,d[e.p.id].ajaxDelOptions);if(!J.url&&!d[e.p.id].useDataProxy)if(a.isFunction(e.p.dataProxy))d[e.p.id].useDataProxy=
true;else{n[0]=false;n[1]+=" "+a.jgrid.errors.nourl}if(n[0])if(d[e.p.id].useDataProxy){J=e.p.dataProxy.call(e,J,"del_"+e.p.id);if(typeof J=="undefined")J=[true,""];if(J[0]===false){n[0]=false;n[1]=J[1]||"Error deleting the selected row!"}else a.jgrid.hideModal("#"+p.themodal,{gb:"#gbox_"+F,jqm:b.jqModal,onClose:d[e.p.id].onClose})}else a.ajax(J)}if(n[0]===false){a("#DelError>td","#"+k).html(n[1]);a("#DelError","#"+k).show()}return false});a("#eData","#"+k+"_2").click(function(){a.jgrid.hideModal("#"+
p.themodal,{gb:"#gbox_"+F,jqm:d[e.p.id].jqModal,onClose:d[e.p.id].onClose});return false});r&&d[e.p.id].beforeShowForm(a("#"+k));a.jgrid.viewModal("#"+p.themodal,{gbox:"#gbox_"+F,jqm:d[e.p.id].jqModal,overlay:d[e.p.id].overlay,modal:d[e.p.id].modal})}x&&d[e.p.id].afterShowForm(a("#"+k));d[e.p.id].closeOnEscape===true&&setTimeout(function(){a(".ui-jqdialog-titlebar-close","#"+p.modalhead).focus()},0)}})},navGrid:function(f,b,e,r,x,D,F){b=a.extend({edit:true,editicon:"ui-icon-pencil",add:true,addicon:"ui-icon-plus",
del:true,delicon:"ui-icon-trash",search:true,searchicon:"ui-icon-search",refresh:true,refreshicon:"ui-icon-refresh",refreshstate:"firstpage",view:false,viewicon:"ui-icon-document",position:"left",closeOnEscape:true,beforeRefresh:null,afterRefresh:null,cloneToTop:false,alertwidth:200,alertheight:"auto",alerttop:null,alertleft:null,alertzIndex:null},a.jgrid.nav,b||{});return this.each(function(){if(!this.nav){var y={themodal:"alertmod",modalhead:"alerthd",modalcontent:"alertcnt"},o=this,k;if(!(!o.grid||
typeof f!="string")){if(a("#"+y.themodal).html()===null){if(!b.alerttop&&!b.alertleft){if(typeof window.innerWidth!="undefined"){b.alertleft=window.innerWidth;b.alerttop=window.innerHeight}else if(typeof document.documentElement!="undefined"&&typeof document.documentElement.clientWidth!="undefined"&&document.documentElement.clientWidth!==0){b.alertleft=document.documentElement.clientWidth;b.alerttop=document.documentElement.clientHeight}else{b.alertleft=1024;b.alerttop=768}b.alertleft=b.alertleft/
2-parseInt(b.alertwidth,10)/2;b.alerttop=b.alerttop/2-25}a.jgrid.createModal(y,"<div>"+b.alerttext+"</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",{gbox:"#gbox_"+o.p.id,jqModal:true,drag:true,resize:true,caption:b.alertcap,top:b.alerttop,left:b.alertleft,width:b.alertwidth,height:b.alertheight,closeOnEscape:b.closeOnEscape,zIndex:b.alertzIndex},"","",true)}var s=1;if(b.cloneToTop&&o.p.toppager)s=2;for(var N=0;N<s;N++){var c=a("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"),
t,p;if(N===0){t=f;p=o.p.id;if(t==o.p.toppager){p+="_top";s=1}}else{t=o.p.toppager;p=o.p.id+"_top"}o.p.direction=="rtl"&&a(c).attr("dir","rtl").css("float","right");if(b.add){r=r||{};k=a("<td class='ui-pg-button ui-corner-all'></td>");a(k).append("<div class='ui-pg-div'><span class='ui-icon "+b.addicon+"'></span>"+b.addtext+"</div>");a("tr",c).append(k);a(k,c).attr({title:b.addtitle||"",id:r.id||"add_"+p}).click(function(){a(this).hasClass("ui-state-disabled")||(a.isFunction(b.addfunc)?b.addfunc():
a(o).jqGrid("editGridRow","new",r));return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});k=null}if(b.edit){k=a("<td class='ui-pg-button ui-corner-all'></td>");e=e||{};a(k).append("<div class='ui-pg-div'><span class='ui-icon "+b.editicon+"'></span>"+b.edittext+"</div>");a("tr",c).append(k);a(k,c).attr({title:b.edittitle||"",id:e.id||"edit_"+p}).click(function(){if(!a(this).hasClass("ui-state-disabled")){var j=
o.p.selrow;if(j)a.isFunction(b.editfunc)?b.editfunc(j):a(o).jqGrid("editGridRow",j,e);else{a.jgrid.viewModal("#"+y.themodal,{gbox:"#gbox_"+o.p.id,jqm:true});a("#jqg_alrt").focus()}}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});k=null}if(b.view){k=a("<td class='ui-pg-button ui-corner-all'></td>");F=F||{};a(k).append("<div class='ui-pg-div'><span class='ui-icon "+b.viewicon+"'></span>"+b.viewtext+
"</div>");a("tr",c).append(k);a(k,c).attr({title:b.viewtitle||"",id:F.id||"view_"+p}).click(function(){if(!a(this).hasClass("ui-state-disabled")){var j=o.p.selrow;if(j)a.isFunction(b.viewfunc)?b.viewfunc(j):a(o).jqGrid("viewGridRow",j,F);else{a.jgrid.viewModal("#"+y.themodal,{gbox:"#gbox_"+o.p.id,jqm:true});a("#jqg_alrt").focus()}}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});k=null}if(b.del){k=
a("<td class='ui-pg-button ui-corner-all'></td>");x=x||{};a(k).append("<div class='ui-pg-div'><span class='ui-icon "+b.delicon+"'></span>"+b.deltext+"</div>");a("tr",c).append(k);a(k,c).attr({title:b.deltitle||"",id:x.id||"del_"+p}).click(function(){if(!a(this).hasClass("ui-state-disabled")){var j;if(o.p.multiselect){j=o.p.selarrrow;if(j.length===0)j=null}else j=o.p.selrow;if(j)"function"==typeof b.delfunc?b.delfunc(j):a(o).jqGrid("delGridRow",j,x);else{a.jgrid.viewModal("#"+y.themodal,{gbox:"#gbox_"+
o.p.id,jqm:true});a("#jqg_alrt").focus()}}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});k=null}if(b.add||b.edit||b.del||b.view)a("tr",c).append("<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>");if(b.search){k=a("<td class='ui-pg-button ui-corner-all'></td>");D=D||{};a(k).append("<div class='ui-pg-div'><span class='ui-icon "+b.searchicon+
"'></span>"+b.searchtext+"</div>");a("tr",c).append(k);a(k,c).attr({title:b.searchtitle||"",id:D.id||"search_"+p}).click(function(){a(this).hasClass("ui-state-disabled")||a(o).jqGrid("searchGrid",D);return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});D.showOnLoad&&D.showOnLoad===true&&a(k,c).click();k=null}if(b.refresh){k=a("<td class='ui-pg-button ui-corner-all'></td>");a(k).append("<div class='ui-pg-div'><span class='ui-icon "+
b.refreshicon+"'></span>"+b.refreshtext+"</div>");a("tr",c).append(k);a(k,c).attr({title:b.refreshtitle||"",id:"refresh_"+p}).click(function(){if(!a(this).hasClass("ui-state-disabled")){a.isFunction(b.beforeRefresh)&&b.beforeRefresh();o.p.search=false;try{var j=o.p.id;o.p.postData.filters="";a("#fbox_"+j).jqFilter("resetFilter");a.isFunction(o.clearToolbar)&&o.clearToolbar(false)}catch(n){}switch(b.refreshstate){case "firstpage":a(o).trigger("reloadGrid",[{page:1}]);break;case "current":a(o).trigger("reloadGrid",
[{current:true}])}a.isFunction(b.afterRefresh)&&b.afterRefresh()}return false}).hover(function(){a(this).hasClass("ui-state-disabled")||a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});k=null}k=a(".ui-jqgrid").css("font-size")||"11px";a("body").append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+k+";visibility:hidden;' ></div>");k=a(c).clone().appendTo("#testpg2").width();a("#testpg2").remove();a(t+"_"+b.position,t).append(c);
if(o.p._nvtd){if(k>o.p._nvtd[0]){a(t+"_"+b.position,t).width(k);o.p._nvtd[0]=k}o.p._nvtd[1]=k}c=k=k=null;this.nav=true}}}})},navButtonAdd:function(f,b){b=a.extend({caption:"newButton",title:"",buttonicon:"ui-icon-newwin",onClickButton:null,position:"last",cursor:"pointer"},b||{});return this.each(function(){if(this.grid){if(f.indexOf("#")!==0)f="#"+f;var e=a(".navtable",f)[0],r=this;if(e)if(!(b.id&&a("#"+b.id,e).html()!==null)){var x=a("<td></td>");b.buttonicon.toString().toUpperCase()=="NONE"?a(x).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'>"+
b.caption+"</div>"):a(x).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'><span class='ui-icon "+b.buttonicon+"'></span>"+b.caption+"</div>");b.id&&a(x).attr("id",b.id);if(b.position=="first")e.rows[0].cells.length===0?a("tr",e).append(x):a("tr td:eq(0)",e).before(x);else a("tr",e).append(x);a(x,e).attr("title",b.title||"").click(function(D){a(this).hasClass("ui-state-disabled")||a.isFunction(b.onClickButton)&&b.onClickButton.call(r,D);return false}).hover(function(){a(this).hasClass("ui-state-disabled")||
a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")})}}})},navSeparatorAdd:function(f,b){b=a.extend({sepclass:"ui-separator",sepcontent:""},b||{});return this.each(function(){if(this.grid){if(f.indexOf("#")!==0)f="#"+f;var e=a(".navtable",f)[0];if(e){var r="<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='"+b.sepclass+"'></span>"+b.sepcontent+"</td>";a("tr",e).append(r)}}})},GridToForm:function(f,b){return this.each(function(){var e=this;
if(e.grid){var r=a(e).jqGrid("getRowData",f);if(r)for(var x in r)a("[name="+a.jgrid.jqID(x)+"]",b).is("input:radio")||a("[name="+a.jgrid.jqID(x)+"]",b).is("input:checkbox")?a("[name="+a.jgrid.jqID(x)+"]",b).each(function(){if(a(this).val()==r[x])a(this)[e.p.useProp?"prop":"attr"]("checked",true);else a(this)[e.p.useProp?"prop":"attr"]("checked",false)}):a("[name="+a.jgrid.jqID(x)+"]",b).val(r[x])}})},FormToGrid:function(f,b,e,r){return this.each(function(){if(this.grid){e||(e="set");r||(r="first");
var x=a(b).serializeArray(),D={};a.each(x,function(F,y){D[y.name]=y.value});if(e=="add")a(this).jqGrid("addRowData",f,D,r);else e=="set"&&a(this).jqGrid("setRowData",f,D)}})}})})(jQuery);
(function(c){c.fn.jqFilter=function(k){if(typeof k==="string"){var w=c.fn.jqFilter[k];if(!w)throw"jqFilter - No such method: "+k;var B=c.makeArray(arguments).slice(1);return w.apply(this,B)}var o=c.extend(true,{filter:null,columns:[],onChange:null,afterRedraw:null,checkValues:null,error:false,errmsg:"",errorcheck:true,showQuery:true,sopt:null,ops:[{name:"eq",description:"equal",operator:"="},{name:"ne",description:"not equal",operator:"<>"},{name:"lt",description:"less",operator:"<"},{name:"le",description:"less or equal",
operator:"<="},{name:"gt",description:"greater",operator:">"},{name:"ge",description:"greater or equal",operator:">="},{name:"bw",description:"begins with",operator:"LIKE"},{name:"bn",description:"does not begin with",operator:"NOT LIKE"},{name:"in",description:"in",operator:"IN"},{name:"ni",description:"not in",operator:"NOT IN"},{name:"ew",description:"ends with",operator:"LIKE"},{name:"en",description:"does not end with",operator:"NOT LIKE"},{name:"cn",description:"contains",operator:"LIKE"},{name:"nc",
description:"does not contain",operator:"NOT LIKE"},{name:"nu",description:"is null",operator:"IS NULL"},{name:"nn",description:"is not null",operator:"IS NOT NULL"}],numopts:["eq","ne","lt","le","gt","ge","nu","nn","in","ni"],stropts:["eq","ne","bw","bn","ew","en","cn","nc","nu","nn","in","ni"],_gridsopt:[],groupOps:["AND","OR"],groupButton:true,ruleButtons:true,direction:"ltr"},k||{});return this.each(function(){if(!this.filter){this.p=o;if(this.p.filter===null||this.p.filter===undefined)this.p.filter=
{groupOp:this.p.groupOps[0],rules:[],groups:[]};var q,x=this.p.columns.length,j,y=/msie/i.test(navigator.userAgent)&&!window.opera;if(this.p._gridsopt.length)for(q=0;q<this.p._gridsopt.length;q++)this.p.ops[q].description=this.p._gridsopt[q];this.p.initFilter=c.extend(true,{},this.p.filter);if(x){for(q=0;q<x;q++){j=this.p.columns[q];if(j.stype)j.inputtype=j.stype;else if(!j.inputtype)j.inputtype="text";if(j.sorttype)j.searchtype=j.sorttype;else if(!j.searchtype)j.searchtype="string";if(j.hidden===
undefined)j.hidden=false;if(!j.label)j.label=j.name;if(j.index)j.name=j.index;if(!j.hasOwnProperty("searchoptions"))j.searchoptions={};if(!j.hasOwnProperty("searchrules"))j.searchrules={}}this.p.showQuery&&c(this).append("<table class='queryresult ui-widget ui-widget-content' style='display:block;max-width:440px;border:0px none;' dir='"+this.p.direction+"'><tbody><tr><td class='query'></td></tr></tbody></table>");var z=function(d,g){var a=[true,""];if(c.isFunction(g.searchrules))a=g.searchrules(d,
g);else if(c.jgrid&&c.jgrid.checkValues)try{a=c.jgrid.checkValues(d,-1,null,g.searchrules,g.label)}catch(b){}if(a&&a.length&&a[0]===false){o.error=!a[0];o.errmsg=a[1]}};this.onchange=function(){this.p.error=false;this.p.errmsg="";return c.isFunction(this.p.onChange)?this.p.onChange.call(this,this.p):false};this.reDraw=function(){c("table.group:first",this).remove();var d=this.createTableForGroup(o.filter,null);c(this).append(d);c.isFunction(this.p.afterRedraw)&&this.p.afterRedraw.call(this,this.p)};
this.createTableForGroup=function(d,g){var a=this,b,e=c("<table class='group ui-widget ui-widget-content' style='border:0px none;'><tbody></tbody></table>"),i="left";if(this.p.direction=="rtl"){i="right";e.attr("dir","rtl")}g===null&&e.append("<tr class='error' style='display:none;'><th colspan='5' class='ui-state-error' align='"+i+"'></th></tr>");var f=c("<tr></tr>");e.append(f);i=c("<th colspan='5' align='"+i+"'></th>");f.append(i);if(this.p.ruleButtons===true){var h=c("<select class='opsel'></select>");
i.append(h);f="";var l;for(b=0;b<o.groupOps.length;b++){l=d.groupOp===a.p.groupOps[b]?" selected='selected'":"";f+="<option value='"+a.p.groupOps[b]+"'"+l+">"+a.p.groupOps[b]+"</option>"}h.append(f).bind("change",function(){d.groupOp=c(h).val();a.onchange()})}f="<span></span>";if(this.p.groupButton){f=c("<input type='button' value='+ {}' title='Add subgroup' class='add-group'/>");f.bind("click",function(){if(d.groups===undefined)d.groups=[];d.groups.push({groupOp:o.groupOps[0],rules:[],groups:[]});
a.reDraw();a.onchange();return false})}i.append(f);if(this.p.ruleButtons===true){f=c("<input type='button' value='+' title='Add rule' class='add-rule ui-add'/>");var m;f.bind("click",function(){if(d.rules===undefined)d.rules=[];for(b=0;b<a.p.columns.length;b++){var n=typeof a.p.columns[b].search==="undefined"?true:a.p.columns[b].search,s=a.p.columns[b].hidden===true;if(a.p.columns[b].searchoptions.searchhidden===true&&n||n&&!s){m=a.p.columns[b];break}}d.rules.push({field:m.name,op:(m.searchoptions.sopt?
m.searchoptions.sopt:a.p.sopt?a.p.sopt:m.searchtype==="string"?a.p.stropts:a.p.numopts)[0],data:""});a.reDraw();return false});i.append(f)}if(g!==null){f=c("<input type='button' value='-' title='Delete group' class='delete-group'/>");i.append(f);f.bind("click",function(){for(b=0;b<g.groups.length;b++)if(g.groups[b]===d){g.groups.splice(b,1);break}a.reDraw();a.onchange();return false})}if(d.groups!==undefined)for(b=0;b<d.groups.length;b++){i=c("<tr></tr>");e.append(i);f=c("<td class='first'></td>");
i.append(f);f=c("<td colspan='4'></td>");f.append(this.createTableForGroup(d.groups[b],d));i.append(f)}if(d.groupOp===undefined)d.groupOp=a.p.groupOps[0];if(d.rules!==undefined)for(b=0;b<d.rules.length;b++)e.append(this.createTableRowForRule(d.rules[b],d));return e};this.createTableRowForRule=function(d,g){var a=this,b=c("<tr></tr>"),e,i,f,h,l="",m;b.append("<td class='first'></td>");var n=c("<td class='columns'></td>");b.append(n);var s=c("<select></select>"),p,t=[];n.append(s);s.bind("change",function(){d.field=
c(s).val();f=c(this).parents("tr:first");for(e=0;e<a.p.columns.length;e++)if(a.p.columns[e].name===d.field){h=a.p.columns[e];break}if(h){h.searchoptions.id=c.jgrid.randId();if(y&&h.inputtype==="text")if(!h.searchoptions.size)h.searchoptions.size=10;var r=c.jgrid.createEl(h.inputtype,h.searchoptions,"",true,a.p.ajaxSelectOptions,true);c(r).addClass("input-elm");i=h.searchoptions.sopt?h.searchoptions.sopt:a.p.sopt?a.p.sopt:h.searchtype==="string"?a.p.stropts:a.p.numopts;var u="",A=0;t=[];c.each(a.p.ops,
function(){t.push(this.name)});for(e=0;e<i.length;e++){p=c.inArray(i[e],t);if(p!==-1){if(A===0)d.op=a.p.ops[p].name;u+="<option value='"+a.p.ops[p].name+"'>"+a.p.ops[p].description+"</option>";A++}}c(".selectopts",f).empty().append(u);c(".selectopts",f)[0].selectedIndex=0;if(c.browser.msie&&c.browser.version<9){u=parseInt(c("select.selectopts",f)[0].offsetWidth)+1;c(".selectopts",f).width(u);c(".selectopts",f).css("width","auto")}c(".data",f).empty().append(r);c(".input-elm",f).bind("change",function(){d.data=
c(this).val();a.onchange()});setTimeout(function(){d.data=c(r).val();a.onchange()},0)}});for(e=n=0;e<a.p.columns.length;e++){m=typeof a.p.columns[e].search==="undefined"?true:a.p.columns[e].search;var C=a.p.columns[e].hidden===true;if(a.p.columns[e].searchoptions.searchhidden===true&&m||m&&!C){m="";if(d.field===a.p.columns[e].name){m=" selected='selected'";n=e}l+="<option value='"+a.p.columns[e].name+"'"+m+">"+a.p.columns[e].label+"</option>"}}s.append(l);l=c("<td class='operators'></td>");b.append(l);
h=o.columns[n];h.searchoptions.id=c.jgrid.randId();if(y&&h.inputtype==="text")if(!h.searchoptions.size)h.searchoptions.size=10;n=c.jgrid.createEl(h.inputtype,h.searchoptions,d.data,true,a.p.ajaxSelectOptions,true);var v=c("<select class='selectopts'></select>");l.append(v);v.bind("change",function(){d.op=c(v).val();f=c(this).parents("tr:first");var r=c(".input-elm",f)[0];if(d.op==="nu"||d.op==="nn"){d.data="";r.value="";r.setAttribute("readonly","true");r.setAttribute("disabled","true")}else{r.removeAttribute("readonly");
r.removeAttribute("disabled")}a.onchange()});i=h.searchoptions.sopt?h.searchoptions.sopt:a.p.sopt?a.p.sopt:h.searchtype==="string"?o.stropts:a.p.numopts;l="";c.each(a.p.ops,function(){t.push(this.name)});for(e=0;e<i.length;e++){p=c.inArray(i[e],t);if(p!==-1){m=d.op===a.p.ops[p].name?" selected='selected'":"";l+="<option value='"+a.p.ops[p].name+"'"+m+">"+a.p.ops[p].description+"</option>"}}v.append(l);l=c("<td class='data'></td>");b.append(l);l.append(n);c(n).addClass("input-elm").bind("change",function(){d.data=
c(this).val();a.onchange()});l=c("<td></td>");b.append(l);if(this.p.ruleButtons===true){n=c("<input type='button' value='-' title='Delete rule' class='delete-rule ui-del'/>");l.append(n);n.bind("click",function(){for(e=0;e<g.rules.length;e++)if(g.rules[e]===d){g.rules.splice(e,1);break}a.reDraw();a.onchange();return false})}return b};this.getStringForGroup=function(d){var g="(",a;if(d.groups!==undefined)for(a=0;a<d.groups.length;a++){if(g.length>1)g+=" "+d.groupOp+" ";try{g+=this.getStringForGroup(d.groups[a])}catch(b){alert(b)}}if(d.rules!==
undefined)try{for(a=0;a<d.rules.length;a++){if(g.length>1)g+=" "+d.groupOp+" ";g+=this.getStringForRule(d.rules[a])}}catch(e){alert(e)}g+=")";return g==="()"?"":g};this.getStringForRule=function(d){var g="",a="",b,e;for(b=0;b<this.p.ops.length;b++)if(this.p.ops[b].name===d.op){g=this.p.ops[b].operator;a=this.p.ops[b].name;break}for(b=0;b<this.p.columns.length;b++)if(this.p.columns[b].name===d.field){e=this.p.columns[b];break}b=d.data;if(a==="bw"||a==="bn")b+="%";if(a==="ew"||a==="en")b="%"+b;if(a===
"cn"||a==="nc")b="%"+b+"%";if(a==="in"||a==="ni")b=" ("+b+")";o.errorcheck&&z(d.data,e);return c.inArray(e.searchtype,["int","integer","float","number","currency"])!==-1||a==="nn"||a==="nu"?d.field+" "+g+" "+b:d.field+" "+g+' "'+b+'"'};this.resetFilter=function(){this.p.filter=c.extend(true,{},this.p.initFilter);this.reDraw();this.onchange()};this.hideError=function(){c("th.ui-state-error",this).html("");c("tr.error",this).hide()};this.showError=function(){c("th.ui-state-error",this).html(this.p.errmsg);
c("tr.error",this).show()};this.toUserFriendlyString=function(){return this.getStringForGroup(o.filter)};this.toString=function(){function d(a){var b="(",e;if(a.groups!==undefined)for(e=0;e<a.groups.length;e++){if(b.length>1)b+=a.groupOp==="OR"?" || ":" && ";b+=d(a.groups[e])}if(a.rules!==undefined)for(e=0;e<a.rules.length;e++){if(b.length>1)b+=a.groupOp==="OR"?" || ":" && ";var i=a.rules[e];if(g.p.errorcheck){var f=void 0,h=void 0;for(f=0;f<g.p.columns.length;f++)if(g.p.columns[f].name===i.field){h=
g.p.columns[f];break}h&&z(i.data,h)}b+=i.op+"(item."+i.field+",'"+i.data+"')"}b+=")";return b==="()"?"":b}var g=this;return d(this.p.filter)};this.reDraw();if(this.p.showQuery)this.onchange();this.filter=true}}})};c.extend(c.fn.jqFilter,{toSQLString:function(){var k="";this.each(function(){k=this.toUserFriendlyString()});return k},filterData:function(){var k;this.each(function(){k=this.p.filter});return k},getParameter:function(k){if(k!==undefined)if(this.p.hasOwnProperty(k))return this.p[k];return this.p},
resetFilter:function(){return this.each(function(){this.resetFilter()})},addFilter:function(k){if(typeof k==="string")k=jQuery.jgrid.parse(k);this.each(function(){this.p.filter=k;this.reDraw();this.onchange()})}})})(jQuery);
(function(a){a.jgrid.extend({editRow:function(g,x,m,s,u,y,v,l,t){var h={keys:x||false,oneditfunc:m||null,successfunc:s||null,url:u||null,extraparam:y||{},aftersavefunc:v||null,errorfunc:l||null,afterrestorefunc:t||null,restoreAfterError:true,mtype:"POST"},o=a.makeArray(arguments).slice(1),b;b=o[0]&&typeof o[0]=="object"&&!a.isFunction(o[0])?a.extend(h,o[0]):h;return this.each(function(){var d=this,c,j,q=0,r=null,p={},k,e;if(d.grid){k=a(d).jqGrid("getInd",g,true);if(k!==false)if((a(k).attr("editable")||
"0")=="0"&&!a(k).hasClass("not-editable-row")){e=d.p.colModel;a("td",k).each(function(f){c=e[f].name;var A=d.p.treeGrid===true&&c==d.p.ExpandColumn;if(A)j=a("span:first",this).html();else try{j=a.unformat(this,{rowId:g,colModel:e[f]},f)}catch(n){j=e[f].edittype&&e[f].edittype=="textarea"?a(this).text():a(this).html()}if(c!="cb"&&c!="subgrid"&&c!="rn"){if(d.p.autoencode)j=a.jgrid.htmlDecode(j);p[c]=j;if(e[f].editable===true){if(r===null)r=f;A?a("span:first",this).html(""):a(this).html("");var i=a.extend({},
e[f].editoptions||{},{id:g+"_"+c,name:c});if(!e[f].edittype)e[f].edittype="text";if(j=="&nbsp;"||j=="&#160;"||j.length==1&&j.charCodeAt(0)==160)j="";i=a.jgrid.createEl(e[f].edittype,i,j,true,a.extend({},a.jgrid.ajaxOptions,d.p.ajaxSelectOptions||{}));a(i).addClass("editable");A?a("span:first",this).append(i):a(this).append(i);e[f].edittype=="select"&&typeof e[f].editoptions!=="undefined"&&e[f].editoptions.multiple===true&&typeof e[f].editoptions.dataUrl==="undefined"&&a.browser.msie&&a(i).width(a(i).width());
q++}}});if(q>0){p.id=g;d.p.savedRow.push(p);a(k).attr("editable","1");a("td:eq("+r+") input",k).focus();b.keys===true&&a(k).bind("keydown",function(f){f.keyCode===27&&a(d).jqGrid("restoreRow",g,t);if(f.keyCode===13){if(f.target.tagName=="TEXTAREA")return true;a(d).jqGrid("saveRow",g,b);return false}f.stopPropagation()});a.isFunction(b.oneditfunc)&&b.oneditfunc.call(d,g)}}}})},saveRow:function(g,x,m,s,u,y,v){var l={successfunc:x||null,url:m||null,extraparam:s||{},aftersavefunc:u||null,errorfunc:y||
null,afterrestorefunc:v||null,restoreAfterError:true,mtype:"POST"},t=a.makeArray(arguments).slice(1),h;h=t[0]&&typeof t[0]=="object"&&!a.isFunction(t[0])?a.extend(l,t[0]):l;var o=false,b=this[0],d,c={},j={},q={},r,p,k;if(!b.grid)return o;k=a(b).jqGrid("getInd",g,true);if(k===false)return o;l=a(k).attr("editable");h.url=h.url?h.url:b.p.editurl;if(l==="1"){var e;a("td",k).each(function(n){e=b.p.colModel[n];d=e.name;if(d!="cb"&&d!="subgrid"&&e.editable===true&&d!="rn"&&!a(this).hasClass("not-editable-cell")){switch(e.edittype){case "checkbox":var i=
["Yes","No"];if(e.editoptions)i=e.editoptions.value.split(":");c[d]=a("input",this).is(":checked")?i[0]:i[1];break;case "text":case "password":case "textarea":case "button":c[d]=a("input, textarea",this).val();break;case "select":if(e.editoptions.multiple){i=a("select",this);var w=[];c[d]=a(i).val();c[d]=c[d]?c[d].join(","):"";a("select option:selected",this).each(function(B,C){w[B]=a(C).text()});j[d]=w.join(",")}else{c[d]=a("select option:selected",this).val();j[d]=a("select option:selected",this).text()}if(e.formatter&&
e.formatter=="select")j={};break;case "custom":try{if(e.editoptions&&a.isFunction(e.editoptions.custom_value)){c[d]=e.editoptions.custom_value.call(b,a(".customelement",this),"get");if(c[d]===undefined)throw"e2";}else throw"e1";}catch(z){z=="e1"&&a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);z=="e2"?a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+a.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):
a.jgrid.info_dialog(jQuery.jgrid.errors.errcap,z.message,jQuery.jgrid.edit.bClose)}}p=a.jgrid.checkValues(c[d],n,b);if(p[0]===false){p[1]=c[d]+" "+p[1];return false}if(b.p.autoencode)c[d]=a.jgrid.htmlEncode(c[d]);if(h.url!=="clientArray"&&e.editoptions&&e.editoptions.NullIfEmpty===true)if(c[d]=="")q[d]="null"}});if(p[0]===false){try{var f=a.jgrid.findPos(a("#"+a.jgrid.jqID(g),b.grid.bDiv)[0]);a.jgrid.info_dialog(a.jgrid.errors.errcap,p[1],a.jgrid.edit.bClose,{left:f[0],top:f[1]})}catch(A){alert(p[1])}return o}l=
b.p.prmNames;t=l.oper;f=l.id;if(c){c[t]=l.editoper;c[f]=g;if(typeof b.p.inlineData=="undefined")b.p.inlineData={};c=a.extend({},c,b.p.inlineData,h.extraparam)}if(h.url=="clientArray"){c=a.extend({},c,j);b.p.autoencode&&a.each(c,function(n,i){c[n]=a.jgrid.htmlDecode(i)});f=a(b).jqGrid("setRowData",g,c);a(k).attr("editable","0");for(l=0;l<b.p.savedRow.length;l++)if(b.p.savedRow[l].id==g){r=l;break}r>=0&&b.p.savedRow.splice(r,1);a.isFunction(h.aftersavefunc)&&h.aftersavefunc.call(b,g,f);o=true;a(k).unbind("keydown")}else{a("#lui_"+
b.p.id).show();q=a.extend({},c,q);q[f]=a.jgrid.stripPref(b.p.idPrefix,q[f]);a.ajax(a.extend({url:h.url,data:a.isFunction(b.p.serializeRowData)?b.p.serializeRowData.call(b,q):q,type:h.mtype,async:false,complete:function(n,i){a("#lui_"+b.p.id).hide();if(i==="success")if((a.isFunction(h.successfunc)?h.successfunc.call(b,n):true)===true){b.p.autoencode&&a.each(c,function(z,B){c[z]=a.jgrid.htmlDecode(B)});c=a.extend({},c,j);a(b).jqGrid("setRowData",g,c);a(k).attr("editable","0");for(var w=0;w<b.p.savedRow.length;w++)if(b.p.savedRow[w].id==
g){r=w;break}r>=0&&b.p.savedRow.splice(r,1);a.isFunction(h.aftersavefunc)&&h.aftersavefunc.call(b,g,n);o=true;a(k).unbind("keydown")}else{a.isFunction(h.errorfunc)&&h.errorfunc.call(b,g,n,i);h.restoreAfterError===true&&a(b).jqGrid("restoreRow",g,h.afterrestorefunc)}},error:function(n,i){a("#lui_"+b.p.id).hide();if(a.isFunction(h.errorfunc))h.errorfunc.call(b,g,n,i);else try{jQuery.jgrid.info_dialog(jQuery.jgrid.errors.errcap,'<div class="ui-state-error">'+n.responseText+"</div>",jQuery.jgrid.edit.bClose,
{buttonalign:"right"})}catch(w){alert(n.responseText)}h.restoreAfterError===true&&a(b).jqGrid("restoreRow",g,h.afterrestorefunc)}},a.jgrid.ajaxOptions,b.p.ajaxRowOptions||{}))}}return o},restoreRow:function(g,x){return this.each(function(){var m=this,s,u,y={};if(m.grid){u=a(m).jqGrid("getInd",g,true);if(u!==false){for(var v=0;v<m.p.savedRow.length;v++)if(m.p.savedRow[v].id==g){s=v;break}if(s>=0){if(a.isFunction(a.fn.datepicker))try{a("input.hasDatepicker","#"+a.jgrid.jqID(u.id)).datepicker("hide")}catch(l){}a.each(m.p.colModel,
function(){if(this.editable===true&&this.name in m.p.savedRow[s]&&!a(this).hasClass("not-editable-cell"))y[this.name]=m.p.savedRow[s][this.name]});a(m).jqGrid("setRowData",g,y);a(u).attr("editable","0").unbind("keydown");m.p.savedRow.splice(s,1)}a.isFunction(x)&&x.call(m,g)}}})}})})(jQuery);
(function(b){b.jgrid.extend({editCell:function(d,f,a){return this.each(function(){var c=this,h,e,g,i;if(!(!c.grid||c.p.cellEdit!==true)){f=parseInt(f,10);c.p.selrow=c.rows[d].id;c.p.knv||b(c).jqGrid("GridNav");if(c.p.savedRow.length>0){if(a===true)if(d==c.p.iRow&&f==c.p.iCol)return;b(c).jqGrid("saveCell",c.p.savedRow[0].id,c.p.savedRow[0].ic)}else window.setTimeout(function(){b("#"+c.p.knv).attr("tabindex","-1").focus()},0);i=c.p.colModel[f];h=i.name;if(!(h=="subgrid"||h=="cb"||h=="rn")){g=b("td:eq("+
f+")",c.rows[d]);if(i.editable===true&&a===true&&!g.hasClass("not-editable-cell")){if(parseInt(c.p.iCol,10)>=0&&parseInt(c.p.iRow,10)>=0){b("td:eq("+c.p.iCol+")",c.rows[c.p.iRow]).removeClass("edit-cell ui-state-highlight");b(c.rows[c.p.iRow]).removeClass("selected-row ui-state-hover")}b(g).addClass("edit-cell ui-state-highlight");b(c.rows[d]).addClass("selected-row ui-state-hover");try{e=b.unformat(g,{rowId:c.rows[d].id,colModel:i},f)}catch(k){e=i.edittype&&i.edittype=="textarea"?b(g).text():b(g).html()}if(c.p.autoencode)e=
b.jgrid.htmlDecode(e);if(!i.edittype)i.edittype="text";c.p.savedRow.push({id:d,ic:f,name:h,v:e});if(e=="&nbsp;"||e=="&#160;"||e.length==1&&e.charCodeAt(0)==160)e="";if(b.isFunction(c.p.formatCell)){var j=c.p.formatCell.call(c,c.rows[d].id,h,e,d,f);if(j!==undefined)e=j}j=b.extend({},i.editoptions||{},{id:d+"_"+h,name:h});var m=b.jgrid.createEl(i.edittype,j,e,true,b.extend({},b.jgrid.ajaxOptions,c.p.ajaxSelectOptions||{}));b.isFunction(c.p.beforeEditCell)&&c.p.beforeEditCell.call(c,c.rows[d].id,h,e,
d,f);b(g).html("").append(m).attr("tabindex","0");window.setTimeout(function(){b(m).focus()},0);b("input, select, textarea",g).bind("keydown",function(l){if(l.keyCode===27)if(b("input.hasDatepicker",g).length>0)b(".ui-datepicker").is(":hidden")?b(c).jqGrid("restoreCell",d,f):b("input.hasDatepicker",g).datepicker("hide");else b(c).jqGrid("restoreCell",d,f);l.keyCode===13&&b(c).jqGrid("saveCell",d,f);if(l.keyCode==9)if(c.grid.hDiv.loading)return false;else l.shiftKey?b(c).jqGrid("prevCell",d,f):b(c).jqGrid("nextCell",
d,f);l.stopPropagation()});b.isFunction(c.p.afterEditCell)&&c.p.afterEditCell.call(c,c.rows[d].id,h,e,d,f)}else{if(parseInt(c.p.iCol,10)>=0&&parseInt(c.p.iRow,10)>=0){b("td:eq("+c.p.iCol+")",c.rows[c.p.iRow]).removeClass("edit-cell ui-state-highlight");b(c.rows[c.p.iRow]).removeClass("selected-row ui-state-hover")}g.addClass("edit-cell ui-state-highlight");b(c.rows[d]).addClass("selected-row ui-state-hover");if(b.isFunction(c.p.onSelectCell)){e=g.html().replace(/\&#160\;/ig,"");c.p.onSelectCell.call(c,
c.rows[d].id,h,e,d,f)}}c.p.iCol=f;c.p.iRow=d}}})},saveCell:function(d,f){return this.each(function(){var a=this,c;if(!(!a.grid||a.p.cellEdit!==true)){c=a.p.savedRow.length>=1?0:null;if(c!==null){var h=b("td:eq("+f+")",a.rows[d]),e,g,i=a.p.colModel[f],k=i.name,j=b.jgrid.jqID(k);switch(i.edittype){case "select":if(i.editoptions.multiple){j=b("#"+d+"_"+j,a.rows[d]);var m=[];if(e=b(j).val())e.join(",");else e="";b("option:selected",j).each(function(o,p){m[o]=b(p).text()});g=m.join(",")}else{e=b("#"+d+
"_"+j+">option:selected",a.rows[d]).val();g=b("#"+d+"_"+j+">option:selected",a.rows[d]).text()}if(i.formatter)g=e;break;case "checkbox":var l=["Yes","No"];if(i.editoptions)l=i.editoptions.value.split(":");g=e=b("#"+d+"_"+j,a.rows[d]).is(":checked")?l[0]:l[1];break;case "password":case "text":case "textarea":case "button":g=e=b("#"+d+"_"+j,a.rows[d]).val();break;case "custom":try{if(i.editoptions&&b.isFunction(i.editoptions.custom_value)){e=i.editoptions.custom_value.call(a,b(".customelement",h),"get");
if(e===undefined)throw"e2";else g=e}else throw"e1";}catch(q){q=="e1"&&b.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose);q=="e2"?b.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+b.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose):b.jgrid.info_dialog(jQuery.jgrid.errors.errcap,q.message,jQuery.jgrid.edit.bClose)}}if(g!==a.p.savedRow[c].v){if(b.isFunction(a.p.beforeSaveCell))if(c=a.p.beforeSaveCell.call(a,
a.rows[d].id,k,e,d,f))g=e=c;var r=b.jgrid.checkValues(e,f,a);if(r[0]===true){c={};if(b.isFunction(a.p.beforeSubmitCell))(c=a.p.beforeSubmitCell.call(a,a.rows[d].id,k,e,d,f))||(c={});b("input.hasDatepicker",h).length>0&&b("input.hasDatepicker",h).datepicker("hide");if(a.p.cellsubmit=="remote")if(a.p.cellurl){var n={};if(a.p.autoencode)e=b.jgrid.htmlEncode(e);n[k]=e;l=a.p.prmNames;i=l.id;j=l.oper;n[i]=b.jgrid.stripPref(a.p.idPrefix,a.rows[d].id);n[j]=l.editoper;n=b.extend(c,n);b("#lui_"+a.p.id).show();
a.grid.hDiv.loading=true;b.ajax(b.extend({url:a.p.cellurl,data:b.isFunction(a.p.serializeCellData)?a.p.serializeCellData.call(a,n):n,type:"POST",complete:function(o,p){b("#lui_"+a.p.id).hide();a.grid.hDiv.loading=false;if(p=="success")if(b.isFunction(a.p.afterSubmitCell)){var s=a.p.afterSubmitCell.call(a,o,n.id,k,e,d,f);if(s[0]===true){b(h).empty();b(a).jqGrid("setCell",a.rows[d].id,f,g,false,false,true);b(h).addClass("dirty-cell");b(a.rows[d]).addClass("edited");b.isFunction(a.p.afterSaveCell)&&
a.p.afterSaveCell.call(a,a.rows[d].id,k,e,d,f);a.p.savedRow.splice(0,1)}else{b.jgrid.info_dialog(b.jgrid.errors.errcap,s[1],b.jgrid.edit.bClose);b(a).jqGrid("restoreCell",d,f)}}else{b(h).empty();b(a).jqGrid("setCell",a.rows[d].id,f,g,false,false,true);b(h).addClass("dirty-cell");b(a.rows[d]).addClass("edited");b.isFunction(a.p.afterSaveCell)&&a.p.afterSaveCell.call(a,a.rows[d].id,k,e,d,f);a.p.savedRow.splice(0,1)}},error:function(o,p){b("#lui_"+a.p.id).hide();a.grid.hDiv.loading=false;b.isFunction(a.p.errorCell)?
a.p.errorCell.call(a,o,p):b.jgrid.info_dialog(b.jgrid.errors.errcap,o.status+" : "+o.statusText+"<br/>"+p,b.jgrid.edit.bClose);b(a).jqGrid("restoreCell",d,f)}},b.jgrid.ajaxOptions,a.p.ajaxCellOptions||{}))}else try{b.jgrid.info_dialog(b.jgrid.errors.errcap,b.jgrid.errors.nourl,b.jgrid.edit.bClose);b(a).jqGrid("restoreCell",d,f)}catch(t){}if(a.p.cellsubmit=="clientArray"){b(h).empty();b(a).jqGrid("setCell",a.rows[d].id,f,g,false,false,true);b(h).addClass("dirty-cell");b(a.rows[d]).addClass("edited");
b.isFunction(a.p.afterSaveCell)&&a.p.afterSaveCell.call(a,a.rows[d].id,k,e,d,f);a.p.savedRow.splice(0,1)}}else try{window.setTimeout(function(){b.jgrid.info_dialog(b.jgrid.errors.errcap,e+" "+r[1],b.jgrid.edit.bClose)},100);b(a).jqGrid("restoreCell",d,f)}catch(u){}}else b(a).jqGrid("restoreCell",d,f)}b.browser.opera?b("#"+a.p.knv).attr("tabindex","-1").focus():window.setTimeout(function(){b("#"+a.p.knv).attr("tabindex","-1").focus()},0)}})},restoreCell:function(d,f){return this.each(function(){var a=
this,c;if(!(!a.grid||a.p.cellEdit!==true)){c=a.p.savedRow.length>=1?0:null;if(c!==null){var h=b("td:eq("+f+")",a.rows[d]);if(b.isFunction(b.fn.datepicker))try{b("input.hasDatepicker",h).datepicker("hide")}catch(e){}b(h).empty().attr("tabindex","-1");b(a).jqGrid("setCell",a.rows[d].id,f,a.p.savedRow[c].v,false,false,true);b.isFunction(a.p.afterRestoreCell)&&a.p.afterRestoreCell.call(a,a.rows[d].id,a.p.savedRow[c].v,d,f);a.p.savedRow.splice(0,1)}window.setTimeout(function(){b("#"+a.p.knv).attr("tabindex",
"-1").focus()},0)}})},nextCell:function(d,f){return this.each(function(){var a=false;if(!(!this.grid||this.p.cellEdit!==true)){for(var c=f+1;c<this.p.colModel.length;c++)if(this.p.colModel[c].editable===true){a=c;break}if(a!==false)b(this).jqGrid("editCell",d,a,true);else this.p.savedRow.length>0&&b(this).jqGrid("saveCell",d,f)}})},prevCell:function(d,f){return this.each(function(){var a=false;if(!(!this.grid||this.p.cellEdit!==true)){for(var c=f-1;c>=0;c--)if(this.p.colModel[c].editable===true){a=
c;break}if(a!==false)b(this).jqGrid("editCell",d,a,true);else this.p.savedRow.length>0&&b(this).jqGrid("saveCell",d,f)}})},GridNav:function(){return this.each(function(){function d(g,i,k){if(k.substr(0,1)=="v"){var j=b(a.grid.bDiv)[0].clientHeight,m=b(a.grid.bDiv)[0].scrollTop,l=a.rows[g].offsetTop+a.rows[g].clientHeight,q=a.rows[g].offsetTop;if(k=="vd")if(l>=j)b(a.grid.bDiv)[0].scrollTop=b(a.grid.bDiv)[0].scrollTop+a.rows[g].clientHeight;if(k=="vu")if(q<m)b(a.grid.bDiv)[0].scrollTop=b(a.grid.bDiv)[0].scrollTop-
a.rows[g].clientHeight}if(k=="h"){k=b(a.grid.bDiv)[0].clientWidth;j=b(a.grid.bDiv)[0].scrollLeft;m=a.rows[g].cells[i].offsetLeft;if(a.rows[g].cells[i].offsetLeft+a.rows[g].cells[i].clientWidth>=k+parseInt(j,10))b(a.grid.bDiv)[0].scrollLeft=b(a.grid.bDiv)[0].scrollLeft+a.rows[g].cells[i].clientWidth;else if(m<j)b(a.grid.bDiv)[0].scrollLeft=b(a.grid.bDiv)[0].scrollLeft-a.rows[g].cells[i].clientWidth}}function f(g,i){var k,j;if(i=="lft"){k=g+1;for(j=g;j>=0;j--)if(a.p.colModel[j].hidden!==true){k=j;break}}if(i==
"rgt"){k=g-1;for(j=g;j<a.p.colModel.length;j++)if(a.p.colModel[j].hidden!==true){k=j;break}}return k}var a=this;if(!(!a.grid||a.p.cellEdit!==true)){a.p.knv=a.p.id+"_kn";var c=b("<span style='width:0px;height:0px;background-color:black;' tabindex='0'><span tabindex='-1' style='width:0px;height:0px;background-color:grey' id='"+a.p.knv+"'></span></span>"),h,e;b(c).insertBefore(a.grid.cDiv);b("#"+a.p.knv).focus().keydown(function(g){e=g.keyCode;if(a.p.direction=="rtl")if(e==37)e=39;else if(e==39)e=37;
switch(e){case 38:if(a.p.iRow-1>0){d(a.p.iRow-1,a.p.iCol,"vu");b(a).jqGrid("editCell",a.p.iRow-1,a.p.iCol,false)}break;case 40:if(a.p.iRow+1<=a.rows.length-1){d(a.p.iRow+1,a.p.iCol,"vd");b(a).jqGrid("editCell",a.p.iRow+1,a.p.iCol,false)}break;case 37:if(a.p.iCol-1>=0){h=f(a.p.iCol-1,"lft");d(a.p.iRow,h,"h");b(a).jqGrid("editCell",a.p.iRow,h,false)}break;case 39:if(a.p.iCol+1<=a.p.colModel.length-1){h=f(a.p.iCol+1,"rgt");d(a.p.iRow,h,"h");b(a).jqGrid("editCell",a.p.iRow,h,false)}break;case 13:parseInt(a.p.iCol,
10)>=0&&parseInt(a.p.iRow,10)>=0&&b(a).jqGrid("editCell",a.p.iRow,a.p.iCol,true)}return false})}})},getChangedCells:function(d){var f=[];d||(d="all");this.each(function(){var a=this,c;!a.grid||a.p.cellEdit!==true||b(a.rows).each(function(h){var e={};if(b(this).hasClass("edited")){b("td",this).each(function(g){c=a.p.colModel[g].name;if(c!=="cb"&&c!=="subgrid")if(d=="dirty"){if(b(this).hasClass("dirty-cell"))try{e[c]=b.unformat(this,{rowId:a.rows[h].id,colModel:a.p.colModel[g]},g)}catch(i){e[c]=b.jgrid.htmlDecode(b(this).html())}}else try{e[c]=
b.unformat(this,{rowId:a.rows[h].id,colModel:a.p.colModel[g]},g)}catch(k){e[c]=b.jgrid.htmlDecode(b(this).html())}});e.id=this.id;f.push(e)}})});return f}})})(jQuery);
(function(b){b.fn.jqm=function(a){var f={overlay:50,closeoverlay:true,overlayClass:"jqmOverlay",closeClass:"jqmClose",trigger:".jqModal",ajax:e,ajaxText:"",target:e,modal:e,toTop:e,onShow:e,onHide:e,onLoad:e};return this.each(function(){if(this._jqm)return j[this._jqm].c=b.extend({},j[this._jqm].c,a);l++;this._jqm=l;j[l]={c:b.extend(f,b.jqm.params,a),a:e,w:b(this).addClass("jqmID"+l),s:l};f.trigger&&b(this).jqmAddTrigger(f.trigger)})};b.fn.jqmAddClose=function(a){return o(this,a,"jqmHide")};b.fn.jqmAddTrigger=
function(a){return o(this,a,"jqmShow")};b.fn.jqmShow=function(a){return this.each(function(){b.jqm.open(this._jqm,a)})};b.fn.jqmHide=function(a){return this.each(function(){b.jqm.close(this._jqm,a)})};b.jqm={hash:{},open:function(a,f){var c=j[a],d=c.c,i="."+d.closeClass,g=parseInt(c.w.css("z-index"));g=g>0?g:3E3;var h=b("<div></div>").css({height:"100%",width:"100%",position:"fixed",left:0,top:0,"z-index":g-1,opacity:d.overlay/100});if(c.a)return e;c.t=f;c.a=true;c.w.css("z-index",g);if(d.modal){k[0]||
setTimeout(function(){p("bind")},1);k.push(a)}else if(d.overlay>0)d.closeoverlay&&c.w.jqmAddClose(h);else h=e;c.o=h?h.addClass(d.overlayClass).prependTo("body"):e;if(q){b("html,body").css({height:"100%",width:"100%"});if(h){h=h.css({position:"absolute"})[0];for(var m in{Top:1,Left:1})h.style.setExpression(m.toLowerCase(),"(_=(document.documentElement.scroll"+m+" || document.body.scroll"+m+"))+'px'")}}if(d.ajax){g=d.target||c.w;h=d.ajax;g=typeof g=="string"?b(g,c.w):b(g);h=h.substr(0,1)=="@"?b(f).attr(h.substring(1)):
h;g.html(d.ajaxText).load(h,function(){d.onLoad&&d.onLoad.call(this,c);i&&c.w.jqmAddClose(b(i,c.w));r(c)})}else i&&c.w.jqmAddClose(b(i,c.w));d.toTop&&c.o&&c.w.before('<span id="jqmP'+c.w[0]._jqm+'"></span>').insertAfter(c.o);d.onShow?d.onShow(c):c.w.show();r(c);return e},close:function(a){a=j[a];if(!a.a)return e;a.a=e;if(k[0]){k.pop();k[0]||p("unbind")}a.c.toTop&&a.o&&b("#jqmP"+a.w[0]._jqm).after(a.w).remove();if(a.c.onHide)a.c.onHide(a);else{a.w.hide();a.o&&a.o.remove()}return e},params:{}};var l=
0,j=b.jqm.hash,k=[],q=b.browser.msie&&b.browser.version=="6.0",e=false,r=function(a){var f=b('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0});if(q)if(a.o)a.o.html('<p style="width:100%;height:100%"/>').prepend(f);else b("iframe.jqm",a.w)[0]||a.w.prepend(f);s(a)},s=function(a){try{b(":input:visible",a.w)[0].focus()}catch(f){}},p=function(a){b(document)[a]("keypress",n)[a]("keydown",n)[a]("mousedown",n)},n=function(a){var f=j[k[k.length-1]];(a=!b(a.target).parents(".jqmID"+
f.s)[0])&&s(f);return!a},o=function(a,f,c){return a.each(function(){var d=this._jqm;b(f).each(function(){if(!this[c]){this[c]=[];b(this).click(function(){for(var i in{jqmShow:1,jqmHide:1})for(var g in this[i])if(j[this[i][g]])j[this[i][g]].w[i](this);return e})}this[c].push(d)})})}})(jQuery);
(function(b){b.fn.jqDrag=function(a){return l(this,a,"d")};b.fn.jqResize=function(a,e){return l(this,a,"r",e)};b.jqDnR={dnr:{},e:0,drag:function(a){if(c.k=="d")d.css({left:c.X+a.pageX-c.pX,top:c.Y+a.pageY-c.pY});else{d.css({width:Math.max(a.pageX-c.pX+c.W,0),height:Math.max(a.pageY-c.pY+c.H,0)});M1&&f.css({width:Math.max(a.pageX-M1.pX+M1.W,0),height:Math.max(a.pageY-M1.pY+M1.H,0)})}return false},stop:function(){b(document).unbind("mousemove",i.drag).unbind("mouseup",i.stop)}};var i=b.jqDnR,c=i.dnr,
d=i.e,f,l=function(a,e,n,m){return a.each(function(){e=e?b(e,a):a;e.bind("mousedown",{e:a,k:n},function(g){var j=g.data,h={};d=j.e;f=m?b(m):false;if(d.css("position")!="relative")try{d.position(h)}catch(o){}c={X:h.left||k("left")||0,Y:h.top||k("top")||0,W:k("width")||d[0].scrollWidth||0,H:k("height")||d[0].scrollHeight||0,pX:g.pageX,pY:g.pageY,k:j.k};M1=f&&j.k!="d"?{X:h.left||f1("left")||0,Y:h.top||f1("top")||0,W:f[0].offsetWidth||f1("width")||0,H:f[0].offsetHeight||f1("height")||0,pX:g.pageX,pY:g.pageY,
k:j.k}:false;if(b("input.hasDatepicker",d[0])[0])try{b("input.hasDatepicker",d[0]).datepicker("hide")}catch(p){}b(document).mousemove(b.jqDnR.drag).mouseup(b.jqDnR.stop);return false})})},k=function(a){return parseInt(d.css(a))||false};f1=function(a){return parseInt(f.css(a))||false}})(jQuery);
(function(b){b.jgrid.extend({setSubGrid:function(){return this.each(function(){var f;this.p.subGridOptions=b.extend({plusicon:"ui-icon-plus",minusicon:"ui-icon-minus",openicon:"ui-icon-carat-1-sw",expandOnLoad:false,delayOnLoad:50,selectOnExpand:false,reloadOnExpand:true},this.p.subGridOptions||{});this.p.colNames.unshift("");this.p.colModel.unshift({name:"subgrid",width:b.browser.safari?this.p.subGridWidth+this.p.cellLayout:this.p.subGridWidth,sortable:false,resizable:false,hidedlg:true,search:false,
fixed:true});f=this.p.subGridModel;if(f[0]){f[0].align=b.extend([],f[0].align||[]);for(var d=0;d<f[0].name.length;d++)f[0].align[d]=f[0].align[d]||"left"}})},addSubGridCell:function(f,d){var a="",n,s;this.each(function(){a=this.formatCol(f,d);s=this.p.id;n=this.p.subGridOptions.plusicon});return'<td role="grid" aria-describedby="'+s+'_subgrid" class="ui-sgcollapsed sgcollapsed" '+a+"><a href='javascript:void(0);'><span class='ui-icon "+n+"'></span></a></td>"},addSubGrid:function(f,d){return this.each(function(){var a=
this;if(a.grid){var n=function(g,j,e){j=b("<td align='"+a.p.subGridModel[0].align[e]+"'></td>").html(j);b(g).append(j)},s=function(g,j){var e,c,h,k=b("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),i=b("<tr></tr>");for(c=0;c<a.p.subGridModel[0].name.length;c++){e=b("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-"+a.p.direction+"'></th>");b(e).html(a.p.subGridModel[0].name[c]);b(e).width(a.p.subGridModel[0].width[c]);b(i).append(e)}b(k).append(i);if(g){h=
a.p.xmlReader.subgrid;b(h.root+" "+h.row,g).each(function(){i=b("<tr class='ui-widget-content ui-subtblcell'></tr>");if(h.repeatitems===true)b(h.cell,this).each(function(m){n(i,b(this).text()||"&#160;",m)});else{var o=a.p.subGridModel[0].mapping||a.p.subGridModel[0].name;if(o)for(c=0;c<o.length;c++)n(i,b(o[c],this).text()||"&#160;",c)}b(k).append(i)})}e=b("table:first",a.grid.bDiv).attr("id")+"_";b("#"+e+j).append(k);a.grid.hDiv.loading=false;b("#load_"+a.p.id).hide();return false},v=function(g,j){var e,
c,h,k,i,o=b("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),m=b("<tr></tr>");for(c=0;c<a.p.subGridModel[0].name.length;c++){e=b("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-"+a.p.direction+"'></th>");b(e).html(a.p.subGridModel[0].name[c]);b(e).width(a.p.subGridModel[0].width[c]);b(m).append(e)}b(o).append(m);if(g){k=a.p.jsonReader.subgrid;e=g[k.root];if(typeof e!=="undefined")for(c=0;c<e.length;c++){h=e[c];m=b("<tr class='ui-widget-content ui-subtblcell'></tr>");
if(k.repeatitems===true){if(k.cell)h=h[k.cell];for(i=0;i<h.length;i++)n(m,h[i]||"&#160;",i)}else{var u=a.p.subGridModel[0].mapping||a.p.subGridModel[0].name;if(u.length)for(i=0;i<u.length;i++)n(m,h[u[i]]||"&#160;",i)}b(o).append(m)}}c=b("table:first",a.grid.bDiv).attr("id")+"_";b("#"+c+j).append(o);a.grid.hDiv.loading=false;b("#load_"+a.p.id).hide();return false},z=function(g){var j,e,c,h;j=b(g).attr("id");e={nd_:(new Date).getTime()};e[a.p.prmNames.subgridid]=j;if(!a.p.subGridModel[0])return false;
if(a.p.subGridModel[0].params)for(h=0;h<a.p.subGridModel[0].params.length;h++)for(c=0;c<a.p.colModel.length;c++)if(a.p.colModel[c].name==a.p.subGridModel[0].params[h])e[a.p.colModel[c].name]=b("td:eq("+c+")",g).text().replace(/\&#160\;/ig,"");if(!a.grid.hDiv.loading){a.grid.hDiv.loading=true;b("#load_"+a.p.id).show();if(!a.p.subgridtype)a.p.subgridtype=a.p.datatype;if(b.isFunction(a.p.subgridtype))a.p.subgridtype.call(a,e);else a.p.subgridtype=a.p.subgridtype.toLowerCase();switch(a.p.subgridtype){case "xml":case "json":b.ajax(b.extend({type:a.p.mtype,
url:a.p.subGridUrl,dataType:a.p.subgridtype,data:b.isFunction(a.p.serializeSubGridData)?a.p.serializeSubGridData.call(a,e):e,complete:function(k){a.p.subgridtype=="xml"?s(k.responseXML,j):v(b.jgrid.parse(k.responseText),j)}},b.jgrid.ajaxOptions,a.p.ajaxSubgridOptions||{}))}}return false},l,t,w,x=0,p,q;b.each(a.p.colModel,function(){if(this.hidden===true||this.name=="rn"||this.name=="cb")x++});var y=a.rows.length,r=1;if(d!==undefined&&d>0){r=d;y=d+1}for(;r<y;){b(a.rows[r]).hasClass("jqgrow")&&b(a.rows[r].cells[f]).bind("click",
function(){var g=b(this).parent("tr")[0];q=g.nextSibling;if(b(this).hasClass("sgcollapsed")){t=a.p.id;l=g.id;if(a.p.subGridOptions.reloadOnExpand===true||a.p.subGridOptions.reloadOnExpand===false&&!b(q).hasClass("ui-subgrid")){w=f>=1?"<td colspan='"+f+"'>&#160;</td>":"";p=true;if(b.isFunction(a.p.subGridBeforeExpand))p=a.p.subGridBeforeExpand.call(a,t+"_"+l,l);if(p===false)return false;b(g).after("<tr role='row' class='ui-subgrid'>"+w+"<td class='ui-widget-content subgrid-cell'><span class='ui-icon "+
a.p.subGridOptions.openicon+"'></span></td><td colspan='"+parseInt(a.p.colNames.length-1-x,10)+"' class='ui-widget-content subgrid-data'><div id="+t+"_"+l+" class='tablediv'></div></td></tr>");b.isFunction(a.p.subGridRowExpanded)?a.p.subGridRowExpanded.call(a,t+"_"+l,l):z(g)}else b(q).show();b(this).html("<a href='javascript:void(0);'><span class='ui-icon "+a.p.subGridOptions.minusicon+"'></span></a>").removeClass("sgcollapsed").addClass("sgexpanded");a.p.subGridOptions.selectOnExpand&&b(a).jqGrid("setSelection",
l)}else if(b(this).hasClass("sgexpanded")){p=true;if(b.isFunction(a.p.subGridRowColapsed)){l=g.id;p=a.p.subGridRowColapsed.call(a,t+"_"+l,l)}if(p===false)return false;if(a.p.subGridOptions.reloadOnExpand===true)b(q).remove(".ui-subgrid");else b(q).hasClass("ui-subgrid")&&b(q).hide();b(this).html("<a href='javascript:void(0);'><span class='ui-icon "+a.p.subGridOptions.plusicon+"'></span></a>").removeClass("sgexpanded").addClass("sgcollapsed")}return false});a.p.subGridOptions.expandOnLoad===true&&
b(a.rows[r].cells[f]).trigger("click");r++}a.subGridXml=function(g,j){s(g,j)};a.subGridJson=function(g,j){v(g,j)}}})},expandSubGridRow:function(f){return this.each(function(){if(this.grid||f)if(this.p.subGrid===true){var d=b(this).jqGrid("getInd",f,true);if(d)(d=b("td.sgcollapsed",d)[0])&&b(d).trigger("click")}})},collapseSubGridRow:function(f){return this.each(function(){if(this.grid||f)if(this.p.subGrid===true){var d=b(this).jqGrid("getInd",f,true);if(d)(d=b("td.sgexpanded",d)[0])&&b(d).trigger("click")}})},
toggleSubGridRow:function(f){return this.each(function(){if(this.grid||f)if(this.p.subGrid===true){var d=b(this).jqGrid("getInd",f,true);if(d){var a=b("td.sgcollapsed",d)[0];if(a)b(a).trigger("click");else(a=b("td.sgexpanded",d)[0])&&b(a).trigger("click")}}})}})})(jQuery);
(function(e){e.jgrid.extend({groupingSetup:function(){return this.each(function(){var a=this.p.groupingView;if(a!==null&&(typeof a==="object"||e.isFunction(a)))if(a.groupField.length){if(typeof a.visibiltyOnNextGrouping=="undefined")a.visibiltyOnNextGrouping=[];for(var c=0;c<a.groupField.length;c++){a.groupOrder[c]||(a.groupOrder[c]="asc");a.groupText[c]||(a.groupText[c]="{0}");if(typeof a.groupColumnShow[c]!="boolean")a.groupColumnShow[c]=true;if(typeof a.groupSummary[c]!="boolean")a.groupSummary[c]=
false;if(a.groupColumnShow[c]===true){a.visibiltyOnNextGrouping[c]=true;e(this).jqGrid("showCol",a.groupField[c])}else{a.visibiltyOnNextGrouping[c]=e("#"+this.p.id+"_"+a.groupField[c]).is(":visible");e(this).jqGrid("hideCol",a.groupField[c])}a.sortitems[c]=[];a.sortnames[c]=[];a.summaryval[c]=[];if(a.groupSummary[c]){a.summary[c]=[];for(var b=this.p.colModel,d=0,g=b.length;d<g;d++)b[d].summaryType&&a.summary[c].push({nm:b[d].name,st:b[d].summaryType,v:""})}}this.p.scroll=false;this.p.rownumbers=false;
this.p.subGrid=false;this.p.treeGrid=false;this.p.gridview=true}else this.p.grouping=false;else this.p.grouping=false})},groupingPrepare:function(a,c,b,d){this.each(function(){c[0]+="";var g=c[0].toString().split(" ").join(""),h=this.p.groupingView,f=this;if(b.hasOwnProperty(g))b[g].push(a);else{b[g]=[];b[g].push(a);h.sortitems[0].push(g);h.sortnames[0].push(e.trim(c[0].toString()));h.summaryval[0][g]=e.extend(true,[],h.summary[0])}h.groupSummary[0]&&e.each(h.summaryval[0][g],function(){this.v=e.isFunction(this.st)?
this.st.call(f,this.v,this.nm,d):e(f).jqGrid("groupingCalculations."+this.st,this.v,this.nm,d)})});return b},groupingToggle:function(a){this.each(function(){var c=this.p.groupingView,b=a.lastIndexOf("_"),d=a.substring(0,b+1);b=parseInt(a.substring(b+1),10)+1;var g=c.minusicon,h=c.plusicon,f=e("#"+a);f=f.length?f[0].nextSibling:null;var k=e("#"+a+" span.tree-wrap-"+this.p.direction),l=false;if(k.hasClass(g)){if(c.showSummaryOnHide&&c.groupSummary[0]){if(f)for(;f;){if(e(f).hasClass("jqfoot"))break;
e(f).hide();f=f.nextSibling}}else if(f)for(;f;){if(e(f).attr("id")==d+String(b))break;e(f).hide();f=f.nextSibling}k.removeClass(g).addClass(h);l=true}else{if(f)for(;f;){if(e(f).attr("id")==d+String(b))break;e(f).show();f=f.nextSibling}k.removeClass(h).addClass(g)}e.isFunction(this.p.onClickGroup)&&this.p.onClickGroup.call(this,a,l)});return false},groupingRender:function(a,c){return this.each(function(){var b=this,d=b.p.groupingView,g="",h="",f,k=d.groupCollapse?d.plusicon:d.minusicon,l,r,m;if(!d.groupDataSorted){d.sortitems[0].sort();
d.sortnames[0].sort();if(d.groupOrder[0].toLowerCase()=="desc"){d.sortitems[0].reverse();d.sortnames[0].reverse()}}k+=" tree-wrap-"+b.p.direction;for(m=0;m<c;){if(b.p.colModel[m].name==d.groupField[0]){r=m;break}m++}e.each(d.sortitems[0],function(o,n){f=b.p.id+"ghead_"+o;h="<span style='cursor:pointer;' class='ui-icon "+k+"' onclick=\"jQuery('#"+b.p.id+"').jqGrid('groupingToggle','"+f+"');return false;\"></span>";try{l=b.formatter(f,d.sortnames[0][o],r,d.sortitems[0])}catch(v){l=d.sortnames[0][o]}g+=
'<tr id="'+f+'" role="row" class= "ui-widget-content jqgroup ui-row-'+b.p.direction+'"><td colspan="'+c+'">'+h+e.jgrid.format(d.groupText[0],l,a[n].length)+"</td></tr>";for(var i=0;i<a[n].length;i++)g+=a[n][i].join("");if(d.groupSummary[0]){i="";if(d.groupCollapse&&!d.showSummaryOnHide)i=' style="display:none;"';g+="<tr"+i+' role="row" class="ui-widget-content jqfoot ui-row-'+b.p.direction+'">';i=d.summaryval[0][n];for(var p=b.p.colModel,q,s=a[n].length,j=0;j<c;j++){var t="<td "+b.formatCol(j,1,"")+
">&#160;</td>",u="{0}";e.each(i,function(){if(this.nm==p[j].name){if(p[j].summaryTpl)u=p[j].summaryTpl;if(this.st=="avg")if(this.v&&s>0)this.v/=s;try{q=b.formatter("",this.v,j,this)}catch(w){q=this.v}t="<td "+b.formatCol(j,1,"")+">"+e.jgrid.format(u,q)+"</td>";return false}});g+=t}g+="</tr>"}});e("#"+b.p.id+" tbody:first").append(g);g=null})},groupingGroupBy:function(a,c){return this.each(function(){if(typeof a=="string")a=[a];var b=this.p.groupingView;this.p.grouping=true;if(typeof b.visibiltyOnNextGrouping==
"undefined")b.visibiltyOnNextGrouping=[];var d;for(d=0;d<b.groupField.length;d++)!b.groupColumnShow[d]&&b.visibiltyOnNextGrouping[d]&&e(this).jqGrid("showCol",b.groupField[d]);for(d=0;d<a.length;d++)b.visibiltyOnNextGrouping[d]=e("#"+this.p.id+"_"+a[d]).is(":visible");this.p.groupingView=e.extend(this.p.groupingView,c||{});b.groupField=a;e(this).trigger("reloadGrid")})},groupingRemove:function(a){return this.each(function(){if(typeof a=="undefined")a=true;this.p.grouping=false;if(a===true){for(var c=
this.p.groupingView,b=0;b<c.groupField.length;b++)!c.groupColumnShow[b]&&c.visibiltyOnNextGrouping[b]&&e(this).jqGrid("showCol",c.groupField);e("tr.jqgroup, tr.jqfoot","#"+this.p.id+" tbody:first").remove();e("tr.jqgrow:hidden","#"+this.p.id+" tbody:first").show()}else e(this).trigger("reloadGrid")})},groupingCalculations:{sum:function(a,c,b){return parseFloat(a||0)+parseFloat(b[c]||0)},min:function(a,c,b){if(a==="")return parseFloat(b[c]||0);return Math.min(parseFloat(a),parseFloat(b[c]||0))},max:function(a,
c,b){if(a==="")return parseFloat(b[c]||0);return Math.max(parseFloat(a),parseFloat(b[c]||0))},count:function(a,c,b){if(a==="")a=0;return b.hasOwnProperty(c)?a+1:0},avg:function(a,c,b){return parseFloat(a||0)+parseFloat(b[c]||0)}}})})(jQuery);
(function(d){d.jgrid.extend({setTreeNode:function(a,c){return this.each(function(){var b=this;if(b.grid&&b.p.treeGrid)for(var e=b.p.expColInd,g=b.p.treeReader.expanded_field,h=b.p.treeReader.leaf_field,f=b.p.treeReader.level_field,l=b.p.treeReader.icon_field,i=b.p.treeReader.loaded,j,o,n,k;a<c;){k=b.p.data[b.p._index[b.rows[a].id]];if(b.p.treeGridModel=="nested")if(!k[h]){j=parseInt(k[b.p.treeReader.left_field],10);o=parseInt(k[b.p.treeReader.right_field],10);k[h]=o===j+1?"true":"false";b.rows[a].cells[b.p._treeleafpos].innerHTML=
k[h]}j=parseInt(k[f],10);if(b.p.tree_root_level===0){n=j+1;o=j}else{n=j;o=j-1}n="<div class='tree-wrap tree-wrap-"+b.p.direction+"' style='width:"+n*18+"px;'>";n+="<div style='"+(b.p.direction=="rtl"?"right:":"left:")+o*18+"px;' class='ui-icon ";if(k[i]!==undefined)k[i]=k[i]=="true"||k[i]===true?true:false;if(k[h]=="true"||k[h]===true){n+=(k[l]!==undefined&&k[l]!==""?k[l]:b.p.treeIcons.leaf)+" tree-leaf treeclick";k[h]=true;o="leaf"}else{k[h]=false;o=""}k[g]=(k[g]=="true"||k[g]===true?true:false)&&
k[i];n+=k[g]===false?k[h]===true?"'":b.p.treeIcons.plus+" tree-plus treeclick'":k[h]===true?"'":b.p.treeIcons.minus+" tree-minus treeclick'";n+="></div></div>";d(b.rows[a].cells[e]).wrapInner("<span class='cell-wrapper"+o+"'></span>").prepend(n);if(j!==parseInt(b.p.tree_root_level,10))(k=(k=d(b).jqGrid("getNodeParent",k))&&k.hasOwnProperty(g)?k[g]:true)||d(b.rows[a]).css("display","none");d(b.rows[a].cells[e]).find("div.treeclick").bind("click",function(m){m=d(m.target||m.srcElement,b.rows).closest("tr.jqgrow")[0].id;
m=b.p._index[m];if(!b.p.data[m][h])if(b.p.data[m][g]){d(b).jqGrid("collapseRow",b.p.data[m]);d(b).jqGrid("collapseNode",b.p.data[m])}else{d(b).jqGrid("expandRow",b.p.data[m]);d(b).jqGrid("expandNode",b.p.data[m])}return false});b.p.ExpandColClick===true&&d(b.rows[a].cells[e]).find("span.cell-wrapper").css("cursor","pointer").bind("click",function(m){m=d(m.target||m.srcElement,b.rows).closest("tr.jqgrow")[0].id;var r=b.p._index[m];if(!b.p.data[r][h])if(b.p.data[r][g]){d(b).jqGrid("collapseRow",b.p.data[r]);
d(b).jqGrid("collapseNode",b.p.data[r])}else{d(b).jqGrid("expandRow",b.p.data[r]);d(b).jqGrid("expandNode",b.p.data[r])}d(b).jqGrid("setSelection",m);return false});a++}})},setTreeGrid:function(){return this.each(function(){var a=this,c=0,b=false,e,g,h=[];if(a.p.treeGrid){a.p.treedatatype||d.extend(a.p,{treedatatype:a.p.datatype});a.p.subGrid=false;a.p.altRows=false;a.p.pgbuttons=false;a.p.pginput=false;a.p.gridview=true;if(a.p.rowTotal===null)a.p.rowNum=1E4;a.p.multiselect=false;a.p.rowList=[];a.p.expColInd=
0;a.p.treeIcons=d.extend({plus:"ui-icon-triangle-1-"+(a.p.direction=="rtl"?"w":"e"),minus:"ui-icon-triangle-1-s",leaf:"ui-icon-radio-off"},a.p.treeIcons||{});if(a.p.treeGridModel=="nested")a.p.treeReader=d.extend({level_field:"level",left_field:"lft",right_field:"rgt",leaf_field:"isLeaf",expanded_field:"expanded",loaded:"loaded",icon_field:"icon"},a.p.treeReader);else if(a.p.treeGridModel=="adjacency")a.p.treeReader=d.extend({level_field:"level",parent_id_field:"parent",leaf_field:"isLeaf",expanded_field:"expanded",
loaded:"loaded",icon_field:"icon"},a.p.treeReader);for(g in a.p.colModel)if(a.p.colModel.hasOwnProperty(g)){e=a.p.colModel[g].name;if(e==a.p.ExpandColumn&&!b){b=true;a.p.expColInd=c}c++;for(var f in a.p.treeReader)a.p.treeReader[f]==e&&h.push(e)}d.each(a.p.treeReader,function(l,i){if(i&&d.inArray(i,h)===-1){if(l==="leaf_field")a.p._treeleafpos=c;c++;a.p.colNames.push(i);a.p.colModel.push({name:i,width:1,hidden:true,sortable:false,resizable:false,hidedlg:true,editable:true,search:false})}})}})},expandRow:function(a){this.each(function(){var c=
this;if(c.grid&&c.p.treeGrid){var b=d(c).jqGrid("getNodeChildren",a),e=c.p.treeReader.expanded_field;d(b).each(function(){var g=d.jgrid.getAccessor(this,c.p.localReader.id);d("#"+g,c.grid.bDiv).css("display","");this[e]&&d(c).jqGrid("expandRow",this)})}})},collapseRow:function(a){this.each(function(){var c=this;if(c.grid&&c.p.treeGrid){var b=d(c).jqGrid("getNodeChildren",a),e=c.p.treeReader.expanded_field;d(b).each(function(){var g=d.jgrid.getAccessor(this,c.p.localReader.id);d("#"+g,c.grid.bDiv).css("display",
"none");this[e]&&d(c).jqGrid("collapseRow",this)})}})},getRootNodes:function(){var a=[];this.each(function(){var c=this;if(c.grid&&c.p.treeGrid)switch(c.p.treeGridModel){case "nested":var b=c.p.treeReader.level_field;d(c.p.data).each(function(){parseInt(this[b],10)===parseInt(c.p.tree_root_level,10)&&a.push(this)});break;case "adjacency":var e=c.p.treeReader.parent_id_field;d(c.p.data).each(function(){if(this[e]===null||String(this[e]).toLowerCase()=="null")a.push(this)})}});return a},getNodeDepth:function(a){var c=
null;this.each(function(){if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":c=parseInt(a[this.p.treeReader.level_field],10)-parseInt(this.p.tree_root_level,10);break;case "adjacency":c=d(this).jqGrid("getNodeAncestors",a).length}});return c},getNodeParent:function(a){var c=null;this.each(function(){if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":var b=this.p.treeReader.left_field,e=this.p.treeReader.right_field,g=this.p.treeReader.level_field,h=parseInt(a[b],
10),f=parseInt(a[e],10),l=parseInt(a[g],10);d(this.p.data).each(function(){if(parseInt(this[g],10)===l-1&&parseInt(this[b],10)<h&&parseInt(this[e],10)>f){c=this;return false}});break;case "adjacency":var i=this.p.treeReader.parent_id_field,j=this.p.localReader.id;d(this.p.data).each(function(){if(this[j]==a[i]){c=this;return false}})}});return c},getNodeChildren:function(a){var c=[];this.each(function(){if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":var b=this.p.treeReader.left_field,
e=this.p.treeReader.right_field,g=this.p.treeReader.level_field,h=parseInt(a[b],10),f=parseInt(a[e],10),l=parseInt(a[g],10);d(this.p.data).each(function(){parseInt(this[g],10)===l+1&&parseInt(this[b],10)>h&&parseInt(this[e],10)<f&&c.push(this)});break;case "adjacency":var i=this.p.treeReader.parent_id_field,j=this.p.localReader.id;d(this.p.data).each(function(){this[i]==a[j]&&c.push(this)})}});return c},getFullTreeNode:function(a){var c=[];this.each(function(){var b;if(this.grid&&this.p.treeGrid)switch(this.p.treeGridModel){case "nested":var e=
this.p.treeReader.left_field,g=this.p.treeReader.right_field,h=this.p.treeReader.level_field,f=parseInt(a[e],10),l=parseInt(a[g],10),i=parseInt(a[h],10);d(this.p.data).each(function(){parseInt(this[h],10)>=i&&parseInt(this[e],10)>=f&&parseInt(this[e],10)<=l&&c.push(this)});break;case "adjacency":if(a){c.push(a);var j=this.p.treeReader.parent_id_field,o=this.p.localReader.id;d(this.p.data).each(function(n){b=c.length;for(n=0;n<b;n++)if(c[n][o]==this[j]){c.push(this);break}})}}});return c},getNodeAncestors:function(a){var c=
[];this.each(function(){if(this.grid&&this.p.treeGrid)for(var b=d(this).jqGrid("getNodeParent",a);b;){c.push(b);b=d(this).jqGrid("getNodeParent",b)}});return c},isVisibleNode:function(a){var c=true;this.each(function(){if(this.grid&&this.p.treeGrid){var b=d(this).jqGrid("getNodeAncestors",a),e=this.p.treeReader.expanded_field;d(b).each(function(){c=c&&this[e];if(!c)return false})}});return c},isNodeLoaded:function(a){var c;this.each(function(){if(this.grid&&this.p.treeGrid){var b=this.p.treeReader.leaf_field;
c=a!==undefined?a.loaded!==undefined?a.loaded:a[b]||d(this).jqGrid("getNodeChildren",a).length>0?true:false:false}});return c},expandNode:function(a){return this.each(function(){if(this.grid&&this.p.treeGrid){var c=this.p.treeReader.expanded_field,b=this.p.treeReader.parent_id_field,e=this.p.treeReader.loaded,g=this.p.treeReader.level_field,h=this.p.treeReader.left_field,f=this.p.treeReader.right_field;if(!a[c]){var l=d.jgrid.getAccessor(a,this.p.localReader.id),i=d("#"+l,this.grid.bDiv)[0],j=this.p._index[l];
if(d(this).jqGrid("isNodeLoaded",this.p.data[j])){a[c]=true;d("div.treeclick",i).removeClass(this.p.treeIcons.plus+" tree-plus").addClass(this.p.treeIcons.minus+" tree-minus")}else{a[c]=true;d("div.treeclick",i).removeClass(this.p.treeIcons.plus+" tree-plus").addClass(this.p.treeIcons.minus+" tree-minus");this.p.treeANode=i.rowIndex;this.p.datatype=this.p.treedatatype;this.p.treeGridModel=="nested"?d(this).jqGrid("setGridParam",{postData:{nodeid:l,n_left:a[h],n_right:a[f],n_level:a[g]}}):d(this).jqGrid("setGridParam",
{postData:{nodeid:l,parentid:a[b],n_level:a[g]}});d(this).trigger("reloadGrid");a[e]=true;this.p.treeGridModel=="nested"?d(this).jqGrid("setGridParam",{postData:{nodeid:"",n_left:"",n_right:"",n_level:""}}):d(this).jqGrid("setGridParam",{postData:{nodeid:"",parentid:"",n_level:""}})}}}})},collapseNode:function(a){return this.each(function(){if(this.grid&&this.p.treeGrid)if(a.expanded){a.expanded=false;var c=d.jgrid.getAccessor(a,this.p.localReader.id);c=d("#"+c,this.grid.bDiv)[0];d("div.treeclick",
c).removeClass(this.p.treeIcons.minus+" tree-minus").addClass(this.p.treeIcons.plus+" tree-plus")}})},SortTree:function(a,c,b,e){return this.each(function(){if(this.grid&&this.p.treeGrid){var g,h,f,l=[],i=this,j;g=d(this).jqGrid("getRootNodes");g=d.jgrid.from(g);g.orderBy(a,c,b,e);j=g.select();g=0;for(h=j.length;g<h;g++){f=j[g];l.push(f);d(this).jqGrid("collectChildrenSortTree",l,f,a,c,b,e)}d.each(l,function(o){var n=d.jgrid.getAccessor(this,i.p.localReader.id);d("#"+i.p.id+" tbody tr:eq("+o+")").after(d("tr#"+
n,i.grid.bDiv))});l=j=g=null}})},collectChildrenSortTree:function(a,c,b,e,g,h){return this.each(function(){if(this.grid&&this.p.treeGrid){var f,l,i,j;f=d(this).jqGrid("getNodeChildren",c);f=d.jgrid.from(f);f.orderBy(b,e,g,h);j=f.select();f=0;for(l=j.length;f<l;f++){i=j[f];a.push(i);d(this).jqGrid("collectChildrenSortTree",a,i,b,e,g,h)}}})},setTreeRow:function(a,c){var b=false;this.each(function(){if(this.grid&&this.p.treeGrid)b=d(this).jqGrid("setRowData",a,c)});return b},delTreeNode:function(a){return this.each(function(){var c=
this.p.localReader.id,b=this.p.treeReader.left_field,e=this.p.treeReader.right_field,g,h,f;if(this.grid&&this.p.treeGrid){var l=this.p._index[a];if(l!==undefined){g=parseInt(this.p.data[l][e],10);h=g-parseInt(this.p.data[l][b],10)+1;l=d(this).jqGrid("getFullTreeNode",this.p.data[l]);if(l.length>0)for(var i=0;i<l.length;i++)d(this).jqGrid("delRowData",l[i][c]);if(this.p.treeGridModel==="nested"){c=d.jgrid.from(this.p.data).greater(b,g,{stype:"integer"}).select();if(c.length)for(f in c)c[f][b]=parseInt(c[f][b],
10)-h;c=d.jgrid.from(this.p.data).greater(e,g,{stype:"integer"}).select();if(c.length)for(f in c)c[f][e]=parseInt(c[f][e],10)-h}}}})},addChildNode:function(a,c,b){var e=this[0];if(b){var g=e.p.treeReader.expanded_field,h=e.p.treeReader.leaf_field,f=e.p.treeReader.level_field,l=e.p.treeReader.parent_id_field,i=e.p.treeReader.left_field,j=e.p.treeReader.right_field,o=e.p.treeReader.loaded,n,k,m,r,p;n=0;var s=c,t;if(!a){p=e.p.data.length-1;if(p>=0)for(;p>=0;){n=Math.max(n,parseInt(e.p.data[p][e.p.localReader.id],
10));p--}a=n+1}var u=d(e).jqGrid("getInd",c);t=false;if(c===undefined||c===null||c===""){s=c=null;n="last";r=e.p.tree_root_level;p=e.p.data.length+1}else{n="after";k=e.p._index[c];m=e.p.data[k];c=m[e.p.localReader.id];r=parseInt(m[f],10)+1;p=d(e).jqGrid("getFullTreeNode",m);if(p.length){s=p=p[p.length-1][e.p.localReader.id];p=d(e).jqGrid("getInd",s)+1}else p=d(e).jqGrid("getInd",c)+1;if(m[h]){t=true;m[g]=true;d(e.rows[u]).find("span.cell-wrapperleaf").removeClass("cell-wrapperleaf").addClass("cell-wrapper").end().find("div.tree-leaf").removeClass(e.p.treeIcons.leaf+
" tree-leaf").addClass(e.p.treeIcons.minus+" tree-minus");e.p.data[k][h]=false;m[o]=true}}k=p+1;b[g]=false;b[o]=true;b[f]=r;b[h]=true;if(e.p.treeGridModel==="adjacency")b[l]=c;if(e.p.treeGridModel==="nested"){var q;if(c!==null){h=parseInt(m[j],10);f=d.jgrid.from(e.p.data);f=f.greaterOrEquals(j,h,{stype:"integer"});f=f.select();if(f.length)for(q in f){f[q][i]=f[q][i]>h?parseInt(f[q][i],10)+2:f[q][i];f[q][j]=f[q][j]>=h?parseInt(f[q][j],10)+2:f[q][j]}b[i]=h;b[j]=h+1}else{h=parseInt(d(e).jqGrid("getCol",
j,false,"max"),10);f=d.jgrid.from(e.p.data).greater(i,h,{stype:"integer"}).select();if(f.length)for(q in f)f[q][i]=parseInt(f[q][i],10)+2;f=d.jgrid.from(e.p.data).greater(j,h,{stype:"integer"}).select();if(f.length)for(q in f)f[q][j]=parseInt(f[q][j],10)+2;b[i]=h+1;b[j]=h+2}}if(c===null||d(e).jqGrid("isNodeLoaded",m)||t){d(e).jqGrid("addRowData",a,b,n,s);d(e).jqGrid("setTreeNode",p,k)}m&&!m[g]&&d(e.rows[u]).find("div.treeclick").click()}}})})(jQuery);
(function(b){b.jgrid.extend({jqGridImport:function(a){a=b.extend({imptype:"xml",impstring:"",impurl:"",mtype:"GET",impData:{},xmlGrid:{config:"roots>grid",data:"roots>rows"},jsonGrid:{config:"grid",data:"data"},ajaxOptions:{}},a||{});return this.each(function(){var d=this,c=function(f,g){var e=b(g.xmlGrid.config,f)[0],h=b(g.xmlGrid.data,f)[0],i;if(xmlJsonClass.xml2json&&b.jgrid.parse){e=xmlJsonClass.xml2json(e," ");e=b.jgrid.parse(e);for(var l in e)if(e.hasOwnProperty(l))i=e[l];if(h){h=e.grid.datatype;
e.grid.datatype="xmlstring";e.grid.datastr=f;b(d).jqGrid(i).jqGrid("setGridParam",{datatype:h})}else b(d).jqGrid(i)}else alert("xml2json or parse are not present")},j=function(f,g){if(f&&typeof f=="string"){var e=b.jgrid.parse(f),h=e[g.jsonGrid.config];if(e=e[g.jsonGrid.data]){var i=h.datatype;h.datatype="jsonstring";h.datastr=e;b(d).jqGrid(h).jqGrid("setGridParam",{datatype:i})}else b(d).jqGrid(h)}};switch(a.imptype){case "xml":b.ajax(b.extend({url:a.impurl,type:a.mtype,data:a.impData,dataType:"xml",
complete:function(f,g){if(g=="success"){c(f.responseXML,a);b.isFunction(a.importComplete)&&a.importComplete(f)}}},a.ajaxOptions));break;case "xmlstring":if(a.impstring&&typeof a.impstring=="string"){var k=b.jgrid.stringToDoc(a.impstring);if(k){c(k,a);b.isFunction(a.importComplete)&&a.importComplete(k);a.impstring=null}k=null}break;case "json":b.ajax(b.extend({url:a.impurl,type:a.mtype,data:a.impData,dataType:"json",complete:function(f,g){if(g=="success"){j(f.responseText,a);b.isFunction(a.importComplete)&&
a.importComplete(f)}}},a.ajaxOptions));break;case "jsonstring":if(a.impstring&&typeof a.impstring=="string"){j(a.impstring,a);b.isFunction(a.importComplete)&&a.importComplete(a.impstring);a.impstring=null}}})},jqGridExport:function(a){a=b.extend({exptype:"xmlstring",root:"grid",ident:"\t"},a||{});var d=null;this.each(function(){if(this.grid){var c=b.extend({},b(this).jqGrid("getGridParam"));if(c.rownumbers){c.colNames.splice(0,1);c.colModel.splice(0,1)}if(c.multiselect){c.colNames.splice(0,1);c.colModel.splice(0,
1)}if(c.subGrid){c.colNames.splice(0,1);c.colModel.splice(0,1)}c.knv=null;if(c.treeGrid)for(var j in c.treeReader)if(c.treeReader.hasOwnProperty(j)){c.colNames.splice(c.colNames.length-1);c.colModel.splice(c.colModel.length-1)}switch(a.exptype){case "xmlstring":d="<"+a.root+">"+xmlJsonClass.json2xml(c,a.ident)+"</"+a.root+">";break;case "jsonstring":d="{"+xmlJsonClass.toJson(c,a.root,a.ident,false)+"}";if(c.postData.filters!==undefined){d=d.replace(/filters":"/,'filters":');d=d.replace(/}]}"/,"}]}")}}}});
return d},excelExport:function(a){a=b.extend({exptype:"remote",url:null,oper:"oper",tag:"excel",exportOptions:{}},a||{});return this.each(function(){if(this.grid){var d;if(a.exptype=="remote"){d=b.extend({},this.p.postData);d[a.oper]=a.tag;d=jQuery.param(d);d=a.url.indexOf("?")!=-1?a.url+"&"+d:a.url+"?"+d;window.location=d}}})}})})(jQuery);
var xmlJsonClass={xml2json:function(a,b){if(a.nodeType===9)a=a.documentElement;var h=this.toJson(this.toObj(this.removeWhite(a)),a.nodeName,"\t");return"{\n"+b+(b?h.replace(/\t/g,b):h.replace(/\t|\n/g,""))+"\n}"},json2xml:function(a,b){var h=function(d,c,i){var g="",k,j;if(d instanceof Array)if(d.length===0)g+=i+"<"+c+">__EMPTY_ARRAY_</"+c+">\n";else{k=0;for(j=d.length;k<j;k+=1){var l=i+h(d[k],c,i+"\t")+"\n";g+=l}}else if(typeof d==="object"){k=false;g+=i+"<"+c;for(j in d)if(d.hasOwnProperty(j))if(j.charAt(0)===
"@")g+=" "+j.substr(1)+'="'+d[j].toString()+'"';else k=true;g+=k?">":"/>";if(k){for(j in d)if(d.hasOwnProperty(j))if(j==="#text")g+=d[j];else if(j==="#cdata")g+="<![CDATA["+d[j]+"]]\>";else if(j.charAt(0)!=="@")g+=h(d[j],j,i+"\t");g+=(g.charAt(g.length-1)==="\n"?i:"")+"</"+c+">"}}else if(typeof d==="function")g+=i+"<"+c+"><![CDATA["+d+"]]\></"+c+">";else{if(d===undefined)d="";g+=d.toString()==='""'||d.toString().length===0?i+"<"+c+">__EMPTY_STRING_</"+c+">":i+"<"+c+">"+d.toString()+"</"+c+">"}return g},
e="",f;for(f in a)if(a.hasOwnProperty(f))e+=h(a[f],f,"");return b?e.replace(/\t/g,b):e.replace(/\t|\n/g,"")},toObj:function(a){var b={},h=/function/i;if(a.nodeType===1){if(a.attributes.length){var e;for(e=0;e<a.attributes.length;e+=1)b["@"+a.attributes[e].nodeName]=(a.attributes[e].nodeValue||"").toString()}if(a.firstChild){var f=e=0,d=false,c;for(c=a.firstChild;c;c=c.nextSibling)if(c.nodeType===1)d=true;else if(c.nodeType===3&&c.nodeValue.match(/[^ \f\n\r\t\v]/))e+=1;else if(c.nodeType===4)f+=1;
if(d)if(e<2&&f<2){this.removeWhite(a);for(c=a.firstChild;c;c=c.nextSibling)if(c.nodeType===3)b["#text"]=this.escape(c.nodeValue);else if(c.nodeType===4)if(h.test(c.nodeValue))b[c.nodeName]=[b[c.nodeName],c.nodeValue];else b["#cdata"]=this.escape(c.nodeValue);else if(b[c.nodeName])if(b[c.nodeName]instanceof Array)b[c.nodeName][b[c.nodeName].length]=this.toObj(c);else b[c.nodeName]=[b[c.nodeName],this.toObj(c)];else b[c.nodeName]=this.toObj(c)}else if(a.attributes.length)b["#text"]=this.escape(this.innerXml(a));
else b=this.escape(this.innerXml(a));else if(e)if(a.attributes.length)b["#text"]=this.escape(this.innerXml(a));else{b=this.escape(this.innerXml(a));if(b==="__EMPTY_ARRAY_")b="[]";else if(b==="__EMPTY_STRING_")b=""}else if(f)if(f>1)b=this.escape(this.innerXml(a));else for(c=a.firstChild;c;c=c.nextSibling)if(h.test(a.firstChild.nodeValue)){b=a.firstChild.nodeValue;break}else b["#cdata"]=this.escape(c.nodeValue)}if(!a.attributes.length&&!a.firstChild)b=null}else if(a.nodeType===9)b=this.toObj(a.documentElement);
else alert("unhandled node type: "+a.nodeType);return b},toJson:function(a,b,h,e){if(e===undefined)e=true;var f=b?'"'+b+'"':"",d="\t",c="\n";if(!e)c=d="";if(a==="[]")f+=b?":[]":"[]";else if(a instanceof Array){var i,g,k=[];g=0;for(i=a.length;g<i;g+=1)k[g]=this.toJson(a[g],"",h+d,e);f+=(b?":[":"[")+(k.length>1?c+h+d+k.join(","+c+h+d)+c+h:k.join(""))+"]"}else if(a===null)f+=(b&&":")+"null";else if(typeof a==="object"){i=[];for(g in a)if(a.hasOwnProperty(g))i[i.length]=this.toJson(a[g],g,h+d,e);f+=(b?
":{":"{")+(i.length>1?c+h+d+i.join(","+c+h+d)+c+h:i.join(""))+"}"}else f+=typeof a==="string"?(b&&":")+'"'+a.replace(/\\/g,"\\\\").replace(/\"/g,'\\"')+'"':(b&&":")+'"'+a.toString()+'"';return f},innerXml:function(a){var b="";if("innerHTML"in a)b=a.innerHTML;else{var h=function(e){var f="",d;if(e.nodeType===1){f+="<"+e.nodeName;for(d=0;d<e.attributes.length;d+=1)f+=" "+e.attributes[d].nodeName+'="'+(e.attributes[d].nodeValue||"").toString()+'"';if(e.firstChild){f+=">";for(d=e.firstChild;d;d=d.nextSibling)f+=
h(d);f+="</"+e.nodeName+">"}else f+="/>"}else if(e.nodeType===3)f+=e.nodeValue;else if(e.nodeType===4)f+="<![CDATA["+e.nodeValue+"]]\>";return f};for(a=a.firstChild;a;a=a.nextSibling)b+=h(a)}return b},escape:function(a){return a.replace(/[\\]/g,"\\\\").replace(/[\"]/g,'\\"').replace(/[\n]/g,"\\n").replace(/[\r]/g,"\\r")},removeWhite:function(a){a.normalize();var b;for(b=a.firstChild;b;)if(b.nodeType===3)if(b.nodeValue.match(/[^ \f\n\r\t\v]/))b=b.nextSibling;else{var h=b.nextSibling;a.removeChild(b);
b=h}else{b.nodeType===1&&this.removeWhite(b);b=b.nextSibling}return a}};
function tableToGrid(n,o){jQuery(n).each(function(){if(!this.grid){jQuery(this).width("99%");var a=jQuery(this).width(),d=jQuery("tr td:first-child input[type=checkbox]:first",jQuery(this)),b=jQuery("tr td:first-child input[type=radio]:first",jQuery(this));d=d.length>0;b=!d&&b.length>0;var l=d||b,c=[],g=[];jQuery("th",jQuery(this)).each(function(){if(c.length===0&&l){c.push({name:"__selection__",index:"__selection__",width:0,hidden:true});g.push("__selection__")}else{c.push({name:jQuery(this).attr("id")||
jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),index:jQuery(this).attr("id")||jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),width:jQuery(this).width()||150});g.push(jQuery(this).html())}});var f=[],h=[],i=[];jQuery("tbody > tr",jQuery(this)).each(function(){var j={},e=0;jQuery("td",jQuery(this)).each(function(){if(e===0&&l){var k=jQuery("input",jQuery(this)),m=k.attr("value");h.push(m||f.length);k.is(":checked")&&i.push(m);j[c[e].name]=
k.attr("value")}else j[c[e].name]=jQuery(this).html();e++});e>0&&f.push(j)});jQuery(this).empty();jQuery(this).addClass("scroll");jQuery(this).jqGrid(jQuery.extend({datatype:"local",width:a,colNames:g,colModel:c,multiselect:d},o||{}));for(a=0;a<f.length;a++){b=null;if(h.length>0)if((b=h[a])&&b.replace)b=encodeURIComponent(b).replace(/[.\-%]/g,"_");if(b===null)b=a+1;jQuery(this).jqGrid("addRowData",b,f[a])}for(a=0;a<i.length;a++)jQuery(this).jqGrid("setSelection",i[a])}})};
(function(a){if(a.browser.msie&&a.browser.version==8)a.expr[":"].hidden=function(b){return b.offsetWidth===0||b.offsetHeight===0||b.style.display=="none"};a.jgrid._multiselect=false;if(a.ui)if(a.ui.multiselect){if(a.ui.multiselect.prototype._setSelected){var q=a.ui.multiselect.prototype._setSelected;a.ui.multiselect.prototype._setSelected=function(b,g){var c=q.call(this,b,g);if(g&&this.selectedList){var f=this.element;this.selectedList.find("li").each(function(){a(this).data("optionLink")&&a(this).data("optionLink").remove().appendTo(f)})}return c}}if(a.ui.multiselect.prototype.destroy)a.ui.multiselect.prototype.destroy=
function(){this.element.show();this.container.remove();a.Widget===undefined?a.widget.prototype.destroy.apply(this,arguments):a.Widget.prototype.destroy.apply(this,arguments)};a.jgrid._multiselect=true}a.jgrid.extend({sortableColumns:function(b){return this.each(function(){function g(){c.p.disableClick=true}var c=this,f=c.p.id;f={tolerance:"pointer",axis:"x",scrollSensitivity:"1",items:">th:not(:has(#jqgh_"+f+"_cb,#jqgh_"+f+"_rn,#jqgh_"+f+"_subgrid),:hidden)",placeholder:{element:function(h){return a(document.createElement(h[0].nodeName)).addClass(h[0].className+
" ui-sortable-placeholder ui-state-highlight").removeClass("ui-sortable-helper")[0]},update:function(h,j){j.height(h.currentItem.innerHeight()-parseInt(h.currentItem.css("paddingTop")||0,10)-parseInt(h.currentItem.css("paddingBottom")||0,10));j.width(h.currentItem.innerWidth()-parseInt(h.currentItem.css("paddingLeft")||0,10)-parseInt(h.currentItem.css("paddingRight")||0,10))}},update:function(h,j){var i=a(j.item).parent();i=a(">th",i);var l={},m=c.p.id+"_";a.each(c.p.colModel,function(k){l[this.name]=
k});var d=[];i.each(function(){var k=a(">div",this).get(0).id.replace(/^jqgh_/,"").replace(m,"");k in l&&d.push(l[k])});a(c).jqGrid("remapColumns",d,true,true);a.isFunction(c.p.sortable.update)&&c.p.sortable.update(d);setTimeout(function(){c.p.disableClick=false},50)}};if(c.p.sortable.options)a.extend(f,c.p.sortable.options);else if(a.isFunction(c.p.sortable))c.p.sortable={update:c.p.sortable};if(f.start){var e=f.start;f.start=function(h,j){g();e.call(this,h,j)}}else f.start=g;if(c.p.sortable.exclude)f.items+=
":not("+c.p.sortable.exclude+")";b.sortable(f).data("sortable").floating=true})},columnChooser:function(b){function g(d,k){if(d)if(typeof d=="string")a.fn[d]&&a.fn[d].apply(k,a.makeArray(arguments).slice(2));else a.isFunction(d)&&d.apply(k,a.makeArray(arguments).slice(2))}var c=this;if(!a("#colchooser_"+c[0].p.id).length){var f=a('<div id="colchooser_'+c[0].p.id+'" style="position:relative;overflow:hidden"><div><select multiple="multiple"></select></div></div>'),e=a("select",f);b=a.extend({width:420,
height:240,classname:null,done:function(d){d&&c.jqGrid("remapColumns",d,true)},msel:"multiselect",dlog:"dialog",dlog_opts:function(d){var k={};k[d.bSubmit]=function(){d.apply_perm();d.cleanup(false)};k[d.bCancel]=function(){d.cleanup(true)};return{buttons:k,close:function(){d.cleanup(true)},modal:d.modal?d.modal:false,resizable:d.resizable?d.resizable:true,width:d.width+20}},apply_perm:function(){a("option",e).each(function(){this.selected?c.jqGrid("showCol",h[this.value].name):c.jqGrid("hideCol",
h[this.value].name)});var d=[];a("option:selected",e).each(function(){d.push(parseInt(this.value,10))});a.each(d,function(){delete i[h[parseInt(this,10)].name]});a.each(i,function(){var k=parseInt(this,10);var p=d,o=k;if(o>=0){var n=p.slice(),r=n.splice(o,Math.max(p.length-o,o));if(o>p.length)o=p.length;n[o]=k;d=n.concat(r)}else d=void 0});b.done&&b.done.call(c,d)},cleanup:function(d){g(b.dlog,f,"destroy");g(b.msel,e,"destroy");f.remove();d&&b.done&&b.done.call(c)},msel_opts:{}},a.jgrid.col,b||{});
if(a.ui)if(a.ui.multiselect)if(b.msel=="multiselect"){if(!a.jgrid._multiselect){alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");return}b.msel_opts=a.extend(a.ui.multiselect.defaults,b.msel_opts)}b.caption&&f.attr("title",b.caption);if(b.classname){f.addClass(b.classname);e.addClass(b.classname)}if(b.width){a(">div",f).css({width:b.width,margin:"0 auto"});e.css("width",b.width)}if(b.height){a(">div",f).css("height",b.height);e.css("height",b.height-10)}var h=
c.jqGrid("getGridParam","colModel"),j=c.jqGrid("getGridParam","colNames"),i={},l=[];e.empty();a.each(h,function(d){i[this.name]=d;if(this.hidedlg)this.hidden||l.push(d);else e.append("<option value='"+d+"' "+(this.hidden?"":"selected='selected'")+">"+j[d]+"</option>")});var m=a.isFunction(b.dlog_opts)?b.dlog_opts.call(c,b):b.dlog_opts;g(b.dlog,f,m);m=a.isFunction(b.msel_opts)?b.msel_opts.call(c,b):b.msel_opts;g(b.msel,e,m)}},sortableRows:function(b){return this.each(function(){var g=this;if(g.grid)if(!g.p.treeGrid)if(a.fn.sortable){b=
a.extend({cursor:"move",axis:"y",items:".jqgrow"},b||{});if(b.start&&a.isFunction(b.start)){b._start_=b.start;delete b.start}else b._start_=false;if(b.update&&a.isFunction(b.update)){b._update_=b.update;delete b.update}else b._update_=false;b.start=function(c,f){a(f.item).css("border-width","0px");a("td",f.item).each(function(j){this.style.width=g.grid.cols[j].style.width});if(g.p.subGrid){var e=a(f.item).attr("id");try{a(g).jqGrid("collapseSubGridRow",e)}catch(h){}}b._start_&&b._start_.apply(this,
[c,f])};b.update=function(c,f){a(f.item).css("border-width","");g.p.rownumbers===true&&a("td.jqgrid-rownum",g.rows).each(function(e){a(this).html(e+1+(parseInt(g.p.page,10)-1)*parseInt(g.p.rowNum,10))});b._update_&&b._update_.apply(this,[c,f])};a("tbody:first",g).sortable(b);a("tbody:first",g).disableSelection()}})},gridDnD:function(b){return this.each(function(){function g(){var e=a.data(c,"dnd");a("tr.jqgrow:not(.ui-draggable)",c).draggable(a.isFunction(e.drag)?e.drag.call(a(c),e):e.drag)}var c=
this;if(c.grid)if(!c.p.treeGrid)if(a.fn.draggable&&a.fn.droppable){a("#jqgrid_dnd").html()===null&&a("body").append("<table id='jqgrid_dnd' class='ui-jqgrid-dnd'></table>");if(typeof b=="string"&&b=="updateDnD"&&c.p.jqgdnd===true)g();else{b=a.extend({drag:function(e){return a.extend({start:function(h,j){if(c.p.subGrid){var i=a(j.helper).attr("id");try{a(c).jqGrid("collapseSubGridRow",i)}catch(l){}}for(i=0;i<a.data(c,"dnd").connectWith.length;i++)a(a.data(c,"dnd").connectWith[i]).jqGrid("getGridParam",
"reccount")=="0"&&a(a.data(c,"dnd").connectWith[i]).jqGrid("addRowData","jqg_empty_row",{});j.helper.addClass("ui-state-highlight");a("td",j.helper).each(function(m){this.style.width=c.grid.headers[m].width+"px"});e.onstart&&a.isFunction(e.onstart)&&e.onstart.call(a(c),h,j)},stop:function(h,j){if(j.helper.dropped&&!e.dragcopy){var i=a(j.helper).attr("id");a(c).jqGrid("delRowData",i)}for(i=0;i<a.data(c,"dnd").connectWith.length;i++)a(a.data(c,"dnd").connectWith[i]).jqGrid("delRowData","jqg_empty_row");
e.onstop&&a.isFunction(e.onstop)&&e.onstop.call(a(c),h,j)}},e.drag_opts||{})},drop:function(e){return a.extend({accept:function(h){if(!a(h).hasClass("jqgrow"))return h;var j=a(h).closest("table.ui-jqgrid-btable");if(j.length>0&&a.data(j[0],"dnd")!==undefined){h=a.data(j[0],"dnd").connectWith;return a.inArray("#"+this.id,h)!=-1?true:false}return h},drop:function(h,j){if(a(j.draggable).hasClass("jqgrow")){var i=a(j.draggable).attr("id");i=j.draggable.parent().parent().jqGrid("getRowData",i);if(!e.dropbyname){var l=
0,m={},d,k=a("#"+this.id).jqGrid("getGridParam","colModel");try{for(var p in i){if(i.hasOwnProperty(p)&&k[l]){d=k[l].name;m[d]=i[p]}l++}i=m}catch(o){}}j.helper.dropped=true;if(e.beforedrop&&a.isFunction(e.beforedrop)){d=e.beforedrop.call(this,h,j,i,a("#"+c.id),a(this));if(typeof d!="undefined"&&d!==null&&typeof d=="object")i=d}if(j.helper.dropped){var n;if(e.autoid)if(a.isFunction(e.autoid))n=e.autoid.call(this,i);else{n=Math.ceil(Math.random()*1E3);n=e.autoidprefix+n}a("#"+this.id).jqGrid("addRowData",
n,i,e.droppos)}e.ondrop&&a.isFunction(e.ondrop)&&e.ondrop.call(this,h,j,i)}}},e.drop_opts||{})},onstart:null,onstop:null,beforedrop:null,ondrop:null,drop_opts:{activeClass:"ui-state-active",hoverClass:"ui-state-hover"},drag_opts:{revert:"invalid",helper:"clone",cursor:"move",appendTo:"#jqgrid_dnd",zIndex:5E3},dragcopy:false,dropbyname:false,droppos:"first",autoid:true,autoidprefix:"dnd_"},b||{});if(b.connectWith){b.connectWith=b.connectWith.split(",");b.connectWith=a.map(b.connectWith,function(e){return a.trim(e)});
a.data(c,"dnd",b);c.p.reccount!="0"&&!c.p.jqgdnd&&g();c.p.jqgdnd=true;for(var f=0;f<b.connectWith.length;f++)a(b.connectWith[f]).droppable(a.isFunction(b.drop)?b.drop.call(a(c),b):b.drop)}}}})},gridResize:function(b){return this.each(function(){var g=this;if(g.grid&&a.fn.resizable){b=a.extend({},b||{});if(b.alsoResize){b._alsoResize_=b.alsoResize;delete b.alsoResize}else b._alsoResize_=false;if(b.stop&&a.isFunction(b.stop)){b._stop_=b.stop;delete b.stop}else b._stop_=false;b.stop=function(c,f){a(g).jqGrid("setGridParam",
{height:a("#gview_"+g.p.id+" .ui-jqgrid-bdiv").height()});a(g).jqGrid("setGridWidth",f.size.width,b.shrinkToFit);b._stop_&&b._stop_.call(g,c,f)};b.alsoResize=b._alsoResize_?eval("("+("{'#gview_"+g.p.id+" .ui-jqgrid-bdiv':true,'"+b._alsoResize_+"':true}")+")"):a(".ui-jqgrid-bdiv","#gview_"+g.p.id);delete b._alsoResize_;a("#gbox_"+g.p.id).resizable(b)}})}})})(jQuery);

/*
 * jQuery UI 1.8.6
 *
 * Copyright 2010, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI
 */
(function(C,B){function A(D){return !C(D).parents().andSelf().filter(function(){return C.curCSS(this,"visibility")==="hidden"||C.expr.filters.hidden(this)
}).length
}C.ui=C.ui||{};
if(!C.ui.version){C.extend(C.ui,{version:"1.8.6",keyCode:{ALT:18,BACKSPACE:8,CAPS_LOCK:20,COMMA:188,COMMAND:91,COMMAND_LEFT:91,COMMAND_RIGHT:93,CONTROL:17,DELETE:46,DOWN:40,END:35,ENTER:13,ESCAPE:27,HOME:36,INSERT:45,LEFT:37,MENU:93,NUMPAD_ADD:107,NUMPAD_DECIMAL:110,NUMPAD_DIVIDE:111,NUMPAD_ENTER:108,NUMPAD_MULTIPLY:106,NUMPAD_SUBTRACT:109,PAGE_DOWN:34,PAGE_UP:33,PERIOD:190,RIGHT:39,SHIFT:16,SPACE:32,TAB:9,UP:38,WINDOWS:91}});
C.fn.extend({_focus:C.fn.focus,focus:function(E,D){return typeof E==="number"?this.each(function(){var F=this;
setTimeout(function(){C(F).focus();
D&&D.call(F)
},E)
}):this._focus.apply(this,arguments)
},scrollParent:function(){var D;
D=C.browser.msie&&/(static|relative)/.test(this.css("position"))||/absolute/.test(this.css("position"))?this.parents().filter(function(){return/(relative|absolute|fixed)/.test(C.curCSS(this,"position",1))&&/(auto|scroll)/.test(C.curCSS(this,"overflow",1)+C.curCSS(this,"overflow-y",1)+C.curCSS(this,"overflow-x",1))
}).eq(0):this.parents().filter(function(){return/(auto|scroll)/.test(C.curCSS(this,"overflow",1)+C.curCSS(this,"overflow-y",1)+C.curCSS(this,"overflow-x",1))
}).eq(0);
return/fixed/.test(this.css("position"))||!D.length?C(document):D
},zIndex:function(E){if(E!==B){return this.css("zIndex",E)
}if(this.length){E=C(this[0]);
for(var D;
E.length&&E[0]!==document;
){D=E.css("position");
if(D==="absolute"||D==="relative"||D==="fixed"){D=parseInt(E.css("zIndex"),10);
if(!isNaN(D)&&D!==0){return D
}}E=E.parent()
}}return 0
},disableSelection:function(){return this.bind((C.support.selectstart?"selectstart":"mousedown")+".ui-disableSelection",function(D){D.preventDefault()
})
},enableSelection:function(){return this.unbind(".ui-disableSelection")
}});
C.each(["Width","Height"],function(E,D){function I(M,L,K,J){C.each(H,function(){L-=parseFloat(C.curCSS(M,"padding"+this,true))||0;
if(K){L-=parseFloat(C.curCSS(M,"border"+this+"Width",true))||0
}if(J){L-=parseFloat(C.curCSS(M,"margin"+this,true))||0
}});
return L
}var H=D==="Width"?["Left","Right"]:["Top","Bottom"],G=D.toLowerCase(),F={innerWidth:C.fn.innerWidth,innerHeight:C.fn.innerHeight,outerWidth:C.fn.outerWidth,outerHeight:C.fn.outerHeight};
C.fn["inner"+D]=function(J){if(J===B){return F["inner"+D].call(this)
}return this.each(function(){C(this).css(G,I(this,J)+"px")
})
};
C.fn["outer"+D]=function(K,J){if(typeof K!=="number"){return F["outer"+D].call(this,K)
}return this.each(function(){C(this).css(G,I(this,K,true,J)+"px")
})
}
});
C.extend(C.expr[":"],{data:function(E,D,F){return !!C.data(E,F[3])
},focusable:function(E){var D=E.nodeName.toLowerCase(),F=C.attr(E,"tabindex");
if("area"===D){D=E.parentNode;
F=D.name;
if(!E.href||!F||D.nodeName.toLowerCase()!=="map"){return false
}E=C("img[usemap=#"+F+"]")[0];
return !!E&&A(E)
}return(/input|select|textarea|button|object/.test(D)?!E.disabled:"a"==D?E.href||!isNaN(F):!isNaN(F))&&A(E)
},tabbable:function(E){var D=C.attr(E,"tabindex");
return(isNaN(D)||D>=0)&&C(E).is(":focusable")
}});
C(function(){var E=document.body,D=E.appendChild(D=document.createElement("div"));
C.extend(D.style,{minHeight:"100px",height:"auto",padding:0,borderWidth:0});
C.support.minHeight=D.offsetHeight===100;
C.support.selectstart="onselectstart" in D;
E.removeChild(D).style.display="none"
});
C.extend(C.ui,{plugin:{add:function(E,D,G){E=C.ui[E].prototype;
for(var F in G){E.plugins[F]=E.plugins[F]||[];
E.plugins[F].push([D,G[F]])
}},call:function(E,D,G){if((D=E.plugins[D])&&E.element[0].parentNode){for(var F=0;
F<D.length;
F++){E.options[D[F][0]]&&D[F][1].apply(E.element,G)
}}}},contains:function(E,D){return document.compareDocumentPosition?E.compareDocumentPosition(D)&16:E!==D&&E.contains(D)
},hasScroll:function(E,D){if(C(E).css("overflow")==="hidden"){return false
}D=D&&D==="left"?"scrollLeft":"scrollTop";
var F=false;
if(E[D]>0){return true
}E[D]=1;
F=E[D]>0;
E[D]=0;
return F
},isOverAxis:function(E,D,F){return E>D&&E<D+F
},isOver:function(E,D,I,H,G,F){return C.ui.isOverAxis(E,I,G)&&C.ui.isOverAxis(D,H,F)
}})
}})(jQuery);
/*
 * jQuery UI Widget 1.8.6
 *
 * Copyright 2010, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Widget
 */
(function(A,D){if(A.cleanData){var C=A.cleanData;
A.cleanData=function(E){for(var G=0,F;
(F=E[G])!=null;
G++){A(F).triggerHandler("remove")
}C(E)
}
}else{var B=A.fn.remove;
A.fn.remove=function(E,F){return this.each(function(){if(!F){if(!E||A.filter(E,[this]).length){A("*",this).add([this]).each(function(){A(this).triggerHandler("remove")
})
}}return B.call(A(this),E,F)
})
}
}A.widget=function(E,I,H){var G=E.split(".")[0],F;
E=E.split(".")[1];
F=G+"-"+E;
if(!H){H=I;
I=A.Widget
}A.expr[":"][F]=function(J){return !!A.data(J,E)
};
A[G]=A[G]||{};
A[G][E]=function(J,K){arguments.length&&this._createWidget(J,K)
};
I=new I;
I.options=A.extend(true,{},I.options);
A[G][E].prototype=A.extend(true,I,{namespace:G,widgetName:E,widgetEventPrefix:A[G][E].prototype.widgetEventPrefix||E,widgetBaseClass:F},H);
A.widget.bridge(E,A[G][E])
};
A.widget.bridge=function(E,F){A.fn[E]=function(J){var I=typeof J==="string",H=Array.prototype.slice.call(arguments,1),G=this;
J=!I&&H.length?A.extend.apply(null,[true,J].concat(H)):J;
if(I&&J.charAt(0)==="_"){return G
}I?this.each(function(){var L=A.data(this,E),K=L&&A.isFunction(L[J])?L[J].apply(L,H):L;
if(K!==L&&K!==D){G=K;
return false
}}):this.each(function(){var K=A.data(this,E);
K?K.option(J||{})._init():A.data(this,E,new F(J,this))
});
return G
}
};
A.Widget=function(E,F){arguments.length&&this._createWidget(E,F)
};
A.Widget.prototype={widgetName:"widget",widgetEventPrefix:"",options:{disabled:false},_createWidget:function(E,G){A.data(G,this.widgetName,this);
this.element=A(G);
this.options=A.extend(true,{},this.options,this._getCreateOptions(),E);
var F=this;
this.element.bind("remove."+this.widgetName,function(){F.destroy()
});
this._create();
this._trigger("create");
this._init()
},_getCreateOptions:function(){return A.metadata&&A.metadata.get(this.element[0])[this.widgetName]
},_create:function(){},_init:function(){},destroy:function(){this.element.unbind("."+this.widgetName).removeData(this.widgetName);
this.widget().unbind("."+this.widgetName).removeAttr("aria-disabled").removeClass(this.widgetBaseClass+"-disabled ui-state-disabled")
},widget:function(){return this.element
},option:function(E,G){var F=E;
if(arguments.length===0){return A.extend({},this.options)
}if(typeof E==="string"){if(G===D){return this.options[E]
}F={};
F[E]=G
}this._setOptions(F);
return this
},_setOptions:function(E){var F=this;
A.each(E,function(H,G){F._setOption(H,G)
});
return this
},_setOption:function(E,F){this.options[E]=F;
if(E==="disabled"){this.widget()[F?"addClass":"removeClass"](this.widgetBaseClass+"-disabled ui-state-disabled").attr("aria-disabled",F)
}return this
},enable:function(){return this._setOption("disabled",false)
},disable:function(){return this._setOption("disabled",true)
},_trigger:function(E,I,H){var G=this.options[E];
I=A.Event(I);
I.type=(E===this.widgetEventPrefix?E:this.widgetEventPrefix+E).toLowerCase();
H=H||{};
if(I.originalEvent){E=A.event.props.length;
for(var F;
E;
){F=A.event.props[--E];
I[F]=I.originalEvent[F]
}}this.element.trigger(I,H);
return !(A.isFunction(G)&&G.call(this.element[0],I,H)===false||I.isDefaultPrevented())
}}
})(jQuery);
/*
 * jQuery UI Mouse 1.8.6
 *
 * Copyright 2010, AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * http://docs.jquery.com/UI/Mouse
 *
 * Depends:
 *	jquery.ui.widget.js
 */
(function(A){A.widget("ui.mouse",{options:{cancel:":input,option",distance:1,delay:0},_mouseInit:function(){var B=this;
this.element.bind("mousedown."+this.widgetName,function(C){return B._mouseDown(C)
}).bind("click."+this.widgetName,function(C){if(B._preventClickEvent){B._preventClickEvent=false;
C.stopImmediatePropagation();
return false
}});
this.started=false
},_mouseDestroy:function(){this.element.unbind("."+this.widgetName)
},_mouseDown:function(C){C.originalEvent=C.originalEvent||{};
if(!C.originalEvent.mouseHandled){this._mouseStarted&&this._mouseUp(C);
this._mouseDownEvent=C;
var B=this,E=C.which==1,D=typeof this.options.cancel=="string"?A(C.target).parents().add(C.target).filter(this.options.cancel).length:false;
if(!E||D||!this._mouseCapture(C)){return true
}this.mouseDelayMet=!this.options.delay;
if(!this.mouseDelayMet){this._mouseDelayTimer=setTimeout(function(){B.mouseDelayMet=true
},this.options.delay)
}if(this._mouseDistanceMet(C)&&this._mouseDelayMet(C)){this._mouseStarted=this._mouseStart(C)!==false;
if(!this._mouseStarted){C.preventDefault();
return true
}}this._mouseMoveDelegate=function(F){return B._mouseMove(F)
};
this._mouseUpDelegate=function(F){return B._mouseUp(F)
};
A(document).bind("mousemove."+this.widgetName,this._mouseMoveDelegate).bind("mouseup."+this.widgetName,this._mouseUpDelegate);
C.preventDefault();
return C.originalEvent.mouseHandled=true
}},_mouseMove:function(B){if(A.browser.msie&&!(document.documentMode>=9)&&!B.button){return this._mouseUp(B)
}if(this._mouseStarted){this._mouseDrag(B);
return B.preventDefault()
}if(this._mouseDistanceMet(B)&&this._mouseDelayMet(B)){(this._mouseStarted=this._mouseStart(this._mouseDownEvent,B)!==false)?this._mouseDrag(B):this._mouseUp(B)
}return !this._mouseStarted
},_mouseUp:function(B){A(document).unbind("mousemove."+this.widgetName,this._mouseMoveDelegate).unbind("mouseup."+this.widgetName,this._mouseUpDelegate);
if(this._mouseStarted){this._mouseStarted=false;
this._preventClickEvent=B.target==this._mouseDownEvent.target;
this._mouseStop(B)
}return false
},_mouseDistanceMet:function(B){return Math.max(Math.abs(this._mouseDownEvent.pageX-B.pageX),Math.abs(this._mouseDownEvent.pageY-B.pageY))>=this.options.distance
},_mouseDelayMet:function(){return this.mouseDelayMet
},_mouseStart:function(){},_mouseDrag:function(){},_mouseStop:function(){},_mouseCapture:function(){return true
}})
})(jQuery);
(function(A){A.widget("ui.slider",A.ui.mouse,{widgetEventPrefix:"slide",options:{animate:false,distance:0,max:100,min:0,orientation:"horizontal",range:false,step:1,value:0,values:null},_create:function(){var C=this,B=this.options;
this._mouseSliding=this._keySliding=false;
this._animateOff=true;
this._handleIndex=null;
this._detectOrientation();
this._mouseInit();
this.element.addClass("ui-slider ui-slider-"+this.orientation+" ui-widget ui-widget-content ui-corner-all");
B.disabled&&this.element.addClass("ui-slider-disabled ui-disabled");
this.range=A([]);
if(B.range){if(B.range===true){this.range=A("<div></div>");
if(!B.values){B.values=[this._valueMin(),this._valueMin()]
}if(B.values.length&&B.values.length!==2){B.values=[B.values[0],B.values[0]]
}}else{this.range=A("<div></div>")
}this.range.appendTo(this.element).addClass("ui-slider-range");
if(B.range==="min"||B.range==="max"){this.range.addClass("ui-slider-range-"+B.range)
}this.range.addClass("ui-widget-header")
}A(".ui-slider-handle",this.element).length===0&&A("<a href='#'></a>").appendTo(this.element).addClass("ui-slider-handle");
if(B.values&&B.values.length){for(;
A(".ui-slider-handle",this.element).length<B.values.length;
){A("<a href='#'></a>").appendTo(this.element).addClass("ui-slider-handle")
}}this.handles=A(".ui-slider-handle",this.element).addClass("ui-state-default ui-corner-all");
this.handle=this.handles.eq(0);
this.handles.add(this.range).filter("a").click(function(D){D.preventDefault()
}).hover(function(){B.disabled||A(this).addClass("ui-state-hover")
},function(){A(this).removeClass("ui-state-hover")
}).focus(function(){if(B.disabled){A(this).blur()
}else{A(".ui-slider .ui-state-focus").removeClass("ui-state-focus");
A(this).addClass("ui-state-focus")
}}).blur(function(){A(this).removeClass("ui-state-focus")
});
this.handles.each(function(D){A(this).data("index.ui-slider-handle",D)
});
this.handles.keydown(function(I){var H=true,G=A(this).data("index.ui-slider-handle"),E,F,D;
if(!C.options.disabled){switch(I.keyCode){case A.ui.keyCode.HOME:case A.ui.keyCode.END:case A.ui.keyCode.PAGE_UP:case A.ui.keyCode.PAGE_DOWN:case A.ui.keyCode.UP:case A.ui.keyCode.RIGHT:case A.ui.keyCode.DOWN:case A.ui.keyCode.LEFT:H=false;
if(!C._keySliding){C._keySliding=true;
A(this).addClass("ui-state-active");
E=C._start(I,G);
if(E===false){return 
}}break
}D=C.options.step;
E=C.options.values&&C.options.values.length?(F=C.values(G)):(F=C.value());
switch(I.keyCode){case A.ui.keyCode.HOME:F=C._valueMin();
break;
case A.ui.keyCode.END:F=C._valueMax();
break;
case A.ui.keyCode.PAGE_UP:F=C._trimAlignValue(E+(C._valueMax()-C._valueMin())/5);
break;
case A.ui.keyCode.PAGE_DOWN:F=C._trimAlignValue(E-(C._valueMax()-C._valueMin())/5);
break;
case A.ui.keyCode.UP:case A.ui.keyCode.RIGHT:if(E===C._valueMax()){return 
}F=C._trimAlignValue(E+D);
break;
case A.ui.keyCode.DOWN:case A.ui.keyCode.LEFT:if(E===C._valueMin()){return 
}F=C._trimAlignValue(E-D);
break
}C._slide(I,G,F);
return H
}}).keyup(function(E){var D=A(this).data("index.ui-slider-handle");
if(C._keySliding){C._keySliding=false;
C._stop(E,D);
C._change(E,D);
A(this).removeClass("ui-state-active")
}});
this._refreshValue();
this._animateOff=false
},destroy:function(){this.handles.remove();
this.range.remove();
this.element.removeClass("ui-slider ui-slider-horizontal ui-slider-vertical ui-slider-disabled ui-widget ui-widget-content ui-corner-all").removeData("slider").unbind(".slider");
this._mouseDestroy();
return this
},_mouseCapture:function(C){var B=this.options,H,G,F,D,E;
if(B.disabled){return false
}this.elementSize={width:this.element.outerWidth(),height:this.element.outerHeight()};
this.elementOffset=this.element.offset();
H=this._normValueFromMouse({x:C.pageX,y:C.pageY});
G=this._valueMax()-this._valueMin()+1;
D=this;
this.handles.each(function(J){var I=Math.abs(H-D.values(J));
if(G>I){G=I;
F=A(this);
E=J
}});
if(B.range===true&&this.values(1)===B.min){E+=1;
F=A(this.handles[E])
}if(this._start(C,E)===false){return false
}this._mouseSliding=true;
D._handleIndex=E;
F.addClass("ui-state-active").focus();
B=F.offset();
this._clickOffset=!A(C.target).parents().andSelf().is(".ui-slider-handle")?{left:0,top:0}:{left:C.pageX-B.left-F.width()/2,top:C.pageY-B.top-F.height()/2-(parseInt(F.css("borderTopWidth"),10)||0)-(parseInt(F.css("borderBottomWidth"),10)||0)+(parseInt(F.css("marginTop"),10)||0)};
this._slide(C,E,H);
return this._animateOff=true
},_mouseStart:function(){return true
},_mouseDrag:function(C){var B=this._normValueFromMouse({x:C.pageX,y:C.pageY});
this._slide(C,this._handleIndex,B);
return false
},_mouseStop:function(B){this.handles.removeClass("ui-state-active");
this._mouseSliding=false;
this._stop(B,this._handleIndex);
this._change(B,this._handleIndex);
this._clickOffset=this._handleIndex=null;
return this._animateOff=false
},_detectOrientation:function(){this.orientation=this.options.orientation==="vertical"?"vertical":"horizontal"
},_normValueFromMouse:function(C){var B;
if(this.orientation==="horizontal"){B=this.elementSize.width;
C=C.x-this.elementOffset.left-(this._clickOffset?this._clickOffset.left:0)
}else{B=this.elementSize.height;
C=C.y-this.elementOffset.top-(this._clickOffset?this._clickOffset.top:0)
}B=C/B;
if(B>1){B=1
}if(B<0){B=0
}if(this.orientation==="vertical"){B=1-B
}C=this._valueMax()-this._valueMin();
return this._trimAlignValue(this._valueMin()+B*C)
},_start:function(C,B){var D={handle:this.handles[B],value:this.value()};
if(this.options.values&&this.options.values.length){D.value=this.values(B);
D.values=this.values()
}return this._trigger("start",C,D)
},_slide:function(C,B,E){var D;
if(this.options.values&&this.options.values.length){D=this.values(B?0:1);
if(this.options.values.length===2&&this.options.range===true&&(B===0&&E>D||B===1&&E<D)){E=D
}if(E!==this.values(B)){D=this.values();
D[B]=E;
C=this._trigger("slide",C,{handle:this.handles[B],value:E,values:D});
this.values(B?0:1);
C!==false&&this.values(B,E,true)
}}else{if(E!==this.value()){C=this._trigger("slide",C,{handle:this.handles[B],value:E});
C!==false&&this.value(E)
}}},_stop:function(C,B){var D={handle:this.handles[B],value:this.value()};
if(this.options.values&&this.options.values.length){D.value=this.values(B);
D.values=this.values()
}this._trigger("stop",C,D)
},_change:function(C,B){if(!this._keySliding&&!this._mouseSliding){var D={handle:this.handles[B],value:this.value()};
if(this.options.values&&this.options.values.length){D.value=this.values(B);
D.values=this.values()
}this._trigger("change",C,D)
}},value:function(B){if(arguments.length){this.options.value=this._trimAlignValue(B);
this._refreshValue();
this._change(null,0)
}return this._value()
},values:function(C,B){var F,E,D;
if(arguments.length>1){this.options.values[C]=this._trimAlignValue(B);
this._refreshValue();
this._change(null,C)
}if(arguments.length){if(A.isArray(arguments[0])){F=this.options.values;
E=arguments[0];
for(D=0;
D<F.length;
D+=1){F[D]=this._trimAlignValue(E[D]);
this._change(null,D)
}this._refreshValue()
}else{return this.options.values&&this.options.values.length?this._values(C):this.value()
}}else{return this._values()
}},_setOption:function(C,B){var E,D=0;
if(A.isArray(this.options.values)){D=this.options.values.length
}A.Widget.prototype._setOption.apply(this,arguments);
switch(C){case"disabled":if(B){this.handles.filter(".ui-state-focus").blur();
this.handles.removeClass("ui-state-hover");
this.handles.attr("disabled","disabled");
this.element.addClass("ui-disabled")
}else{this.handles.removeAttr("disabled");
this.element.removeClass("ui-disabled")
}break;
case"orientation":this._detectOrientation();
this.element.removeClass("ui-slider-horizontal ui-slider-vertical").addClass("ui-slider-"+this.orientation);
this._refreshValue();
break;
case"value":this._animateOff=true;
this._refreshValue();
this._change(null,0);
this._animateOff=false;
break;
case"values":this._animateOff=true;
this._refreshValue();
for(E=0;
E<D;
E+=1){this._change(null,E)
}this._animateOff=false;
break
}},_value:function(){var B=this.options.value;
return B=this._trimAlignValue(B)
},_values:function(C){var B,D;
if(arguments.length){B=this.options.values[C];
return B=this._trimAlignValue(B)
}else{B=this.options.values.slice();
for(D=0;
D<B.length;
D+=1){B[D]=this._trimAlignValue(B[D])
}return B
}},_trimAlignValue:function(C){if(C<this._valueMin()){return this._valueMin()
}if(C>this._valueMax()){return this._valueMax()
}var B=this.options.step>0?this.options.step:1,D=C%B;
C=C-D;
if(Math.abs(D)*2>=B){C+=D>0?B:-B
}return parseFloat(C.toFixed(5))
},_valueMin:function(){return this.options.min
},_valueMax:function(){return this.options.max
},_refreshValue:function(){var K=this.options.range,J=this.options,I=this,H=!this._animateOff?J.animate:false,G,E={},F,D,C,B;
if(this.options.values&&this.options.values.length){this.handles.each(function(L){G=(I.values(L)-I._valueMin())/(I._valueMax()-I._valueMin())*100;
E[I.orientation==="horizontal"?"left":"bottom"]=G+"%";
A(this).stop(1,1)[H?"animate":"css"](E,J.animate);
if(I.options.range===true){if(I.orientation==="horizontal"){if(L===0){I.range.stop(1,1)[H?"animate":"css"]({left:G+"%"},J.animate)
}if(L===1){I.range[H?"animate":"css"]({width:G-F+"%"},{queue:false,duration:J.animate})
}}else{if(L===0){I.range.stop(1,1)[H?"animate":"css"]({bottom:G+"%"},J.animate)
}if(L===1){I.range[H?"animate":"css"]({height:G-F+"%"},{queue:false,duration:J.animate})
}}}F=G
})
}else{D=this.value();
C=this._valueMin();
B=this._valueMax();
G=B!==C?(D-C)/(B-C)*100:0;
E[I.orientation==="horizontal"?"left":"bottom"]=G+"%";
this.handle.stop(1,1)[H?"animate":"css"](E,J.animate);
if(K==="min"&&this.orientation==="horizontal"){this.range.stop(1,1)[H?"animate":"css"]({width:G+"%"},J.animate)
}if(K==="max"&&this.orientation==="horizontal"){this.range[H?"animate":"css"]({width:100-G+"%"},{queue:false,duration:J.animate})
}if(K==="min"&&this.orientation==="vertical"){this.range.stop(1,1)[H?"animate":"css"]({height:G+"%"},J.animate)
}if(K==="max"&&this.orientation==="vertical"){this.range[H?"animate":"css"]({height:100-G+"%"},{queue:false,duration:J.animate})
}}}});
A.extend(A.ui.slider,{version:"1.8.6"})
})(jQuery);
(function(d,G){function K(){this.debug=false;
this._curInst=null;
this._keyEvent=false;
this._disabledInputs=[];
this._inDialog=this._datepickerShowing=false;
this._mainDivId="ui-datepicker-div";
this._inlineClass="ui-datepicker-inline";
this._appendClass="ui-datepicker-append";
this._triggerClass="ui-datepicker-trigger";
this._dialogClass="ui-datepicker-dialog";
this._disableClass="ui-datepicker-disabled";
this._unselectableClass="ui-datepicker-unselectable";
this._currentClass="ui-datepicker-current-day";
this._dayOverClass="ui-datepicker-days-cell-over";
this.regional=[];
this.regional[""]={closeText:"Done",prevText:"Prev",nextText:"Next",currentText:"Today",monthNames:["January","February","March","April","May","June","July","August","September","October","November","December"],monthNamesShort:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],dayNames:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],dayNamesShort:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],dayNamesMin:["Su","Mo","Tu","We","Th","Fr","Sa"],weekHeader:"Wk",dateFormat:"mm/dd/yy",firstDay:0,isRTL:false,showMonthAfterYear:false,yearSuffix:""};
this._defaults={showOn:"focus",showAnim:"fadeIn",showOptions:{},defaultDate:null,appendText:"",buttonText:"...",buttonImage:"",buttonImageOnly:false,hideIfNoPrevNext:false,navigationAsDateFormat:false,gotoCurrent:false,changeMonth:false,changeYear:false,yearRange:"c-75:c+0",showOtherMonths:false,selectOtherMonths:false,showWeek:false,calculateWeek:this.iso8601Week,shortYearCutoff:"+10",minDate:null,maxDate:null,duration:"fast",beforeShowDay:null,beforeShow:null,onSelect:null,onChangeMonthYear:null,onClose:null,numberOfMonths:1,showCurrentAtPos:0,stepMonths:1,stepBigMonths:12,altField:"",altFormat:"",constrainInput:true,showButtonPanel:false,autoSize:false};
d.extend(this._defaults,this.regional[""]);
this.dpDiv=d('<div id="'+this._mainDivId+'" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ui-helper-hidden-accessible"></div>')
}function E(a,b){d.extend(a,b);
for(var c in b){if(b[c]==null||b[c]==G){a[c]=b[c]
}}return a
}d.extend(d.ui,{datepicker:{version:"1.8.6"}});
var y=(new Date).getTime();
d.extend(K.prototype,{markerClassName:"hasDatepicker",log:function(){this.debug&&console.log.apply("",arguments)
},_widgetDatepicker:function(){return this.dpDiv
},setDefaults:function(a){E(this._defaults,a||{});
return this
},_attachDatepicker:function(a,b){var c=null;
for(var e in this._defaults){var f=a.getAttribute("date:"+e);
if(f){c=c||{};
try{c[e]=eval(f)
}catch(h){c[e]=f
}}}e=a.nodeName.toLowerCase();
f=e=="div"||e=="span";
if(!a.id){this.uuid+=1;
a.id="dp"+this.uuid
}var i=this._newInst(d(a),f);
i.settings=d.extend({},b||{},c||{});
if(e=="input"){this._connectDatepicker(a,i)
}else{f&&this._inlineDatepicker(a,i)
}},_newInst:function(a,b){return{id:a[0].id.replace(/([^A-Za-z0-9_-])/g,"\\\\$1"),input:a,selectedDay:0,selectedMonth:0,selectedYear:0,drawMonth:0,drawYear:0,inline:b,dpDiv:!b?this.dpDiv:d('<div class="'+this._inlineClass+' ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>')}
},_connectDatepicker:function(a,b){var c=d(a);
b.append=d([]);
b.trigger=d([]);
if(!c.hasClass(this.markerClassName)){this._attachments(c,b);
c.addClass(this.markerClassName).keydown(this._doKeyDown).keypress(this._doKeyPress).keyup(this._doKeyUp).bind("setData.datepicker",function(e,f,h){b.settings[f]=h
}).bind("getData.datepicker",function(e,f){return this._get(b,f)
});
this._autoSize(b);
d.data(a,"datepicker",b)
}},_attachments:function(a,b){var c=this._get(b,"appendText"),e=this._get(b,"isRTL");
b.append&&b.append.remove();
if(c){b.append=d('<span class="'+this._appendClass+'">'+c+"</span>");
a[e?"before":"after"](b.append)
}a.unbind("focus",this._showDatepicker);
b.trigger&&b.trigger.remove();
c=this._get(b,"showOn");
if(c=="focus"||c=="both"){a.focus(this._showDatepicker)
}if(c=="button"||c=="both"){c=this._get(b,"buttonText");
var f=this._get(b,"buttonImage");
b.trigger=d(this._get(b,"buttonImageOnly")?d("<img/>").addClass(this._triggerClass).attr({src:f,alt:c,title:c}):d('<button type="button"></button>').addClass(this._triggerClass).html(f==""?c:d("<img/>").attr({src:f,alt:c,title:c})));
a[e?"before":"after"](b.trigger);
b.trigger.click(function(){d.datepicker._datepickerShowing&&d.datepicker._lastInput==a[0]?d.datepicker._hideDatepicker():d.datepicker._showDatepicker(a[0]);
return false
})
}},_autoSize:function(a){if(this._get(a,"autoSize")&&!a.inline){var b=new Date(2009,11,20),c=this._get(a,"dateFormat");
if(c.match(/[DM]/)){var e=function(f){for(var h=0,i=0,g=0;
g<f.length;
g++){if(f[g].length>h){h=f[g].length;
i=g
}}return i
};
b.setMonth(e(this._get(a,c.match(/MM/)?"monthNames":"monthNamesShort")));
b.setDate(e(this._get(a,c.match(/DD/)?"dayNames":"dayNamesShort"))+20-b.getDay())
}a.input.attr("size",this._formatDate(a,b).length)
}},_inlineDatepicker:function(a,b){var c=d(a);
if(!c.hasClass(this.markerClassName)){c.addClass(this.markerClassName).append(b.dpDiv).bind("setData.datepicker",function(e,f,h){b.settings[f]=h
}).bind("getData.datepicker",function(e,f){return this._get(b,f)
});
d.data(a,"datepicker",b);
this._setDate(b,this._getDefaultDate(b),true);
this._updateDatepicker(b);
this._updateAlternate(b)
}},_dialogDatepicker:function(a,b,c,e,f){a=this._dialogInst;
if(!a){this.uuid+=1;
this._dialogInput=d('<input type="text" id="'+("dp"+this.uuid)+'" style="position: absolute; top: -100px; width: 0px; z-index: -10;"/>');
this._dialogInput.keydown(this._doKeyDown);
d("body").append(this._dialogInput);
a=this._dialogInst=this._newInst(this._dialogInput,false);
a.settings={};
d.data(this._dialogInput[0],"datepicker",a)
}E(a.settings,e||{});
b=b&&b.constructor==Date?this._formatDate(a,b):b;
this._dialogInput.val(b);
this._pos=f?f.length?f:[f.pageX,f.pageY]:null;
if(!this._pos){this._pos=[document.documentElement.clientWidth/2-100+(document.documentElement.scrollLeft||document.body.scrollLeft),document.documentElement.clientHeight/2-150+(document.documentElement.scrollTop||document.body.scrollTop)]
}this._dialogInput.css("left",this._pos[0]+20+"px").css("top",this._pos[1]+"px");
a.settings.onSelect=c;
this._inDialog=true;
this.dpDiv.addClass(this._dialogClass);
this._showDatepicker(this._dialogInput[0]);
d.blockUI&&d.blockUI(this.dpDiv);
d.data(this._dialogInput[0],"datepicker",a);
return this
},_destroyDatepicker:function(a){var b=d(a),c=d.data(a,"datepicker");
if(b.hasClass(this.markerClassName)){var e=a.nodeName.toLowerCase();
d.removeData(a,"datepicker");
if(e=="input"){c.append.remove();
c.trigger.remove();
b.removeClass(this.markerClassName).unbind("focus",this._showDatepicker).unbind("keydown",this._doKeyDown).unbind("keypress",this._doKeyPress).unbind("keyup",this._doKeyUp)
}else{if(e=="div"||e=="span"){b.removeClass(this.markerClassName).empty()
}}}},_enableDatepicker:function(a){var b=d(a),c=d.data(a,"datepicker");
if(b.hasClass(this.markerClassName)){var e=a.nodeName.toLowerCase();
if(e=="input"){a.disabled=false;
c.trigger.filter("button").each(function(){this.disabled=false
}).end().filter("img").css({opacity:"1.0",cursor:""})
}else{if(e=="div"||e=="span"){b.children("."+this._inlineClass).children().removeClass("ui-state-disabled")
}}this._disabledInputs=d.map(this._disabledInputs,function(f){return f==a?null:f
})
}},_disableDatepicker:function(a){var b=d(a),c=d.data(a,"datepicker");
if(b.hasClass(this.markerClassName)){var e=a.nodeName.toLowerCase();
if(e=="input"){a.disabled=true;
c.trigger.filter("button").each(function(){this.disabled=true
}).end().filter("img").css({opacity:"0.5",cursor:"default"})
}else{if(e=="div"||e=="span"){b.children("."+this._inlineClass).children().addClass("ui-state-disabled")
}}this._disabledInputs=d.map(this._disabledInputs,function(f){return f==a?null:f
});
this._disabledInputs[this._disabledInputs.length]=a
}},_isDisabledDatepicker:function(a){if(!a){return false
}for(var b=0;
b<this._disabledInputs.length;
b++){if(this._disabledInputs[b]==a){return true
}}return false
},_getInst:function(a){try{return d.data(a,"datepicker")
}catch(b){throw"Missing instance data for this datepicker"
}},_optionDatepicker:function(a,b,c){var e=this._getInst(a);
if(arguments.length==2&&typeof b=="string"){return b=="defaults"?d.extend({},d.datepicker._defaults):e?b=="all"?d.extend({},e.settings):this._get(e,b):null
}var f=b||{};
if(typeof b=="string"){f={};
f[b]=c
}if(e){this._curInst==e&&this._hideDatepicker();
var h=this._getDateDatepicker(a,true);
E(e.settings,f);
this._attachments(d(a),e);
this._autoSize(e);
this._setDateDatepicker(a,h);
this._updateDatepicker(e)
}},_changeDatepicker:function(a,b,c){this._optionDatepicker(a,b,c)
},_refreshDatepicker:function(a){(a=this._getInst(a))&&this._updateDatepicker(a)
},_setDateDatepicker:function(a,b){if(a=this._getInst(a)){this._setDate(a,b);
this._updateDatepicker(a);
this._updateAlternate(a)
}},_getDateDatepicker:function(a,b){(a=this._getInst(a))&&!a.inline&&this._setDateFromField(a,b);
return a?this._getDate(a):null
},_doKeyDown:function(a){var b=d.datepicker._getInst(a.target),c=true,e=b.dpDiv.is(".ui-datepicker-rtl");
b._keyEvent=true;
if(d.datepicker._datepickerShowing){switch(a.keyCode){case 9:d.datepicker._hideDatepicker();
c=false;
break;
case 13:c=d("td."+d.datepicker._dayOverClass,b.dpDiv).add(d("td."+d.datepicker._currentClass,b.dpDiv));
c[0]?d.datepicker._selectDay(a.target,b.selectedMonth,b.selectedYear,c[0]):d.datepicker._hideDatepicker();
return false;
case 27:d.datepicker._hideDatepicker();
break;
case 33:d.datepicker._adjustDate(a.target,a.ctrlKey?-d.datepicker._get(b,"stepBigMonths"):-d.datepicker._get(b,"stepMonths"),"M");
break;
case 34:d.datepicker._adjustDate(a.target,a.ctrlKey?+d.datepicker._get(b,"stepBigMonths"):+d.datepicker._get(b,"stepMonths"),"M");
break;
case 35:if(a.ctrlKey||a.metaKey){d.datepicker._clearDate(a.target)
}c=a.ctrlKey||a.metaKey;
break;
case 36:if(a.ctrlKey||a.metaKey){d.datepicker._gotoToday(a.target)
}c=a.ctrlKey||a.metaKey;
break;
case 37:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,e?+1:-1,"D")
}c=a.ctrlKey||a.metaKey;
if(a.originalEvent.altKey){d.datepicker._adjustDate(a.target,a.ctrlKey?-d.datepicker._get(b,"stepBigMonths"):-d.datepicker._get(b,"stepMonths"),"M")
}break;
case 38:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,-7,"D")
}c=a.ctrlKey||a.metaKey;
break;
case 39:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,e?-1:+1,"D")
}c=a.ctrlKey||a.metaKey;
if(a.originalEvent.altKey){d.datepicker._adjustDate(a.target,a.ctrlKey?+d.datepicker._get(b,"stepBigMonths"):+d.datepicker._get(b,"stepMonths"),"M")
}break;
case 40:if(a.ctrlKey||a.metaKey){d.datepicker._adjustDate(a.target,+7,"D")
}c=a.ctrlKey||a.metaKey;
break;
default:c=false
}}else{if(a.keyCode==36&&a.ctrlKey){d.datepicker._showDatepicker(this)
}else{c=false
}}if(c){a.preventDefault();
a.stopPropagation()
}},_doKeyPress:function(a){var b=d.datepicker._getInst(a.target);
if(d.datepicker._get(b,"constrainInput")){b=d.datepicker._possibleChars(d.datepicker._get(b,"dateFormat"));
var c=String.fromCharCode(a.charCode==G?a.keyCode:a.charCode);
return a.ctrlKey||c<" "||!b||b.indexOf(c)>-1
}},_doKeyUp:function(a){a=d.datepicker._getInst(a.target);
if(a.input.val()!=a.lastVal){try{if(d.datepicker.parseDate(d.datepicker._get(a,"dateFormat"),a.input?a.input.val():null,d.datepicker._getFormatConfig(a))){d.datepicker._setDateFromField(a);
d.datepicker._updateAlternate(a);
d.datepicker._updateDatepicker(a)
}}catch(b){d.datepicker.log(b)
}}return true
},_showDatepicker:function(a){a=a.target||a;
if(a.nodeName.toLowerCase()!="input"){a=d("input",a.parentNode)[0]
}if(!(d.datepicker._isDisabledDatepicker(a)||d.datepicker._lastInput==a)){var b=d.datepicker._getInst(a);
d.datepicker._curInst&&d.datepicker._curInst!=b&&d.datepicker._curInst.dpDiv.stop(true,true);
var c=d.datepicker._get(b,"beforeShow");
E(b.settings,c?c.apply(a,[a,b]):{});
b.lastVal=null;
d.datepicker._lastInput=a;
d.datepicker._setDateFromField(b);
if(d.datepicker._inDialog){a.value=""
}if(!d.datepicker._pos){d.datepicker._pos=d.datepicker._findPos(a);
d.datepicker._pos[1]+=a.offsetHeight
}var e=false;
d(a).parents().each(function(){e|=d(this).css("position")=="fixed";
return !e
});
if(e&&d.browser.opera){d.datepicker._pos[0]-=document.documentElement.scrollLeft;
d.datepicker._pos[1]-=document.documentElement.scrollTop
}c={left:d.datepicker._pos[0],top:d.datepicker._pos[1]};
d.datepicker._pos=null;
b.dpDiv.css({position:"absolute",display:"block",top:"-1000px"});
d.datepicker._updateDatepicker(b);
c=d.datepicker._checkOffset(b,c,e);
b.dpDiv.css({position:d.datepicker._inDialog&&d.blockUI?"static":e?"fixed":"absolute",display:"none",left:c.left+"px",top:c.top+"px"});
if(!b.inline){c=d.datepicker._get(b,"showAnim");
var f=d.datepicker._get(b,"duration"),h=function(){d.datepicker._datepickerShowing=true;
var i=d.datepicker._getBorders(b.dpDiv);
b.dpDiv.find("iframe.ui-datepicker-cover").css({left:-i[0],top:-i[1],width:b.dpDiv.outerWidth(),height:b.dpDiv.outerHeight()})
};
b.dpDiv.zIndex(d(a).zIndex()+1);
d.effects&&d.effects[c]?b.dpDiv.show(c,d.datepicker._get(b,"showOptions"),f,h):b.dpDiv[c||"show"](c?f:null,h);
if(!c||!f){h()
}b.input.is(":visible")&&!b.input.is(":disabled")&&b.input.focus();
d.datepicker._curInst=b
}}},_updateDatepicker:function(a){var b=this,c=d.datepicker._getBorders(a.dpDiv);
a.dpDiv.empty().append(this._generateHTML(a)).find("iframe.ui-datepicker-cover").css({left:-c[0],top:-c[1],width:a.dpDiv.outerWidth(),height:a.dpDiv.outerHeight()}).end().find("button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a").bind("mouseout",function(){d(this).removeClass("ui-state-hover");
this.className.indexOf("ui-datepicker-prev")!=-1&&d(this).removeClass("ui-datepicker-prev-hover");
this.className.indexOf("ui-datepicker-next")!=-1&&d(this).removeClass("ui-datepicker-next-hover")
}).bind("mouseover",function(){if(!b._isDisabledDatepicker(a.inline?a.dpDiv.parent()[0]:a.input[0])){d(this).parents(".ui-datepicker-calendar").find("a").removeClass("ui-state-hover");
d(this).addClass("ui-state-hover");
this.className.indexOf("ui-datepicker-prev")!=-1&&d(this).addClass("ui-datepicker-prev-hover");
this.className.indexOf("ui-datepicker-next")!=-1&&d(this).addClass("ui-datepicker-next-hover")
}}).end().find("."+this._dayOverClass+" a").trigger("mouseover").end();
c=this._getNumberOfMonths(a);
var e=c[1];
e>1?a.dpDiv.addClass("ui-datepicker-multi-"+e).css("width",17*e+"em"):a.dpDiv.removeClass("ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4").width("");
a.dpDiv[(c[0]!=1||c[1]!=1?"add":"remove")+"Class"]("ui-datepicker-multi");
a.dpDiv[(this._get(a,"isRTL")?"add":"remove")+"Class"]("ui-datepicker-rtl");
a==d.datepicker._curInst&&d.datepicker._datepickerShowing&&a.input&&a.input.is(":visible")&&!a.input.is(":disabled")&&a.input.focus()
},_getBorders:function(a){var b=function(c){return{thin:1,medium:2,thick:3}[c]||c
};
return[parseFloat(b(a.css("border-left-width"))),parseFloat(b(a.css("border-top-width")))]
},_checkOffset:function(a,b,c){var e=a.dpDiv.outerWidth(),f=a.dpDiv.outerHeight(),h=a.input?a.input.outerWidth():0,i=a.input?a.input.outerHeight():0,g=document.documentElement.clientWidth+d(document).scrollLeft(),k=document.documentElement.clientHeight+d(document).scrollTop();
b.left-=this._get(a,"isRTL")?e-h:0;
b.left-=c&&b.left==a.input.offset().left?d(document).scrollLeft():0;
b.top-=c&&b.top==a.input.offset().top+i?d(document).scrollTop():0;
b.left-=Math.min(b.left,b.left+e>g&&g>e?Math.abs(b.left+e-g):0);
b.top-=Math.min(b.top,b.top+f>k&&k>f?Math.abs(f+i):0);
return b
},_findPos:function(a){for(var b=this._get(this._getInst(a),"isRTL");
a&&(a.type=="hidden"||a.nodeType!=1);
){a=a[b?"previousSibling":"nextSibling"]
}a=d(a).offset();
return[a.left,a.top]
},_hideDatepicker:function(a){var b=this._curInst;
if(!(!b||a&&b!=d.data(a,"datepicker"))){if(this._datepickerShowing){a=this._get(b,"showAnim");
var c=this._get(b,"duration"),e=function(){d.datepicker._tidyDialog(b);
this._curInst=null
};
d.effects&&d.effects[a]?b.dpDiv.hide(a,d.datepicker._get(b,"showOptions"),c,e):b.dpDiv[a=="slideDown"?"slideUp":a=="fadeIn"?"fadeOut":"hide"](a?c:null,e);
a||e();
if(a=this._get(b,"onClose")){a.apply(b.input?b.input[0]:null,[b.input?b.input.val():"",b])
}this._datepickerShowing=false;
this._lastInput=null;
if(this._inDialog){this._dialogInput.css({position:"absolute",left:"0",top:"-100px"});
if(d.blockUI){d.unblockUI();
d("body").append(this.dpDiv)
}}this._inDialog=false
}}},_tidyDialog:function(a){a.dpDiv.removeClass(this._dialogClass).unbind(".ui-datepicker-calendar")
},_checkExternalClick:function(a){if(d.datepicker._curInst){a=d(a.target);
a[0].id!=d.datepicker._mainDivId&&a.parents("#"+d.datepicker._mainDivId).length==0&&!a.hasClass(d.datepicker.markerClassName)&&!a.hasClass(d.datepicker._triggerClass)&&d.datepicker._datepickerShowing&&!(d.datepicker._inDialog&&d.blockUI)&&d.datepicker._hideDatepicker()
}},_adjustDate:function(a,b,c){a=d(a);
var e=this._getInst(a[0]);
if(!this._isDisabledDatepicker(a[0])){this._adjustInstDate(e,b+(c=="M"?this._get(e,"showCurrentAtPos"):0),c);
this._updateDatepicker(e)
}},_gotoToday:function(a){a=d(a);
var b=this._getInst(a[0]);
if(this._get(b,"gotoCurrent")&&b.currentDay){b.selectedDay=b.currentDay;
b.drawMonth=b.selectedMonth=b.currentMonth;
b.drawYear=b.selectedYear=b.currentYear
}else{var c=new Date;
b.selectedDay=c.getDate();
b.drawMonth=b.selectedMonth=c.getMonth();
b.drawYear=b.selectedYear=c.getFullYear()
}this._notifyChange(b);
this._adjustDate(a)
},_selectMonthYear:function(a,b,c){a=d(a);
var e=this._getInst(a[0]);
e._selectingMonthYear=false;
e["selected"+(c=="M"?"Month":"Year")]=e["draw"+(c=="M"?"Month":"Year")]=parseInt(b.options[b.selectedIndex].value,10);
this._notifyChange(e);
this._adjustDate(a)
},_clickMonthYear:function(a){var b=this._getInst(d(a)[0]);
b.input&&b._selectingMonthYear&&setTimeout(function(){b.input.focus()
},0);
b._selectingMonthYear=!b._selectingMonthYear
},_selectDay:function(a,b,c,e){var f=d(a);
if(!(d(e).hasClass(this._unselectableClass)||this._isDisabledDatepicker(f[0]))){f=this._getInst(f[0]);
f.selectedDay=f.currentDay=d("a",e).html();
f.selectedMonth=f.currentMonth=b;
f.selectedYear=f.currentYear=c;
this._selectDate(a,this._formatDate(f,f.currentDay,f.currentMonth,f.currentYear))
}},_clearDate:function(a){a=d(a);
this._getInst(a[0]);
this._selectDate(a,"")
},_selectDate:function(a,b){a=this._getInst(d(a)[0]);
b=b!=null?b:this._formatDate(a);
a.input&&a.input.val(b);
this._updateAlternate(a);
var c=this._get(a,"onSelect");
if(c){c.apply(a.input?a.input[0]:null,[b,a])
}else{a.input&&a.input.trigger("change")
}if(a.inline){this._updateDatepicker(a)
}else{this._hideDatepicker();
this._lastInput=a.input[0];
typeof a.input[0]!="object"&&a.input.focus();
this._lastInput=null
}},_updateAlternate:function(a){var b=this._get(a,"altField");
if(b){var c=this._get(a,"altFormat")||this._get(a,"dateFormat"),e=this._getDate(a),f=this.formatDate(c,e,this._getFormatConfig(a));
d(b).each(function(){d(this).val(f)
})
}},noWeekends:function(a){a=a.getDay();
return[a>0&&a<6,""]
},iso8601Week:function(a){a=new Date(a.getTime());
a.setDate(a.getDate()+4-(a.getDay()||7));
var b=a.getTime();
a.setMonth(0);
a.setDate(1);
return Math.floor(Math.round((b-a)/86400000)/7)+1
},parseDate:function(a,b,c){if(a==null||b==null){throw"Invalid arguments"
}b=typeof b=="object"?b.toString():b+"";
if(b==""){return null
}for(var e=(c?c.shortYearCutoff:null)||this._defaults.shortYearCutoff,f=(c?c.dayNamesShort:null)||this._defaults.dayNamesShort,h=(c?c.dayNames:null)||this._defaults.dayNames,i=(c?c.monthNamesShort:null)||this._defaults.monthNamesShort,g=(c?c.monthNames:null)||this._defaults.monthNames,k=c=-1,l=-1,u=-1,j=false,o=function(p){(p=z+1<a.length&&a.charAt(z+1)==p)&&z++;
return p
},m=function(p){o(p);
p=new RegExp("^\\d{1,"+(p=="@"?14:p=="!"?20:p=="y"?4:p=="o"?3:2)+"}");
p=b.substring(s).match(p);
if(!p){throw"Missing number at position "+s
}s+=p[0].length;
return parseInt(p[0],10)
},n=function(p,w,H){p=o(p)?H:w;
for(w=0;
w<p.length;
w++){if(b.substr(s,p[w].length).toLowerCase()==p[w].toLowerCase()){s+=p[w].length;
return w+1
}}throw"Unknown name at position "+s
},r=function(){if(b.charAt(s)!=a.charAt(z)){throw"Unexpected literal at position "+s
}s++
},s=0,z=0;
z<a.length;
z++){if(j){if(a.charAt(z)=="'"&&!o("'")){j=false
}else{r()
}}else{switch(a.charAt(z)){case"d":l=m("d");
break;
case"D":n("D",f,h);
break;
case"o":u=m("o");
break;
case"m":k=m("m");
break;
case"M":k=n("M",i,g);
break;
case"y":c=m("y");
break;
case"@":var v=new Date(m("@"));
c=v.getFullYear();
k=v.getMonth()+1;
l=v.getDate();
break;
case"!":v=new Date((m("!")-this._ticksTo1970)/10000);
c=v.getFullYear();
k=v.getMonth()+1;
l=v.getDate();
break;
case"'":if(o("'")){r()
}else{j=true
}break;
default:r()
}}}if(c==-1){c=(new Date).getFullYear()
}else{if(c<100){c+=(new Date).getFullYear()-(new Date).getFullYear()%100+(c<=e?0:-100)
}}if(u>-1){k=1;
l=u;
do{e=this._getDaysInMonth(c,k-1);
if(l<=e){break
}k++;
l-=e
}while(1)
}v=this._daylightSavingAdjust(new Date(c,k-1,l));
if(v.getFullYear()!=c||v.getMonth()+1!=k||v.getDate()!=l){throw"Invalid date"
}return v
},ATOM:"yy-mm-dd",COOKIE:"D, dd M yy",ISO_8601:"yy-mm-dd",RFC_822:"D, d M y",RFC_850:"DD, dd-M-y",RFC_1036:"D, d M y",RFC_1123:"D, d M yy",RFC_2822:"D, d M yy",RSS:"D, d M y",TICKS:"!",TIMESTAMP:"@",W3C:"yy-mm-dd",_ticksTo1970:(718685+Math.floor(492.5)-Math.floor(19.7)+Math.floor(4.925))*24*60*60*10000000,formatDate:function(a,b,c){if(!b){return""
}var e=(c?c.dayNamesShort:null)||this._defaults.dayNamesShort,f=(c?c.dayNames:null)||this._defaults.dayNames,h=(c?c.monthNamesShort:null)||this._defaults.monthNamesShort;
c=(c?c.monthNames:null)||this._defaults.monthNames;
var i=function(o){(o=j+1<a.length&&a.charAt(j+1)==o)&&j++;
return o
},g=function(o,m,n){m=""+m;
if(i(o)){for(;
m.length<n;
){m="0"+m
}}return m
},k=function(o,m,n,r){return i(o)?r[m]:n[m]
},l="",u=false;
if(b){for(var j=0;
j<a.length;
j++){if(u){if(a.charAt(j)=="'"&&!i("'")){u=false
}else{l+=a.charAt(j)
}}else{switch(a.charAt(j)){case"d":l+=g("d",b.getDate(),2);
break;
case"D":l+=k("D",b.getDay(),e,f);
break;
case"o":l+=g("o",(b.getTime()-(new Date(b.getFullYear(),0,0)).getTime())/86400000,3);
break;
case"m":l+=g("m",b.getMonth()+1,2);
break;
case"M":l+=k("M",b.getMonth(),h,c);
break;
case"y":l+=i("y")?b.getFullYear():(b.getYear()%100<10?"0":"")+b.getYear()%100;
break;
case"@":l+=b.getTime();
break;
case"!":l+=b.getTime()*10000+this._ticksTo1970;
break;
case"'":if(i("'")){l+="'"
}else{u=true
}break;
default:l+=a.charAt(j)
}}}}return l
},_possibleChars:function(a){for(var b="",c=false,e=function(h){(h=f+1<a.length&&a.charAt(f+1)==h)&&f++;
return h
},f=0;
f<a.length;
f++){if(c){if(a.charAt(f)=="'"&&!e("'")){c=false
}else{b+=a.charAt(f)
}}else{switch(a.charAt(f)){case"d":case"m":case"y":case"@":b+="0123456789";
break;
case"D":case"M":return null;
case"'":if(e("'")){b+="'"
}else{c=true
}break;
default:b+=a.charAt(f)
}}}return b
},_get:function(a,b){return a.settings[b]!==G?a.settings[b]:this._defaults[b]
},_setDateFromField:function(a,b){if(a.input.val()!=a.lastVal){var c=this._get(a,"dateFormat"),e=a.lastVal=a.input?a.input.val():null,f,h;
f=h=this._getDefaultDate(a);
var i=this._getFormatConfig(a);
try{f=this.parseDate(c,e,i)||h
}catch(g){this.log(g);
e=b?"":e
}a.selectedDay=f.getDate();
a.drawMonth=a.selectedMonth=f.getMonth();
a.drawYear=a.selectedYear=f.getFullYear();
a.currentDay=e?f.getDate():0;
a.currentMonth=e?f.getMonth():0;
a.currentYear=e?f.getFullYear():0;
this._adjustInstDate(a)
}},_getDefaultDate:function(a){return this._restrictMinMax(a,this._determineDate(a,this._get(a,"defaultDate"),new Date))
},_determineDate:function(a,b,c){var e=function(h){var i=new Date;
i.setDate(i.getDate()+h);
return i
},f=function(h){try{return d.datepicker.parseDate(d.datepicker._get(a,"dateFormat"),h,d.datepicker._getFormatConfig(a))
}catch(i){}var g=(h.toLowerCase().match(/^c/)?d.datepicker._getDate(a):null)||new Date,k=g.getFullYear(),l=g.getMonth();
g=g.getDate();
for(var u=/([+-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,j=u.exec(h);
j;
){switch(j[2]||"d"){case"d":case"D":g+=parseInt(j[1],10);
break;
case"w":case"W":g+=parseInt(j[1],10)*7;
break;
case"m":case"M":l+=parseInt(j[1],10);
g=Math.min(g,d.datepicker._getDaysInMonth(k,l));
break;
case"y":case"Y":k+=parseInt(j[1],10);
g=Math.min(g,d.datepicker._getDaysInMonth(k,l));
break
}j=u.exec(h)
}return new Date(k,l,g)
};
if(b=(b=b==null?c:typeof b=="string"?f(b):typeof b=="number"?isNaN(b)?c:e(b):b)&&b.toString()=="Invalid Date"?c:b){b.setHours(0);
b.setMinutes(0);
b.setSeconds(0);
b.setMilliseconds(0)
}return this._daylightSavingAdjust(b)
},_daylightSavingAdjust:function(a){if(!a){return null
}a.setHours(a.getHours()>12?a.getHours()+2:0);
return a
},_setDate:function(a,b,c){var e=!b,f=a.selectedMonth,h=a.selectedYear;
b=this._restrictMinMax(a,this._determineDate(a,b,new Date));
a.selectedDay=a.currentDay=b.getDate();
a.drawMonth=a.selectedMonth=a.currentMonth=b.getMonth();
a.drawYear=a.selectedYear=a.currentYear=b.getFullYear();
if((f!=a.selectedMonth||h!=a.selectedYear)&&!c){this._notifyChange(a)
}this._adjustInstDate(a);
if(a.input){a.input.val(e?"":this._formatDate(a))
}},_getDate:function(a){return !a.currentYear||a.input&&a.input.val()==""?null:this._daylightSavingAdjust(new Date(a.currentYear,a.currentMonth,a.currentDay))
},_generateHTML:function(a){var b=new Date;
b=this._daylightSavingAdjust(new Date(b.getFullYear(),b.getMonth(),b.getDate()));
var c=this._get(a,"isRTL"),e=this._get(a,"showButtonPanel"),f=this._get(a,"hideIfNoPrevNext"),h=this._get(a,"navigationAsDateFormat"),i=this._getNumberOfMonths(a),g=this._get(a,"showCurrentAtPos"),k=this._get(a,"stepMonths"),l=i[0]!=1||i[1]!=1,u=this._daylightSavingAdjust(!a.currentDay?new Date(9999,9,9):new Date(a.currentYear,a.currentMonth,a.currentDay)),j=this._getMinMaxDate(a,"min"),o=this._getMinMaxDate(a,"max");
g=a.drawMonth-g;
var m=a.drawYear;
if(g<0){g+=12;
m--
}if(o){var n=this._daylightSavingAdjust(new Date(o.getFullYear(),o.getMonth()-i[0]*i[1]+1,o.getDate()));
for(n=j&&n<j?j:n;
this._daylightSavingAdjust(new Date(m,g,1))>n;
){g--;
if(g<0){g=11;
m--
}}}a.drawMonth=g;
a.drawYear=m;
n=this._get(a,"prevText");
n=!h?n:this.formatDate(n,this._daylightSavingAdjust(new Date(m,g-k,1)),this._getFormatConfig(a));
n=this._canAdjustMonth(a,-1,m,g)?'<a class="ui-datepicker-prev ui-corner-all" onclick="DP_jQuery_'+y+".datepicker._adjustDate('#"+a.id+"', -"+k+", 'M');\" title=\""+n+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"e":"w")+'">'+n+"</span></a>":f?"":'<a class="ui-datepicker-prev ui-corner-all ui-state-disabled" title="'+n+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"e":"w")+'">'+n+"</span></a>";
var r=this._get(a,"nextText");
r=!h?r:this.formatDate(r,this._daylightSavingAdjust(new Date(m,g+k,1)),this._getFormatConfig(a));
f=this._canAdjustMonth(a,+1,m,g)?'<a class="ui-datepicker-next ui-corner-all" onclick="DP_jQuery_'+y+".datepicker._adjustDate('#"+a.id+"', +"+k+", 'M');\" title=\""+r+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"w":"e")+'">'+r+"</span></a>":f?"":'<a class="ui-datepicker-next ui-corner-all ui-state-disabled" title="'+r+'"><span class="ui-icon ui-icon-circle-triangle-'+(c?"w":"e")+'">'+r+"</span></a>";
k=this._get(a,"currentText");
r=this._get(a,"gotoCurrent")&&a.currentDay?u:b;
k=!h?k:this.formatDate(k,r,this._getFormatConfig(a));
h=!a.inline?'<button type="button" class="ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all" onclick="DP_jQuery_'+y+'.datepicker._hideDatepicker();">'+this._get(a,"closeText")+"</button>":"";
e=e?'<div class="ui-datepicker-buttonpane ui-widget-content">'+(c?h:"")+(this._isInRange(a,r)?'<button type="button" class="ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all" onclick="DP_jQuery_'+y+".datepicker._gotoToday('#"+a.id+"');\">"+k+"</button>":"")+(c?"":h)+"</div>":"";
h=parseInt(this._get(a,"firstDay"),10);
h=isNaN(h)?0:h;
k=this._get(a,"showWeek");
r=this._get(a,"dayNames");
this._get(a,"dayNamesShort");
var s=this._get(a,"dayNamesMin"),z=this._get(a,"monthNames"),v=this._get(a,"monthNamesShort"),p=this._get(a,"beforeShowDay"),w=this._get(a,"showOtherMonths"),H=this._get(a,"selectOtherMonths");
this._get(a,"calculateWeek");
for(var L=this._getDefaultDate(a),I="",C=0;
C<i[0];
C++){for(var M="",D=0;
D<i[1];
D++){var N=this._daylightSavingAdjust(new Date(m,g,a.selectedDay)),t=" ui-corner-all",x="";
if(l){x+='<div class="ui-datepicker-group';
if(i[1]>1){switch(D){case 0:x+=" ui-datepicker-group-first";
t=" ui-corner-"+(c?"right":"left");
break;
case i[1]-1:x+=" ui-datepicker-group-last";
t=" ui-corner-"+(c?"left":"right");
break;
default:x+=" ui-datepicker-group-middle";
t="";
break
}}x+='">'
}x+='<div class="ui-datepicker-header ui-widget-header ui-helper-clearfix'+t+'">'+(/all|left/.test(t)&&C==0?c?f:n:"")+(/all|right/.test(t)&&C==0?c?n:f:"")+this._generateMonthYearHeader(a,g,m,j,o,C>0||D>0,z,v)+'</div><table class="ui-datepicker-calendar"><thead><tr>';
var A=k?'<th class="ui-datepicker-week-col">'+this._get(a,"weekHeader")+"</th>":"";
for(t=0;
t<7;
t++){var q=(t+h)%7;
A+="<th"+((t+h+6)%7>=5?' class="ui-datepicker-week-end"':"")+'><span title="'+r[q]+'">'+s[q]+"</span></th>"
}x+=A+"</tr></thead><tbody>";
A=this._getDaysInMonth(m,g);
if(m==a.selectedYear&&g==a.selectedMonth){a.selectedDay=Math.min(a.selectedDay,A)
}t=(this._getFirstDayOfMonth(m,g)-h+7)%7;
A=l?6:Math.ceil((t+A)/7);
q=this._daylightSavingAdjust(new Date(m,g,1-t));
for(var O=0;
O<A;
O++){x+="<tr>";
var P=!k?"":'<td class="ui-datepicker-week-col">'+this._get(a,"calculateWeek")(q)+"</td>";
for(t=0;
t<7;
t++){var F=p?p.apply(a.input?a.input[0]:null,[q]):[true,""],B=q.getMonth()!=g,J=B&&!H||!F[0]||j&&q<j||o&&q>o;
P+='<td class="'+((t+h+6)%7>=5?" ui-datepicker-week-end":"")+(B?" ui-datepicker-other-month":"")+(q.getTime()==N.getTime()&&g==a.selectedMonth&&a._keyEvent||L.getTime()==q.getTime()&&L.getTime()==N.getTime()?" "+this._dayOverClass:"")+(J?" "+this._unselectableClass+" ui-state-disabled":"")+(B&&!w?"":" "+F[1]+(q.getTime()==u.getTime()?" "+this._currentClass:"")+(q.getTime()==b.getTime()?" ui-datepicker-today":""))+'"'+((!B||w)&&F[2]?' title="'+F[2]+'"':"")+(J?"":' onclick="DP_jQuery_'+y+".datepicker._selectDay('#"+a.id+"',"+q.getMonth()+","+q.getFullYear()+', this);return false;"')+">"+(B&&!w?"&#xa0;":J?'<span class="ui-state-default">'+q.getDate()+"</span>":'<a class="ui-state-default'+(q.getTime()==b.getTime()?" ui-state-highlight":"")+(q.getTime()==u.getTime()?" ui-state-active":"")+(B?" ui-priority-secondary":"")+'" href="#">'+q.getDate()+"</a>")+"</td>";
q.setDate(q.getDate()+1);
q=this._daylightSavingAdjust(q)
}x+=P+"</tr>"
}g++;
if(g>11){g=0;
m++
}x+="</tbody></table>"+(l?"</div>"+(i[0]>0&&D==i[1]-1?'<div class="ui-datepicker-row-break"></div>':""):"");
M+=x
}I+=M
}I+=e+(d.browser.msie&&parseInt(d.browser.version,10)<7&&!a.inline?'<iframe src="javascript:false;" class="ui-datepicker-cover" frameborder="0"></iframe>':"");
a._keyEvent=false;
return I
},_generateMonthYearHeader:function(a,b,c,e,f,h,i,g){var k=this._get(a,"changeMonth"),l=this._get(a,"changeYear"),u=this._get(a,"showMonthAfterYear"),j='<div class="ui-datepicker-title">',o="";
if(h||!k){o+='<span class="ui-datepicker-month">'+i[b]+"</span>"
}else{i=e&&e.getFullYear()==c;
var m=f&&f.getFullYear()==c;
o+='<select class="ui-datepicker-month" onchange="DP_jQuery_'+y+".datepicker._selectMonthYear('#"+a.id+"', this, 'M');\" onclick=\"DP_jQuery_"+y+".datepicker._clickMonthYear('#"+a.id+"');\">";
for(var n=0;
n<12;
n++){if((!i||n>=e.getMonth())&&(!m||n<=f.getMonth())){o+='<option value="'+n+'"'+(n==b?' selected="selected"':"")+">"+g[n]+"</option>"
}}o+="</select>"
}u||(j+=o+(h||!(k&&l)?"&#xa0;":""));
if(h||!l){j+='<span class="ui-datepicker-year">'+c+"</span>"
}else{g=this._get(a,"yearRange").split(":");
var r=(new Date).getFullYear();
i=function(s){s=s.match(/c[+-].*/)?c+parseInt(s.substring(1),10):s.match(/[+-].*/)?r+parseInt(s,10):parseInt(s,10);
return isNaN(s)?r:s
};
b=i(g[0]);
g=Math.max(b,i(g[1]||""));
b=e?Math.max(b,e.getFullYear()):b;
g=f?Math.min(g,f.getFullYear()):g;
for(j+='<select class="ui-datepicker-year" onchange="DP_jQuery_'+y+".datepicker._selectMonthYear('#"+a.id+"', this, 'Y');\" onclick=\"DP_jQuery_"+y+".datepicker._clickMonthYear('#"+a.id+"');\">";
b<=g;
b++){j+='<option value="'+b+'"'+(b==c?' selected="selected"':"")+">"+b+"</option>"
}j+="</select>"
}j+=this._get(a,"yearSuffix");
if(u){j+=(h||!(k&&l)?"&#xa0;":"")+o
}j+="</div>";
return j
},_adjustInstDate:function(a,b,c){var e=a.drawYear+(c=="Y"?b:0),f=a.drawMonth+(c=="M"?b:0);
b=Math.min(a.selectedDay,this._getDaysInMonth(e,f))+(c=="D"?b:0);
e=this._restrictMinMax(a,this._daylightSavingAdjust(new Date(e,f,b)));
a.selectedDay=e.getDate();
a.drawMonth=a.selectedMonth=e.getMonth();
a.drawYear=a.selectedYear=e.getFullYear();
if(c=="M"||c=="Y"){this._notifyChange(a)
}},_restrictMinMax:function(a,b){var c=this._getMinMaxDate(a,"min");
a=this._getMinMaxDate(a,"max");
b=c&&b<c?c:b;
return b=a&&b>a?a:b
},_notifyChange:function(a){var b=this._get(a,"onChangeMonthYear");
if(b){b.apply(a.input?a.input[0]:null,[a.selectedYear,a.selectedMonth+1,a])
}},_getNumberOfMonths:function(a){a=this._get(a,"numberOfMonths");
return a==null?[1,1]:typeof a=="number"?[1,a]:a
},_getMinMaxDate:function(a,b){return this._determineDate(a,this._get(a,b+"Date"),null)
},_getDaysInMonth:function(a,b){return 32-(new Date(a,b,32)).getDate()
},_getFirstDayOfMonth:function(a,b){return(new Date(a,b,1)).getDay()
},_canAdjustMonth:function(a,b,c,e){var f=this._getNumberOfMonths(a);
c=this._daylightSavingAdjust(new Date(c,e+(b<0?b:f[0]*f[1]),1));
b<0&&c.setDate(this._getDaysInMonth(c.getFullYear(),c.getMonth()));
return this._isInRange(a,c)
},_isInRange:function(a,b){var c=this._getMinMaxDate(a,"min");
a=this._getMinMaxDate(a,"max");
return(!c||b.getTime()>=c.getTime())&&(!a||b.getTime()<=a.getTime())
},_getFormatConfig:function(a){var b=this._get(a,"shortYearCutoff");
b=typeof b!="string"?b:(new Date).getFullYear()%100+parseInt(b,10);
return{shortYearCutoff:b,dayNamesShort:this._get(a,"dayNamesShort"),dayNames:this._get(a,"dayNames"),monthNamesShort:this._get(a,"monthNamesShort"),monthNames:this._get(a,"monthNames")}
},_formatDate:function(a,b,c,e){if(!b){a.currentDay=a.selectedDay;
a.currentMonth=a.selectedMonth;
a.currentYear=a.selectedYear
}b=b?typeof b=="object"?b:this._daylightSavingAdjust(new Date(e,c,b)):this._daylightSavingAdjust(new Date(a.currentYear,a.currentMonth,a.currentDay));
return this.formatDate(this._get(a,"dateFormat"),b,this._getFormatConfig(a))
}});
d.fn.datepicker=function(a){if(!d.datepicker.initialized){d(document).mousedown(d.datepicker._checkExternalClick).find("body").append(d.datepicker.dpDiv);
d.datepicker.initialized=true
}var b=Array.prototype.slice.call(arguments,1);
if(typeof a=="string"&&(a=="isDisabled"||a=="getDate"||a=="widget")){return d.datepicker["_"+a+"Datepicker"].apply(d.datepicker,[this[0]].concat(b))
}if(a=="option"&&arguments.length==2&&typeof arguments[1]=="string"){return d.datepicker["_"+a+"Datepicker"].apply(d.datepicker,[this[0]].concat(b))
}return this.each(function(){typeof a=="string"?d.datepicker["_"+a+"Datepicker"].apply(d.datepicker,[this].concat(b)):d.datepicker._attachDatepicker(this,a)
})
};
d.datepicker=new K;
d.datepicker.initialized=false;
d.datepicker.uuid=(new Date).getTime();
d.datepicker.version="1.8.6";
window["DP_jQuery_"+y]=d
})(jQuery);
