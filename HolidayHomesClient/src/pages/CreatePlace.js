import React from 'react';
import auth from '../authorization/auth';
const URL = require("../../package.json").serverURL;

export default class CreatePlace extends React.Component {
  constructor() {
    super();
    this.state = {
      newPlace: {
        placeName: "",
        gpsLat: "",
        gpsLong: "",
        description: "",
        user: { username: auth.username },
        imgURL: ""
      },
      clientOrigin: window.location.origin
    };
  }

  onChange = (e) => {
    const propertyName = e.target.id;
    const value = e.target.value;
    let newPlace = this.state.newPlace;
    newPlace[propertyName] = value;
    this.setState({ newPlace });
  }

  checkForPlaceName = (event) => {
    event.preventDefault();
    let status;
    fetch(URL + "api/places/checkName/" + this.state.newPlace.placeName)
      .then(res => {
        status = res.status;
        switch (status) {
          case 202: //placeName is free
            this.imgUpload(); //Next fetch
            break;
          case 409: //placeName is already used
            alert("PlaceName already used");
            break;
          case 503: //Service unavailable
            alert("Service unavailable! Please try again later!");
            break;
          default:
        }
      })
  }

  imgUpload = () => {
    let image = document.querySelector('input[type="file"]');
    let data = new FormData();
    data.append('file', image.files[0]);
    data.append('placeName', this.state.newPlace.placeName);

    let status;
    fetch(URL + 'api/imgUpload', {
      method: 'POST',
      body: data
    }).then(res => {
      status = res.status;
      return res.json();
    }).then(json => {
      let newPlace = this.state.newPlace;
      newPlace['imgURL'] = json.imgURL;
      this.setState({ newPlace });
      switch (status) {
        case 200: //ImgUploaded is free
          this.submitCreatePlace();//Next fetch
          break;
        case 415: //Mediatype not supported
          alert("Mediatype not supported");
          break;
        case 503: //Service unavailable
          alert("Service unavailable! Please try again later!");
          break;
        default:
      }
    })
  }

  submitCreatePlace() {
    let status;
    const options = {
      method: "POST",
      body: JSON.stringify(this.state.newPlace),
      headers: { "Content-Type": "application/json" }
    }
    fetch(URL + "api/places/create", options)
      .then(res => {
        status = res.status;
        switch (status) {
          case 201: //Place created
            alert("Place created!");
            window.location.href = this.state.clientOrigin+"/#/myDashboard/myPlaces";
            break;
          case 406: //PlaceName already used
            alert("The Place Name is already used! Please try again with different name!");
            break;
          case 503: //Service unavailable
            alert("Service unavailable! Please try again later!");
            break;
          default:
        }
      })

  }


  render() {
    return (
      <div className="container">
        <form className="form-signin" onSubmit={this.checkForPlaceName}>
          <h3 className="form-signin-heading">Create a NicePlace</h3>
          <p>Please fill in the necessary information:</p>
          <label htmlFor="placeName" className="sr-only">Place Name</label>
          <input type="text" value={this.state.newPlace.placeName} onChange={this.onChange} id="placeName" className="form-control" placeholder="Give it a catchy name ;)" required autoFocus />
          <label htmlFor="gpsLat" className="sr-only">GPS Latitude</label>
          <input type="text" value={this.state.newPlace.gpsLat} onChange={this.onChange} id="gpsLat" className="form-control" placeholder="GPS Latitude" required />
          <label htmlFor="gpsLong" className="sr-only">GPS Longtitude</label>
          <input type="text" value={this.state.newPlace.gpsLong} onChange={this.onChange} id="gpsLong" className="form-control" placeholder="GPS Longtitude" required />
          <label htmlFor="description" className="sr-only">Description</label>
          <input type="text" value={this.state.newPlace.description} onChange={this.onChange} id="description" className="form-control" placeholder="Description" required />
          <hr />
          <div className="well well-sm">
            <label htmlFor="imageUpload">Upload Image</label>
            <input type="file" name="imageUpload" accept=".png, .jpg, .jpeg" required />
            <br />
            <p>(We will only take : .png, .jpg, .jpeg)</p>
          </div>
          <button className="btn btn-lg btn-primary btn-block" type="submit"><span className="glyphicon glyphicon-floppy-disk"></span> Create it</button>
          <br />
        </form>
      </div>
    )
  }

}
