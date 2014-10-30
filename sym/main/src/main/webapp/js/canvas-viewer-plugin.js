(function ($) {
    // default settings
    var defaultConfig = { viewCanvasId: "view-canvas" };

    // actual settings
    var config;

    $.fn.viewInCanvas = function (params) {
        config = $.extend({}, defaultConfig, config, params);

        //canvas initial image loading
        var canvas = document.getElementById(config.viewCanvasId);
        if (canvas) {
            var context = canvas.getContext("2d");

            setInterval(function () {
                var src = canvas.getAttribute("src");
                if (src) {
                    var initImage = new Image();
                    initImage.onload = function () {
                        context.drawImage(this, 0, 0, canvas.width, canvas.height);
                    };
                    initImage.setAttribute("src", src + "?" + Math.random());
                }
            }, 6000);

            //setting image click handler
            $(this).on("click", function () {
                context.drawImage(this, 0, 0, 200, 200);
            });

        }


        return this;
    };
})(jQuery);