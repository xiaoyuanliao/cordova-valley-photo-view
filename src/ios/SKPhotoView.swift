import UIKit
import SKPhotoBrowser

class SKPhotoView: UIViewController, SKPhotoBrowserDelegate {

    @IBOutlet weak var imageView: UIImageView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }
    
    func showPhotos(current: Int, imageStr: String) {
        
        if imageStr.isEmpty {
            
        }else {
            var images = [SKPhoto]()
            let imgArr = imageStr.characters.split(separator: ",").map(String.init)
            for item in imgArr {
                let photo = SKPhoto.photoWithImageURL(item)
                photo.shouldCachePhotoURLImage = true
                images.append(photo)
            }
            let browser = SKPhotoBrowser(photos: images)
            
            browser.initializePageIndex(current)
            browser.delegate = self
            self.present(browser, animated: true, completion: nil)
        }
        
        
    }
}
