import React from 'react';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;

export default class CreateRentable extends React.Component {
  constructor() {
    super();
    this.state = {
      newRentable: {
        rentableName: "",
        street: "",
        city: "",
        zipCode: 0,
        country: "",
        price: 0,
        imgURL: "",
        description: "",
        admin: { username: auth.username }
      },
      clientOrigin: window.location.origin
    };
  }

  onChange = (e) => {
    const propertyName = e.target.id;
    const value = e.target.value;
    let newRentable = this.state.newRentable;
    newRentable[propertyName] = value;
    this.setState({ newRentable });
  }

  checkForRentableName = (event) => {
    event.preventDefault();
    let options = fetchHelper.makeOptions("GET", true);
    fetch(URL + "api/rentables/checkName/" + this.state.newRentable.rentableName, options)
      .then(res => {
        switch (res.status) {
          case 202: //rentableName is free
            this.imgUpload(); //Next fetch
            break;
          case 409: //rentableName is already used
            alert("Rentable already used");
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
    data.append('placeName', this.state.newRentable.rentableName);
    let status;
    let headers = {};
    headers.Authorization = `Bearer ${sessionStorage.token}`;
    let options = {
      method: "POST",
      headers,
      body: data,
    }
    fetch(URL + 'api/imgUpload', options)
    .then(res => {
      status = res.status;
      return res.json();
    })
    .then(json => {
      let newRentable = this.state.newRentable;
      newRentable['imgURL'] = json.imgURL;
      this.setState({ newRentable });
      switch (status) {
        case 200: //ImgUploaded is done
          this.submitCreateRentable();//Next fetch
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

  submitCreateRentable() {
    let status;
    let options = fetchHelper.makeOptions("POST", true, this.state.newRentable);
    fetch(URL + "api/rentables/create", options)
      .then(res => {
        status = res.status;
        switch (status) {
          case 201: //Rentable created
            alert("Rentable created!");
            window.location.href = this.state.clientOrigin + "/#/rentables";
            break;
          case 406: //RentableName already used
            alert("The Rentable Name is already used! Please try again with different name!");
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
        <form className="form-signin" onSubmit={this.checkForRentableName}>
          <h3 className="form-signin-heading">Create Rentable</h3>
          <p>Please fill in the necessary information:</p>
          <label htmlFor="rentableName" className="sr-only">Rentable Name</label>
          <input type="text" value={this.state.rentableName} onChange={this.onChange} id="rentableName" className="form-control" placeholder="Give it a catchy name ;)" required autoFocus />
          <label htmlFor="street" className="sr-only">Street</label>
          <input type="text" value={this.state.street} onChange={this.onChange} id="street" className="form-control" placeholder="Street" required />
          <label htmlFor="city" className="sr-only">City</label>
          <input type="text" value={this.state.city} onChange={this.onChange} id="city" className="form-control" placeholder="City" required />
          <label htmlFor="zipCode" className="sr-only">ZIP Code</label>
          <input type="text" value={this.state.zipCode} onChange={this.onChange} id="zipCode" className="form-control" placeholder="ZIP Code (e.g. 2810)" required />
          <label htmlFor="country" className="sr-only">Country</label>
          <input type="text" value={this.state.country} onChange={this.onChange} id="country" className="form-control" placeholder="Country" required />
          <label htmlFor="price" className="sr-only">Price</label>
          <input type="text" value={this.state.price} onChange={this.onChange} id="price" className="form-control" placeholder="Price" required />
          <label htmlFor="description" className="sr-only">Description</label>
          <input type="text" value={this.state.description} onChange={this.onChange} id="description" className="form-control" placeholder="Description" required />
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
