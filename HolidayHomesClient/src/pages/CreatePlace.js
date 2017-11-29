import React from 'react';
const URL = require("../../package.json").serverURL;


export default class CreatePlace extends React.Component {
    constructor() {
        super();
        this.state = { newPlace: { placeName: "", street: "", city: "", zipCode: 0, country: "" } };
      }

      
    
      componentWillMount() {
    
      }
    
      onChange = (e) => {
        const propertyName = e.target.id;
        const value = e.target.value;
        let newPlace = this.state.newPlace;
        newPlace[propertyName] = value;
        this.setState({ newPlace });
      }

      handleSubmit = (event) => {
        const options = {
          method: "POST",
          body: JSON.stringify(this.state.userItself),
          headers: { "Content-Type": "application/json" }
      }
        event.preventDefault();
        let placeName = this.state.placeName;
        console.log(placeName);
        let status;
        fetch(URL + "api/checkName/" + placeName)
        .then(res => {
          status = res.status;
          console.log(status);
        }).catch(error => {
          console.log(error);
        })
        switch(status) {
          case 202: //placeName is free
            this.imgUpload; //Next fetch
            break;
          case 409: //placeName is already used
            break;
          case 503: //Something went wrong
        }
      }

      imgUpload = () => {
        
      }
    
    
      render() {
        return (
          <div className="container">
            <form className="form-signin" onSubmit={this.handleSubmit}>
              <h3 className="form-signin-heading">Create a NicePlace</h3>
              <p>Please fill in the necessary information:</p>
              <label htmlFor="placeName" className="sr-only">Place Name</label>
              <input type="text" value={this.state.placeName} onChange={this.onChange} id="placeName" className="form-control" placeholder="Give it a catchy name ;)" required autoFocus />
              <label htmlFor="zip" className="sr-only">GPS Latitude</label>
              <input type="text" value={this.state.zipCode} onChange={this.onChange} id="zip" className="form-control" placeholder="GPS Latitude" required />
              <label htmlFor="coutry" className="sr-only">GPS Longtitude</label>
              <input type="text" value={this.state.country} onChange={this.onChange} id="coutry" className="form-control" placeholder="GPS Longtitude" required />
              <label htmlFor="coutry" className="sr-only">Description</label>
              <input type="text" value={this.state.country} onChange={this.onChange} id="coutry" className="form-control" placeholder="Description" required />
              <hr />
              <div className="well well-sm">
              <label htmlFor="imageUpload">Upload Image</label>
              <input type="file" name="imageUpload" accept=".png" />
              <br />
              <p>(We will only take : .png, .jpg, .jpeg)</p>
              </div>
              <button className="btn btn-lg btn-primary btn-block" type="submit"><span className="glyphicon glyphicon-floppy-disk"></span> Create it</button>
              <br />
            </form>
            {this.state.err && (
              <div className="alert alert-danger errmsg" role="alert">
                {this.state.err}
              </div>
            )}
          </div>
        )
      }

}
