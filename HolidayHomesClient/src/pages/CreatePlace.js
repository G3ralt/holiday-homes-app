import React from 'react';

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
    
    
      render() {
        return (
          <div className="container">
            <form className="form-signin" onSubmit={this.handleSubmit}>
              <h3 className="form-signin-heading">Create a NicePlace</h3>
              <p>Please fill in the necessary information:</p>
              <label htmlFor="placeName" className="sr-only">Place Name</label>
              <input type="text" value={this.state.placeName} onChange={this.onChange} id="placeName" className="form-control" placeholder="Give it a catchy name ;)" required autoFocus />
              <label htmlFor="street" className="sr-only">Street</label>
              <input type="text" value={this.state.street} onChange={this.onChange} id="street" className="form-control" placeholder="Street" required />
              <label htmlFor="city" className="sr-only">City</label>
              <input type="text" value={this.state.city} onChange={this.onChange} id="city" className="form-control" placeholder="City" required />
              <label htmlFor="zip" className="sr-only">ZIP Code</label>
              <input type="text" value={this.state.zipCode} onChange={this.onChange} id="zip" className="form-control" placeholder="ZIP Code (e.g. 2810)" required />
              <label htmlFor="coutry" className="sr-only">Coutry</label>
              <input type="text" value={this.state.country} onChange={this.onChange} id="coutry" className="form-control" placeholder="Coutry" required />
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
