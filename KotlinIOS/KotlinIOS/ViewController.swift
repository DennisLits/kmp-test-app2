import UIKit
import SharedCode

class ViewController: UIViewController {
    
    var lifecycle = KLifecycle()
    
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
                      uiContext:IosUtilities().getIOSDispatcher(),
                      lifeCycleOwner: lifecycle        )
    }()
    

    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupUI()
        
        
        // The below DOES work if you want to observe and react to life cycles run in swift
        /*
        presenter.testLiveData.observe(lifecycle: lifecycle) { (value) -> KotlinUnit in
            if let viewState = value as? CurrentCityWeatherResponse {
                NSLog("in swift logging name -> " + viewState.name)
            }
            return KotlinUnit()
        }
        */
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        presenter.onStart()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        presenter.onStop()
    }
    
    @IBAction func goButtonTapped(_ sender: Any) {
        
        let userNameText = userNameTextField.text ?? ""
        presenter.loadData(searchString: userNameText)
      
       
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
    func displayData(data: MainDisplayData) {
        
        NSLog("WE ARE NOW DISPLAYING REAL DATA -> " + data.name)
        var displayText = ""
        if (data.fromNetwork) {
            displayText = data.name + " from network"
        }
        else {
            displayText = data.name + " from DB"
        }
        userNameLabel.text = displayText
        showUserDetails()
    }
    

    func showError(error: String) {
        NSLog("ERRROR is -> " + error)
    
    }
    
    
    func showLoader() {
        activityIndicator.startAnimating()
    }
    
    func hideLoader() {
        activityIndicator.stopAnimating()
    }
    
    /*
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
    */
    
}

