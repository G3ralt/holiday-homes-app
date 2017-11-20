import jwtDecode from "jwt-decode";
import fetchHelper, { errorChecker } from "../facades/fetchHelpers"

const URL = require("../../package.json").serverURL;

class AuthenticationHandler {

  constructor() {
    this._token = null;  //Keps users logged in, even after a refresh of the page
    //this.failedLogin = false;
    this._userName = "";
    this._isAdmin = false;
    this._isUser = false;
    this._errorMessage = "";
  }

  get isloggedIn() {
    return this._token !== null;
  }

  get isUser() {
    return this._isUser;
  }

  get isAdmin() {
    return this._isAdmin;
  }
  get userName() {
    return this._userName;
  }

  setLoginObserver = (observer) => {
    this._loginObserver = observer;
  }

  setToken = (value) => {
    sessionStorage.token = value;
    this.initDataFromToken();
  }

  initDataFromToken = () => {
    if (!sessionStorage.token) {
      return;
    }
    console.log("Initializing Data From Token");
    this._token = sessionStorage.token;
    var decoded = jwtDecode(this._token);
    this._userName = decoded.username;
    this._isAdmin = false;
    this._isUser = false;
    decoded.roles.forEach(function (role) {
      if (role === "Admin") {
        this._isAdmin = true;
      }
      if (role === "User") {
        this._isUser = true;
      }
    }, this);
  }


  logout = () => {
    delete sessionStorage.token;
    this._token = null;
    this._userName = "";
    this._isAdmin = false;
    this._isUser = false;
    this._errorMessage = "";
    if (this._loginObserver) {
      this._loginObserver(false, this._userName,this._isUser,this._isAdmin);
    }
  }

  _userWasLoggenIn = (cb) => {
    if (cb) {
      cb(null, true);
      if (this._loginObserver) {
        this._loginObserver(true, this._userName,this._isUser,this._isAdmin);
      }
      return;
    }
  }

  login = (username, password, cb) => {
    this._errorMessage = "";
    if (this._token != null) {
      this._userWasLoggenIn(cb);
    }

    var user = { username, password };

    var options = {
      method: "POST",
      body: JSON.stringify(user),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }
    let resFromFirstPromise=null;  //Pass on response the "second" promise so we can read errors from server
    fetch(URL + "api/login", options)
      .then(res => {
        resFromFirstPromise = res;
        return res.json();
      })
      .then(data => {
        errorChecker(resFromFirstPromise, data);
        this.setToken(data.token);
        if (this._token != null) {
          this._userWasLoggenIn(cb);
        }
      })
      .catch(err => {
        console.log(err);
        if (cb) {
          cb({ errorMessage: fetchHelper.addJustErrorMessage(err) });
        }
      })
    return;
  }

  register = (username, password, cb) => {
	  this._errorMessage = "";
	  
	  var newUser = { username, password };

	  var options = {
		  method: "POST",
		  body: JSON.stringify(newUser),
		  headers: new Headers({
			  "Content-Type": "application/json"
		  })
	  }
          console.log(options)
	  fetch(URL + "api/register", options)
	  .then(res => {
		  return res.json();
	  }).then(data => {

	  })
	  return;
  }
  addPlace = (city, zip, street, gpsLocation, description, rating, imgUri, cb) => {
	  this._errorMessage = "";
	  
	  var newPlace = { city, zip, street, gpsLocation, description, rating, imgUri };

	  var options = {
		  method: "POST",
		  body: JSON.stringify(newPlace),
		  headers: new Headers({
			  "Content-Type": "application/json"
		  })
	  }
          console.log(options)
	  fetch(URL + "api/places", options)
	  .then(res => {
		  return res.json();
	  }).then(data => {

	  })
	  return;
  }

}

var auth = new AuthenticationHandler();

//Call init, if a new Instance was created due to a refresh (F5 or similar)
auth.initDataFromToken();

//Comment out for debugging
//window.auth = auth;

export default auth;


