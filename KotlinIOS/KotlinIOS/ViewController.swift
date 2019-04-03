import UIKit
import SharedCode

class ViewController: UIViewController {
    

    @IBOutlet weak var userNameTextField: UITextField! {
        didSet {
            userNameTextField.delegate = self
        }
    }
    @IBOutlet weak var searchButton: UIButton!
    @IBOutlet weak var activityIndicator: UIActivityIndicatorView!
    @IBOutlet weak var userPic: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var reposLabel: UILabel!
    @IBOutlet weak var gistsLabel: UILabel!
    @IBOutlet weak var bioLabel: UILabel!
    @IBOutlet weak var userDetailsView: UIView!
    
    lazy var presenter : MainPresenter = {
        MainPresenter(view: self,
                      repository: DataRepositoryImpl(),
                      uiContext:IosUtilities().getDispetcher()
        )
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    @IBAction func goButtonTapped(_ sender: Any) {
        
        let userNameText = userNameTextField.text ?? ""
        hideUserDetails()
        presenter.loadData(userName: userNameText)
    }
}

// MARK: UI Updates
private extension ViewController {
    
    func setupUI() {
        hideUserDetails()
        //hideKeyboardWhenTappedAround()
    }
    
    func showUserDetails() {
        userDetailsView.isHidden = false
    }
    
    func hideUserDetails() {
        userDetailsView.isHidden = true
    }
}

// MARK: UITextField Delegate
extension ViewController: UITextFieldDelegate {
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
}

// MARK: Presenter Delegate
extension ViewController: MainView {
    func showError(error: String) {
        NSLog("ERRROR is -> " + error)
        
    }
    
    
    func showLoader() {
        activityIndicator.startAnimating()
    }
    
    func hideLoader() {
        activityIndicator.stopAnimating()
    }
    
    func displayData(data: DisplayData) {
        
        userNameLabel.text = data.name
        
        let url = URL(string: data.avatarUrl)
        DispatchQueue.global().async {
            let data = try? Data(contentsOf: url!) //make sure your image in this url does exist, otherwise unwrap in a if let check / try-catch
            DispatchQueue.main.async {
                self.userPic.image = UIImage(data: data!)
            }
        }
        
        NSLog("avatarURL is -> " + data.avatarUrl)
        reposLabel.text = data.publicRepos
        gistsLabel.text = data.publicGists
        bioLabel.text = data.bio
        
        showUserDetails()
    }
    
}

