import UIKit
import SKPhotoBrowser

@objc(ValleyPhotoViewPlugin) class ValleyPhotoView: CDVPlugin, SKPhotoBrowserDelegate {

    @objc(showPhotos:)
    func showPhotos(command: CDVInvokedUrlCommand) {
            print("here")

            if command.arguments != nil && command.arguments.count > 0 {
                let imageStr = command.arguments[2] as! String
                let current = command.arguments[3] as! Int
                
                if imageStr.isEmpty {
                    
                }else {
                    var images = [SKPhoto]()
                    let imgArr = imageStr.characters.split(separator: ",").map(String.init)
                    for (index,item) in imgArr.enumerated() {
                        let photo = SKPhoto.photoWithImageURL(item)
                        photo.shouldCachePhotoURLImage = true
                        let imgCaption = index+1
                        photo.caption = String(imgCaption)+"(\(imgArr.count))"
                        images.append(photo)
                    }
                    let browser = SKPhotoBrowser(photos: images)
                    
                    browser.initializePageIndex(current)
                    browser.delegate = self
                    self.viewController.present(browser, animated: true, completion: nil)
                }
                
                
            }
     }
    
    
}
