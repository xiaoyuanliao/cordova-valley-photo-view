 module.exports = {
  getPhotos: function(arg0, successCallback, errorCallback) {
  	var maxImages = arg0.maxImages || 5;
  	var quality = arg0.quality || 10;
  	var images = arg0.images;
  	var current = arg0.current;

    cordova.exec(successCallback,
                 errorCallback, // No failure callback
                 "ValleyPhotoView",
                 "selectPhotos",
                 [maxImages, quality,images,current]);
  }
};