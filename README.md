# cordova-valley-photo-view
一个简单多图浏览插件 基于[SKPhotoBrowser ](https://github.com/suzuki-0000/SKPhotoBrowser), [PhotoPicker](https://github.com/mwaterfall/MWPhotoBrowser).

Simple PhotoBrowser/Viewer inspired  based on [SKPhotoBrowser ](https://github.com/suzuki-0000/SKPhotoBrowser), [PhotoPicker](https://github.com/mwaterfall/MWPhotoBrowser).

Android平台开发参照  [CordovaFancyImagePicker ](https://github.com/muthuridennis/CordovaFancyImagePicker) 


## features
- 根据图片链接字符串和当前显示第几张显示图片
- Display one or more images by providing string of URL array and current index.


## Requirements
- iOS 9.0+
- Swift 2.0+
- Android4+

## Installation

cordova plugin add https://github.com/xiaoyuanliao/cordova-valley-photo-view.git

#### CocoaPods
available on CocoaPods. Just add the following to your project Podfile:
```
pod 'SKPhotoBrowser'
use_frameworks!
```

## Usage
```
var imgPaths = 'http://www.chinavvv.com/sitesources/chinavvv/theme/uxtheme10008/images/img_21.png,http://www.chinavvv.com/sitesources/chinavvv/theme/uxtheme10008/images/img_23.png'
var options = {
          images : imgPaths,
          current : current
        };
 ValleyPhotoView.showPhotos(options,function (data) {
   console.log(data)
 },function (e) {
   console.log(e)
 })
```

## Photos from
- [Unsplash](http://www.chinavvv.com)

## License

