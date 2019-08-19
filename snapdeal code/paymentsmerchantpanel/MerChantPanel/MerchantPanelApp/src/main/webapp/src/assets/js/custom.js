// JavaScript Document
$.material.init();
$(document).ready(function () {

    //header start
    $('body').on('click', '.navigation , .nav-bg', function (e) {
        $(".side-nav").toggleClass("left");
        $(".nav-bg").fadeToggle();
        $(".side-nav .menu-area > ul > li > ul").siblings("a").addClass("nav-arrow")

    });

    //$('body').on('click', '.side-nav ul li a', function (e) {
    //    $(".side-nav").removeClass("left");
    //    $(".nav-bg").fadeOut();
    //});


    $('body').on('click', '.profile-detail button', function (e) {
        $(".login-main-box").slideUp();
        $(".login-bg").fadeOut();
    });

    $('body').on('click', '.userlogin , .login-bg', function (e) {
        $(".login-main-box").slideToggle();
        $(".login-bg").fadeToggle();
    });


    $('body').on('click', '.header-search-icon', function (e) {
        $(".search-box").slideToggle();
    });

    $('body').on('click', '.search', function (e) {
        $(".search-box").addClass("search-hover");
    });

    $('body').on('click', '.search', function (e) {
        $(".search-box").removeClass("search-hover");
    });

    // header end
    $('body').on('click', '.adduser', function (e) {
        $(".add-user").addClass("show");
        $(".adduser-bg").fadeIn();
    });

/*    $('body').on('click', '.export-xl', function (e) {
        $(".export-xl-popup , .lightbox-bg").fadeIn();
    });*/

    $('body').on('click', '#refund-upload', function (e) {
        $(".upload-tab-cont,.refund-bg").fadeIn();
    });

    $('body').on('click', '#bluck-refund', function (e) {
        $(".bulk-refund-cont,.refund-bg").fadeIn();
    });

    $('body').on('click', '.refund-bg', function (e) {
        $(".bulk-refund-cont,.upload-tab-cont,.refund-bg").fadeOut();
    });

    $('body').on('click', '.cancelPopup', function (e) {
        $(".bulk-refund-cont,.upload-tab-cont,.refund-bg").fadeOut();
    });

    $('body').on('click', '.close-popup', function (e) {
        $(".export-xl-popup,.lightbox-bg").fadeOut();
    });

    $('body').on('click', '.side-nav .menu-area > ul > li > a', function (e) {
        $(this).toggleClass("nav-arrow-down")
        $(this).parent().siblings().children("a").removeClass("nav-arrow-down")
        $(this).parent().siblings().children("ul").slideUp();
        $(this).siblings("ul").slideToggle();
    });


    $('body').on('click', '.edituser', function (e) {
        $(".edit-user").addClass("show");
        $(".adduser-bg").fadeIn();
        $("#amount").focus();
    });

    $('body').on('click', '.adduser-bg', function (e) {
        $(".add-user , .edit-user").removeClass("show");
        $(".add-user , .edit-user").removeClass("show");
        $(".adduser-bg").fadeOut();

    });

    $('body').on('click', '.myprofile-tab li a', function (e) {
        $(this).parent().siblings().children().removeClass("active")
        $(this).addClass("active");
        tabclass = $(this).parent().attr('class');
        //alert(tabclass)
        $(".myprofile > div").fadeOut();
        $("#" + tabclass).fadeIn();

    });

    $('body').on('click', '.choose-field-button', function (e) {
        $(".choose-field-area").slideToggle();
        $(".filter-area").slideUp();
        $(this).toggleClass("active-b")
        $(".filter-button").removeClass("active-b")
    });

    $('body').on('click', '.filter-button', function (e) {
        $(".filter-area").slideToggle();
        $(".choose-field-area").slideUp();
        $(this).toggleClass("active-b")
        $(".choose-field-button").removeClass("active-b")
    });


/*    $('body').on('keydown','.disableTyping', function (e) {
        e.preventDefault();
    });*/

  /*  $('body').on('click', '.subheader ul li a', function (e) {
        $(".subheader ul li a").removeClass("active");
        $(this).addClass("active");
    });*/
});





